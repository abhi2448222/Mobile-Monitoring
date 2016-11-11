package com.example.goyal.group14;

import java.util.Date;

/**
 * Created by goyal on 4/12/2016.
 */
public class CallLogCLS {

    Integer id;
    String contactno;
    Boolean blocked;
    String tmDate;
    String type;
    String duration;

    public String getTmDate() {
        return tmDate;
    }

    public void setTmDate(String tmDate) {
        this.tmDate = tmDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }





    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
