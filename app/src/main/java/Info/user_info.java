package Info;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HRITIK on 12/19/2017.
 */

public class user_info {
    private String name;
    private String enroll;
    private String contact;
    private String uid;
    private String college;
    private String download_uri;

//    public Map<String, Boolean> getListOfEvents() {
//        return ListOfEvents;
//    }
    private Map<String,EventsClass> listOfEvents = new HashMap<>();
//    public void setListOfEvents(Map<String, Boolean> listOfEvents) {
//        ListOfEvents = listOfEvents;
//    }

//    public Map<String, Boolean> ListOfEvents = new HashMap<>();

    public String getDownload_uri() {
        return download_uri;
    }

    public void setDownload_uri(String download_uri) {
        this.download_uri = download_uri;
    }


    public user_info(String Download_uri ){
        this.download_uri = Download_uri;

    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    user_info(){

    }

    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setListOfEvents(Map<String, EventsClass> listOfEvents) {
        this.listOfEvents = listOfEvents;
    }

    public user_info(String uid, String name, String Enroll, String number, String uri){
        this.download_uri = uri;
        this.name = name;
        this.enroll = Enroll;
        this.contact = number;
        this.uid = uid;

    }
    public user_info(HashMap<String ,EventsClass> list){
        this.listOfEvents = list;

    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Map<String, EventsClass> getListOfEvents() {
        return listOfEvents;
    }

    public user_info(String uid, String name, String Enroll, String number, String uri, String college){
        this.download_uri = uri;
        this.college = college;
        this.name = name;

        this.enroll = Enroll;
        this.contact = number;
        this.uid = uid;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("enroll",enroll);
        result.put("contact",contact);

        return result;
    }



}
