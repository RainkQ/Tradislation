package tk.tnicy.tradislation.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import tk.tnicy.tradislation.R;
import tk.tnicy.tradislation.entities.BigType;
import tk.tnicy.tradislation.entities.SmallType;
import tk.tnicy.tradislation.entities.Translation;
import tk.tnicy.tradislation.entities.TranslationRecyclerViewAdapter;
import tk.tnicy.tradislation.utiles.Utility;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    static private Boolean createOrNot = true;
    List<Translation> translations;
    List<Translation> translations_searched;
    List<BigType> bigTypes;
    List<SmallType> smallTypes;
    EditText editText;
    Button bottom_button_qin;
    Button bottom_button_qi;
    Button bottom_button_shu;
    Button bottom_button_hua;
    RecyclerView recyclerView;
    TranslationRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if (createOrNot) {
            createOrNot = false;
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
            onCreate(null);
        }
    }

    void init() {

        /////////////////////////////////////
        translations = Utility.queryAllTranslations(this);
        Utility.queryAllBigTypes(this);
        Utility.queryAllSmallTypes(this);
        /////////////////////////////////////


        translations_searched = new ArrayList<>();
        translations_searched.addAll(translations);


        editText = findViewById(R.id.search_text);

        bottom_button_qin = findViewById(R.id.bottom_button_qin);
        bottom_button_qin.setTypeface(Typeface.createFromAsset(getAssets(), "kaishu1.ttf"));
        bottom_button_qi = findViewById(R.id.bottom_button_qi);
        bottom_button_qi.setTypeface(Typeface.createFromAsset(getAssets(), "kaishu1.ttf"));
        bottom_button_shu = findViewById(R.id.bottom_button_shu);
        bottom_button_shu.setTypeface(Typeface.createFromAsset(getAssets(), "kaishu1.ttf"));
        bottom_button_hua = findViewById(R.id.bottom_button_hua);
        bottom_button_hua.setTypeface(Typeface.createFromAsset(getAssets(), "kaishu1.ttf"));

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TranslationRecyclerViewAdapter(translations);
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


    }


    void addListeners(final Button button, final String name) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TypeActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(MainActivity.this,
                                button, "bottom");

                intent.putExtra("BigType", name);

                startActivity(intent, options.toBundle());
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


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: touched");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        return;
    }
}
