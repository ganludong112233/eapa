package com.ep.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ep.util.PropertiesParser;
/**
 * 
 * @author yi_liu
 *
 */
public class Configuration {
    private final static String epClientUrlPrefix = "/ep/";
    private static PropertiesParser propParser;
    private final static String INGORE_PKGS_KEY = "ignore.pkgs";

    static {
        reloadConfiguration();
    }

    private static void reloadConfiguration() {
        // load the default properties file
        InputStream inputStream =
                Configuration.class.getResourceAsStream("/eapa-default.properties");

        InputStream userInputStream = null;

        Properties prop = new Properties();
        try {
            prop.load(inputStream);
            propParser = new PropertiesParser(prop);

            String jarPath =
                    Configuration.class.getProtectionDomain().getCodeSource().getLocation()
                            .getPath();
            String propLocation =
                    jarPath.substring(0, jarPath.lastIndexOf("/")) + "/eapa.properties";
            userInputStream = new FileInputStream(propLocation);
            prop.load(userInputStream);
        } catch (FileNotFoundException e) {
            System.out
                    .println("Warnning:The user eapa.properties files not found!!!! Agent will not work properly.");
        } catch (IOException e) {
            System.out.println("Error:fail to load the properties");
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {}
            if (userInputStream != null) {
                try {
                    userInputStream.close();
                } catch (IOException e) {}
            }

        }
        System.out.println("INFO:EAPA detected the serverEnv is " + getServerEnv());
    }

    public static PropertiesParser getPropParser() {
        return propParser;
    }

    public static long getProjectId() {
        return propParser.getLong("projectId");
    }

    public static String getEpClientUrlPrefix() {
        return epClientUrlPrefix;
    }

    public static String getServerEnv() {
        return propParser.getString("env");
    }
    
    public static boolean isDebug() {
        return propParser.getBoolean("debug");
    }

    public static String getIgnoredPkgs() {
        return propParser.getString(INGORE_PKGS_KEY);
    }
}
