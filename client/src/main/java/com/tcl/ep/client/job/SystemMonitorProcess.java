package com.tcl.ep.client.job;

import com.tcl.ep.client.util.ClientConfiguration;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by panmin on 16-11-29.
 */
public class SystemMonitorProcess implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        if (ClientConfiguration.isTimerStatus()){
            SystemMonitorReport systemMonitorReport = new SystemMonitorReport();
            Thread thread = new Thread(systemMonitorReport);
            thread.start();
        }
    }
}
