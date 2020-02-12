package com.example.kviz;

public interface QuizHandelr {

    public void ChekAnswer(final String answerClient);

    public void SetUpQuestion(int numQuestion);

    public int  AnswerCounter(int answerCorrectIncorrect);

    public void StartCountDownTimer(long totalMilliseconds, long interval);

}
