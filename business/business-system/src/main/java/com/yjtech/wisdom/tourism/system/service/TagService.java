package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.system.domain.TagEntity;
import com.yjtech.wisdom.tourism.system.mapper.TagMapper;
import com.yjtech.wisdom.tourism.system.vo.TagQueryVO;
import com.yjtech.wisdom.tourism.system.vo.TagUpdateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 标签管理(TbTag)表服务实现类
 *
 * @author horadirm
 * @since 2022-03-11 09:59:08
 */
@Service
public class TagService extends ServiceImpl<TagMapper, TagEntity> {


    /**
     * 初始化信息
     */
    @PostConstruct
    public void init(){
        //构建信息
        List<TagEntity> entityList = Lists.newArrayList(
                new TagEntity().toBuilder().id(IdWorker.getInstance().nextId()).enterpriseRole("投资方").build(),
                new TagEntity().toBuilder().id(IdWorker.getInstance().nextId()).enterpriseRole("业态方").build(),
                new TagEntity().toBuilder().id(IdWorker.getInstance().nextId()).enterpriseRole("运营方").build()
        );
        baseMapper.insertUpdateBatch(entityList);
    }

    /**
     * 编辑
     * @param vo
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(TagUpdateVO vo){
        //获取信息
        TagEntity entity = baseMapper.selectById(vo.getId());
        if(null == entity){
            throw new CustomException(ErrorCode.NOT_FOUND, "编辑失败：标签信息不存在");
        }
        entity.buildUpdate(vo);

        baseMapper.updateById(entity);
    }

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<TagEntity> queryForList(TagQueryVO vo){
        return baseMapper.queryForList(null, vo);
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public Page<TagEntity> queryForPage(TagQueryVO vo){
        Page<TagEntity> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        page.setRecords(baseMapper.queryForList(page, vo));

        return page;
    }

    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public TagEntity queryById(Long id){
        return baseMapper.queryById(id);
    }

}