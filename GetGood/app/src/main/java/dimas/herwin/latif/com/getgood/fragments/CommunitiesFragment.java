package dimas.herwin.latif.com.getgood.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.tasks.AsyncTaskListener;
import dimas.herwin.latif.com.getgood.tasks.HttpTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommunitiesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CommunitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunitiesFragment extends Fragment{

    private OnFragmentInteractionListener listener;
    private View view;

    public CommunitiesFragment() {
        // Required empty public constructor
    }

    public static CommunitiesFragment newInstance() {
        return new CommunitiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadFeeds() {
        if(view.findViewById(R.id.list_community) != null){
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo         = connectivityManager.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()){
                String url = "http://" + getString(R.string.server_address) + "/ggwp/public/api/auth/login?email=herwinpradana@gmail.com";

                new HttpTask(new AsyncTaskListener() {
                    @Override
                    public void onTaskCompleted(String response) {
                        handleGetCommunitiesTask(response);
                    }
                }).execute(url, "POST", "");
            }
            else {
                Log.e("CONNECTION: ", "NOT CONNECTED");
            }
        }
    }

    public void handleGetCommunitiesTask(String response) {
        try {
            response = "[{\"id\" : 1, \"name\": \"Archery Community\", \"desc\": \"We do traditional archery.\", \"image\": \"archery.jpg\"}]";

            CommunityFragment communityFragment = new CommunityFragment();

            Bundle args = new Bundle();
            args.putString(CommunityFragment.ARG_JSON, response);

            communityFragment.setArguments(args);

            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.list_community, communityFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        catch (IllegalStateException error){
            Log.e("Communities Fragment", error.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_communities, container, false);

        if(savedInstanceState == null)
            loadFeeds();

        // Inflate the layout for this fragment
        return view;
    }

    // TO-DO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
