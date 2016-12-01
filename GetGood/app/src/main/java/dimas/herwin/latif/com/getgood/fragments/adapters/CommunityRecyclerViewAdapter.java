package dimas.herwin.latif.com.getgood.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.fragments.CommunityFragment;
import dimas.herwin.latif.com.getgood.fragments.items.Community;

public class CommunityRecyclerViewAdapter extends RecyclerView.Adapter<CommunityRecyclerViewAdapter.ViewHolder> {

    private final List<Community> items;
    private final CommunityFragment.OnListFragmentInteractionListener listener;
    private Context context;

    public CommunityRecyclerViewAdapter(List<Community> items, CommunityFragment.OnListFragmentInteractionListener listener, Context context) {
        this.items    = items;
        this.listener = listener;
        this.context  = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_community, parent, false);
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
        private final TextView  nameView;
        private final TextView  descView;
        private final ImageView imageView;
        private final Button    detailButton;
        private final Button    joinButon;
        public Community        item;

        public ViewHolder(View view) {
            super(view);
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

            //Picasso.with(context).setLoggingEnabled(true);
            Picasso.with(context).load("http://192.168.43.111/ggwp/public/images/communities/" + item.image).placeholder(R.mipmap.placeholder).into(imageView);

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

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'";
        }
    }
}
