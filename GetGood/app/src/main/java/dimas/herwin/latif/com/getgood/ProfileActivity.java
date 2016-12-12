package dimas.herwin.latif.com.getgood;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dimas.herwin.latif.com.getgood.fragments.CommunitiesFragment;
import dimas.herwin.latif.com.getgood.fragments.CommunityFragment;
import dimas.herwin.latif.com.getgood.fragments.DiscoveryFragment;
import dimas.herwin.latif.com.getgood.fragments.FeedFragment;
import dimas.herwin.latif.com.getgood.fragments.PostFragment;
import dimas.herwin.latif.com.getgood.fragments.items.Community;
import dimas.herwin.latif.com.getgood.fragments.items.Post;

public class ProfileActivity extends AppCompatActivity implements FeedFragment.OnFragmentInteractionListener, DiscoveryFragment.OnFragmentInteractionListener, PostFragment.OnListFragmentInteractionListener, CommunityFragment.OnListFragmentInteractionListener{

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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if(tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);

            TabLayout.Tab feedTab       = tabLayout.getTabAt(0);
            TabLayout.Tab discoveryTab  = tabLayout.getTabAt(1);
            TabLayout.Tab communityTab  = tabLayout.getTabAt(2);

            if(feedTab != null)      feedTab.setIcon(R.drawable.ic_arrow_downward_black_24dp);
            if(discoveryTab != null) discoveryTab.setIcon(R.drawable.ic_arrow_downward_black_24dp);
            if(communityTab != null) communityTab.setIcon(R.drawable.ic_arrow_downward_black_24dp);
        }
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
        private DiscoveryFragment   discoveryFragment;
        private CommunitiesFragment communitiesFragment;

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            feedFragment        = FeedFragment.newInstance();
            discoveryFragment   = DiscoveryFragment.newInstance();
            communitiesFragment = CommunitiesFragment.newInstance();

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
                    return discoveryFragment;
                case 2:
                    return communitiesFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.feed);
                case 1:
                    return getString(R.string.about_me);
                case 2:
                    return getString(R.string.communities);
            }

            return null;
        }
    }
}
