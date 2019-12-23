package id.dev.birifqa.edcgold.model;

import android.os.Parcelable;

public class DownlineModel {
    private String userid;
    private String name;
    private String userid_parent;
    private String level;
    private String hasDownline;

    public DownlineModel() {
    }

    public DownlineModel(String userid, String name, String userid_parent, String level, String hasDownline) {
        this.userid = userid;
        this.name = name;
        this.userid_parent = userid_parent;
        this.level = level;
        this.hasDownline = hasDownline;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid_parent() {
        return userid_parent;
    }

    public void setUserid_parent(String userid_parent) {
        this.userid_parent = userid_parent;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getHasDownline() {
        return hasDownline;
    }

    public void setHasDownline(String hasDownline) {
        this.hasDownline = hasDownline;
    }
}
