package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.constant.UserConstants;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictType;
import com.yjtech.wisdom.tourism.infrastructure.core.page.TableSupport;
import com.yjtech.wisdom.tourism.infrastructure.utils.DictUtils;
import com.yjtech.wisdom.tourism.system.mapper.SysDictDataMapper;
import com.yjtech.wisdom.tourism.system.mapper.SysDictTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author liuhong
 */
//@DependsOn("FlywayConfig")
@Service
public class SysDictTypeService {
  @Autowired private SysDictTypeMapper dictTypeMapper;

  @Autowired private SysDictDataMapper dictDataMapper;

  /** 项目启动时，初始化字典到缓存 */
//  @PostConstruct
  public void init() {
    List<SysDictType> dictTypeList = dictTypeMapper.selectDictTypeAll();
    for (SysDictType dictType : dictTypeList) {
      List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType.getDictType());
      DictUtils.setDictCache(dictType.getDictType(), dictDatas);
    }
  }

  /**
   * 根据条件分页查询字典类型
   *
   * @param dictType 字典类型信息
   * @return 字典类型集合信息
   */
  public IPage<SysDictType> selectDictTypeList(SysDictType dictType) {
    IPage<SysDictType> pageDomain = TableSupport.buildIPageRequest();
    return dictTypeMapper.selectDictTypeList(pageDomain, dictType);
  }

  /**
   * 根据所有字典类型
   *
   * @return 字典类型集合信息
   */
  public List<SysDictType> selectDictTypeAll() {
    return dictTypeMapper.selectDictTypeAll();
  }

  /**
   * 根据字典类型查询字典数据
   *
   * @param dictType 字典类型
   * @return 字典数据集合信息
   */
  public List<SysDictData> selectDictDataByType(String dictType) {
    List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
    if (StringUtils.isNotNull(dictDatas)) {
      return dictDatas;
    }
    dictDatas = dictDataMapper.selectDictDataByType(dictType);
    if (StringUtils.isNotNull(dictDatas)) {
      DictUtils.setDictCache(dictType, dictDatas);
      return dictDatas;
    }
    return null;
  }

  /**
   * 根据字典类型ID查询信息
   *
   * @param dictId 字典类型ID
   * @return 字典类型
   */
  public SysDictType selectDictTypeById(Long dictId) {
    return dictTypeMapper.selectDictTypeById(dictId);
  }

  /**
   * 根据字典类型查询信息
   *
   * @param dictType 字典类型
   * @return 字典类型
   */
  public SysDictType selectDictTypeByType(String dictType) {
    return dictTypeMapper.selectDictTypeByType(dictType);
  }

  /**
   * 批量删除字典类型信息
   *
   * @param dictIds 需要删除的字典ID
   * @return 结果
   */
  public int deleteDictTypeByIds(Long[] dictIds) {
    for (Long dictId : dictIds) {
      SysDictType dictType = selectDictTypeById(dictId);
      dictDataMapper.deleteDictDataByType(dictType.getDictType());
//      if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
//        throw new CustomException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
//      }
    }
    int count = dictTypeMapper.deleteDictTypeByIds(dictIds);
    if (count > 0) {
      DictUtils.clearDictCache();
      init();
    }
    return count;
  }

  /** 清空缓存数据 */
  public void clearCache() {
    DictUtils.clearDictCache();
    init();
  }

  /**
   * 新增保存字典类型信息
   *
   * @param dictType 字典类型信息
   * @return 结果
   */
  public int insertDictType(SysDictType dictType) {
    int row = dictTypeMapper.insertDictType(dictType);
    if (row > 0) {
      DictUtils.clearDictCache();
      init();
    }
    return row;
  }

  /**
   * 修改保存字典类型信息
   *
   * @param dictType 字典类型信息
   * @return 结果
   */
  @Transactional
  public int updateDictType(SysDictType dictType) {
    SysDictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
    dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
    int row = dictTypeMapper.updateDictType(dictType);
    if (row > 0) {
      DictUtils.clearDictCache();
      init();
    }
    return row;
  }

  /**
   * 校验字典类型称是否唯一
   *
   * @param dict 字典类型
   * @return 结果
   */
  public String checkDictTypeUnique(SysDictType dict) {
    Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
    SysDictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
    if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }
}
