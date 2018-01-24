package com.tcl.mie.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * -javaagent:/data/work/workspace/eclipse/eapa/agent/target/agent-jar-with-dependencies.jar
 * 
 * @author yi_liu
 * 
 */
public class CrossThreadTest {

    static BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    public static void main(String[] args) {
        new Thread(new A()).start();
        new Thread(new B()).start();
    }

    static class A implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i <= 10; i++) {
                put();
            }
        }

        public void put() {
            queue.add("xxxxxxxx");
        }

    }

    static class B implements Runnable {

        @Override
        public void run() {
            while (true) {
                String content;
                try {
                    content = queue.take();
                    consume(content);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void consume(String content) {
            System.out.println(content);
        }

    }
}
