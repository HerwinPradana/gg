package dimas.herwin.latif.com.getgood;

import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import dimas.herwin.latif.com.getgood.fragments.AboutFragment;
import dimas.herwin.latif.com.getgood.fragments.CommunitiesFragment;
import dimas.herwin.latif.com.getgood.fragments.CommunityFragment;
import dimas.herwin.latif.com.getgood.fragments.DiscoveryFragment;
import dimas.herwin.latif.com.getgood.fragments.FeedFragment;
import dimas.herwin.latif.com.getgood.fragments.PostFragment;
import dimas.herwin.latif.com.getgood.fragments.items.Community;
import dimas.herwin.latif.com.getgood.fragments.items.Post;

public class ProfileActivity extends AppCompatActivity implements
        FeedFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener,
        PostFragment.OnListFragmentInteractionListener,
        CommunityFragment.OnListFragmentInteractionListener,
        CommunitiesFragment.OnFragmentInteractionListener {

    public final static String USER_ID = "dimas.herwin.latif.com.getgood.USER_ID";
    private String userID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userID = getIntent().getStringExtra(USER_ID);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        ProfileActivity.SectionsPagerAdapter sectionsPagerAdapter = new ProfileActivity.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        if(viewPager != null)
            viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.profile_tabs);
        if(tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);

            TabLayout.Tab feedTab       = tabLayout.getTabAt(0);
            TabLayout.Tab communityTab  = tabLayout.getTabAt(1);
            TabLayout.Tab aboutTab      = tabLayout.getTabAt(2);

            if(feedTab != null) {
                feedTab.setIcon(R.drawable.ic_art_track_black_24dp);
                feedTab.getIcon().setTint(ResourcesCompat.getColor(getResources(), R.color.grey_500, null));
            }
            if(communityTab != null) {
                communityTab.setIcon(R.drawable.community);
                communityTab.getIcon().setTint(ResourcesCompat.getColor(getResources(), R.color.grey_500, null));
            }
            if(aboutTab != null) {
                aboutTab.setIcon(R.drawable.ic_info_outline_black_24dp);
                aboutTab.getIcon().setTint(ResourcesCompat.getColor(getResources(), R.color.grey_500, null));
            }

            tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    super.onTabSelected(tab);
                    int color = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
                    tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    super.onTabUnselected(tab);
                    int color = ResourcesCompat.getColor(getResources(), R.color.grey_500, null);
                    tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
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

    public void onFragmentInteraction(Uri uri){

    }

    public void onListFragmentInteraction(Post post){

    }

    public void onListFragmentInteraction(Community post){

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private FeedFragment        feedFragment;
        private CommunitiesFragment communitiesFragment;
        private AboutFragment       aboutFragment;

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            feedFragment        = FeedFragment.newInstance();
            communitiesFragment = CommunitiesFragment.newInstance();
            aboutFragment       = AboutFragment.newInstance();

            Bundle feedBundle = new Bundle();
            feedBundle.putString(USER_ID, userID);
            feedFragment.setArguments(feedBundle);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return feedFragment;
                case 1:
                    return communitiesFragment;
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

//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return getString(R.string.feed);
//                case 1:
//                    return getString(R.string.communities);
//                case 2:
//                    return getString(R.string.about_me);
//            }
//
//            return null;
//        }
    }
}
