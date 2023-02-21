package com.naratmal.user.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;



import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;


public class KakaoUtil {
    private static Logger logger = LoggerFactory.getLogger(KakaoUtil.class);

    public static String getKakaoAccessToken(String code) throws Exception {
        String accessToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        String apiKey = "";
        //String redirectUri = "http://localhost:8083/api/user/redirect";
        String redirectUri = "http://localhost:3000/oauth/callback/kakao";
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
            logger.info("KaKao getToken responseCode: {}", responseCode);
            if (responseCode != 200) {
                logger.error("Kakao Get Token Request Error");
                throw new Exception("Connection Fail");
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
            connection.disconnect();
            bw.close();
            br.close();
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
                logger.error("Kakao Get Email Request Error");
                throw new Exception("Connection Fail");
            }

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            String kakaoAccount = (String) new Gson().fromJson(result, Map.class).get("kakao_account");
            String resEmail = (String) new Gson().fromJson(kakaoAccount, Map.class).get("email");
            return resEmail;
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
            br.close();
        }
    }
}
