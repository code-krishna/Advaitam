package Info;

/**
 * Created by HRITIK on 12/24/2017.
 */

public class EventsData {

    private String desc;
    private String name;
    private String date;
    private String time;
    private String ImagesUri;
    public EventsData( String name, String date, String time, String desc,String ImagesUri) {
        this.desc = desc;
        this.name = name;
        this.date = date;
        this.time = time;
        this.ImagesUri = ImagesUri;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImagesUri() {
        return ImagesUri;
    }

    public void setImagesUri(String imagesUri) {
        ImagesUri = imagesUri;
    }

    public static final EventsData[] cInfo={
            new EventsData("Technical Event 1","AA-BB-CCCC","HH:MM:SS", "TECHNICAL 1 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789","https://"),
            new EventsData("Technical Event 2","AA-BB-CCCC","HH:MM:SS", "TECHNICAL 2 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789","https://"),
            new EventsData("Technical Event 3","AA-BB-CCCC","HH:MM:SS", "TECHNICAL 3 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789","https://"),
            new EventsData("Technical Event 4","AA-BB-CCCC","HH:MM:SS", "TECHNICAL 4 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789","https://"),
            new EventsData("Technical Event 5","AA-BB-CCCC","HH:MM:SS", "TECHNICAL 5 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789","https://"),
    };

    public String getDesc() {
        return desc;
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

    public static EventsData[] getcInfo() {
        return cInfo;
    }
}
