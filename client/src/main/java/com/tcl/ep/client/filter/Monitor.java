package com.tcl.ep.client.filter;

import com.tcl.ep.client.EPClient;
import com.tcl.ep.client.util.ClientConfiguration;
import com.tcl.ep.client.constant.EapaClientConstants;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by panmin on 16-11-28.
 */
public class Monitor implements MethodInterceptor{
    public static final Logger logger = LoggerFactory.getLogger(Monitor.class);

    private boolean switchStatus;
    private boolean timerStatus;
    private String projectId;
    private String module;

    Monitor(){
        ClientConfiguration.setSwitchStatus(this.switchStatus);
        ClientConfiguration.setTimerStatus(this.timerStatus);
        ClientConfiguration.setModule(this.module);
        try {
            ClientConfiguration.setProjectId(Long.parseLong(this.projectId));
        }catch (NumberFormatException e){
            logger.error("NumberFormatException");
        }
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (isSwitchStatus()) {
            return startMethodMonitor(invocation);
        }else {
            return endMethodMonitor(invocation);
        }
    }

    private Object startMethodMonitor(MethodInvocation invocation) throws Throwable{


        try {
            invocation.proceed();
        } catch (Exception e) {
            EPClient.saveException(e);
            e.printStackTrace();
        }
        return null;
    }

    private Object endMethodMonitor(MethodInvocation invocation) throws Throwable{

        return invocation.proceed();
    }

    public boolean isSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(boolean switchStatus) {
        this.switchStatus = switchStatus;
    }

    public boolean isTimerStatus() {
        return timerStatus;
    }

    public void setTimerStatus(boolean timerStatus) {
        this.timerStatus = timerStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
