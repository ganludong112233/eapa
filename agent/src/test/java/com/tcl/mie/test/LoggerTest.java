package com.tcl.mie.test;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerTest {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("xxx");
        logger.addHandler(new ConsoleHandler());
        logger.setLevel(Level.FINEST);
        logger.log(Level.FINE, "xxxx");
    }

}
