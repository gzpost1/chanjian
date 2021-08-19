package com.yjtech.wisdom.tourism.common.utils;

import java.util.Random;

/**
 * 随机数工具
 *
 * @author renguangqian
 * @date 2021/8/13 9:23
 */
public class RandomUtils {

    /**
     * 获取一个随机数 指定范围
     *
     * @param min
     * @param max
     * @return
     */
    public static Integer getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min + 1) + min);
    }
}
