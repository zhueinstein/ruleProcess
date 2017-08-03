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
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by admin on 2017/7/18.
 *
 */
public class Json2ExcelMain {
    private static DecimalFormat df   = new DecimalFormat("######0.00");
    private static Hashtable<String, BigDecimal> ht = new Hashtable<String, BigDecimal>();

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
        sumPriceEveyOrder(list);
        HashSet<String> hs = Sets.newHashSet();
        String orderNo = "";
        for (OutFormatMessage outFormatMessage : list) {
            if(StringUtils.isBlank(orderNo) || !outFormatMessage.getOrderNO().equals(orderNo)){
                outFormatMessage.setOrderTotal(ht.get(outFormatMessage.getOrderNO()).toString());
                outFormatMessage.setOrderAuditTotal((ht.get(outFormatMessage.getOrderNO() + "A")).toString());

                if(new BigDecimal(outFormatMessage.getOrderAuditTotal()).setScale(2, RoundingMode.HALF_UP).equals(new BigDecimal(outFormatMessage.getOrderTotal()).setScale(2, RoundingMode.HALF_UP))){
                    hs.add(outFormatMessage.getOrderNO());
                }

            }
            orderNo = outFormatMessage.getOrderNO();
        }

        List<OutFormatMessage> list1 = Lists.newArrayList();
        for (OutFormatMessage outFormatMessage : list) {
                if(hs.add(outFormatMessage.getOrderNO())){
                    hs.remove(outFormatMessage.getOrderNO());
                    list1.add(outFormatMessage);
                }
        }
        return list1;
    }

     private static void sumPriceEveyOrder(List<OutFormatMessage> list){
        BigDecimal decimal;
        for (OutFormatMessage outFormatMessage : list) {
            if(ht.keySet().contains(outFormatMessage.getOrderNO())){
                decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal(outFormatMessage.getNumber()));
                decimal.setScale(2, RoundingMode.HALF_UP);
               ht.put(outFormatMessage.getOrderNO(), ht.get(outFormatMessage.getOrderNO()).add(decimal));
            }else{
                decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal(outFormatMessage.getNumber()));
                decimal.setScale(2, RoundingMode.HALF_UP);
               ht.put(outFormatMessage.getOrderNO(), decimal);
            }
            if(ht.keySet().contains(outFormatMessage.getOrderNO() + "A")){
                decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal(outFormatMessage.getAuditNumber()));
                decimal.setScale(2, RoundingMode.HALF_UP);
                ht.put(outFormatMessage.getOrderNO() + "A", ht.get(outFormatMessage.getOrderNO() + "A").add(decimal));
            }else{
                decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal(outFormatMessage.getAuditNumber()));
                decimal.setScale(2, RoundingMode.HALF_UP);
                ht.put(outFormatMessage.getOrderNO() + "A", decimal);
            }

            if (StringUtils.isNotBlank(outFormatMessage.getRuleViolates())) {

                if(!Collections.disjoint(Arrays.asList(outFormatMessage.getRuleViolates().split(",")), RulesConstant.common)){
                    if(ht.keySet().contains("common")){
                        decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal((Double.valueOf(outFormatMessage.getNumber()) - Double.valueOf(outFormatMessage.getAuditNumber()))));
                        decimal.setScale(2, RoundingMode.HALF_UP);
                        ht.put("common", ht.get("common").add(decimal));
                    }else{
                        decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal((Double.valueOf(outFormatMessage.getNumber()) - Double.valueOf(outFormatMessage.getAuditNumber()))));
                        decimal.setScale(2, RoundingMode.HALF_UP);
                        ht.put("common", decimal);
                    }
                }
                if(!Collections.disjoint(Arrays.asList(outFormatMessage.getRuleViolates().split(",")), RulesConstant.medicine)){
                    if(ht.keySet().contains("medicine")){
                        decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal((Double.valueOf(outFormatMessage.getNumber()) - Double.valueOf(outFormatMessage.getAuditNumber()))));
                        decimal.setScale(2, RoundingMode.HALF_UP);
                        ht.put("medicine", ht.get("medicine").add(decimal));
                    }else{
                        decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal((Double.valueOf(outFormatMessage.getNumber()) - Double.valueOf(outFormatMessage.getAuditNumber()))));
                        decimal.setScale(2, RoundingMode.HALF_UP);
                        ht.put("medicine", decimal);
                    }
                }

                if (ht.keySet().contains(outFormatMessage.getRuleViolates() + "EV")) {
                    decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal((Double.valueOf(outFormatMessage.getNumber()) - Double.valueOf(outFormatMessage.getAuditNumber()))));
                    decimal.setScale(2, RoundingMode.HALF_UP);
                    ht.put(outFormatMessage.getRuleViolates() + "EV", ht.get(outFormatMessage.getRuleViolates() + "EV").add(decimal) );
                } else {
                    if(outFormatMessage.getComplexSave() != null && outFormatMessage.getComplexSave().size() > 0) {
                        for (String ruleMark : outFormatMessage.getRuleViolates().split(",")) {
                            decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal(outFormatMessage.getComplexSave().get(ruleMark)));
                            decimal.setScale(2, RoundingMode.HALF_UP);
                            ht.put(ruleMark + "EV", decimal );
                        }
                    }else{
                        decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal(Double.valueOf(outFormatMessage.getNumber()) - Double.valueOf(outFormatMessage.getAuditNumber())));
                        decimal.setScale(2, RoundingMode.HALF_UP);
                        ht.put(outFormatMessage.getRuleViolates() + "EV", decimal);
                    }

                }

                if(ht.keySet().contains("kfTotal")){
                    decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal(Double.valueOf(outFormatMessage.getNumber()) - Double.valueOf(outFormatMessage.getAuditNumber())));
                    decimal.setScale(2, RoundingMode.HALF_UP);
                    ht.put("kfTotal", ht.get("kfTotal").add(decimal));
                }else{
                    decimal = new BigDecimal(outFormatMessage.getPrice()).multiply(new BigDecimal(Double.valueOf(outFormatMessage.getNumber()) - Double.valueOf(outFormatMessage.getAuditNumber())));
                    decimal.setScale(2, RoundingMode.HALF_UP);
                    ht.put("kfTotal", decimal);
                }
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
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        decimal = new BigDecimal(vo.getTotal());
                        decimal.setScale(2, RoundingMode.HALF_UP);
                        cell.setCellValue(decimal.doubleValue());
                        break;
                    case 18:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        if(StringUtils.isNotBlank(vo.getOrderTotal())) {
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
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        decimal = new BigDecimal(vo.getAuditTotal());
                        decimal.setScale(2, RoundingMode.HALF_UP);
                        cell.setCellValue(decimal.doubleValue());
                        break;
                    case 22:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        if(StringUtils.isNotBlank(vo.getOrderAuditTotal())) {
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
                        cell.setCellValue(new BigDecimal(vo.getValue()).divide(ht.get("kfTotal"), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).doubleValue() + "%");
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
                                cell.setCellValue(ht.get("medicine").divide(ht.get("kfTotal"),2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).doubleValue() + "%");
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
                                cell.setCellValue(ht.get("common").divide(ht.get("kfTotal"),2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).doubleValue() + "%");
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
