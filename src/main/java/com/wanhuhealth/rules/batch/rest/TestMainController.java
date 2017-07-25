package com.wanhuhealth.rules.batch.rest;

import com.google.common.collect.Maps;
import com.wanhuhealth.rules.batch.mapper.DiseaseInfoMapper;
import com.wanhuhealth.rules.batch.mapper.DrugInformationMapper;
import com.wanhuhealth.rules.batch.mapper.OrderInfoMapper;
import com.wanhuhealth.rules.batch.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/21.
 */
@RestController
public class TestMainController {
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    DiseaseInfoMapper diseaseInfoMapper;
    @Autowired
    DrugInformationMapper drugInformationMapper;

//    @PostConstruct
    public void test(){
        Map<String, Object> param = Maps.newHashMap();
        param.put("start", "2016-01-26");
        param.put("end", "2016-01-27");
        List<OrderInfo>  orderInfoList = orderInfoMapper.findBetween2Date("2016-01-16", "2016-01-27");
        System.out.println      (orderInfoList.size());
//        System.out.println(diseaseInfoMapper.findAll().size());
//        System.out.println(drugInformationMapper.findAll().size());
    }
}
