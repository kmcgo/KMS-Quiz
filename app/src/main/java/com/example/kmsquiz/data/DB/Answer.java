package com.example.kmsquiz.data.DB;

public class Answer {
    private String Txt;

    public int getQuizNum() {
        return quizNum;
    }

    public void setQuizNum(int quizNum) {
        this.quizNum = quizNum;
    }

    private int quizNum;
    private int AnsId;
    private int quesNum;


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
