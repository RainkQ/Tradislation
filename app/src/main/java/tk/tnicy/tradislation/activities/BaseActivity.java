package tk.tnicy.tradislation.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.jaeger.library.StatusBarUtil;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setTransparent(this);

    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }
}
