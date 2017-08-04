package com.wanhuhealth.rules.batch.service;


import com.wanhuhealth.rules.batch.excel.Json2ExcelMain;
import com.wanhuhealth.rules.utils.DateUtils;
import com.wanhuhealth.rules.utils.RuleTaskThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2017/7/17.
 */
@Component
public class DroolsProcessMainService {
    @Autowired
    ThreadPoolTaskExecutor threadPoolExecutor;
    @Autowired
    DroolsProcessTaskService batchRunTask;
    @PostConstruct
    public void batchRunTask() throws Exception {
        Long begin = System.currentTimeMillis();
        Calendar from = Calendar.getInstance();
        from.set(2015,10,01); // 2015-11-01开始
        Integer gap = 30; // 30天为一个线程的计算周期
        Calendar to = Calendar.getInstance();
        to.set(2015, 10, 01);
        do{
            to.add(Calendar.DATE, gap);
            String taskBegin = DateUtils.formatDate(new Date(from.getTimeInMillis()),"yyyy-MM-dd");
            String taskEnd = DateUtils.formatDate(new Date(to.getTimeInMillis()), "yyyy-MM-dd");
            threadPoolExecutor.execute(new RuleTaskThread(taskBegin, taskEnd, batchRunTask));
            from.add(Calendar.DATE, gap);
        }while (from.getTimeInMillis() < System.currentTimeMillis());
//        threadPoolExecutor.execute(new RuleTaskThread("2016-01-25", "2016-01-26", batchRunTask));
        while(true){
            if(threadPoolExecutor.getActiveCount() == 0){
                DroolsProcessTaskService.fw.close();
                DroolsProcessTaskService.modelFw.close();
                Long end = System.currentTimeMillis();
                System.out.println(String.format("Multi thread is processed! It costs %s%s", batchRunTask.df.format(Double.valueOf(end - begin) / (1000 * 60)), "minutes"));
                System.out.println("xml文件转换成xlsx文件开始：");
                Json2ExcelMain.export(Json2ExcelMain.readList());
                Json2ExcelMain.exportEveryRulePercent();
                Json2ExcelMain.exportTotalPercent();
                break;
            }else {
                Thread.sleep(5000);
                System.out.println(String.format("批量处理任务进度：%s%s",
                       (Double.valueOf(threadPoolExecutor.getThreadPoolExecutor().getCompletedTaskCount())/
                                threadPoolExecutor.getThreadPoolExecutor().getTaskCount())* 100,"%"));
            }
        }
    }
}
