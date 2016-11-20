package dimas.herwin.latif.com.getgood;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import dimas.herwin.latif.com.getgood.tasks.AsyncTaskListener;
import dimas.herwin.latif.com.getgood.tasks.LoginTask;

public class LoginActivity extends AppCompatActivity implements AsyncTaskListener{

    EditText editTextEmail;
    EditText editTextPassword;
    Button   buttonLogin;
    TextView textViewErrors;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail    = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonLogin      = (Button) findViewById(R.id.login);
        textViewErrors   = (TextView) findViewById(R.id.errors);

        sharedPreferences = getSharedPreferences(getString(R.string.app_pref), MODE_PRIVATE);
    }

    public void login(View view){
        textViewErrors.setText("");

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo                 = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            String email    = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            String parameters = "email=" + email + "&password=" + password;
            String url = "http://192.168.43.116/ggwp/public/api/auth/login";

            new LoginTask(this).execute(url, "POST", parameters);
        }
        else{
            Log.e("CONNECTION: ", "NOT CONNECTED");
        }
    }

    public void onTaskCompleted(String response){
        try {
            JSONObject loginData = new JSONObject(response);

            if(!loginData.has("error") && loginData.getString("status").equals("ok")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", loginData.getString("token"));
                editor.apply();
            }
            else{
                JSONObject error    = loginData.getJSONObject("error");
                String errorString  = getString(R.string.login_failed);

                int statusCode = error.getInt("status_code");

                if(statusCode == 422) {
                    // Unprocessable Entity / Field requirements not met.
                    JSONObject errors = error.getJSONObject("errors");

                    if(errors.has("email")) {
                        JSONArray messages = errors.getJSONArray("email");

                        int nMessages = messages.length();
                        for(int i = 0;i < nMessages;i++)
                            errorString += "\n" + messages.getString(i);
                    }

                    if(errors.has("password")) {
                        JSONArray messages = errors.getJSONArray("password");

                        int nMessages = messages.length();
                        for(int i = 0;i < nMessages;i++)
                            errorString += "\n" + messages.getString(i);
                    }
                }
                else if(statusCode == 403){
                    // Forbidden
                    errorString += "\n" + getString(R.string.wrong_email_password);
                }

                textViewErrors.setText(errorString);
            }
        }
        catch (JSONException error){
            Log.e("JSONException", "Invalid string response.");
        }
    }
}
