package com.yjtech.wisdom.tourism.resource.wifi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.resource.wifi.dto.WifiPageQueryDto;
import com.yjtech.wisdom.tourism.resource.wifi.entity.WifiEntity;
import com.yjtech.wisdom.tourism.resource.wifi.entity.WifiTemporaryEntity;
import com.yjtech.wisdom.tourism.resource.wifi.mapper.WifiMapper;
import com.yjtech.wisdom.tourism.resource.wifi.vo.WifiHotVo;
import com.yjtech.wisdom.tourism.resource.wifi.vo.WifiStatisticsVo;
import com.yjtech.wisdom.tourism.resource.wifi.vo.WifiVo;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.yjtech.wisdom.tourism.common.utils.StringUtils.isNull;

@Service
public class WifiService extends ServiceImpl<WifiMapper, WifiEntity> {

    @Autowired
    private WifiTemporaryService temporaryService;

    @Autowired
    private SysConfigService configService;

    private final static String WIFI_NUM_KEY = "wifi_num";

    public IPage<WifiVo> queryForPage(WifiPageQueryDto query) {
        return baseMapper.queryForPage(new Page(query.getPageNo(), query.getPageSize()), query);
    }

    /** 
     * wifi综合统计（首页）
     * @Param:  
     * @return:
     * @Author: zc
     * @Date: 2021-07-15 
     */
    public WifiStatisticsVo queryWifiStatistics(){

        WifiTemporaryEntity temporaryEntity = temporaryService.getOne(
                new LambdaQueryWrapper<WifiTemporaryEntity>().eq(WifiTemporaryEntity::getType, 1));

        //获取wifi的设备最大数
        int pageSize = 10;
        IPage<WifiHotVo> page = queryHotWifi(new PageQuery());
        List<WifiHotVo> wifiHotVos = new ArrayList();
        wifiHotVos.addAll(page.getRecords());
        //获取当前连接数
        int currentConnectNum = isNull(temporaryEntity) || isNull(temporaryEntity.getValue()) ? 0 : temporaryEntity.getValue();
        //所有设备数求和
        int total = wifiHotVos.stream().mapToInt(WifiHotVo::getConnectTotal).sum();
        //连接占比
        double connectRate = isNull(total) || total == 0 ?
                0D : MathUtil.divide(BigDecimal.valueOf(currentConnectNum), BigDecimal.valueOf(total), 3).doubleValue();

        List<BaseValueVO> valueVOS = new ArrayList<>();
        List<Integer> abscissa = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            abscissa.add(i + 1);
            try {
                values.add(wifiHotVos.get(i).getCurrentConnect());
            } catch (Exception e) {
                values.add(0);
            }
        }
        //添加横坐标
        valueVOS.add(BaseValueVO.builder().name("coordinate").value(abscissa).build());
        //添加纵坐标对应值
        valueVOS.add(BaseValueVO.builder().name("quantity").value(values).build());

        return WifiStatisticsVo.builder()
                .currentConnectNum(currentConnectNum)
                .connectRate(connectRate)
                .notConnectRate(1 - connectRate)
                .top10List(valueVOS)
                .build();
    }

    /**
     * 更新状态
     *
     * @param updateStatusParam
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(UpdateStatusParam updateStatusParam) {
        WifiEntity entity = this.getById(updateStatusParam.getId());
        entity.setStatus(updateStatusParam.getStatus());
        this.updateById(entity);
    }

    /**
     * 详情
     *
     * @param
     */
    public WifiVo queryForDetail(Long id) {
        WifiEntity byId = Optional.ofNullable(this.getById(id)).orElse(new WifiEntity());
        WifiVo wifiVo = new WifiVo();
        BeanUtils.copyProperties(byId, wifiVo);
        return wifiVo;
    }

    /**
     * 设备编号是否唯一
     * @param deviceId
     * @param id
     * @return
     */
    public boolean driveExist(String deviceId, Long id) {
        LambdaQueryWrapper<WifiEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WifiEntity::getDeviceId, deviceId);
        if(Objects.nonNull(id)){
            queryWrapper.ne(WifiEntity::getId, id);
        }
        int count = Optional.ofNullable(this.count(queryWrapper)).orElse(0);
        return count > 0 ? false : true;
    }

    /**
     * 热门点位
     * @param
     * @param
     * @return
     */
    public IPage<WifiHotVo> queryHotWifi(PageQuery query){
        IPage<WifiHotVo> iPage = baseMapper.queryHotWifi(new Page<>(query.getPageNo(), query.getPageSize()), query);
        if(CollectionUtils.isEmpty(iPage.getRecords())){
            return new Page();
        }
        //从配置信息获取wifi设备数,如果为空则设置为0
        String value = configService.selectConfigByKey(WIFI_NUM_KEY);
        Integer maxNum = StringUtils.isNotBlank(value) ? Integer.parseInt(value) : 0;
        iPage.getRecords().forEach(item ->{
            item.setConnectTotal(isNull(item.getConnectTotal()) ? maxNum : item.getConnectTotal());
        });
        return iPage;
    }
}
