package com.example.kmsquiz.data.DB;

import android.os.Parcel;
import android.os.Parcelable;


public class QuestionDB implements Parcelable {

    private String Txt;

    protected QuestionDB(Parcel in) {
        Txt = in.readString();
        num = in.readInt();
        TotPts = in.readInt();
        QuizId = in.readInt();
    }

    public static final Creator<QuestionDB> CREATOR = new Creator<QuestionDB>() {
        @Override
        public QuestionDB createFromParcel(Parcel in) {
            return new QuestionDB(in);
        }

        @Override
        public QuestionDB[] newArray(int size) {
            return new QuestionDB[size];
        }
    };

    public void setNum(int num) {
        this.num = num;
    }

    private int num;
    private int TotPts;
    private int QuizId;


    public int getNum()
    {
        return num;
    }

    public int getQuizId() {
        return QuizId;
    }

    public void setQuizId(int quizId) {
        QuizId = quizId;
    }

    public String getTxt() {
        return Txt;
    }

    public void setTxt(String txt) {
        Txt = txt;
    }

    public int getTotPts() {
        return TotPts;
    }

    public void setTotPts(int pts) {
        TotPts = pts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(num);
        dest.writeString(Txt);
        dest.writeInt(TotPts);
        dest.writeInt(QuizId);
    }
}
