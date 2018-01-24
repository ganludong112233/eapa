package com.ep.inst.weaver;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class BeforeTraceWeaver extends TraceWeaver {

    @Override
    public void doAddTraceId(CtClass clazz, CtMethod method, ClassPool pool)
            throws CannotCompileException {
        method.insertBefore("Tracer.generateTraceId();");
    }

}
