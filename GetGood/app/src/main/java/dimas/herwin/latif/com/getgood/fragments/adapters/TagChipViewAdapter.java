package dimas.herwin.latif.com.getgood.fragments.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.plumillonforge.android.chipview.ChipViewAdapter;

import dimas.herwin.latif.com.getgood.fragments.items.Tag;

public class TagChipViewAdapter extends ChipViewAdapter{

    public TagChipViewAdapter(Context context){
        super(context);
    }

    @Override
    public int getLayoutRes(int position) {
        return 0;
    }

    @Override
    public int getBackgroundRes(int position) {
        return 0;
    }

    @Override
    public int getBackgroundColor(int position) {
        Tag tag = (Tag) getChip(position);

        return Color.parseColor(tag.getBackgroundColor());
    }

    @Override
    public int getBackgroundColorSelected(int position) {
        return 0;
    }

    @Override
    public void onLayout(View view, int position) {
        Tag tag = (Tag) getChip(position);

        ((TextView) view.findViewById(android.R.id.text1)).setTextColor(Color.parseColor(tag.getTextColor()));
    }
}
