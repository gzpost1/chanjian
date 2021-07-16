package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.infrastructure.core.page.PageDomain;
import com.yjtech.wisdom.tourism.infrastructure.core.page.TableSupport;
import com.yjtech.wisdom.tourism.infrastructure.utils.DictUtils;
import com.yjtech.wisdom.tourism.system.domain.SysConfig;
import com.yjtech.wisdom.tourism.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author liuhong
 */
@Service
public class SysDictDataService {
  @Autowired private SysDictDataMapper dictDataMapper;

  /**
   * 根据条件分页查询字典数据
   *
   * @param dictData 字典数据信息
   * @return 字典数据集合信息
   */
  public IPage<SysDictData> selectDictDataList(SysDictData dictData) {
    IPage<SysDictData> pageDomain = TableSupport.buildIPageRequest();
    return dictDataMapper.selectDictDataList(pageDomain, dictData);
  }

  /**
   * 根据字典类型和字典键值查询字典数据信息
   *
   * @param dictType 字典类型
   * @param dictValue 字典键值
   * @return 字典标签
   */
  public String selectDictLabel(String dictType, String dictValue) {
    return dictDataMapper.selectDictLabel(dictType, dictValue);
  }

  /**
   * 根据字典数据ID查询信息
   *
   * @param dictCode 字典数据ID
   * @return 字典数据
   */
  public SysDictData selectDictDataById(Long dictCode) {
    return dictDataMapper.selectDictDataById(dictCode);
  }

  /**
   * 批量删除字典数据信息
   *
   * @param dictCodes 需要删除的字典数据ID
   * @return 结果
   */
  public int deleteDictDataByIds(Long[] dictCodes) {
    int row = dictDataMapper.deleteDictDataByIds(dictCodes);
    if (row > 0) {
      DictUtils.clearDictCache();
    }
    return row;
  }

  /**
   * 新增保存字典数据信息
   *
   * @param dictData 字典数据信息
   * @return 结果
   */
  public int insertDictData(SysDictData dictData) {
    // 判断键值是否存在
    if (dictDataValueExists(dictData.getDictType(), dictData.getDictValue())) {
      throw new CustomException(ErrorCode.DICT_VALUE_EXISTED);
    }
    int row = dictDataMapper.insertDictData(dictData);
    if (row > 0) {
      DictUtils.clearDictCache();
    }
    return row;
  }

  /**
   * 修改保存字典数据信息
   *
   * @param dictData 字典数据信息
   * @return 结果
   */
  public int updateDictData(SysDictData dictData) {
    int row = dictDataMapper.updateDictData(dictData);
    if (row > 0) {
      DictUtils.clearDictCache();
    }
    return row;
  }

  private boolean dictDataValueExists(String type, String value) {
    String dictLabel = selectDictLabel(type, value);
    return StringUtils.isNotEmpty(dictLabel);
  }
}
