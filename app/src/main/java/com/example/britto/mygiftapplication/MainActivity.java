package com.example.britto.mygiftapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
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


public class MainActivity extends AppCompatActivity {

    private WebView mWebview ;
    private Button dynbutton;
    private LinearLayout aboutus,shop,wrappingarea,deliveryarea,mycloud,wishlist,help,yourorder,myaccountlayout,change_password_layout;
    private DrawerLayout mDrawerLayout;

    private Button buttonlogin;
    private TextView usernametext;
    private LoginSession loginSession;

    private ImageButton menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernametext=findViewById(R.id.usernametext);
        myaccountlayout=findViewById(R.id.myaccountlayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        loginSession = new LoginSession(MainActivity.this);

        menu=(ImageButton)findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mDrawerLayout.isDrawerVisible(GravityCompat.START)){
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this , mDrawerLayout ,
                toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close);

        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawerLayout.isDrawerVisible(GravityCompat.START)){
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        buttonlogin=(Button)findViewById(R.id.buttonlogin);

        if(loginSession.isLoggedInUser()){
            Log.e("TAG" , "User Logged In ");
            usernametext.setText(loginSession.getUserName());
            buttonlogin.setText("Logout");
            if(myaccountlayout.getVisibility()!=View.VISIBLE){
                myaccountlayout.setVisibility(View.VISIBLE);
            }
            buttonlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent=new Intent(MainActivity.this,LoginPage.class);
                    startActivity(intent);*/
                    Log.e("TAG", "Logout");
                    open();
                }
            });
        }
        else {
            Log.e("TAG" , "no User Logged In ");
            buttonlogin.setText("Login");
            if(myaccountlayout.getVisibility()!=View.GONE){
                myaccountlayout.setVisibility(View.GONE);
            }
            buttonlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this,LoginPage.class);
                    startActivity(intent);
                    finish();
                }
            });
        }



        change_password_layout=(LinearLayout)findViewById(R.id.change_password_layout);
        change_password_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordDialog();
            }
        });

        aboutus=(LinearLayout)findViewById(R.id.aboutusid);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AboutUSPage.class);
                startActivity(intent);
            }
        });


        shop=(LinearLayout)findViewById(R.id.shopid);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Shop.class);
                startActivity(intent);
            }
        });



        wrappingarea=(LinearLayout)findViewById(R.id.wrappid);
        wrappingarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,WrappingArea.class);
                startActivity(intent);
            }
        });

        deliveryarea=(LinearLayout)findViewById(R.id.deliveryid);
        deliveryarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Delivery.class);
                startActivity(intent);
            }
        });

        mycloud=(LinearLayout)findViewById(R.id.mycloudid);
        mycloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyCloud.class);
                startActivity(intent);
            }
        });

        help=(LinearLayout)findViewById(R.id.helpid);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Help.class);
                startActivity(intent);
            }
        });

        wishlist=(LinearLayout)findViewById(R.id.wishlistid);
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Wishlist.class);
                startActivity(intent);
            }
        });


        yourorder=(LinearLayout)findViewById(R.id.yourorderid);
        yourorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,YourOrder.class);
                startActivity(intent);
            }
        });

        mWebview = (WebView)findViewById(R.id.webView);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebview.setWebViewClient(new WebBrowser());
        mWebview.loadUrl(GiftingUrls.mainpageurl);

    }

    public class WebBrowser extends WebViewClient{
        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
           /* SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginPage.class);
            String imgSett = prefs.getString(UserName, "");
*/
            /*dynbutton=(Button)findViewById(R.id.buttonlogin);
            dynbutton.setText("Logout");*/
            progressDialog.show();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            if(progressDialog.isShowing()) progressDialog.dismiss();
        }
    }


    public void open(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure want to Logout?");

        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                        loginSession.sessionClear();
                        recreate();
                        //Toast.makeText(MainActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void changePasswordDialog(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.change_password_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText currentPassword = (EditText)dialog.findViewById(R.id.current_password_edittext);
        final EditText newPassword = (EditText)dialog.findViewById(R.id.new_password_edittext);
        final EditText reenterNewPassword = (EditText)dialog.findViewById(R.id.reenter_password_edittext);

        Button changePasswordButton = (Button)dialog.findViewById(R.id.change_password_dialog_change_button);
        Button cancelPasswordButton = (Button)dialog.findViewById(R.id.change_password_dialog_cancel_button);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog.isShowing()) dialog.dismiss();

                if(newPassword.getText().toString().equals(reenterNewPassword.getText().toString())){
                    new HttpAsyncTask(loginSession.getEmailk(),
                            currentPassword.getText().toString() , newPassword.getText().toString())
                            .execute(GiftingUrls.changepasswordurl);
                }
                else{
                    Toast.makeText(MainActivity.this, "Passwords don't Match pls re-enter", Toast.LENGTH_LONG).show();
                }

            }
        });

        cancelPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog.isShowing()) dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class HttpAsyncTask extends AsyncTask<String, Void, String> {

        String username , oldPassword ,  newPassword;
        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        private HttpAsyncTask(String username , String oldPassword , String newPassword ){
            this.username = username;
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }

        @Override
        protected String doInBackground(String... urls) {
            return POST(urls[0] , username , oldPassword , newPassword);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.e("Change Password " , "Result : "+result);
            if(progressDialog.isShowing()) progressDialog.dismiss();
            if(result.toLowerCase().contains("changed")){
                Toast.makeText(MainActivity.this, "Password Successfully changed!!",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Error Changing Password!!",Toast.LENGTH_SHORT).show();
            }
         }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading");
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

    public static String POST(String url,String username , String oldPassword , String newPassword ){
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
            jsonObject.accumulate("username",username);
            jsonObject.accumulate("oldpassword", oldPassword);
            jsonObject.accumulate("password", newPassword);

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

}
