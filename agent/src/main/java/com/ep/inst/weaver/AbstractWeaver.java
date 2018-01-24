package com.ep.inst.weaver;

import java.util.HashSet;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.ep.inst.MonitorValidator;
import com.ep.inst.Weaver;

public abstract class AbstractWeaver implements Weaver {

    protected MonitorValidator moValidator = new MonitorValidator();

    public AbstractWeaver() {
        moValidator.addPatterns(getAllowdPatterns(), false);
        moValidator.addPatterns(getExcludedPatterns(), true);
    }

    public abstract Set<String> getAllowdPatterns();

    public abstract Set<String> getExcludedPatterns();

    public abstract void doWeave(CtClass clazz, CtMethod method, ClassPool pool)
            throws CannotCompileException, NotFoundException;

    public boolean isEligible(CtClass clazz, CtMethod method) {
        return moValidator.verifyMethodPatternList(clazz.getName().replace("/", "."),
                method.getName());
    }

    public MonitorValidator getMonitorValidator() {
        return this.moValidator;
    }

    @Override
    public void weave(CtClass clazz, CtMethod method, ClassPool pool) throws CannotCompileException,NotFoundException {
        if (!isEligible(clazz, method)) {
            return;
        }
        doWeave(clazz, method, pool);
    }

    public static Set<String> getAnnotionSet(Object[] annotionObjects) {
        Set<String> set = new HashSet<String>();
        for (Object obj : annotionObjects) {
            String objectStr = obj.toString();
            String className = objectStr.substring(0, objectStr.lastIndexOf("@"));
            set.add(className);
        }
        return set;
    }
}
