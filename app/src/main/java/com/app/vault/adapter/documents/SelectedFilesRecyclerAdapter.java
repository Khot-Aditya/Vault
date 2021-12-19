package com.app.vault.adapter.documents;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.vault.R;
import com.app.vault.model.SelectedFilesModel;

import java.util.List;

public class SelectedFilesRecyclerAdapter extends RecyclerView.Adapter<SelectedFilesRecyclerAdapter.ListBasicViewHolder> {

    private final List<SelectedFilesModel> objectList;
    private final Context context;

    public SelectedFilesRecyclerAdapter(List<SelectedFilesModel> objectList, Context context) {
        this.objectList = objectList;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectedFilesRecyclerAdapter.ListBasicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_files, parent, false);
        return new SelectedFilesRecyclerAdapter.ListBasicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedFilesRecyclerAdapter.ListBasicViewHolder holder, int position) {
        final SelectedFilesModel object = objectList.get(position);

        holder.txt_FileName.setText(object.getFileName());
        holder.txt_Filetype.setText(object.getFileType());
        holder.txt_FileSize.setText(Formatter.formatShortFileSize(context, Integer.parseInt(object.getFileSize())));

        holder.img_RemoveFile.setOnClickListener(v -> {
            objectList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });

    }


    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public static class ListBasicViewHolder extends RecyclerView.ViewHolder {

        private final TextView txt_FileName;
        private final TextView txt_Filetype;
        private final TextView txt_FileSize;
        private final ImageView img_RemoveFile;

        public ListBasicViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_FileName = itemView.findViewById(R.id.txt_FileName);
            txt_Filetype = itemView.findViewById(R.id.txt_FileType);
            txt_FileSize = itemView.findViewById(R.id.txt_FileSize);
            img_RemoveFile = itemView.findViewById(R.id.img_RemoveFile);

        }
    }
}
