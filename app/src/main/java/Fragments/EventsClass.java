package Fragments;

import android.support.annotation.Keep;

import java.io.Serializable;

/**
 * Created by HP on 12/29/2017.
 */

@Keep
public class EventsClass {
    public String name;
    public String date;
    public String time;

    public EventsClass(){

    }

    public EventsClass (String name, String date, String time)
    {
        this.name= name;
        this.time = time;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}