package com.example.nightingale.qwalk.Model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nightingale.qwalk.InterfaceView.IFriendActivity;
import com.example.nightingale.qwalk.R;
import com.example.nightingale.qwalk.View.FriendActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Nightingale on 2017-05-15.
 */

public class DatabaseHandler {

    static IFriendActivity view;

    private static Context ctx;
    final private static String host = "programmeringsprinsessorna.000webhostapp.com";
    final private static String insertQuizURL = "https://programmeringsprinsessorna.000webhostapp.com/insertquiz.php";
    final private static String insertAccountURL = "https://programmeringsprinsessorna.000webhostapp.com/insert.php";


    final private static String insertFriendURL = "https://programmeringsprinsessorna.000webhostapp.com/insertfriend.php";
    final private static String validateURL = "https://programmeringsprinsessorna.000webhostapp.com/validera.php";
    final private static String readQuizURL = "https://programmeringsprinsessorna.000webhostapp.com/readquiz.php";

    public static String getHost() {
        return host;
    }

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

    public static String getInsertFriendURL() {return insertFriendURL;}

    private static int request;

    public static void setFriendUsername(String friendUsername) {
        FriendUsername = friendUsername;
    }

    private static String FriendUsername;

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



    public static void loadFriends() {

        Log.d("JSON", "Method entered");
        request = 1;
        FriendUsername = "";
        try {
            String JSONstring = new SendFriendRequest().execute().get();

            Log.d("JSON", JSONstring);
            JSONArray jsonArray = new JSONArray(JSONstring);

            Account.getInstance().WipeLists();
            for (int i = 0; i < jsonArray.length(); ++i) {

                JSONObject quiz = jsonArray.getJSONObject(i);
                String friend = quiz.getString("username");
                Log.d("JSON", friend);
                Account.getInstance().getFriends().add(friend);

                int friendID = quiz.getInt("accountid");
                Log.d("JSON", String.valueOf(friendID));
                Account.getInstance().getFriendIDs().add(friendID);
            }

        } catch (Exception e) {
            Log.d("JSON", "Crash2Handler");
        }
        request = 0;
    }

public static void addFriend(String Friend, FriendActivity view2){
    view = view2;
    FriendUsername = Friend;
    request = 0;

    new DatabaseHandler.SendFriendRequest().execute();


}

    public static class SendFriendRequest extends AsyncTask<String, Void, String> {

        String Username = FriendUsername;
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL(DatabaseHandler.getInsertFriendURL());

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("friendusername", Username);
                postDataParams.put("userid", Account.getInstance().getUserID());
                postDataParams.put("request", request);



                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(DatabaseHandler.getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }


        @Override
        protected void onPostExecute(String result) {

            String msg = "";


                Log.e("response", result);
            result = result.replaceAll("\\s+", "");



                if (result.equals("0")) {
                    msg = ctx.getResources().getString(R.string.error);
                    view.AddFriendComplete(msg);
                } else if (result.equals("1")) {
                    msg = ctx.getResources().getString(R.string.no_existing_username);
                    view.AddFriendComplete(msg);
                } else if (result.equals("2")) {
                    msg = ctx.getResources().getString(R.string.already_friends);
                    view.AddFriendComplete(msg);
                } else if (result.equals("3")) {
                    //msg = ctx.getResources().getString(R.string.friend_added);
                    msg = "Vän tillagd";
                    view.AddFriendComplete(msg);
                }
                else if(result.equals("Exception:Unabletoresolvehost\""+ DatabaseHandler.getHost() + "\":Noaddressassociatedwithhostname")){
                    msg = ctx.getResources().getString(R.string.connection_failed_ex);
                    view.AddFriendComplete(msg);
                }








        }
    }

}
