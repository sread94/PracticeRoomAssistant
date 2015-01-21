package example.android.ait.hu.practiceroomassistant.data;

import com.orm.SugarRecord;

/**
 * Created by Sarah Read on 11/18/2014.
 *
 * Class represents the comments associated with a song
 *
 * Data stored using SugarORM
 */
public class SongComments extends SugarRecord<SongComments> {

    /**
     * Enum to store the status of the song
     */
    public enum StatusEnum{
        practice, progress, review;

        /**
         * get the string value of the enum instance
         * @return
         */
        @Override
        public String toString() {

            String s = super.toString();
            if(s.equals("practice")){
                return "Must Practice!";
            }
            else if (s.equals("progress")){
                return "In Progress";
            }
            else{
                return "Review";
            }
        }

        /**
         * get the enum instance given the
         * integer representation
         * @param i
         * @return
         */
        public static StatusEnum getItem(int i){
            if(i==0){
                return practice;
            }
            else if(i==1){
                return progress;
            }
            return review;
        }
    }

    //song details/extra user notes
    private String details;

    //song name the comments are associated with
    private String song;

    //the concert the piece is to be performed at
    private String concert;

    //day of the concert
    private int day;

    //month of the concert
    private int month;

    //year of the concert
    private int year;

    //style of the piece
    private String typeOfPiece;

    //int representation of StatusEnum value of
    //the status of the song
    private int status;

    //how long the user has bee practicing this piece
    private String timePracticed;

    /**
     * Default constructor (required by SugarORM)
     */
    public SongComments(){

    }

    /**
     * Constructor for new song (with no other info)
     * @param song
     */
    public SongComments(String song){
        details = "";
        concert = "";
        day=-1;
        month=-1;
        year=-1;
        typeOfPiece="";
        status=-1;
        timePracticed="0";
    }

    /**
     * Constructor to set all instance properties
     * @param details
     * @param song
     * @param concert
     * @param day
     * @param month
     * @param year
     * @param typeOfPiece
     * @param status
     * @param timePracticed
     */
    public SongComments(String details, String song, String concert, int day, int month, int year, String typeOfPiece, int status,
                        String timePracticed) {
        this.details = details;
        this.song = song;
        this.concert = concert;
        this.day = day;
        this.month = month;
        this.year = year;
        this.typeOfPiece = typeOfPiece;
        this.status = status;
        this.timePracticed = timePracticed;
    }

    /**
     * getter for details
     * @return
     */
    public String getDetails() {
        return details;
    }

    /**
     * getter for song
     * @return
     */
    public String getSong() {
        return song;
    }

    /**
     * getter for concert
     * @return
     */
    public String getConcert() {
        return concert;
    }

    /**
     * getter for day
     * @return
     */
    public int getDay() {
        return day;
    }

    /**
     * getter for month
     * @return
     */
    public int getMonth() {
        return month;
    }

    /**
     * getter for year
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     * getter for typeOfPiece
     * @return
     */
    public String getTypeOfPiece() {
        return typeOfPiece;
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
     * setter for details
     * @param details
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * setter for song
     * @param song
     */
    public void setSong(String song) {
        this.song = song;
    }

    /**
     * setter for concert
     * @param concert
     */
    public void setConcert(String concert) {
        this.concert = concert;
    }

    /**
     * setter for day
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * setter for month
     * @param month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * setter for year
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * setter for typeOfPiece
     * @param typeOfPiece
     */
    public void setTypeOfPiece(String typeOfPiece) {
        this.typeOfPiece = typeOfPiece;
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
