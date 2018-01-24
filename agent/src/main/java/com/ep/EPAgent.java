package com.ep;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

import com.ep.config.Configuration;
import com.ep.hook.ShutdownHookContext;
import com.ep.inst.EPClassTransformer;

public class EPAgent {
    static private Instrumentation inst = null;

    /**
     * This method is called before the application’s main-method is called, when this agent is
     * specified to the Java VM.
     **/
    public static void premain(String agentArgs, Instrumentation _inst) {
        System.out.println("EPAgent.premain() was called.");
        // Initialize the static variables we use to track information.
        inst = _inst;
        // Set up the class-file transformer.
        ClassFileTransformer trans = new EPClassTransformer();
        System.out.println("Adding a EPClassTransformer instance to the JVM.");
        inst.addTransformer(trans);
        if (Configuration.getPropParser().getBoolean("collecthost")) {
            startCollectHostStatus();
        }
        if (Configuration.getPropParser().getBoolean("collectjvm")) {
            startCollectJVMStatus();
        }
        registerShutdownHook();
    }

    public static void startCollectHostStatus() {
        System.out.println("Start to collect Host status............");
    }

    public static void startCollectJVMStatus() {
        System.out.println("Start to collect JVM status..............");
    }

    public static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                ShutdownHookContext.execHooks();
            }
        }));
    }
}
