package Info;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HRITIK on 12/24/2017.
 */

public class EventsData implements Parcelable {

    EventsData(){}

    private String Date;
    private String Description;
    private String ImageUri;
    private Map<String,items_for_list_of_participants> ListOfParticipants = new HashMap<>();
    private String Name;
    private String Time;

    public static Creator<EventsData> getCREATOR() {
        return CREATOR;
    }

    protected EventsData(Parcel in) {
        Date = in.readString();
        Description = in.readString();
        ImageUri = in.readString();
        Name = in.readString();
        Time = in.readString();

    }

    public static final Creator<EventsData> CREATOR = new Creator<EventsData>() {
        @Override
        public EventsData createFromParcel(Parcel in) {
            return new EventsData(in);
        }

        @Override
        public EventsData[] newArray(int size) {
            return new EventsData[size];
        }
    };

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

    public EventsData(String date, String desc, String Imagesuri, Map<String,items_for_list_of_participants> parti , String name, String time) {

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




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(Date);
        parcel.writeString(Description);
        parcel.writeString(ImageUri);
        parcel.writeString(Name);
        parcel.writeString(Time);
    }

}
