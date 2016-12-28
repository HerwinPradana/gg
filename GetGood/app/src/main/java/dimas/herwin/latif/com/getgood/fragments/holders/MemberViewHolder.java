package dimas.herwin.latif.com.getgood.fragments.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dimas.herwin.latif.com.getgood.ProfileActivity;
import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.fragments.items.Member;

public class MemberViewHolder extends RecyclerView.ViewHolder {
    private Context context;

    private final View      view;
    private final TextView  nameView;
    private final ImageView imageView;

    private Member item;

    public MemberViewHolder(View view, Context context) {
        super(view);

        this.context = context;
        this.view    = view;
        nameView     = (TextView) view.findViewById(R.id.name);
        imageView    = (ImageView) view.findViewById(R.id.image);
    }

    public void setItem(final Member item){
        this.item = item;

        nameView.setText(item.name);

        String server = context.getResources().getString(R.string.server_address);

        //Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load("http://" +  server + "/ggwp/public/images/users/" + item.image).placeholder(R.mipmap.placeholder).into(imageView);

        nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(ProfileActivity.USER_ID, item.id);

                context.startActivity(intent);
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

    public Member getItem(){
        return item;
    }

    @Override
    public String toString() {
        return super.toString() + " '" + nameView.getText() + "'";
    }
}
