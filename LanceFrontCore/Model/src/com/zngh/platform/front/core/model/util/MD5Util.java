package com.zngh.platform.front.core.model.util;

import java.security.MessageDigest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MD5Util {
    public MD5Util() {
        super();
    }

    public String getMD5(String plainText) {
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] agrs) {
        MessageDigest md;
        String password = "welcome1";

        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update("welcome1".getBytes());

            byte[] hash = md.digest();

            String encodedHash = "{SHA-1}" + new sun.misc.BASE64Encoder().encode(hash);
            System.out.println(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        
    }

}
