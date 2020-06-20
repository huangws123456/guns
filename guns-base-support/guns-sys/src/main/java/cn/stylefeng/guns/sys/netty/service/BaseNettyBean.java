package cn.stylefeng.guns.sys.netty.service;

import java.util.Date;

public class BaseNettyBean {
    private int cmid;
    private String deveice;
    private String des;
    private String cmd;
    private String date;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getDeveice() {
        return deveice;
    }

    public void setDeveice(String deveice) {
        this.deveice = deveice;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getCmid() {
        return cmid;
    }

    public void setCmid(int cmid) {
        this.cmid = cmid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
