package com.yjtech.wisdom.tourism.wechat.wechat.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.wechat.wechat.vo.CodeTemplateVO;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.infrastructure.constant.EntityConstants;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.CodeTemplate;
import com.yjtech.wisdom.tourism.wechat.wechat.mapper.CodeTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by wuyongchong on 2019/11/8.
 */
@Service
public class CodeTemplateService extends ServiceImpl<CodeTemplateMapper, CodeTemplate> {

  @Autowired
  private CodeTemplateMapper codeTemplateMapper;

  public List<CodeTemplateVO> getCodeTemplateList() {

    List<CodeTemplate> codeTemplates = codeTemplateMapper.selectList(
        new QueryWrapper<CodeTemplate>().eq("status", EntityConstants.ENABLED));

    return Optional.ofNullable(codeTemplates).orElse(Lists.newArrayList()).stream().map(item -> {
      return CodeTemplateVO.builder()
          .templateId(item.getId())
          .templateName(item.getTemplateName())
          .userVersion(item.getUserVersion()).userDesc(item.getUserDesc()).build();
    }).collect(Collectors.toList());
  }

  public CodeTemplate getCodeTemplateByTemplateId(Long templateId) {
    List<CodeTemplate> codeTemplates = codeTemplateMapper
        .selectList(new QueryWrapper<CodeTemplate>().eq("template_id", templateId));
    return CollectionUtils.isEmpty(codeTemplates) ? null : codeTemplates.get(0);
  }

}
