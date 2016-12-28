package dimas.herwin.latif.com.getgood.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dimas.herwin.latif.com.getgood.CommunityActivity;
import dimas.herwin.latif.com.getgood.LoginActivity;
import dimas.herwin.latif.com.getgood.R;
import dimas.herwin.latif.com.getgood.tasks.AsyncTaskListener;
import dimas.herwin.latif.com.getgood.tasks.HttpTask;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MembersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MembersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MembersFragment extends Fragment{

    private OnFragmentInteractionListener listener;
    private View view;
    private SharedPreferences sharedPreferences;
    private String communityId = null;

    public MembersFragment() {
        // Required empty public constructor
    }

    public static MembersFragment newInstance() {
        return new MembersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.app_pref), MODE_PRIVATE);

        if(getArguments() != null)
            communityId = getArguments().getString(CommunityActivity.COMMUNITY_ID, null);
    }

    private void loadFeeds() {
        if(view.findViewById(R.id.list_member) != null){
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo         = connectivityManager.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()){
                String url = "http://" + getString(R.string.server_address) + "/ggwp/public/api/community/members";
                String parameters = "";

                if(this.communityId != null) {
                    parameters = "community_id=" + communityId;
                }
                new HttpTask(new AsyncTaskListener() {
                    @Override
                    public void onTaskCompleted(String response) {
                        handleGetMembersTask(response);
                    }
                }).execute(url, "POST", parameters, sharedPreferences.getString("token", null));
            }
            else {
                Log.e("CONNECTION: ", "NOT CONNECTED");
            }
        }
    }

    public void handleGetMembersTask(String response) {
        try {
            JSONObject json = new JSONObject(response);

            if(!json.has("error")) {
                JSONArray members = json.getJSONArray("result");

                MemberFragment memberFragment = new MemberFragment();

                Bundle args = new Bundle();
                args.putString(MemberFragment.ARG_JSON, members.toString());

                memberFragment.setArguments(args);

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.list_member, memberFragment);
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
        catch (JSONException error){
            Log.d("Response", response);
            Log.e("Members Fragment", error.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_members, container, false);

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
