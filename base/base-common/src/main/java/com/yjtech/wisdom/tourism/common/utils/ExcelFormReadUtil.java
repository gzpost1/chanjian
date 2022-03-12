package com.yjtech.wisdom.tourism.common.utils;

import cn.hutool.core.util.ReflectUtil;
import com.yjtech.wisdom.tourism.common.annotation.ExcelRead;
import com.yjtech.wisdom.tourism.common.bean.DemoExtraData;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ExcelFormReadUtil<T> {
    /**
     * 读取excel
     * @param list        读取到的数据
     * @param clazz       映射的class
     * @param startRow    第几行开始
     * @param <T>
     * @return
     */
    public <T> T readExcel(List<DemoExtraData> list, Class<T> clazz,int startRow) {
        try {
            T data = clazz.newInstance();
            Field[] declaredFields = data.getClass().getDeclaredFields();


            for (int i = 0; i < list.size(); i++) {
                if (Objects.nonNull(declaredFields)) {
                    for (Field declaredField : declaredFields) {
                        ExcelRead annotation = declaredField.getAnnotation(ExcelRead.class);
                        //加上开上行而且需要excel是从第一行开始的，而数组查询是从0开始的
                        if (Objects.nonNull(annotation) && (annotation.rowNum()) == (i+startRow+1)) {
                            DemoExtraData extraData = list.get(i);
                            if(Objects.nonNull(extraData)){
                                Object excelResult = ReflectUtil.getFieldValue(extraData, "row" + annotation.cellNum());
                                if(Objects.nonNull(excelResult)){
                                    declaredField.setAccessible(true);
                                    declaredField.set(data,excelResult);
                                }
                            }

                        }
                    }

                }

            }

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return null;
    }

    /**
     * 利用validate校验excel参数合法性
     * @param t
     * @param dataName
     */
    public void validateExcelReadData(T t,String dataName){
        Validator validator = new SpringValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());
        BindingResult bindingResult = new BindException(t, dataName);
        validator.validate(t, bindingResult);
        if (bindingResult.hasErrors()) {
            String join = StringUtils.join(
                    bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()),
                    ",");
            throw new CustomException("导入失败，原因为：" + join);
        }
    }
}
