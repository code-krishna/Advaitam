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
    private Map<String,String> ListOfParticipants = new HashMap<>();
    private String Name;
    private String Time;

    public String getDescription() {
        return Description;
    }



    public void setDescription(String description) {
        Description = description;
    }

    public Map<String, String> getListOfParticipants() {
        return ListOfParticipants;
    }

    public void setListOfParticipants(Map<String, String> listOfParticipants) {
        ListOfParticipants = listOfParticipants;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public EventsData(String date, String desc, String Imagesuri, Map<String,String> parti , String name, String time) {

         Map<String,String> list =new  HashMap<>();
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Date);
        dest.writeString(this.Description);
        dest.writeString(this.ImageUri);
        dest.writeInt(this.ListOfParticipants.size());
        for (Map.Entry<String, String> entry : this.ListOfParticipants.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeString(this.Name);
        dest.writeString(this.Time);
    }

    protected EventsData(Parcel in) {
        this.Date = in.readString();
        this.Description = in.readString();
        this.ImageUri = in.readString();
        int ListOfParticipantsSize = in.readInt();
        this.ListOfParticipants = new HashMap<String, String>(ListOfParticipantsSize);
        for (int i = 0; i < ListOfParticipantsSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.ListOfParticipants.put(key, value);
        }
        this.Name = in.readString();
        this.Time = in.readString();
    }

    public static final Creator<EventsData> CREATOR = new Creator<EventsData>() {
        @Override
        public EventsData createFromParcel(Parcel source) {
            return new EventsData(source);
        }

        @Override
        public EventsData[] newArray(int size) {
            return new EventsData[size];
        }
    };
}
