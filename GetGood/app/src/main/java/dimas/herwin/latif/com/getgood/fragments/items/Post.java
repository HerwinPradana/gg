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

    public  final List<Chip>    tagChips;
    public  final List<String>  imageList;

    public Post(JSONObject post){
        id          = post.optString("id");
        content     = post.optString("content");
        image       = post.optString("image");
        createdAt   = post.optString("created_at");

        tagChips    = new ArrayList<>();
        imageList   = new ArrayList<>();

        JSONObject user = new JSONObject();
        try {
            user = post.getJSONObject("user");

            // Build tags
            JSONArray  tags = post.getJSONArray("tags");
            JSONObject tag;

            int nTags = tags.length();
            for (int i = 0; i < nTags; i++) {
                tag = tags.getJSONObject(i);

                String background   = (!tag.isNull("background_color"))? tag.getString("background_color") : null;
                String text         = (!tag.isNull("text_color"))? tag.getString("text_color") : null;

                tagChips.add(new Tag(tag.getString("id"), tag.getString("name"), background, text));
            }

            // Build tags
            JSONArray  communities = post.getJSONArray("communities");
            JSONObject community;

            int nCommunities = communities.length();
            for (int i = 0; i < nCommunities; i++) {
                community = communities.getJSONObject(i);

                String background   = (!community.isNull("background_color"))? community.getString("background_color") : null;
                String text         = (!community.isNull("text_color"))? community.getString("text_color") : null;

                tagChips.add(new Tag(community.getString("id"), community.getString("name"), background, text));
            }

            // Build images
            JSONArray  images = post.getJSONArray("images");
            JSONObject image;

            int nImages = images.length();
            for (int i = 0; i < nImages; i++) {
                image = images.getJSONObject(i);
                imageList.add(image.getString("file"));
            }
        }
        catch (JSONException e){
            Log.e("Post", "JSONException");
            Log.e("JSONException", e.getMessage());
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
