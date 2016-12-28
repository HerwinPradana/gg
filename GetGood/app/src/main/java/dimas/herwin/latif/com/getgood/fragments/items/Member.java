package dimas.herwin.latif.com.getgood.fragments.items;

import android.util.Log;

import com.plumillonforge.android.chipview.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Member {
    public final String id;
    public final String name;
    public final String image;

    public Member(JSONObject community){
        id      = community.optString("id");
        name    = community.optString("name");
        image   = community.optString("image");
    }

    @Override
    public String toString() {
        return name;
    }

}
