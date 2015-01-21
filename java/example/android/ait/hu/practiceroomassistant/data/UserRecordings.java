package example.android.ait.hu.practiceroomassistant.data;

import com.orm.SugarRecord;

/**
 * Created by Sarah Read on 12/4/2014.
 *
 * Class for a recording associated with a specific song
 *
 * the data is stored using SugarORM
 */
public class UserRecordings extends SugarRecord<UserRecordings> {

    //the name of the file where the recording is stored
    private String fileName;

    //the name of the song the recording is associated with
    private String song;

    //the name of the recording that the user will see
    private String label;

    /**
     * Default Constructor (required by SugarORM)
     */
    public UserRecordings() {
    }

    /**
     * Constructor
     * @param fileName
     * @param song
     * @param label
     */
    public UserRecordings(String fileName, String song, String label) {
        this.fileName = fileName;
        this.song = song;
        this.label = label;
    }

    /**
     * getter for fileName
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * getter for song
     * @return
     */
    public String getSong() {
        return song;
    }

    /**
     * getter for label
     * @return
     */
    public String getLabel() {
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
