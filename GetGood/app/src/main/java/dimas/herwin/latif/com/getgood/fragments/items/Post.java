package dimas.herwin.latif.com.getgood.fragments.items;

import org.json.JSONObject;

public class Post {
    public final String     id;
    public final String     content;
    public final String     image;
    public final String     createdAt;
    public final String     userId;
    public final String     userName;
    public final String     userImage;
    public final boolean    isTutorial;

    public Post(JSONObject jsonObject){
        id          = jsonObject.optString("id");
        content     = jsonObject.optString("content");
        image       = jsonObject.optString("image");
        createdAt   = jsonObject.optString("created_at");
        userId      = jsonObject.optString("user_id");
        userName    = jsonObject.optString("user_name");
        userImage   = jsonObject.optString("user_image");
        isTutorial  = jsonObject.optBoolean("is_tutorial");
    }

    @Override
    public String toString() {
        return content;
    }

}
