package com.example.kmsquiz.data.DB;

public class Answer {
    private String Txt;
    private int quesNum;
    private int AnsId;


    public int getAnsId() {
        return AnsId;
    }

    public void setAnsId(int ansId) {
        AnsId = ansId;
    }

    public String getTxt() {
        return Txt;
    }

    public void setTxt(String txt) {
        Txt = txt;
    }

    public int getQuesNum() {
        return quesNum;
    }

    public void setQuesNum(int quesNum) {
        this.quesNum = quesNum;
    }
}
