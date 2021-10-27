package com.yjtech.wisdom.tourism.resource.video.bo;

import com.yjtech.wisdom.tourism.common.annotation.Excel;
import lombok.Data;

@Data
public class VideoGuideBo {

    @Excel(name = "展示序号\n（必填）",
            notNull = true,
            regex = "^[\\d]{0,3}$",
            regexMsg = "允许输入整数范围：0-999")
    private String sort;

    @Excel(name = "设备编号\n（必填，不能重复）",
            notNull = true,
            regex = "^.{1,128}$",
            regexMsg = "允许输入长度必须小于128")
    private String deviceId;

    @Excel(name = "设备名称\n（必填）",
            notNull = true,
            regex = "^[\\u4E00-\\u9FA5A-Za-z0-9_]{1,30}$",
            regexMsg = "允许输入长度必须小于30")
    private String name;

    @Excel(name = "所在位置",
            regex = "^.{1,100}$",
            regexMsg = "允许输入长度必须小于100")
    private String address;

    @Excel(name = "经度",
            regex = "^[\\-\\+]?(0(\\.\\d{1,6})?|([1-9](\\d)?)(\\.\\d{1,6})?|1[0-7]\\d{1}(\\.\\d{1,6})?|180\\.0{1,6})$",
            regexMsg = "格式不符合要求")
    private String longitude;

    @Excel(name = "纬度",
            regex = "^[\\-\\+]?((0|([1-8]\\d?))(\\.\\d{1,6})?|90(\\.0{1,6})?)$",
            regexMsg = "格式不符合要求")
    private String latitude;

    @Excel(name = "视频流地址\n（必填）",
            notNull = true,
            regex = "^.{1,256}$",
            regexMsg = "允许输入长度必须小于256")
    private String url;
}
