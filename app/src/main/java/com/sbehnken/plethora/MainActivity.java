package com.sbehnken.plethora;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sbehnken.plethora.model.DictionaryResponse;
import com.sbehnken.plethora.model.UserEntry;
import com.squareup.picasso.Picasso;

import java.io.File;
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

    private ImageView mBackgroundPicture;

    private Button mStartButton;
    private EditText mEnterWordsEditText;

    private RecyclerView mFinishedWordsRecyclerView;

    private UserEntryItemAdapter mAdapter;

    private TextView mTimerText;
    private TextView mTotalPoints;

    private Handler mHandler;
    private Runnable mRunnable;

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

        mStartButton = (Button) findViewById(R.id.start_button);

        mFinishedWordsRecyclerView = findViewById(R.id.finished_words_list);

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

        mBackgroundPicture = (ImageView) findViewById(R.id.background_photo);

        mEnterWordsEditText = (EditText) findViewById(R.id.enterWordsEditText);

        mTimerText = (TextView) findViewById(R.id.timer_text);
        mTotalPoints = (TextView) findViewById(R.id.total_points);

        String filename = "patternwall.jpg";

        Uri uri = Uri.fromFile(new File(filename));

        Picasso.with(this).load(uri).into(mBackgroundPicture);


        mAdapter = new UserEntryItemAdapter(this);
        mFinishedWordsRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mFinishedWordsRecyclerView.setLayoutManager(layoutManager);

        mEnterWordsEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:

                            final String word = ((EditText) v).getText().toString().toUpperCase();

                            boolean valid = false;

                            List<String> displayedLettersList = Arrays.asList(displayedTileLetter);

                            for (int i = 0; i < word.length(); i++) {
                                String c = String.valueOf(word.charAt(i));
                                if (displayedLettersList.contains(c) && word.length() > 2) {
                                    valid = true;
                                } else {
                                    valid = false;

                                    final Toast toast = Toast.makeText(getApplicationContext(), "Letter not found",
                                            Toast.LENGTH_SHORT);

                                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 200);
                                    toast.show();
                                    mEnterWordsEditText.setText("");

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
                                                UserEntry userEntry = new UserEntry(word, calculatesPoints(word));
                                                mAdapter.addWord(userEntry);
                                                mEnterWordsEditText.setText("");

                                                ArrayList<UserEntry> userEntryList = new ArrayList();
                                                userEntryList = mAdapter.getUserEntryList();

                                                int total = 0;

                                                for (int i = 0; i < userEntryList.size(); i++) {
                                                    total = total + userEntryList.get(i).getPoints();
                                                }

                                                 mTotalPoints.setText(String.valueOf(total));

                                            } else {

                                                final Toast toast = Toast.makeText(getApplicationContext(), "Word doesn't exist",
                                                        Toast.LENGTH_SHORT);

                                                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 200);
                                                toast.show();
                                                mEnterWordsEditText.setText("");
                                            }
                                        }
                                    }

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

        mStartButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                randomizeViews();

//                mHandler = new Handler();
//                mRunnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        long millis = 180000;
//                        int seconds = (int) (millis / 1000);
//                        int minutes = seconds / 60;
//                        seconds = seconds % 60;
//                        mTimerText.setText(String.valueOf(seconds));
//                        millis = millis - 1000;
//
//                        if (millis == 0) {
//                            mHandler.removeCallbacks(mRunnable);
//                        }
//
//                    }
//                };
//
//                mHandler.postDelayed(mRunnable, 1000);
//
                }
                });

        mFirstletterTextView.getText().toString();


            }

            private int calculatesPoints(String word) {
                if (word.length() == 3 || word.length() == 4) {
                    return 1;
                } else if (word.length() == 5) {
                    return 2;
                } else if (word.length() == 6) {
                    return 3;
                } else if (word.length() == 7) {
                    return 5;
                } else if (word.length() >= 8) {
                    return 11;
                }
                return 0;
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



