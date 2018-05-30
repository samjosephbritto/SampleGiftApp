package com.example.britto.mygiftapplication;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.britto.mygiftapplication.sessions.LoginSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class LoginPage extends Activity{

    private Button b,dynbutton;
    private Button regbutton;
    private EditText email, password;
    Boolean state=false;

    private LoginSession session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        state=true;
        Log.e("Console","State !!"+state);
        System.out.println();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        b=(Button)findViewById(R.id.loginbutton);
        email = (EditText) findViewById(R.id.emailText1);
        password = (EditText) findViewById(R.id.passText2);

        session = new LoginSession(LoginPage.this);


       b.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new HttpAsyncTask().execute(GiftingUrls.loginurl);
              /* Intent intent=new Intent(LoginPage.this,MainActivity.class);
               startActivity(intent);*/
           }
       });

        regbutton=(Button)findViewById(R.id.registerbutton);
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intend=new Intent(LoginPage.this,Register.class);
                startActivity(intend);
            }
        });


    }
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    public static String POST(String url, LoginParameters loginParameters){
        InputStream inputStream = null;
        String result = "";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
            String json = "";
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("username",loginParameters.getEmail());
            jsonObject.accumulate("password", loginParameters.getPassword());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            Log.e("TAG", "Register Json : "+json);
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    public class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog = new ProgressDialog(LoginPage.this);

        @Override
        protected String doInBackground(String... urls) {

            LoginParameters loginParameters  = new LoginParameters();
            loginParameters.setEmail(email.getText().toString());
            loginParameters.setPassword(password.getText().toString());
            return POST(urls[0],loginParameters);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.e("Register Result" , "Result : "+result);
            if(progressDialog.isShowing())progressDialog.dismiss();

            try {
                JSONObject jsonObject= new JSONObject(result);
                if (jsonObject.getString("username").toLowerCase().contains("invalid")){
                    Toast.makeText(getBaseContext(), "Invalid Username Or Password", Toast.LENGTH_LONG).show();
                }
                else {
                   /* String tempemail=email.getText().toString();*/
                  /*  setDefaults(Emailk,tempemail,null);
                    setDefaults(UserName,jsonObject.getString("username"),null);*/
                  /*  SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Emailk,tempemail);
                    editor.putString(UserName,jsonObject.getString("username"));
                    editor.commit();*/
                   /* String text = sharedpreferences.getString(Emailk,null);
                    String userName = sharedpreferences.getString(UserName,null);
                    Log.e("TAG" , "Email : "+text +"\tUser Name : "+userName);*/
                    session.createLoginSession(email.getText().toString() , jsonObject.getString("username"));
                    Toast.makeText(getBaseContext(), "Successfully Logined ", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(LoginPage.this , MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public  void setDefaults(String key, String value, Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.commit();
        }


        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Signing In");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(LoginPage.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
