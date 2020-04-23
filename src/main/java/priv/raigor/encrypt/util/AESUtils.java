
package priv.raigor.encrypt.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密工具
 *
 * @author xxx
 *
 */
public class AESUtils {

    private static final String ENCODING = "gbk";

    public static String encrypt(String sSrc, String sKey) throws Exception {
        return encrypt(sSrc, sKey, ENCODING);
    }

    public static String encrypt(String sSrc, String sKey, String charset) throws Exception {
        if (sKey == null) {
            throw new Exception("Key 不能为空！");
        }

        byte[] raw = hex2byte(sKey);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] anslBytes = sSrc.getBytes(charset);//string2AnslBytes(sSrc);
        byte[] encrypted = cipher.doFinal(anslBytes);
        return byte2hex(encrypted).toUpperCase();
    }

    public static String decrypt(String sSrc, String sKey) throws Exception {
        return decrypt(sSrc, sKey, ENCODING);
    }

    public static String decrypt(String sSrc, String sKey, String charset) throws Exception {
        if (sKey == null) {
            throw new Exception("Key 不能为空！");
        }

        byte[] raw = hex2byte(sKey);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = hex2byte(sSrc);

        byte[] original = cipher.doFinal(encrypted1);
        return new String(original, charset);// anslBytes2String(original);
    }

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l/2];
        for (int i = 0; i !=l/2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }


    public static byte[] string2AnslBytes(String str){
        StringBuilder sb = new StringBuilder();
        for(char c : str.toCharArray()){
            sb.append(",");
            sb.append((int)c);
        }

        String anslStr = sb.substring(1);
        byte[] b = new byte[anslStr.length()];
        int i = 0;
        for(char c : anslStr.toCharArray()){
            b[i++] = (byte)c;
        }
        return b;
    }


    public static String anslBytes2String(byte[] anslBytes){
        char[] cs = new char[anslBytes.length];
        for(int i=0; i<anslBytes.length; i++){
            cs[i] = (char)anslBytes[i];

        }
        StringBuilder sb = new StringBuilder();
        String[] srcArray = new String(cs).split(",");
        for(int i=0; i<srcArray.length; i++){
            sb.append((char)(Integer.parseInt(srcArray[i])));
        }
        return sb.toString();

    }

    public static void main(String[] args) throws Exception {
        /*
         * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
         */
        String cKey = "8B6695D40FAA00B353F2DF7227CBCA902B1A0924359D2099";
        String src = "上线邮件审批一下";
        String enc = encrypt(src, cKey);
        System.out.println(enc);
        System.out.println(decrypt(enc, cKey));

        String key = "8B6695D40FAA00B353F2DF7227CBCA902B1A0924359D2099";
        System.out.println(encrypt("bjzhangyanyan123", key));
        System.out.println(decrypt("B073E4AA86D0683E05FFDF88BF2CECE954D5E6C9A58E340969C2338160CB67A180A55732D56E8F883C0D62E42E5C47B31D9B6E712FDE8D762994205373714099A2D2C460B2293B5D3FAACAB4E4522ED07B7B1C835306BE975292B4A7B5CD4FC4E8161FCBAB109D4F04CD25C21188BFA5695743663F75866CFCFD4AFD2CCC29B3F2EB3F8D334B4485906179F8FEBA83F1638E537843D0D03F699ABF7B8549B570", key, "utf-8"));


        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(System.currentTimeMillis());

    }
}
