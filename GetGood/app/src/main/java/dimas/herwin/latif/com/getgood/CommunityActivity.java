package dimas.herwin.latif.com.getgood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import dimas.herwin.latif.com.getgood.fragments.AboutFragment;
import dimas.herwin.latif.com.getgood.fragments.FeedFragment;
import dimas.herwin.latif.com.getgood.fragments.MemberFragment;
import dimas.herwin.latif.com.getgood.fragments.MembersFragment;
import dimas.herwin.latif.com.getgood.fragments.PostFragment;
import dimas.herwin.latif.com.getgood.fragments.items.Member;
import dimas.herwin.latif.com.getgood.fragments.items.Post;
import dimas.herwin.latif.com.getgood.tasks.AsyncTaskListener;
import dimas.herwin.latif.com.getgood.tasks.HttpTask;

public class CommunityActivity extends AppCompatActivity implements
        FeedFragment.OnFragmentInteractionListener,
        PostFragment.OnListFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener,
        MembersFragment.OnFragmentInteractionListener,
        MemberFragment.OnListFragmentInteractionListener
{

    public final static String COMMUNITY_ID = "dimas.herwin.latif.com.getgood.COMMUNITY_ID";
    private String communityId = null;

    private TextView    nameView;
    private ImageView   imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);

        communityId = getIntent().getStringExtra(COMMUNITY_ID);

        nameView    = (TextView) findViewById(R.id.name);
        imageView   = (ImageView) findViewById(R.id.image);

        SharedPreferences sharedPreferences     = getSharedPreferences(getString(R.string.app_pref), MODE_PRIVATE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo                 = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            String url    = "http://" + getString(R.string.server_address) + "/ggwp/public/api/community/detail";
            String params = "id=" + communityId;

            new HttpTask(new AsyncTaskListener() {
                @Override
                public void onTaskCompleted(String response) {
                    handleGetCommunityTask(response);
                }
            }).execute(url, "POST", params, sharedPreferences.getString("token", null));
        }
        else {
            Log.e("CONNECTION: ", "NOT CONNECTED");
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        CommunityActivity.SectionsPagerAdapter sectionsPagerAdapter = new CommunityActivity.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        if(viewPager != null)
            viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.profile_tabs);
        if(tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);

            TabLayout.Tab feedTab   = tabLayout.getTabAt(0);
            TabLayout.Tab memberTab = tabLayout.getTabAt(1);
            TabLayout.Tab aboutTab  = tabLayout.getTabAt(2);

            if(feedTab != null) {
                feedTab.setIcon(R.drawable.ic_art_track_black_24dp);
                Drawable icon = feedTab.getIcon();
                if(icon != null)
                    icon.setTint(ResourcesCompat.getColor(getResources(), R.color.red_500, null));
            }
            if(memberTab != null) {
                memberTab.setIcon(R.drawable.ic_group_black_24dp);
                Drawable icon = memberTab.getIcon();
                if(icon != null)
                    icon.setTint(ResourcesCompat.getColor(getResources(), R.color.grey_500, null));
            }
            if(aboutTab != null) {
                aboutTab.setIcon(R.drawable.ic_info_outline_black_24dp);
                Drawable icon = aboutTab.getIcon();
                if(icon != null)
                    icon.setTint(ResourcesCompat.getColor(getResources(), R.color.grey_500, null));
            }

            tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    super.onTabSelected(tab);
                    int color = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
                    Drawable icon = tab.getIcon();
                    if(icon != null)
                        icon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    super.onTabUnselected(tab);
                    int color = ResourcesCompat.getColor(getResources(), R.color.grey_500, null);
                    Drawable icon = tab.getIcon();
                    if(icon != null)
                        icon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    super.onTabReselected(tab);
                }
            });

        }

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void handleGetCommunityTask(String response) {
        try {
            JSONObject json = new JSONObject(response);

            if(!json.has("error")){
                JSONObject community = json.getJSONObject("result");

                nameView.setText(community.getString("name"));

                String server = getString(R.string.server_address);
                Picasso.with(this).load("http://" + server + "/ggwp/public/images/communities/" + community.getString("image")).placeholder(R.mipmap.placeholder).into(imageView);
                //Picasso.with(this).load("http://" + server + "/ggwp/public/images/banners/" + user.getString("banner")).placeholder(R.mipmap.placeholder_banner).into(bannerView);
            }
            else {
                // If token expires return to login.
                if(json.getString("error").equals("token_not_provided") || json.getString("error").equals("token_expired")){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.e("ResponseError", json.getString("error"));
                }
            }
        }
        catch (JSONException e){
            Log.d("Response", response);
            Log.e("CommunityActivity", e.getMessage());
        }
    }

    public void onFragmentInteraction(Uri uri){

    }

    public void onListFragmentInteraction(Post post){

    }

    public void onListFragmentInteraction(Member member){

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private FeedFragment    feedFragment;
        private MembersFragment usersFragment;
        private AboutFragment   aboutFragment;

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            feedFragment  = FeedFragment.newInstance();
            usersFragment = MembersFragment.newInstance();
            aboutFragment = AboutFragment.newInstance();

            Bundle feedBundle = new Bundle();
            feedBundle.putString(COMMUNITY_ID, communityId);

            feedFragment.setArguments(feedBundle);
            usersFragment.setArguments(feedBundle);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return feedFragment;
                case 1:
                    return usersFragment;
                case 2:
                    return aboutFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
