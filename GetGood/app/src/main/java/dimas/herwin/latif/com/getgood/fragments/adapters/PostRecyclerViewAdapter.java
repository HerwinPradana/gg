package dimas.herwin.latif.com.getgood.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.fragments.PostFragment;
import dimas.herwin.latif.com.getgood.fragments.holders.PostViewHolder;
import dimas.herwin.latif.com.getgood.fragments.items.Post;

import java.util.List;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private final List<Post> items;
    private final PostFragment.OnListFragmentInteractionListener listener;
    private Context context;

    public PostRecyclerViewAdapter(List<Post> items, PostFragment.OnListFragmentInteractionListener listener, Context context) {
        this.items    = items;
        this.listener = listener;
        this.context  = context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_post, parent, false);
        return new PostViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        holder.setItem(items.get(position));

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListFragmentInteraction(holder.getItem());
                }
            }
        });
    }

    @Override
    public void onViewRecycled(final PostViewHolder holder) {
        holder.cleanup();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
