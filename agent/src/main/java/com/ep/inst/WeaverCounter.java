package com.ep.inst;

/**
 * Count the method weaved . If one method have more than one weaver it will be counted duplicated.
 * 
 * @author yi_liu
 * 
 */
public class WeaverCounter {
    private static int counter = 0;

    public static void incre() {
        counter++;
    }

    public static void print() {
        System.out.println("The EAPA totally weaved (" + counter + ") method.");
    }
}
