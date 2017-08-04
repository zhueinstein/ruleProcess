package com.wanhuhealth.rules.batch.excel;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wanhuhealth.rules.batch.drools.RulesConstant;
import com.wanhuhealth.rules.utils.CountClick;
import com.wanhuhealth.rules.utils.FrequencyEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by admin on 2017/7/18.
 *
 */
public class Json2ExcelMain {
    private static Hashtable<String, BigDecimal> ht = new Hashtable<String, BigDecimal>();
    private static HashSet<String> hs = Sets.newHashSet();
    public static void main(String[] args) {
        Json2ExcelMain.export(Json2ExcelMain.readList());
        Json2ExcelMain.exportEveryRulePercent();
        Json2ExcelMain.exportTotalPercent();
    }

   static String loadFileName = String.format("%sDroolsResult_", RulesConstant.data_direction);
    static {
        File file = new File(RulesConstant.data_direction);
        if (!file.exists()){
            file.mkdir();
        }
        if(file.isDirectory()){
            Long temp;
            Long largest = 0L;
            for (String s : file.list()) {
                if(s.contains("DroolsResult")){
                    s  = s.substring(13);
                    s = s.substring(0, s.length() - 4);
                    temp = Long.valueOf(s);
                    if(temp > largest){
                        largest = temp;
                    }
                }
            }
            loadFileName += largest + ".txt";
        }else{
            System.out.println("文件");
        }

    }
    public static List<OutFormatMessage> readList(){
        List<OutFormatMessage> list = Lists.newArrayList();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            String content;
            System.out.println(loadFileName);
            fis = new FileInputStream(loadFileName);// FileInputStream
            // 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            while ((content = br.readLine()) != null) {
                if(StringUtils.isBlank(content)){
                    continue;
                }
                if(content.split("}\\{").length > 1){
                    for (String s : content.split("}\\{")) {
                        if(!s.startsWith("{")){
                            s = "{" + s;
                        }
                        if(!s.endsWith("}")){
                            s = s + "}";
                        }
                        if (!JSON.parseObject(s, OutFormatMessage.class).getPatientName().startsWith("X2")) {
                            list.add(JSON.parseObject(s, OutFormatMessage.class));
                        }
                    }
                }else {
                    if (!JSON.parseObject(content, OutFormatMessage.class).getPatientName().startsWith("X2")) {
                        list.add(JSON.parseObject(content, OutFormatMessage.class));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
                if(isr != null) {
                    isr.close();
                }
                if(fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(list, new Comparator<OutFormatMessage>() {
            @Override
            public int compare(OutFormatMessage o1, OutFormatMessage o2) {
                return o1.getOrderNO().compareTo(o2.getOrderNO());
            }
        });
        // 筛选违规的订单编号

        for (OutFormatMessage outFormatMessage : list) {
            if(!outFormatMessage.getLegal()) {
                hs.add(outFormatMessage.getOrderNO());
            }
        }

        handleSummaryPrice(list);

        HashSet<String> in = Sets.newHashSet();
        List<OutFormatMessage> illegalList = Lists.newArrayList();
        for (OutFormatMessage outFormatMessage : list) {
                if(hs.contains(outFormatMessage.getOrderNO())){
                    if(in.add(outFormatMessage.getOrderNO())) {
                        outFormatMessage.setShow(true);
                    }
                    illegalList.add(outFormatMessage);
                }
        }
        return illegalList;
    }

     private static void handleSummaryPrice(List<OutFormatMessage> list){
        BigDecimal decimal;
        String key;
        BigDecimal price;
        System.out.println(hs.size());
         for (OutFormatMessage outFormatMessage : list) {
            if(!hs.contains(outFormatMessage.getOrderNO())){
                continue;
            }
            price = new BigDecimal(outFormatMessage.getPrice());

            // 个人每个订单合并后的价格
            key = outFormatMessage.getOrderNO();
            decimal = price.multiply(new BigDecimal(outFormatMessage.getNumber()));
            decimal.setScale(2, RoundingMode.HALF_UP);
            if(ht.keySet().contains(key)){
               ht.put(key, ht.get(key).add(decimal));
            }else{
               ht.put(key, decimal);
            }

            // 个人每天订单审核后的价格
            key = outFormatMessage.getOrderNO() + "A";
            decimal = price.multiply(new BigDecimal(outFormatMessage.getAuditNumber()));
            decimal.setScale(2, RoundingMode.HALF_UP);
            if(ht.keySet().contains(key)){
                ht.put(key, ht.get(key).add(decimal));
            }else{
                ht.put(key, decimal);
            }

            // 通用订单节余
            if(!Collections.disjoint(Arrays.asList(outFormatMessage.getRuleViolates().split(",")), RulesConstant.common)){
                key = "common";
                decimal = price.multiply(new BigDecimal(outFormatMessage.getNumber()).subtract(new BigDecimal(outFormatMessage.getAuditNumber())));
                decimal.setScale(2, RoundingMode.HALF_UP);
                if(ht.keySet().contains(key)){
                    ht.put(key, ht.get(key).add(decimal));
                }else{
                    ht.put(key, decimal);
                }
            }

            // 药学部规则节余
//            if(!Collections.disjoint(Arrays.asList(outFormatMessage.getRuleViolates().split(",")), RulesConstant.medicineNames)){
             if (RulesConstant.medicineNames.contains(outFormatMessage.getRuleViolates())) {
                 key = "medicine";
                decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal(outFormatMessage.getNumber()).subtract(new BigDecimal(outFormatMessage.getAuditNumber())));
                decimal.setScale(2, RoundingMode.HALF_UP);
                if(ht.keySet().contains(key)){
                    ht.put(key, ht.get(key).add(decimal));
                }else{
                    ht.put(key, decimal);
                }
            }

            // 每一种规则节约
             if(StringUtils.isNotBlank(outFormatMessage.getRuleViolates())) {
                 key = outFormatMessage.getRuleViolates() + "EV";
                 decimal = price.multiply(new BigDecimal(outFormatMessage.getNumber()).subtract(new BigDecimal(outFormatMessage.getAuditNumber())));
                 decimal.setScale(2, RoundingMode.HALF_UP);
                 if (ht.keySet().contains(key)) {
                     ht.put(key, ht.get(key).add(decimal));
                 } else {
                     if (outFormatMessage.getComplexSave() != null && outFormatMessage.getComplexSave().size() > 0) {
                         for (String ruleMark : outFormatMessage.getRuleViolates().split(",")) {
                             decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal(outFormatMessage.getComplexSave().get(ruleMark)));
                             decimal.setScale(2, RoundingMode.HALF_UP);
                             ht.put(ruleMark + "EV", decimal);
                         }
                     } else {
                         ht.put(key, decimal);
                     }

                 }
             }
            // 所有规则总节约
            key = "kfTotal";
            decimal = price.multiply(new BigDecimal(outFormatMessage.getNumber()).subtract(new BigDecimal(outFormatMessage.getAuditNumber())));
            decimal.setScale(2, RoundingMode.HALF_UP);
            if(ht.keySet().contains(key)){
                ht.put(key, ht.get(key).add(decimal));
            }else{
                ht.put(key, decimal);
            }
        }

    }
    public static void export(List<OutFormatMessage> list){
        final CountClick click = new CountClick();
        click.setTotal(list.size() * 23.0);

        ExportExcel excel = DefaultExportExcel.newInstance(new ExportExcel.CellBuilder() {
            @Override
            public Cell builder(Row row, int index, Object data) {
                if (index > 23) {
                    return null;
                }
                click.setCount(click.getCount() + 1);
                click.processing(click);
                Cell cell = row.createCell(index);
                BigDecimal decimal;
                OutFormatMessage vo = (OutFormatMessage) data;
                switch (index) {
                    case 0:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getOrderNO().split(",")[0]);
                        break;
                    case 1:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Integer.valueOf(vo.getCount()));
                        break;
                    case 2:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getOrderDate());
                        break;
                    case 3:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getHospital());
                        break;
                    case 4:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getPatientName());
                        break;
                    case 5:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getWanhuCard());
                        break;
                    case 6:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getPatientIdCard());
                        break;
                    case 7:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue("男");
                        break;
                    case 8:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getAge());
                        break;
                    case 9:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getDiseases());
                        break;
                    case 10:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getCycle());
                        break;
                    case 11:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getDrugName());
                        break;
                    case 12:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getPackageSpecification());
                        break;
                    case 13:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        decimal = new BigDecimal(vo.getUseAmount());
                        decimal.setScale(2, RoundingMode.HALF_UP);
                        cell.setCellValue(decimal.doubleValue());
                        break;
                    case 14:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(FrequencyEnum.getName(vo.getFrequency()));
                        break;
                    case 15:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        decimal = new BigDecimal(vo.getPrice());
                        decimal.setScale(2, RoundingMode.HALF_UP);
                        cell.setCellValue(decimal.doubleValue());
                        break;
                    case 16:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        decimal = new BigDecimal(vo.getNumber());
                        decimal.setScale(0, RoundingMode.HALF_UP);
                        cell.setCellValue(decimal.doubleValue());
                        break;
                    case 17:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        decimal = new BigDecimal(vo.getTotal());
                        decimal.setScale(2, RoundingMode.HALF_UP);
                        cell.setCellValue(decimal.doubleValue());
                        break;
                    case 18:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        if(vo.getShow()) {
                            cell.setCellValue(ht.get(vo.getOrderNO()).doubleValue());
                        }
                        break;
                    case 19:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getRuleViolates());
                        break;
                    case 20:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        decimal = new BigDecimal(vo.getAuditNumber());
                        decimal.setScale(2, RoundingMode.HALF_UP);
                        cell.setCellValue(decimal.doubleValue());
                        break;
                    case 21:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        if(vo.getShow()) {
                            decimal = new BigDecimal(vo.getAuditTotal());
                            decimal.setScale(2, RoundingMode.HALF_UP);
                            cell.setCellValue(decimal.doubleValue());
                        }
                        break;
                    case 22:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        if(vo.getShow()) {
                            cell.setCellValue(ht.get(vo.getOrderNO() + "A").doubleValue());
                        }
                        break;
                }
                return cell;
            }
        });

        excel.setMaxCell(23);
        excel.setExportTitles(Arrays.asList("订单号","合并前订单数", "日期","社区医院", "患者姓名", "万户卡号","身份证号", "性别", "年龄", "疾病", "用药周期", "药品名称",
                "规格","单次用量","频次","单价", "数量", "药品总价","处方总价", "违规规则代号", "审核后数量","审核后总价","审核后处方总价"));
        File file = new File(RulesConstant.outFileName);
        try {
            excel.write(list, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void exportEveryRulePercent() {
        Hashtable<String, Double> inner = new Hashtable<String, Double>();
        for (String key : ht.keySet()) {
            if (key.contains("EV")) {
                inner.put(key.replaceAll("EV", ""), ht.get(key).doubleValue());
            }
        }
        List<Map.Entry<String, Double>> entryList = Lists.newArrayList(inner.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });
        for (Map.Entry<String, Double> stringDoubleEntry : entryList) {
            System.out.println(stringDoubleEntry.getKey() + " == " + stringDoubleEntry.getValue());
        }
        ExportExcel excel = DefaultExportExcel.newInstance(new ExportExcel.CellBuilder() {
            @Override
            public Cell builder(Row row, int index, Object data) {
                if (index > 3) {
                    return null;
                }

                Cell cell = row.createCell(index);
                Map.Entry<String, Double> vo = (Map.Entry<String, Double>) data;
                switch (index) {
                    case 0:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getKey());
                        break;
                    case 1:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(vo.getValue());
                        break;
                    case 2:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(new BigDecimal(vo.getValue()).multiply(new BigDecimal(100)).divide(ht.get("kfTotal"), 2, RoundingMode.HALF_UP).doubleValue() + "%");
                        break;
                }
                return cell;
            }
        });

        excel.setMaxCell(3);
        excel.setExportTitles(Arrays.asList("规则名称", "扣费金额", "单个规则扣费Percent"));
        File file = new File(RulesConstant.outRulePercentFileName);
        try {
            excel.write(entryList, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void exportTotalPercent(){
        List<String> list = Lists.newArrayList("导出Percent");

        ExportExcel excel = DefaultExportExcel.newInstance(new ExportExcel.CellBuilder() {
            @Override
            public Cell builder(Row row, int index, Object data) {
                if (index > 5) {
                    return null;
                }

                Cell cell = row.createCell(index);
                switch (index) {
                    case 0:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                            cell.setCellValue(ht.get("kfTotal").doubleValue());
                        break;
                    case 1:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                            if(ht.get("medicine") != null) {
                                cell.setCellValue(ht.get("medicine").doubleValue());
                            }
                        break;
                    case 2:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                            if(ht.get("medicine") != null) {
                                System.out.println(ht.get("medicine"));
                                cell.setCellValue(ht.get("medicine").divide(ht.get("kfTotal"),2,RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).doubleValue() + "%");
                            }
                        break;
                    case 3:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                            if(ht.get("common") != null) {
                                cell.setCellValue(ht.get("common").doubleValue());
                            }
                        break;
                    case 4:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                            if(ht.get("common") != null) {
                                System.out.println(ht.get("common"));
                                cell.setCellValue(ht.get("common").divide(ht.get("kfTotal"),2,RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).doubleValue() + "%");
                            }
                        break;
                }
                return cell;
            }
        });

        excel.setMaxCell(5);
        excel.setExportTitles(Arrays.asList("所有规则总扣费金额","新规则扣费金额","新规则扣费Percent","通用规则扣费金额","通用规则扣费Percent"));
        File file = new File(RulesConstant.outTotalPercentFileName);
        try {
            excel.write(list, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
