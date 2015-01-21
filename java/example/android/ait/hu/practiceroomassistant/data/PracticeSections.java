package example.android.ait.hu.practiceroomassistant.data;

import com.orm.SugarRecord;

/**
 * Created by Sarah Read on 11/18/2014.
 *
 * Class represents a practice section within a song
 * that a user would like to represent
 *
 * Data saved using SugarORM
 */
public class PracticeSections extends SugarRecord<PracticeSections>{

    //name of the section
    private String section;

    //the song the section exists within
    private String song;

    //the type of section
    private String typeOfSection;

    //any additional notes
    private String notesOnSection;

    //the current status of the song (0,1,2)
    //representing SongComments.StatusEnum
    private int status;

    //the number of hours the user practiced
    private String timePracticed;

    /**
     * Default constructor (required by SugarORM)
     */
    public PracticeSections(){

    }

    /**
     * Constructor
     * @param section
     * @param song
     * @param typeOfSection
     * @param notesOnSection
     * @param status
     * @param timePracticed
     */
    public PracticeSections(String section, String song, String typeOfSection, String notesOnSection, int status, String timePracticed) {
        this.section = section;
        this.song = song;
        this.typeOfSection = typeOfSection;
        this.notesOnSection = notesOnSection;
        this.status = status;
        this.timePracticed = timePracticed;
    }

    /**
     * getter for notesOnSection
     * @return
     */
    public String getNotesOnSection() {
        return notesOnSection;
    }

    /**
     * getter for status
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * getter for timePracticed
     * @return
     */
    public String getTimePracticed() {
        return timePracticed;
    }

    /**
     * getter for typeOfSection
     * @return
     */
    public String getTypeOfSection() {
        return typeOfSection;
    }

    /**
     * getter for typeOfSection
     * @param typeOfSection
     */
    public void setTypeOfSection(String typeOfSection) {
        this.typeOfSection = typeOfSection;
    }

    /**
     * getter for section
     * @return
     */
    public String getSection() {
        return section;
    }

    /**
     * getter for song
     * @return
     */
    public String getSong() {
        return song;
    }

    /**
     * setter for section
     * @param section
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * setter for song
     * @param song
     */
    public void setSong(String song) {
        this.song = song;
    }

    /**
     * setter for notesOnSection
     * @param notesOnSection
     */
    public void setNotesOnSection(String notesOnSection) {
        this.notesOnSection = notesOnSection;
    }

    /**
     * setter for status
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * setter for timePracticed
     * @param timePracticed
     */
    public void setTimePracticed(String timePracticed) {
        this.timePracticed = timePracticed;
    }
}
