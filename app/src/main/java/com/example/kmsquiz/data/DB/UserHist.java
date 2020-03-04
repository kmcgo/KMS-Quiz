package com.example.kmsquiz.data.DB;

public class UserHist {

    private int QuestId, QuizId, GivenId, CorrectId, Attempt, UserID;

    public int getQuestId() {
        return QuestId;
    }

    public void setQuestId(int questId) {
        QuestId = questId;
    }

    public int getQuizId() {
        return QuizId;
    }

    public void setQuizId(int quizId) {
        QuizId = quizId;
    }

    public int getGivenId() {
        return GivenId;
    }

    public void setGivenId(int givenId) {
        GivenId = givenId;
    }

    public int getCorrectId() {
        return CorrectId;
    }

    public void setCorrectId(int correctId) {
        CorrectId = correctId;
    }

    public int getAttempt() {
        return Attempt;
    }

    public void setAttempt(int attempt) {
        Attempt = attempt;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}
