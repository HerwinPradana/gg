package dimas.herwin.latif.com.getgood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_pref), MODE_PRIVATE);

                String token = sharedPreferences.getString("token", "");

                if(token.equals("")) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
