package com.app.vault.adapter.passwords;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
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

import com.app.vault.activity.PasswordsActivity;
import com.app.vault.R;
import com.app.vault.model.PasswordModel;
import com.app.vault.utils.Utils;
import com.app.vault.sqlite.passwords.PasswordsDatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PasswordRecyclerAdapter extends RecyclerView.Adapter<PasswordRecyclerAdapter.ListBasicViewHolder> {

    private final List<PasswordModel> objectList;
    private final Context context;

    public PasswordRecyclerAdapter(List<PasswordModel> objectList, Context context) {
        this.objectList = objectList;
        this.context = context;
    }

    @NonNull
    @Override
    public PasswordRecyclerAdapter.ListBasicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_passwords, parent, false);
        return new ListBasicViewHolder(itemView);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull PasswordRecyclerAdapter.ListBasicViewHolder holder, int position) {
        final PasswordModel object = objectList.get(position);

        holder.txt_index.setText(String.valueOf(position + 1));
        holder.txt_title.setText(object.getTitle());
        holder.txt_email.setText(object.getEmail());

        try {
            if (Objects.requireNonNull(new SimpleDateFormat("yyyy-MM-dd").parse(object.getDate())).before(new Date())) {
                //expired
            } else {
                //not expired
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.img_Star.setVisibility((object.isImportant() ? View.VISIBLE : View.GONE));

        holder.ic_more_options.setVisibility(View.INVISIBLE);

        holder.itemView.setOnClickListener(view -> {

            View view1 = View.inflate(context, R.layout.bottom_sheet_options_passwords, null);

            BottomSheetDialog dialog = new BottomSheetDialog(context);

            dialog.setContentView(view1);
            dialog.show();

            LinearLayout menu_CopyPassword = (LinearLayout) dialog.findViewById(R.id.menu_copy);
            LinearLayout menu_Edit = (LinearLayout) dialog.findViewById(R.id.menu_edit);
            LinearLayout menu_Important = (LinearLayout) dialog.findViewById(R.id.menu_star);
            ImageView menu_img_Important = (ImageView) dialog.findViewById(R.id.menu_img_MarkAsImportant);
            TextView menu_txt_Important = (TextView) dialog.findViewById(R.id.menu_txt_MarkAsImportant);
            LinearLayout menu_AccountVisibility = (LinearLayout) dialog.findViewById(R.id.menu_AccountVisibility);
            ImageView menu_img_HideAccount = (ImageView) dialog.findViewById(R.id.menu_img_HideAccount);
            TextView menu_txt_HideAccount = (TextView) dialog.findViewById(R.id.menu_txt_HideAccount);
            LinearLayout menu_Delete = (LinearLayout) dialog.findViewById(R.id.menu_delete);

            if (object.isImportant()) {
                assert menu_txt_Important != null;
                menu_txt_Important.setText(R.string.mark_as_not_important);
            }

            if (object.isHidden()) {
                assert menu_txt_HideAccount != null;
                menu_txt_HideAccount.setText(R.string.remove_from_hidden);
                assert menu_img_HideAccount != null;
                menu_img_HideAccount.setImageResource(R.drawable.ic_sign_out);
            }

            assert menu_CopyPassword != null;
            menu_CopyPassword.setOnClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", object.getPassword());
                if (clipboard == null || clip == null) return;
                clipboard.setPrimaryClip(clip);

                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });

            assert menu_Edit != null;
            menu_Edit.setOnClickListener(v -> {
                Intent intent = new Intent(context, PasswordsActivity.class);

                intent.putExtra(Utils.ACTIVITY_ACTION, Utils.ACTION_EDIT);
                intent.putExtra(Utils.COL_PASSWORDS_ID, object.getId());
                intent.putExtra(Utils.COL_PASSWORDS_TITLE, object.getTitle());
                intent.putExtra(Utils.COL_PASSWORDS_DATE, object.getDate());
                intent.putExtra(Utils.COL_PASSWORDS_WEBSITE_NAME, object.getWebsiteName());
                intent.putExtra(Utils.COL_PASSWORDS_USER_NAME, object.getUserName());
                intent.putExtra(Utils.COL_PASSWORDS_EMAIL_ADDRESS, object.getEmail());
                intent.putExtra(Utils.COL_PASSWORDS_PHONE_NUMBER, object.getPhoneNumber());
                intent.putExtra(Utils.COL_PASSWORDS_PASSWORD, object.getPassword());
                intent.putExtra(Utils.COL_PASSWORDS_TAG, object.getTag());
                intent.putExtra(Utils.COL_PASSWORDS_IS_IMPORTANT, object.isImportant());
                intent.putExtra(Utils.COL_PASSWORDS_IS_HIDDEN, object.isHidden());

                context.startActivity(intent);
                dialog.dismiss();
            });

            assert menu_Important != null;
            menu_Important.setOnClickListener(v -> {

                if (object.isImportant()) {
                    new PasswordsDatabaseHelper(context).setAccountImportance(object, false);
                    objectList.get(holder.getAdapterPosition()).setImportant(false);
                } else {
                    new PasswordsDatabaseHelper(context).setAccountImportance(object, true);
                    objectList.get(holder.getAdapterPosition()).setImportant(true);
                }

                notifyItemChanged(holder.getAdapterPosition());
                dialog.dismiss();
            });

            assert menu_AccountVisibility != null;
            menu_AccountVisibility.setOnClickListener(v -> {

                new PasswordsDatabaseHelper(context).setAccountVisibility(object, !object.isHidden());

                objectList.remove(object);

                notifyItemRemoved(holder.getAdapterPosition());
                dialog.dismiss();
            });

            assert menu_Delete != null;
            menu_Delete.setOnClickListener(v -> {

                if (!new PasswordsDatabaseHelper(context).deletePassword(object.getId())) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

                objectList.remove(object);
                notifyItemRemoved(holder.getAdapterPosition());
                dialog.dismiss();
            });

        });

    }


    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public static class ListBasicViewHolder extends RecyclerView.ViewHolder {

        private final TextView txt_index;
        private final TextView txt_title;
        private final TextView txt_email;
        private final ImageView ic_more_options;
        private final ImageView img_Star;

        public ListBasicViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_index = itemView.findViewById(R.id.txt_index);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_email = itemView.findViewById(R.id.txt_email);
            ic_more_options = itemView.findViewById(R.id.ic_more_options);
            img_Star = itemView.findViewById(R.id.img_Star);
        }
    }
}


