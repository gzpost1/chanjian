//package com.yjtech.wisdom.tourism.portal.controller.marketing;
//
//import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcOtaPlaceParam;
//import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
//import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
//import com.yjtech.wisdom.tourism.marketing.pojo.dto.PlaceInfoDTO;
//import com.yjtech.wisdom.tourism.marketing.pojo.vo.PlaceQueryVO;
//import com.yjtech.wisdom.tourism.marketing.service.*;
//import com.yjtech.wisdom.tourism.system.service.zc.params.ZcBaiduParam;
//import com.yjtech.wisdom.tourism.system.service.zc.params.ZcOtaEvaluateParam;
//import com.yjtech.wisdom.tourism.system.service.zc.params.ZcOtaHotTagParam;
//import com.yjtech.wisdom.tourism.system.service.zc.params.ZcOtaPlaceParam;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * 营销推广 第三方数据
// *
// * @Author horadirm
// * @Date 2020/11/20 18:02
// */
//@Slf4j
//@RestController
//@RequestMapping("/marketing/data/")
//public class MarketingDataController {
//
//    @Autowired
//    private MarketingPlaceService marketingPlaceService;
//    @Autowired
//    private MarketingEvaluateService marketingEvaluateService;
//    @Autowired
//    private MarketingHotTagService marketingHotTagService;
//    @Autowired
//    private MarketingBaiduService marketingBaiduService;
//    @Autowired
//    private MarketingWechatService marketingWechatService;
//    @Autowired
//    private MarketingMicroblogService marketingMicroblogService;
//    @Autowired
//    private MarketingProductService marketingProductService;
//    @Autowired
//    private MarketingTravelGuideService marketingTravelGuideService;
//
//
//    /**
//     * 同步创建场所信息
//     * @return
//     */
//    @GetMapping("place/syncCreate")
//    public JsonResult syncCreatePlace() {
//        //优先物理删除所有
//        marketingPlaceService.deleteAll();
//
//        //同步创建
//        marketingPlaceService.syncCreate(new ZcOtaPlaceParam());
//        return JsonResult.success();
//    }
//
//    /**
//     * 同步评价信息
//     * @return
//     */
//    @GetMapping("evaluate/syncCreate")
//    public JsonResult syncCreateEvaluate() {
//        //获取第三方场所信息列表
//        List<PlaceInfoDTO> placeInfoList = marketingPlaceService.getPlaceInfoList(new PlaceQueryVO());
//        if(placeInfoList.isEmpty()){
//            return JsonResult.error(ErrorCode.NOT_FOUND.getCode(), "同步评价信息失败：有效场所信息不存在");
//        }
//
//        for(PlaceInfoDTO placeInfoDTO : placeInfoList){
//            ZcOtaEvaluateParam params = new ZcOtaEvaluateParam();
//            params.setTargetId(placeInfoDTO.getTpId());
//            params.setAreaCode(placeInfoDTO.getAreaCode());
//            params.setTargetName(placeInfoDTO.getPlaceName());
//            //同步第三方评价数据
//            marketingEvaluateService.syncCreate(params);
//        }
//
//        return JsonResult.success();
//    }
//
//    /**
//     * 同步热词信息
//     * @return
//     */
//    @GetMapping("hotTag/syncCreate")
//    public JsonResult syncCreateHotTag() {
//        //获取第三方场所信息列表
//        List<PlaceInfoDTO> placeInfoList = marketingPlaceService.getPlaceInfoList(new PlaceQueryVO());
//        if(placeInfoList.isEmpty()){
//            return JsonResult.error(ErrorCode.NOT_FOUND.getCode(), "同步热词信息失败：有效场所信息不存在");
//        }
//
//        for(PlaceInfoDTO placeInfoDTO : placeInfoList){
//            ZcOtaHotTagParam params = new ZcOtaHotTagParam();
//            params.setTargetId(placeInfoDTO.getTpId());
//            params.setAreaCode(placeInfoDTO.getAreaCode());
//            params.setTargetName(placeInfoDTO.getPlaceName());
//            //同步第三方评价数据
//            marketingHotTagService.syncCreate(params);
//        }
//
//        return JsonResult.success();
//    }
//
//    /**
//     * 同步百度信息
//     * @return
//     */
//    @GetMapping("baidu/syncCreate")
//    public JsonResult syncCreateBaidu() {
//        //同步创建
//        marketingBaiduService.syncCreate(new ZcBaiduParam());
//        return JsonResult.success();
//    }
//
//    /**
//     * 同步微信信息
//     * @return
//     */
//    @GetMapping("wechat/syncCreate")
//    public JsonResult syncCreateWechat() {
//        //同步创建
//        marketingWechatService.syncCreate();
//        return JsonResult.success();
//    }
//
//    /**
//     * 同步微博信息
//     * @return
//     */
//    @GetMapping("microblog/syncCreate")
//    public JsonResult syncCreateMicroblog() {
//        //同步创建
//        marketingMicroblogService.syncCreate();
//        return JsonResult.success();
//    }
//
//    /**
//     * 同步产品信息
//     * @return
//     */
//    @GetMapping("product/syncCreate")
//    public JsonResult syncCreateProduct() {
//        //同步创建
//        marketingProductService.syncCreate();
//        return JsonResult.success();
//    }
//
//    /**
//     * 同步游记攻略信息
//     * @return
//     */
//    @GetMapping("travelGuide/syncCreate")
//    public JsonResult syncCreateTravelGuide() {
//        //同步创建
//        marketingTravelGuideService.syncCreate();
//        return JsonResult.success();
//    }
//
//}
