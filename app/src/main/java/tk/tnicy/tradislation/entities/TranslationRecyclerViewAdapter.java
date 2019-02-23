package tk.tnicy.tradislation.entities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import tk.tnicy.tradislation.R;
import tk.tnicy.tradislation.activities.DetailActivity;

import java.util.List;


public class TranslationRecyclerViewAdapter extends RecyclerView.Adapter<TranslationRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Translation> mTranslationList;

    public TranslationRecyclerViewAdapter(List<Translation> translationList) {
        mTranslationList = translationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.translationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Translation translation = mTranslationList.get(position);
                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("translation", translation);
                intent.putExtras(bundle);


                Pair p1 = new Pair<>(holder.translationView, "bottom");
                Pair p2 = new Pair<>(holder.translation_chi, "detailChi");
                Pair p3 = new Pair<>(holder.translation_eng, "detailEng");

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, p1,p2,p3);

                mContext.startActivity(intent, options.toBundle());


            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Translation translation = mTranslationList.get(position);
        viewHolder.translation_chi.setText(translation.getChi());
        viewHolder.translation_eng.setText(translation.getEng());

    }

    @Override
    public int getItemCount() {
        return mTranslationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View translationView;
        TextView translation_chi;
        TextView translation_eng;

        public ViewHolder(View view) {
            super(view);
            translationView = view;
            translation_chi = view.findViewById(R.id.translation_item_chi);
            translation_eng = view.findViewById(R.id.translation_item_eng);
        }


    }


}


