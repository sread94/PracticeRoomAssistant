package example.android.ait.hu.practiceroomassistant.data;

import java.util.ArrayList;

/**
 * Created by Sarah Read on 11/18/2014.
 *
 * Class to represent a song
 */
public class Song{

    //the title of the song
    private String title;

    //an array list of PracticeSections associated
    //with this song
    private ArrayList<PracticeSections> practiceSections;

    //the SongComments associated with the song
    private SongComments comments;

    //an array list of WebURL associated with the song
    private ArrayList<WebURL> urls;

    //and array list of UserRecordings associated with the song
    private ArrayList<UserRecordings> recordings;

    /**
     * Constructor
     * Initializes array lists to be empty
     * @param title
     */
    public Song(String title){
        this.title = title;
        practiceSections = new ArrayList<PracticeSections>();
        urls = new ArrayList<WebURL>();
        recordings = new ArrayList<UserRecordings>();
    }

    /**
     * getter for comments
     * @return
     */
    public SongComments getComments() {
        return comments;
    }

    /**
     * setter for comments
     * @param comments
     */
    public void addComments(SongComments comments){
        this.comments = comments;
    }

    /**
     * setter for practiceSections
     * @param practiceSections
     */
    public void setPracticeSections(ArrayList<PracticeSections> practiceSections) {
        this.practiceSections = practiceSections;
    }

    /**
     * add one practice section to the array list
     * @param section
     */
    public void addPracticeSections(PracticeSections section){
        practiceSections.add(section);
    }

    /**
     * setter for title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter for practiceSections
     * @return
     */
    public ArrayList<PracticeSections> getPracticeSections() {
        return practiceSections;
    }

    /**
     * remove all of the practice sections from the array list
     */
    public void removePracticeSections() {
        for(int i = practiceSections.size()-1; i>-1; i--){
            practiceSections.remove(i);
        }
    }

    /**
     * add a WebURL to the array list
     * @param url
     */
    public void addUrl(WebURL url){
        urls.add(url);
    }

    /**
     * getter for urls
     * @return
     */
    public ArrayList<WebURL> getUrls(){
        return urls;
    }

    /**
     * add a recording to the array list
     * @param recording
     */
    public void addRecording(UserRecordings recording){
        recordings.add(recording);
    }

    /**
     * getter for recordings
     * @return
     */
    public ArrayList<UserRecordings> getRecordings(){
        return recordings;
    }
}
