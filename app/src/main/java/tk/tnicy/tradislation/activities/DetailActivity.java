package tk.tnicy.tradislation.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import tk.tnicy.tradislation.R;
import tk.tnicy.tradislation.entities.Translation;
import tk.tnicy.tradislation.entities.TranslationImageAdapter;

public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";

    EditText searchText;

    CardView searchCard;
    Intent lastIntent;

    TextView itemChi;
    TextView itemEng;
    TextView itemDetail;
    TextView spelling;

    Button backButton;


    TextView bigTypeBack;


    DiscreteScrollView discreteScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        init();
    }

    public void init() {

        ////////////跳转main//////////////////
        searchText = findViewById(R.id.search_text);
        searchCard = findViewById(R.id.search_card);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                intent.putExtra("ifFocus", true);
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(DetailActivity.this,
                                searchCard, "searchCard");
                startActivity(intent, options.toBundle());
            }
        });
        //////////////////////////////////////


        lastIntent = getIntent();
        Translation translation = (Translation) lastIntent.getSerializableExtra("translation");
        itemChi = findViewById(R.id.item_chi);
        itemChi.setTypeface(Typeface.createFromAsset(getAssets(),"kaishu1.ttf"));
        itemEng = findViewById(R.id.item_eng);
        backButton = findViewById(R.id.back_button);
        bigTypeBack = findViewById(R.id.item_bigtype_back);
        bigTypeBack.setTypeface(Typeface.createFromAsset(getAssets(),"kaishu1.ttf"));
        itemDetail = findViewById(R.id.item_detail);
        spelling = findViewById(R.id.spelling);

        itemChi.setText(translation.getChi());
        itemChi.setTypeface(Typeface.createFromAsset(getAssets(),"kaishu1.ttf"));
        spelling.setText(translation.getSpelling());
        itemEng.setText(translation.getEng());
        bigTypeBack.setText(translation.getBigType());
        itemDetail.setText(translation.getDetail());
        itemChi.setTypeface(Typeface.createFromAsset(getAssets(),"kaishu1.ttf"));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

        discreteScrollView = findViewById(R.id.item_pic_Pager);
        discreteScrollView.setAdapter(new TranslationImageAdapter(translation));

    }


}
