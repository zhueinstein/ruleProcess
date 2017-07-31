package com.wanhuhealth.rules.batch.drools;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2017/7/28.
 */
@Component
@ConfigurationProperties(prefix = "drools.HL.salience")
public class ComputeSalience {
     private  Integer  ageLimit;        //  65499  #年龄限制
     private  Integer  mergeDrug;        // 65399 #联合用药不合理-种类
     private  Integer  mergeSymptom;        // 65299 #合并症用药不合理
     private  Integer  westernMedicineMainIngredientDuplicate;        // 65199 #西药主要成分重复
     private  Integer  westernMedicineDuplicate;        // 65099 #西药药理作用重复
     private  Integer  westernMedicinePharmacologic;        // 64999 #西药药理作用机制冲突
     private  Integer  ChinesePatentMedicineDuplicate;        // 64899 #中成药功效类别重复
     private  Integer  ChinesePatentMedicineIngredientDuplicate;        // 64799 #中成药主要成分相同
     private  Integer  ChinesePatentMedicineToxicity;        // 64699 #中成药毒副作用累加
     private  Integer  westernMedicineInteraction;        // 64599 #西药相互作用
     private  Integer  CompoundChineseWesternAndWesternMedicine;        // 64499 #中西复方制剂与西药制剂成分重复
     private  Integer  ChinesePatentWesternDuplicate;        // 64399 #中成药与西药成分重复

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public Integer getMergeDrug() {
        return mergeDrug;
    }

    public void setMergeDrug(Integer mergeDrug) {
        this.mergeDrug = mergeDrug;
    }

    public Integer getMergeSymptom() {
        return mergeSymptom;
    }

    public void setMergeSymptom(Integer mergeSymptom) {
        this.mergeSymptom = mergeSymptom;
    }

    public Integer getWesternMedicineMainIngredientDuplicate() {
        return westernMedicineMainIngredientDuplicate;
    }

    public void setWesternMedicineMainIngredientDuplicate(Integer westernMedicineMainIngredientDuplicate) {
        this.westernMedicineMainIngredientDuplicate = westernMedicineMainIngredientDuplicate;
    }

    public Integer getWesternMedicineDuplicate() {
        return westernMedicineDuplicate;
    }

    public void setWesternMedicineDuplicate(Integer westernMedicineDuplicate) {
        this.westernMedicineDuplicate = westernMedicineDuplicate;
    }

    public Integer getWesternMedicinePharmacologic() {
        return westernMedicinePharmacologic;
    }

    public void setWesternMedicinePharmacologic(Integer westernMedicinePharmacologic) {
        this.westernMedicinePharmacologic = westernMedicinePharmacologic;
    }

    public Integer getChinesePatentMedicineDuplicate() {
        return ChinesePatentMedicineDuplicate;
    }

    public void setChinesePatentMedicineDuplicate(Integer chinesePatentMedicineDuplicate) {
        ChinesePatentMedicineDuplicate = chinesePatentMedicineDuplicate;
    }

    public Integer getChinesePatentMedicineIngredientDuplicate() {
        return ChinesePatentMedicineIngredientDuplicate;
    }

    public void setChinesePatentMedicineIngredientDuplicate(Integer chinesePatentMedicineIngredientDuplicate) {
        ChinesePatentMedicineIngredientDuplicate = chinesePatentMedicineIngredientDuplicate;
    }

    public Integer getChinesePatentMedicineToxicity() {
        return ChinesePatentMedicineToxicity;
    }

    public void setChinesePatentMedicineToxicity(Integer chinesePatentMedicineToxicity) {
        ChinesePatentMedicineToxicity = chinesePatentMedicineToxicity;
    }

    public Integer getWesternMedicineInteraction() {
        return westernMedicineInteraction;
    }

    public void setWesternMedicineInteraction(Integer westernMedicineInteraction) {
        this.westernMedicineInteraction = westernMedicineInteraction;
    }

    public Integer getCompoundChineseWesternAndWesternMedicine() {
        return CompoundChineseWesternAndWesternMedicine;
    }

    public void setCompoundChineseWesternAndWesternMedicine(Integer compoundChineseWesternAndWesternMedicine) {
        CompoundChineseWesternAndWesternMedicine = compoundChineseWesternAndWesternMedicine;
    }

    public Integer getChinesePatentWesternDuplicate() {
        return ChinesePatentWesternDuplicate;
    }

    public void setChinesePatentWesternDuplicate(Integer chinesePatentWesternDuplicate) {
        ChinesePatentWesternDuplicate = chinesePatentWesternDuplicate;
    }
}
