package c;



import java.security.Provider;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static final String TAG = "AESUtils";
    private final static String HEX = "0123456789ABCDEF";
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";//AES鏄姞瀵嗘柟寮� CBC鏄伐浣滄ā寮� PKCS5Padding鏄～鍏呮ā寮�
    private static final String AES = "AES";//AES 鍔犲瘑
    private static final String SHA1PRNG = "SHA1PRNG";// SHA1PRNG 寮洪殢鏈虹瀛愮畻娉�, 瑕佸尯鍒�4.2浠ヤ笂鐗堟湰鐨勮皟鐢ㄦ柟娉�

    private byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        //for android
        SecureRandom sr = null;
       
            sr = SecureRandom.getInstance(SHA1PRNG);
        

        // for Java
        // secureRandom = SecureRandom.getInstance(SHA1PRNG);
        sr.setSeed(seed);
        kgen.init(128, sr); //256 bits or 128 bits,192bits
        // AES涓�128浣嶅瘑閽ョ増鏈湁10涓姞瀵嗗惊鐜紝192姣旂壒瀵嗛挜鐗堟湰鏈�12涓姞瀵嗗惊鐜紝256姣旂壒瀵嗛挜鐗堟湰鍒欐湁14涓姞瀵嗗惊鐜��
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        byte[] rets = new byte[16];
        for (int i = 0; i < 16; i++) {
            if (seed.length > i) {
                rets[i] = seed[i];
            } else {
                // key bytes padding by ZERO
                rets[i] = 0;
            }
        }
        return rets;

    }


    /*
     * 鍔犲瘑
     */
    public String encryptStr(String key, String cleartext) {
//        Log.e(TAG, "encrypt: " + cleartext);
        if (cleartext.isEmpty()) {
            return cleartext;
        }
        try {
            byte[] result = encrypt(key, cleartext.getBytes());
//            return new String(Base64.encode(result, "cleartext"));
            return   Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
//            Log.e(TAG, "encrypt: ", e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 鍔犲瘑
     */
    private byte[] encrypt(String key, byte[] clear) throws Exception {
        byte[] raw = getRawKey(key.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    /**
     * 瑙ｅ瘑
     */
    public String decrypt(String key, String encrypted) {
        if (encrypted.isEmpty()) {
            return encrypted;
        }
        try {
            byte[] enc  = Base64.getDecoder().decode(encrypted);
            byte[] result = decrypt(key, enc);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 瑙ｅ瘑
     */
    private byte[] decrypt(String key, byte[] encrypted) throws Exception {
        byte[] raw = getRawKey(key.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static class CryptoProvider extends Provider {

        /**
         * Constructs a provider with the specified name, version number,
         * and information.
         *
         * @param name    the provider name.
         * @param version the provider version number.
         * @param info    a description of the provider and its services.
         */
        public CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
        
        public static void main(String[] args) {
        	AESUtils aesUtils = new AESUtils();
//        	String string = aesUtils.encryptStr("zhongdun","123");
//        	System.out.println(string);
        	String fanhui = aesUtils.decrypt("zhongdun","6VCQFH5gve6bvG/XxEutfjFeVolIVH1LAdASCo2N0C+Cuv2QSuTEB2juYYjUC9F7");
        	System.out.println("解密的密码"+fanhui);
        	
		}
    }
}
