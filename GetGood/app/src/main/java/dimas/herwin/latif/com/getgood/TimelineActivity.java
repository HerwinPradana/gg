package dimas.herwin.latif.com.getgood;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import org.json.JSONException;

import dimas.herwin.latif.com.getgood.fragments.PostFragment;
import dimas.herwin.latif.com.getgood.fragments.items.Post;
import dimas.herwin.latif.com.getgood.tasks.AsyncTaskListener;
import dimas.herwin.latif.com.getgood.tasks.HttpTask;

public class TimelineActivity extends FragmentActivity implements PostFragment.OnListFragmentInteractionListener, AsyncTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        if(savedInstanceState == null)
            loadTimeline();
    }

    private void loadTimeline() {
        if(findViewById(R.id.listPost) != null){
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo         = connectivityManager.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()){
                String url = "http://192.168.122.1/ggwp/public/api/auth/login";

                new HttpTask(this).execute(url, "POST", "");
            }
            else {
                Log.e("CONNECTION: ", "NOT CONNECTED");
            }
        }
    }

    public void onListFragmentInteraction(Post post){
    }

    public void onTaskCompleted(String response) {
        try {
            PostFragment postFragment = new PostFragment();

            Bundle args = new Bundle();
            args.putString(PostFragment.ARG_JSON, response);

            postFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.listPost, postFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        catch (IllegalStateException error){
            Log.e("JSONException", "Illegal state exception on onPostExecute().");
        }
    }
}
