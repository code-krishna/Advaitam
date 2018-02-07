package Info;

/**
 * Created by HRITIK on 2/4/2018.
 */

public class events_populator {

    String Date;
    String Name;

    public events_populator(String Name, String Date){
        this.Date = Date;
        this.Name = Name;
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
}
