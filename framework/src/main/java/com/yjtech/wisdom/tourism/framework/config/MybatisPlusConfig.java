package com.yjtech.wisdom.tourism.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.infrastructure.constant.EntityConstants;
import com.yjtech.wisdom.tourism.infrastructure.constant.ProfileConstants;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.mybatis.extension.MySqlInjector;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

/** MybatisPlus配置 */
@Slf4j
@Configuration
@EnableTransactionManagement
@MapperScan({"com.yjtech.wisdom.tourism.**.mapper"})
public class MybatisPlusConfig {

  private final static String CREATETIME_FIELD = "createTime";
  private final static String CREATEUSER_FIELD = "createUser";
  private final static String UPDATETIME_FIELD = "updateTime";
  private final static String UPDATEUSER_FIELD = "updateUser";
  private final static String DELETED_FIELD = "deleted";
  private final static String STATUS_FIELD = "status";

  /** 公共字段填充 */
  @Bean
  public MetaObjectHandler metaObjectHandler() {
    return new MetaObjectHandler() {

      @Override
      public void insertFill(MetaObject metaObject) {

        if (metaObject.hasSetter(CREATETIME_FIELD)) {
          Object createTime = getFieldValByName(CREATETIME_FIELD, metaObject);
          if (createTime == null) {
            Class<?> fieldType = getFieldType(metaObject, CREATETIME_FIELD);
            if (fieldType.isAssignableFrom(LocalDateTime.class)) {
              setFieldValByName(CREATETIME_FIELD, LocalDateTime.now(), metaObject);
            } else if (fieldType.isAssignableFrom(LocalDate.class)) {
              setFieldValByName(CREATETIME_FIELD, LocalDate.now(), metaObject);
            } else {
              setFieldValByName(CREATETIME_FIELD, new Date(), metaObject);
            }
          }
        }

        if (metaObject.hasSetter(CREATEUSER_FIELD)) {
          Object createUser = getFieldValByName(CREATEUSER_FIELD, metaObject);
          if (createUser == null) {
            setFieldValByName(CREATEUSER_FIELD, getCurUserId(), metaObject);
          }
        }

        if (metaObject.hasSetter(DELETED_FIELD)) {
          Object deleted = getFieldValByName(DELETED_FIELD, metaObject);
          if (deleted == null) {
            setFieldValByName(DELETED_FIELD, EntityConstants.NOT_DELETED, metaObject);
          }
        }
        if (metaObject.hasSetter(STATUS_FIELD)) {
          Object status = getFieldValByName(STATUS_FIELD, metaObject);
          if (status == null) {
            setFieldValByName(STATUS_FIELD, EntityConstants.ENABLED, metaObject);
          }
        }

      }

      @Override
      public void updateFill(MetaObject metaObject) {

        if (metaObject.hasSetter(UPDATETIME_FIELD)) {
          Class<?> fieldType = getFieldType(metaObject, UPDATETIME_FIELD);
          if (fieldType.isAssignableFrom(LocalDateTime.class)) {
            setFieldValByName(UPDATETIME_FIELD, LocalDateTime.now(), metaObject);
          } else if (fieldType.isAssignableFrom(LocalDate.class)) {
            setFieldValByName(UPDATETIME_FIELD, LocalDate.now(), metaObject);
          } else {
            setFieldValByName(UPDATETIME_FIELD, new Date(), metaObject);
          }
        }

        if (metaObject.hasSetter(UPDATEUSER_FIELD)) {
          Object updateUser = getFieldValByName(UPDATEUSER_FIELD, metaObject);
          if (updateUser == null) {
            setFieldValByName(UPDATEUSER_FIELD, getCurUserId(), metaObject);
          }
        }

      }
    };
  }



  private Class<?> getFieldType(MetaObject metaObject, String name) {
    if (metaObject.hasSetter(name) && metaObject.hasGetter(name)) {
      return metaObject.getSetterType(name);
    } else if (metaObject.hasGetter("et")) {
      Object et = metaObject.getValue("et");
      if (et != null) {
        MetaObject etMeta = SystemMetaObject.forObject(et);
        if (etMeta.hasSetter(name)) {
          return etMeta.getSetterType(name);
        }
      }
    }
    return Object.class;
  }

  /** 分页插件 */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }

  /**
   * 打印sql
   *
   * @return
   */
  @Bean
  @Profile({ProfileConstants.DEVELOPMENT, ProfileConstants.LOCAL})
  public PerformanceInterceptor performanceInterceptor() {
    PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
    Properties properties = new Properties();
    performanceInterceptor.setFormat(true);
    return performanceInterceptor;
  }

  /**
   * 3.1.1版本之后不再需要这一步
   *
   * @return
   */
  @Bean
  public ISqlInjector sqlInjector() {
    return new MySqlInjector();
  }

  private Long getCurUserId() {
    try {
      return SecurityUtils.getLoginUser().getUser().getUserId();
    } catch (Exception e) {
      return -1L;
    }
  }
}
