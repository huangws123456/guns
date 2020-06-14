package cn.stylefeng.guns.sys.netty.service;

public class BaseNettyBean {
    private int cmid;
    private String deveice;
    private String des;
    private String cmd;

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
}
