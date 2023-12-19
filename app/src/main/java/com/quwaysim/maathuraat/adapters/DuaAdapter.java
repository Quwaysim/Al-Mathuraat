package com.quwaysim.maathuraat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quwaysim.maathuraat.R;

public class DuaAdapter extends RecyclerView.Adapter<DuaAdapter.DuaViewHolder> {
    private final int mLayout;
    private final String[] mDuaList;
    private final String[] mTransliteration;
    private final String[] mReferences;
    private final Context mContext;
    private final int mSize;

    public DuaAdapter(Context context, String[] dualist, String[] transliteration, String[] references, int layout, int size) {
        mContext = context;
        mDuaList = dualist;
        mTransliteration = transliteration;
        mReferences = references;
        mSize = size;
        mLayout = layout;
    }


    @NonNull
    @Override
    public DuaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayout, parent, false);
        return new DuaViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull DuaViewHolder holder, int position) {
        String dua = mDuaList[position];
        if (mReferences != null) {
            String reference = mReferences[position];
            holder.referencesTextView.setText(reference);
            holder.referencesTextView.setTextSize(mSize - 10);
            if (position == 0) {
                //TODO: not working - changes on RecyclerView scroll
                holder.referencesTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
        holder.duaTextView.setText(dua);
        holder.duaTextView.setTextSize(mSize);

        if (mTransliteration != null) {
            holder.transliterationTextView.setVisibility(View.VISIBLE);
            String trans = mTransliteration[position];
            holder.transliterationTextView.setText(trans);
            holder.transliterationTextView.setTextSize(mSize - 6);
        }
//        if (holder.counter != null)
//            if (position == 0 || position == 8 || position == 22 || position == 24 || position == 26) {
//
//                holder.counter.setVisibility(View.VISIBLE);
//            }
    }

    @Override
    public int getItemCount() {
        return mDuaList.length;
    }


    static class DuaViewHolder extends RecyclerView.ViewHolder {

        public final TextView duaTextView;
        public final TextView transliterationTextView;
        public final TextView referencesTextView;
        public final TextView counter;
        final DuaAdapter mDuaAdapter;

        public DuaViewHolder(@NonNull View itemView, DuaAdapter duaAdapter) {
            super(itemView);
            mDuaAdapter = duaAdapter;
            duaTextView = itemView.findViewById(R.id.dua_text);
            transliterationTextView = itemView.findViewById(R.id.transliteration);
            referencesTextView = itemView.findViewById(R.id.textview_references);
            counter = itemView.findViewById(R.id.count_textview);
        }
    }
}
