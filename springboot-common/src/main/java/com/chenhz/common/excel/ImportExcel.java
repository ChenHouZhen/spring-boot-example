package com.chenhz.common.excel;

import com.chenhz.common.excel.annotation.ExcelField;
import com.chenhz.common.exception.RRException;
import com.chenhz.common.validator.ValidatorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImportExcel {
    private static Logger log = LoggerFactory.getLogger(ImportExcel.class);

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 标题行号
     */
    private int headerNum;

    /**
     * 构造函数
     *
     * @param fileName  导入文件，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, int headerNum)
            throws InvalidFormatException, IOException {
        this(new File(fileName), headerNum);
    }

    /**
     * 构造函数
     *
     * @param file      导入文件对象，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum)
            throws InvalidFormatException, IOException {
        this(file, headerNum, 0);
    }

    /**
     * 构造函数
     *
     * @param fileName   导入文件
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        this(new File(fileName), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param file       导入文件对象
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param multipartFile 导入文件对象
     * @param headerNum     标题行号，数据行号=标题行号+1
     * @param sheetIndex    工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param fileName   导入文件对象
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, InputStream is, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("Import file is empty!");
        } else if (fileName.toLowerCase().endsWith("xls")) {
            this.wb = WorkbookFactory.create(is);
        } else if (fileName.toLowerCase().endsWith("xlsx")) {
            this.wb = new XSSFWorkbook(is);
        } else {
            throw new RuntimeException("Invalid import file type!");
        }
        if (this.wb.getNumberOfSheets() < sheetIndex) {
            throw new RuntimeException("No sheet in Import file!");
        }
        this.sheet = this.wb.getSheetAt(sheetIndex);
        this.headerNum = headerNum;
        log.debug("Initialize success.");
    }

    /**
     * 获取行对象
     *
     * @param rownum
     * @return
     */
    private Row getRow(int rownum) {
        return this.sheet.getRow(rownum);
    }

    /**
     * 获取数据行号
     *
     * @return
     */
    private int getDataRowNum() {
//        return headerNum + 1;
        return headerNum; // poi的row是从零开始的
    }

    /**
     * 获取最后一个数据行号
     *
     * @return
     */
    private int getLastDataRowNum() {
        return this.sheet.getLastRowNum() + 1;
    }

    /**
     * 不要原生的计算行数的方法，有bug
     * 以下计算方法：1、遇到 空行（无数据，有|无格式），不计算到总行数，excel向上提
     */
    private int calcLastDataRowNum(){
        log.info("原来的行数 >>> "+ this.sheet.getLastRowNum());
        boolean flag = false;
        int i = this.getDataRowNum();
        for (;i < this.getLastDataRowNum();) {
            Row row = this.getRow(i);
            if (row == null){
                this.sheet.shiftRows(i+1,this.getLastDataRowNum(),-1);
                continue;
            }
            flag = false;
            int column = 0;
            for (Cell c: row){
                Object val = this.getCellValue(row,column++);
                if (!org.springframework.util.StringUtils.isEmpty(val)){
                    flag = true;
                    break;
                }
            }
            if (flag){
                i++;
            }else {
                if (i == this.getLastDataRowNum()){
                    this.sheet.removeRow(row);
                }else {
                    this.sheet.shiftRows(i+1,this.getLastDataRowNum(),-1);
                }
            }
        }
        log.info("计算后的行数 >>> "+ i);
        return i;
    }




    /**
     * 获取最后一个列号
     *
     * @return
     */
//    public int getLastCellNum() {
//        return this.getRow(headerNum).getLastCellNum();
//    }

    /**
     * 获取单元格值
     *
     * @param row    获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    private Object getCellValue(Row row, int column) {
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                    // val = cell.getNumericCellValue();
                    // 当excel 中的数据为数值或日期是需要特殊处理
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        double d = cell.getNumericCellValue();
                        Date date = HSSFDateUtil.getJavaDate(d);
                        SimpleDateFormat dformat = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        val = dformat.format(date);
                    } else {
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setGroupingUsed(false);// true时的格式：1,234,567,890
                        val = nf.format(cell.getNumericCellValue());// 数值类型的数据为double，所以需要转换一下
                    }
                } else if (cell.getCellTypeEnum() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
                    val = cell.getCellFormula();
                } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellTypeEnum() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }
            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }

    /**
     * 获取导入数据列表
     *
     * @param cls    导入对象类型
     * @param groups 导入分组
     */
    public <E> List<E> getDataList(Class<E> cls, int... groups) throws InstantiationException, IllegalAccessException {
        List<Object[]> annotationList = new ArrayList();
        // Get annotation field
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            ExcelField ef = f.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, f});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, f});
                }
            }
        }
        // Get annotation method
        Method[] ms = cls.getDeclaredMethods();
        for (Method m : ms) {
            ExcelField ef = m.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, m});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, m});
                }
            }
        }
        // Fields sorting
        Collections.sort(annotationList, new Comparator<Object[]>() {
            public int compare(Object[] o1, Object[] o2) {
                return new Integer(((ExcelField) o1[0]).sort()).compareTo(
                        new Integer(((ExcelField) o2[0]).sort()));
            };
        });

        List<String> expectHeaderList = new ArrayList();
        List<String> realHeaderList = new ArrayList();
        int headerColumn = 0;
        for (Object[] os : annotationList){

            //获取注解title属性值
            String t = ((ExcelField)os[0]).title().trim();
            // 如果存在备注，则去掉，只保留 Title
            String[] ss = StringUtils.split(t, "**", 2);
            if (ss.length==2){
                t = ss[0];
            }
            expectHeaderList.add(t);
            Object val = this.getCellValue(this.getRow(headerNum-1),headerColumn++);
            if (val == null || "".equals(val.toString())){
                throw new RRException("模板不正确,也可能是模板标题错误");
            }
            realHeaderList.add(val.toString().trim());
        }
        if (!expectHeaderList.equals(realHeaderList)){
            throw new RRException("模板不正确,也可能是模板标题错误");
        }

        //log.debug("Import column count:"+annotationList.size());
        // Get excel data
        List<E> dataList = new ArrayList<>();
        //循环获取每一行的数据
        int rownum = this.calcLastDataRowNum();
        for (int i = this.getDataRowNum(); i < rownum; i++) {
            //实例化对象
            E e = (E) cls.newInstance();
            int column = 0;
            Row row = this.getRow(i);
            StringBuilder sb = new StringBuilder();
            //取出每一个通过@Excel标注的字段或方法
            for (Object[] os : annotationList) {
                Object val = this.getCellValue(row, column++);
                if (val != null && val.toString().length() > 0) {
                    ExcelField ef = (ExcelField) os[0];
                    // Get param type and type cast
                    Class<?> valType = Class.class;
                    if (os[1] instanceof Field) {
                        valType = ((Field) os[1]).getType();
                    } else if (os[1] instanceof Method) {
                        Method method = ((Method) os[1]);
                        if ("get".equals(method.getName().substring(0, 3))) {
                            valType = method.getReturnType();
                        } else if ("set".equals(method.getName().substring(0, 3))) {
                            valType = ((Method) os[1]).getParameterTypes()[0];
                        }
                    }
                    //log.debug("Import value type: ["+i+","+column+"] " + valType);
                    try {
                        //如果导入的java对象，需要在这里自己进行变换。
                        if (valType == String.class) {
                            String s = String.valueOf(val.toString());
                            if (StringUtils.endsWith(s, ".0")) {
                                val = StringUtils.substringBefore(s, ".0");
                            } else {
                                val = String.valueOf(val.toString());
                            }
                        } else if (valType == Integer.class) {
                            val = Double.valueOf(val.toString()).intValue();  // poi不提供对整数的读取，所以读出来的是double类型，可以获取后进行强转
                        } else if (valType == Long.class) {
                            val = Double.valueOf(val.toString()).longValue();
                        } else if (valType == Double.class) {
                            val = Double.valueOf(val.toString());
                        } else if (valType == Float.class) {
                            val = Float.valueOf(val.toString());
                        } else if (valType == Date.class) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            val = sdf.parse(val.toString());
                        } else {
                            if (ef.fieldType() != Class.class) {
                                val = ef.fieldType().getMethod("getValue", String.class).invoke(null, val.toString());
                            } else {
                                val = Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
                                        "fieldtype." + valType.getSimpleName() + "Type")).getMethod("getValue", String.class).invoke(null, val.toString());
                            }
                        }
                    } catch (Exception ex) {
                        log.error("Get cell value [" + i + "," + column + "] error: " + ex.toString());
                        val = null;
                    }
                    // set entity value
                    if (os[1] instanceof Field) {
                        Reflections.invokeSetter(e, ((Field) os[1]).getName(), val);
                    } else if (os[1] instanceof Method) {
                        String mthodName = ((Method) os[1]).getName();
                        if ("get".equals(mthodName.substring(0, 3))) {
                            mthodName = "set" + StringUtils.substringAfter(mthodName, "get");
                        }
                        Reflections.invokeMethod(e, mthodName, new Class[]{valType}, new Object[]{val});
                    }
                }
                sb.append(val).append(", ");
            }

            try {
                ValidatorUtils.validateEntity(e);
            }catch (RRException ee){
                log.error("DATE:[{}] ,ERROR:EXCEL文件不符合规范 ,ERRORMSG: 行数：[{}], 错误信息：[{}] ",new Date(),(i+headerNum),ee.getMessage());
                throw new IllegalArgumentException("EXCEL文件不符合规范！行数->"+(i+headerNum)+" 错误信息->"+ee.getMessage());
            }
            dataList.add(e);
            log.debug("Read success: [" + i + "] " + sb.toString());
        }
        return dataList;
    }

//	/**
//	 * 导入测试
//	 */
//	public static void main(String[] args) throws Throwable {
//
//		ImportExcel ei = new ImportExcel("target/export.xlsx", 1);
//
//		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
//			Row row = ei.getRow(i);
//			for (int j = 0; j < ei.getLastCellNum(); j++) {
//				Object val = ei.getCellValue(row, j);
//				System.out.print(val+", ");
//			}
//			System.out.print("\n");
//		}
//
//	}
}
