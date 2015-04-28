package edu.washington.vivyanw.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.*;

/**
 * Created by WoodsFamily on 4/27/15.
 */
public class Question extends ActionBarActivity {

    String topic;
    TextView title;
    TextView question;
    Button submit;

    RadioGroup answers;
    RadioButton opt1;
    RadioButton opt2;
    RadioButton opt3;
    RadioButton opt4;

    int numQ;
    int qAnswered;
    String answer;
    String rightAnswer;
    int correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_layout);

        Intent launchingIntent = getIntent();
        topic = launchingIntent.getStringExtra("topic");
        numQ = launchingIntent.getIntExtra("numQ", 1);
        qAnswered = launchingIntent.getIntExtra("answered", 0);
        correct = launchingIntent.getIntExtra("correct", 0);

        title = (TextView) findViewById(R.id.txtTitle);
        title.setText(topic);

        question = (TextView) findViewById(R.id.txtQuestion);
        answers = (RadioGroup) findViewById(R.id.rdogrpAnswers);
        opt1 = (RadioButton) findViewById(R.id.btnChoice1);
        opt2 = (RadioButton) findViewById(R.id.btnChoice2);
        opt3 = (RadioButton) findViewById(R.id.btnChoice3);
        opt4 = (RadioButton) findViewById(R.id.btnChoice4);
        submit = (Button) findViewById(R.id.btnSubmit);

        if (topic.equalsIgnoreCase("math")) {
            setMathQ();
        } else if (topic.equalsIgnoreCase("physics")) {
            setPhysicsQ();
        } else if (topic.equalsIgnoreCase("marvel super heroes")) {
            setMarvelQ();
        } else if (topic.equalsIgnoreCase("spongebob")) {
            setSpongeQ();
        } else if (topic.equalsIgnoreCase("legend of zelda")) {
            setZeldaQ();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qAnswered++;
                Intent next = new Intent(Question.this, Answer.class);
                next.putExtra("correct", correct);
                next.putExtra("topic", topic);
                next.putExtra("numQ", numQ);
                next.putExtra("answered", qAnswered);
                next.putExtra("choice", answer);
                next.putExtra("right", rightAnswer);

                startActivity(next);
            }
        });
    }

    private void setMathQ() {
        if (qAnswered == 0) {
            question.setText("pi is 3.141592...., what is e?");

            opt1.setText("2");
            opt2.setText("2.718281828");
            opt3.setText("10");
            opt4.setText("1.6180339887");
            rightAnswer = (String) opt2.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt2.getId()) {
                        correct++;
                        answer = (String) opt2.getText();
                    } else {
                        if (checkedId == opt1.getId()) {
                            answer = (String) opt1.getText();
                        } else if (checkedId == opt4.getId()) {
                            answer = (String) opt4.getText();
                        } else {
                            answer = (String) opt3.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        } else {
            question.setText("what is the derivative of e ^ x?");

            opt1.setText("e");
            opt2.setText("e ^ x + C");
            opt3.setText("e ^ x");
            opt4.setText("e ^ (1/x)");
            rightAnswer = (String) opt3.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt3.getId()) {
                        correct++;
                        answer = (String) opt3.getText();
                    } else {
                        if (checkedId == opt1.getId()) {
                            answer = (String) opt1.getText();
                        } else if (checkedId == opt2.getId()) {
                            answer = (String) opt2.getText();
                        } else {
                            answer = (String) opt4.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void setPhysicsQ() {

        if (qAnswered == 0) {
            question.setText("What is 9.98m/s^2?");

            opt1.setText("speed of light");
            opt2.setText("particle acceleration");
            opt3.setText("force of gravity");
            opt4.setText("amount of atoms per mole");
            rightAnswer = (String) opt3.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt3.getId()) {
                        correct++;
                        answer = (String) opt3.getText();
                    } else {
                        if (checkedId == opt1.getId()) {
                            answer = (String) opt1.getText();
                        } else if (checkedId == opt2.getId()) {
                            answer = (String) opt2.getText();
                        } else {
                            answer = (String) opt4.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        } else {
            question.setText("Which law of physics is not part of the grand unified theory?");

            opt1.setText("weak interactions");
            opt2.setText("strong interactions");
            opt3.setText("electromagnetism");
            opt4.setText("gravity");
            rightAnswer = (String) opt4.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt4.getId()) {
                        correct++;
                        answer = (String) opt4.getText();
                    } else {
                        if (checkedId == opt1.getId()) {
                            answer = (String) opt1.getText();
                        } else if (checkedId == opt2.getId()) {
                            answer = (String) opt2.getText();
                        } else {
                            answer = (String) opt3.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void setMarvelQ() {
        if (qAnswered == 0) {
            question.setText("Which marvel super hero started as a military contractor?");

            opt1.setText("Iron Man");
            opt2.setText("Batman");
            opt3.setText("Superman");
            opt4.setText("Captain America");
            rightAnswer = (String) opt1.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt1.getId()) {
                        correct++;
                        answer = (String) opt1.getText();
                    } else {
                        if (checkedId == opt4.getId()) {
                            answer = (String) opt4.getText();
                        } else if (checkedId == opt2.getId()) {
                            answer = (String) opt2.getText();
                        } else {
                            answer = (String) opt3.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        } else if (qAnswered == 1) {
            question.setText("Which super hero doesn't have an alter ego?");

            opt1.setText("Iron Man");
            opt2.setText("Thor");
            opt3.setText("Hawkeye");
            opt4.setText("Hulk");
            rightAnswer = (String) opt2.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt2.getId()) {
                        correct++;
                        answer = (String) opt2.getText();
                    } else {
                        if (checkedId == opt4.getId()) {
                            answer = (String) opt4.getText();
                        } else if (checkedId == opt1.getId()) {
                            answer = (String) opt1.getText();
                        } else {
                            answer = (String) opt3.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        } else {
            question.setText("Which super hero is not part of the Avengers?");

            opt1.setText("Iron Man");
            opt2.setText("Hulk");
            opt3.setText("Thor");
            opt4.setText("Loki");
            rightAnswer = (String) opt4.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt4.getId()) {
                        correct++;
                        answer = (String) opt4.getText();
                    } else {
                        if (checkedId == opt1.getId()) {
                            answer = (String) opt1.getText();
                        } else if (checkedId == opt2.getId()) {
                            answer = (String) opt2.getText();
                        } else {
                            answer = (String) opt3.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void setSpongeQ() {
        if (qAnswered == 0) {
            question.setText("What were Spongebob's first words?");

            opt1.setText("I'm ready!");
            opt2.setText("Krabby Patty");
            opt3.setText("Forts win wars");
            opt4.setText("May I take your order?");
            rightAnswer = (String) opt4.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt4.getId()) {
                        correct++;
                        answer = (String) opt4.getText();
                    } else {
                        if (checkedId == opt1.getId()) {
                            answer = (String) opt1.getText();
                        } else if (checkedId == opt2.getId()) {
                            answer = (String) opt2.getText();
                        } else {
                            answer = (String) opt3.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        } else if (qAnswered == 1) {
            question.setText("What is Spongebob's favorite color?");

            opt1.setText("Beige");
            opt2.setText("Aquamarine");
            opt3.setText("14");
            opt4.setText("Clear");
            rightAnswer = (String) opt1.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt1.getId()) {
                        correct++;
                        answer = (String) opt1.getText();
                    } else {
                        if (checkedId == opt4.getId()) {
                            answer = (String) opt4.getText();
                        } else if (checkedId == opt2.getId()) {
                            answer = (String) opt2.getText();
                        } else {
                            answer = (String) opt3.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        } else {
            question.setText("What is Spongebob's address?");

            opt1.setText("123 Conch Street");
            opt2.setText("124 Conch Street");
            opt3.setText("124 Anemone Lane");
            opt4.setText("123 Jellyfish Lane");
            rightAnswer = (String) opt2.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt2.getId()) {
                        correct++;
                        answer = (String) opt2.getText();
                    } else {
                        if (checkedId == opt1.getId()) {
                            answer = (String) opt1.getText();
                        } else if (checkedId == opt4.getId()) {
                            answer = (String) opt4.getText();
                        } else {
                            answer = (String) opt3.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void setZeldaQ() {
        if (qAnswered == 0) {
            question.setText("In what year was the first Legend of Zelda game released?");

            opt1.setText("1986");
            opt2.setText("1987");
            opt3.setText("1992");
            opt4.setText("2001");
            rightAnswer = (String) opt1.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt1.getId()) {
                        correct++;
                        answer = (String) opt1.getText();
                    } else {
                        if (checkedId == opt4.getId()) {
                            answer = (String) opt4.getText();
                        } else if (checkedId == opt2.getId()) {
                            answer = (String) opt2.getText();
                        } else {
                            answer = (String) opt3.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        } else if (qAnswered == 1) {
            question.setText("In the Hyrule timeline, which game takes place the earliest?");

            opt1.setText("The Legend of Zelda");
            opt2.setText("The Legend of Zelda: Four Swords");
            opt3.setText("The Legend of Zelda: Skyward Sword");
            opt4.setText("The Legend of Zelda: Ocarina of Time");
            rightAnswer = (String) opt3.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt3.getId()) {
                        correct++;
                        answer = (String) opt3.getText();
                    } else {
                        if (checkedId == opt4.getId()) {
                            answer = (String) opt4.getText();
                        } else if (checkedId == opt2.getId()) {
                            answer = (String) opt2.getText();
                        } else {
                            answer = (String) opt1.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        } else {
            question.setText("What was the first Legend of Zelda game released for the Wii?");

            opt1.setText("The Legend of Zelda: Ocarina of Time");
            opt2.setText("The Legend of Zelda: Twilight Princess");
            opt3.setText("The Legend of Zelda: Wind Waker");
            opt4.setText("The Legend of Zelda: Skyward Sword");
            rightAnswer = (String) opt2.getText();

            answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == opt2.getId()) {
                        correct++;
                        answer = (String) opt2.getText();
                    } else {
                        if (checkedId == opt4.getId()) {
                            answer = (String) opt4.getText();
                        } else if (checkedId == opt1.getId()) {
                            answer = (String) opt1.getText();
                        } else {
                            answer = (String) opt3.getText();
                        }
                    }
                    submit.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
