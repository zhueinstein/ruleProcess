package com.wanhuhealth.rules.utils;


import com.wanhuhealth.rules.batch.rest.MultiThreadProcessTaskUnit;

/**
 * Created by admin on 2017/7/17.
 */
public class RuleTaskThread extends Thread {
    MultiThreadProcessTaskUnit batchRunTask;
    private String start;
    private String end;

    public RuleTaskThread(String start, String end, MultiThreadProcessTaskUnit batchRunTask){
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
