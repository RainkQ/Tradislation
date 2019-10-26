package tk.rainkq.tran.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.jaeger.library.StatusBarUtil;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        StatusBarUtil.setTransparent(this);
        
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }
}
