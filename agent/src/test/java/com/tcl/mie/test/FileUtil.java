package com.tcl.mie.test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ep.util.StringUtil;

public class FileUtil {

    private final static Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 向文件中增量写文件
     * 
     * @param path
     * @param value
     */
    public static void writeToFileByLock(String path, String value) {
        RandomAccessFile rf = null;
        FileChannel fc = null;
        FileLock lock = null;
        ByteBuffer byteBuffer = null;
        try {
            rf = new RandomAccessFile(path, "rw");
            fc = rf.getChannel(); // 获得文件通道
            byteBuffer = ByteBuffer.wrap(StringUtil.append(value, "\n").getBytes());
            while (true) {
                try {
                    lock = fc.lock(fc.size() + 1, byteBuffer.limit(), false);// 获取阻塞锁
                    break;
                } catch (OverlappingFileLockException e) {
                    Thread.sleep(1 * 1000);
                    LOG.info("file locked");
                }
            }
            if (null != lock) {
                fc.position(fc.size());
                fc.write(byteBuffer);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                if (lock != null && lock.isValid()) {
                    lock.release();
                }
                if (fc != null && fc.isOpen()) {
                    fc.close();
                    fc = null;
                }
                if (rf != null) {
                    rf.close();
                    rf = null;
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

}
