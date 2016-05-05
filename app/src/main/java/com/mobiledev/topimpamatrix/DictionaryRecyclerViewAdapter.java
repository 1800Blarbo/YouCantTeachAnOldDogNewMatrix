//package com.mobiledev.topimpamatrix;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//
//import java.util.ArrayList;
//
///**
// * Created by larspmayrand on 5/4/16.
// */
//public class DictionaryRecyclerViewAdapter extends RecyclerView.Adapter<DictionaryRecyclerViewAdapter.DefinitionViewHolder> {
//
//    private final ArrayList<Definition> Definitions;
//
//    private final OnDefinitionRowClickListener Listener;
//
//    public DictionaryRecyclerViewAdapter(ArrayList<Definition> definitions, OnDefinitionRowClickListener listener) {
//        Definitions = definitions;
//        Listener = listener;
//    }
//
//    public interface OnDefinitionRowClickListener {
//        void onDefinitionRowClick(Definition definition);
//    }
//
//    @Override
//    public DefinitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View row = inflater.inflate(R.layout.dictionary_activity, parent, false);
//        return new DefinitionViewHolder(row);
//    }
//
//    @Override
//    public void onBindViewHolder(final DefinitionViewHolder holder, int position) {
//        Definition definition = Definitions.get(position);
//        WebSettings webSettings = holder.definition.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        String js = "\\bo{ " + definition.getTerm() + "}: " + FormatHelper.makeLatexString(definition.getDefinition());
//        holder.definition.loadDataWithBaseURL("file:///android_asset/", js, "text/html", "UTF-8", null);
//
////        holder.mItemView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                mListener.onDetailRowClick(detail);
//////                MyViewHolder holder = (MyViewHolder) v.getTag();
//////                boolean result = holder.expandableLayout.toggleExpansion();
//////                Item item = mItems.get(holder.getAdapterPosition());
//////                item.isExpand = result ? !item.isExpand : item.isExpand;
////            }
////        });
////
////        holder.fullView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (Listener != null) {
////                    Listener.onDefinitionRowClick(Definitions.get(holder.getAdapterPosition()));
////                }
////            }
////        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return Definitions.size();
//    }
//
//    static class DefinitionViewHolder extends RecyclerView.ViewHolder {
//
//        WebView definition;
//        View fullView;
//
//        public DefinitionViewHolder(View itemView) {
//            super(itemView);
//            fullView = itemView;
//            definition = (WebView) itemView.findViewById(R.id.definition_row_webview);
//        }
//    }
//
//}
