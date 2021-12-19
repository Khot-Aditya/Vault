package com.app.vault.adapter.documents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.vault.R;
import com.app.vault.activity.DocumentsActivity;
import com.app.vault.model.DocumentsModel;
import com.app.vault.sqlite.documents.DocumentsDatabaseHelper;
import com.app.vault.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class DocumentsRecyclerAdapter extends RecyclerView.Adapter<DocumentsRecyclerAdapter.ListBasicViewHolder> {

    private final List<DocumentsModel> objectList;
    private final Context context;

    public DocumentsRecyclerAdapter(List<DocumentsModel> objectList, Context context) {
        this.objectList = objectList;
        this.context = context;
    }

    @NonNull
    @Override
    public DocumentsRecyclerAdapter.ListBasicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_documents, parent, false);
        return new DocumentsRecyclerAdapter.ListBasicViewHolder(itemView);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull DocumentsRecyclerAdapter.ListBasicViewHolder holder, int position) {
        final DocumentsModel object = objectList.get(position);

        holder.txt_index.setText(String.valueOf(position + 1));
        holder.txt_title.setText(object.getTitle());
        holder.txt_documentContent.setText(object.getDocumentContent());
        holder.img_Star.setVisibility((object.isImportant() ? View.VISIBLE : View.GONE));


        holder.ic_more_options.setVisibility(View.INVISIBLE);

        holder.itemView.setOnClickListener(view -> {

            View view1 = View.inflate(context, R.layout.bottom_sheet_options_documents, null);

            BottomSheetDialog dialog = new BottomSheetDialog(context);

            dialog.setContentView(view1);
            dialog.show();

            LinearLayout menu_ViewDocuments = (LinearLayout) dialog.findViewById(R.id.menu_ViewDocument);
            LinearLayout menu_EditDocument = (LinearLayout) dialog.findViewById(R.id.menu_EditDocument);
            LinearLayout menu_CopyDocument = (LinearLayout) dialog.findViewById(R.id.menu_CopyDocument);
            LinearLayout menu_DuplicateDocument = (LinearLayout) dialog.findViewById(R.id.menu_DuplicateDocument);
            LinearLayout menu_ShareDocument = (LinearLayout) dialog.findViewById(R.id.menu_ShareDocument);
            LinearLayout menu_MarkAsImportantDocument = (LinearLayout) dialog.findViewById(R.id.menu_MarkAsImportantDocument);
            LinearLayout menu_HideDocument = (LinearLayout) dialog.findViewById(R.id.menu_HideDocument);
            LinearLayout menu_DeleteDocument = (LinearLayout) dialog.findViewById(R.id.menu_DeleteDocument);

            TextView menu_txt_MarkAsImportantDocument = (TextView) dialog.findViewById(R.id.menu_txt_MarkAsImportantDocument);
            TextView menu_txt_HideDocument = (TextView) dialog.findViewById(R.id.menu_txt_HideDocument);
            ImageView menu_img_HideDocument = (ImageView) dialog.findViewById(R.id.menu_img_HideDocument);

            if (object.isImportant()) {
                assert menu_txt_MarkAsImportantDocument != null;
                menu_txt_MarkAsImportantDocument.setText(R.string.mark_as_not_important);
            }

            if (object.isHidden()) {
                assert menu_txt_HideDocument != null;
                menu_txt_HideDocument.setText(R.string.remove_from_hidden);
                assert menu_img_HideDocument != null;
                menu_img_HideDocument.setImageResource(R.drawable.ic_sign_out);
            }


            assert menu_ViewDocuments != null;
            menu_ViewDocuments.setOnClickListener(v -> {
                Toast.makeText(context, "View", Toast.LENGTH_SHORT).show();
            });

            assert menu_EditDocument != null;
            menu_EditDocument.setOnClickListener(v -> {
                Intent intent = new Intent(context, DocumentsActivity.class);

                intent.putExtra(Utils.ACTIVITY_ACTION, Utils.ACTION_EDIT);
                intent.putExtra(Utils.COL_DOCUMENTS_ID, object.getId());
                intent.putExtra(Utils.COL_DOCUMENTS_TITLE, object.getTitle());
                intent.putExtra(Utils.COL_DOCUMENTS_DATE, object.getDate());
                intent.putExtra(Utils.COL_DOCUMENTS_FILES,object.getFiles());
                intent.putExtra(Utils.COL_DOCUMENTS_TAG, object.getTag());
                intent.putExtra(Utils.COL_DOCUMENTS_IS_IMPORTANT, object.isImportant());
                intent.putExtra(Utils.COL_DOCUMENTS_IS_HIDDEN, object.isHidden());

                context.startActivity(intent);
                dialog.dismiss();
            });

            assert menu_CopyDocument != null;
            menu_CopyDocument.setOnClickListener(v -> {
                Toast.makeText(context, "View", Toast.LENGTH_SHORT).show();
            });

            assert menu_DuplicateDocument != null;
            menu_DuplicateDocument.setOnClickListener(v -> {
                Toast.makeText(context, "View", Toast.LENGTH_SHORT).show();
            });

            assert menu_ShareDocument != null;
            menu_ShareDocument.setOnClickListener(v -> {
                Toast.makeText(context, "View", Toast.LENGTH_SHORT).show();
            });

            assert menu_MarkAsImportantDocument != null;
            menu_MarkAsImportantDocument.setOnClickListener(v -> {
                if (object.isImportant()) {
                    new DocumentsDatabaseHelper(context).setDocumentImportance(object, false);
                    objectList.get(holder.getAdapterPosition()).setImportant(false);
                } else {
                    new DocumentsDatabaseHelper(context).setDocumentImportance(object, true);
                    objectList.get(holder.getAdapterPosition()).setImportant(true);
                }

                notifyItemChanged(holder.getAdapterPosition());
                dialog.dismiss();
            });

            assert menu_HideDocument != null;
            menu_HideDocument.setOnClickListener(v -> {
                Toast.makeText(context, "View", Toast.LENGTH_SHORT).show();
            });

            assert menu_DeleteDocument != null;
            menu_DeleteDocument.setOnClickListener(v -> {
                if (!new DocumentsDatabaseHelper(context).deleteDocument(object.getId())) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

                objectList.remove(object);
                notifyItemRemoved(holder.getAdapterPosition());
                dialog.dismiss();
            });


//            assert menu_CopyPassword != null;
//            menu_CopyPassword.setOnClickListener(v -> {
//                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clip = ClipData.newPlainText("label", object.getPassword());
//                if (clipboard == null || clip == null) return;
//                clipboard.setPrimaryClip(clip);
//
//                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            });

//            assert menu_Edit != null;
//            menu_Edit.setOnClickListener(v -> {
//                Intent intent = new Intent(context, PasswordsActivity.class);
//
//                intent.putExtra(Utils.ACTIVITY_ACTION, Utils.EDIT);
//                intent.putExtra(Utils.COL_PASSWORDS_ID, object.getId());
//                intent.putExtra(Utils.COL_PASSWORDS_TITLE, object.getTitle());
//                intent.putExtra(Utils.COL_PASSWORDS_DATE, object.getDate());
//                intent.putExtra(Utils.COL_PASSWORDS_WEBSITE_NAME, object.getWebsiteName());
//                intent.putExtra(Utils.COL_PASSWORDS_USER_NAME, object.getUserName());
//                intent.putExtra(Utils.COL_PASSWORDS_EMAIL_ADDRESS, object.getEmail());
//                intent.putExtra(Utils.COL_PASSWORDS_PHONE_NUMBER, object.getPhoneNumber());
//                intent.putExtra(Utils.COL_PASSWORDS_PASSWORD, object.getPassword());
//                intent.putExtra(Utils.COL_PASSWORDS_TAG, object.getTag());
//                intent.putExtra(Utils.COL_PASSWORDS_IS_IMPORTANT, object.isImportant());
//                intent.putExtra(Utils.COL_PASSWORDS_IS_HIDDEN, object.isHidden());
//
//                context.startActivity(intent);
//                dialog.dismiss();
//            });
//
//            assert menu_Important != null;
//            menu_Important.setOnClickListener(v -> {
//
//                if (object.isImportant()) {
//                    new PasswordsDatabaseHelper(context).setAccountImportance(object, false);
//                    objectList.get(holder.getAdapterPosition()).setImportant(false);
//                } else {
//                    new PasswordsDatabaseHelper(context).setAccountImportance(object, true);
//                    objectList.get(holder.getAdapterPosition()).setImportant(true);
//                }
//
//                notifyItemChanged(holder.getAdapterPosition());
//                dialog.dismiss();
//            });
//
//            assert menu_AccountVisibility != null;
//            menu_AccountVisibility.setOnClickListener(v -> {
//
//                new PasswordsDatabaseHelper(context).setAccountVisibility(object, !object.isHidden());
//
//                objectList.remove(object);
//
//                notifyItemRemoved(holder.getAdapterPosition());
//                dialog.dismiss();
//            });
//
//            assert menu_Delete != null;
//            menu_Delete.setOnClickListener(v -> {
//
//                if (!new PasswordsDatabaseHelper(context).deletePassword(object.getId())) {
//                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
//                }
//
//                objectList.remove(object);
//                notifyItemRemoved(holder.getAdapterPosition());
//                dialog.dismiss();
//            });

        });

    }


    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public static class ListBasicViewHolder extends RecyclerView.ViewHolder {

        private final TextView txt_title;
        private final TextView txt_index;
        private final TextView txt_documentContent;
        private final ImageView ic_more_options;
        private final ImageView img_Star;

        public ListBasicViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_index = itemView.findViewById(R.id.txt_index2);
            txt_title = itemView.findViewById(R.id.txt_title2);
            txt_documentContent = itemView.findViewById(R.id.txt_DocumentCount);
            ic_more_options = itemView.findViewById(R.id.ic_more_options2);
            img_Star = itemView.findViewById(R.id.img_Star2);
        }
    }
}