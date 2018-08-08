package vattgert.player.musicplayer.ui.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vattgert.player.musicplayer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LibraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LibraryFragment extends Fragment {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private FragmentManager fragmentManager;

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance(String param1, String param2) {
        return new LibraryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSectionsPagerAdapter = new SectionsPagerAdapter(((AppCompatActivity)getActivity()).getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        TabLayout tabLayout = view.findViewById(R.id.library_tabs);
        //Toolbar toolbar = view.findViewById(R.id.library_toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        mViewPager = view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        return view;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            android.support.v4.app.Fragment fragment = null;
            switch(position)
            {
                case 0:
                    fragment =  SongsFragment.newInstance();
                    break;
                case 1:
                    fragment = AlbumsFragment.newInstance();
                    break;
                case 2:
                    fragment =  ArtistsFragment.newInstance();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

}
