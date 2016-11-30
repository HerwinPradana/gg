package dimas.herwin.latif.com.getgood.fragments.items;

import org.json.JSONObject;

public class Community {
    public final String id;
    public final String name;
    public final String desc;
    public final String image;

    public Community(JSONObject jsonObject){
        id      = jsonObject.optString("id");
        name    = jsonObject.optString("name");
        desc    = jsonObject.optString("desc");
        image   = jsonObject.optString("image");
    }

    @Override
    public String toString() {
        return name;
    }

}
