package com.ep.inst.weaver;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class AfterTraceWeaver extends TraceWeaver {

    @Override
    public void doAddTraceId(CtClass clazz, CtMethod method, ClassPool pool)
            throws CannotCompileException {
        /**
         * remove the traceId while the method invoke end.
         */
        method.insertAfter("Tracer.removeTraceId();");
    }

}
