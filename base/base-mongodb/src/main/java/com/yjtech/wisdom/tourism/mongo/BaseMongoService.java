package com.yjtech.wisdom.tourism.mongo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanUtils;
import com.yjtech.wisdom.tourism.common.utils.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ExecutableRemoveOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/** @Description @Author Mujun~ @Date 2020-09-21 16:49 */
@NoRepositoryBean
@Slf4j
public abstract class BaseMongoService<T, ID> {
  @Autowired private MongoTemplate mongoTemplate;

  public static <T> Criteria getLikeCondition(T t) {
    return getWhereCondition(t, true, null);
  }

  public static <T> Criteria getIsCondition(T t) {
    return getWhereCondition(t, false, null);
  }

  public static <T> Criteria getIsCondition(T t, Criteria... extraCriterias) {
    return getWhereCondition(t, false, extraCriterias);
  }

  /**
   * @param t 条件对象
   * @param extraCriterias 额外的查询条件
   * @param isLike 是否模糊匹配
   * @param <T>
   * @return
   */
  public static <T> Criteria getWhereCondition(T t, boolean isLike, Criteria... extraCriterias) {
    Criteria queryCriteria = new Criteria();
    List<Criteria> criteriaList = Lists.newLinkedList();
    try {
      Map<String, Object> queryMap = BeanUtils.transBean2Map(t);
      Iterator<String> iterator = queryMap.keySet().iterator();
      while (iterator.hasNext()) {
        String key = iterator.next();
        Object val = queryMap.get(key);
        if (Objects.nonNull(val)) {
          if (isLike) {
            queryCriteria = Criteria.where(key).regex(".*?\\" + val + ".*");
          } else {
            queryCriteria = Criteria.where(key).is(val);
          }
        }
        criteriaList.add(queryCriteria);
      }
    } catch (Exception e) {
      log.error("转换失败:" + e);
    }
    if (Objects.nonNull(extraCriterias) && extraCriterias.length > 0) {
      queryCriteria.andOperator(extraCriterias);
    }
    return queryCriteria;
  }

  public List<T> list(T t) {
    return list(new Query(getIsCondition(t)));
  }

  public List<T> list(T t, Sort sort, Criteria... criterias) {
    return list(new Query(getIsCondition(t, criterias)).with(sort));
  }

  public List<T> list(T t, Sort sort) {
    return list(new Query(getIsCondition(t)).with(sort));
  }

  public List<T> list(Query query) {
    List<T> list = mongoTemplate.find(query, getTClass());
    return list;
  }

  public List<T> list(T t, Criteria... criterias) {
    Criteria criteria = getIsCondition(t);
    criteria.andOperator(criterias);
    Query query = new Query(criteria);
    List<T> list = mongoTemplate.find(query, getTClass());
    return list;
  }

  public <T> T save(T obj) {
    return mongoTemplate.save(obj);
  }

  /**
   * 新增
   *
   * @param t
   * @return
   */
  public T insert(T t) {
    return mongoTemplate.insert(t);
  }
  /**
   * 新增
   *
   * @param collection
   * @return
   */
  public Collection<T> insert(Collection<T> collection) {
    Class<T> tClass = getTClass();
    return mongoTemplate.insert(collection, tClass);
  }

  /**
   * 查询
   *
   * @param id
   * @return
   */
  public T findById(Long id) {
    Class<T> tClass = getTClass();
    return mongoTemplate.findById(id, tClass);
  }

  /**
   * 根据ID批量查询
   *
   * @param ids
   * @return
   */
  public List<T> findByIds(List<Long> ids) {
    Class<T> tClass = getTClass();
    String idFieldName = ReflectUtils.getFieldName(tClass, Id.class);
    Assert.notNull(idFieldName, "@Id not find");
    return mongoTemplate.find(Query.query(Criteria.where(idFieldName).in(ids)), tClass);
  }

  public Class<T> getTClass() {
    return (Class<T>)
        ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  /**
   * 强制更新
   *
   * @param t
   * @return
   */
  public UpdateResult updateById(T t) {
    ReflectUtils.FieldNameValue id = ReflectUtils.getFieldNameValue(t, Id.class);
    Update update = null;
    try {
      update = getUpdate(t);
    } catch (Exception e) {
      log.error("mongodb更新失败:" + e);
    }
    Assert.notNull(id, "@Id not find");
    return mongoTemplate.updateFirst(
        Query.query(Criteria.where(id.getFieldName()).is(id.getFieldValue())),
        update,
        t.getClass());
  }

  public Update getUpdate(T t)
      throws IllegalAccessException, IntrospectionException, InvocationTargetException {
    Update update = new Update();
    Map<String, Object> map = BeanUtils.transBean2Map(t);
    map.forEach(
        (k, v) -> {
          update.set(k, v);
        });
    return update;
  }

  /**
   * 完整匹配条件,物理删除
   *
   * @return
   */
  public <T> ExecutableRemoveOperation.ExecutableRemove<T> delete() {
    Class clazz = getTClass();
    return mongoTemplate.remove(clazz);
  }
  /**
   * 乐观锁更新
   *
   * @param v
   * @return
   */
  //  public UpdateResult updateByIdWithVersion(ID v) {
  //    ReflectUtils.FieldNameValue id = ReflectUtils.getFieldNameValue(v, Id.class);
  //    Assert.notNull(id, "@Id not find");
  //    ReflectUtils.FieldNameValue version = ReflectUtils.getFieldNameValue(v, MongoVersion.class);
  //    Assert.notNull(version, "@MongoVersion not find");
  //    Object fieldValue = Optional.ofNullable(version.getFieldValue()).orElse("0");
  //    Criteria criteria =
  // Criteria.where(id.getFieldName()).is(id.getFieldValue()).and(version.getFieldName()).is(fieldValue);
  //    // 版本号+1
  //    ReflectUtils.setFieldValue(v, version.getFieldName(),
  // (Integer.parseInt(fieldValue.toString()) + 1) + "");
  //    Update update = Update.fromDocument(Document.parse(JsonUtil.writeValueAsString(v)));
  //    update.set("_class", v.getClass().getName());
  //    return mongoTemplate.updateFirst(Query.query(criteria), update, v.getClass());
  //  }

  /**
   * 物理删除
   *
   * @param id
   * @return
   */
  public DeleteResult deleteById(ID id) {
    Class<T> tClass = getTClass();
    String idFieldName = ReflectUtils.getFieldName(tClass, Id.class);
    Assert.notNull(idFieldName, "@Id not find");
    return mongoTemplate.remove(Query.query(Criteria.where(idFieldName).is(id)), tClass);
  }
  /**
   * 完整匹配条件,物理删除
   *
   * @param t
   * @return
   */
  public DeleteResult delete(T t) {
    return mongoTemplate.remove(new Query(getIsCondition(t)), t.getClass());
  }

  /**
   * 根据查询条件查找列表
   *
   * @param criteria
   * @return
   */
  public List<T> find(Criteria criteria) {
    Class<T> tClass = getTClass();
    return mongoTemplate.find(Query.query(criteria), tClass);
  }
  public T findOne(Query query) {
    return mongoTemplate.findOne(query,getTClass());
  }

  /**
   * 根据查询条件查找列表
   *
   * @param query
   * @return
   */
  public List<T> find(Query query, Sort sort, Long skip, Integer limit) {
    Class<T> tClass = getTClass();
    if (Objects.nonNull(sort)) {
      query.with(sort);
    }
    if (Objects.nonNull(skip)) {
      query.skip(skip);
    }
    if (Objects.nonNull(limit)) {
      query.limit(limit);
    }
    return mongoTemplate.find(query, tClass);
  }

  /**
   * 根据查询条件查找条数
   *
   * @param query
   * @return
   */
  public long count(Query query) {
    Class<T> tClass = getTClass();
    return mongoTemplate.count(query, tClass);
  }

  /**
   * 根据查询条件分页查询
   *
   * @param query 查询条件
   * @param sort 排序条件
   * @param pageNow 查找的页数
   * @param pageSize 每页显示大小
   */
  public IPage<T> paging(Query query, Sort sort, long pageNow, int pageSize) {
    long totalRecord = this.count(query);
    long totalPage = totalRecord / pageSize + 1;
    long skip = pageSize * (pageNow - 1);
    List<T> records = this.find(query, sort, skip, pageSize);
    return new Page<T>()
        .setCurrent(pageNow)
        .setSize(pageSize)
        .setTotal(totalRecord)
        .setPages(totalPage)
        .setRecords(records);
  }

  /**
   * 更新第一条匹配到的
   *
   * @param criteria
   * @param update
   * @return
   */
  public UpdateResult updateFirst(Criteria criteria, Update update) {
    Class<T> tClass = getTClass();
    return mongoTemplate.updateFirst(Query.query(criteria), update, tClass);
  }

  /**
   * 更新所有匹配到的
   *
   * @param criteria
   * @param update
   * @return
   */
  public UpdateResult updateMulti(Criteria criteria, Update update) {
    Class<T> tClass = getTClass();
    return mongoTemplate.updateMulti(Query.query(criteria), update, tClass);
  }

  public List<HashMap> group(T t, String... groupKey) {
    // 聚合操作
    List<AggregationOperation> operations = new ArrayList<>();

    // 筛选条件
    operations.add(Aggregation.match(getIsCondition(t)));
    // 分组字段

    GroupOperation groupOperation = Aggregation.group(Fields.fields(groupKey));
    //    GroupOperation groupOperation1 = Aggregation.group(Fields.fields(groupKey)).
    // max("longtitude").as("maxLong").;

    // 添加选项  (聚合查询字段和添加筛选是有区别的注意)
    operations.add(groupOperation);

    // 最终聚合查询所有信息
    Aggregation aggregation = Aggregation.newAggregation(operations);

    // 查询结果
    AggregationResults<HashMap> results =
        mongoTemplate.aggregate(aggregation, t.getClass(), HashMap.class);
    // 获取结果
    List<HashMap> result = results.getMappedResults();
    return result;
  }
}
