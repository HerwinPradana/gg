package dimas.herwin.latif.com.getgood.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.fragments.adapters.PostRecyclerViewAdapter;
import dimas.herwin.latif.com.getgood.fragments.lists.PostList;
import dimas.herwin.latif.com.getgood.fragments.items.Post;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PostFragment extends Fragment {

    public static final String ARG_JSON = "dimas.herwin.latif.com.getgood.post-data";

    private String          json;
    private PostList        postList;
    private RecyclerView    recyclerView;

    private OnListFragmentInteractionListener listener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            json = getArguments().getString(ARG_JSON);
        }

        postList = new PostList(json);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);
        View list = view.findViewById(R.id.list);

        // Set the adapter
        if (list instanceof RecyclerView) {
            Context context = list.getContext();

            recyclerView = (RecyclerView) list;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            if(postList.items.size() == 0)
                view.findViewById(R.id.no_posts).setVisibility(View.VISIBLE);

            recyclerView.setAdapter(new PostRecyclerViewAdapter(postList.items, listener, context));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        recyclerView.setAdapter(null);
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Post item);
    }
}
