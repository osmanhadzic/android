package com.example.kviz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Quiz1 extends AppCompatActivity {
    private TextView timerTxT;
    private TextView pitanje;
    private TextView odgovorA;
    private TextView odgovorB;
    private TextView odgovorC;
    private TextView brQuestion;
    private TextView isCorrect;
    private Button nextBtn;
    private Button BtnA;
    private Button BtnB;
    private Button BtnC;
    private long timeleft = 30000;
    private DatabaseReference mDatabase;
    int numQuestion = 1;
    private short questionCunter=1;

   // private boolean isPaused = false;
    private boolean isCanceled = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        SetUpQuestion(RandomNum());
        long totalMilliseconds = 20000;
        //count down interval in milliseconds
        long interval = 1000;
        //1000 milliseconds = 1 second
        StartCountDownTimer(totalMilliseconds, interval);


        timerTxT = (TextView)findViewById(R.id.brojac);
        pitanje = (TextView)findViewById(R.id.pitanje);
        odgovorA = (TextView)findViewById(R.id.txt_pitanjeA);
        odgovorB = (TextView)findViewById(R.id.txt_pitanjeB);
        odgovorC = (TextView)findViewById(R.id.txt_pitanjeC);
        brQuestion = (TextView)findViewById(R.id.txtv_question);
        isCorrect = (TextView)findViewById(R.id.txtv_opis);

        nextBtn = (Button)findViewById(R.id.btnNextQ);

        BtnA = (Button)findViewById(R.id.btn_answerA);
        BtnB = (Button)findViewById(R.id.btn_answerB);
        BtnC = (Button)findViewById(R.id.btnC);

        BtnA.setEnabled(true);
        BtnB.setEnabled(true);
        BtnC.setEnabled(true);

        brQuestion.setText(questionCunter +"");


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnA.setEnabled(true);
                BtnB.setEnabled(true);
                BtnC.setEnabled(true);

                CounterQuestion();

                SetUpQuestion(RandomNum());
                isCorrect.setText("");
                long totalMilliseconds = 20000;
                //count down interval in milliseconds
                long interval = 1000;
                //1000 milliseconds = 1 second
                StartCountDownTimer(totalMilliseconds, interval);


            }
        });

        BtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnB.setEnabled(false);
                BtnC.setEnabled(false);
                ChekAnswer("A");
            }
        });

        BtnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnA.setEnabled(false);
                BtnC.setEnabled(false);
                ChekAnswer("B");
            }
        });

        BtnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnA.setEnabled(false);
                BtnB.setEnabled(false);
                ChekAnswer("C");
            }
        });

    }



    public void SetUpQuestion(int numQuestion){
        final DatabaseReference myRef = mDatabase.child("questions");
        final String number = Integer.toString(numQuestion);
        myRef.child(number).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String que = dataSnapshot.child("question").getValue().toString();
                pitanje.setText(que);
                String answerA = dataSnapshot.child("A").getValue().toString();
                odgovorA.setText(answerA);
                String answerB = dataSnapshot.child("B").getValue().toString();
                odgovorB.setText(answerB);
                String answerC = dataSnapshot.child("C").getValue().toString();
                odgovorC.setText(answerC);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void ChekAnswer(final String answerClient){
        final DatabaseReference myRef = mDatabase.child("questions");
        final String number = Integer.toString(numQuestion);
        myRef.child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String trueAnswer = dataSnapshot.child("answer").getValue().toString();
                if (trueAnswer.equals(answerClient)){
                    isCorrect.setText("Tacno");
                    Answer(1);
                }
                else{
                    isCorrect.setText("Ne tacno");
                    Answer(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public boolean Answer(int chek){
        if (chek==1){
            return true;
        }else {
            return false;
        }

    }
    public int RandomNum(){
        Random rand = new Random();
        return rand.nextInt(10)+1;
    }



    public void StartCountDownTimer(long totalMilliseconds, long interval)
    {
        //CountDownTimer(long millisInFuture, long countDownInterval)
        new CountDownTimer(totalMilliseconds, interval)
        {
            //textview widget to display count down
            TextView tv = (TextView) findViewById(R.id.brojac);
            public void onTick(long millisUntilFinished) {
                if(isCanceled){

                    cancel();

                }else {

                    tv.setText("" + millisUntilFinished / 1000);
                }
            }
            public void onFinish()
            {
                //message to display when count down finished
                tv.setText("done!");
            }
        }.start();
    }

     public void CounterQuestion(){
         ++questionCunter;
        brQuestion.setText(questionCunter+"");

        if(questionCunter==10){
            
        }

     }

  


}
