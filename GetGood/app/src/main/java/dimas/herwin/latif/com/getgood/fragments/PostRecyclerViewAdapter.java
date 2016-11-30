package dimas.herwin.latif.com.getgood.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dimas.herwin.latif.com.getgood.R;
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
        private final View       view;
        private final TextView   titleView;
        private final TextView   contentView;
        private final ImageView imageView;
        public Post             item;

        public ViewHolder(View view) {
            super(view);
            this.view   = view;
            titleView   = (TextView) view.findViewById(R.id.title);
            contentView = (TextView) view.findViewById(R.id.content);
            imageView   = (ImageView) view.findViewById(R.id.image);
        }

        public void setItem(Post item){
            this.item = item;
            this.titleView.setText(item.title);
            this.contentView.setText(item.content);
            Picasso.with(context).load("http://goku.ngeartstudio.com/images/" + item.image).into(imageView);
        }

        public void cleanup(){
            Picasso.with(context).cancelRequest(imageView);
            imageView.setImageDrawable(null);
        }

        public View getView(){
            return view;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "'";
        }
    }
}
