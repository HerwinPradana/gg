package dimas.herwin.latif.com.getgood.fragments.items;

import com.plumillonforge.android.chipview.Chip;

public class Tag implements Chip {

    private String id;
    private String name;

    private String textColor        = "#FFFFFF";
    private String backgroundColor  = "#F44336";

    public Tag(String id, String name, String backgroundColor, String textColor){
        this.id = id;
        this.name = name;

        if(backgroundColor != null)
            this.backgroundColor = backgroundColor;

        if(textColor != null)
            this.textColor = textColor;
    }

    @Override
    public String getText(){
        return name;
    }

    @Override
    public String toString(){
        return name;
    }

    public String getId(){
        return id;
    }

    public String getBackgroundColor(){
        return backgroundColor;
    }

    public String getTextColor(){
        return textColor;
    }
}
