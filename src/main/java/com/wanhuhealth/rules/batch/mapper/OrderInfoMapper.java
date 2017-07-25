package com.wanhuhealth.rules.batch.mapper;

import com.wanhuhealth.rules.batch.model.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/21.
 */
@Mapper
public interface OrderInfoMapper {
    List<OrderInfo> findBetween2Date(@Param("start") String start, @Param("end") String end);
}
