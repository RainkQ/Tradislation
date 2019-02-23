package tk.tnicy.tradislation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import org.litepal.LitePal;
import tk.tnicy.tradislation.R;
import tk.tnicy.tradislation.entities.BigType;
import tk.tnicy.tradislation.entities.SmallType;
import tk.tnicy.tradislation.entities.Translation;

import java.util.ArrayList;
import java.util.List;

public class TypeActivity extends Activity {


    private static final String TAG = "TypeActivity";

    EditText searchText;

    CardView searchCard;
    ListView typeListView;
    Intent lastIntent;
    private int currentLevel = 0;
    private List<String> dataList;
    private ArrayAdapter<String> adapter;


    List<SmallType> tmpSmallTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        init();

    }

    void init() {

        ///////点击搜索框跳转mainActivity
        searchText = findViewById(R.id.search_text);
        searchCard = findViewById(R.id.search_card);
        searchText.setFocusable(false);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeActivity.this, MainActivity.class);
                intent.putExtra("ifFocus", true);
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(TypeActivity.this,
                                searchCard, "searchCard");
                startActivity(intent, options.toBundle());
            }
        });
        /////////////////////


        typeListView = findViewById(R.id.TypeListView);
        lastIntent = getIntent();
        String bigType = lastIntent.getStringExtra("BigType");
        BigType tmpBigType = LitePal.where("typename is ?", bigType).find(BigType.class).get(0);

        tmpSmallTypes = LitePal.where("bigtypeid = ?", tmpBigType.getBigTypeId().toString()).find(SmallType.class);


        dataList = new ArrayList<>();


        for (SmallType s : tmpSmallTypes) {
            dataList.add(s.getTypeName());
        }


        //init listView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        typeListView.setAdapter(adapter);


        //添加onclick
        typeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == 0) {
                    List<Translation> tmpTranslations = LitePal.where("smalltypeid = ?", LitePal.where("typename is ?", dataList.get(position)).find(SmallType.class).get(0).getSmallTypeId().toString()).find(Translation.class);


                    dataList.clear();
                    for (Translation t : tmpTranslations) {
                        dataList.add(t.getChi());
                    }

                    adapter.notifyDataSetChanged();
                    currentLevel = 1;
                } else {
                    Intent intent = new Intent(TypeActivity.this, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("translation", LitePal.where("chi is ?", dataList.get(position)).find(Translation.class).get(0));

                    intent.putExtras(bundle);

                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(TypeActivity.this,
                                    view, "bottom");


                    startActivity(intent, options.toBundle());
                }


            }
        });
    }


    @Override
    public void onBackPressed() {
        if (currentLevel == 0) {
            finishAfterTransition();
        }else {
            dataList.clear();
            for (SmallType s : tmpSmallTypes) {
                dataList.add(s.getTypeName());
            }
            adapter.notifyDataSetChanged();
            currentLevel = 0;
        }
    }
}
