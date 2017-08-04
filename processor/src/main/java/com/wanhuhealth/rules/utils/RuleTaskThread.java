package com.wanhuhealth.rules.utils;


import com.wanhuhealth.rules.batch.service.DroolsProcessTaskService;

/**
 * Created by admin on 2017/7/17.
 */
public class RuleTaskThread extends Thread {
    DroolsProcessTaskService batchRunTask;
    private String start;
    private String end;

    public RuleTaskThread(String start, String end, DroolsProcessTaskService batchRunTask){
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
