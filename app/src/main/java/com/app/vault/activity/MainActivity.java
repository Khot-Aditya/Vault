package com.app.vault.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.vault.R;
import com.app.vault.adapter.passwords.PasswordRecyclerAdapter;
import com.app.vault.databinding.ActivityMainBinding;
import com.app.vault.sqlite.passwords.PasswordsDatabaseHelper;
import com.app.vault.utils.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private BottomSheetDialog bottomSheetDialog_Passwords;
    private BottomSheetDialog bottomSheetDialog_Cards;
    private BottomSheetDialog bottomSheetDialog_Documents;
    private BottomSheetDialog bottomSheetDialog_Settings;

    private BottomSheetDialog bottomSheetDialog_Filter_Passwords;
    private BottomSheetDialog bottomSheetDialog_Filter_Cards;
    private BottomSheetDialog bottomSheetDialog_Filter_Documents;

    private RadioGroup radioGroup_Sort;
    private RadioGroup radioGroup_Group;
    private RadioGroup radioGroup_Validity;

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;

    /*
        TODO - CHANGES:
            DOCUMENT FILTER
            1. Recently
            2. A-Z
            3. Z-A
            4. File Size - Small or Large
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.app.vault.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_passwords, R.id.navigation_cards, R.id.navigation_documents, R.id.navigation_settings)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);


        MaterialToolbar topAppBar = findViewById(R.id.topAppBar_MainActivity);
        topAppBar.setOnMenuItemClickListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.menu_search) {

                if (getForegroundFragmentId() == R.id.navigation_passwords) {
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                    overridePendingTransition(0, 0);
                } else if (getForegroundFragmentId() == R.id.navigation_cards) {
                    //TODO - SEARCH OPTION FOR CARDS FRAGMENT

                    Toast.makeText(this, "Search Cards", Toast.LENGTH_SHORT).show();
                } else if (getForegroundFragmentId() == R.id.navigation_documents) {
                    //TODO - SEARCH OPTION FOR DOCUMENTS FRAGMENT

                    Toast.makeText(this, "Search Documents", Toast.LENGTH_SHORT).show();
                }


            } else if (itemId == R.id.menu_filter) {
                filterMenu();
            } else if (itemId == R.id.menu_more) {
                moreMenu();
            }
            return false;
        });

        requestPermissionForReadExtertalStorage();

    }

    public void requestPermissionForReadExtertalStorage() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }


    //----------------------------------------------------------------------------------------------
    private void newPassword() {
        bottomSheetDialog_Passwords.dismiss();

        Intent intent = new Intent(this, PasswordsActivity.class);
        intent.putExtra(Utils.ACTIVITY_ACTION, Utils.ACTION_ADD);
        intent.putExtra(Utils.COL_PASSWORDS_WEBSITE_NAME, "");
        intent.putExtra(Utils.COL_PASSWORDS_USER_NAME, "");
        intent.putExtra(Utils.COL_PASSWORDS_EMAIL_ADDRESS, "");
        intent.putExtra(Utils.COL_PASSWORDS_PHONE_NUMBER, "");
        intent.putExtra(Utils.COL_PASSWORDS_PASSWORD, "");
        intent.putExtra(Utils.COL_PASSWORDS_TAG, R.id.chip_Other);
        startActivity(intent);
    }

    private void backupCodes() {
        //TODO- BACKUP CODES ITEM VIEW AND RECYCLERVIEW

        bottomSheetDialog_Passwords.dismiss();
    }

    private void hiddenAccounts() {
        PasswordsDatabaseHelper passwordsDatabaseHelper = new PasswordsDatabaseHelper(this);

        PasswordRecyclerAdapter listsAdapter = new PasswordRecyclerAdapter(passwordsDatabaseHelper.getHiddenAccounts(true), this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_Passwords);
        recyclerView.addItemDecoration(new Utils.SimpleDividerItemDecoration(this));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listsAdapter);

        bottomSheetDialog_Passwords.dismiss();
    }

    private void importantAccounts() {
        PasswordsDatabaseHelper passwordsDatabaseHelper = new PasswordsDatabaseHelper(this);

        PasswordRecyclerAdapter listsAdapter = new PasswordRecyclerAdapter(passwordsDatabaseHelper.getImportantAccounts(true), this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_Passwords);
        recyclerView.addItemDecoration(new Utils.SimpleDividerItemDecoration(this));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listsAdapter);

        bottomSheetDialog_Passwords.dismiss();
    }

    //----------------------------------------------------------------------------------------------
    private void newCard() {
        bottomSheetDialog_Passwords.dismiss();

        Intent intent = new Intent(this, CardsActivity.class);
        intent.putExtra(Utils.ACTIVITY_ACTION, Utils.ACTION_ADD);
        intent.putExtra(Utils.COL_CARDS_FIRST_NAME, "");
        intent.putExtra(Utils.COL_CARDS_LAST_NAME, "");
        intent.putExtra(Utils.COL_CARDS_CARD_NUMBER, "");
        intent.putExtra(Utils.COL_CARDS_EXPIRY_DATE, "");
        intent.putExtra(Utils.COL_CARDS_CARD_CVV, "");
        intent.putExtra(Utils.COL_CARDS_BANK_NAME, "");
        startActivity(intent);
    }

    private void hiddenCards() {
        //TODO - HIDDEN CARDS
        Toast.makeText(this, "Hidden Cards", Toast.LENGTH_SHORT).show();
        bottomSheetDialog_Cards.dismiss();
    }

    private void importantCards() {
        //TODO - IMPORTANT CARDS
        Toast.makeText(this, "Important Cards", Toast.LENGTH_SHORT).show();
        bottomSheetDialog_Cards.dismiss();
    }

    //----------------------------------------------------------------------------------------------
    private void newDocument() {
        bottomSheetDialog_Documents.dismiss();

        Intent intent = new Intent(this, DocumentsActivity.class);
        intent.putExtra(Utils.ACTIVITY_ACTION, Utils.ACTION_ADD);
        intent.putExtra(Utils.COL_DOCUMENTS_TITLE, "");
        intent.putExtra(Utils.COL_DOCUMENTS_TAG, R.id.chip_Documents_Other);
        startActivity(intent);
    }

    private void hiddenDocuments() {
        //TODO - HIDDEN DOCUMENTS
        Toast.makeText(this, "Hidden Documents", Toast.LENGTH_SHORT).show();
        bottomSheetDialog_Documents.dismiss();
    }

    private void importantDocuments() {
        //TODO - IMPORTANT DOCUMENTS
        Toast.makeText(this, "Important Documents", Toast.LENGTH_SHORT).show();
        bottomSheetDialog_Documents.dismiss();
    }

    //----------------------------------------------------------------------------------------------
    private void helpFeedback_Passwords() {
        //TODO - FEEDBACK FOR PASSWORDS ACTIVITY
        startActivity(new Intent(this, FeedbackActivity.class));
        bottomSheetDialog_Passwords.dismiss();
    }

    private void helpFeedback_Cards() {
        //TODO - FEEDBACK FOR CARDS ACTIVITY
        startActivity(new Intent(this, FeedbackActivity.class));
        bottomSheetDialog_Cards.dismiss();
    }

    private void helpFeedback_Documents() {
        //TODO - FEEDBACK FOR DOCUMENTS ACTIVITY
        startActivity(new Intent(this, FeedbackActivity.class));
        bottomSheetDialog_Documents.dismiss();
    }

    private void helpFeedback_Settings() {
        //TODO - FEEDBACK FOR SETTINGS ACTIVITY
        startActivity(new Intent(this, FeedbackActivity.class));
        bottomSheetDialog_Settings.dismiss();
    }

    //----------------------------------------------------------------------------------------------
    private void applyPasswordFilter() {
        int checkedRadioButtonId_Sort = radioGroup_Sort.getCheckedRadioButtonId();
        if (checkedRadioButtonId_Sort == R.id.radioButton_Ascending) {
            //TODO - SORT A-Z

            Toast.makeText(this, "Sort A-Z", Toast.LENGTH_SHORT).show();
        } else if (checkedRadioButtonId_Sort == R.id.radioButton_Descending) {
            //TODO - SORT Z-A

            Toast.makeText(this, "Sort Z-A", Toast.LENGTH_SHORT).show();
        } else if (checkedRadioButtonId_Sort == R.id.radioButton_RecentlyAdded) {
            //TODO  - RECENTLY ADDED

            Toast.makeText(this, "Sort Recently Added", Toast.LENGTH_SHORT).show();
        }

        int checkedRadioButtonId_Group = radioGroup_Group.getCheckedRadioButtonId();
        if (checkedRadioButtonId_Group == R.id.radioButton_EmailAddress) {
            //TODO - SORT A-Z

            Toast.makeText(this, "Group Email Address", Toast.LENGTH_SHORT).show();
        } else if (checkedRadioButtonId_Group == R.id.radioButton_PhoneNumber) {
            //TODO - SORT Z-A

            Toast.makeText(this, "Group Phone Number", Toast.LENGTH_SHORT).show();
        }

        int checkedRadioButtonId_Validity = radioGroup_Validity.getCheckedRadioButtonId();

        if (checkedRadioButtonId_Validity == R.id.radioButton_Healthy) {
            //TODO - FILTER PASSWORD VALIDITY

            Toast.makeText(this, "Validity Healthy", Toast.LENGTH_SHORT).show();
        } else if (checkedRadioButtonId_Validity == R.id.radioButton_Expired) {
            //TODO - FILTER PASSWORD VALIDITY

            Toast.makeText(this, "Validity Expired", Toast.LENGTH_SHORT).show();
        } else if (checkedRadioButtonId_Validity == R.id.radioButton_Obsolete) {
            //TODO - FILTER PASSWORD VALIDITY

            Toast.makeText(this, "Validity Obsolete", Toast.LENGTH_SHORT).show();
        }
        bottomSheetDialog_Filter_Passwords.dismiss();
    }

    private void resetPasswordFilter() {
        //TODO - RESET PASSWORD FILTER

        bottomSheetDialog_Filter_Passwords.dismiss();
    }

    //----------------------------------------------------------------------------------------------
    private void applyCardsFilter() {

    }

    private void resetCardsFilter() {

    }

    //----------------------------------------------------------------------------------------------
    private void applyDocumentsFilter() {

    }

    private void resetDocumentsFilter() {

    }

    //----------------------------------------------------------------------------------------------
    private void filterMenu() {

        if (getForegroundFragmentId() == R.id.navigation_passwords) {

            bottomSheetDialog_Filter_Passwords = new BottomSheetDialog(MainActivity.this);
            bottomSheetDialog_Filter_Passwords.setContentView(View.inflate(MainActivity.this, R.layout.bottom_sheet_filter_passwords, null));
            bottomSheetDialog_Filter_Passwords.setDismissWithAnimation(true);
            bottomSheetDialog_Filter_Passwords.show();

            radioGroup_Sort = (RadioGroup) bottomSheetDialog_Filter_Passwords.findViewById(R.id.radioGroup_Sort);
            radioGroup_Group = (RadioGroup) bottomSheetDialog_Filter_Passwords.findViewById(R.id.radioGroup_GroupBy);
            radioGroup_Validity = (RadioGroup) bottomSheetDialog_Filter_Passwords.findViewById(R.id.radioGroup_PasswordHealth);


            MaterialButton materialButton_Reset = (MaterialButton) bottomSheetDialog_Filter_Passwords.findViewById(R.id.button_Reset);
            MaterialButton materialButton_Filter = (MaterialButton) bottomSheetDialog_Filter_Passwords.findViewById(R.id.button_Filter);


            assert materialButton_Reset != null;
            materialButton_Reset.setOnClickListener(v -> resetPasswordFilter());
            assert materialButton_Filter != null;
            materialButton_Filter.setOnClickListener(v -> applyPasswordFilter());
        } else if (getForegroundFragmentId() == R.id.navigation_cards) {
            //TODO - FILTER FOR CARDS FRAGMENT

            Toast.makeText(this, "Filter Cards", Toast.LENGTH_SHORT).show();
        } else if (getForegroundFragmentId() == R.id.navigation_documents) {
            //TODO - FILTER FOR DOCUMENTS FRAGMENT

            Toast.makeText(this, "Filter Documents", Toast.LENGTH_SHORT).show();
        }

    }

    private void moreMenu() {

        int foregroundFragmentId = getForegroundFragmentId();
        if (foregroundFragmentId == R.id.navigation_passwords) {

            bottomSheetDialog_Passwords = new BottomSheetDialog(this);
            bottomSheetDialog_Passwords.setContentView(View.inflate(this, R.layout.bottom_sheet_navigation_passwords, null));
            bottomSheetDialog_Passwords.show();
        } else if (foregroundFragmentId == R.id.navigation_cards) {

            bottomSheetDialog_Cards = new BottomSheetDialog(this);
            bottomSheetDialog_Cards.setContentView(View.inflate(this, R.layout.bottom_sheet_navigation_cards, null));
            bottomSheetDialog_Cards.show();
        } else if (foregroundFragmentId == R.id.navigation_documents) {

            bottomSheetDialog_Documents = new BottomSheetDialog(this);
            bottomSheetDialog_Documents.setContentView(View.inflate(this, R.layout.bottom_sheet_navigation_documents, null));
            bottomSheetDialog_Documents.show();
        } else if (foregroundFragmentId == R.id.navigation_settings) {

            bottomSheetDialog_Settings = new BottomSheetDialog(this);
            bottomSheetDialog_Settings.setContentView(View.inflate(this, R.layout.bottom_sheet_navigation_settings, null));
            bottomSheetDialog_Settings.show();
        } else {
            throw new IllegalStateException("Unexpected value: " + getForegroundFragmentId());
        }

        if (bottomSheetDialog_Passwords != null) {
            LinearLayout menu_Passwords_AddNewAccount = (LinearLayout) bottomSheetDialog_Passwords.findViewById(R.id.menu_Passwords_AddNewAccount);
            LinearLayout menu_Passwords_BackupCodes = (LinearLayout) bottomSheetDialog_Passwords.findViewById(R.id.menu_Passwords_BackupCodes);
            LinearLayout menu_Passwords_HiddenAccounts = (LinearLayout) bottomSheetDialog_Passwords.findViewById(R.id.menu_Passwords_HiddenAccounts);
            LinearLayout menu_Passwords_ImportantAccounts = (LinearLayout) bottomSheetDialog_Passwords.findViewById(R.id.menu_Passwords_ImportantAccounts);
            LinearLayout menu_Passwords_HelpFeedback = (LinearLayout) bottomSheetDialog_Passwords.findViewById(R.id.menu_Passwords_HelpFeedback);

            assert menu_Passwords_AddNewAccount != null;
            menu_Passwords_AddNewAccount.setOnClickListener(v -> newPassword());
            assert menu_Passwords_BackupCodes != null;
            menu_Passwords_BackupCodes.setOnClickListener(v -> backupCodes());
            assert menu_Passwords_HiddenAccounts != null;
            menu_Passwords_HiddenAccounts.setOnClickListener(v -> hiddenAccounts());
            assert menu_Passwords_ImportantAccounts != null;
            menu_Passwords_ImportantAccounts.setOnClickListener(v -> importantAccounts());
            assert menu_Passwords_HelpFeedback != null;
            menu_Passwords_HelpFeedback.setOnClickListener(v -> helpFeedback_Passwords());
        }

        if (bottomSheetDialog_Cards != null) {
            LinearLayout menu_Cards_AddNewCard = (LinearLayout) bottomSheetDialog_Cards.findViewById(R.id.menu_Cards_AddNewCard);
            LinearLayout menu_Cards_HiddenCards = (LinearLayout) bottomSheetDialog_Cards.findViewById(R.id.menu_Cards_HiddenCards);
            LinearLayout menu_Cards_ImportantCards = (LinearLayout) bottomSheetDialog_Cards.findViewById(R.id.menu_Cards_Important);
            LinearLayout menu_Cards_HelpFeedback = (LinearLayout) bottomSheetDialog_Cards.findViewById(R.id.menu_Cards_HelpFeedback);

            assert menu_Cards_AddNewCard != null;
            menu_Cards_AddNewCard.setOnClickListener(v -> newCard());
            assert menu_Cards_HiddenCards != null;
            menu_Cards_HiddenCards.setOnClickListener(v -> hiddenCards());
            assert menu_Cards_ImportantCards != null;
            menu_Cards_ImportantCards.setOnClickListener(v -> importantCards());
            assert menu_Cards_HelpFeedback != null;
            menu_Cards_HelpFeedback.setOnClickListener(v -> helpFeedback_Cards());

        }

        if (bottomSheetDialog_Documents != null) {
            LinearLayout menu_Documents_AddNewDocument = (LinearLayout) bottomSheetDialog_Documents.findViewById(R.id.menu_Documents_AddNewDocument);
            LinearLayout menu_Documents_HiddenDocuments = (LinearLayout) bottomSheetDialog_Documents.findViewById(R.id.menu_Documents_HiddenDocuments);
            LinearLayout menu_Documents_ImportantDocuments = (LinearLayout) bottomSheetDialog_Documents.findViewById(R.id.menu_Documents_ImportantDocuments);
            LinearLayout menu_Documents_HelpFeedback = (LinearLayout) bottomSheetDialog_Documents.findViewById(R.id.menu_Documents_HelpFeedback);

            assert menu_Documents_AddNewDocument != null;
            menu_Documents_AddNewDocument.setOnClickListener(v -> newDocument());
            assert menu_Documents_HiddenDocuments != null;
            menu_Documents_HiddenDocuments.setOnClickListener(v -> hiddenDocuments());
            assert menu_Documents_ImportantDocuments != null;
            menu_Documents_ImportantDocuments.setOnClickListener(v -> importantDocuments());
            assert menu_Documents_HelpFeedback != null;
            menu_Documents_HelpFeedback.setOnClickListener(v -> helpFeedback_Documents());

        }

        if (bottomSheetDialog_Settings != null) {
            LinearLayout menu_Settings_HelpFeedback = (LinearLayout) bottomSheetDialog_Settings.findViewById(R.id.menu_Settings_HelpFeedback);

            assert menu_Settings_HelpFeedback != null;
            menu_Settings_HelpFeedback.setOnClickListener(v -> helpFeedback_Settings());
        }


    }

    //----------------------------------------------------------------------------------------------
    public int getForegroundFragmentId() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return Objects.requireNonNull(navController.getCurrentDestination()).getId();
    }
}