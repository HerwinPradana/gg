package dimas.herwin.latif.com.getgood.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dimas.herwin.latif.com.getgood.LoginActivity;
import dimas.herwin.latif.com.getgood.PostActivity;
import dimas.herwin.latif.com.getgood.ProfileActivity;
import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.tasks.AsyncTaskListener;
import dimas.herwin.latif.com.getgood.tasks.HttpTask;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {

    private OnFragmentInteractionListener listener;
    private View view;
    private SharedPreferences sharedPreferences;
    private String userId = null;

    FloatingActionButton addPostBtn;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.app_pref), MODE_PRIVATE);

        if(getArguments() != null)
            userId = getArguments().getString(ProfileActivity.USER_ID, null);
    }

    private void loadFeeds() {
        if(view.findViewById(R.id.list_post) != null){
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()){
                if(userId == null){
                    String url = "http://" + getString(R.string.server_address) + "/ggwp/public/api/post/interests";

                    new HttpTask(new AsyncTaskListener() {
                        @Override
                        public void onTaskCompleted(String response) {
                            handleGetPostTask(response);
                        }
                    }).execute(url, "POST", null, sharedPreferences.getString("token", null));
                }
                else{
                    String url        = "http://" + getString(R.string.server_address) + "/ggwp/public/api/post/users";
                    String parameters = "id=" + userId;

                    new HttpTask(new AsyncTaskListener() {
                        @Override
                        public void onTaskCompleted(String response) {
                            handleGetPostTask(response);
                        }
                    }).execute(url, "POST", parameters, sharedPreferences.getString("token", null));
                }
            }
            else {
                Log.e("CONNECTION: ", "NOT CONNECTED");
            }
        }
    }

    public void handleGetPostTask(String response) {
        try {
            JSONObject json = new JSONObject(response);

            if(!json.has("error")){
                JSONArray posts = json.getJSONArray("result");

                PostFragment postFragment = new PostFragment();

                Bundle args = new Bundle();
                args.putString(PostFragment.ARG_JSON, posts.toString());

                postFragment.setArguments(args);

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.list_post, postFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            else {
                // If token expires return to login.
                if(json.getString("error").equals("token_not_provided") || json.getString("error").equals("token_expired")){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.e("ResponseError", json.getString("error"));
                }
            }
        }
        catch (JSONException e){
            Log.d("Response", response);
            Log.e("FeedFragment", e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);

//        String image = sharedPreferences.getString("user_image", null);

//        if(image != null) {
//            ImageView userImageView = (ImageView) view.findViewById(R.id.user_image);
//            Picasso.with(getActivity()).load("http://" + getString(R.string.server_address) + "/ggwp/public/images/users/" + image).placeholder(R.mipmap.placeholder).into(userImageView);
//        }

        if(savedInstanceState == null)
            loadFeeds();


        // Add Post Action Button Event Handler
        addPostBtn = (FloatingActionButton) view.findViewById(R.id.add_post_action_btn);
        if(addPostBtn != null) {
            addPostBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), PostActivity.class));
                }
            });
        }

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
        void onFragmentInteraction(Uri uri);
    }
}
