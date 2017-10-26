package cn.crabime.crypto;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by crabime on 10/26/17.
 * 使用md5进行加密
 */
public class MD5Crypto {

    public String md5(String v){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(v.getBytes(),0, v.length());
        String hashedPass = new BigInteger(1,messageDigest.digest()).toString(16);
        if (hashedPass.length() < 32) {
            hashedPass = "0" + hashedPass;
        }
        return hashedPass;
    }

    public String bcrypt(String str){
        BCryptPasswordEncoder bCryptPassword = new BCryptPasswordEncoder(10);
        return bCryptPassword.encode(str);
    }

    /**
     * 使用未加密的密码于加密后的密码进行比对
     * @param rawPassword 原始密码
     * @return 是否匹对
     */
    public boolean matches(String rawPassword, String encodedPassword){
        BCryptPasswordEncoder bCryptPassword = new BCryptPasswordEncoder(10);
        return bCryptPassword.matches(rawPassword, encodedPassword);
    }
}
