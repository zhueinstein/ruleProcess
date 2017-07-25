package com.Learning;

import com.wanhuhealth.HelloService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2017/6/9.
 */
@RestController
@RequestMapping(value = "/learning")
public class LearningController {
    private Logger logger = Logger.getLogger(LearningController.class);

    /**
     * 读取配置文件的属性
     */
    @Value(value = "${learning.friend}")
    private String author;
    @RequestMapping(value = "/prop")
    private String propertites(){
        System.out.println(author);
        return author;
    }

    /**
     * 属性和Bean类型安全绑定
     * @return
     */
    @Autowired
    ConfigurationBean configurationBean;
    @RequestMapping(value = "/configurationBean")
    private ConfigurationBean configurationBean(){
        logger.debug(configurationBean);
        return configurationBean;
    }

    @Autowired
    HelloService helloService;
    @RequestMapping(value = "/autoConfigurationStarter")
    public String testBootAutoConfigurationStarter(){
        return helloService.sayHello();
    }
}
