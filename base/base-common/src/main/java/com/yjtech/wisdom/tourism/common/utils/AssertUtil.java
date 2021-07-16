package com.yjtech.wisdom.tourism.common.utils;

import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by wuyongchong on 2019/8/21.
 */
public abstract class AssertUtil {

  public static ErrorCode DEFAULT_ERROR_CODE = ErrorCode.PARAM_WRONG;

  public static void isEq(Object a,Object b, RuntimeException re) {
    if (!String.valueOf(a).equals(String.valueOf(b))) {
      throw re;
    }
  }

  public static void state(boolean expression, RuntimeException re) {
    if (!expression) {
      throw re;
    }
  }

  public static void state(boolean expression, String message) {
    if (!expression) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }


  public static void isTrue(boolean expression, RuntimeException re) {
    if (!expression) {
      throw re;
    }
  }

  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void isFalse(boolean expression, RuntimeException re) {
    if (expression) {
      throw re;
    }
  }

  public static void isFalse(boolean expression, String message) {
    if (expression) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void isBlank(@Nullable String text, RuntimeException re) {
    if (StringUtils.hasLength(text)) {
      throw re;
    }
  }

  public static void isBlank(@Nullable String text, String message) {
    if (StringUtils.hasLength(text)) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void notBlank(@Nullable String text, RuntimeException re) {
    if (!StringUtils.hasLength(text)) {
      throw re;
    }
  }

  public static void notBlank(@Nullable String text, String message) {
    if (!StringUtils.hasLength(text)) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void isNull(@Nullable Object object, RuntimeException re) {
    if (object != null) {
      throw re;
    }
  }

  public static void isNull(@Nullable Object object, String message) {
    if (object != null) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void notNull(@Nullable Object object, RuntimeException re) {
    if (object == null) {
      throw re;
    }
  }

  public static void notNull(@Nullable Object object, String message) {
    if (object == null) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void hasLength(@Nullable String text, RuntimeException re) {
    if (!StringUtils.hasLength(text)) {
      throw re;
    }
  }

  public static void hasLength(@Nullable String text, String message) {
    if (!StringUtils.hasLength(text)) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void hasText(@Nullable String text, RuntimeException re) {
    if (!StringUtils.hasText(text)) {
      throw re;
    }
  }

  public static void hasText(@Nullable String text, String message) {
    if (!StringUtils.hasText(text)) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void doesNotContain(@Nullable String textToSearch, String substring,
                                    RuntimeException re) {
    if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch
        .contains(substring)) {
      throw re;
    }
  }

  public static void doesNotContain(@Nullable String textToSearch, String substring,
                                    String message) {
    if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch
        .contains(substring)) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void notEmpty(@Nullable Object[] array, RuntimeException re) {
    if (ObjectUtils.isEmpty(array)) {
      throw re;
    }
  }

  public static void notEmpty(@Nullable Object[] array, String message) {
    if (ObjectUtils.isEmpty(array)) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void notEmpty(@Nullable Collection<?> collection, RuntimeException re) {
    if (CollectionUtils.isEmpty(collection)) {
      throw re;
    }
  }

  public static void notEmpty(@Nullable Collection<?> collection, String message) {
    if (CollectionUtils.isEmpty(collection)) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void notEmpty(@Nullable Map<?, ?> map, RuntimeException re) {
    if (CollectionUtils.isEmpty(map)) {
      throw re;
    }
  }

  public static void notEmpty(@Nullable Map<?, ?> map, String message) {
    if (CollectionUtils.isEmpty(map)) {
      throw new CustomException(DEFAULT_ERROR_CODE, message);
    }
  }

  public static void noNullElements(@Nullable Object[] array, RuntimeException re) {
    if (array != null) {
      Object[] var2 = array;
      int var3 = array.length;

      for (int var4 = 0; var4 < var3; ++var4) {
        Object element = var2[var4];
        if (element == null) {
          throw re;
        }
      }
    }
  }

  public static void noNullElements(@Nullable Object[] array, String message) {
    if (array != null) {
      Object[] var2 = array;
      int var3 = array.length;

      for (int var4 = 0; var4 < var3; ++var4) {
        Object element = var2[var4];
        if (element == null) {
          throw new CustomException(DEFAULT_ERROR_CODE, message);
        }
      }
    }
  }

}
