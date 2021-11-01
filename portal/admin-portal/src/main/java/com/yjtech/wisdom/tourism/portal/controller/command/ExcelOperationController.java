package com.yjtech.wisdom.tourism.portal.controller.command;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.enums.ImportInfoTypeEnum;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.ImportTemplateUtils;
import com.yjtech.wisdom.tourism.common.utils.file.FileUtils;

import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入导出业务层
 *
 * @Date 2020/11/3 17:52
 * @Author horadirm
 */
@Slf4j
public class ExcelOperationController {

    /**
     * 解析excel文档
     *
     * @param infoTypeEnum 文档信息类型
     * @param tClass 信息对应实体
     * @param file 文档文件
     * @param rowNum 表头所在行数
     * @param dataRowNum 数据所在行数
     * @param sheetName 工作区名称
     * @param <T>
     * @return
     */
    public <T> List<T> analysisExcel(ImportInfoTypeEnum infoTypeEnum, Class<T> tClass, MultipartFile file, Integer rowNum, Integer dataRowNum, String sheetName) {
        //构建信息数据
        List<T> infoList = new ArrayList<>();
        try {
            ExcelUtil<T> util = new ExcelUtil<>(tClass);
            infoList = util.importExcel(sheetName, file.getInputStream(), rowNum, dataRowNum);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(ErrorCode.EXCEL_IMPORT_ERROR, infoTypeEnum.getDescribe() + "信息导入失败：excel数据解析异常,请使用规范的模板文档");
        }

        log.info("{}信息导入数据 ====================> {}", infoTypeEnum.getDescribe(), JSONObject.toJSONString(infoList));

        if(infoList.isEmpty()){
            throw new CustomException(ErrorCode.PARAM_MISS, infoTypeEnum.getDescribe() + "信息导入失败：导入数据为空");
        }

        if (infoList.size() > 1000) {
            throw new CustomException(ErrorCode.EXCEL_IMPORT_ERROR, infoTypeEnum.getDescribe() + "单次数据量不能超过1000行");
        }

        return infoList;
    }

    /**
     * 导出模板文档
     * @param typeDesc 文档类型描述
     * @param request
     * @param response
     * @return
     */
    public void getTemplate(String typeDesc, HttpServletRequest request, HttpServletResponse response){
        try {
            //构建模板文件名称
            String templateFileName = String.format("%s%s%s", typeDesc, ImportTemplateUtils.TEMPLATE_SUPPLEMENT, ImportTemplateUtils.TEMPLATE_SUFFIX_XLSX);
            //构建默认统计时间
            String defaultDuration = DateTimeUtil.localDateToString(LocalDate.now(),"yyyyMMdd");
            //构建文件名称
            String fileName = String.format("%s%s%s%s", typeDesc, ImportTemplateUtils.TEMPLATE_SUPPLEMENT, defaultDuration, ImportTemplateUtils.TEMPLATE_SUFFIX_XLSX);

            response.setCharacterEncoding(CharEncoding.UTF_8);
            response.setContentType("application/force-download");
            response.setHeader(
                    "Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName));
            InputStream fileIs = this.getClass().getClassLoader().getResourceAsStream(ImportTemplateUtils.RESOURCE_LOCATION + templateFileName);
            FileUtils.writeBytes(fileIs, response);
        } catch (Exception e) {
            log.error("====================> 获取模板文档失败 <====================\n");
            e.printStackTrace();
        }
    }


}
