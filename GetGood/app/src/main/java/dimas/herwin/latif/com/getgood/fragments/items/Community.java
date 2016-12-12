package dimas.herwin.latif.com.getgood.fragments.items;

import android.util.Log;

import com.plumillonforge.android.chipview.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Community {
    public final String id;
    public final String name;
    public final String desc;
    public final String image;

    public final List<Chip> tagChips;

    public Community(JSONObject community){
        id      = community.optString("id");
        name    = community.optString("name");
        desc    = community.optString("description");
        image   = community.optString("image");

        tagChips    = new ArrayList<>();
        try {
            // Build tags
            JSONArray tags = community.getJSONArray("tags");
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
            Log.e("Community", "JSONException");
            Log.e("JSONException", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
