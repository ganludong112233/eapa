package com.ep.inst;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public interface Weaver {

    public MonitorValidator getMonitorValidator();

    public void weave(CtClass clazz, CtMethod method, ClassPool pool) throws CannotCompileException,NotFoundException;
}
