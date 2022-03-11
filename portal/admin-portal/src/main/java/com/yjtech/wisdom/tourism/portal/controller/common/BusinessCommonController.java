package com.yjtech.wisdom.tourism.portal.controller.common;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.yjtech.wisdom.tourism.common.enums.ImportInfoTypeEnum;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.common.utils.ImportTemplateUtils;
import com.yjtech.wisdom.tourism.common.utils.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;
import com.yjtech.wisdom.tourism.portal.controller.common.BusinessCommonController;


/**
 * 业务共有控制器
 *
 * @Date 2020/11/3 17:52
 * @Author horadirm
 */
@Slf4j
public class BusinessCommonController {

    /**
     * 获取模板文档
     * @param importInfoTypeEnum 文档类型
     * @param request
     * @param response
     * @return
     */
    public void getTemplate(ImportInfoTypeEnum importInfoTypeEnum, HttpServletRequest request, HttpServletResponse response){
        try {
            response.setCharacterEncoding(CharEncoding.UTF_8);
            response.setContentType("application/force-download");
            response.setHeader(
                    "Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, importInfoTypeEnum.getDescribe()));
            InputStream fileIs = this.getClass().getClassLoader().getResourceAsStream(ImportTemplateUtils.RESOURCE_LOCATION + importInfoTypeEnum.getDescribe());
            FileUtils.writeBytes(fileIs, response);
        } catch (Exception e) {
            log.error("====================> 获取模板文档失败 <====================\n");
            e.printStackTrace();
        }
    }

}
