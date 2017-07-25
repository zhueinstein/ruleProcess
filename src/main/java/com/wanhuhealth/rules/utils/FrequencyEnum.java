package com.wanhuhealth.rules.utils;

/**
 *
 * Created by admin on 2017/7/20.
 *
     *  if(useAmount == null || frequency == null){
     return 0.0;
     }
     if ("1".equals(frequency) || "5".equals(frequency)) {//每日或每晚
     return useAmount * 1.00;
     } else if ("2".equals(frequency)) {//每日两次
     return useAmount * 2.00;
     } else if ("3".equals(frequency)) {//每日三次
     return useAmount * 3.00;
     } else if ("4".equals(frequency)) {//每日四次
     return useAmount * 4.00;
     }else if("6".equals(frequency)){
     return  (useAmount / 7) * 1.00;
     }
     else
     return 0.0;
     }
 */
public enum  FrequencyEnum {
    EVERY_DAY_ONCE("每日一次", "1"),
    EVERY_DAY_TWICE("每日两次","2"),
    EVERY_DAY_THIRD("每日三次","3"),
    EVERY_DAY_FOURTH("每日四次","4"),
    EVERY_NIGHT_ONCE("每夜一次","5"),
    EVERY_WEEK_ONCE("每周一次","6");

    // 成员变量
    private String name;
    private String frequency;

    // 构造方法
    private FrequencyEnum(String name, String frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    // 普通方法
    public static String getName(String frequency) {
        for (FrequencyEnum c : FrequencyEnum.values()) {
            if (c.getFrequency().equals(frequency)) {
                return c.name;
            }
        }
        return null;
    }

    public static FrequencyEnum get(String frequency) {
        for (FrequencyEnum c : FrequencyEnum.values()) {
            if (c.getFrequency() == frequency) {
                return c;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFrequency() {
        return frequency;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
