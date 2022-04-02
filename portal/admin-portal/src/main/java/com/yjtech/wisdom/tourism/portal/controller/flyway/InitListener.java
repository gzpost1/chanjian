package com.yjtech.wisdom.tourism.portal.controller.flyway;

import com.yjtech.wisdom.tourism.extension.register.ExtensionBootstrap;
import com.yjtech.wisdom.tourism.scheduler.service.SysJobService;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import com.yjtech.wisdom.tourism.system.service.TagService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 初始化事件 监听
 *
 * @author horadirm
 * @date 2022/4/2 9:34
 */
@Component
public class InitListener implements ApplicationListener<ApplicationStartedEvent> {


    @Autowired
    private ExtensionBootstrap extensionBootstrap;
    @Autowired
    private SysJobService sysJobService;
    @Autowired
    private SysDictTypeService sysDictTypeService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private TagService tagService;

    /**
     * 项目启动时，初始化事件
     *
     * @param event
     */
    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        extensionBootstrap.init();
        sysJobService.init();
        sysDictTypeService.init();
        sysConfigService.init();
        tagService.init();
    }

}
