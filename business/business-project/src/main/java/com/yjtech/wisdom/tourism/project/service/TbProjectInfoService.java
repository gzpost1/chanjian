package com.yjtech.wisdom.tourism.project.service;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.bean.index.DataStatisticsDTO;
import com.yjtech.wisdom.tourism.common.bean.index.DataStatisticsQueryVO;
import com.yjtech.wisdom.tourism.common.bean.project.ProjectDataStatisticsQueryVO;
import com.yjtech.wisdom.tourism.common.enums.ImportInfoTypeEnum;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.ImportTemplateUtils;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.file.FileDownloadUtils;
import com.yjtech.wisdom.tourism.common.utils.file.FileUtils;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.project.dto.ProjectQuery;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelRelationEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectResourceEntity;
import com.yjtech.wisdom.tourism.project.mapper.TbProjectInfoMapper;
import com.yjtech.wisdom.tourism.project.vo.InvestmentTotalVo;
import com.yjtech.wisdom.tourism.project.vo.ProjectAmountVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TbProjectInfoService extends ServiceImpl<TbProjectInfoMapper, TbProjectInfoEntity> {

    @Autowired
    private TbProjectResourceService projectResourceService;
    @Autowired
    private TbProjectLabelRelationService tbProjectLabelRelationService;
    @Autowired
    private TbProjectInfoMapper tbProjectInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        this.removeById(id);

        LambdaUpdateWrapper<TbProjectResourceEntity> wrapper = new LambdaUpdateWrapper<TbProjectResourceEntity>();
        wrapper.eq(TbProjectResourceEntity::getProjectId, id);
        boolean result = projectResourceService.remove(wrapper);

        //同步删除该项目的标签关联
        if (result) {
            tbProjectLabelRelationService.remove(new LambdaQueryWrapper<TbProjectLabelRelationEntity>().eq(TbProjectLabelRelationEntity::getProjectId, id));
        }
    }


    /**
     * 查询列表
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public List<TbProjectInfoEntity> queryForList(ProjectQuery params) {
        List<TbProjectInfoEntity> list = baseMapper.queryForList(null, params);
        //构建已选中项目标签id列表
        if (CollectionUtils.isNotEmpty(list)) {
            for (TbProjectInfoEntity entity : list) {
                entity.setPitchOnLabelIdList(tbProjectLabelRelationService.queryForLabelIdListByProjectId(entity.getId()));
                entity.setResource(Optional.ofNullable(projectResourceService.list(new LambdaQueryWrapper<TbProjectResourceEntity>().eq(TbProjectResourceEntity::getProjectId, entity.getId())))
                        .orElse(new ArrayList<>()));
            }
        }
        return list;
    }

    /**
     * 查询分页
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<TbProjectInfoEntity> queryForPage(ProjectQuery params) {
        Page<TbProjectInfoEntity> page = new Page<>(params.getPageNo(), params.getPageSize());
        List<TbProjectInfoEntity> records = baseMapper.queryForList(page, params);
        //构建已选中项目标签id列表
        if (CollectionUtils.isNotEmpty(records)) {
            for (TbProjectInfoEntity entity : records) {
                entity.setPitchOnLabelIdList(tbProjectLabelRelationService.queryForLabelIdListByProjectId(entity.getId()));
            }
            page.setRecords(records);
        }
        page.setRecords(records);
        return page;
    }

    /**
     * 大屏-数据统计-平台项目累计总数
     *
     * @Param:
     * @return:
     */
    public List<BaseValueVO> queryProjectNumTrend() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime beginTime = endTime.minusMonths(11)
                .with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        //上线时间为2022-05-01，仅展示2022年5月以后的月份及对应数据
        LocalDateTime beginTime1 = LocalDate.of(2022, 5, 1).atStartOfDay();
        if (beginTime.isBefore(beginTime1)) {
            beginTime = beginTime1;
        }
        List<BaseVO> vos = baseMapper.queryProjectNumTrend(beginTime, endTime);

        List<String> nameList = Lists.newLinkedList();
        List<String> valueList = Lists.newLinkedList();
        Map<String, BaseVO> map = vos.stream().collect(Collectors.toMap(BaseVO::getName, e -> e));

        long sum = 0; //项目总数
        while (!endTime.isBefore(beginTime)) {
            int i = beginTime.getMonthValue();
            String month = Convert.numberToChinese(i, false);
            nameList.add((i < 10 ? month : StringUtils.substring(month, 1)) + "月");
            sum += map.containsKey(i + "") ? Long.parseLong(map.get(i + "").getValue()) : 0;
            valueList.add(sum + "");
            beginTime = beginTime.plusMonths(1);
        }
        List<BaseValueVO> list = Lists.newArrayList();
        list.add(BaseValueVO.builder().name("quantity").value(valueList).build());
        list.add(BaseValueVO.builder().name("coordinate").value(nameList).build());
        return list;
    }

    /**
     * 大屏-数据统计-月度总投资额与引资金额需求趋势
     *
     * @Param:
     * @return:
     */
    public List<BaseValueVO> queryProjectAmountTrend() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime beginTime = endTime.minusMonths(11)
                .with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        //上线时间为2022-05-01，仅展示2022年5月以后的月份及对应数据
        LocalDateTime beginTime1 = LocalDate.of(2022, 5, 1).atStartOfDay();
        if (beginTime.isBefore(beginTime1)) {
            beginTime = beginTime1;
        }
        List<ProjectAmountVo> vos = baseMapper.queryProjectAmountTrend(beginTime, endTime);

        List<String> nameList = Lists.newLinkedList();
        List<String> investmentTotalList = Lists.newLinkedList();
        List<String> fundingAmountList = Lists.newLinkedList();
        Map<String, ProjectAmountVo> map = vos.stream().collect(Collectors.toMap(ProjectAmountVo::getName, e -> e));
        //累加 总投资额
        BigDecimal investmentTotal = BigDecimal.ZERO;
        //累加 引资金额
        BigDecimal fundingAmount = BigDecimal.ZERO;

        while (!endTime.isBefore(beginTime)) {
            int i = beginTime.getMonthValue();
            String month = Convert.numberToChinese(i, false);
            nameList.add((i < 10 ? month : StringUtils.substring(month, 1)) + "月");
            //当月总投资额
            String currentInvestmentTotal = map.containsKey(i + "") ? map.get(i + "").getInvestmentTotal() : "0";
            //当月引资金额
            String currentFundingAmount = map.containsKey(i + "") ? map.get(i + "").getFundingAmount() : "0";
            //累加金额
            investmentTotal = investmentTotal.add(new BigDecimal(currentInvestmentTotal));
            fundingAmount = fundingAmount.add(new BigDecimal(currentFundingAmount));

            investmentTotalList.add(investmentTotal.toString());
            fundingAmountList.add(fundingAmount.toString());
            beginTime = beginTime.plusMonths(1);
        }
        List<BaseValueVO> list = Lists.newArrayList();
        list.add(BaseValueVO.builder().name("investmentTotalQuantity").value(investmentTotalList).build());
        list.add(BaseValueVO.builder().name("fundingAmountQuantity").value(fundingAmountList).build());
        list.add(BaseValueVO.builder().name("coordinate").value(nameList).build());
        return list;
    }

    /**
     * 查询公司绑定的发布项目
     *
     * @param id
     * @return
     */
    public int findBingProject(Long id) {
        return tbProjectInfoMapper.selectCount(new LambdaQueryWrapper<TbProjectInfoEntity>().eq(TbProjectInfoEntity::getCompanyId, String.valueOf(id))
                .eq(TbProjectInfoEntity::getStatus, Byte.valueOf("2"))
                .eq(TbProjectInfoEntity::getDeleted, Byte.valueOf("0")));
    }

    /**
     * 根据企业id查询项目id列表
     *
     * @param companyId
     * @return
     */
    public List<Long> queryIdListByCompanyId(Long companyId) {
        return baseMapper.queryIdListByCompanyId(companyId);
    }


    /**
     * 下载项目信息，PPT,视频（压缩包）
     */
    public void download(Long projectId, HttpServletRequest request, HttpServletResponse response) {
        TbProjectInfoEntity entity = Optional.ofNullable(baseMapper.selectById(projectId))
                .orElseThrow(() -> new CustomException("项目不存在"));
        //获取项目资源（下载ppt,视频等资源）
        List<String> list = getResourceDownloadPath(projectId);
        //获取项目信息Excel
        String excelPath = builcExcel(entity);
        list.add(excelPath);
        String zipFilePath = FileDownloadUtils.RESOURCE_LOCATION + entity.getProjectName() + ".zip";
        try {
            FileDownloadUtils.generateZip(zipFilePath, list);
            File file = new File(zipFilePath);
            if (!file.exists()) {
                return;
            }
            response.setCharacterEncoding(CharEncoding.UTF_8);
            response.setContentType("application/force-download");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, entity.getProjectName() + ".zip"));
            InputStream fileInputStream = new FileInputStream(file);
            FileUtils.writeBytes(fileInputStream, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            File file = new File(zipFilePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 获取项目资源，ppt,mp4等本地资源路径
     *
     * @param projectId
     * @return
     */
    private List<String> getResourceDownloadPath(Long projectId) {
        List<String> list = new ArrayList<>();

        List<TbProjectResourceEntity> tbProjectResourceEntities = Optional.ofNullable(projectResourceService.list(new LambdaQueryWrapper<TbProjectResourceEntity>().eq(TbProjectResourceEntity::getProjectId, projectId)))
                .orElse(new ArrayList<>());
        for (TbProjectResourceEntity tbProjectResourceEntity : tbProjectResourceEntities) {
            if (tbProjectResourceEntity.getResourceUrl() != null) {
                String[] a = tbProjectResourceEntity.getResourceUrl().split("\\.");
                String x = a[a.length - 1];
                String downloadPath = FileDownloadUtils.RESOURCE_LOCATION + tbProjectResourceEntity.getName() + "." + x;
                boolean b = FileDownloadUtils.downVideo(tbProjectResourceEntity.getResourceUrl(), downloadPath);
                if (b) {
                    list.add(downloadPath);
                    log.info("已下载成功地址路径: {}", downloadPath);
                }
            }
        }

        return list;
    }

    /**
     * 根据模板写入项目信息
     *
     * @param entity
     * @return
     */
    private String builcExcel(TbProjectInfoEntity entity) {
        File file = createNewFile(ImportTemplateUtils.RESOURCE_LOCATION + ImportInfoTypeEnum.PROJECT.getDescribe(), FileDownloadUtils.RESOURCE_LOCATION + entity.getProjectName() + ".xlsx");
        InputStream is = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        try {
            is = new FileInputStream(file);
            workbook = new XSSFWorkbook(is);
            //获取第一个sheet
            sheet = workbook.getSheetAt(0);
            sheet.getRow(3).getCell(1).setCellValue(entity.getProjectName());
            sheet.getRow(4).getCell(1).setCellValue(entity.getConstructionRequirement());
            sheet.getRow(5).getCell(1).setCellValue(entity.getCooperationMethod());
            sheet.getRow(7).getCell(1).setCellValue(entity.getAreaName());
            sheet.getRow(8).getCell(1).setCellValue(entity.getAddress());
            sheet.getRow(9).getCell(1).setCellValue(entity.getConstructionCondition());
            sheet.getRow(10).getCell(1).setCellValue(entity.getIndustrialCondition());
            sheet.getRow(11).getCell(1).setCellValue(entity.getMarketOutlookForecast());
            sheet.getRow(12).getCell(1).setCellValue(entity.getProjectPlanFootprint());
            sheet.getRow(13).getCell(1).setCellValue(entity.getProjectBuildArea());
            sheet.getRow(14).getCell(1).setCellValue(entity.getProjectMap());
            sheet.getRow(16).getCell(1).setCellValue(entity.getProjectInvestmentContent());
            sheet.getRow(17).getCell(1).setCellValue(entity.getInvestmentTotal());
            sheet.getRow(18).getCell(1).setCellValue(entity.getFundingAmount());
            sheet.getRow(19).getCell(1).setCellValue(entity.getPrivateCapital());
            sheet.getRow(20).getCell(1).setCellValue(entity.getPaybackPeriod());
            sheet.getRow(22).getCell(1).setCellValue(entity.getServiceUnitName());
            sheet.getRow(23).getCell(1).setCellValue(entity.getProjectServiceConcat());
            sheet.getRow(24).getCell(1).setCellValue(entity.getProjectServiceLandline());
            sheet.getRow(25).getCell(1).setCellValue(entity.getProjectServicePhone());
            sheet.getRow(27).getCell(0).setCellValue(entity.getSupportDesc());
            workbook.write(new FileOutputStream(file));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return FileDownloadUtils.RESOURCE_LOCATION + entity.getProjectName() + ".xlsx";
    }


    /**
     * 读取excel模板，并复制到新文件中供写入和下载
     *
     * @return
     */
    public File createNewFile(String oldFileName, String newFileName) {
        //读取模板，并赋值到新文件************************************************************
        //文件模板路径
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(oldFileName);
        //判断路径是否存在
        File dir = new File(FileDownloadUtils.RESOURCE_LOCATION);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //写入到新的excel
        File newFile = new File(newFileName);
        try {
            newFile.createNewFile();
            //复制模板到新文件
            FileDownloadUtils.fileChannelCopy(resourceAsStream, newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 查询数据统计
     *
     * @return
     */
    @Transactional(readOnly = true)
    public DataStatisticsDTO queryDataStatistics() {
        DataStatisticsDTO dto = baseMapper.queryDataStatisticsByDuration(new DataStatisticsQueryVO(null));
        dto.setTotalNum(baseMapper.selectCount(new LambdaQueryWrapper<>()));

        return dto;
    }

    /**
     * 查询趋势
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<BaseVO> queryAnalysis(DataStatisticsQueryVO vo) {
        return baseMapper.queryAnalysis(vo);
    }

    /**
     * 查询浏览数趋势
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<BaseVO> queryViewNumAnalysis(ProjectDataStatisticsQueryVO vo) {
        return baseMapper.queryViewNumAnalysis(vo);
    }

    /**
     * 获取比较投资额 本月与上月比
     * 单位：万元
     *
     * @return
     */
    public Long getCompareMoney() {
        InvestmentTotalVo vo = new InvestmentTotalVo();
        vo.setBeginTime(DateTimeUtil.getCurrentMonthFirstDayStr());
        vo.setEndTime(DateTimeUtil.getCurrentMonthLastDayStr());
        Long currentMoney = baseMapper.getInvestmentTotal(vo);
        currentMoney = currentMoney == null ? 0 : currentMoney;

        vo.setBeginTime(DateTimeUtil.getCurrentLastMonthFirstDayStr());
        vo.setEndTime(DateTimeUtil.getCurrentLastMonthLastDayStr());
        Long lastMonthMoney = baseMapper.getInvestmentTotal(vo);
        lastMonthMoney = lastMonthMoney == null ? 0 : lastMonthMoney;

        return currentMoney - lastMonthMoney;
    }

    /**
     * 大屏-底部-注册公司、投资项目、规划项目占地统计
     *
     * @return
     */
    public List<BaseVO> queryDataStatic() {
        return baseMapper.queryDataStatic();
    }

    public IPage<TbProjectInfoEntity> customPage(ProjectQuery query) {
        return baseMapper.customPage(new Page<>(query.getPageNo(), query.getPageSize()), query);
    }

    public IPage<TbProjectInfoEntity> auditPage(ProjectQuery query) {
        Long userId = SecurityUtils.getUserId();
        return baseMapper.auditPage(new Page<>(query.getPageNo(), query.getPageSize()), query, userId);
    }

    public List<TbProjectInfoEntity> queryRecommendProject(ProjectQuery query) {
        return baseMapper.queryRecommendProject(query);
    }
}
