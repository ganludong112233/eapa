package com.ep.inst;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AccessFlag;

import com.ep.config.Configuration;
import com.ep.config.ExceptionConfig;
import com.ep.config.PerfConfig;
import com.ep.config.SQLConfig;
import com.ep.inst.perf.PerfUtil;
import com.ep.inst.trace.Tracer;
import com.ep.inst.weaver.AbstractWeaver;
import com.ep.inst.weaver.AfterTraceWeaver;
import com.ep.inst.weaver.BeforeTraceWeaver;
import com.ep.inst.weaver.ExceptionWeaver;
import com.ep.inst.weaver.PerformanceWeaver;
import com.ep.inst.weaver.jdbc.MysqlDriverWeaver;
import com.ep.util.StringUtil;

/**
 * 
 * @author yi_liu
 * 
 */
public class EPClassTransformer extends FilterClassTransformer {

    private MonitorValidator moValidator = null;

    public List<Weaver> weavers = new ArrayList<Weaver>();
    public Map<String, String> filteredMethod = new HashMap<String, String>();
    public Set<String> ignoredPkgs = StringUtil.split2Set(Configuration.getIgnoredPkgs(), ",");

    public EPClassTransformer() {
        // add all weavers
        weavers.add(new BeforeTraceWeaver());
        if (PerfConfig.isEnabled()) {
            weavers.add(new PerformanceWeaver());
        }
        if (ExceptionConfig.isEnabled()) {
            weavers.add(new ExceptionWeaver());
        }
        weavers.add(new AfterTraceWeaver());

        if (SQLConfig.isMonitorEnabled()) {
            weavers.add(new MysqlDriverWeaver());
        }

        ClassPool pool = ClassPool.getDefault();
        pool.importPackage(this.getClass().getPackage().getName());
        pool.importPackage(Tracer.class.getPackage().getName());
        pool.importPackage(PerfUtil.class.getPackage().getName());
        pool.importPackage(AbstractWeaver.class.getPackage().getName());
        moValidator = new MonitorValidator();

        for (Weaver w : weavers) {
            w.getMonitorValidator().copyTo(moValidator);;
        }

        fullfillFilteredMethod();
    }

    private void fullfillFilteredMethod() {
        filteredMethod.put("wait", null);
        filteredMethod.put("toString", null);
        filteredMethod.put("equals", null);
        filteredMethod.put("finalize", null);
        filteredMethod.put("wait", null);
    }

    /**
     * As default, Only allowed pattern will be eligible for transforming.
     * 
     * @param className
     * @return
     */
    public boolean isEligible(String className) {
        if (moValidator.verifyClassPatternList(className)) {
            return true;
        }
        return false;
    }

    public byte[] doTransform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        className = className.replace("/", ".");
        if (!isEligible(className)) {
            return classfileBuffer;
        }

        for (String pkg : ignoredPkgs) {
            if (className.indexOf("." + pkg + ".") != -1) {
                return classfileBuffer;
            }
        }

        byte[] transformed = null;
        InputStream classIs = null;
        CtClass clazz = null;
        try {
            clazz = pool.get(className);

            for (CtMethod method : clazz.getMethods()) {
                // As the native and abstract method has not method body , so should skip
                // them.
                if ((AccessFlag.NATIVE & method.getModifiers()) != 0) {
                    continue;
                }

                if ((AccessFlag.ABSTRACT & method.getModifiers()) != 0) {
                    continue;
                }

                if (filteredMethod.containsKey(method.getName())) {
                    continue;
                }

                for (Weaver mw : weavers) {
                    mw.weave(clazz, method, pool);
                }

            }
            transformed = clazz.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not instrument  " + className + ",  exception : "
                    + e.getMessage());
            return classfileBuffer;
        } finally {
            if (clazz != null) {
                clazz.detach();
            }
            if (classIs != null) {
                try {
                    classIs.close();
                } catch (IOException e) {}
            }
        }
        return transformed;
    }

}
