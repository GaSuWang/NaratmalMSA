package com.naratmal.user.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;



import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;


public class KakaoUtil {
    private static Logger logger = LoggerFactory.getLogger(KakaoUtil.class);

    private static String apiKey="77f6e1649bf8e9de2d17b8270ad693c2";

    private static String redirectUri="http://localhost:3000/oauth/callback/kakao";

    public static String getKakaoAccessToken(String code) throws Exception {
        String accessToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        //String redirectUri = "http://localhost:8083/api/user/redirect";
        //String redirectUri = "http://localhost:3000/oauth/callback/kakao";
        HttpURLConnection connection = null;
        BufferedWriter bw = null;
        BufferedReader br = null;
        try {
            URL url = new URL(reqURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuffer sb = new StringBuffer();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=");
            sb.append(apiKey);
            sb.append("&redirect_uri=");
            sb.append(redirectUri);
            sb.append("&code=");
            sb.append(code);
            bw.write(sb.toString());
            bw.flush();
            int responseCode = connection.getResponseCode();
            logger.info("Kakao getToken responseCode: {}", responseCode);
            if (responseCode != 200) {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line = "";
                String error = "";

                while ((line = br.readLine()) != null) {
                    error += line;
                }
                logger.error("",error);

                throw new Exception("Kakao API Connection Fail During Get Token: ResponseCode [ "+responseCode+" ]\n"+error);
            }
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            String output;
            sb = new StringBuffer();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            String result = sb.toString();
            accessToken = (String) new Gson().fromJson(result, Map.class).get("access_token");
            return accessToken;
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
            close(bw);
            close(br);
        }
    }

    public static String getKakaoEmail(String accessToken) throws Exception {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        HttpURLConnection connection = null;
        BufferedReader br = null;
        try {
            URL url = new URL(reqURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            if (connection.getResponseCode() != 200) {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line = "";
                String error = "";

                while ((line = br.readLine()) != null) {
                    error += line;
                }
                logger.error("",error);
                throw new Exception("Kakao API Connection Fail During Get Email: ResponseCode [ "+connection.getResponseCode()+" ]\n"+error);
            }

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            String kakaoAccount = new Gson().fromJson(result, Map.class).get("kakao_account").toString();
            String resEmail = (String) new Gson().fromJson(kakaoAccount, Map.class).get("email");
            return resEmail;
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
            close(br);
        }
    }


    private static void close(BufferedReader br) throws Exception{
        if(br!=null){
            br.close();
        }
    }
    private static void close(BufferedWriter bw) throws Exception{
        if(bw!=null){
            bw.close();
        }
    }
    private static void close(HttpURLConnection connection) throws Exception{
        if(connection!=null){
            connection.disconnect();
        }
    }
}
