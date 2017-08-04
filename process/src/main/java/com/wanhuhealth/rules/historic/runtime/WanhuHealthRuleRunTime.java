package com.wanhuhealth.rules.historic.runtime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.google.common.collect.Maps;
import com.wanhuhealth.rules.historic.fact.DiseaseReferFact;
import com.wanhuhealth.rules.historic.fact.DrugReferFact;
import com.wanhuhealth.rules.historic.fact.OrderFact;
import com.wanhuhealth.rules.historic.fact.OrderReferFact;
import com.wanhuhealth.rules.historic.globals.ResResult;
import com.wanhuhealth.rules.historic.runtime.handlers.RulesChain;
import com.wanhuhealth.rules.historic.runtime.mapper.RuleFilesMapper;
import com.wanhuhealth.rules.utils.KieSessionUtil;
import com.wanhuhealth.rules.utils.RulesFilter;
import com.wanhuhealth.rules.utils.RulesUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/5/31.
 */
@RestController
@RequestMapping(value = "/rule/validate/")
public class WanhuHealthRuleRunTime {
	private Logger logger = Logger.getLogger(WanhuHealthRuleRunTime.class);
	@Autowired
	RuleFilesMapper ruleFilesMapper;
	@RequestMapping(value = "/verifyOrder")
	@ResponseBody
	public ResResult verification(@RequestBody String orderInfoRuleModelJson){
		ResResult resResult = new ResResult();
		logger.info("规则引擎开始: 接收到的前端请求json数据 = " + orderInfoRuleModelJson);
		try{
			OrderFact orderFact = JSON.parseObject(orderInfoRuleModelJson, OrderFact.class);
			try {
				KieSession ks = KieServices.Factory.get().getKieClasspathContainer().newKieSession("kDrugCalculation");
				if(ks != null) {
					List<String> agenda = RulesUtils.effectiveRules(orderFact);
					ks.setGlobal("resResult", resResult);

					for (String ruleMark : agenda) {
						RulesChain.getRuleHandlerChain().handleRequest(ruleMark, orderFact, ks);
					}

					ks.fireAllRules(new RulesFilter(agenda));
				}
				if(orderFact.getRuleResultList().size() > 0){
					resResult.getRuleResultList().addAll(orderFact.getRuleResultList());
				}
				ks.dispose();
			}catch (Exception e){
				logger.error("规则引擎错误", e);
			}finally {
			}
			resResult.setRuleResultList(RulesUtils.addLevel(orderFact, resResult.getRuleResultList()));
			resResult.setCode(0);
		}catch (JSONException jsonException){
			resResult.setCode(1); // json解析错误
			resResult.setMessage("json解析错误");
		}
		logger.info("规则引擎结束。解析结果 = " + JSON.toJSONString(resResult));
		return resResult;
	}

	@RequestMapping(value = "/cacheForRules")
	@ResponseBody
	public Map<String, Object> updateCacheForRules(){
		RulesUtils.redisCacheRulesHash();
		RulesUtils.redisCacheRegionRule();
		KieSessionUtil.createKieBase();
		logger.info("接收到通知，重新创建KnowledgeBase!");
		Map<String, Object> rm = Maps.newHashMap();
		rm.put("code", 0);
		return rm;
	}

	/**
	 * 药品计算规则
	 * @return
	 */
	@RequestMapping(value = "/drugCalculation", method = RequestMethod.POST)
	@ResponseBody
	public ResResult validateDrugCalculation(HttpServletRequest request){
		ResResult resResult = new ResResult();
		resResult.setCode(200);
		String drugReferJson = request.getParameter("drugReferFact");
		String auth = request.getParameter("auth");
		if(StringUtils.isBlank(drugReferJson) ||StringUtils.isBlank(auth)){
			resResult.setCode(400);
			resResult.setMessage("参数有错误");
			return new ResResult();
		}else{
			if(!auth.equals("123456")){
				resResult.setCode(400);
				resResult.setMessage("请输入正确的鉴权码");
				return resResult;
			}
		}
		try {
			drugReferJson = new String(drugReferJson.getBytes(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		DrugReferFact drugReferFact = JSON.parseObject(drugReferJson, DrugReferFact.class);
		KieSession ks = KieSessionUtil.getKieSession("kDrugCalculation");

		ks.setGlobal("resResult", resResult);
		ks.insert(drugReferFact);
		ks.fireAllRules();
		System.out.println(resResult.getRuleResultList());
		return resResult;
	}

	/**
	 * 药品业务逻辑规则
	 * @return
	 */
	@RequestMapping(value = "/drugBusiness", method = RequestMethod.POST)
	@ResponseBody
	public ResResult validateDrugBusiness(HttpServletRequest request){
		ResResult resResult = new ResResult();
		DrugReferFact drugReferFact = JSON.parseObject(request.getParameter("drugReferFact"), DrugReferFact.class);
		KieSession kieSession = KieSessionUtil.getKieSession("kDrugBusiness");
		kieSession.setGlobal("resResult", resResult);
		kieSession.insert(drugReferFact);
		kieSession.fireAllRules();
		return resResult;
	}

	/**
	 * 订单业务逻辑规则
	 * @return
	 */
	@RequestMapping(value = "/orderBusiness", method = RequestMethod.POST)
	@ResponseBody
	public ResResult validateOrderBusiness(HttpServletRequest request){
		ResResult resResult = new ResResult();
		resResult.setCode(200);
		String orderReferJson = request.getParameter("orderReferFact");
		OrderReferFact orderReferFact = JSON.parseObject(orderReferJson, OrderReferFact.class);
		KieSession ks = KieSessionUtil.getKieSession("kOrderBusiness");
		ks.setGlobal("resResult", resResult);
		ks.insert(orderReferFact);
		ks.fireAllRules();
		return resResult;
	}

	/**
	 * 疾病业务逻辑规则
	 * @return
	 */
	@RequestMapping(value = "/diseaseBusiness", method = RequestMethod.POST)
	@ResponseBody
	public ResResult validateDiseaseBusiness(HttpServletRequest request){
		ResResult resResult = new ResResult();
		resResult.setCode(200);
		String diseaseReferJson = request.getParameter("diseaseReferFact");
		DiseaseReferFact diseaseReferFact = JSON.parseObject(diseaseReferJson, DiseaseReferFact.class);
		KieSession ks = KieSessionUtil.getKieSession("kDiseaseBusiness");
		ks.setGlobal("resResult", resResult);
		ks.insert(diseaseReferFact);
		ks.fireAllRules();
		return resResult;
	}

//	@PostConstruct
	public void sss(){
		System.out.println(ruleFilesMapper.findAll().size());
	}
}
