package com.yjtech.wisdom.tourism.common.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/** @Description @Author Mujun~ @Date 2020-10-10 9:58 */
public class GpsUtils {
  public static String getDmsFun(double data) {
    int degree = (int) (data / 360000);
    int minute = (int) ((data - degree * 360000) / 6000);
    int second = (int) ((data - degree * 360000 - minute * 6000) / 100);
    return String.valueOf(degree)
        + "°"
        + String.valueOf(minute)
        + "′"
        + String.valueOf(second)
        + "″";
  }
  public static BigDecimal transFormGps(double data) {
      double gps = data/360000;
    return new BigDecimal(gps).setScale(6,BigDecimal.ROUND_HALF_UP);
  }

  public static BigDecimal getGps(double data) {
    double degree = data / 360000;
    double minute = (data - degree * 360000) / 6000;
    double second = (data - degree * 360000 - minute * 6000) / 100;
    double gps = degree + (minute / 60) + second / (3600);
//    int degree = (int) (data / 360000);
//    int minute = (int) ((data - degree * 360000) / 6000);
//    int second = (int) ((data - degree * 360000 - minute * 6000) / 100);
//    double gps = degree + (minute / 60) + second / (3600);
    return new BigDecimal(gps);
  }
  /**
   * 经纬度转换 ，度分秒转度
   * @author Cai_YF
   * @return
   */
  public static String Dms2D(String jwd){
    if(StringUtils.isNotEmpty(jwd)&&(jwd.contains("°"))){//如果不为空并且存在度单位
      //计算前进行数据处理
      jwd = jwd.replace("E", "").replace("N", "").replace(":", "").replace("：", "");
      double d=0,m=0,s=0;
      d = Double.parseDouble(jwd.split("°")[0]);
      //不同单位的分，可扩展
      if(jwd.contains("′")){//正常的′
        m = Double.parseDouble(jwd.split("°")[1].split("′")[0]);
      }else if(jwd.contains("'")){//特殊的'
        m = Double.parseDouble(jwd.split("°")[1].split("'")[0]);
      }
      //不同单位的秒，可扩展
      if(jwd.contains("″")){//正常的″
        //有时候没有分 如：112°10.25″
        s = jwd.contains("′")?Double.parseDouble(jwd.split("′")[1].split("″")[0]):Double.parseDouble(jwd.split("°")[1].split("″")[0]);
      }else if(jwd.contains("''")){//特殊的''
        //有时候没有分 如：112°10.25''
        s = jwd.contains("'")?Double.parseDouble(jwd.split("'")[1].split("''")[0]):Double.parseDouble(jwd.split("°")[1].split("''")[0]);
      }
      jwd = String.valueOf(d+m/60+s/60/60);//计算并转换为string
      //使用BigDecimal进行加减乘除
	/*BigDecimal bd = new BigDecimal("60");
	BigDecimal d = new BigDecimal(jwd.contains("°")?jwd.split("°")[0]:"0");
	BigDecimal m = new BigDecimal(jwd.contains("′")?jwd.split("°")[1].split("′")[0]:"0");
	BigDecimal s = new BigDecimal(jwd.contains("″")?jwd.split("′")[1].split("″")[0]:"0");
	//divide相除可能会报错（无限循环小数），要设置保留小数点
	jwd = String.valueOf(d.add(m.divide(bd,6,BigDecimal.ROUND_HALF_UP)
            .add(s.divide(bd.multiply(bd),6,BigDecimal.ROUND_HALF_UP))));*/
    }
    return jwd;
  }

  public static void main(String[] args) {
      String date = "2020-10-09T15:41:53.000+08:00";
      ZonedDateTime zdt = ZonedDateTime.parse(date);
      LocalDateTime ldt = zdt.toLocalDateTime();

      System.out.println(ldt);

      System.out.println("latitude:" + GpsUtils.transFormGps(10511272));
      System.out.println("longitude:" + GpsUtils.transFormGps(41568590));
      System.out.println("latitude:" + GpsUtils.getGps(10511272));
      System.out.println("longitude:" + GpsUtils.getGps(41568590));
      System.out.println("latitude:" + GpsUtils.Dms2D("29°11′52″"));
      System.out.println("longitude:" + GpsUtils.Dms2D("115°28′5″"));
  }
}
