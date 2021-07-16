package com.yjtech.wisdom.tourism.systemconfig.simulation.enums;

import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;

import java.util.Objects;

/**
 * 模拟数据类型烦心
 * 1票务 2停车场 3wifi 4一码游 5智慧厕所 6口碑 7事件
 */
public enum SimulationEnum {
    TICKET(1,  SimulationConstants.TICKET, "票务"),
    DEPORT(2, SimulationConstants.DEPORT, "停车场"),
    WIFI(3, SimulationConstants.WIFI, "wifi"),
    ONETRAVEL(4, SimulationConstants.ONETRAVEL, "一码游"),
    TOILET(5,  SimulationConstants.TOILET, "智慧厕所"),
    PRAISE(6,  SimulationConstants.PRAISE, "口碑"),
    EVENT(7, SimulationConstants.EVENT, "事件");

    SimulationEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
        this.desc = name;
    }

    SimulationEnum(Integer type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
    }

    public static String getSimulationNameByType(Integer type) {
        String name = null;

        if (Objects.isNull(type)) {
            return name;
        }

        for (SimulationEnum value : SimulationEnum.values()) {
            if (value.type.equals(type)) {
                name = value.name;
                break;
            }
        }
        return name;
    }

    /**
     * 类型
     */
    private Integer type;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
