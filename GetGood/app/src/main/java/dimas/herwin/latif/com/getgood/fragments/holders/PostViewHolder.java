package dimas.herwin.latif.com.getgood.fragments.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.plumillonforge.android.chipview.ChipView;
import com.squareup.picasso.Picasso;

import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.fragments.adapters.TagChipViewAdapter;
import dimas.herwin.latif.com.getgood.fragments.items.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private Context context;

    private final View view;
    private final TextView contentView;
    private final TextView  createdAtView;
    private final ImageView userImageView;
    private final TextView  userNameView;
    private final ChipView  tagsView;
    private final ImageView mainImageView;
    private final ImageView subImageView1;
    private final ImageView subImageView2;
    private Post            item;

    public PostViewHolder(View view, Context context) {
        super(view);

        this.context    = context;
        this.view       = view;
        contentView     = (TextView) view.findViewById(R.id.content);
        createdAtView   = (TextView) view.findViewById(R.id.created_at);
        userImageView   = (ImageView) view.findViewById(R.id.user_image);
        userNameView    = (TextView) view.findViewById(R.id.user_name);
        tagsView        = (ChipView) view.findViewById(R.id.tags);
        mainImageView   = (ImageView) view.findViewById(R.id.main_image);
        subImageView1   = (ImageView) view.findViewById(R.id.sub_image_1);
        subImageView2   = (ImageView) view.findViewById(R.id.sub_image_2);

        tagsView.setAdapter(new TagChipViewAdapter(context));
    }

    public void setItem(Post item){
        this.item = item;

        contentView.setText(item.content);
        createdAtView.setText(item.createdAt);
        userNameView.setText(item.userName);

        //Picasso.with(context).setLoggingEnabled(true);
        String server = context.getResources().getString(R.string.server_address);
        Picasso.with(context).load("http://" + server + "/ggwp/public/images/users/" + item.userImage).placeholder(R.mipmap.placeholder).into(userImageView);

        if(!item.tagChips.isEmpty())
            tagsView.setChipList(item.tagChips);

        if(!item.imageList.isEmpty()) {
            int nImages = item.imageList.size();

            Picasso.with(context).load("http://" + server + "/ggwp/public/images/posts/" + item.imageList.get(0)).into(mainImageView);

            if(nImages > 1)
                Picasso.with(context).load("http://" + server + "/ggwp/public/images/posts/" + item.imageList.get(1)).into(subImageView1);

            if(nImages > 2)
                Picasso.with(context).load("http://" + server + "/ggwp/public/images/posts/" + item.imageList.get(2)).into(subImageView2);
        }
    }

    public void cleanup(){
        userImageView.setImageResource(R.mipmap.placeholder);

        Picasso.with(context).cancelRequest(mainImageView);
        Picasso.with(context).cancelRequest(subImageView1);
        Picasso.with(context).cancelRequest(subImageView2);

        mainImageView.setImageDrawable(null);
        subImageView1.setImageDrawable(null);
        subImageView2.setImageDrawable(null);
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