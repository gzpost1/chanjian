package com.yjtech.wisdom.tourism.common.utils.file;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * description: FileDownloadUtils
 * date: 2022/7/15 16:03
 * author: chenzhongge
 */
@Slf4j
public class FileDownloadUtils {

    /**
     * 资源地址
     */
//    public static final String RESOURCE_LOCATION = "/Users/chenzhongge/Downloads/zip/";
    public static final String RESOURCE_LOCATION = "/home/temp/";



    /**
     * 下载视频
     * @param videoUrl 视频网络地址
     * @param downloadPath  视频保存地址
     */
    public static boolean downVideo(String videoUrl, String downloadPath) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        RandomAccessFile randomAccessFile = null;
        boolean re;
        try {

            URL url = new URL(videoUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Range", "bytes=0-");
            connection.connect();
            if (connection.getResponseCode() / 100 != 2) {
                System.out.println("连接失败...");
                return false;
            }
            inputStream = connection.getInputStream();
            int downloaded = 0;
            int fileSize = connection.getContentLength();
            randomAccessFile = new RandomAccessFile(downloadPath, "rw");
            while (downloaded < fileSize) {
                byte[] buffer = null;
                if (fileSize - downloaded >= 1000000) {
                    buffer = new byte[1000000];
                } else {
                    buffer = new byte[fileSize - downloaded];
                }
                int read = -1;
                int currentDownload = 0;
                long startTime = System.currentTimeMillis();
                while (currentDownload < buffer.length) {
                    read = inputStream.read();
                    buffer[currentDownload++] = (byte) read;
                }
                long endTime = System.currentTimeMillis();
                double speed = 0.0;
                if (endTime - startTime > 0) {
                    speed = currentDownload / 1024.0 / ((double) (endTime - startTime) / 1000);
                }
                randomAccessFile.write(buffer);
                downloaded += currentDownload;
                randomAccessFile.seek(downloaded);
                log.info(downloadPath+"下载了进度:%.2f%%,下载速度：%.1fkb/s(%.1fM/s)%n", downloaded * 1.0 / fileSize * 10000 / 100,
                        speed, speed / 1000);
                System.out.printf(downloadPath+"下载了进度:%.2f%%,下载速度：%.1fkb/s(%.1fM/s)%n", downloaded * 1.0 / fileSize * 10000 / 100,
                        speed, speed / 1000);
            }
            re = true;
            return re;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            re = false;
            return re;
        } catch (IOException e) {
            e.printStackTrace();
            re = false;
            return re;
        } finally {
            try {
                connection.disconnect();
                inputStream.close();
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }


    /**
     * 压缩打包
     * @param path
     * @param files
     * @throws Exception
     */
    public static void generateZip(String path, List<String> files) throws Exception {
        ZipOutputStream out = null;
        try {
            byte[] buffer = new byte[1024];
            out = new ZipOutputStream(new FileOutputStream(path));
            for (String filePath : files) {
                File file = new File(filePath);
                FileInputStream fis = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(file.getName()));
                int len;
                //读入需要下载的文件的内容，打包到zip文件
                while ((len = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.closeEntry();
                fis.close();
            }
            for(String fileString : files) {
                File file = new File(fileString);
                if(file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * 复制文件
     *
     * @param s
     * 源文件
     * @param t
     * 复制到的新文件
     */

    public static void fileChannelCopy(InputStream s, File t) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(s,1024);
                out = new BufferedOutputStream(new FileOutputStream(t),1024);
                byte[] buffer = new byte[1024];
                int len;
                while ((len=in.read(buffer))!=-1) {
                    out.write(buffer,0,len);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
