package com.example.nightingale.qwalk.View;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.Toast;


        import com.example.nightingale.qwalk.Model.Account;
        import com.example.nightingale.qwalk.Model.DatabaseHandler;
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
    private ProgressBar spinner;
    Button Loginbutton;
    Button Registerbutton;
    Button Guestbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameInput   = (EditText)findViewById(R.id.username);
        PasswordInput   = (EditText)findViewById(R.id.password);
        Loginbutton = (Button) findViewById(R.id.LoginButton);
        Registerbutton = (Button) findViewById(R.id.Registerbutton);
        Guestbutton = (Button) findViewById(R.id.Guestbutton);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);

        spinner.setVisibility(View.GONE);
        
    }

    /** Called when the user taps the Send button */
    public void guestButtonClicked(View view) {
        Account.getInstance().logOut();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    public void RegisterButtonClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }





    public void LoginButtonClicked(View view) {
        Loginbutton.setEnabled(false);
        Registerbutton.setEnabled(false);
        Guestbutton.setEnabled(false);
        spinner.setVisibility(View.VISIBLE);



        try {
            new SendRequest().execute();



        } catch (Exception e) {

        }





    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        String Username = UsernameInput.getText().toString();
        String Password = PasswordInput.getText().toString();
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL(DatabaseHandler.getValidateURL());

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

            ID = result;
            ID = ID.replaceAll("\\s+","");
            Log.e("CrashID",ID);
            if(ID.equals("Exception:Unabletoresolvehost\""+ DatabaseHandler.getHost() + "\":Noaddressassociatedwithhostname")){

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_ex),
                        Toast.LENGTH_LONG).show();
            }
            else if(ID == null){

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_failed_ex),
                        Toast.LENGTH_LONG).show(); //"Connection Failed" -> "Uppkoppnilng misslyckades"
            }

            else if(Integer.parseInt(ID) == -1) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.incorrect_un_pw_ex),
                        Toast.LENGTH_LONG).show(); // "Incorrect Password/Username" översatt
            }
            else if(Integer.parseInt(ID) == -2){
                Account.getInstance().setUserID(Integer.parseInt(ID));
                Account.getInstance().setUsername(UsernameInput.getText().toString());
                Toast.makeText(getApplicationContext(), "Admin Success",
                        Toast.LENGTH_LONG).show();
                DatabaseHandler.loadFriends();

                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }

            else{
                Account.getInstance().setUserID(Integer.parseInt(ID));
                Account.getInstance().setUsername(UsernameInput.getText().toString());
                //Toast.makeText(getApplicationContext(), getResources().getString(R.string.done),
                        //Toast.LENGTH_LONG).show(); // "Success" -> "Klar"
                DatabaseHandler.loadFriends();
                spinner.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
            spinner.setVisibility(View.GONE);
            Loginbutton.setEnabled(true);
            Registerbutton.setEnabled(true);
            Guestbutton.setEnabled(true);
        }
    }

}

