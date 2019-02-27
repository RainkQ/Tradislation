package tk.tnicy.tradislation.entities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import tk.tnicy.tradislation.R;

public class TranslationImageAdapter extends RecyclerView.Adapter<TranslationImageAdapter.ImageViewHolder> {

    private static final String TAG = "TranslationImageAdapter";

    Translation translation;

    public TranslationImageAdapter(Translation translation) {
        this.translation = translation;
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
        return 2;
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.item_pic);
        }

    }


}
