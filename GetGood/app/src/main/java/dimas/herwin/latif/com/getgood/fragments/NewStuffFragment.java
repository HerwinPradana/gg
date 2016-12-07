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
 * {@link NewStuffFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewStuffFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewStuffFragment extends Fragment {

    private OnFragmentInteractionListener listener;
    private View view;

    public NewStuffFragment() {
        // Required empty public constructor
    }

    public static NewStuffFragment newInstance() {
        return new NewStuffFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadTimeline() {
        if(view.findViewById(R.id.list_post) != null){
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo         = connectivityManager.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()){
                String url = "http://" + getString(R.string.server_address) + "/ggwp/public/api/auth/login?email=herwinpradana@gmail.com";

                new HttpTask(new AsyncTaskListener() {
                    @Override
                    public void onTaskCompleted(String response) {
                        handleGetPostTask(response);
                    }
                }).execute(url, "POST", "");
            }
            else {
                Log.e("CONNECTION: ", "NOT CONNECTED");
            }
        }
    }

    public void handleGetPostTask(String response) {
        try {
            response = "[{\"id\" : 1, \"content\": \"This post is about a hobby you haven't discovered.\", \"image\": \"fight.png\", \"created_at\": \"Yesterday at 8:21 AM\", \"user_id\" : 1, \"user_name\": \"Oberyn Martell\", \"user_image\": \"oberyn-martell.jpg\"}, {\"id\" : 2, \"content\": \"Man, this coaching group is great.\", \"image\": \"random.png\", \"created_at\": \"15-11-2016 at 7:14 PM\", \"user_id\" : 2, \"user_name\": \"Lord Popo\", \"user_image\": \"popo.png\"}]";

            PostFragment postFragment = new PostFragment();

            Bundle args = new Bundle();
            args.putString(PostFragment.ARG_JSON, response);

            postFragment.setArguments(args);

            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.list_post, postFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        catch (IllegalStateException error){
            Log.e("Timeline Fragment", error.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);

        if(savedInstanceState == null)
            loadTimeline();

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
