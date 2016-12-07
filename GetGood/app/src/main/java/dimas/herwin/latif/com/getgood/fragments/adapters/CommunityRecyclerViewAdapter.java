package dimas.herwin.latif.com.getgood.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.fragments.CommunityFragment;
import dimas.herwin.latif.com.getgood.fragments.holders.CommunityViewHolder;
import dimas.herwin.latif.com.getgood.fragments.items.Community;

public class CommunityRecyclerViewAdapter extends RecyclerView.Adapter<CommunityViewHolder> {

    private final List<Community> items;
    private final CommunityFragment.OnListFragmentInteractionListener listener;
    private Context context;

    public CommunityRecyclerViewAdapter(List<Community> items, CommunityFragment.OnListFragmentInteractionListener listener, Context context) {
        this.items    = items;
        this.listener = listener;
        this.context  = context;
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_community, parent, false);
        return new CommunityViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final CommunityViewHolder holder, int position) {
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
    public void onViewRecycled(final CommunityViewHolder holder) {
        holder.cleanup();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
