package com.wanhuhealth.rules.historic.fact;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by admin on 2017/5/31.
 */
public class DrugFact extends BaseFact{
	private Long drugId; // ID
	private Integer frequency;	// 频次
	private Integer packageSize;	//包装规格量
	private String dosageForm;	//剂型分类（口服、注射、外服、其他）---关联字典表(type=medi_dosage_class )
	private List<DiseaseFact> indications = Lists.newArrayList(); //  适应症
	private Integer recommendAmount;	//推荐用量
	private Double recommendDose;		//推荐剂量
	private Double recommendFrequency;	//推荐频率
	private Double maximumDoseInUnit;		//单次使用最大量(单位)
	private Double maximumDoseDailyInUnit;	//日使用最大量(单位)
	private Double maximumDose;		//单次最大剂量
	private String maximumDoseDaily;	//日最大剂量
	private String commonName;		// 通用名

	private String productName;	//产品名
	private String largeClass;	//大类（中药、西药）-关联字典表(type=medi_large_class )
	private String mediumClass;	//中类---关联字典表(type=medi_medium_class )
	private String smallClass;	//小类---关联字典表(type=medi_small_class )
	private String pharmacologyClass;	//药理学分类 ----关联字典表（type= pharmacology）
	private Double hisAmount; 	// 历史药品余量
	private Double dailyAmount; //日用量（需求用量）
	private String packageUnit; // 包装单位
	private String useDoseUnit; // 粒、ml
	private Double buyAmount; 	// 购买量
	private Double useAmount; 	// 单次用量
	private Double maxBuyAmountOfRequired; // 最大用药需求购买量
	private Double maxBuyAmountOfIntruduction; // 最大说明书购买量
	private Double useAmountDaily; // 需求日使用量

	public Long getDrugId() {
		return drugId;
	}

	public void setDrugId(Long drugId) {
		this.drugId = drugId;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(Integer packageSize) {
		this.packageSize = packageSize;
	}

	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	public List<DiseaseFact> getIndications() {
		return indications;
	}

	public void setIndications(List<DiseaseFact> indications) {
		this.indications = indications;
	}

	public Integer getRecommendAmount() {
		return recommendAmount;
	}

	public void setRecommendAmount(Integer recommendAmount) {
		this.recommendAmount = recommendAmount;
	}

	public Double getRecommendDose() {
		return recommendDose;
	}

	public void setRecommendDose(Double recommendDose) {
		this.recommendDose = recommendDose;
	}

	public Double getRecommendFrequency() {
		return recommendFrequency;
	}

	public void setRecommendFrequency(Double recommendFrequency) {
		this.recommendFrequency = recommendFrequency;
	}

	public Double getMaximumDoseInUnit() {
		return maximumDoseInUnit;
	}

	public void setMaximumDoseInUnit(Double maximumDoseInUnit) {
		this.maximumDoseInUnit = maximumDoseInUnit;
	}

	public Double getMaximumDoseDailyInUnit() {
		return maximumDoseDailyInUnit;
	}

	public void setMaximumDoseDailyInUnit(Double maximumDoseDailyInUnit) {
		this.maximumDoseDailyInUnit = maximumDoseDailyInUnit;
	}

	public Double getMaximumDose() {
		return maximumDose;
	}

	public void setMaximumDose(Double maximumDose) {
		this.maximumDose = maximumDose;
	}

	public String getMaximumDoseDaily() {
		return maximumDoseDaily;
	}

	public void setMaximumDoseDaily(String maximumDoseDaily) {
		this.maximumDoseDaily = maximumDoseDaily;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLargeClass() {
		return largeClass;
	}

	public void setLargeClass(String largeClass) {
		this.largeClass = largeClass;
	}

	public String getMediumClass() {
		return mediumClass;
	}

	public void setMediumClass(String mediumClass) {
		this.mediumClass = mediumClass;
	}

	public String getSmallClass() {
		return smallClass;
	}

	public void setSmallClass(String smallClass) {
		this.smallClass = smallClass;
	}

	public String getPharmacologyClass() {
		return pharmacologyClass;
	}

	public void setPharmacologyClass(String pharmacologyClass) {
		this.pharmacologyClass = pharmacologyClass;
	}

	public Double getHisAmount() {
		return hisAmount;
	}

	public void setHisAmount(Double hisAmount) {
		this.hisAmount = hisAmount;
	}

	public Double getDailyAmount() {
		return dailyAmount;
	}

	public void setDailyAmount(Double dailyAmount) {
		this.dailyAmount = dailyAmount;
	}

	public String getPackageUnit() {
		return packageUnit;
	}

	public void setPackageUnit(String packageUnit) {
		this.packageUnit = packageUnit;
	}

	public String getUseDoseUnit() {
		return useDoseUnit;
	}

	public void setUseDoseUnit(String useDoseUnit) {
		this.useDoseUnit = useDoseUnit;
	}

	public Double getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(Double buyAmount) {
		this.buyAmount = buyAmount;
	}

	public Double getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(Double useAmount) {
		this.useAmount = useAmount;
	}

	public Double getMaxBuyAmountOfRequired() {
		return maxBuyAmountOfRequired;
	}

	public void setMaxBuyAmountOfRequired(Double maxBuyAmountOfRequired) {
		this.maxBuyAmountOfRequired = maxBuyAmountOfRequired;
	}

	public Double getMaxBuyAmountOfIntruduction() {
		return maxBuyAmountOfIntruduction;
	}

	public void setMaxBuyAmountOfIntruduction(Double maxBuyAmountOfIntruduction) {
		this.maxBuyAmountOfIntruduction = maxBuyAmountOfIntruduction;
	}

	public Double getUseAmountDaily() {
		return useAmountDaily;
	}

	public void setUseAmountDaily(Double useAmountDaily) {
		this.useAmountDaily = useAmountDaily;
	}
}
