package com;

/**
 * Created by foxhound7812 on 2016/4/14.
 * 抽籤名單欄位model
 */
public class RegisterModel {

    //CSV 欄位
    private String rid; //
    private String eid; //
    private String rsid; //
    private String rstatus; //
    private String IDNumber; //
    private String RName;
    private String RType;
    private String score;
    private String age;

    //rid
    public String getRid(){
        return rid;
    }

    public void setRid(String rid){
        this.rid = rid;
    }

    //eid
    public String getEid(){
        return eid;
    }

    public void setEid(String eid){
        this.eid = eid;
    }

    //rsid
    public String getRsid(){
        return rsid;
    }

    public void setRsid(String rsid){
        this.rsid = rsid;
    }

    //rstatus
    public String getRstatus(){
        return rstatus;
    }

    public void setRstatus(String Rstatus){
        this.rstatus = rstatus;
    }

    //IDNumber
    public String getIDNumber(){
        return IDNumber;
    }

    public void setIDNumber(String IDNumber){
        this.IDNumber = IDNumber;
    }

    //RName
    public String getRName(){
        return RName;
    }

    public void setRName(String RName){
        this.RName = RName;
    }

    //RType
    public String getRType(){
        return RType;
    }

    public void setRType(String RType){
        this.RType = RType;
    }

    //score
    public String getScore(){
        return score;
    }

    public void setScore(String score){
        this.score = score;
    }
    //age
    public String getAge(){
        return age;
    }

    public void setAge(String age){
        this.age = age;
    }
}
