package dimas.herwin.latif.com.getgood.fragments.items;

import com.plumillonforge.android.chipview.Chip;

public class Tag implements Chip {

    private String name;

    private String textColor        = "#FFFFFF";
    private String backgroundColor  = "#F44336";

    public Tag(String name){
        this.name = name;
    }

    public Tag(String name, String backgroundColor){
        this(name);
        this.backgroundColor = backgroundColor;
    }

    public Tag(String name, String backgroundColor, String textColor){
        this(name, backgroundColor);
        this.textColor = textColor;
    }

    @Override
    public String getText(){
        return name;
    }

    public String getBackgroundColor(){
        return backgroundColor;
    }

    public String getTextColor(){
        return textColor;
    }
}
