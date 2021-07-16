package com.yjtech.wisdom.tourism.wechat.wxcommon.util;

import com.yjtech.wisdom.tourism.wechat.wxcommon.annotation.Required;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxError;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuyongchong on 2019/9/16.
 */
public class RequiredUtils {

  private static Logger log = LoggerFactory.getLogger(RequiredUtils.class);

  /**
   * 检查bean里标记为@Required的field是否为空，为空则抛异常
   *
   * @param bean 要检查的bean对象
   */
  public static void checkRequiredFields(Object bean) throws WxErrorException {

    List<String> requiredFields = Lists.newArrayList();

    List<Field> fields = new ArrayList<>(Arrays.asList(bean.getClass().getDeclaredFields()));

    fields.addAll(Arrays.asList(bean.getClass().getSuperclass().getDeclaredFields()));

    for (Field field : fields) {
      try {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        if (field.isAnnotationPresent(Required.class)) {
          boolean isRequiredMissing = field.get(bean) == null
              || (field.get(bean) instanceof String
              && StringUtils.isBlank(field.get(bean).toString())
          );
          if (isRequiredMissing) {
            requiredFields.add(field.getName());
          }
        }
        field.setAccessible(isAccessible);
      } catch (SecurityException | IllegalArgumentException
          | IllegalAccessException e) {
        log.error(e.getMessage(), e);
      }
    }
    if (!requiredFields.isEmpty()) {
      String msg = "必填字段 " + requiredFields + " 必须提供值";
      log.debug(msg);
      throw new WxErrorException(WxError.builder().errorMsg(msg).build());
    }
  }
}
