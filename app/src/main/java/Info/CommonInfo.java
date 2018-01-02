package Info;

/**
 * Created by sourav9674 on 12/19/2017.
 */

public class CommonInfo {


    private String desc;
    private String name;
    private String date;
    private String time;


    public CommonInfo() {

    }


    public CommonInfo( String name, String date, String time, String desc) {
        this.desc = desc;
        this.name = name;
        this.date = date;
        this.time = time;
    }

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

}
