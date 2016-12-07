package dimas.herwin.latif.com.getgood.fragments.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.fragments.adapters.TagChipViewAdapter;
import dimas.herwin.latif.com.getgood.fragments.items.Post;
import dimas.herwin.latif.com.getgood.fragments.items.Tag;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private Context context;

    private final View view;
    private final TextView contentView;
    private final TextView  createdAtView;
    private final ImageView imageView;
    private final ImageView userImageView;
    private final TextView  userNameView;
    private final ChipView tagsView;
    private Post  item;

    public PostViewHolder(View view, Context context) {
        super(view);

        this.context    = context;
        this.view       = view;
        contentView     = (TextView) view.findViewById(R.id.content);
        createdAtView   = (TextView) view.findViewById(R.id.created_at);
        imageView       = (ImageView) view.findViewById(R.id.image);
        userImageView   = (ImageView) view.findViewById(R.id.user_image);
        userNameView    = (TextView) view.findViewById(R.id.user_name);
        tagsView        = (ChipView) view.findViewById(R.id.tags);

        tagsView.setAdapter(new TagChipViewAdapter(context));
    }

    public void setItem(Post item){
        this.item = item;

        contentView.setText(item.content);
        createdAtView.setText(item.createdAt);
        userNameView.setText(item.userName);

        if(item.isTutorial) {
            List<Chip> chipList = new ArrayList<>();
            chipList.add(new Tag("Tutorial"));

            tagsView.setChipList(chipList);
        }

        String server = context.getResources().getString(R.string.server_address);

        //Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load("http://" +  server + "/ggwp/public/images/posts/" + item.image).into(imageView);
        Picasso.with(context).load("http://" + server + "/ggwp/public/images/users/" + item.userImage).placeholder(R.mipmap.placeholder).into(userImageView);
    }

    public void cleanup(){
        Picasso.with(context).cancelRequest(imageView);
        imageView.setImageDrawable(null);
        userImageView.setImageResource(R.mipmap.placeholder);
    }

    public View getView(){
        return view;
    }

    public Post getItem(){
        return item;
    }

    @Override
    public String toString() {
        return super.toString() + " '" + contentView.getText() + "'";
    }
}