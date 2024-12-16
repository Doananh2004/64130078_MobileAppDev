package th.trandoananh.th_bai8_quizappgui2fullcode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private TextView quesTxt, answerATxt, answerBTxt, answerCTxt, answerDTxt, resultTxt;
    private ImageView quesImg;
    private CardView answerALayout, answerBLayout, answerCLayout, answerDLayout;
    private Button nextBtn;
    private List<Question> questionList;
    private List<TextView> answerList;
    private List<CardView> answerLayoutList;
    private int currentQuestion;
    private int score;

    private void getControl(){
        quesTxt = findViewById(R.id.quesTxt);
        answerATxt = findViewById(R.id.answerATxt);
        answerBTxt = findViewById(R.id.answerBTxt);
        answerCTxt = findViewById(R.id.answerCTxt);
        answerDTxt = findViewById(R.id.answerDTxt);
        quesImg = findViewById(R.id.quesImg);
        resultTxt = findViewById(R.id.resultTxt);
        answerALayout = findViewById(R.id.answerALayout);
        answerBLayout = findViewById(R.id.answerBLayout);
        answerCLayout = findViewById(R.id.answerCLayout);
        answerDLayout = findViewById(R.id.answerDLayout);
        nextBtn = findViewById(R.id.nextBtn);
        answerList = new ArrayList<>(Arrays.asList(answerATxt, answerBTxt, answerCTxt, answerDTxt));
        answerLayoutList = new ArrayList<>(Arrays.asList(answerALayout, answerBLayout, answerCLayout, answerDLayout));
        createQuestion();
        currentQuestion = 0;
        score = 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getControl();
        nextBtn.setOnClickListener(nextQues);
        for(int i = 0; i < answerList.size(); i++){
            int finalI = i;
            answerList.get(i).setOnClickListener(v -> {
                if(checkAnswer(finalI)){
                    answerLayoutList.get(finalI).setCardBackgroundColor(getResources().getColor(R.color.correct));
                    resultTxt.setText("Correct!");
                    resultTxt.setTextColor(getResources().getColor(R.color.correct));
                    score++;
                    questionList.get(currentQuestion).isTrue = true;
                } else{
                    answerLayoutList.get(finalI).setCardBackgroundColor(getResources().getColor(R.color.incorrect));
                    resultTxt.setText("InCorrect!");
                    resultTxt.setTextColor(getResources().getColor(R.color.incorrect));
                }
                setButtonStateOff(finalI);
            });
        }
        displayQuestion(currentQuestion);
    }

    View.OnClickListener nextQues = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentQuestion++;
            if(currentQuestion > questionList.size()-1){
                boolean[] check = new boolean[questionList.size()];
                for(int i = 0; i < questionList.size()-1; i++){
                    check[i] = questionList.get(i).isTrue();
                }
                Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("checkTF", check);
                startActivity(intent);
                finish();
            } else {
                setButtonStateOn();
                displayQuestion(currentQuestion);
                resultTxt.setText("");
            }
        }
    };

    private boolean isCorrectAnswer(int index){
        if(answerList.get(index).getText().equals(questionList.get(currentQuestion).getCorrectAnswer())){
            answerLayoutList.get(index).setCardBackgroundColor(getResources().getColor(R.color.correct));
            return true;
        }
        return false;
    }

    private void setButtonStateOff(int chooseIndex){
        for(TextView tv : answerList){
            tv.setEnabled(false);
        }
        for(int i = 0; i < answerLayoutList.size(); i++){
            if(!isCorrectAnswer(i) && answerLayoutList.get(i)!=answerLayoutList.get(chooseIndex))
                answerLayoutList.get(i).setCardBackgroundColor(getResources().getColor(R.color.unenable));
        }
    }

    private void setButtonStateOn(){
        for(TextView tv : answerList){
            tv.setEnabled(true);
        }
        for(CardView cv : answerLayoutList){
            cv.setCardBackgroundColor(getResources().getColor(R.color.buttonColor));
        }
    }

    private boolean checkAnswer(int index){
        return answerList.get(index).getText().equals(questionList.get(currentQuestion).getCorrectAnswer());
    }

    private void displayQuestion(int currentQuestion){
        Question ques = questionList.get(currentQuestion);
        quesTxt.setText(ques.getQuestion());
        if(questionList.get(currentQuestion).getImage() != -1){
            quesImg.setVisibility(View.VISIBLE);
            quesImg.setImageResource(ques.getImage());
        }

        for(int i = 0; i < answerList.size(); i++){
            answerList.get(i).setText(ques.getAnswers().get(i));
        }
    }

    private void createQuestion(){
        questionList = new ArrayList<>(Arrays.asList(
                new Question(
                        "What is the capital of France?",
                        "Paris",
                        Arrays.asList("Paris", "London", "Berlin", "Madrid"),
                        -1
                ),
                new Question(
                        "Who invented Java programming language?",
                        "James Gosling",
                        Arrays.asList("James Gosling", "Dennis Ritchie", "Bjarne Stroustrup", "Guido van Rossum"),
                        -1
                ),
                new Question(
                        "What is the square root of 64?",
                        "8",
                        Arrays.asList("6", "7", "8", "9"),
                        -1
                ),
                new Question(
                        "Which planet is known as the Red Planet?",
                        "Mars",
                        Arrays.asList("Earth", "Venus", "Mars", "Jupiter"),
                        -1
                ),
                new Question(
                        "Which language is used for Android development?",
                        "Java",
                        Arrays.asList("C++", "Java", "Python", "Ruby"),
                        -1
                ),
                new Question(
                        "What is the boiling point of water in Celsius?",
                        "100",
                        Arrays.asList("90", "100", "110", "120"),
                        -1
                ),
                new Question(
                        "What is the name of the largest ocean on Earth?",
                        "Pacific Ocean",
                        Arrays.asList("Atlantic Ocean", "Pacific Ocean", "Indian Ocean", "Arctic Ocean"),
                        -1
                ),
                new Question(
                        "Which year did World War II end?",
                        "1945",
                        Arrays.asList("1939", "1941", "1945", "1950"),
                        -1
                ),
                new Question(
                        "Which gas do plants absorb during photosynthesis?",
                        "Carbon dioxide",
                        Arrays.asList("Oxygen", "Carbon dioxide", "Nitrogen", "Hydrogen"),
                        -1
                ),
                new Question(
                        "What is the largest animal on Earth?",
                        "Blue Whale",
                        Arrays.asList("Elephant", "Blue Whale", "Great White Shark", "Giraffe"),
                        -1
                )
        ));
    }
}