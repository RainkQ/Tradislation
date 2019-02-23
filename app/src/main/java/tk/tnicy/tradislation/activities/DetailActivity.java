package tk.tnicy.tradislation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import tk.tnicy.tradislation.R;
import tk.tnicy.tradislation.entities.Translation;
import tk.tnicy.tradislation.entities.TranslationImageAdapter;
import tk.tnicy.tradislation.utiles.CardTransformer;
import tk.tnicy.tradislation.utiles.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends Activity {

    private static final String TAG = "DetailActivity";

    EditText searchText;

    CardView searchCard;
    Intent lastIntent;

    TextView itemChi;
    TextView itemEng;
    TextView itemDetail;

    Button backButton;

    ViewPager viewPager;

    TextView bigTypeBack;

    private List<ImageView> imageViewList;



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
        searchText.setFocusable(false);
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
        itemEng = findViewById(R.id.item_eng);
        backButton = findViewById(R.id.back_button);
        bigTypeBack = findViewById(R.id.item_bigtype_back);
        itemDetail = findViewById(R.id.item_detail);

        itemChi.setText(translation.getChi());
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
        discreteScrollView.setAdapter(new TranslationImageAdapter(translation));


    }










}
