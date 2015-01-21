package example.android.ait.hu.practiceroomassistant.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import example.android.ait.hu.practiceroomassistant.RecordingsFragment;
import example.android.ait.hu.practiceroomassistant.SongDetailFragment;
import example.android.ait.hu.practiceroomassistant.SongDetailsActivity;
import example.android.ait.hu.practiceroomassistant.SongSectionsFragment;
import example.android.ait.hu.practiceroomassistant.WebFragment;

/**
 * Created by Sarah Read on 11/24/2014.
 *
 * Adapter for the fragments in the view pager
 */
public class SongDetailFragmentAdapter extends FragmentPagerAdapter {

    //the index of the song
    private int index;

    /**
     * Constructor
     * @param fm
     * @param i
     */
    public SongDetailFragmentAdapter(FragmentManager fm, int i){
        super(fm);
        index=i;
    }

    /**
     * get the fragment at the given position
     * @param i
     * @return
     */
    @Override
    public Fragment getItem(int i) {
        Bundle arguments = new Bundle();
        arguments.putString(SongDetailsActivity.POSITION_KEY,
                index+"");
        Fragment fragment;
        switch (i) {
            case 0:
                fragment = new SongDetailFragment();
                break;
            case 1:
                fragment = new SongSectionsFragment();
                break;
            case 2:
                fragment = new RecordingsFragment();
                break;
            case 3:
                fragment = new WebFragment();
                break;
            default:
                fragment = new SongDetailFragment();
                break;
        }
        fragment.setArguments(arguments);
        return fragment;
    }

    /**
     * get the corresponding title given the fragment position
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Song Details";
            case 1:
                return "Song Sections";
            case 2:
                return "Song Recordings";
            case 3:
                return "Web Links";
            default:
                return "Song Details";
        }
    }

    /**
     * return the number of fragments
     * @return
     */
    @Override
    public int getCount() {
        return 4;
    }
}
