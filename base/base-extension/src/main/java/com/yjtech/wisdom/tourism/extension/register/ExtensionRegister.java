package com.yjtech.wisdom.tourism.extension.register;

import com.yjtech.wisdom.tourism.extension.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ExtensionRegister 
 * @author fulan.zjf 2017-11-05
 */
@Component
public class ExtensionRegister{

    @Resource
    private ExtensionRepository extensionRepository;

    public final static String EXTENSION_EXTPT_NAMING = "ExtPt";


    public void doRegistration(ExtensionPointI extensionObject){
        Class<?>  extensionClz = extensionObject.getClass();
        Extension extensionAnn = extensionClz.getDeclaredAnnotation(Extension.class);
        BizScenario bizScenario = BizScenario.valueOf(extensionAnn.bizId(), extensionAnn.useCase(), extensionAnn.scenario());
        ExtensionCoordinate extensionCoordinate = new ExtensionCoordinate(calculateExtensionPoint(extensionClz), bizScenario.getUniqueIdentity());
        ExtensionPointI preVal = extensionRepository.getExtensionRepo().put(extensionCoordinate, extensionObject);
        if (preVal != null) {
            throw new RuntimeException("Duplicate registration is not allowed for :" + extensionCoordinate);
        }

    }

    /**
     * @param targetClz
     * @return
     */
    private String calculateExtensionPoint(Class<?> targetClz) {
        Class[] interfaces = targetClz.getInterfaces();
        if (interfaces == null || interfaces.length == 0)
            throw new RuntimeException("Please assign a extension point interface for "+targetClz);
        for (Class intf : interfaces) {
            String extensionPoint = intf.getSimpleName();
            if (extensionPoint.contains(EXTENSION_EXTPT_NAMING))
                return intf.getName();
        }
        throw new RuntimeException("Your name of ExtensionPoint for "+targetClz+" is not valid, must be end of "+ EXTENSION_EXTPT_NAMING);
    }

}