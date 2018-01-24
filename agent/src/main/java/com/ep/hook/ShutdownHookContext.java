package com.ep.hook;

import java.util.ArrayList;
import java.util.List;

public class ShutdownHookContext {
    public static List<ShutdownHook> hooks = new ArrayList<ShutdownHook>();

    public static void registerHook(ShutdownHook hook) {
        hooks.add(hook);
    }

    public static void execHooks() {
        for (ShutdownHook hook : hooks) {
            hook.shutdown();
        }
    }
}
