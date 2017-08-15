package care.work.careassignment.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import care.work.careassignment.Constant;
import care.work.careassignment.R;
import care.work.careassignment.databinding.ActivityRandomlettersBinding;

public class LetterFliper extends AppCompatActivity {

    private ActivityRandomlettersBinding binding;
    private int mPosition;
    private Timer mTimer;
    private char[] mCharList;
    private boolean isRunning;
    private boolean isStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomletters);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_randomletters);
          setTitle("Random Letter");
        if (getIntent().hasExtra(Constant.EXTRA_INTENT_POSITION)) {
            mPosition = getIntent().getIntExtra(Constant.EXTRA_INTENT_POSITION, -1);
        }

        if (getIntent().hasExtra(Constant.EXTRA_INTENT_CHAR_LIST)) {
            mCharList = getIntent().getCharArrayExtra(Constant.EXTRA_INTENT_CHAR_LIST);
            char clickedLetter = mCharList[mPosition];
            binding.displayLetter.setText(clickedLetter + "");
        }
        binding.displayLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCharFlip();
            }
        });
    }

    private void initCharFlip() {
        mTimer = new Timer();
        if (isRunning) {
            return;
        }
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                binding.displayLetter.post(new Runnable() {
                    @Override
                    public void run() {
                        int currentDsiplayPosition = new Random().nextInt(mCharList.length - 1);
                        binding.displayLetter.setText(mCharList[currentDsiplayPosition] + "");
                        isRunning = true;
                        isStarted = true;
                    }
                });
            }
        }, 0, 500);
    }


    @Override
    public void onBackPressed() {
        if (isStarted) {
            Intent intent = new Intent(this, LetterFliper.class);
            intent.putExtra(Constant.EXTRA_INTENT_POSITION, mPosition);
            intent.putExtra(Constant.EXTRA_INTENT_CHAR, binding.displayLetter.getText().toString());
            intent.putExtra(Constant.EXTRA_INTENT_CHAR_LIST, mCharList);
            setResult(RESULT_OK, intent);
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
        }
        super.onBackPressed();
    }
}
