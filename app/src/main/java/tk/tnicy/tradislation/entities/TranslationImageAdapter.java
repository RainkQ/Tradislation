package tk.tnicy.tradislation.entities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import tk.tnicy.tradislation.R;
import tk.tnicy.tradislation.utiles.HttpUtil;

import java.io.IOException;

public class TranslationImageAdapter extends RecyclerView.Adapter<TranslationImageAdapter.ImageViewHolder> {

    private static final String TAG = "TranslationImageAdapter";

    Translation translation;

    int img_count;

    public TranslationImageAdapter(Translation translation) {
        this.translation = translation;
        img_count = 0;
        //        创建时查询这个translation的图片数量
        HttpUtil.sendOkHttpRequest("http://101.132.120.236:9999/pic/search/" + translation.getChi(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 图片数量读取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                img_count = Integer.parseInt(response.body().string());
            }
        });
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_pic_page, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {


        Glide.with(imageViewHolder.itemView.getContext())
                .load("http://101.132.120.236:9999/img/" + translation.getChi() + (i + 1))
                .placeholder(R.drawable.loading_place_holder)
                .error(R.drawable.error_place_holder)
                .into(imageViewHolder.imageView);


    }


    @Override
    public int getItemCount() {
        return img_count;
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.item_pic);
        }

    }


}
