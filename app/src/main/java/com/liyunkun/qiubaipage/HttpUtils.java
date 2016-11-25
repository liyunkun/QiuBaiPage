package com.liyunkun.qiubaipage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liyunkun on 2016/9/3 0003.
 */
public class HttpUtils {

    public static byte[] getJSONArray(String urlPath){
        HttpURLConnection conn=null;
        InputStream is=null;
        ByteArrayOutputStream baos=null;
        try {
            URL url=new URL(urlPath);
            conn= (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5*1000);
            conn.connect();
            if(conn.getResponseCode()==200){
                is=conn.getInputStream();
                baos=new ByteArrayOutputStream();
                int len;
                byte[] bytes=new byte[1024];
                while(true){
                    len=is.read(bytes);
                    if(len!=-1){
                        baos.write(bytes,0,len);
                        baos.flush();
                    }else{
                        break;
                    }
                }
                return baos.toByteArray();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
            try {
                if(baos!=null){
                    baos.close();
                }
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static String getJSONString(String urlPath){
        HttpURLConnection conn=null;
        InputStream is=null;
       StringBuilder stringBuilder=new StringBuilder();
        try {
            URL url=new URL(urlPath);
            conn= (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5*1000);
            conn.connect();
            if(conn.getResponseCode()==200){
                is=conn.getInputStream();
                int len;
                byte[] bytes=new byte[1024];
                while(true){
                    len=is.read(bytes);
                    if(len!=-1){
                        stringBuilder.append(new String(bytes,0,len));
                    }else{
                        break;
                    }
                }
                return stringBuilder.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
