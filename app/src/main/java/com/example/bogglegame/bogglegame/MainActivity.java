package com.example.bogglegame.bogglegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bogglegame.bogglegame.model.DictionaryResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity  {

    private TextView mFirstletterTextView;
    private TextView mSecondletterTextView;
    private TextView mThirdletterTextView;
    private TextView mFourthletterTextView;
    private TextView mFifthletterTextView;
    private TextView mSixthletterTextView;
    private TextView mSeventhletterTextView;
    private TextView mEightletterTextView;
    private TextView mNinthLetterTextView;
    private TextView mTenthLetterTextView;
    private TextView mEleventhLetterTextView;
    private TextView mTwelfthLetterTextView;
    private TextView mThirteenthLetterTextView;
    private TextView mFourteenthLetterTextView;
    private TextView mFifteenthLetterTextView;
    private TextView mSixteenthLetterTextView;

    private Button mGetLettersButton;
    private EditText mEnterWordsEditText;
//    private Button mEnterButton;
    private TextView mFinishedWords;

    private String[][] dice = {
            {"R", "I", "F", "O", "B", "N"},
            {"I", "F", "E", "H", "E", "Y"},
            {"D", "E", "N", "O", "W", "S"},
            {"U", "T", "O", "K", "N", "D"},
            {"H", "M", "S", "R", "A", "O"},
            {"L", "U", "P", "E", "T", "S"},
            {"A", "C", "I", "T", "O", "A"},
            {"Y", "L", "G", "K", "U", "E"},
            {"QU", "J", "M", "B", "O", "A"},
            {"E", "H", "I", "S", "P", "N"},
            {"V", "E", "T", "I", "G", "N"},
            {"B", "A", "L", "I", "Y", "T"},
            {"E", "Z", "A", "V", "N", "D"},
            {"R", "A", "L", "E", "S", "C"},
            {"U", "W", "I", "L", "R", "G"},
            {"P", "A", "C", "E", "M", "D"}
    };

    private String[] displayedTileLetter = new String[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGetLettersButton = (Button) findViewById(R.id.getLettersButton);
        mFinishedWords = (TextView) findViewById(R.id.finishedWords);

//        mEnterButton = (Button) findViewById(R.id.enterButton);
//
//        mEnterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String words = mEnterWordsEditText.getText().toString();
//                mFinishedWords.setText(words);
//
//            }
//            });

        mFirstletterTextView = (TextView) findViewById(R.id.firstLetterTextView);
        mSecondletterTextView = (TextView) findViewById(R.id.secondLetterTextView);
        mThirdletterTextView = (TextView) findViewById(R.id.thirdLetterTextView);
        mFourthletterTextView = (TextView) findViewById(R.id.fourthLetterTextView);
        mFifthletterTextView = (TextView) findViewById(R.id.fifthLetterTextView);
        mSixthletterTextView = (TextView) findViewById(R.id.sixthLetterTextView);
        mSeventhletterTextView = (TextView) findViewById(R.id.seventhLetterTextView);
        mEightletterTextView = (TextView) findViewById(R.id.eigthLetterTextView);
        mNinthLetterTextView = (TextView) findViewById(R.id.ninthLetterTextView);
        mTenthLetterTextView = (TextView) findViewById(R.id.tenthLetterTextView);
        mEleventhLetterTextView = (TextView) findViewById(R.id.eleventhLetterTextView);
        mTwelfthLetterTextView = (TextView) findViewById(R.id.twelfthLetterTextView);
        mThirteenthLetterTextView = (TextView) findViewById(R.id.thirteenthLetterTextView);
        mFourteenthLetterTextView = (TextView) findViewById(R.id.fourteenthLetterTextView);
        mFifteenthLetterTextView = (TextView) findViewById(R.id.fifteenthLetterTextView);
        mSixteenthLetterTextView = (TextView) findViewById(R.id.sixteenthLetterTextView);

        mEnterWordsEditText = (EditText) findViewById(R.id.enterWordsEditText);

        final Toast toast = Toast.makeText(getApplicationContext(), "Word doesn't exist",
                Toast.LENGTH_SHORT);

        mEnterWordsEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:

                            final String word = ((EditText) v).getText().toString().toUpperCase();

                            boolean valid = false;

                            List<String> list = Arrays.asList(displayedTileLetter);

                            for (int i = 0; i < word.length(); i++) {
                                String c = String.valueOf(word.charAt(i));
                                if (list.contains(c)) {
                                    valid = true;
                                } else {
                                    valid = false;

                                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 200);
                                    toast.show();

                                    break;


                            }

                            }

                            if (valid) {

                                final DictionaryService dictionaryService = new DictionaryService();
                                dictionaryService.getResponse(word).enqueue(new Callback<DictionaryResponse>() {
                                    @Override
                                    public void onResponse(Call<DictionaryResponse> call, Response<DictionaryResponse> response) {
                                        if (response != null) {

                                            if (response.body() != null) {

                                                String moveWord = mFinishedWords.getText().toString() + "\n" + word;
                                                mFinishedWords.setText(moveWord);
                                                mEnterWordsEditText.setText("");

                                            } else {

                                                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 200);
                                                toast.show();
                                            }
                                        }
                                    }

                                    //todo null out wrong words
                                    //todo make pretty

                                    @Override
                                    public void onFailure(Call<DictionaryResponse> call, Throwable t) {
                                        Log.d("MainActivity", t.getLocalizedMessage());

                                        Toast.makeText(MainActivity.this, "Network Error. Please try again.", Toast.LENGTH_SHORT).show();

                                    }

                                });

                            }
                            break;
                        default:
                            return true;

                    }
                }
                return false;

            }

        });

        mGetLettersButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                randomizeViews();

                }
                });

        mFirstletterTextView.getText().toString();


            }

            private void randomizeViews() {

                List<Integer> numbers = new ArrayList<Integer>();
                numbers.add(0);
                numbers.add(1);
                numbers.add(2);
                numbers.add(3);
                numbers.add(4);
                numbers.add(5);
                numbers.add(6);
                numbers.add(7);
                numbers.add(8);
                numbers.add(9);
                numbers.add(10);
                numbers.add(11);
                numbers.add(12);
                numbers.add(13);
                numbers.add(14);
                numbers.add(15);

                Collections.shuffle(numbers);

                List<TextView> viewList = new ArrayList<>();
                viewList.add(mFirstletterTextView);
                viewList.add(mSecondletterTextView);
                viewList.add(mThirdletterTextView);
                viewList.add(mFourthletterTextView);
                viewList.add(mFifthletterTextView);
                viewList.add(mSixthletterTextView);
                viewList.add(mSeventhletterTextView);
                viewList.add(mEightletterTextView);
                viewList.add(mNinthLetterTextView);
                viewList.add(mTenthLetterTextView);
                viewList.add(mEleventhLetterTextView);
                viewList.add(mTwelfthLetterTextView);
                viewList.add(mThirteenthLetterTextView);
                viewList.add(mFourteenthLetterTextView);
                viewList.add(mFifteenthLetterTextView);
                viewList.add(mSixteenthLetterTextView);

                Collections.shuffle(viewList);

                for(int i = 0; i < 16; i++) {
                    displayedTileLetter[numbers.get(i)] = dice[numbers.get(i)][new Random().nextInt(6)];
                    viewList.get(numbers.get(i)).setText(displayedTileLetter[numbers.get(i)]);

                }


            }


        }



