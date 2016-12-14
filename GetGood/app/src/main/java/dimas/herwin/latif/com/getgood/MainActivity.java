package dimas.herwin.latif.com.getgood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import dimas.herwin.latif.com.getgood.fragments.CommunitiesFragment;
import dimas.herwin.latif.com.getgood.fragments.CommunityFragment;
import dimas.herwin.latif.com.getgood.fragments.DiscoveryFragment;
import dimas.herwin.latif.com.getgood.fragments.PostFragment;
import dimas.herwin.latif.com.getgood.fragments.FeedFragment;
import dimas.herwin.latif.com.getgood.fragments.items.Community;
import dimas.herwin.latif.com.getgood.fragments.items.Post;

public class MainActivity extends AppCompatActivity
        implements
        FeedFragment.OnFragmentInteractionListener,
        DiscoveryFragment.OnFragmentInteractionListener,
        CommunitiesFragment.OnFragmentInteractionListener,
        PostFragment.OnListFragmentInteractionListener,
        CommunityFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

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

            if(feedTab != null) {
                feedTab.setIcon(R.drawable.ic_home_black_24dp);
                feedTab.getIcon().setTint(ResourcesCompat.getColor(getResources(), R.color.red_800, null));
            }
            if(discoveryTab != null) {
                discoveryTab.setIcon(R.drawable.ic_explore_black_24dp);
                discoveryTab.getIcon().setTint(ResourcesCompat.getColor(getResources(), R.color.red_800, null));
            }
            if(communityTab != null) {
                communityTab.setIcon(R.drawable.community);
                communityTab.getIcon().setTint(ResourcesCompat.getColor(getResources(), R.color.red_800, null));
            }

            tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    super.onTabSelected(tab);
                    int color = ResourcesCompat.getColor(getResources(), R.color.white, null);
                    tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    super.onTabUnselected(tab);
                    int color = ResourcesCompat.getColor(getResources(), R.color.red_800, null);
                    tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    super.onTabReselected(tab);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_pref), MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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

        // Show text below icons.
//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return getString(R.string.feed);
//                case 1:
//                    return getString(R.string.discover);
//                case 2:
//                    return getString(R.string.communities);
//            }
//
//            return null;
//        }
    }
}
