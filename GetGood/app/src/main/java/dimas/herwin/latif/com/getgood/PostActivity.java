package dimas.herwin.latif.com.getgood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import dimas.herwin.latif.com.getgood.fragments.adapters.TagChipViewAdapter;
import dimas.herwin.latif.com.getgood.fragments.items.Tag;
import dimas.herwin.latif.com.getgood.tasks.AsyncTaskListener;
import dimas.herwin.latif.com.getgood.tasks.HttpTask;

public class PostActivity extends AppCompatActivity {

    private EditText                contentInput;
    private ChipView                tagsView;
    private LinearLayout            tagLayout;
    private LinearLayout            communityLayout;
    private AutoCompleteTextView    tagInput;
    private AutoCompleteTextView    communityInput;
    private SharedPreferences       sharedPreferences;

    private List<String>            tagIds;
    private List<String>            communityIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(getString(R.string.app_pref), MODE_PRIVATE);

        contentInput    = (EditText) findViewById(R.id.content);
        tagsView        = (ChipView) findViewById(R.id.tags);
        tagLayout       = (LinearLayout) findViewById(R.id.tag_layout);
        communityLayout = (LinearLayout) findViewById(R.id.community_layout);
        tagInput        = (AutoCompleteTextView) findViewById(R.id.tag);
        communityInput  = (AutoCompleteTextView) findViewById(R.id.community);
        tagIds          = new ArrayList<>();
        communityIds    = new ArrayList<>();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo         networkInfo         = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            String url = "http://" + getString(R.string.server_address) + "/ggwp/public/api/";

            new HttpTask(new AsyncTaskListener() {
                @Override
                public void onTaskCompleted(String response) {
                    handleTagsTask(response, tagInput);
                }
            }).execute(url + "tags", "GET", null, sharedPreferences.getString("token", null));

            new HttpTask(new AsyncTaskListener() {
                @Override
                public void onTaskCompleted(String response) {
                    handleTagsTask(response, communityInput);
                }
            }).execute(url + "community/get", "POST", null, sharedPreferences.getString("token", null));
        }
        else{
            Log.e("NetworkInfo", "Not connected to a network.");
        }

        tagInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tag tag = (Tag) parent.getItemAtPosition(position);

                List<Chip> chips = tagsView.getChipList();
                chips.add(tag);
                tagsView.setChipList(chips);

                tagIds.add(tag.getId());

                tagInput.setText(null);
            }
        });

        communityInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tag tag = (Tag) parent.getItemAtPosition(position);

                List<Chip> chips = tagsView.getChipList();
                chips.add(tag);
                tagsView.setChipList(chips);

                communityIds.add(tag.getId());

                communityInput.setText(null);
            }
        });

        tagsView.setAdapter(new TagChipViewAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_menu, menu);
        return true;
    }

    public void tag(View v){
        ViewGroup.LayoutParams tagParams = tagLayout.getLayoutParams();
        if(tagParams.height != 0) {
            tagParams.height = 0;
        }
        else{
            tagParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            ViewGroup.LayoutParams communityParams = communityLayout.getLayoutParams();
            communityParams.height = 0;
            communityLayout.setLayoutParams(communityParams);
        }
        tagLayout.setLayoutParams(tagParams);
    }

    public void community(View v){
        ViewGroup.LayoutParams communityParams = communityLayout.getLayoutParams();
        if(communityParams.height != 0) {
            communityParams.height = 0;
        }
        else{
            communityParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            ViewGroup.LayoutParams tagParams = tagLayout.getLayoutParams();
            tagParams.height = 0;
            tagLayout.setLayoutParams(tagParams);
        }
        communityLayout.setLayoutParams(communityParams);
    }

    public void post(View v){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo         networkInfo         = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                JSONArray tags        = new JSONArray(tagIds);
                JSONArray communities = new JSONArray(communityIds);

                String tagString        = URLEncoder.encode(tags.toString(), "UTF-8");
                String communityString  = URLEncoder.encode(communities.toString(), "UTF-8");
                String contentString    = URLEncoder.encode(contentInput.getText().toString(), "UTF-8");

                String url    = "http://" + getString(R.string.server_address) + "/ggwp/public/api/post/store";
                String params = "content=" + contentString + "&tags=" + tagString + "&communities=" + communityString;

                new HttpTask(new AsyncTaskListener() {
                    @Override
                    public void onTaskCompleted(String response) {
                        handlePostTask(response);
                    }
                }).execute(url, "POST", params, sharedPreferences.getString("token", null));
            } else {
                Log.e("NetworkInfo", "Not connected to a network.");
            }
        }
        catch (Exception e){
            Log.e("PostActivity", e.getMessage());
        }
    }

    public void handlePostTask(String response){
        try {
            JSONObject json = new JSONObject(response);

            if(!json.has("error")) {
                if(json.has("status") && json.getBoolean("status")){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
            else {
                // If token expires return to login.
                if(json.getString("error").equals("token_not_provided") || json.getString("error").equals("token_expired")){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.e("ResponseError", json.getString("error"));
                }
            }
        }
        catch (JSONException e){
            Log.e("Error", e.getMessage());
            Log.e("Response", response);
            Log.e("JSONException", "Invalid string response.");
        }
        catch(Exception e){
            if(response != null)
                Log.e("Response", response);

            Log.e("PostActivity", e.getMessage());
        }
    }

    public void handleTagsTask(String response, AutoCompleteTextView input) {
        try {
            JSONObject json = new JSONObject(response);

            if(!json.has("error")) {
                JSONArray tags = json.getJSONArray("result");

                JSONObject tag;
                Tag[] list = new Tag[tags.length()];
                for(int i = 0;i < list.length;i++){
                    tag = tags.getJSONObject(i);

                    String background   = (!tag.isNull("background_color"))? tag.getString("background_color") : null;
                    String text         = (!tag.isNull("text_color"))? tag.getString("text_color") : null;

                    list[i] = new Tag(tag.getString("id"), tag.getString("name"), background, text);
                }

                ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, list);
                input.setAdapter(adapter);
            }
            else {
                // If token expires return to login.
                if(json.getString("error").equals("token_not_provided") || json.getString("error").equals("token_expired")){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.e("ResponseError", json.getString("error"));
                }
            }
        }
        catch (JSONException e){
            Log.e("Error", e.getMessage());
            Log.e("Response", response);
            Log.e("JSONException", "Invalid string response.");
        }
        catch(Exception e){
            if(response != null)
                Log.e("Response", response);

            Log.e("PostActivity", e.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.attach_file:
                // Post Attach File actions
                return true;
            case R.id.attach_image:
                // Post Attach Image actions
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
