package dimas.herwin.latif.com.getgood.fragments.lists;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dimas.herwin.latif.com.getgood.fragments.items.Post;

public class PostList {

    public List<Post>       items;
    public Map<String,Post> itemMap;

    public PostList(String json){
        items   = new ArrayList<>();
        itemMap = new HashMap<>();

        try{
            JSONArray posts = new JSONArray(json);

            // Add some sample items.
            int nPosts = posts.length();
            for (int i = 0; i < nPosts; i++) {
                JSONObject post = posts.getJSONObject(i);

                String id       = post.optString("id");
                String title    = post.optString("title");
                String content  = post.optString("content");
                String image    = post.optString("image");

                addItem(id, title, content, image);
            }
        }
        catch (JSONException error){
            Log.e("POST LIST", error.getMessage());
        }
    }

    private void addItem(Post item) {
        items.add(item);
        itemMap.put(item.id, item);
    }

    private void addItem(String id, String title, String content, String image){
        Post item = new Post(id, title, content, image);
        addItem(item);
    }
}
