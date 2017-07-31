package com.wanhuhealth.rules.utils;


import com.wanhuhealth.rules.batch.rest.DroolsProcessTaskUnit;

/**
 * Created by admin on 2017/7/17.
 */
public class RuleTaskThread extends Thread {
    DroolsProcessTaskUnit batchRunTask;
    private String start;
    private String end;

    public RuleTaskThread(String start, String end, DroolsProcessTaskUnit batchRunTask){
        this.start = start;
        this.end = end;
        this.batchRunTask = batchRunTask;
    }
    @Override
    public void run() {
        try {
            batchRunTask.batchRun(start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
