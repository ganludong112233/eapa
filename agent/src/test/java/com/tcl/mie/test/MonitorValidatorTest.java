package com.tcl.mie.test;

import java.util.HashSet;
import java.util.Set;

import com.ep.inst.MonitorValidator;

public class MonitorValidatorTest {
    public static void main(String[] args) {
        testAnnotion();
    }

    public static void testAnnotion(){
        MonitorValidator validator = new MonitorValidator();
        validator.addAnnotion("org.springframework.stereotype.Controller", false);
        validator.addAnnotion("org.springframework.stereotype.Service", true);
        //validator.addAnnotion("org.springframework.web.bind.annotation.RequestMapping", true);
        
        Set<String> classAnnotions = new HashSet<>();
        Set<String> methodAnnotions = new HashSet<>();
        classAnnotions.add("org.springframework.stereotype.Controller");
        methodAnnotions.add("org.springframework.web.bind.annotation.RequestMapping");
//        if(validator.verifyClass("com.tcl.asg.controller.Admin", classAnnotions)){
//            System.out.println(true);
//        }else{
//            System.out.println(false);
//        }
        if(validator.verifyMethod("com.tcl.asg.controller.Admin", "", classAnnotions, methodAnnotions)){
            System.out.println(true);
        }else{
            System.out.println(false);
        }
        
        
        classAnnotions.add("org.springframework.stereotype.Service");
//        if(validator.verifyClass("com.tcl.asg.controller.Admin", classAnnotions)){
//            System.out.println(true);
//        }else{
//            System.out.println(false);
//        }
        
        if(validator.verifyMethod("com.tcl.asg.controller.Admin", "", classAnnotions, methodAnnotions)){
            System.out.println(true);
        }else{
            System.out.println(false);
        }
    }

    public static void testPattern() {
        MonitorValidator validator = new MonitorValidator();
        validator.addPattern("com.tcl.**.controller.Login.*()", false);
        validator.addPattern("com.tcl.**.controller.Admin.*Name()", false);
        validator.addPattern("com.tcl.**.service", false);
        validator.addPattern("com.tcl.**.service.Admin.*Cloud()", true);
        // if(validator.verifyClass("com.tcl.asg.controller.Admin", null)){
        // System.out.println(true);
        // }else{
        // System.out.println(false);
        // }
        // if(validator.verifyClass("com.tcl.asg.service.Admin", null)){
        // System.out.println(true);
        // }else{
        // System.out.println(false);
        // }

        if (validator.verifyMethod("com.tcl.asg.service.Admin", "MyName", null, null)) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
        if (validator.verifyMethod("com.tcl.asg.service.Admin", "MyCloud", null, null)) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
        // if(validator.verifyMethod("com.tcl.asg.controller.Admin", "MyNames", null, null)){
        // System.out.println(true);
        // }else{
        // System.out.println(false);
        // }
        // if(validator.verifyMethod("com.tcl.asg.controller.Login", "MyNames", null, null)){
        // System.out.println(true);
        // }else{
        // System.out.println(false);
        // }
    }
}
