package com.ep.util;

import java.io.File;

import com.ep.inst.perf.PerfUtil;

public class FileUtil {

    public static String getFileName(String filePath) {
        if (filePath == null) {
            return null;
        }
        int slashIdx = filePath.lastIndexOf("/");
        return filePath.substring(slashIdx + 1);
    }

    public static String getFileNamePrefix(String filePath) {
        String fileName = getFileName(filePath);
        if (fileName == null) {
            return null;
        }
        int dotIdx = fileName.indexOf(".");
        return fileName.substring(0, dotIdx);
    }

    public static String getFilePathSuffix(String filePath) {
        if (filePath == null) {
            return null;
        }
        int lastDotIndex = filePath.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return filePath.substring(lastDotIndex + 1);
    }

    public static String getFilePathExtractedSuffix(String filePath) {
        if (filePath == null) {
            return null;
        }
        int lastDotIndex = filePath.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return filePath.substring(0, lastDotIndex);
    }

    public static void renameFile(String srcName, String newName) {
        File file = new File(srcName);
        if (file.exists()) {
            file.renameTo(new File(newName));
        }
    }

    public static String getDirectoryPath(String filePath) {
        int slashIdx = filePath.lastIndexOf("/");
        if (slashIdx == -1) {
            return "";
        }
        return filePath.substring(0, slashIdx);
    }

    public static String getDefaultLogDirectory() {
        // as default, the root log folder will be the place of Agent.jar/logs
        String jarPath =
                PerfUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return jarPath.substring(0, jarPath.lastIndexOf("/")) + "/logs/";
    }
}
