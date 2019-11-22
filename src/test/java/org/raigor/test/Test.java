package org.raigor.test;


import priv.raigor.encrypt.util.AESUtils;

public class Test {

    public static void main(String[] args){

        String encryptKey = "8B6695D40FAA00B353F2DF7227CBCA902B1A0924359D2099";
        String encryptEncoding = "UTF-8";

        String src = "862F2927A7C8E984D52A49C6180D3D7D2F2795ADBDA99B2E41EB71EDC97D510FD7B946167E7A1BC070022C677FD846418F12678469A8FAD8E10E5964A036B1E6657D7F8105B299410A81B1267F4D9D4C38D3EA44668C2745BEA27743D8F2580353F00365109BAB7C412B836144C32954BD3B2F0C84C6211F29613AAB855C9077";
        try {
            //AESUtils.encrypt(src, encryptKey, encryptEncoding);
            String ss = AESUtils.decrypt(src, encryptKey, encryptEncoding);
            //String ss = AESUtils.encrypt("wocaonimma", "8B6695D40FAA00B38B6695D40FAA00B3", encryptEncoding);
            System.out.println(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
