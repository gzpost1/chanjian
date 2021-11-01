package com.yjtech.wisdom.tourism.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.yjtech.wisdom.tourism.common.annotation.Excel;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导入模板 处理工具
 *
 * @Describe 针对导入文档模板进行强耦合处理，后续视情况进行解耦及优化
 * @Date 2020/11/3 14:53
 * @Author horadirm
 */
@Slf4j
public class ImportTemplateUtils {

    /**
     * contentType
     */
    private static final String CONTENT_TYPE = "application/vnd.ms-excel;charset=utf-8";
//    private static final String CONTENT_TYPE = "application/force-download";
    /**
     * 英文中划线
     */
    public static final String STRIKETHROUGH_EN = "-";
    /**
     * 英文句号
     */
    public static final String FULL_STOP_EN = "\\.";
    /**
     * 中文小括号（左）
     */
    public static final String BRACKET_LEFT_CN = "（";
    /**
     * 中文小括号（右）
     */
    public static final String BRACKET_RIGHT_CN = "）";
    /**
     * 英文小括号（左）
     */
    public static final String BRACKET_LEFT_EN = "(";
    /**
     * 英文小括号（右）
     */
    public static final String BRACKET_RIGHT_EN = ")";
    /**
     * 开始key
     */
    public static final String KEY_BEGIN = "begin";
    /**
     * 结束key
     */
    public static final String KEY_END = "end";
    /**
     * get前缀
     */
    public static final String PREFIX_GET = "get";
    /**
     * 资源地址
     */
    public static final String RESOURCE_LOCATION = "files/excel/";
    /**
     * 模板名称补充
     */
    public static final String TEMPLATE_SUPPLEMENT = "模板";
    /**
     * 模板后缀
     */
    public static final String TEMPLATE_SUFFIX_XLSX = ".xlsx";



    /**
     * 根据时间戳获取文档名称
     * @param documentName 原始文档名称
     * @param cut 截取标识
     * @param requireIndex 截取后所需部分下标
     * @return
     */
    public static String cutNameByTimestamp(String documentName, String cut, Integer requireIndex){
        try {
            return String.format("%s%s", documentName.split(cut)[requireIndex], DateUtils.dateTimeNow());
        }catch (Exception e){
            log.info("====================> 根据时间戳获取文档名称 异常 <====================\n");
            e.printStackTrace();
            return documentName;
        }
    }

    /**
     * 根据名称获取统计时间
     * @param documentName
     * @return
     */
    public static String cutDurationByName(String documentName){
        try {
            //获取“-”下标
            int strikethroughIndex = documentName.indexOf(STRIKETHROUGH_EN);
            return documentName.substring(strikethroughIndex - Constants.NumberConstants.NUMBER_EIGHT, strikethroughIndex + Constants.NumberConstants.NUMBER_NINE);
        }catch (Exception e){
            log.info("====================> 根据名称获取统计时间 异常 <====================\n");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建统计时间
     * @param documentName
     * @return
     */
    public static Map<String, String> buildDuration(String documentName){
        try {
            Map<String, String> map = new HashMap<>(Constants.NumberConstants.NUMBER_TWO);
            //获取统计时间
            String duration = cutDurationByName(documentName);
            String[] durationArray = duration.split(STRIKETHROUGH_EN);

            map.put(KEY_BEGIN, durationArray[Constants.NumberConstants.NUMBER_ZERO]);
            map.put(KEY_END, durationArray[Constants.NumberConstants.NUMBER_ONE]);

            return map;
        }catch (Exception e){
            log.info("====================> 构建统计时间 异常 <====================\n");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据注解构建实体信息
     * @param t
     * @param <T>
     */
    public static <T> Map<String, Object> buildInfoByAnnotation(T t) {
        //构建返回
        Map<String, Object> resultMap = new HashMap<>(16);
        Field[] declaredFields = t.getClass().getDeclaredFields();
        for(Field field : declaredFields){
            //判断属性是否标注该注解
            if(field.isAnnotationPresent(Excel.class)){
                //获取该注解信息
                Excel attr = field.getAnnotation(Excel.class);
                //通过调用get方法获取属性值
                Object value = invokeGet(t, field.getName());

                resultMap.put(attr.name(), value);
            }
        }
        return resultMap;
    }

    /**
     * 调用get方法获取属性值
     * @param object
     * @param fieldName
     * @return
     */
    public static Object invokeGet(Object object, String fieldName) {
        //构建get方法
        try {
            Method method = object.getClass().getMethod(PREFIX_GET +
                            fieldName.substring(Constants.NumberConstants.NUMBER_ZERO, Constants.NumberConstants.NUMBER_ONE).toUpperCase() +
                            fieldName.substring(Constants.NumberConstants.NUMBER_ONE));
            //调用
            return method.invoke(object, new Object[Constants.NumberConstants.NUMBER_ZERO]);
        } catch (Exception e) {
            log.info("====================> 调用get方法获取属性值 异常 <====================\n");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 构建模板
     * @param exportFileName 导出文件名称
     * @param templateFilePath 导出文件路径
     * @param infoKey 导出数据自定义名称，匹配模板中数据列表名称
     * @param infoList 导出数据集合
     * @param response
     * @param <T>
     * @throws IOException
     */
    public static <T> void buildTemplate(String exportFileName,
                                         String templateFilePath,
                                         String infoKey,
                                         List<T> infoList,
                                         HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.setContentType(CONTENT_TYPE);
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(exportFileName, CharEncoding.UTF_8));

        TemplateExportParams params = new TemplateExportParams(templateFilePath);
        params.setScanAllsheet(true);

        final Map<String, Object> dataMap = new HashMap<String, Object>(2) {
            private static final long serialVersionUID = 5972946029271860203L;
            {
                put(infoKey, infoList);
            }
        };

        Workbook workbook = ExcelExportUtil.exportExcel(params, dataMap);
        workbook.write(response.getOutputStream());
    }


}
