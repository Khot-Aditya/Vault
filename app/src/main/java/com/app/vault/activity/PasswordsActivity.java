package com.app.vault.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.app.vault.utils.Constants;
import com.app.vault.R;
import com.app.vault.utils.Utils;
import com.app.vault.model.PasswordModel;
import com.app.vault.sqlite.passwords.PasswordsDatabaseHelper;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PasswordsActivity extends Activity {

    private TextInputEditText edt_WebsiteName, edt_UserName, edt_EmailAddress, edt_PhoneNumber, edt_Password;
    private ChipGroup chip_chipGroup;

    private String title;
    private String date;
    private boolean isImportant;
    private boolean isHidden;

    private BottomSheetDialog dialog;
    private int accountId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwords);

        edt_WebsiteName = (TextInputEditText) findViewById(R.id.edt_Website);
        edt_UserName = (TextInputEditText) findViewById(R.id.edt_User);
        edt_EmailAddress = (TextInputEditText) findViewById(R.id.edt_EmailAddress);
        edt_PhoneNumber = (TextInputEditText) findViewById(R.id.edt_PhoneNumber);
        edt_Password = (TextInputEditText) findViewById(R.id.edt_Password);
        chip_chipGroup = (ChipGroup) findViewById(R.id.chipGroup_AddPassword);


        if (getIntent().getStringExtra(Utils.ACTIVITY_ACTION).equals(Utils.ACTION_EDIT)) {
            accountId = getIntent().getIntExtra(Utils.COL_PASSWORDS_ID, -0);
            title = getIntent().getStringExtra(Utils.COL_PASSWORDS_TITLE);
            date = getIntent().getStringExtra(Utils.COL_PASSWORDS_DATE);
            edt_WebsiteName.setText(getIntent().getStringExtra(Utils.COL_PASSWORDS_WEBSITE_NAME));
            edt_UserName.setText(getIntent().getStringExtra(Utils.COL_PASSWORDS_USER_NAME));
            edt_EmailAddress.setText(getIntent().getStringExtra(Utils.COL_PASSWORDS_EMAIL_ADDRESS));
            edt_PhoneNumber.setText(getIntent().getStringExtra(Utils.COL_PASSWORDS_PHONE_NUMBER));
            edt_Password.setText(getIntent().getStringExtra(Utils.COL_PASSWORDS_PASSWORD));
            chip_chipGroup.check(getChipId(getIntent().getIntExtra(Utils.COL_PASSWORDS_TAG, R.id.chip_Other)));
            isImportant = getIntent().getBooleanExtra(Utils.COL_PASSWORDS_IS_IMPORTANT, false);
            isHidden = getIntent().getBooleanExtra(Utils.COL_PASSWORDS_IS_HIDDEN, false);

            edt_Password.setTransformationMethod(new PasswordTransformationMethod());
        }

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar_AddPasswordActivity);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBack();
            }
        });

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menu_savePassword:
                        menuSave();
                        break;

                    case R.id.menu_more2:
                        moreOptions();
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }

                return false;
            }
        });

    }


    private void menuBack() {
        finish();
    }

    private void moreOptions() {
        View view = View.inflate(this, R.layout.bottom_sheet_activity_passwords, null);

        dialog = new BottomSheetDialog(this);

        dialog.setContentView(view);
        dialog.show();

        LinearLayout menu_Delete = (LinearLayout) view.findViewById(R.id.menu_Delete_AddPassword);
        LinearLayout menu_HelpFeedback = (LinearLayout) view.findViewById(R.id.menu_HelpFeedback_AddPassword);

        if (getIntent().getStringExtra(Utils.ACTIVITY_ACTION).equals(Utils.ACTION_EDIT)) {
            menu_Delete.setVisibility(View.VISIBLE);
        } else {
            menu_Delete.setVisibility(View.GONE);
        }

        menu_Delete.setOnClickListener(v -> menuDelete());
        menu_HelpFeedback.setOnClickListener(v -> menuHelpFeedback());
    }

    private int getChipId(int tagId){
        int id;
        if (tagId == Constants.TAG_PASSWORDS_GOOGLE_ACCOUNT) {
            id = R.id.chip_GoogleAccount;
        } else if (tagId == Constants.TAG_PASSWORDS_SECURITY_PIN) {
            id = R.id.chip_SecurityPIN;
        } else if (tagId == Constants.TAG_PASSWORDS_BUSINESS) {
            id = R.id.chip_Business;
        } else if (tagId == Constants.TAG_PASSWORDS_WALLET) {
            id = R.id.chip_Wallet;
        } else if (tagId == Constants.TAG_PASSWORDS_MASTER_PASSWORD) {
            id = R.id.chip_MasterPassword;
        } else if (tagId == Constants.TAG_PASSWORDS_E_COMMERCE) {
            id = R.id.chip_Ecommerce;
        } else if (tagId == Constants.TAG_PASSWORDS_FINANCE) {
            id = R.id.chip_Finance;
        } else if (tagId == Constants.TAG_PASSWORDS_SOCIAL_MEDIA) {
            id = R.id.chip_SocialMedia;
        } else if (tagId == Constants.TAG_PASSWORDS_OTHER) {
            id = Constants.TAG_PASSWORDS_OTHER;
        } else {
            throw new IllegalStateException("Unexpected value: " + tagId);
        }

        return id;
    }

    private int getTagId(int chipId) {

        int id;
        if (chipId == R.id.chip_GoogleAccount) {
            id = Constants.TAG_PASSWORDS_GOOGLE_ACCOUNT;
        } else if (chipId == R.id.chip_SecurityPIN) {
            id = Constants.TAG_PASSWORDS_SECURITY_PIN;
        } else if (chipId == R.id.chip_Business) {
            id = Constants.TAG_PASSWORDS_BUSINESS;
        } else if (chipId == R.id.chip_Wallet) {
            id = Constants.TAG_PASSWORDS_WALLET;
        } else if (chipId == R.id.chip_MasterPassword) {
            id = Constants.TAG_PASSWORDS_MASTER_PASSWORD;
        } else if (chipId == R.id.chip_Ecommerce) {
            id = Constants.TAG_PASSWORDS_E_COMMERCE;
        } else if (chipId == R.id.chip_Finance) {
            id = Constants.TAG_PASSWORDS_FINANCE;
        } else if (chipId == R.id.chip_SocialMedia) {
            id = Constants.TAG_PASSWORDS_SOCIAL_MEDIA;
        } else if (chipId == R.id.chip_Other) {
            id = Constants.TAG_PASSWORDS_OTHER;
        } else {
            throw new IllegalStateException("Unexpected value: " + chipId);
        }

        return id;
    }

    private void menuSave() {

        //TODO - TOAST EMPTY FIELDS ERROR

        PasswordModel passwordModel = new PasswordModel();
        passwordModel.setTitle(Objects.requireNonNull(edt_WebsiteName.getText()).toString());
        passwordModel.setDate(addToDate(3));
        passwordModel.setWebsiteName(Objects.requireNonNull(edt_WebsiteName.getText()).toString());
        passwordModel.setUserName(Objects.requireNonNull(edt_UserName.getText()).toString());
        passwordModel.setEmail(Objects.requireNonNull(edt_EmailAddress.getText()).toString());
        passwordModel.setPhoneNumber(Objects.requireNonNull(edt_PhoneNumber.getText()).toString());
        passwordModel.setPassword(Objects.requireNonNull(edt_Password.getText()).toString());
        passwordModel.setTag(getTagId(chip_chipGroup.getCheckedChipId()));

        PasswordsDatabaseHelper passwordsDatabaseHelper = new PasswordsDatabaseHelper(this);


        if (getIntent().getStringExtra(Utils.ACTIVITY_ACTION).equals(Utils.ACTION_EDIT)) {

            passwordModel.setHidden(isHidden);
            passwordModel.setImportant(isImportant);

            if (!passwordsDatabaseHelper.updateExistingPassword(passwordModel, accountId)) {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }

        } else {

            passwordModel.setHidden(false);
            passwordModel.setImportant(false);

            if (!passwordsDatabaseHelper.addNewAccount(passwordModel)) {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
        }


    }

    private void menuDelete() {

        if (!new PasswordsDatabaseHelper(this).deletePassword(accountId)) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        //TODO - ALERT DIALOG BEFORE DELETING

        finish();
    }

    private void menuHelpFeedback() {
        startActivity(new Intent(this, FeedbackActivity.class));
        dialog.dismiss();
    }

    public void generatePassword(View view) {
        edt_Password.setTransformationMethod(null);
        edt_Password.setText(Utils.getRandomPassword().toString());
    }

    public void copyFromClipboard(View view) {

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        try {
            if (clipboard != null && clipboard.getPrimaryClipDescription().hasMimeType("text/plain")) {
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                String yourText = item.getText().toString();

                edt_Password.setTransformationMethod(null);
                edt_Password.setText(yourText);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: Invalid Clipboard Data", Toast.LENGTH_SHORT).show();
        }


    }

    /* TODO
    create function in recycler adapter to compare date date
    save current date in database
    add 30/60/90 in recycler view
     */

    private String addToDate(int validation) {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String currentDate = dateFormat.format(date);  // get current date here

        calendar.add(Calendar.DAY_OF_YEAR, validation);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); //2021-06-06
        Date resultDate = calendar.getTime();

        return dateFormat.format(resultDate);
    }


}
