package tk.rainkq.tran.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.dyhdyh.widget.loadingbar2.LoadingBar;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import tk.rainkq.tran.R;
import tk.rainkq.tran.entities.Translation;
import tk.rainkq.tran.entities.TranslationImageAdapter;

public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";

    private EditText searchText;

    private CardView searchCard;
    private Intent lastIntent;

    private TextView itemChi;
    private TextView itemEng;
    private TextView itemDetail;
    private TextView spelling;

    private Button backButton;


    private TextView bigTypeBack;


    private DiscreteScrollView discreteScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        init();
    }

    private void init() {

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
//                startActivity(intent, options.toBundle());
                startActivity(intent);
            }
        });
        //////////////////////////////////////


        lastIntent = getIntent();
        Translation translation = (Translation) lastIntent.getSerializableExtra("translation");
        itemChi = findViewById(R.id.item_chi);
        itemEng = findViewById(R.id.item_eng);
        backButton = findViewById(R.id.back_button);
        bigTypeBack = findViewById(R.id.item_bigtype_back);
        itemDetail = findViewById(R.id.item_detail);
        spelling = findViewById(R.id.spelling);

        itemChi.setText(translation.getChi());
        spelling.setText(translation.getSpelling());
        itemEng.setText(translation.getEng());
        bigTypeBack.setText(translation.getBigType());
        itemDetail.setText(translation.getDetail());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

        discreteScrollView = findViewById(R.id.item_pic_Pager);
        LoadingBar.view(discreteScrollView).show();
        discreteScrollView.setAdapter(new TranslationImageAdapter(translation, this,discreteScrollView));

    }


}
