package Info;

/**
 * Created by Prince on 09-12-2017.
 */

public class CulturalInfo {

    private String desc;
    private String name;
    private String date;
    private String time;

    public CulturalInfo( String name, String date, String time, String desc) {
        this.desc = desc;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static final CulturalInfo[] cInfo={
            new CulturalInfo("Technical Event 1","AA-BB-CCCC","HH:MM:SS", "TECHNICAL 1 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789"),
            new CulturalInfo("Technical Event 2","AA-BB-CCCC","HH:MM:SS", "TECHNICAL 2 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789"),
            new CulturalInfo("Technical Event 3","AA-BB-CCCC","HH:MM:SS", "TECHNICAL 3 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789"),
            new CulturalInfo("Technical Event 4","AA-BB-CCCC","HH:MM:SS", "TECHNICAL 4 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789"),
            new CulturalInfo("Technical Event 5","AA-BB-CCCC","HH:MM:SS", "TECHNICAL 5 EVENT DETAILS...\nDetails 123...\nDetails 456...\nDetails 789"),
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

    public static CulturalInfo[] getcInfo() {
        return cInfo;
    }
}
