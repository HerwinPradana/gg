package dimas.herwin.latif.com.getgood;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import dimas.herwin.latif.com.getgood.fragments.PostFragment;
import dimas.herwin.latif.com.getgood.fragments.TimelineFragment;
import dimas.herwin.latif.com.getgood.fragments.items.Post;

public class MainActivity extends AppCompatActivity implements  TimelineFragment.OnFragmentInteractionListener, PostFragment.OnListFragmentInteractionListener{

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
        if(tabLayout != null)
            tabLayout.setupWithViewPager(viewPager);
    }

    public void onFragmentInteraction(Uri uri){

    }

    public void onListFragmentInteraction(Post post){

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private TimelineFragment timelineFragment;
        private TimelineFragment timelineFragment2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            timelineFragment = TimelineFragment.newInstance();
            timelineFragment2 = TimelineFragment.newInstance();
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return timelineFragment;
                case 1:
                    return timelineFragment2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Timeline";
                case 1:
                    return "Timeline 2";
            }
            return null;
        }
    }
}
