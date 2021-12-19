package com.app.vault.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app.vault.R;
import com.app.vault.utils.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CardsActivity extends AppCompatActivity {

    TextInputEditText edt_FirstName, edt_LastName, edt_CardNumber, edt_ExpiryDate, edt_CVV, edt_BankName;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        edt_FirstName = (TextInputEditText) findViewById(R.id.edt_FirstName);
        edt_LastName = (TextInputEditText) findViewById(R.id.edt_LastName);
        edt_CardNumber = (TextInputEditText) findViewById(R.id.edt_CardNumber);
        edt_ExpiryDate = (TextInputEditText) findViewById(R.id.edt_ExpiryDate);
        edt_CVV = (TextInputEditText) findViewById(R.id.edt_CVV);
        edt_BankName = (TextInputEditText) findViewById(R.id.edt_BankName);

        if (getIntent().getStringExtra(Utils.ACTIVITY_ACTION).equals(Utils.ACTION_EDIT)) {
            edt_FirstName.setText(getIntent().getStringExtra(Utils.COL_CARDS_FIRST_NAME));
            edt_LastName.setText(getIntent().getStringExtra(Utils.COL_CARDS_LAST_NAME));
            edt_CardNumber.setText(getIntent().getStringExtra(Utils.COL_CARDS_CARD_NUMBER));
            edt_ExpiryDate.setText(getIntent().getStringExtra(Utils.COL_CARDS_EXPIRY_DATE));
            edt_CVV.setText(getIntent().getStringExtra(Utils.COL_CARDS_CARD_CVV));
            edt_BankName.setText(getIntent().getStringExtra(Utils.COL_CARDS_BANK_NAME));
        }


        myCalendar = Calendar.getInstance();


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                updateLabel();
            }

        };


        edt_ExpiryDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CardsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar_AddCardActivity);
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

                    case R.id.menu_saveCard:
                        menuSave();
                        break;

                    case R.id.menu_deleteCard:
                        menuDelete();
                        break;

                    case R.id.menu_helpCard:
                        menuHelpFeedback();
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }

                return false;
            }
        });


    }

    private void updateLabel() {
        String myFormat = "MM/yy"; //In which you need put here
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);

        edt_ExpiryDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void menuBack() {
        finish();
    }

    private void menuSave() {
        Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
    }

    private void menuDelete() {
        Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
    }


    private void menuHelpFeedback() {
        Toast.makeText(this, "Help & feedback", Toast.LENGTH_SHORT).show();
    }
}