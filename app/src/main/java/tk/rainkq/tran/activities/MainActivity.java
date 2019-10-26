package tk.rainkq.tran.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.dyhdyh.widget.loadingbar2.LoadingBar;
import tk.rainkq.tran.R;
import tk.rainkq.tran.entities.BigType;
import tk.rainkq.tran.entities.SmallType;
import tk.rainkq.tran.entities.Translation;
import tk.rainkq.tran.entities.TranslationRecyclerViewAdapter;
import tk.rainkq.tran.utiles.Utility;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private List<Translation> translations = new ArrayList<>();
    private List<Translation> translations_searched = new ArrayList<>();
    List<BigType> bigTypes;
    List<SmallType> smallTypes;
    private EditText editText;
    public TranslationRecyclerViewAdapter adapter;


//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_qin:
//                    mTextMessage.setText(R.string.title_qin);
//                    return true;
//                case R.id.navigation_qi:
//                    mTextMessage.setText(R.string.title_qi);
//                    return true;
//                case R.id.navigation_all:
//                    mTextMessage.setText(R.string.title_all);
//                    return true;
//                case R.id.navigation_shu:
//                    mTextMessage.setText(R.string.title_shu);
//                    return true;
//                case R.id.navigation_hua:
//                    mTextMessage.setText(R.string.title_hua);
//                    return true;
//            }
//            return false;
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ////////启动页////////

        translations = Utility.queryAllTranslations(MainActivity.this);
        Utility.queryAllBigTypes(this);
        Utility.queryAllSmallTypes(this);
        translations_searched = new ArrayList<>();
        translations_searched.addAll(translations);


        ////////启动结束//////

        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // 开始设置view

        editText = findViewById(R.id.search_text);

        Button bottom_button_qin = findViewById(R.id.btm_button_qin);
        Button bottom_button_qi = findViewById(R.id.btm_button_qi);
        Button bottom_button_shu = findViewById(R.id.btm_button_shu);
        Button bottom_button_hua = findViewById(R.id.btm_button_hua);

        adapter = new TranslationRecyclerViewAdapter(translations);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        editText = findViewById(R.id.editText);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);







        addListeners(bottom_button_qin, "琴");
        addListeners(bottom_button_qi, "棋");
        addListeners(bottom_button_shu, "书");
        addListeners(bottom_button_hua, "画");


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search();
            }
        });


        Intent lastIntent = getIntent();
        boolean ifFocus = lastIntent.getBooleanExtra("ifFocus", false);
        if (ifFocus) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            //显示软键盘
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        }


        /////////////////end///////////////
    }




    private void addListeners(final Button button, final String name) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TypeActivity.class);
//                ActivityOptionsCompat options = ActivityOptionsCompat
//                        .makeSceneTransitionAnimation(MainActivity.this,
//                                button, "bottom");
//

//                startActivity(intent, options.toBundle());
                intent.putExtra("BigType", name);
                startActivity(intent);
            }
        });
    }


    private void search() {
        String data = editText.getText().toString().trim();
        if (!translations.isEmpty()) {
            // 清除原数据
            translations.clear();
        }
        // 对副List数据进行遍历匹配关键词
        for (Translation translation : translations_searched) {
            if ((translation.getChi() + translation.getEng() + translation.getBigType() + translation.getSmallType() + translation.getDetail() + translation.getSpelling()).contains(data)) {
                translations.add(translation);
            }
        }
        adapter.notifyDataSetChanged();
    }


}
