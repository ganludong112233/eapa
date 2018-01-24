package com.ep.inst.weaver;

import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import com.ep.config.TraceConfig;

public abstract class TraceWeaver extends AbstractWeaver {

    public void doWeave(CtClass clazz, CtMethod method, ClassPool pool)
            throws CannotCompileException {
        doAddTraceId(clazz, method, pool);
    }

    public abstract void doAddTraceId(CtClass clazz, CtMethod method, ClassPool pool)
            throws CannotCompileException;

    @Override
    public Set<String> getAllowdPatterns() {
        Set<String> patterns = TraceConfig.getTranceEntrances();
        patterns.add("org.springframework.web.servlet.DispatcherServlet.doDispatch()");
        return patterns;
    }

    @Override
    public Set<String> getExcludedPatterns() {
        return null;
    }

}
