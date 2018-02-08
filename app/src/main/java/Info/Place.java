package Info;

import android.support.annotation.Keep;

import java.io.Serializable;

/**
 * Created by HP on 12/29/2017.
 */

@Keep
public class Place {
    public String nameStudent;
    public String roll;
    public String phone;

    public Place(){

    }

    public Place (String nameStudent, String roll, String phone)
    {
        this.nameStudent = nameStudent;
        this.roll = roll;
        this.phone = phone;
    }


    public String getNameStudent() {
        return nameStudent;
    }

    public String getRoll() {
        return roll;
    }

    public String getPhone() {
        return phone;
    }
}