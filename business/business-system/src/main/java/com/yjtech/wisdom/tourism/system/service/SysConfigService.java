package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.annotation.DataSource;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.UserConstants;
import com.yjtech.wisdom.tourism.common.core.text.Convert;
import com.yjtech.wisdom.tourism.common.enums.DataSourceType;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.page.PageDomain;
import com.yjtech.wisdom.tourism.infrastructure.core.page.TableSupport;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.system.domain.SysConfig;
import com.yjtech.wisdom.tourism.system.mapper.SysConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * 参数配置 服务层实现
 *
 * @author liuhong
 */
//@DependsOn("FlywayConfig")
@Service
public class SysConfigService {
  @Autowired private SysConfigMapper configMapper;

  @Autowired private RedisCache redisCache;

  /** 项目启动时，初始化参数到缓存 */
//  @PostConstruct
  public void init() {
    IPage<SysConfig> configsList =
        configMapper.selectConfigList(new Page<>(1, Integer.MAX_VALUE), new SysConfig());
    for (SysConfig config : configsList.getRecords()) {
      redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
    }
  }

  /**
   * 查询参数配置信息
   *
   * @param configId 参数配置ID
   * @return 参数配置信息
   */
  @DataSource(DataSourceType.MASTER)
  public SysConfig selectConfigById(Long configId) {
    SysConfig config = new SysConfig();
    config.setConfigId(configId);
    return configMapper.selectConfig(config);
  }

  /**
   * 根据键名查询参数配置信息
   *
   * @param configKey 参数key
   * @return 参数键值
   */
  public String selectConfigByKey(String configKey) {
    String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
    if (StringUtils.isNotEmpty(configValue)) {
      return configValue;
    }
    SysConfig config = new SysConfig();
    config.setConfigKey(configKey);
    SysConfig retConfig = configMapper.selectConfig(config);
    if (StringUtils.isNotNull(retConfig)) {
      redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
      return retConfig.getConfigValue();
    }
    return StringUtils.EMPTY;
  }

  /**
   * 查询参数配置列表
   *
   * @param config 参数配置信息
   * @return 参数配置集合
   */
  public IPage<SysConfig> selectConfigList(SysConfig config) {
    IPage<SysConfig> pageDomain = TableSupport.buildIPageRequest();
    return configMapper.selectConfigList(pageDomain, config);
  }

  /**
   * 新增参数配置
   *
   * @param config 参数配置信息
   * @return 结果
   */
  public int insertConfig(SysConfig config) {
    int row = configMapper.insertConfig(config);
    if (row > 0) {
      redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
    }
    return row;
  }

  /**
   * 修改参数配置
   *
   * @param config 参数配置信息
   * @return 结果
   */
  public int updateConfig(SysConfig config) {
    int row = configMapper.updateConfig(config);
    if (row > 0) {
      redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
    }
    return row;
  }

  /**
   * 批量删除参数信息
   *
   * @param configIds 需要删除的参数ID
   * @return 结果
   */
  public int deleteConfigByIds(Long[] configIds) {
    int count = configMapper.deleteConfigByIds(configIds);
    if (count > 0) {
      Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
      redisCache.deleteObject(keys);
    }
    return count;
  }

  /** 清空缓存数据 */
  public void clearCache() {
    Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
    redisCache.deleteObject(keys);
  }

  /**
   * 校验参数键名是否唯一
   *
   * @param config 参数配置信息
   * @return 结果
   */
  public String checkConfigKeyUnique(SysConfig config) {
    Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
    SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
    if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 设置cache key
   *
   * @param configKey 参数键
   * @return 缓存键key
   */
  private String getCacheKey(String configKey) {
    return Constants.SYS_CONFIG_KEY + configKey;
  }
}
