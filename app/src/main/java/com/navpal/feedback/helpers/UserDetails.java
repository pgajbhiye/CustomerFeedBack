package com.navpal.feedback.helpers;

import com.navpal.feedback.util.Utils;

/**
 * Created by fissionlabs on 15-03-2015.
 */
public class UserDetails {
    String name, emailId;
    boolean firstTime = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public boolean isValid(){
        return (name!=null && name.trim().length()>0 && Utils.isValidEmail(emailId));
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }
}
