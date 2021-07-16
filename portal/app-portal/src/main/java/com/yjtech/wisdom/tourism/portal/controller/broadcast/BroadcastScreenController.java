package com.yjtech.wisdom.tourism.portal.controller.broadcast;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.CommonLogicDeleteObj;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastGroupEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastMediaLibraryEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastPlayEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.dto.BroadcastGroupSaveDto;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.dto.BroadcastGroupUpdateDto;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.dto.BroadcastPlayCreateDto;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.dto.BroadcastPlayUpdateDto;
import com.yjtech.wisdom.tourism.resource.broadcast.query.*;
import com.yjtech.wisdom.tourism.resource.broadcast.service.BroadcastGroupService;
import com.yjtech.wisdom.tourism.resource.broadcast.service.BroadcastMediaLibraryService;
import com.yjtech.wisdom.tourism.resource.broadcast.service.BroadcastPlayService;
import com.yjtech.wisdom.tourism.resource.broadcast.service.BroadcastService;
import com.yjtech.wisdom.tourism.system.domain.IconSpotEnum;
import com.yjtech.wisdom.tourism.system.service.IconService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.yjtech.wisdom.tourism.common.core.domain.JsonResult.success;
import static com.yjtech.wisdom.tourism.common.utils.CommonPreconditions.checkCollectionEmpty;
import static com.yjtech.wisdom.tourism.common.utils.CommonPreconditions.checkStringEmpty;
import static java.util.Objects.isNull;

/**
 * 前端大屏-广播
 * @author zc
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/broadcast/screen")
public class BroadcastScreenController {

    @Autowired
    private BroadcastGroupService groupService;
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private BroadcastPlayService playService;
    @Autowired
    private IconService iconService;
    @Autowired
    private BroadcastMediaLibraryService mediaLibraryService;

    /**
     * 查询广播列表
     * @Param:  query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/queryForPage")
    public JsonResult<IPage<BroadcastEntity>> queryForPage(@RequestBody @Valid BroadcastQuery query) {
        return success(broadcastService.queryForPage(query));
    }

    /**
     * 点位
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/queryForList")
    public JsonResult<List<BroadcastEntity>> queryForList() {
        List<BroadcastEntity> list = broadcastService.list();
        if(CollectionUtils.isEmpty(list)){
            return success();
        }
        list.forEach(item ->{
            item.setIconUrl(iconService.queryIconUrl(IconSpotEnum.BROADCAST,item.getEquipStatus().toString()));
        });
        return success(list);
    }

    /**
     * 设置终端音量
     * @Param:  query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/setTermVol")
    public JsonResult<?> setTermVol(@RequestBody TermVolSetQuery query) {
        return success(null);
    }

    /**
     * 媒体库
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/queryMediaLib")
    public JsonResult<IPage<BroadcastMediaLibraryEntity>> queryMediaLib(@RequestBody BroadcastMediaLibQuery query) {
        LambdaQueryWrapper<BroadcastMediaLibraryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(query.getName()), BroadcastMediaLibraryEntity::getName, query.getName());
        return success(mediaLibraryService.page(new Page<>(query.getPageNo(), query.getPageSize()), wrapper));
    }

    /**
     * 根据类型查询终端 (话筒)
     * @Param:  query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/broadcastMicrophoneList")
    public JsonResult<IPage<BroadcastEntity>> queryBroadcastMicrophones(@RequestBody BroadcastMicrophoneQuery query) {
        return success(broadcastService.queryBroadcastMicrophones(query));
    }

    /**
     * 根据类型查询终端 (音响)
     * @Param:  query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/broadcastSoundList")
    public JsonResult<IPage<BroadcastEntity>> queryBroadcastSoundList(@RequestBody BroadcastSoundQuery query) {
        IPage<BroadcastEntity> iPage = broadcastService.getBaseMapper().queryBroadcastSounds(
                new Page(query.getPageNo(), query.getPageSize()), query);
        return success(iPage);
    }

    /**
     * 根据type查询广播 （类型, 0-实时采播, 1-文件广播，2-文本播放）
     * @Param: query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/playFileDetail")
    public JsonResult queryPlayFileDetail(@RequestBody EquipmentTypeQuery query) {
        return success(playService.queryPlay(query.getType()));
    }

    /**
     * 开启（文件）广播
     * @Param:  query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/startFilePlay")
    public JsonResult<?> startFilePlay(@RequestBody @Valid PlayStartQuery query) {
        if (isNull(query.getId())) {
            throw new CustomException(ErrorCode.PARAM_MISS, "文件播放id不能为空");
        }
        return success(null);
    }

    /**
     * 开启（文本）广播
     * @Param:  query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/startTextPlay")
    public JsonResult<?> textPlay(@RequestBody @Valid PlayStartQuery query) {
        if (isNull(query.getId())) {
            throw new CustomException(ErrorCode.PARAM_MISS, "文本播放id不能为空");
        }
        return success(null);
    }

    /**
     * 开启（实时）广播
     * @Param:  query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/startRealPlay")
    public JsonResult<?> startRealPlay(@RequestBody @Valid PlayStartQuery query) {
        if (isNull(query.getId())) {
            throw new CustomException(ErrorCode.PARAM_MISS, "实时广播id不能为空");
        }
        return success(null);
    }

    /**
     * 停止广播
     * @Param:  query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/stopTask")
    public JsonResult<?> stopTask(@RequestBody TaskQuery query) {
        return success(null);
    }

    /**
     * 查询正在执行的任务
     * @Param:  query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/executingTask")
    public JsonResult<?> executingTask(@RequestBody TaskQuery query) {
        return success(null);
    }

    /**
     * 创建（文件/文本/实时）播放
     * @Param:  createDto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/createFilePlay")
    public JsonResult<?> createPlay(@RequestBody @Valid BroadcastPlayCreateDto createDto) {
        if (createDto.getType().equals((byte) 0) && StringUtils.isNull(createDto.getMicrophoneId())) {
            throw new CustomException(ErrorCode.PARAM_MISS, "话筒不能为空");
        }
        if (createDto.getType().equals((byte) 1) && checkStringEmpty(createDto.getMusicIds())) {
            throw new CustomException(ErrorCode.PARAM_MISS, "文件不能为空");
        }
        if (createDto.getType().equals((byte) 2)) {
            if (checkStringEmpty(createDto.getText())) {
                throw new CustomException(ErrorCode.PARAM_MISS, "广播内容不能为空");
            }
            if (StringUtils.isNull(createDto.getRepeatTime())) {
                throw new CustomException(ErrorCode.PARAM_MISS, "播放次数不能为空");
            } else {
                try {
                    Integer.parseInt(createDto.getRepeatTime());
                } catch (Exception e) {
                    throw new CustomException(ErrorCode.PARAM_INVALID, "播放次数必须是正整数");
                }
            }
        }
        BroadcastPlayEntity entity = BeanMapper.map(createDto, BroadcastPlayEntity.class);
        playService.save(entity);
        return JsonResult.ok();
    }

    /**
     * 修改（文件/文本/实时）播放
     */
    @PostMapping(value = "/updateFilePlay")
    public JsonResult<?> updateFilePlay(@RequestBody @Valid BroadcastPlayUpdateDto updateDto) {
        if (updateDto.getType().equals((byte) 0) && StringUtils.isNull(updateDto.getMicrophoneId())) {
            throw new CustomException(ErrorCode.PARAM_MISS, "话筒不能为空");
        }
        if (updateDto.getType().equals((byte) 1) && checkStringEmpty(updateDto.getMusicIds())) {
            throw new CustomException(ErrorCode.PARAM_MISS, "文件不能为空");
        }
        if (updateDto.getType().equals((byte) 2)) {
            if (checkStringEmpty(updateDto.getText())) {
                throw new CustomException(ErrorCode.PARAM_MISS, "广播内容不能为空");
            }
            if (StringUtils.isNull(updateDto.getRepeatTime())) {
                throw new CustomException(ErrorCode.PARAM_MISS, "播放次数不能为空");
            } else {
                try {
                    Integer.parseInt(updateDto.getRepeatTime());
                } catch (Exception e) {
                    throw new CustomException(ErrorCode.PARAM_INVALID, "播放次数必须是正整数");
                }
            }
        }
        playService.updateBroadcast(updateDto);
        return JsonResult.ok();
    }

    /**
     * 删除（文件/文本/实时）播放
     *
     * @Param: idParam
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/deleteFilePlay")
    public JsonResult delete(@RequestBody @Valid IdParam idParam) {
        playService.removeById(idParam.getId());
        return JsonResult.ok();
    }

    /**
     * 查询广播分组信息
     *
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/queryGroupList")
    public JsonResult<List<BroadcastGroupEntity>> queryGroupList() {

        return success(groupService.queryGroupList());
    }

    /**
     * 新建广播分组
     *
     * @Param: dto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/createGroup")
    public JsonResult<?> createGroup(@RequestBody @Valid BroadcastGroupSaveDto dto) {
        groupService.createGroup(dto);
        return success(null);
    }

    /**
     * 编辑广播分组信息
     *
     * @Param: dto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/modifyGroup")
    public JsonResult<?> modifyGroup(@RequestBody @Valid BroadcastGroupUpdateDto dto) {
        if (isNull(dto.getGroupId())) {
            throw new CustomException(ErrorCode.PARAM_MISS, "分组id不能为空");
        }
        groupService.modifyGroup(dto);
        return success(null);
    }

    /**
     * 删除广播分组
     *
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping(value = "/deleteGroup")
    public JsonResult<?> deleteGroup(@RequestBody CommonLogicDeleteObj logicDeleteObj) {
        if (checkCollectionEmpty(logicDeleteObj.getIds())) {
            throw new CustomException(ErrorCode.PARAM_MISS, "分组id不能为空");
        }
        groupService.deleteGroup(logicDeleteObj);
        return success(null);
    }
}