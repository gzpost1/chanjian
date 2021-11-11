package com.yjtech.wisdom.tourism.portal.controller.video;


import com.yjtech.wisdom.tourism.common.bean.zlmedia.ZlmediaTaskBaseInfo;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.ZlmediaConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.ValidateExcelEntity;
import com.yjtech.wisdom.tourism.common.enums.ImportInfoTypeEnum;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.task.ZlmediaDelayedTask;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.framework.manager.ZlmediaDelayQueueManager;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.portal.controller.command.ExcelOperationController;
import com.yjtech.wisdom.tourism.resource.video.bo.VideoGuideBo;
import com.yjtech.wisdom.tourism.resource.video.domain.TbVideoParam;
import com.yjtech.wisdom.tourism.resource.video.entity.TbVideoEntity;
import com.yjtech.wisdom.tourism.resource.video.service.TbVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isNotEmpty;

/***
 * 后台管理 视频监控
 *
 * @author Mujun
 * @since 2021-07-05
 */
@Slf4j
@RestController
@RequestMapping("/video")
public class TbVideoController extends BaseCurdController<TbVideoService, TbVideoEntity, TbVideoParam> {

    @Autowired
    private ZlmediaDelayQueueManager zlmediaDelayQueueManager;


    /** 获取监控excel模板 */
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) {
        new ExcelOperationController().getTemplate("监控导入",request,response);
    }

    /**
     * 更新信息
     * @param entity
     * @return
     */
    @Override
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid TbVideoEntity entity) {
        boolean result = service.updateById(entity);

        //修改视频流地址后，同步新增代理
        if(result && StringUtils.isNotBlank(entity.getUrl())){
            //获取流id
            String stream = ZlmediaConstants.INDUSTRY_MONITORING_STANDARD_VIDEO_MARK + entity.getId();
            //移除历史流代理
            service.closeStreamsByMedia(stream);

            zlmediaDelayQueueManager.put(new ZlmediaDelayedTask(new ZlmediaTaskBaseInfo(entity.getId().toString(),
                    stream, entity.getUrl()), ZlmediaConstants.DEFAULT_DELAYED_DURATION));
        }
        return JsonResult.success(result);
    }

    /**
     * 導入exce
     *
     * @param file   excel
     * @param spotId 景区id
     * @return
     */
    @PostMapping("/importExcel")
    @Transactional
    public JsonResult importExcel(@RequestParam("file") MultipartFile file, @Valid @RequestParam("spotId") Long spotId) {

        //构建信息数据
        List<VideoGuideBo> videoGuideBos = new ExcelOperationController()
                .analysisExcel(ImportInfoTypeEnum.TYPE_SUPERVISION, VideoGuideBo.class, file, Constants.NumberConstants.NUMBER_ZERO, Constants.NumberConstants.NUMBER_ONE, null);
        //校验文档数据合法性
        ValidateExcelEntity validateExcelEntity = ExcelUtil.validExcel(videoGuideBos);
        if (!validateExcelEntity.isPass()) {
            String result = isNotEmpty(validateExcelEntity.getErrorMsg()) ? validateExcelEntity.getErrorMsg().get(0) : null;
            return JsonResult.error(ErrorCode.EXCEL_IMPORT_ERROR.getCode(), result);
        }
        //移除无效信息
        videoGuideBos.removeAll(Collections.singleton(null));

        String errorMsg = service.importExcel(videoGuideBos, spotId);
        if (StringUtils.isNotBlank(errorMsg)) {
            return JsonResult.error(ErrorCode.EXCEL_IMPORT_ERROR.getCode(), errorMsg);
        }
        return JsonResult.success("导入成功");
    }
}
