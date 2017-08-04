package com.wanhuhealth.rules.batch.mapper;

import com.wanhuhealth.rules.batch.model.DiseaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/7/21.
 */
@Mapper
public interface DiseaseInfoMapper {

    List<DiseaseInfo> diseaseInfoList(@Param("orderId") String orderId);

    List<DiseaseInfo> findAll();
}
