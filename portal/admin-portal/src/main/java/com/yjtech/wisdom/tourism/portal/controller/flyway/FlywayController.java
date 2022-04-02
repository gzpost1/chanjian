//package com.yjtech.wisdom.tourism.portal.controller.flyway;
//
//import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * flyway
// *
// * @date 2022/3/30 9:42
// * @author horadirm
// */
//@Slf4j
//@RestController
//@RequestMapping("/flyway/")
//public class FlywayController {
//
//    @Autowired
//    private FlywayConfig flywayConfig;
//
//
//    @GetMapping("migrate")
//    public JsonResult migrate() {
//        flywayConfig.migrate();
//        return JsonResult.success();
//    }
//
//}
