package Info;

/**
 * Created by Prince on 09-12-2017.
 */

public class SportsInfo {

    private String desc;
    private String name;
    private String date;
    private String time;

    public SportsInfo( String name, String date, String time, String desc) {
        this.desc = desc;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static final SportsInfo[] sInfo={
            new SportsInfo("Sports Event 1","AA-BB-CCCC","HH:MM:SS", "Sports 1 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789"),
            new SportsInfo("Sports Event 2","AA-BB-CCCC","HH:MM:SS", "Sports 2 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789"),
            new SportsInfo("Sports Event 3","AA-BB-CCCC","HH:MM:SS", "Sports 3 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789"),
            new SportsInfo("Sports Event 4","AA-BB-CCCC","HH:MM:SS", "Sports 4 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789"),
            new SportsInfo("Sports Event 5","AA-BB-CCCC","HH:MM:SS", "Sports 5 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789"),
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

    public static SportsInfo[] getcInfo() {
        return sInfo;
    }
}
