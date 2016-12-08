package dimas.herwin.latif.com.getgood;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dimas.herwin.latif.com.getgood.tasks.AsyncTaskListener;
import dimas.herwin.latif.com.getgood.tasks.HttpTask;

public class SignupActivity extends AppCompatActivity{

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    Button   buttonSignup;
    TextView textViewErrors;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextName     = (EditText) findViewById(R.id.name);
        editTextEmail    = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonSignup     = (Button) findViewById(R.id.login);
        textViewErrors   = (TextView) findViewById(R.id.errors);

        sharedPreferences = getSharedPreferences(getString(R.string.app_pref), MODE_PRIVATE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void signup(View view){
        textViewErrors.setText("");

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo                 = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            String name     = editTextName.getText().toString();
            String email    = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            String parameters = "name=" + name + "&email=" + email + "&password=" + password;
            String url = "http://" + getString(R.string.server_address) + "/ggwp/public/api/auth/signup";

            new HttpTask(new AsyncTaskListener() {
                @Override
                public void onTaskCompleted(String response) {
                    handleSignupTask(response);
                }
            }).execute(url, "POST", parameters);
        }
        else{
            Log.e("CONNECTION: ", "NOT CONNECTED");
        }
    }

    public void handleSignupTask(String response){
        try {
            JSONObject signupData = new JSONObject(response);

            if(!signupData.has("error") && signupData.getString("status").equals("ok")){
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra(LoginActivity.LOGIN_MESSAGE, getString(R.string.signup_succeed));
                startActivity(intent);
            }
            else{
                JSONObject error    = signupData.getJSONObject("error");
                String errorString  = getString(R.string.login_failed);

                int statusCode = error.getInt("status_code");

                if(statusCode == 422) {
                    // Unprocessable Entity / Field requirements not met.
                    JSONObject errors = error.getJSONObject("errors");

                    if(errors.has("name")) {
                        JSONArray messages = errors.getJSONArray("name");

                        int nMessages = messages.length();
                        for(int i = 0;i < nMessages;i++)
                            errorString += "\n" + messages.getString(i);
                    }

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
                else if(statusCode == 500){
                    // Forbidden
                    errorString += "\n" + getString(R.string.email_already_used);
                }

                textViewErrors.setText(errorString);
            }
        }
        catch (JSONException error){
            Log.e("Response", response);
            Log.e("JSONException", "Invalid string response.");
        }
    }
}
