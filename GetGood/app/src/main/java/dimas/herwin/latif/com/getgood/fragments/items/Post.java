package dimas.herwin.latif.com.getgood.fragments.items;

import android.util.Log;

import com.plumillonforge.android.chipview.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Post {
    public  final String     id;
    public  final String     content;
    public  final String     image;
    public  final String     createdAt;

    public  final String     userId;
    public  final String     userName;
    public  final String     userImage;

    public  final List<Chip> tagChips;

    public Post(JSONObject post){
        id          = post.optString("id");
        content     = post.optString("content");
        image       = post.optString("image");
        createdAt   = post.optString("created_at");

        tagChips = new ArrayList<>();

        if(post.optInt("is_tutorial") == 1)
            tagChips.add(new Tag("Tutorial"));

        JSONObject user = new JSONObject();
        try {
            user = post.getJSONObject("user");

            JSONArray  tags = post.getJSONArray("tags");
            JSONObject tag;

            int nTags = tags.length();
            for (int i = 0; i < nTags; i++) {
                tag = tags.getJSONObject(i);

                if(!tag.isNull("background_color") && !tag.isNull("text_color"))
                    tagChips.add(new Tag(tag.getString("name"), tag.getString("background_color"), tag.getString("text_color")));
                else if(!tag.isNull("background_color") && tag.isNull("text_color"))
                    tagChips.add(new Tag(tag.getString("name"), tag.getString("background_color")));
                else
                    tagChips.add(new Tag(tag.getString("name")));
            }
        }
        catch (JSONException e){
            Log.e("Post", "JSONException");
        }

        userId      = user.optString("id");
        userName    = user.optString("name");
        userImage   = user.optString("image");
    }

    @Override
    public String toString() {
        return content;
    }

}
