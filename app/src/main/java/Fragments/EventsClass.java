package Fragments;

import android.support.annotation.Keep;

import java.io.Serializable;

/**
 * Created by HP on 12/29/2017.
 */

@Keep
public class EventsClass implements Comparable<EventsClass>{
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

    @Override
    public int compareTo(EventsClass o) {
        return getDate().compareTo(o.getDate());
    }


    public void setName(String name) {
        name = name;
    }

    public void setDate(String date) {
        date = date;
    }

    public void setTime(String time) {
        time = time;
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