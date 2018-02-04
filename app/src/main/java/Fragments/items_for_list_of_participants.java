package Fragments;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by HRITIK on 2/2/2018.
 */

public class items_for_list_of_participants implements Parcelable {

    private String Name;
    private String Enroll;
    private String Contact;

    public items_for_list_of_participants(){}

    protected items_for_list_of_participants(Parcel in) {
        Name = in.readString();
        Enroll = in.readString();
        Contact = in.readString();
    }

    public static final Creator<items_for_list_of_participants> CREATOR = new Creator<items_for_list_of_participants>() {
        @Override
        public items_for_list_of_participants createFromParcel(Parcel in) {
            return new items_for_list_of_participants(in);
        }

        @Override
        public items_for_list_of_participants[] newArray(int size) {
            return new items_for_list_of_participants[size];
        }
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEnroll() {
        return Enroll;
    }

    public void setEnroll(String enroll) {
        Enroll = enroll;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public items_for_list_of_participants(String Name,String Enroll,String Contct){
        this.Name = Name;
        this.Enroll = Enroll;
        this.Contact = Contct;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Enroll);
        parcel.writeString(Contact);
    }
}