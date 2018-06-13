package com.drewsullivandma.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    public static final String TAG = "QuizActivity";
    public static final String KEY_INDEX = "index";
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_math, true),
            new Question(R.string.question_CS, true),
            new Question(R.string.question_geography, false),
            new Question(R.string.question_history, true),
            new Question(R.string.question_politics, false)
    };
    private int mCurrentIndex = 0;
    private Boolean[] answerTally = new Boolean[mQuestionBank.length];
    private int numCorrectAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNextQuestion();
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPreviousQuestion();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNextQuestion();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                startActivity(intent);
            }
        });
        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "OnPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "OnSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "OnStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if (answerTally[mCurrentIndex] == null) {
            enableAnswerButtons();
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        if (answerTally[mCurrentIndex] != null) {
            disableAnswerButtons();
        }
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            numCorrectAnswers++;
            answerTally[mCurrentIndex] = true;
            messageResId = R.string.correct_toast;
        } else {
            answerTally[mCurrentIndex] = false;
            messageResId = R.string.incorrect_toast;
        }
        disableAnswerButtons();

        if (continueQuiz() == true) {
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        } else {
            String completionMessage = "You answered " + numCorrectAnswers + "/" + mQuestionBank.length + " right!";
            Toast.makeText(this, completionMessage, Toast.LENGTH_LONG).show();
            enableAnswerButtons();
        }

    }

    private void updateCurrentIndex() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
    }

    private void moveToNextQuestion() {
        updateCurrentIndex();
        updateQuestion();
    }

    private void moveToPreviousQuestion() {
        mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
        updateQuestion();
    }

    private void enableAnswerButtons() {
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }

    private void disableAnswerButtons() {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    private boolean continueQuiz() {
        for (Boolean answer : answerTally) {
            if (answer == null) {
                return true;
            }
        }
        return false;
    }

    private void checkTally() {
        if (getNumAnswersRemaining() == 0) {
            String completionMessage = "You answered " + (Math.round((double) numCorrectAnswers / mQuestionBank.length)) + "% right!";
            Toast.makeText(this, completionMessage, Toast.LENGTH_LONG).show();
        }
    }

    private int getNumAnswersRemaining() {
        int numAnswersRemaining = 0;
        for (Boolean answer : answerTally) {
            if (answer == null) {
                numAnswersRemaining++;
            }
        }
        return numAnswersRemaining;
    }
}
