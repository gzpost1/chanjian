//package com.yjtech.wisdom.tourism.portal.controller.flyway;
//
//import lombok.extern.slf4j.Slf4j;
//import org.flywaydb.core.Flyway;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.sql.DataSource;
//
///**
// * flyway 配置
// *
// * @date 2022/3/29 16:54
// * @author horadirm
// */
//@Slf4j
//@Component
//public class FlywayConfig {
//
//    @Autowired
//    private DataSource dataSource;
//
//
//    @PostConstruct
//    public void migrate() {
//        Flyway flyway = Flyway
//                .configure().dataSource(dataSource)
//                .locations("db/wlcy")
//                .encoding("utf-8")
//                .baselineOnMigrate(true)
//                .baselineVersion("0")
//                .placeholderReplacement(false)
//                .load();
//        try {
//            flyway.migrate();
//        }catch (Exception e){
//            log.info("******************** flyway首次配置失败 ********************");
//            e.printStackTrace();
//            try {
//                log.info("******************** flyway尝试修复 ********************");
//                flyway.repair();
//                log.info("******************** flyway尝试二次配置 ********************");
//                flyway.migrate();
//            }catch (Exception e1){
//                log.info("******************** flyway二次配置失败 ********************");
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
