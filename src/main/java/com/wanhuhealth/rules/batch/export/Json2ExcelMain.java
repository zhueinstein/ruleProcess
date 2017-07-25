package com.wanhuhealth.rules.batch.export;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wanhuhealth.rules.utils.CountClick;
import com.wanhuhealth.rules.utils.DateUtils;
import com.wanhuhealth.rules.utils.FrequencyEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by admin on 2017/7/18.
 *
 */
public class Json2ExcelMain {
    private static DecimalFormat df   = new DecimalFormat("######0.00");
    private static Hashtable<String, Double> ht = new Hashtable<String, Double>();
    public static void main(String[] args) {
        export(readList());
    }
    private static String outFileName = "d://drools_data//RuleResult"+ DateUtils.formatDate(new Date(), "yyyyMMddHHmm") +".xlsx";
    private static String loadFileName = "d://drools_data//DroolsResult_";
    static {
        File file = new File("D:\\drools_data");
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
        }
    }
    private static List<OutFormatMessage> readList(){
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
                        list.add(JSON.parseObject(s, OutFormatMessage.class));
                    }
                }else {
                    list.add(JSON.parseObject(content, OutFormatMessage.class));
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
                if(outFormatMessage.getOrderAuditTotal().equals(outFormatMessage.getOrderTotal())){
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

        for (OutFormatMessage outFormatMessage : list) {
           if(ht.keySet().contains(outFormatMessage.getOrderNO())){
               ht.put(outFormatMessage.getOrderNO(), ht.get(outFormatMessage.getOrderNO()) + Double.valueOf(outFormatMessage.getPrice()) * Double.valueOf(outFormatMessage.getNumber()));
           }else{
               ht.put(outFormatMessage.getOrderNO(), Double.valueOf(outFormatMessage.getPrice()) * Double.valueOf(outFormatMessage.getNumber()));
           }
            if(ht.keySet().contains(outFormatMessage.getOrderNO() + "A")){
                ht.put(outFormatMessage.getOrderNO() + "A", ht.get(outFormatMessage.getOrderNO() + "A") + Double.valueOf(outFormatMessage.getPrice()) * Double.valueOf(outFormatMessage.getAuditNumber()));
            }else{
                ht.put(outFormatMessage.getOrderNO() + "A", Double.valueOf(outFormatMessage.getPrice()) * Double.valueOf(outFormatMessage.getAuditNumber()));
            }
        }
//        return ht;
    }
    private static void export(List<OutFormatMessage> list){
        final Integer total = list.size();
        final CountClick click = new CountClick();
        ExportExcel excel = DefaultExportExcel.newInstance(new ExportExcel.CellBuilder() {
            @Override
            public Cell builder(Row row, int index, Object data) {
                if (index > 23) {
                    return null;
                }
                click.setCount(click.getCount() + 1);
                System.out.println("进度  " + Double.valueOf(click.getCount())/(23 * total)* 100 + "%" );
                Cell cell = row.createCell(index);
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
                        cell.setCellValue("男");
                        break;
                    case 7:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getAge());
                        break;
                    case 8:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getDiseases());
                        break;
                    case 9:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getCycle());
                        break;
                    case 10:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getDrugName());
                        break;
                    case 11:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getPackageSpecification());
                        break;
                    case 12:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(df.format(Double.valueOf(vo.getUseAmount())));
                        break;
                    case 13:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(FrequencyEnum.getName(vo.getFrequency()));
                        break;
                    case 14:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(df.format(Double.valueOf(vo.getPrice())));
                        break;
                    case 15:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Double.valueOf(vo.getNumber()));
                        break;
                    case 16:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(df.format(Double.valueOf(vo.getTotal())));
                        break;
                    case 17:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        if(StringUtils.isNotBlank(vo.getOrderTotal())) {
                            cell.setCellValue(df.format(ht.get(vo.getOrderNO())));
                        }
                        break;
                    case 18:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(vo.getRuleViolates());
                        break;
                    case 19:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Double.valueOf(vo.getAuditNumber()));
                        break;
                    case 20:
                        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(df.format(Double.valueOf(vo.getAuditTotal())));
                        break;
                    case 21:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        if(StringUtils.isNotBlank(vo.getOrderAuditTotal())) {
                            cell.setCellValue(df.format(ht.get(vo.getOrderNO() + "A")));
                        }
                        break;
                    case 22:
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        if(StringUtils.isNotBlank(vo.getOrderAuditTotal())) {
                            Double gap = (ht.get(vo.getOrderNO()) - ht.get(vo.getOrderNO() + "A")) * 100;
                            cell.setCellValue(df.format(gap / ht.get(vo.getOrderNO())) + "%");
                        }
                        break;
                }
                return cell;
            }
        });
//

        excel.setMaxCell(23);
        excel.setExportTitles(Arrays.asList("订单号","合并前订单数", "日期","社区医院", "患者姓名", "万户卡号", "性别", "年龄", "疾病", "用药周期", "药品名称",
                "规格","单次用量","频次","单价", "数量", "药品总价","处方总价", "违规规则代号", "审核后数量","审核后总价","审核后处方总价","节约比例"));
        File file = new File(outFileName);
        try {
            excel.write(list, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
