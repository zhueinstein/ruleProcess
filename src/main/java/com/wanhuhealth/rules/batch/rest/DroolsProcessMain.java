package com.wanhuhealth.rules.batch.rest;


import com.wanhuhealth.rules.batch.export.Json2ExcelMain;
import com.wanhuhealth.rules.utils.RuleTaskThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by admin on 2017/7/17.
 */
@Component
public class DroolsProcessMain {
    @Autowired
    ThreadPoolTaskExecutor threadPoolExecutor;
    @Autowired
    DroolsProcessTaskUnit batchRunTask;
    @PostConstruct
    public void batchRunTask() throws Exception {
        Long begin = System.currentTimeMillis();
        threadPoolExecutor.execute(new RuleTaskThread("2015-11-01", "2015-12-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2015-12-01", "2015-01-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-01-01", "2016-02-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-02-01", "2016-03-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-03-01", "2016-04-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-04-01", "2016-05-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-05-01", "2016-06-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-06-01", "2016-07-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-07-01", "2016-08-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-08-01", "2016-09-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-09-01", "2016-10-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-10-01", "2016-11-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-11-01", "2016-12-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2016-12-01", "2017-01-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2017-01-01", "2017-02-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2017-02-01", "2017-03-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2017-03-01", "2017-04-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2017-04-01", "2017-05-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2017-05-01", "2017-06-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2017-06-01", "2017-07-01", batchRunTask));
        threadPoolExecutor.execute(new RuleTaskThread("2017-07-01", "2017-08-01", batchRunTask));
//        threadPoolExecutor.execute(new RuleTaskThread("2016-03-07", "2016-03-08", batchRunTask));
        while(true){
            if(threadPoolExecutor.getActiveCount() == 0){
                DroolsProcessTaskUnit.fw.close();
                DroolsProcessTaskUnit.modelFw.close();
                Long end = System.currentTimeMillis();
                System.out.println(String.format("Multi thread is processed! It costs %s%s", batchRunTask.df.format(Double.valueOf(end - begin) / (1000 * 60)), "minutes"));
                System.out.println("xml文件转换成xlsx文件开始：");
                Json2ExcelMain.export(Json2ExcelMain.readList());
                break;
            }else {
                Thread.sleep(5000);
                System.out.println(String.format("批量处理任务进度：%s%s",
                       (Double.valueOf(threadPoolExecutor.getThreadPoolExecutor().getCompletedTaskCount())/
                                threadPoolExecutor.getThreadPoolExecutor().getTaskCount())* 100,"%"));
            }
        }

//        System.out.println(threadPoolExecutor.getThreadPoolExecutor().getCorePoolSize());
    }
}
