package com.wanhuhealth.rules.utils;

import java.text.DecimalFormat;

/**
 * Created by admin on 2017/7/18.
 */
public class CountClick {
    private static DecimalFormat df   = new DecimalFormat("######0");
    private Integer count = 0 ;
    private String processing = "0";
    private Double total = 0.0;
    private String threadName;
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public void processing(CountClick click){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(click.getThreadName())) {
            System.err.println(String.format("进度：Current thread is %s，the progress is %s%s", click.getThreadName(), click.getCount()/click.getTotal() * 100, "%"));
        }else{
            click.setProcessing(((click.getCount()/click.getTotal())+ "").substring(0,2));
            System.out.println(click.getCount() / click.getTotal() * 100 + "%");
        }
    }
}
