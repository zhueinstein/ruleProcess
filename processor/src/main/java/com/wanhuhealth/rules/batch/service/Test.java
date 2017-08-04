package com.wanhuhealth.rules.batch.service;

import com.wanhuhealth.rules.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zcx on 2017/8/4.
 */
public class Test {

    public static void main(String[] args) {

        Integer gap = 30; // 30天为一个线程的计算周期
        Calendar to = Calendar.getInstance();
        Calendar d = to;
        to.set(2015, 10, 01);
        to.add(Calendar.DATE, gap);
        String taskBegin = DateUtils.formatDate(new Date(to.getTimeInMillis()),"yyyy-MM-dd");
        System.out.println(DateUtils.formatDate(new Date(d.getTimeInMillis()), "yyyy-MM-dd"));
        System.out.println(taskBegin);
    }
}
