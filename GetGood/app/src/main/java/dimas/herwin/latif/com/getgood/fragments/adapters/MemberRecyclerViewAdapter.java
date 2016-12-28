package dimas.herwin.latif.com.getgood.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.fragments.MemberFragment;
import dimas.herwin.latif.com.getgood.fragments.holders.MemberViewHolder;
import dimas.herwin.latif.com.getgood.fragments.items.Member;

public class MemberRecyclerViewAdapter extends RecyclerView.Adapter<MemberViewHolder> {

    private final List<Member> items;
    private final MemberFragment.OnListFragmentInteractionListener listener;
    private Context context;

    public MemberRecyclerViewAdapter(List<Member> items, MemberFragment.OnListFragmentInteractionListener listener, Context context) {
        this.items    = items;
        this.listener = listener;
        this.context  = context;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_member, parent, false);
        return new MemberViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final MemberViewHolder holder, int position) {
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
    public void onViewRecycled(final MemberViewHolder holder) {
        holder.cleanup();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
