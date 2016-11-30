package dimas.herwin.latif.com.getgood.fragments.items;

public class Post {
    public final String id;
    public final String title;
    public final String content;
    public final String image;

    public Post(String id, String title, String content, String image){
        this.id      = id;
        this.title   = title;
        this.content = content;
        this.image   = image;
    }

    @Override
    public String toString() {
        return title;
    }

}
