package dimas.herwin.latif.com.getgood.fragments.lists;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dimas.herwin.latif.com.getgood.fragments.items.Community;
import dimas.herwin.latif.com.getgood.fragments.items.Post;

public class CommunityList {

    public List<Community>       items;
    public Map<String,Community> itemMap;

    public CommunityList(String json){
        items   = new ArrayList<>();
        itemMap = new HashMap<>();

        try{
            JSONArray posts = new JSONArray(json);

            // Add some sample items.
            int nPosts = posts.length();
            for (int i = 0; i < nPosts; i++) {
                addItem(posts.getJSONObject(i));
            }
        }
        catch (JSONException error){
            Log.e("COMMUNITY LIST", error.getMessage());
        }
    }

    private void addItem(Community item) {
        items.add(item);
        itemMap.put(item.id, item);
    }

    private void addItem(JSONObject jsonObject){
        Community item = new Community(jsonObject);
        addItem(item);
    }
}
