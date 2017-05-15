package com.example.nightingale.qwalk.Model;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Nightingale on 2017-05-15.
 */

public class DatabaseHandler {
    final private static String insertQuizURL = "https://programmeringsprinsessorna.000webhostapp.com/insertquiz.php";
    final private static String insertAccountURL = "https://programmeringsprinsessorna.000webhostapp.com/insert.php";
    final private static String validateURL = "https://programmeringsprinsessorna.000webhostapp.com/validera.php";
    final private static String readQuizURL = "https://programmeringsprinsessorna.000webhostapp.com/readquiz.php";

    public static String getInsertQuizURL() {
        return insertQuizURL;
    }

    public static String getInsertAccountURL() {
        return insertAccountURL;
    }

    public static String getValidateURL() {
        return validateURL;
    }

    public static String getReadQuizURL() {
        return readQuizURL;
    }



    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}
