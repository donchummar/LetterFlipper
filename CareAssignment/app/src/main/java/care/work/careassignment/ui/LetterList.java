package care.work.careassignment.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import java.util.Hashtable;

import care.work.careassignment.Constant;
import care.work.careassignment.R;
import care.work.careassignment.adapter.ChatListAdapter;
import care.work.careassignment.databinding.ActivityMainBinding;
import care.work.careassignment.interfaces.ChatListConnector;

public class LetterList extends AppCompatActivity implements ChatListConnector {

    private char[] mAlphabetList = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private ActivityMainBinding mBinding;
    private ChatListAdapter mListAdapter;
    private Hashtable<Character, Integer> mCharTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setTitle("Letter List");
        mBinding.charListView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mListAdapter = new ChatListAdapter(this, mAlphabetList);
        mBinding.charListView.setAdapter(mListAdapter);
        mBinding.charListView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        initCharTable();
    }

    private void initCharTable() {
        mCharTable = new Hashtable<>();
        for (int i = 0; i < mAlphabetList.length; i++){
            mCharTable.put(mAlphabetList[i], i);
        }
    }

    @Override
    public void onListItemClicked(int position, char character) {
        Intent intent = new Intent(this, LetterFliper.class);
        intent.putExtra(Constant.EXTRA_INTENT_POSITION, position);
        intent.putExtra(Constant.EXTRA_INTENT_CHAR, character);
        intent.putExtra(Constant.EXTRA_INTENT_CHAR_LIST, mAlphabetList);
        startActivityForResult(intent, Constant.REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE && resultCode == RESULT_OK){
            int clikedPosition = -1;
            int lastDisplayCharPosition = -1;
            String lastChar = "";
            if (data.hasExtra(Constant.EXTRA_INTENT_POSITION)) {
                clikedPosition = data.getIntExtra(Constant.EXTRA_INTENT_POSITION, -1);
            }
            if (data.hasExtra(Constant.EXTRA_INTENT_CHAR)) {
                lastChar = data.getStringExtra(Constant.EXTRA_INTENT_CHAR);
            }
            lastDisplayCharPosition = mCharTable.get(lastChar.charAt(0));
            mCharTable.put(lastChar.charAt(0), clikedPosition);
            mCharTable.put(mAlphabetList[clikedPosition], lastDisplayCharPosition);
            char switchChar = mAlphabetList[clikedPosition];
            mAlphabetList[clikedPosition] = mAlphabetList[lastDisplayCharPosition];
            mAlphabetList[lastDisplayCharPosition] = switchChar;
            mListAdapter.notifyItemChanged(clikedPosition);
            mListAdapter.notifyItemChanged(lastDisplayCharPosition);
        }
    }
}
