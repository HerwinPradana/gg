package dimas.herwin.latif.com.getgood.fragments.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.fragments.items.Community;

public class CommunityViewHolder extends RecyclerView.ViewHolder {
    private Context context;

    private final View view;
    private final TextView nameView;
    private final TextView  descView;
    private final ImageView imageView;
    private final Button detailButton;
    private final Button    joinButon;
    private Community item;

    public CommunityViewHolder(View view, Context context) {
        super(view);

        this.context = context;
        this.view    = view;
        nameView     = (TextView) view.findViewById(R.id.name);
        descView     = (TextView) view.findViewById(R.id.desc);
        imageView    = (ImageView) view.findViewById(R.id.image);
        detailButton = (Button) view.findViewById(R.id.detail);
        joinButon    = (Button) view.findViewById(R.id.join);
    }

    public void setItem(final Community item){
        this.item = item;

        nameView.setText(item.name);
        descView.setText(item.desc);

        String server = context.getResources().getString(R.string.server_address);

        //Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load("http://" +  server + "/ggwp/public/images/communities/" + item.image).placeholder(R.mipmap.placeholder).into(imageView);

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK", "DETAIL " + item.id);
            }
        });

        joinButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("CLICK", "JOIN " + item.id);
            }
        });
    }

    public void cleanup(){
        Picasso.with(context).cancelRequest(imageView);
        imageView.setImageResource(R.mipmap.placeholder);
    }

    public View getView(){
        return view;
    }

    public Community getItem(){
        return item;
    }

    @Override
    public String toString() {
        return super.toString() + " '" + nameView.getText() + "'";
    }
}
