package com.yjtech.wisdom.tourism.mybatis.extension;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.additional.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.additional.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.additional.LogicDeleteByIdWithFill;
import com.yjtech.wisdom.tourism.mybatis.extension.methods.additional.InsertBatchIgnoreInto;
import com.yjtech.wisdom.tourism.mybatis.extension.methods.additional.ReplaceBatchInto;

import java.util.List;

/** 自定义Sql注入 Created by wuyongchong on 2020/1/2. */
public class MySqlInjector extends DefaultSqlInjector {

  @Override
  public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
    List<AbstractMethod> methodList = super.getMethodList(mapperClass);

    methodList.add(new InsertBatchSomeColumn(i -> i.getFieldFill() != FieldFill.UPDATE));
    methodList.add(new AlwaysUpdateSomeColumnById(i -> i.getFieldFill() != FieldFill.INSERT));
    methodList.add(new LogicDeleteByIdWithFill());
    methodList.add(new InsertBatchIgnoreInto());
    methodList.add(new ReplaceBatchInto());

    return methodList;
  }
}
