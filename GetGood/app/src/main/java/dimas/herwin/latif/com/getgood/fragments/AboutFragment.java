package dimas.herwin.latif.com.getgood.fragments;

/**
 * Created by Yanoo on 12/14/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dimas.herwin.latif.com.getgood.ProfileActivity;
import dimas.herwin.latif.com.getgood.R;

import static android.content.Context.MODE_PRIVATE;

public class AboutFragment extends Fragment{

    private View view;
    private SharedPreferences sharedPreferences;
    private AboutFragment.OnFragmentInteractionListener listener;
    private String userId = null;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.app_pref), MODE_PRIVATE);

        if(getArguments() != null)
            userId = getArguments().getString(ProfileActivity.USER_ID, null);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);

//        String image = sharedPreferences.getString("user_image", null);

//        if(image != null) {
//            ImageView userImageView = (ImageView) view.findViewById(R.id.user_image);
//            Picasso.with(getActivity()).load("http://" + getString(R.string.server_address) + "/ggwp/public/images/users/" + image).placeholder(R.mipmap.placeholder).into(userImageView);
//        }

        if(savedInstanceState == null)
            loadInformation();

        // Inflate the layout for this fragment
        return view;
    }

    private void loadInformation(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AboutFragment.OnFragmentInteractionListener) {
            listener = (AboutFragment.OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
