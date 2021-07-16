package com.yjtech.wisdom.tourism.common.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import sun.net.util.IPAddressUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * @author liuhong
 */
@Slf4j
public class CommonUtil {

    public static String getObjectString(Object o) {
        return isNull(o) ? "" : o.toString();
    }

    public static Long getObjectLong(Object o) {
        return isNull(o) ? null : Long.parseLong(o.toString());
    }

    public static Byte getObjectByte(Object o) {
        return isNull(o) ? null : Byte.parseByte(o.toString());
    }

    /**
     * convert byte array to HEX string
     *
     * @param data
     * @return
     */
    public static String toHex(byte[] data) {
        return toHex(data, false);
    }

    /**
     * Convert byte array to Hex code
     *
     * @param data
     * @param isFormat format output string: 16 byte one line, one byte is separted by space
     * @return
     */
    public static String toHex(byte[] data, boolean isFormat) {
        if (data == null || data.length == 0)
            return "";

        int tmp;
        int s1, s2;
        StringBuilder sb = new StringBuilder(data.length * 2 + (isFormat ? data.length + data.length / 16 : 0));
        for (int i = 0; i < data.length; i++) {
            tmp = data[i] & 0x000000FF;
            s1 = tmp / 16;
            s2 = tmp % 16;
            if (s1 < 10)
                sb.append((char) (s1 + 48));
            else if (s1 >= 10)
                sb.append((char) (s1 + 55));
            if (s2 < 10)
                sb.append((char) (s2 + 48));
            else if (s2 >= 10)
                sb.append((char) (s2 + 55));

            if (isFormat) {
                sb.append(" ");

                if ((i + 1) % 16 == 0)
                    sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Convert Hex string to byte array
     * hex string format is: 2 char represent one byte,can use space as separator
     * for example, "12 1e 3d ee FF 09"
     *
     * @param hex
     * @return
     */
    public static byte[] toBytes(String hex) {
        byte[] buff = new byte[hex.length() / 2];

        int s1, s2, count = 0;
        for (int i = 0; i < hex.length(); i++) {
            if (hex.charAt(i) >= '0' && hex.charAt(i) <= '9')
                s1 = hex.charAt(i) - 48;
            else if (hex.charAt(i) >= 'A' && hex.charAt(i) <= 'F')
                s1 = hex.charAt(i) - 55;
            else if (hex.charAt(i) >= 'a' && hex.charAt(i) <= 'f')
                s1 = hex.charAt(i) - 87;
            else
                continue;

            i++;
            if (hex.charAt(i) >= '0' && hex.charAt(i) <= '9')
                s2 = hex.charAt(i) - 48;
            else if (hex.charAt(i) >= 'A' && hex.charAt(i) <= 'F')
                s2 = hex.charAt(i) - 55;
            else if (hex.charAt(i) >= 'a' && hex.charAt(i) <= 'f')
                s2 = hex.charAt(i) - 87;
            else
                continue;

            buff[count] = ((byte) (s1 * 16 + s2));
            count++;
        }
        byte[] result = new byte[count];
        System.arraycopy(buff, 0, result, 0, result.length);
        return result;
    }

    public static byte[] textToNumericFormatV4(String ip) {
        if (StringUtils.isEmpty(ip)) {
            throw new IllegalArgumentException("the argument ip can't be empty.");
        }

        if (!IPAddressUtil.isIPv4LiteralAddress(ip)) {
            throw new IllegalArgumentException("wrong format of ipv4 address.");
        }

        return IPAddressUtil.textToNumericFormatV4(ip);
    }

    /**
     * 将列表切分为多个子列表
     *
     * @param list
     * @param num
     * @param <E>
     * @return
     */
    public static <E> List<List<E>> splitList(final List<E> list, int num) {
        if (list == null || list.isEmpty() || num <= 0) {
            return Lists.newArrayList();
        }

        int size = list.size();
        List<List<E>> resultList = Lists.newArrayList();
        List<E> result = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            if (i % num == 0) {
                result = Lists.newArrayList();
                resultList.add(result);
            }
            result.add(list.get(i));
        }

        return resultList;
    }

    /**
     * 实体转map对象
     */
    public static Map<String, Object> toMap(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        Map<String, Object> map = new HashMap<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                String name = fields[i].getName();
                fields[i].setAccessible(true);
                Object value = null;
                value = fields[i].get(object);
                MapIgnore mapIgnore = fields[i].getAnnotation(MapIgnore.class);
                if (mapIgnore != null) {
                    continue;
                }
                if (value instanceof ArrayList) {
                    value = JSON.toJSONString(value);
                }
                if (value != null && (value + "").length() > 0) {
                    map.put(name, value);
                }
            }
        }
        return map;
    }

    /**
     *      * Map转成实体对象
     *      * @param map map实体对象包含属性
     *      * @param clazz 实体对象类型
     *      * @return
     *      
     */
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) throws IllegalAccessException,
            InstantiationException {
        if (map == null) {
            return null;
        }
        Object obj = null;
        obj = clazz.newInstance();
        List<Field> fields = new ArrayList();
        Class<?> clazzCur = clazz;
        while (clazzCur != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazzCur = clazzCur.getSuperclass(); //得到父类,然后赋给自己
        }
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return (T)obj;
    }

    /**
     * 随机数生成
     */
    public static String getNoceStr(int length) {
        //1.  定义一个字符串（A-Z，a-z，0-9）即62个数字字母；
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        //2.  由Random生成随机数
        Random random = new Random();

        StringBuffer sb = new StringBuffer();
        //3.  长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //从62个的数字或字母中选择
            int number = random.nextInt(str.length());
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }
    public static URI createURI(String url) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        return uriBuilder.build().encode().toUri();
    }
    /**
     * 获取目录下的文件
     *
     * @param realpath
     * @param realpath
     * @return
     */
    public static List<File> getFiles(String realpath) {
        List<File> files = new ArrayList<File>();
        File realFile = new File(realpath);
        // 判断为文件夹
        if (realFile.isDirectory()) {
            // 获取文件list
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                // 遍历判断为文件
                if (file.isFile()) {
                    files.add(file);
                    continue;
                }
                if(file.isDirectory()){
                    log.info("目录："+file.getPath());
                    List<File> tempFiles = getFiles(file.getPath());
                    files.addAll(tempFiles);
                }
            }
        }
        return files;
    }
    /**
     * 获取目录下包含关键字名字的文件
     *
     * @param realpath
     * @param realpath
     * @return
     */
    public static List<File> getFiles(String realpath,String fileName) {
          Pattern pattern =  Pattern.compile(fileName);
        List<File> files = new ArrayList<File>();
        File realFile = new File(realpath);
        // 判断为文件夹
        if (realFile.isDirectory()) {
            // 获取文件list
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                // 遍历判断为文件
                if (file.isFile() &&  pattern.matcher(file.getName()).find()) {
                    files.add(file);
                    continue;
                }
                if(file.isDirectory()){
                    log.info("目录："+file.getPath());
                    List<File> tempFiles = getFiles(file.getPath(),fileName);
                    files.addAll(tempFiles);
                }
            }
        }
        return files;
    }
    /**
     * 删除文件名含有关键字的文件
     *
     * @param realpath
     * @param nameReg
     * @return
     */
    public static boolean deleteFilesMatchName(String realpath, String nameReg) {
        List<File> fileList = CommonUtil.getFiles(realpath);
        // 正则表达式 
        final String fileName = ".*" + nameReg + ".*";
        for (int i = 0; i < fileList.size(); i++) {
            // 是否匹配
            boolean result = Pattern.compile(fileName)
                    .matcher(fileList.get(i).getName()).find();
            // 保留匹配
            if (result == true) {
                fileList.get(i).delete();
            }
        }
        return true;
    }

}
