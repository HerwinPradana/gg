package dimas.herwin.latif.com.getgood.fragments.items;

import com.plumillonforge.android.chipview.Chip;

public class Tag implements Chip {

    private String name;

    public Tag(String name){
        this.name = name;
    }

    @Override
    public String getText(){
        return name;
    }
}
