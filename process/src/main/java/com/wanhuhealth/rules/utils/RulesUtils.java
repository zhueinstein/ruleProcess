package com.wanhuhealth.rules.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.wanhuhealth.rules.historic.fact.OrderFact;
import com.wanhuhealth.rules.historic.globals.RuleResult;
import com.wanhuhealth.rules.historic.model.CfgOrderDrugRules;
import com.wanhuhealth.rules.historic.model.CfgRegionRuleSystemCategory;
import com.wanhuhealth.rules.historic.model.CfgRegionSystemCategory;
import com.wanhuhealth.rules.redis.service.RedisServiceImpl;
import com.wanhuhealth.rules.historic.runtime.service.WanhuHealthRuleService;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by admin on 2017/3/2.
 */

public class RulesUtils {


	private static WanhuHealthRuleService ruleFilesMapper = SpringContextHolder.getBean(WanhuHealthRuleService.class);
	static RedisServiceImpl redis = SpringContextHolder.getBean(RedisServiceImpl.class);
	public static final String CACHE_RULES_HASHTABLE = "rulesHash";
	public static final String CACHE_REGION_RULES_LIST = "regionRulesHash";
	public static final String CACHE_REGION_LEVEL_LIST = "regionLevelHash";
	static {

		redisCacheRulesHash();
		redisCacheRegionRule();
	}

	/**
	 * HashTable
	 * key provinceId&cityId&area&ruleakM&system&innerSystem
	 * Value List<CfgRegionSystemCategory>
	 * 存放省市区对应的系统类别
	 */
	public static void redisCacheRegionRule(){
		Hashtable<String, String> regionLevelHash = new Hashtable<String, String>();
		List<CfgOrderDrugRules> rules = ruleFilesMapper.findAll();
		for (CfgOrderDrugRules rule : rules) {
			if(rule.getOnOff() == 1) {
				for (CfgRegionRuleSystemCategory regionRule : rule.getHasRulesRegionCategory()) {
					if(regionRule.getOnOff() == 1) {
						String provinceId = "";
						if (regionRule.getProvinceId() != null) {
							provinceId = regionRule.getProvinceId();
						}
						String cityId = "";
						if (regionRule.getCityId() != null) {
							cityId = regionRule.getCityId();
						}

						String areaId = "";
						if (regionRule.getAreaId() != null) {
							areaId = regionRule.getAreaId();
						}
						String key = "";
						if (StringUtils.isNotBlank(areaId)) {
							key += String.format("%s&%s&%s&%s", provinceId, cityId, areaId, rule.getRuleMark());
						}
						if (StringUtils.isNotBlank(cityId) && StringUtils.isBlank(areaId)) {
							key += String.format("%s&%s&%s", provinceId, cityId, rule.getRuleMark());
						}
						if (StringUtils.isNotBlank(provinceId) && StringUtils.isBlank(cityId) && StringUtils.isBlank(areaId)) {
							key += String.format("%s&%s", provinceId, rule.getRuleMark());
						}
						if (StringUtils.isBlank(provinceId)) {
							key = rule.getRuleMark();
						}

						for (CfgRegionSystemCategory regionSystemCategory : regionRule.getHasRegionSystemCategories()) {
							regionLevelHash.put(String.format("%s&%s&%s", key, regionSystemCategory.getSysCategory(), regionSystemCategory.getInnerCategory()), regionSystemCategory.getLevel()+"&" + rule.getId());
						}
					}
				}
			}
		}
		redis.remove(CACHE_REGION_LEVEL_LIST);
		redis.set(CACHE_REGION_LEVEL_LIST, JSON.toJSONString(regionLevelHash));
	}
	/**
	 * HashTable
	 * key provinceId&cityId&area&ruleMak
	 * Value ruleMark
	 * 存放省市区对应的ruleMark
	 */
	public  static void redisCacheRulesHash() {
		List<CfgOrderDrugRules> rules = ruleFilesMapper.findAll();
		Hashtable<String, String> regionRulesHash = new Hashtable<String, String>();
		for (CfgOrderDrugRules rule : rules) {
			if(rule.getOnOff() == 1) {
				for (CfgRegionRuleSystemCategory regionRule : rule.getHasRulesRegionCategory()) {
					if(regionRule.getOnOff() == 1) {
						String provinceId = "";
						if (regionRule.getProvinceId() != null) {
							provinceId = regionRule.getProvinceId();
						}
						String cityId = "";
						if (regionRule.getCityId() != null) {
							cityId = regionRule.getCityId();
						}

						String areaId = "";
						if (regionRule.getAreaId() != null) {
							areaId = regionRule.getAreaId();
						}
						String key = "";
						if (StringUtils.isNotBlank(areaId)) {
							key += String.format("%s&%s&%s&%s", provinceId, cityId, areaId, rule.getRuleMark());
						}
						if (StringUtils.isNotBlank(cityId) && StringUtils.isBlank(areaId)) {
							key += String.format("%s&%s&%s", provinceId, cityId, rule.getRuleMark());
						}
						if (StringUtils.isNotBlank(provinceId) && StringUtils.isBlank(cityId) && StringUtils.isBlank(areaId)) {
							key += String.format("%s&%s", provinceId, rule.getRuleMark());
						}
						if (StringUtils.isBlank(provinceId)) {
							key = rule.getRuleMark();
						}

						regionRulesHash.put(key, rule.getRuleMark());
					}
				}
			}
		}

		/**
		 * HashTable
		 * key  ruleMark
		 * value rules 规则文件
		 */
		Hashtable<String, String> rulesHash = new Hashtable<String, String>();
		for (CfgOrderDrugRules rule : rules) {
			if(rule.getOnOff() == 1) {
				rulesHash.put(rule.getRuleMark(), rule.getRule());
			}
		}
		CfgOrderDrugRules logRule = ruleFilesMapper.getByRuleMark("ruleLog");
		rulesHash.put(logRule.getRuleMark(), logRule.getRule());
		redis.remove(CACHE_REGION_RULES_LIST);
		redis.remove(CACHE_RULES_HASHTABLE);
		redis.set(CACHE_REGION_RULES_LIST, JSON.toJSONString(regionRulesHash));
		redis.set(CACHE_RULES_HASHTABLE, JSON.toJSONString(rulesHash));
	}

	/**
	 * 此处必须取规则的ruleMark,用作和规则文件进行比对，作为规则议程中有效的规则
	 * @return
	 * @param orderInfoRuleModel
	 */
	public static List<String> effectiveRules(OrderFact orderInfoRuleModel) {
		List<String> effectiveRules = Lists.newArrayList();
		String provinceId = orderInfoRuleModel.getProvinceId();
		String cityId = orderInfoRuleModel.getCityId();
		String areaId = orderInfoRuleModel.getAreaId();

		String key3 = String.format("%s&%s&%s", provinceId, cityId, areaId);

		String key2 = String.format("%s&%s", provinceId, cityId);
		String key1 = String.format("%s", provinceId);

		Hashtable<String, String> regionRulesHash = JSON.parseObject(redis.get(CACHE_REGION_RULES_LIST), Hashtable.class);
		Hashtable<String, String> rulesHash = JSON.parseObject(redis.get(CACHE_RULES_HASHTABLE), Hashtable.class);
		// 遍历hashkey，从hash表中获取有效的规则ruleMark
		for (String ruleMark : rulesHash.keySet()) {
			String keys3 = String.format("%s&%s", key3, ruleMark);
			String keys2 = String.format("%s&%s", key2, ruleMark);
			String keys1 = String.format("%s&%s", key1, ruleMark);
			String keys0 = String.format("%s", ruleMark);

			if(regionRulesHash.get(keys3) != null || regionRulesHash.get(keys2) != null || regionRulesHash.get(keys1) != null || regionRulesHash.get(keys0) != null){
				effectiveRules.add(ruleMark);
			}
		}
		effectiveRules.add("ruleLog");
		return effectiveRules;
	}

	/**
	 * 添加提示的等级
	 * @param rules
	 * @param orderInfoRuleModel
	 */
	public static List<RuleResult> addLevel(OrderFact orderInfoRuleModel, List<RuleResult> rules) {

		Integer sysCategory = orderInfoRuleModel.getSysCategory();
		Integer innerSysCategory = orderInfoRuleModel.getInnerSysCategory();
		Hashtable<String, String> levelHash = JSON.parseObject(redis.get(CACHE_REGION_LEVEL_LIST), Hashtable.class);
		String provinceId = orderInfoRuleModel.getProvinceId();
		String cityId = orderInfoRuleModel.getCityId();
		String areaId = orderInfoRuleModel.getAreaId();
		for (RuleResult ruleResult : rules) {
			ruleResult.setSysCategory(sysCategory);
			String firstLevel = levelHash.get(String.format("%s&%s&%s&%s", String.format("%s&%s&%s", provinceId, cityId, areaId), ruleResult.getRuleMark(), sysCategory, innerSysCategory));
			String secondLevel = levelHash.get(String.format("%s&%s&%s&%s", String.format("%s&%s", provinceId, cityId), ruleResult.getRuleMark(), sysCategory, innerSysCategory));
			String thirdLevel = levelHash.get(String.format("%s&%s&%s&%s", String.format("%s", provinceId), ruleResult.getRuleMark(), sysCategory, innerSysCategory));
			String defaultLevel = levelHash.get(String.format("%s&%s&%s", ruleResult.getRuleMark(), sysCategory, innerSysCategory));
			if(firstLevel != null){
				List<String> list = Arrays.asList(firstLevel.split("&"));
				ruleResult.setLevel(list.get(0));
				ruleResult.getObjectMap().put("ruleId", list.get(1));
			}else if(secondLevel != null){
				List<String> list = Arrays.asList(secondLevel.split("&"));
				ruleResult.setLevel(list.get(0));
				ruleResult.getObjectMap().put("ruleId", list.get(1));
			}else if(thirdLevel != null){
				List<String> list = Arrays.asList(thirdLevel.split("&"));
				ruleResult.setLevel(list.get(0));
				ruleResult.getObjectMap().put("ruleId", list.get(1));
			}else if(defaultLevel != null){
				List<String> list = Arrays.asList(defaultLevel.split("&"));
				ruleResult.setLevel(list.get(0));
				ruleResult.getObjectMap().put("ruleId", list.get(1));
			}
		}
		return rules;
	}

}
