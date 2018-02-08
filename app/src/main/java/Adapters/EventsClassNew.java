package Adapters;

import android.os.Parcel;

import java.util.HashMap;
import java.util.Map;

import Info.items_for_list_of_participants;


public class EventsClassNew implements Comparable<EventsClassNew> {

    EventsClassNew(){}

    private String Date;
    private String Description;
    private String ImageUri;
    private Map<String, items_for_list_of_participants> ListOfParticipants = new HashMap<>();
    private String Name;
    private String Time;



    protected EventsClassNew(Parcel in) {
        Date = in.readString();
        Description = in.readString();
        ImageUri = in.readString();
        Name = in.readString();
        Time = in.readString();

    }


    @Override
    public int compareTo(EventsClassNew o) {
        return getDate().compareTo(o.getDate());
    }


    public String getDescription() {
        return Description;
    }



    public void setDescription(String description) {
        Description = description;
    }

    public Map<String, items_for_list_of_participants> getListOfParticipants() {
        return ListOfParticipants;
    }

    public void setListOfParticipants(Map<String, items_for_list_of_participants> listOfParticipants) {
        ListOfParticipants = listOfParticipants;
    }



    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public EventsClassNew(String date, String desc, String Imagesuri, Map<String,items_for_list_of_participants> parti , String name, String time) {

//         Map<String,items_for_list_of_participants> list = new  HashMap<>();
        this.Date = date;
        this.Description = desc;
        this.ImageUri = Imagesuri;
        this.ListOfParticipants = parti;
        this.Name = name;
        this.Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


}
