package com.projectdemo.springbootdemo.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESKit {

    private static final String key = "tern-9527";

    /**
     * 加密
     *
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     */
    private static byte[] encryptByte(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return
     */
    private static byte[] decryptByte(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密
     *
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        return HexKit.binary2Hex(encryptByte(str, key));
    }

    /**
     * AES解密
     *
     * @param str
     * @return
     */
    public static String decrypt(String str) {
        // 16进制转2进制
        byte[] decryptFrom = HexKit.hex2Byte(str);
        // 根据byte进行解码
        byte[] decryptResult = decryptByte(decryptFrom, key);
        return new String(decryptResult);
    }

    public static void main(String[] args) {
        if(args.length>0 && args.length%2!=0){
            System.out.println("Parameters Error!");
            System.exit(0);
        }

        for(int i=0;i<args.length/2;i++){
            if(args[i*2].equals("-e")){
                String content = args[i*2+1];
                String s = AESKit.encrypt(content);
                System.out.println("Encryption:"+s);
            }else{
                System.out.println("Parameters '"+args[i*2]+"' do not exist!");
            }
        }
//		System.exit(0);
//

//		String str = "Tern%1228";
//		System.out.println("加密前：" + str);
//		String s = AESKit.encrypt(str);
//		System.out.println("加密后：" + s);
//		System.out.println("解密后：" + AESKit.decrypt(s));
    }
}
