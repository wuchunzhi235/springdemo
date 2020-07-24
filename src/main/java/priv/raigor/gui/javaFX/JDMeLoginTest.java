/*
 * Copyright (c) 1998, 2020, Jd.com and/or its affiliates. All rights reserved.
 * JD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package priv.raigor.gui.javaFX;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.SSLSocketFactory;
import priv.raigor.spider.utils.HttpUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cdwuchunzhi@jd.com
 * @version 1.0
 * @since 2020/4/22 11:35
 */
public class JDMeLoginTest {

    private static BufferedReader recvReader;
    private static PrintWriter sendWriter;

    public static String timLineLoginHaveToken(String userName,String teamId){
        System.out.println("userName IS :"+userName+",and  teamId = "+teamId+" ");
        if(StringUtils.isEmpty(userName)){
            System.out.println("userName IS isEmpty,set userName = wuchunzhi ");
            userName = "wuchunzhi";
        }
        String url = "https://janus-api-pre.jd.com/timLineLogin";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-client", "ANDROID");
        headers.put("x-ts", "1572401824215");
        headers.put("x-gw-version", "2");
        headers.put("x-app", "411994940424458240");
        headers.put("x-api", "login.timLineLogin");
        headers.put("x-nonce", "f7cef957-dac2-424d-baac-196985f48d72");
        headers.put("x-did", "aa4a550304cb0e0f");
        headers.put("x-app-version", "5.9.0");
        headers.put("x-sign", "3410ff2726ec61a759eecf5c3a943f27");
        headers.put("content-type", "application/json");
        headers.put("x-token", "7807c3cb88ce94e3adf75f04eec1741c");
        headers.put("cache-control", "no-cache");
        headers.put("postman-token", "c9b49da3-baab-4b62-4a20-dd3eca1baf42");

        if(StringUtils.isEmpty(teamId)){
            System.out.println("teamId IS isEmpty,set teamId = 00046419 ");
            teamId = "00046419";
        }
        String jsonContent = "{  \"erp\": \""+userName+"\",  \"teamId\": \""+teamId+"\"}";

        String responseResult = HttpUtils.postFormForHeadersAndJson(url,headers,jsonContent);
        System.out.println("responseResult IS:"+responseResult);
        try{
            JSONObject responseJson = JSON.parseObject(responseResult);
            if(responseJson == null){

                System.out.println("responseJson IS null for responseResult:"+responseResult);
            }else{
                String errorMsg = responseJson.getString("errorMsg");
                if(StringUtils.isEmpty(errorMsg)){
                    System.out.println("errorMsg IS isEmpty for responseResult:"+responseResult);
                }
                System.out.println("errorMsg IS :"+errorMsg);
                if("success".equals(errorMsg)){
                    //return true;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return responseResult;

    }


    public static boolean sendMessage(String protocolUpString) {
        try{
            //获取Socket的读取流
            sendWriter.println(protocolUpString);
            System.out.println("protocolUpString:"+protocolUpString);
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    //建立客户端与服务器端的连接
    public static void setScoketConnection(String ip) {
        boolean connected = false;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager trustAllCerts = new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                    if (null == chain) {
                        throw new IllegalArgumentException("chain is null");
                    }
                    if (chain.length <= 0) {
                        throw new IllegalArgumentException("chain is empty");
                    }
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                    if (null == chain) {
                        throw new IllegalArgumentException("chain is null");
                    }
                    if (chain.length <= 0) {
                        throw new IllegalArgumentException("chain is empty");
                    }
                }
            };
            sslContext.init(null, new TrustManager[]{trustAllCerts}, null);

            Socket mSocket = sslContext.getSocketFactory().createSocket();

            InetAddress inetAddress = InetAddress.getByName(ip);
            InetSocketAddress remoteNetSocketAddress = new InetSocketAddress(inetAddress, 443);
            mSocket.setKeepAlive(true);
            mSocket.connect(remoteNetSocketAddress, 100);


            //DataInputStream mReaderStream = new DataInputStream(mSocket.getInputStream());
            //DataOutputStream mWriterStream = new DataOutputStream(mSocket.getOutputStream());

            //Socket sock = new Socket(ip, 443);
            recvReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            sendWriter = new PrintWriter(mSocket.getOutputStream(), true);
            connected = true;
        } catch (Exception ex) {
            ex.printStackTrace(); 				//登录失败
            connected = false; 		//不允许继续开启聊天
        }
        System.out.println("connected:"+connected);
    }

    public static BufferedReader getReader(){
        return recvReader;
    }

    public static PrintWriter getWriter(){
        return sendWriter;
    }
}
