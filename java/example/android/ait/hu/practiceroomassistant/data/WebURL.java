package example.android.ait.hu.practiceroomassistant.data;

import com.orm.SugarRecord;

/**
 * Created by Sarah Read on 12/4/2014.
 *
 * Class for a link to youtube associated with a song
 *
 * the data is stored using SugarORM
 */
public class WebURL extends SugarRecord<WebURL> {

    //the url of the web page linked to
    private String url;

    //the name of the song the link is associated with
    private String song;

    //the name of the link that the user will see
    private String label;

    /**
     * Default constructor (required by SugarORM)
     */
    public WebURL(){

    }

    /**
     * Constructor
     * @param url
     * @param song
     * @param label
     */
    public WebURL(String url, String song, String label){
        this.url = url;
        this.song = song;
        this.label = label;
    }

    /**
     * getter for url
     * @return
     */
    public String getURL(){
        return url;
    }

    /**
     * getter for song
     * @return
     */
    public String getSong(){
        return song;
    }

    /**
     * getter for label
     * @return
     */
    public String getLabel(){
        return label;
    }

    /**
     * setter for song
     * @param song
     */
    public void setSong(String song) {
        this.song = song;
    }
}
