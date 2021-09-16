package com.yjtech.wisdom.tourism.mybatis.typehandler;

import com.yjtech.wisdom.tourism.common.config.EncryptConfig;
import com.yjtech.wisdom.tourism.common.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

/**
 * @author oujiangping
 */
@Slf4j
public class EncryptTypeHandler extends BaseTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, AES.encrypt((String)parameter));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return AES.decrypt(columnValue);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        return AES.decrypt(columnValue);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return AES.decrypt(columnValue);
    }

    public static class AES {

        private static String ENCRYPT_MODE = "ECB";

        /**
         * AES加密
         *
         * @param plaintext 明文
         * @return 该字符串的AES密文值
         */
        public static String encrypt(String plaintext) {
            if(StringUtils.isBlank(plaintext)){
                return plaintext;
            }
            EncryptConfig encryptConfig = SpringContextHolder.getBean(EncryptConfig.class);
            String KEY = encryptConfig.getKey();
            try {
                /*byte[] raw = KEY.getBytes("utf-8");
                SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
                Cipher cipher = Cipher.getInstance("AES/" + ENCRYPT_MODE + "/PKCS5Padding");
                if (ENCRYPT_MODE == "ECB") {
                    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
                } else {
                    //使用CBC模式，需要一个向量iv，可增加加密算法的强度
                    IvParameterSpec iv = new IvParameterSpec(KEY.getBytes("utf-8"));
                    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                }
                byte[] encrypted = cipher.doFinal(plaintext.getBytes("utf-8"));
                String encryptedStr = Base64.toBase64String(encrypted);
                return encryptedStr;*/


               /* byte[] keyBytes = KEY.getBytes("UTF-8");
                SecretKey key = new SecretKeySpec(keyBytes, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] cleartext = plaintext.getBytes("UTF-8");
                byte[] ciphertextBytes = cipher.doFinal(cleartext);
                return new String(Hex.encodeHex(ciphertextBytes));*/

                final Cipher encryptCipher = Cipher.getInstance("AES");
                encryptCipher.init(Cipher.ENCRYPT_MODE, generateMySQLAESKey(KEY, "UTF-8"));
                byte[] bytes = encryptCipher.doFinal(plaintext.getBytes("UTF-8"));
                return Base64.getEncoder().encodeToString(bytes);

            } catch (Exception ex) {
                log.error(ex.toString());
                log.error("加密字段失败 " + plaintext);
                return null;
            }
        }

        public static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) {
            try {
                final byte[] finalKey = new byte[16];
                int i = 0;
                for(byte b : key.getBytes(encoding)) {
                    finalKey[i++ % 16] ^= b;
                }
                return new SecretKeySpec(finalKey, "AES");
            } catch(UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * AES解密
         *
         * @param cipertext 密文
         * @return 该密文的明文
         */
        public static String decrypt(String cipertext) {
            if(StringUtils.isBlank(cipertext)){
                return cipertext;
            }
            try {
                EncryptConfig encryptConfig = SpringContextHolder.getBean(EncryptConfig.class);
                String KEY = encryptConfig.getKey();
                /*byte[] raw = KEY.getBytes("utf-8");
                SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
                Cipher cipher = Cipher.getInstance("AES/" + ENCRYPT_MODE + "/PKCS5Padding");
                if (ENCRYPT_MODE == "ECB") {
                    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
                } else {
                    IvParameterSpec iv = new IvParameterSpec(KEY.getBytes("utf-8"));
                    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                }
                byte[] encrypted1 = Base64.decode(cipertext);
                try {
                    byte[] original = cipher.doFinal(encrypted1);
                    String originalString = new String(original, "utf-8");
                    return originalString;
                } catch (Exception e) {
                    System.out.println(e.toString());
                    return null;
                }*/

                final Cipher decryptCipher = Cipher.getInstance("AES");
                decryptCipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey(KEY, "UTF-8"));
                return new String(decryptCipher.doFinal(Base64.getDecoder().decode(cipertext)));

            } catch (Exception ex) {
                ex.printStackTrace();
                /**
                 * 解密不出来可能 入库的时候没加密
                 */
                log.error("解密字段失败 " + cipertext);
                return cipertext;
            }
        }
    }
}
