package example.android.ait.hu.practiceroomassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import example.android.ait.hu.practiceroomassistant.adapter.SongDetailFragmentAdapter;
import example.android.ait.hu.practiceroomassistant.data.PracticeSections;
import example.android.ait.hu.practiceroomassistant.data.Song;

/**
 * Activity for the ViewPager for song details,
 * practice sections, recordings, and web links
 */
public class SongDetailsActivity extends FragmentActivity {

    //key for the song index
    public static final String POSITION_KEY = "getTheCurrentPosition";

    //key for the screen to show
    public static final String START_SCREEN = "WhatScreenShownFirst";

    //the current song
    private Song curSong;

    //set index initially to 0
    private int index=0;

    //instance of the view pager
    private ViewPager viewPager;

    //screen of the view pager to start on (set to 0 initially)
    private int startScreen = 0;

    /**
     * Override on create
     * Initialize values
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        //sort the songs (so all data is up do date)
        SongListActivity.sortSongs();

        Intent intent = getIntent();

        //get the song position
        String position = intent.getStringExtra(POSITION_KEY);

        //get the fragment number
        String screenNum = intent.getStringExtra(START_SCREEN);

        //get screen index, and the current song
        try{
            startScreen = Integer.parseInt(screenNum);
            index = Integer.parseInt(position);
            curSong = SongListActivity.songs.get(index);
        }catch (Exception e){

        }

        //get up the viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SongDetailFragmentAdapter(getSupportFragmentManager(), index));
    }

    /**
     * onResume set the screen of the viewPager
     */
    @Override
    protected void onResume() {
        super.onResume();
        viewPager.setCurrentItem(startScreen);
        sortSections();
    }

    /**
     * on result set the desired screen to return to
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == WebFragment.REQUEST_CODE_URL){
            startScreen = 3;
        }
        if(resultCode == RecordingsFragment.REQUEST_CODE_RECORD){
            startScreen = 2;
        }
    }

    /**
     * sort the songs based on the status
     */
    private void sortSections(){

        //get the practice sections in the song
        ArrayList<PracticeSections> section = curSong.getPracticeSections();

        //if there are less than 2 songs they do not have to be sorted
        if(section.size()<2){
            return;
        }

        //create an array for each status level
        ArrayList<PracticeSections> section0 = new ArrayList<PracticeSections>();
        ArrayList<PracticeSections> section1 = new ArrayList<PracticeSections>();
        ArrayList<PracticeSections> section2 = new ArrayList<PracticeSections>();

        //add each song to the proper status array
        for(int i= 0; i<section.size(); i++){
            if(section.get(i).getStatus()==0){
                section0.add(section.get(i));
            }
            else if(section.get(i).getStatus()==1){
                section1.add(section.get(i));
            }
            else{
                section2.add(section.get(i));
            }
        }

        //remove the practice sections from the song
        curSong.removePracticeSections();

        //sort each of the sections
        if(section0.size()>1){
            section0 = sortStatus(section0, 0);
        }
        if(section1.size()>1){
            section1 = sortStatus(section1, 0);
        }
        if(section2.size()>1){
            section2 = sortStatus(section2, 0);
        }

        //add the practice sections back
        for(int i = 0; i<section0.size(); i++){
            curSong.addPracticeSections(section0.get(i));
        }
        for(int i = 0; i<section1.size(); i++){
            curSong.addPracticeSections(section1.get(i));
        }
        for(int i = 0; i<section2.size(); i++){
            curSong.addPracticeSections(section2.get(i));
        }

    }

    /**
     * Put the given list of practice sections in alphabetical order
     * @param list
     * @param index
     * @return
     */
    private static ArrayList<PracticeSections> sortStatus(ArrayList<PracticeSections> list, int index){
        PracticeSections first = list.get(index);
        for(int i = index+1; i<list.size(); i++){
            PracticeSections tempSection = list.get(i);
            if(first.getSection().compareTo(tempSection.getSection())>0){
                list.remove(i);
                list.add(index,tempSection);
                first = tempSection;
            }
        }
        if(list.size()-2 > index){
            return sortStatus(list, index+1);
        }
        return list;
    }
}
