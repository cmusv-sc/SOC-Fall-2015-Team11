package myutil;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by ASUA on 2015/11/6.
 */
public class APICall {

    private static final Gson gson = new Gson();
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public static <T> T getRemoteJson(String requestURL, Class<T> cls) {
        T obj = null;
        try {
            URL url = new URL(Constants.BACKEND_URL + requestURL);
            URLConnection httpURLConnection = url.openConnection();
            String result = readAll(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), Charset.forName("UTF-8"))));
            return gson.fromJson(result, cls);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return obj;
        }
    }
}
