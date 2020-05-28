package com.karl.base.util;


import java.security.MessageDigest;


/**
 * MD5工具类
 */
public class MD5Utils implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 加密算法
     *
     * @param plainText
     * @return
     */
    public static String encodeByMd5(String plainText) {
        StringBuilder buf = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();
            int i = 0;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //32位的加密
        return buf.toString();
    }
}
