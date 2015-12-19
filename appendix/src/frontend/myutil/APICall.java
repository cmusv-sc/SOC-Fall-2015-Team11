package myutil;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Comment;
import model.Post;
import model.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APICall {

    public static enum ResponseType {
        SUCCESS, GETERROR, SAVEERROR, DELETEERROR, RESOLVEERROR, TIMEOUT, CONVERSIONERROR, UNKNOWN
    }

    private static final Gson gson = new Gson();
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        System.out.println(result);
        return result.toString();
    }
    public static <T> T getRemoteJson(String requestURL, Class<T> cls) {
        T obj = null;
        try {
            URL url = new URL(Constants.BACKEND_URL + requestURL);

            System.out.println(url);
            URLConnection httpURLConnection = url.openConnection();
            String result = readAll(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), Charset.forName("UTF-8"))));
            return gson.fromJson(result, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Post> getRemotePostJsonArray(String requestURL) {

        try {
            URL url = new URL(Constants.BACKEND_URL + requestURL);
            System.out.println(url);
            URLConnection httpURLConnection = url.openConnection();
            String result = readAll(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), Charset.forName("UTF-8"))));
            return gson.fromJson(result, new TypeToken<List<Post>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String requestURL(String urlString) {
        try {
            URL url = new URL(Constants.BACKEND_URL + urlString);
            System.out.println(url);
            URLConnection httpURLConnection = url.openConnection();
            return readAll(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), Charset.forName("UTF-8"))));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<User> getRemoteUserJsonArray(String requestURL) {
        try {
            URL url = new URL(Constants.BACKEND_URL + requestURL);
            System.out.println(url);
            URLConnection httpURLConnection = url.openConnection();
            String result = readAll(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), Charset.forName("UTF-8"))));
            return gson.fromJson(result, new TypeToken<List<User>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Comment> getRemoteCommentJsonArray(String requestURL) {
        try {
            URL url = new URL(Constants.BACKEND_URL + requestURL);
            System.out.println(url);
            URLConnection httpURLConnection = url.openConnection();
            String result = readAll(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), Charset.forName("UTF-8"))));
            return gson.fromJson(result, new TypeToken<List<Comment>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String postMessages(String requestURL, JsonNode message) {

        try {
            URL url = new URL(Constants.BACKEND_URL + requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            System.out.println(message);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(message.toString());

            writer.flush();
            writer.close();
            os.close();
            return readAll(new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
