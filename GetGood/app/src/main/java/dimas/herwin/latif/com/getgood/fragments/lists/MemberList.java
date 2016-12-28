package dimas.herwin.latif.com.getgood.fragments.lists;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dimas.herwin.latif.com.getgood.fragments.items.Member;

public class MemberList {

    public List<Member>         items;
    public Map<String, Member>  itemMap;

    public MemberList(String json){
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

    private void addItem(Member item) {
        items.add(item);
        itemMap.put(item.id, item);
    }

    private void addItem(JSONObject jsonObject){
        Member item = new Member(jsonObject);
        addItem(item);
    }
}
