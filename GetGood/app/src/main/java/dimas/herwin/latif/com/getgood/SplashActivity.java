package dimas.herwin.latif.com.getgood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import dimas.herwin.latif.com.getgood.tasks.AsyncTaskListener;
import dimas.herwin.latif.com.getgood.tasks.HttpTask;

public class SplashActivity extends AppCompatActivity implements AsyncTaskListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_pref), MODE_PRIVATE);

                if(!sharedPreferences.contains("token")) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    // CHECK TOKEN
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if(networkInfo != null && networkInfo.isConnected()){
                        String url = "http://192.168.122.1/ggwp/public/api/checkToken";

                        new HttpTask(SplashActivity.this).execute(url, "GET", "", sharedPreferences.getString("token", ""));
                    }
                    else {
                        Log.e("NetworkInfo", "Not connected to a network.");
                    }
                }
            }
        }, 3000);
    }

    public void onTaskCompleted(String response) {
        try {
            JSONObject json = new JSONObject(response);

            if(!json.has("error")){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                if(json.getString("error").equals("token_not_provided") || json.getString("error").equals("token_expired")){
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }
        catch (JSONException e){
            Log.e("JSONException", "Invalid string response.");
        }
        catch(Exception e){
            Log.e("SplashActivity", e.getMessage());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
