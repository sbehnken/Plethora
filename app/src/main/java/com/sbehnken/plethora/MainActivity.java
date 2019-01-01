package com.sbehnken.plethora;

import android.os.CountDownTimer;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

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

    private EditText mEnterWordsEditText;

    private UserEntryItemAdapter mAdapter;

    private TextView mTimerText;
    private TextView mTotalPoints;

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

        Button mStartButton = findViewById(R.id.start_button);

        RecyclerView mFinishedWordsRecyclerView = findViewById(R.id.finished_words_list);

        mFirstletterTextView = findViewById(R.id.firstLetterTextView);
        mSecondletterTextView = findViewById(R.id.secondLetterTextView);
        mThirdletterTextView = findViewById(R.id.thirdLetterTextView);
        mFourthletterTextView = findViewById(R.id.fourthLetterTextView);
        mFifthletterTextView = findViewById(R.id.fifthLetterTextView);
        mSixthletterTextView = findViewById(R.id.sixthLetterTextView);
        mSeventhletterTextView = findViewById(R.id.seventhLetterTextView);
        mEightletterTextView = findViewById(R.id.eigthLetterTextView);
        mNinthLetterTextView = findViewById(R.id.ninthLetterTextView);
        mTenthLetterTextView = findViewById(R.id.tenthLetterTextView);
        mEleventhLetterTextView = findViewById(R.id.eleventhLetterTextView);
        mTwelfthLetterTextView = findViewById(R.id.twelfthLetterTextView);
        mThirteenthLetterTextView = findViewById(R.id.thirteenthLetterTextView);
        mFourteenthLetterTextView = findViewById(R.id.fourteenthLetterTextView);
        mFifteenthLetterTextView = findViewById(R.id.fifteenthLetterTextView);
        mSixteenthLetterTextView = findViewById(R.id.sixteenthLetterTextView);

        ImageView mBackgroundPicture = findViewById(R.id.background_photo);

        mEnterWordsEditText = findViewById(R.id.enterWordsEditText);

        mTimerText = findViewById(R.id.timer_text);
        mTotalPoints = findViewById(R.id.total_points);

        Picasso.with(this).load(R.drawable.patternwall).error(getResources().getDrawable(R.drawable.ic_launcher_background)).fit().into(mBackgroundPicture);

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

                                    final Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_msg_error_wrong_letter),
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

                                            if (response.body() != null) {

                                                ArrayList<UserEntry> userEntryList = mAdapter.getUserEntryList();

                                                int total = 0;
                                                boolean isDouble = false;

                                                for (int i = 0; i < userEntryList.size(); i++) {
                                                    isDouble = userEntryList.get(i).getWord().equals(word);

                                                    total = total + userEntryList.get(i).getPoints();
                                                }

                                                if (isDouble) {

                                                    final Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_msg_error_duplicate),
                                                            Toast.LENGTH_SHORT);

                                                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 200);
                                                    toast.show();

                                                } else {

                                                    UserEntry userEntry = new UserEntry(word, calculatesPoints(word));

                                                    if (userEntryList.size() < 1) {
                                                        mTotalPoints.setText(String.valueOf(userEntry.getPoints()));

                                                    } else {

                                                        mTotalPoints.setText(String.valueOf(total + userEntry.getPoints()));
                                                    }

                                                    mAdapter.addWord(userEntry);
                                                }

                                                mEnterWordsEditText.setText("");

                                            } else {

                                                final Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_msg_error_not_exist),
                                                        Toast.LENGTH_SHORT);

                                                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 200);
                                                toast.show();
                                                mEnterWordsEditText.setText("");
                                            }
                                        }

                                    @Override
                                    public void onFailure(Call<DictionaryResponse> call, Throwable t) {
                                        Log.d("MainActivity", t.getLocalizedMessage());

                                        Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_msg_error_network), Toast.LENGTH_SHORT).show();

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

                new CountDownTimer(180000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        int minute = (int) (millisUntilFinished / 1000) / 60;
                        int second = (int) (millisUntilFinished / 1000) % 60;
                        mTimerText.setText(String.format("%02d", minute) + ":" + String.format("%02d", second));
                    }

                    public void onFinish() {
                        mTimerText.setText(getString(R.string.timer_complete_msg));
                    }

                }.start();

            }
        });

        mFirstletterTextView.getText();

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

        List<Integer> numbers = new ArrayList<>();
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

        for (int i = 0; i < 16; i++) {
            displayedTileLetter[numbers.get(i)] = dice[numbers.get(i)][new Random().nextInt(6)];
            viewList.get(numbers.get(i)).setText(displayedTileLetter[numbers.get(i)]);

        }
    }
}



