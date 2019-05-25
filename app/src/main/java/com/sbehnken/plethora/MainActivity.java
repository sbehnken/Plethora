package com.sbehnken.plethora;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private enum ViewConfiguration {
        INITIALIZED,
        PLAYING,
        PAUSED,
    }

    private TextView mFirstletterTextView;
    private TextView mSecondletterTextView;
    private TextView mThirdletterTextView;
    private TextView mFourthletterTextView;
    private TextView mFifthletterTextView;
    private TextView mSixthletterTextView;
    private TextView mSeventhletterTextView;
    private TextView mEighthletterTextView;
    private TextView mNinthLetterTextView;
    private TextView mTenthLetterTextView;
    private TextView mEleventhLetterTextView;
    private TextView mTwelfthLetterTextView;
    private TextView mThirteenthLetterTextView;
    private TextView mFourteenthLetterTextView;
    private TextView mFifteenthLetterTextView;
    private TextView mSixteenthLetterTextView;
    private TextView mTimerText;
    private TextView mTotalPoints;
    private EditText mEnterWordsEditText;

    private Button mBackButton;

    private UserEntryItemAdapter mAdapter;

    private ViewConfiguration viewConfiguration = ViewConfiguration.INITIALIZED;

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

    String result = "";

    private final static String SHARED_PREFS = "shared_prefs";

    private int soundState = 0;

    private MediaPlayer mediaPlayer;
    private RecyclerView mFinishedWordsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button mStartButton = findViewById(R.id.start_button);
        final Button mEnterButton = findViewById(R.id.enter_button);
        mBackButton = findViewById(R.id.back_button);

        mFinishedWordsRecyclerView = findViewById(R.id.finished_words_list);

        mFirstletterTextView = findViewById(R.id.firstLetterTextView);
        mSecondletterTextView = findViewById(R.id.secondLetterTextView);
        mThirdletterTextView = findViewById(R.id.thirdLetterTextView);
        mFourthletterTextView = findViewById(R.id.fourthLetterTextView);
        mFifthletterTextView = findViewById(R.id.fifthLetterTextView);
        mSixthletterTextView = findViewById(R.id.sixthLetterTextView);
        mSeventhletterTextView = findViewById(R.id.seventhLetterTextView);
        mEighthletterTextView = findViewById(R.id.eigthLetterTextView);
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

        final View layout = findViewById(R.id.dice_layout);

        mediaPlayer = MediaPlayer.create(this, R.raw.endring);

        SharedPreferences mSharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt(SHARED_PREFS, soundState);
        mEditor.apply();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setTitle("Plethora");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            mEnterWordsEditText.setShowSoftInputOnFocus(false);
        } else {
            mEnterWordsEditText.setTextIsSelectable(true);
        }

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textString = mEnterWordsEditText.getText().toString();
                if( mBackButton != null && textString.length() > 0) {
                        mEnterWordsEditText.setText(textString.substring(0, textString.length() - 1));
                        mEnterWordsEditText.setSelection(mEnterWordsEditText.getText().length());
                }
            }
        });

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.timer_complete_msg)
                .setNegativeButton(getString(R.string.quit_message), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        layout.setVisibility(View.INVISIBLE);
                        mEnterWordsEditText.setVisibility(View.INVISIBLE);
                        viewConfiguration = ViewConfiguration.INITIALIZED;
                        dialog.dismiss();
                        finish();
                    }
                })
                .setPositiveButton(getString(R.string.play_again_message), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        layout.setVisibility(View.INVISIBLE);
                        mEnterWordsEditText.setVisibility(View.INVISIBLE);
                        viewConfiguration = ViewConfiguration.INITIALIZED;
                        mEnterWordsEditText.setText("");
                        dialog.dismiss();
                    }
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setBackgroundDrawable(getDrawable(R.drawable.twillbeige));
        } else {
            Picasso.with(this).load(R.drawable.twillbeige).error(getResources().getDrawable(R.drawable.ic_launcher_background)).fit().into(mBackgroundPicture);
        }

        mAdapter = new UserEntryItemAdapter(this);
        mFinishedWordsRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mFinishedWordsRecyclerView.setLayoutManager(layoutManager);

        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordAndPointCheck();
                mEnterWordsEditText.setText("");
                result = "";
            }
        });

        mStartButton.setOnClickListener(new View.OnClickListener() {
            private static final long START_TIME_IN_MILLIS = 180000;
            private CountDownTimer mCountDownTimer;
            private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

            @Override
            public void onClick(View view) {
                switch (viewConfiguration) {
                    case INITIALIZED:
                        if (mTimeLeftInMillis == 180000 || mTimerText.getText().equals("00:00")) {
                            randomizeViews();
                            mAdapter.getUserEntryList().clear();
                            mTotalPoints.setText("");
                            mEnterWordsEditText.setVisibility(View.VISIBLE);
                            viewConfiguration = ViewConfiguration.PLAYING;
                        }
                    case PLAYING:
                        startTimer();
                        viewConfiguration = ViewConfiguration.PAUSED;
                        break;

                    case PAUSED:
                        pauseTimer();
                        mStartButton.setText(R.string.start_button_message);
                        viewConfiguration = ViewConfiguration.PLAYING;
                        break;
                }
            }

            private void startTimer() {

                layout.setVisibility(View.VISIBLE);

                mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mTimeLeftInMillis = millisUntilFinished;
                        updateCountDownText();
                    }

                    @Override
                    public void onFinish() {
                        if (viewConfiguration.equals(ViewConfiguration.PAUSED) && mTimerText.getText().equals("00:00")) {
                            alertDialog.show();
                            resetTimer();
                            mStartButton.setText(R.string.start_button_message);
                            if (soundState == 1) {
                                mediaPlayer.start();
                            }
                        }
                    }
                }.start();

                mStartButton.setText(R.string.pause_button_message);
            }

            private void pauseTimer() {
                mCountDownTimer.cancel();
                mStartButton.setText(R.string.start_button_message);
                View layout = findViewById(R.id.dice_layout);
                layout.setVisibility(View.INVISIBLE);
            }

            private void resetTimer() {
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                updateCountDownText();
            }

            private void updateCountDownText() {
                int minute = (int) (mTimeLeftInMillis / 1000) / 60;
                int second = (int) (mTimeLeftInMillis / 1000) % 60;
                mTimerText.setText(String.format(Locale.getDefault(), "%02d:%02d", minute, second));
            }
        });

        mFirstletterTextView.setOnClickListener(this);
        mSecondletterTextView.setOnClickListener(this);
        mThirdletterTextView.setOnClickListener(this);
        mFourthletterTextView.setOnClickListener(this);
        mFifthletterTextView.setOnClickListener(this);
        mSixthletterTextView.setOnClickListener(this);
        mSeventhletterTextView.setOnClickListener(this);
        mEighthletterTextView.setOnClickListener(this);
        mNinthLetterTextView.setOnClickListener(this);
        mTenthLetterTextView.setOnClickListener(this);
        mEleventhLetterTextView.setOnClickListener(this);
        mTwelfthLetterTextView.setOnClickListener(this);
        mThirteenthLetterTextView.setOnClickListener(this);
        mFourteenthLetterTextView.setOnClickListener(this);
        mFifteenthLetterTextView.setOnClickListener(this);
        mSixteenthLetterTextView.setOnClickListener(this);

        hideSoftKeyboard();
    }

    @Override
    public void onClick(View v) {
        if(mBackButton != null) {
            result = mEnterWordsEditText.getText().toString();
        }
        switch (v.getId()) {
            case R.id.firstLetterTextView:
                result += mFirstletterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.secondLetterTextView:
                result += mSecondletterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.thirdLetterTextView:
                result += mThirdletterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.fourthLetterTextView:
                result += mFourthletterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.fifthLetterTextView:
                result += mFifthletterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.sixthLetterTextView:
                result += mSixthletterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.seventhLetterTextView:
                result += mSeventhletterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.eigthLetterTextView:
                result += mEighthletterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.ninthLetterTextView:
                result += mNinthLetterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.tenthLetterTextView:
                result += mTenthLetterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.eleventhLetterTextView:
                result += mEleventhLetterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.twelfthLetterTextView:
                result += mTwelfthLetterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.thirteenthLetterTextView:
                result += mThirteenthLetterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.fourteenthLetterTextView:
                result += mFourteenthLetterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.fifteenthLetterTextView:
                result += mFifteenthLetterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
            case R.id.sixteenthLetterTextView:
                result += mSixteenthLetterTextView.getText().toString();
                mEnterWordsEditText.setText(result);
                break;
        }
        mEnterWordsEditText.setSelection(mEnterWordsEditText.getText().length());
    }

    private int calculatePoints(String w) {
        if (w.length() == 3) {
            return 1;
        } else if (w.length() == 4) {
            return 2;
        } else if (w.length() == 5) {
            return 3;
        } else if (w.length() == 6) {
            return 4;
        } else if (w.length() == 7) {
            return 5;
        } else if (w.length() >= 8) {
            return 11;
        }
        return 0;
    }

    private void randomizeViews() {
        //todo replace with a spread
        final List<Integer> numbers = new ArrayList<>();
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

        final List<TextView> viewList = new ArrayList<>();
        viewList.add(mFirstletterTextView);
        viewList.add(mSecondletterTextView);
        viewList.add(mThirdletterTextView);
        viewList.add(mFourthletterTextView);
        viewList.add(mFifthletterTextView);
        viewList.add(mSixthletterTextView);
        viewList.add(mSeventhletterTextView);
        viewList.add(mEighthletterTextView);
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

    public void wordAndPointCheck() {
        final String word = mEnterWordsEditText.getText().toString().toUpperCase();

        boolean valid = false;

        List<String> displayedLettersList = Arrays.asList(displayedTileLetter);

        for (int i = 0; i < word.length(); i++) {
            String c = String.valueOf(word.charAt(i));

            if ((String.valueOf(word.charAt(i)).equals("Q")) && String.valueOf(word.charAt(i + 1)).equals("U")) {
                valid = true;
                i++;
                break;
            }
            if (displayedLettersList.contains(c) && word.length() > 2) {
                valid = true;
            } else {
                valid = false;

                final Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_msg_error_word_too_short),
                        Toast.LENGTH_SHORT);

                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 500);
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
                        mFinishedWordsRecyclerView.scrollToPosition(userEntryList.size());

                        int total = 0;
                        boolean isDouble = false;

                        for (int i = 0; i < userEntryList.size(); i++) {
                            String wordEntered = userEntryList.get(i).getWord();
                            total = total + userEntryList.get(i).getPoints();
                            if(wordEntered.equals(word)) {
                                isDouble = true;
                                break;
                            }
                        }
                        if (isDouble) {
                            final Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_msg_error_duplicate),
                                    Toast.LENGTH_SHORT);

                            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 500);
                            toast.show();
                        } else {
                            final UserEntry userEntry = new UserEntry(word, calculatePoints(word));

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

                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 500);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.settings_item:
    final AlertDialog.Builder ad = new AlertDialog.Builder(this)
            .setTitle(R.string.sound_message)
            .setNegativeButton(getString(R.string.sound_off), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    soundState = 0;
                    if(soundState == 0) {
                        mediaPlayer.release();
                        getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).edit().putBoolean("mute_pressed", true).apply();
                    } else if (soundState == 1) {
                        mediaPlayer.start();
                        getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).edit().putBoolean("mute_pressed", true).apply();

                    }
                    dialog.dismiss();
                }
            })
            .setPositiveButton(getString(R.string.sound_on), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    soundState = 1;
                    dialog.dismiss();
                }
            });
             ad.show();
        }
        return false;
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}





