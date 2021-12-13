package com.yjtech.wisdom.tourism.systemconfig.menu.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonArray;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.validator.BeanValidationGroup;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JacksonTypeHandler;
import com.yjtech.wisdom.tourism.mybatis.typehandler.ListObjectJsonTypeHandler;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuChartDetailDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuPointDetalDto;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * H5页面配置
 *
 * @author MJ~
 * @since 2021-11-08
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "tb_systemconfig_h5_menu",autoResultMap = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TbSystemconfigH5MenuEntity extends Model<TbSystemconfigH5MenuEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不能为空",groups = BeanValidationGroup.Update.class)
    private Long id;

    /**
     * 页面名称
     */
    @NotNull(message = "页面名称不能为空",groups = {BeanValidationGroup.Update.class,BeanValidationGroup.Create.class})
    private String name;

    /**
     * 页面类型
     */
    @NotNull(message = "页面类型不能为空",groups ={BeanValidationGroup.Update.class,BeanValidationGroup.Create.class})
    private String menuType;

    /**
     * 是否显示日期筛选 0否 1是
     */
    @NotNull(message = "是否显示日期筛选不能为空",groups = {BeanValidationGroup.Update.class,BeanValidationGroup.Create.class})
    private Byte isShowdate;

    /**
     * sort_num 展示序号
     */
    private Integer sortNum;

    /**
     * img_url 大屏缩略图
     */
    private String imgUrl;

    /**
     * is_show 是否展示(0:否,1:是)
     */
    private Byte isShow;

    /**
     * is_simulation 是否启用模拟数据(0:否,1:是)
     */
    @NotNull(message = "是否启用模拟数据不能为空",groups = {BeanValidationGroup.Update.class,BeanValidationGroup.Create.class})
    private Byte isSimulation;

    /**
     * has_backdrop 是否启用背景板(0:否,1:是)
     */
    private Byte hasBackdrop;

    /**
     * point_data 点位展示数据
     */
    @TableField(value = "point_data", typeHandler = JacksonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private JSONArray pointData;

    /**
     * chart_data 图表数据
     */
    @TableField(value = "chart_data", typeHandler = JacksonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private JSONArray chartData;

    /**
     * create_time 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * update_time 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * create_user 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * update_user 更新人id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * deleted 是否删除：0-否,1-是
     */
    @TableLogic
    @Builder.Default
    @TableField(fill = FieldFill.INSERT)
    private Byte deleted = EntityConstants.NOT_DELETED;

    /**
     * 路由地址
     */
    @NotNull(message = "路由地址不能为空",groups = {BeanValidationGroup.Update.class,BeanValidationGroup.Create.class})
    private String routePath;

    /**
     * 1大 2小
     */
    private Byte mapsizeType;

    /**
     * 0否 1是
     */
    @TableField("iS_showreturn")
    private Byte isShowreturn;


    /**
     * 页面类型名称
     */
    @TableField(exist = false)
    private String tempName;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String MENU_TYPE = "menu_type";

    public static final String IS_SHOWDATE = "is_showdate";

    public static final String SORT_NUM = "sort_num";

    public static final String IMG_URL = "img_url";

    public static final String IS_SHOW = "is_show";

    public static final String IS_SIMULATION = "is_simulation";

    public static final String HAS_BACKDROP = "has_backdrop";

    public static final String POINT_DATA = "point_data";

    public static final String CHART_DATA = "chart_data";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_USER = "create_user";

    public static final String UPDATE_USER = "update_user";

    public static final String DELETED = "deleted";

    public static final String ROUTE_PATH = "route_path";

    public static final String MAPSIZE_TYPE = "mapsize_type";

    public static final String IS_SHOWRETURN = "iS_showreturn";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
