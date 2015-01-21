package example.android.ait.hu.practiceroomassistant.data;

import com.orm.SugarRecord;

/**
 * Created by Sarah Read on 11/29/2014.
 *
 * Class represents the data need for each reminder
 *
 * the data is stored using SugarORM
 */
public class Reminder extends SugarRecord<Reminder> {

    //hour when the reminder is triggered
    //0-23
    private int startHour;

    //minute when the reminder is triggered
    //0-59
    private int startMin;

    //the day of the week the reminder is triggered
    //0-6
    private int day;

    //the user designated message to appear on the reminder
    private String message;

    //true if the reminder is to repeat each week
    private boolean repeat;

    /**
     * enum to represent the days of the week
     */
    public enum dayEnum{
        Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;

        /**
         * return the string value of the value
         * @return
         */
        @Override
        public String toString() {
            String s = super.toString();
            return s;
        }

        /**
         * convert the day number to the enum value
         * @param i
         * @return
         */
        public static dayEnum getItem(int i){
           switch (i){
               case 0:
                   return Monday;
               case 1:
                   return Tuesday;
               case 2:
                   return Wednesday;
               case 3:
                   return Thursday;
               case 4:
                   return Friday;
               case 5:
                   return Saturday;
               case 6:
                   return Sunday;
               default:
                   return Monday;
           }
        }
    }

    /**
     * default constructor required by SugarORM
     */
    public Reminder(){

    }

    /**
     * Constructor
     * @param startHour
     * @param startMin
     * @param day
     * @param message
     * @param repeat
     */
    public Reminder(int startHour, int startMin, int day, String message, boolean repeat) {
        this.startHour = startHour;
        this.startMin = startMin;
        this.day = day;
        this.message = message;
        this.repeat = repeat;
    }

    /**
     * getter for startHour
     * @return
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * getter for startMin
     * @return
     */
    public int getStartMin() {
        return startMin;
    }

    /**
     * getter for day
     * @return
     */
    public int getDay() {
        return day;
    }

    /**
     * getter for message
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * getter for repeat
     * @return
     */
    public boolean isRepeat() {
        return repeat;
    }

    /**
     * setter for startHour
     * @param startHour
     */
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    /**
     * setter for startMin
     * @param startMin
     */
    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    /**
     * setter for day
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * setter for message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * setter for repeat
     * @param repeat
     */
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

}
