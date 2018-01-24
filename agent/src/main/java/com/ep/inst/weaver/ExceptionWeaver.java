package com.ep.inst.weaver;

import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.ep.config.ExceptionConfig;
import com.ep.config.PerfConfig;
import com.ep.util.StringUtil;

public class ExceptionWeaver extends AbstractWeaver {
	private boolean webMontior = PerfConfig.monitorWebPerf();
	
    @Override
    public void doWeave(CtClass clazz, CtMethod method, ClassPool pool)
            throws CannotCompileException, NotFoundException {
    	CtClass execptionType = null;
        execptionType = pool.get("java.lang.Exception");
        
    	String className = clazz.getName();
        String methodName = method.getName();
    	if (className.equals(WebFrameworkConstants.CLASS_SPRINGMVC)
                && methodName.equals(WebFrameworkConstants.EXCEPTION_SPRINGMVC)) {
            if (webMontior) {
            	doWeaveSpringMVC(clazz, method, execptionType);
            }
        }else{
        	doDefaultWeave(clazz, method, execptionType);
        }
    }
    
    private void doWeaveSpringMVC(CtClass clazz, CtMethod method, CtClass execptionType)
            throws CannotCompileException {
        method.insertBefore("com.ep.util.ExceptionUtil.setExecptionMark();");
    	method.addCatch(
                "{ ExceptionResolve.collecte($e,\"" + clazz.getName() + "\",\"" + method.getName()
                        + "\",\"" + method.getSignature() + "\"); throw $e; }", execptionType);
        
    }

    private void doDefaultWeave(CtClass clazz, CtMethod method, CtClass execptionType)
            throws CannotCompileException {
    	method.addCatch(
                "{ ExceptionResolve.collecte($e,\"" + clazz.getName() + "\",\"" + method.getName() + "\",\"" 
                		+ method.getSignature() + "\"); throw $e; }", execptionType);
    }

    @Override
    public Set<String> getAllowdPatterns() {
        String matchPattern = ExceptionConfig.getMatchedPattern();
        Set<String> patterns = StringUtil.split2Set(matchPattern, ",");
        patterns.add("org.springframework.web.servlet.DispatcherServlet.processHandlerException()");
        return patterns;
    }

    @Override
    public Set<String> getExcludedPatterns() {
        String excludedPattern = ExceptionConfig.getExcludedPattern();
        Set<String> patterns = StringUtil.split2Set(excludedPattern, ",");
        return patterns;
    }
}
