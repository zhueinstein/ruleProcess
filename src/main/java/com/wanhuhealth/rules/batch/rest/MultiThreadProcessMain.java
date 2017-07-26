package com.wanhuhealth.rules.batch.rest;


import com.wanhuhealth.rules.utils.RuleTaskThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by admin on 2017/7/17.
 */
@Component
public class MultiThreadProcessMain {
    @Autowired
    ThreadPoolTaskExecutor threadPoolExecutor;
    @Autowired
    MultiThreadProcessTaskUnit batchRunTask;
    @PostConstruct
//    @Async
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
//        threadPoolExecutor.execute(new RuleTaskThread("2016-01-26", "2016-01-27", batchRunTask));
        while(true){
            if(threadPoolExecutor.getActiveCount() == 0){
                MultiThreadProcessTaskUnit.fw.close();
                MultiThreadProcessTaskUnit.modelFw.close();
                Long end = System.currentTimeMillis();
                System.out.println(String.format("Multi thread is processed! It costs %s%s", batchRunTask.df.format(Double.valueOf(end - begin) / (1000 * 60)), "minutes"));
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
