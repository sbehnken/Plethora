package com.example.bogglegame.bogglegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

//    private String[] dieOne = new String[] {"R", "I", "F", "O", "B", "N"};
//    private String[] dieTwo = new String[] {"I", "F", "E", "H", "E", "Y"};
//    private String[] dieThree = new String[] {"D", "E", "N", "O", "W", "S"};
//    private String[] dieFour = new String[] {"U", "T", "O", "K", "N", "D"};
//    private String[] dieFive = new String[] {"H", "M", "S", "R", "A", "O"};
//    private String[] dieSix = new String[] {"L", "U", "P", "E", "T", "S"};
//    private String[] dieSeven = new String[] {"A", "C", "I", "T", "O", "A"};
//    private String[] dieEight = new String[] {"Y", "L", "G", "K", "U", "E"};
//    private String[] dieNine = new String[] {"QU", "J", "M", "B", "O", "A"};
//    private String[] dieTen = new String[] {"E", "H", "I", "S", "P", "N"};
//    private String[] dieEleven = new String[] {"V", "E", "T", "I", "G", "N"};
//    private String[] dieTwelve = new String[] {"B", "A", "L", "I", "Y", "T"};
//    private String[] dieThirteen = new String[] {"E", "Z", "A", "V", "N", "D"};
//    private String[] dieFourteen = new String[] {"R", "A", "L", "E", "S", "C"};
//    private String[] dieFifteen = new String[] {"U", "W", "I", "L", "R", "G"};
//    private String[] dieSixteen = new String[] {"P", "A", "C", "E", "M", "D"};
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

        mEnterWordsEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:

                            String word = ((EditText) v).getText().toString();

                            boolean valid = false;

                            for (int i = 0; i < word.length(); i++) {
                                char c = word.charAt(i);
                                for (int j = 0; j < displayedTileLetter.length; j++) {
                                    if (displayedTileLetter[j].toLowerCase().charAt(0) == Character.toLowerCase(c)) {
                                        valid = true;
                                    }
                                }
                            }

                            if (valid) {
                                final DictionaryService dictionaryService = new DictionaryService();
                                dictionaryService.validateWord(word, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        dictionaryService.processResults(response);

                                    }
                                });

                                String newValue = mFinishedWords.getText().toString() + "\n" + word;
                                mFinishedWords.setText(newValue);
                                mEnterWordsEditText.setText("");
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

//                displayedTileLetter[0] = dice[0][new Random().nextInt(dice[0].length)];
//                mFirstletterTextView.setText(displayedTileLetter[0]);
//
//                displayedTileLetter[1] = dice[1][new Random().nextInt(dice[1].length)];
//                mSecondletterTextView.setText(displayedTileLetter[1]);
//
//                displayedTileLetter[2] = dice[2][new Random().nextInt(dice[2].length)];
//                mThirdletterTextView.setText(displayedTileLetter[2]);
//
//                displayedTileLetter[3] = dice[3][new Random().nextInt(dice[3].length)];
//                mFourthletterTextView.setText(displayedTileLetter[3]);
//
//                displayedTileLetter[4] = dice[4][new Random().nextInt(dice[4].length)];
//                mFifthletterTextView.setText(displayedTileLetter[4]);
//
//                displayedTileLetter[5] = dice[5][new Random().nextInt(dice[5].length)];
//                mSixthletterTextView.setText(displayedTileLetter[5]);
//
//                displayedTileLetter[6] = dice[6][new Random().nextInt(dice[6].length)];
//                mSeventhletterTextView.setText(displayedTileLetter[6]);
//
//                displayedTileLetter[7] = dice[7][new Random().nextInt(dice[7].length)];
//                mEightletterTextView.setText(displayedTileLetter[7]);
//
//                displayedTileLetter[8] = dice[8][new Random().nextInt(dice[8].length)];
//                mNinthLetterTextView.setText(displayedTileLetter[8]);
//
//                displayedTileLetter[9] = dice[9][new Random().nextInt(dice[9].length)];
//                mTenthLetterTextView.setText(displayedTileLetter[9]);
//
//                displayedTileLetter[10] = dice[10][new Random().nextInt(dice[10].length)];
//                mEleventhLetterTextView.setText(displayedTileLetter[10]);
//
//                displayedTileLetter[11] = dice[11][new Random().nextInt(dice[11].length)];
//                mTwelfthLetterTextView.setText(displayedTileLetter[11]);
//
//                displayedTileLetter[12] = dice[12][new Random().nextInt(dice[12].length)];
//                mThirteenthLetterTextView.setText(displayedTileLetter[12]);
//
//                displayedTileLetter[13] = dice[13][new Random().nextInt(dice[13].length)];
//                mFourteenthLetterTextView.setText(displayedTileLetter[13]);
//
//                displayedTileLetter[14] = dice[14][new Random().nextInt(dice[14].length)];
//                mFifteenthLetterTextView.setText(displayedTileLetter[14]);
//
//                displayedTileLetter[15] = dice[15][new Random().nextInt(dice[15].length)];
//                mSixteenthLetterTextView.setText(displayedTileLetter[15]);

//                for (int i = 0; i < dice.length; i++)
//                    displayedTileLetter[i] = dice[i][new Random().nextInt(dice[0].length)];

//                  displayedTileLetter[0] = dieOne[new Random().nextInt(dieOne.length)];
//                  mFirstletterTextView.setText(displayedTileLetter[0]);
//
//                  displayedTileLetter[1] = dieTwo[new Random().nextInt(dieTwo.length)];
//                  mSecondletterTextView.setText(displayedTileLetter[1]);
//
//                  displayedTileLetter[2] = dieThree[new Random().nextInt(dieThree.length)];
//                  mThirdletterTextView.setText(displayedTileLetter[2]);
//
//                  displayedTileLetter[3] = dieFour[new Random().nextInt(dieFour.length)];
//                  mFourthletterTextView.setText(displayedTileLetter[3]);
//
//                  displayedTileLetter[4] = dieFive[new Random().nextInt(dieFive.length)];
//                  mFifthletterTextView.setText(displayedTileLetter[4]);
//
//                  displayedTileLetter[5] = dieSix[new Random().nextInt(dieSix.length)];
//                  mSixthletterTextView.setText(displayedTileLetter[5]);
//
//                  displayedTileLetter[6] = dieSeven[new Random().nextInt(dieSeven.length)];
//                  mSeventhletterTextView.setText(displayedTileLetter[6]);
//
//                  displayedTileLetter[7] = dieEight[new Random().nextInt(dieEight.length)];
//                  mEightletterTextView.setText(displayedTileLetter[7]);
//
//                  displayedTileLetter[8] = dieNine[new Random().nextInt(dieNine.length)];
//                  mNinthLetterTextView.setText(displayedTileLetter[8]);
//
//                  displayedTileLetter[9] = dieTen[new Random().nextInt(dieTen.length)];
//                  mTenthLetterTextView.setText(displayedTileLetter[9]);
//
//                  displayedTileLetter[10] = dieEleven[new Random().nextInt(dieEleven.length)];
//                  mEleventhLetterTextView.setText(displayedTileLetter[10]);
//
//                  displayedTileLetter[11] = dieTwelve[new Random().nextInt(dieTwelve.length)];
//                  mTwelfthLetterTextView.setText(displayedTileLetter[11]);
//
//                  displayedTileLetter[12] = dieThirteen[new Random().nextInt(dieThirteen.length)];
//                  mThirteenthLetterTextView.setText(displayedTileLetter[12]);
//
//                  displayedTileLetter[13] = dieFourteen[new Random().nextInt(dieFourteen.length)];
//                  mFourteenthLetterTextView.setText(displayedTileLetter[13]);
//
//                  displayedTileLetter[14] = dieFifteen[new Random().nextInt(dieFifteen.length)];
//                  mFifteenthLetterTextView.setText(displayedTileLetter[14]);
//
//                  displayedTileLetter[15] = dieSixteen[new Random().nextInt(dieSixteen.length)];
//                  mSixteenthLetterTextView.setText(displayedTileLetter[15]);

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



