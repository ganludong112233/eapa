package com.ep.inst.weaver;

import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import com.ep.config.PerfConfig;
import com.ep.util.StringUtil;

public class PerformanceWeaver extends AbstractWeaver {

    private boolean webMontior = PerfConfig.monitorWebPerf();

    public void doWeave(CtClass clazz, CtMethod method, ClassPool pool)
            throws CannotCompileException {
        String className = clazz.getName();
        String methodName = method.getName();
        if (className.equals(WebFrameworkConstants.CLASS_SPRINGMVC)
                && methodName.equals(WebFrameworkConstants.METHOD_SPRINGMVC)) {
            if (webMontior) {
                doWeaveSpringMVC(clazz, method, pool);
            }
        } else {
            doDefaultWeave(clazz, method, pool);
        }
    }

    private void doWeaveSpringMVC(CtClass clazz, CtMethod method, ClassPool pool)
            throws CannotCompileException {
        method.insertBefore("PerfUtil.recordStartTime();");
        method.insertAfter("PerfUtil.recordEndTime($1.getRequestURI());", true);
    }

    private void doDefaultWeave(CtClass clazz, CtMethod method, ClassPool pool)
            throws CannotCompileException {
        method.insertBefore("PerfUtil.recordStartTime();");
        method.insertAfter(
                "PerfUtil.recordEndTime(\"" + clazz.getName() + "\",\"" + method.getName()
                        + "\",\"" + method.getSignature() + "\");", true);
    }

    @Override
    public Set<String> getAllowdPatterns() {
        String matchPattern = PerfConfig.getMatchedPattern();
        Set<String> patterns = StringUtil.split2Set(matchPattern, ",");
        patterns.add("org.springframework.web.servlet.DispatcherServlet.doDispatch()");
        return patterns;
    }

    @Override
    public Set<String> getExcludedPatterns() {
        String matchPattern = PerfConfig.getExcludedPattern();
        Set<String> patterns = StringUtil.split2Set(matchPattern, ",");
        return patterns;
    }

}
