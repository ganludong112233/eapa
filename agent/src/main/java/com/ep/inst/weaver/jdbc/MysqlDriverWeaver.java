package com.ep.inst.weaver.jdbc;

import java.util.HashSet;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import com.ep.inst.weaver.AbstractWeaver;

public class MysqlDriverWeaver extends AbstractWeaver {

    @Override
    public Set<String> getAllowdPatterns() {
        Set<String> patterns = new HashSet<String>();
        patterns.add("com.mysql.jdbc.NonRegisteringDriver.connect()");
        // patterns.add("com.tcl.mie.test.TestPerf.getTarget()");
        return patterns;
    }

    @Override
    public Set<String> getExcludedPatterns() {
        return null;
    }

    @Override
    public void doWeave(CtClass clazz, CtMethod method, ClassPool pool)
            throws CannotCompileException {
        CtMethod cmethod = CtNewMethod.copy(method, clazz, null);
        cmethod.setName("connect0");
        clazz.addMethod(cmethod);
        method.setBody("return new com.ep.jdbc.EpConnection(connect0($1,$2));");
        // method.setBody("return new com.ep.jdbc.Proxy(connect0($1));");
    }

}
