package com.drewsullivandma.geoquiz;

/**
 * Created by dsullivan on 6/12/2018.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerIsTrue;

    public Question(int textResId, boolean answerIsTrue) {
        mTextResId = textResId; // Holds the resource ID (always an int) of the string resource
        mAnswerIsTrue = answerIsTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerIsTrue;
    }

    public void setAnswerIsTrue(boolean answerIsTrue) {
        mAnswerIsTrue = answerIsTrue;
    }
}
