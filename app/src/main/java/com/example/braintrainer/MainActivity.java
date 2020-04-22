package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
        int a;
        int b;
        int correct = 0;
        CountDownTimer countDownTimer;
        Button goButton;
        GridLayout gridLayout;
        LinearLayout linearLayout;
        Button playButton;
        TextView quesView;
        String question;
        String score;
        TextView scoreView;
        int start = 0;
        int sum;
        int tag;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView timerView;
        TextView textView5;
        TextView bestScoreView;
        int total = 0;
        Float perc,old_perc;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        int high_change=1;
        String cor;
        String tol;
        String best;
        int first=1;

        public void generate_random() {
            this.a = new Random().nextInt(20) + 1;
            int nextInt = new Random().nextInt(40) + 1;
            this.b = nextInt;
            this.sum = this.a + nextInt;
            String s = Integer.toString(this.a) + " + " + Integer.toString(this.b);
            this.question = s;
            this.quesView.setText(s);
        }

        public void random_tag() {
            this.tag = new Random().nextInt(4) + 1;
        }

        public int random_num() {
            return new Random().nextInt(this.sum);
        }

        public void calc_best(){

            int old_total=Integer.parseInt(tol);
            int old_correct=Integer.parseInt(cor);
            perc=(float)correct*100/total;
            old_perc=(float)old_correct*100/old_total;
            if(first==1) {
                if (correct >= old_correct && total >= old_total) {
                    editor.clear();
                    editor.commit();
                    best = Integer.toString(correct) + "/" + Integer.toString(total);
                    sharedPreferences.edit().putString("best", best).apply();
                    split_Best();
                    high_change = 0;
                }
            }else{
                if (correct >= old_correct && perc>=old_perc) {
                    editor.clear();
                    editor.commit();
                    best = Integer.toString(correct) + "/" + Integer.toString(total);
                    sharedPreferences.edit().putString("best", best).apply();
                    split_Best();
                    high_change = 0;
                }
            }
        }

    public int random_num2() {
        return new Random().nextInt(20)+sum+1;
    }

    public void split_Best(){

            best=sharedPreferences.getString("best","0/0");
            String[] split_String=best.split("/");
            int x=0;
            for(String a: split_String){
                if(x==0){
                    cor=a;
                    x++;
                }
                if(x==1){
                    tol=a;
                    x++;
                }
            }
            bestScoreView.setText("Highest : "+best);
    }

        public void goClick(View view) {
            String str = "0/0";
            this.score = str;
            this.scoreView.setText(str);
            textView5.setVisibility(View.INVISIBLE);
            this.playButton.setVisibility(View.INVISIBLE);
            this.goButton.setVisibility(View.INVISIBLE);
            this.linearLayout.setVisibility(View.VISIBLE);
            this.gridLayout.setVisibility(View.VISIBLE);
            bestScoreView.setVisibility(View.VISIBLE);
            if (this.start == 0) {
                this.textView1.setEnabled(true);
                this.textView2.setEnabled(true);
                this.textView3.setEnabled(true);
                this.textView4.setEnabled(true);
                generate_random();
                random_tag();
                set_num();
                this.start = 1;
            }
            CountDownTimer r2 = new CountDownTimer(31000, 1000) {
                public void onTick(long millisUntilFinished) {
                    MainActivity.this.updateTimer(((int) millisUntilFinished) / 1000);
                }

                public void onFinish() {
                    calc_best();
                    MainActivity.this.playButton.setVisibility(View.VISIBLE);
                    MainActivity.this.textView1.setEnabled(false);
                    MainActivity.this.textView2.setEnabled(false);
                    MainActivity.this.textView3.setEnabled(false);
                    MainActivity.this.textView4.setEnabled(false);
                    MainActivity.this.correct = 0;
                    MainActivity.this.total = 0;
                    MainActivity.this.scoreView.setText(MainActivity.this.score);
                    MainActivity.this.start = 0;
                    //bestScoreView.setVisibility(View.VISIBLE);
                    if(high_change==0){
                        Toast.makeText(MainActivity.this, "Congratulations!!!\nNEW BEST SCORE!!", Toast.LENGTH_SHORT).show();
                    }
                    high_change=1;
                }
            };
            this.countDownTimer = r2.start();
        }

        public void set_num() {
            String ans = Integer.toString(this.sum);
            int i = this.tag;
            if (i == 1) {
                this.textView1.setText(ans);
                this.textView2.setText(Integer.toString(random_num()));
                this.textView3.setText(Integer.toString(random_num2()));
                this.textView4.setText(Integer.toString(random_num()));
            } else if (i == 2) {
                this.textView2.setText(ans);
                this.textView1.setText(Integer.toString(random_num()));
                this.textView3.setText(Integer.toString(random_num()));
                this.textView4.setText(Integer.toString(random_num2()));
            } else if (i == 3) {
                this.textView3.setText(ans);
                this.textView1.setText(Integer.toString(random_num2()));
                this.textView2.setText(Integer.toString(random_num()));
                this.textView4.setText(Integer.toString(random_num()));
            } else if (i == 4) {
                this.textView4.setText(ans);
                this.textView2.setText(Integer.toString(random_num()));
                this.textView3.setText(Integer.toString(random_num2()));
                this.textView1.setText(Integer.toString(random_num()));
            }
        }

        public void answerClick(View view) {
            if (Integer.parseInt(((TextView) view).getTag().toString()) == this.tag) {
                this.correct++;
            }
            this.total++;
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toString(this.correct));
            sb.append("/");
            sb.append(Integer.toString(this.total));
            String sb2 = sb.toString();
            this.score = sb2;
            this.scoreView.setText(sb2);
            generate_random();
            random_tag();
            set_num();
        }

        public void updateTimer(int progress) {
            String sec = Integer.toString(progress);
            TextView textView = this.timerView;
            StringBuilder sb = new StringBuilder();
            sb.append(sec);
            sb.append("s");
            textView.setText(sb.toString());
        }

        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView((int) R.layout.activity_main);
            this.linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            this.gridLayout = (GridLayout) findViewById(R.id.gridLayout);
            this.playButton = (Button) findViewById(R.id.playButton);
            this.goButton = (Button) findViewById(R.id.goButton);
            this.timerView = (TextView) findViewById(R.id.timerView);
            this.quesView = (TextView) findViewById(R.id.quesView);
            this.textView1 = (TextView) findViewById(R.id.textView1);
            this.textView2 = (TextView) findViewById(R.id.textView2);
            this.textView3 = (TextView) findViewById(R.id.textView3);
            this.textView4 = (TextView) findViewById(R.id.textView4);
            this.scoreView = (TextView) findViewById(R.id.scoreView);
            bestScoreView=(TextView) findViewById(R.id.bestScoreView);
            textView5=(TextView) findViewById(R.id.textView5);
            this.linearLayout.setVisibility(View.INVISIBLE);
            this.gridLayout.setVisibility(View.INVISIBLE);
            this.playButton.setVisibility(View.INVISIBLE);
            bestScoreView.setVisibility(View.INVISIBLE);
            sharedPreferences=this.getSharedPreferences("com.example.braintrainer", Context.MODE_PRIVATE);
            editor=sharedPreferences.edit();
            split_Best();
            if(!best.equals("0/0")){
                first=2;
            }
            scoreView.setText("0/0");
            goButton.setText("Go!");
            playButton.setText("Play Again!");
        }
    }


