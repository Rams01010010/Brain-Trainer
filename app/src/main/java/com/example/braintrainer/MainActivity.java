package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goBtn,playAgainBtn;
    Button[] ansBtns = new Button[4];
    TextView countDownText,questionText,scoreText,displayText;
    CountDownTimer countDownTimer;
    Random random;
    int[] score = {0,0};
    int operandA, operandB, answer, answerBtn;
    boolean isGameActive = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goBtn = (Button) findViewById(R.id.goBtn);
        ansBtns[0] = (Button) findViewById(R.id.aBtn);
        ansBtns[1] = (Button) findViewById(R.id.bBtn);
        ansBtns[2] = (Button) findViewById(R.id.cBtn);
        ansBtns[3] = (Button) findViewById(R.id.dBtn);
        playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
        countDownText = (TextView) findViewById(R.id.countDownTextView);
        questionText = (TextView) findViewById(R.id.questionTextView);
        scoreText = (TextView) findViewById(R.id.scoreTextView);
        displayText = (TextView) findViewById(R.id.displayTextView);
        screenView(true);
    }
    public void screenView(boolean isFirst)
    {
        if(isFirst)
        {
            goBtn.setVisibility(View.VISIBLE);
            ansBtns[0].setVisibility(View.INVISIBLE);
            ansBtns[1].setVisibility(View.INVISIBLE);
            ansBtns[2].setVisibility(View.INVISIBLE);
            ansBtns[3].setVisibility(View.INVISIBLE);
            playAgainBtn.setVisibility(View.INVISIBLE);
            countDownText.setVisibility(View.INVISIBLE);
            questionText.setVisibility(View.INVISIBLE);
            scoreText.setVisibility(View.INVISIBLE);
            displayText.setVisibility(View.INVISIBLE);
        }
        else
        {
            goBtn.setVisibility(View.INVISIBLE);
            ansBtns[0].setVisibility(View.VISIBLE);
            ansBtns[1].setVisibility(View.VISIBLE);
            ansBtns[2].setVisibility(View.VISIBLE);
            ansBtns[3].setVisibility(View.VISIBLE);
            playAgainBtn.setVisibility(View.INVISIBLE);
            countDownText.setVisibility(View.VISIBLE);
            questionText.setVisibility(View.VISIBLE);
            scoreText.setVisibility(View.VISIBLE);
            displayText.setVisibility(View.INVISIBLE);
        }
    }
    public void start(View view)
    {
        isGameActive = true;
        score[0] = 0;
        score[1] = 0;
        screenView(false);
        createQuestion();
        startTimer();
    }
    private void startTimer()
    {
        countDownTimer = new CountDownTimer(61000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String textToDisplay = "";
                if(((int) millisUntilFinished/1000) < 10)
                {
                    textToDisplay += "0";
                }
                textToDisplay += String.valueOf(millisUntilFinished / 1000);
                countDownText.setText(textToDisplay+"s");
            }

            @Override
            public void onFinish() {
                displayText.setText("Done !");
                playAgainBtn.setVisibility(View.VISIBLE);
                isGameActive = false;
            }
        }.start();
    }
    public void createQuestion()
    {
        random = new Random();
        operandA = random.nextInt(49)+1; // 2
        operandB = random.nextInt(49)+1; // 14
        answer = operandA + operandB; // 2 + 4 => 16
        questionText.setText(String.valueOf(operandA) + " + " + String.valueOf(operandB));
        answerBtn =  random.nextInt(4); // 3
        int i = 3;
        while(i != -1)
        {
            int valueHolder = random.nextInt(10) + (answer - 5);
            if(i == answerBtn) // i == 3
                ansBtns[i].setText(String.valueOf(answer));
            else if(valueHolder != answer)
            {
                int flag = 0;
                for(int j = 3;j > i; j--)
                {
                    if(valueHolder == Integer.parseInt(ansBtns[j].getText().toString()))
                    {
                        flag += 1;
                        break;
                    }
                }
                if(flag == 0)
                    ansBtns[i].setText(String.valueOf(valueHolder));
                else
                    i+=1;
            }
            else
                i += 1;
            i -= 1;
        }
        scoreText.setText(String.valueOf(score[0]) + "/" + String.valueOf(score[1]));
    }
    public void checkAnswer(View view)
    {
        if(isGameActive)
        {
            Button button = (Button) view;
            String pressedBtn = button.getTag().toString();
            displayText.setVisibility(View.VISIBLE);
            score[1]+=1;
            if(pressedBtn.equals(String.valueOf(answerBtn)))
            {
                displayText.setText("Correct !");
                score[0] += 1;
            }
            else
            {
                displayText.setText("Wrong :(");
            }
            createQuestion();
        }
    }
}