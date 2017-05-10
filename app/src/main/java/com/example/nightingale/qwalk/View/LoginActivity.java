package com.example.nightingale.qwalk.View;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.nightingale.qwalk.Model.User;
        import com.example.nightingale.qwalk.R;

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

public class LoginActivity extends AppCompatActivity {
    String ID;
    EditText UsernameInput;
    EditText PasswordInput;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameInput   = (EditText)findViewById(R.id.username);
        PasswordInput   = (EditText)findViewById(R.id.password);
    }

    /** Called when the user taps the Send button */
    public void guestButtonClicked(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    public void RegisterButtonClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    private ProgressDialog progress;





    public void LoginButtonClicked(View view) {
        try {
            ID = new SendRequest().execute().get();
            ID = ID.replaceAll("\\s+","");
            if(Integer.parseInt(ID) == -1) {
                Toast.makeText(getApplicationContext(), "Incorrect Password/Username",
                        Toast.LENGTH_LONG).show();
            }
            else if(Integer.parseInt(ID) == -2){
                User.getInstance().setUserID(Integer.parseInt(ID));
                User.getInstance().setUsername(UsernameInput.getText().toString());
                Toast.makeText(getApplicationContext(), "Admin Success",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            }
            else{
                User.getInstance().setUserID(Integer.parseInt(ID));
                User.getInstance().setUsername(UsernameInput.getText().toString());
                Toast.makeText(getApplicationContext(), "Success",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            }



        } catch (Exception e) {

        }





    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        String Username = UsernameInput.getText().toString();
        String Password = PasswordInput.getText().toString();
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL("https://programmeringsprinsessorna.000webhostapp.com/validera.php");

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("username", Username);
                postDataParams.put("password", Password);


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
                writer.write(getPostDataString(postDataParams));

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
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

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
