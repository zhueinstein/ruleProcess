package com.Learning;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2017/6/9.
 */
@Component
@ConfigurationProperties(prefix = "learning.configurationBean")
public class ConfigurationBean {
    private Integer port;
    private String serverName;
    private String topic;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
