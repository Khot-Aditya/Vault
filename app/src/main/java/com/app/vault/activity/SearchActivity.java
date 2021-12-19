package com.app.vault.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.vault.utils.Constants;
import com.app.vault.R;
import com.app.vault.utils.Utils;
import com.app.vault.adapter.passwords.PasswordRecyclerAdapter;
import com.app.vault.model.PasswordModel;
import com.app.vault.sqlite.passwords.PasswordsDatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    TextInputEditText edt_Search;
    TextInputLayout edt_lyt_Search;
    RecyclerView recyclerView_Search;
    LinearLayout linearLayout_noData_Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edt_Search = (TextInputEditText) findViewById(R.id.edt_Search);
        edt_lyt_Search = (TextInputLayout) findViewById(R.id.edt_lyt_Search);
        recyclerView_Search = (RecyclerView) findViewById(R.id.recyclerview_Search);
        linearLayout_noData_Search = (LinearLayout) findViewById(R.id.linearLayout_NoData_Search);

        edt_Search.setHint("Search Passwords");

        edt_Search.requestFocus();

        edt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_lyt_Search.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        recyclerView_Search.addItemDecoration(new Utils.SimpleDividerItemDecoration(this));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_Search.setLayoutManager(layoutManager);
        recyclerView_Search.setAdapter(new PasswordRecyclerAdapter(new PasswordsDatabaseHelper(this)
                .getAccountsList(Constants.TAG_PASSWORDS_ALL,false), this));

    }

    @Override
    protected void onStart() {
        super.onStart();

//        recyclerView_Search.getViewTreeObserver().addOnPreDrawListener(
//                new ViewTreeObserver.OnPreDrawListener() {
//
//                    @Override
//                    public boolean onPreDraw() {
//                        recyclerView_Search.getViewTreeObserver().removeOnPreDrawListener(this);
//
//                        for (int i = 0; i < recyclerView_Search.getChildCount(); i++) {
//                            View v = recyclerView_Search.getChildAt(i);
//                            v.setAlpha(0.0f);
//                            v.animate().alpha(1.0f)
//                                    .setDuration(300)
//                                    .setStartDelay(i * 10L)
//                                    .start();
//                        }
//
//                        return true;
//                    }
//                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    private void search(String searchString) {

        if (searchString.equals("")) {
            recyclerView_Search.setVisibility(View.VISIBLE);
            linearLayout_noData_Search.setVisibility(View.GONE);

            recyclerView_Search.addItemDecoration(new Utils.SimpleDividerItemDecoration(this));
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView_Search.setLayoutManager(layoutManager);
            recyclerView_Search.setAdapter(new PasswordRecyclerAdapter(new PasswordsDatabaseHelper(this)
                    .getAccountsList(Constants.TAG_PASSWORDS_ALL,false), this));
        } else {

            List<PasswordModel> list = new ArrayList<>();

            list = new PasswordsDatabaseHelper(this).findItemsFromPasswords(searchString, false);

            if(list.isEmpty()){
                recyclerView_Search.setVisibility(View.GONE);
                linearLayout_noData_Search.setVisibility(View.VISIBLE);
            }else{
                recyclerView_Search.setVisibility(View.VISIBLE);
                linearLayout_noData_Search.setVisibility(View.GONE);

                recyclerView_Search.addItemDecoration(new Utils.SimpleDividerItemDecoration(this));
                final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerView_Search.setLayoutManager(layoutManager);
                recyclerView_Search.setAdapter(new PasswordRecyclerAdapter(list, this));
            }

        }


    }


}