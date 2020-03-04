package com.example.kmsquiz.data.DB;

public class QuestionDB {

    private String Txt;
    private int num;
    private int TotPts;
    private int TotQues;


    public String getTxt() {
        return Txt;
    }

    public void setTxt(String txt) {
        Txt = txt;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPts() {
        return TotPts;
    }

    public void setPts(int pts) {
        TotPts = pts;
    }

    public int getTotQues() {
        return TotQues;
    }

    public void setTotQues(int totQues) {
        TotQues = totQues;
    }
}
