package dimas.herwin.latif.com.getgood.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.fragments.PostFragment;
import dimas.herwin.latif.com.getgood.fragments.items.Post;

import java.util.List;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {

    private final List<Post> items;
    private final PostFragment.OnListFragmentInteractionListener listener;
    private Context context;

    public PostRecyclerViewAdapter(List<Post> items, PostFragment.OnListFragmentInteractionListener listener, Context context) {
        this.items    = items;
        this.listener = listener;
        this.context  = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setItem(items.get(position));

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    @Override
    public void onViewRecycled(final ViewHolder holder) {
        holder.cleanup();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View      view;
        private final TextView  contentView;
        private final TextView  createdAtView;
        private final ImageView imageView;
        private final ImageView userImageView;
        private final TextView  userNameView;
        public Post             item;

        public ViewHolder(View view) {
            super(view);
            this.view       = view;
            contentView     = (TextView) view.findViewById(R.id.content);
            createdAtView   = (TextView) view.findViewById(R.id.created_at);
            imageView       = (ImageView) view.findViewById(R.id.image);
            userImageView   = (ImageView) view.findViewById(R.id.user_image);
            userNameView    = (TextView) view.findViewById(R.id.user_name);
        }

        public void setItem(Post item){
            this.item = item;

            contentView.setText(item.content);
            createdAtView.setText(item.createdAt);
            userNameView.setText(item.userName);

            //Picasso.with(context).setLoggingEnabled(true);
            Picasso.with(context).load("http://192.168.43.111/ggwp/public/images/posts/" + item.image).into(imageView);
            Picasso.with(context).load("http://192.168.43.111/ggwp/public/images/users/" + item.userImage).placeholder(R.mipmap.placeholder).into(userImageView);
        }

        public void cleanup(){
            Picasso.with(context).cancelRequest(imageView);
            imageView.setImageDrawable(null);
            userImageView.setImageResource(R.mipmap.placeholder);
        }

        public View getView(){
            return view;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }
    }
}
