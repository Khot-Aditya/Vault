package com.app.vault.sqlite.passwords;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.vault.utils.Constants;
import com.app.vault.utils.Utils;
import com.app.vault.model.PasswordModel;

import java.util.ArrayList;
import java.util.List;

public class PasswordsDatabaseHelper extends SQLiteOpenHelper {

    public PasswordsDatabaseHelper(@Nullable Context context) {
        super(context, "passwords.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + Utils.TABLE_NAME_PASSWORDS + " (" +
                Utils.COL_PASSWORDS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + "," +
                Utils.COL_PASSWORDS_TITLE + " TEXT" + " , " +
                Utils.COL_PASSWORDS_DATE + " TEXT" + " , " +
                Utils.COL_PASSWORDS_WEBSITE_NAME + " TEXT" + " , " +
                Utils.COL_PASSWORDS_USER_NAME + " TEXT" + " , " +
                Utils.COL_PASSWORDS_EMAIL_ADDRESS + " TEXT" + " , " +
                Utils.COL_PASSWORDS_PHONE_NUMBER + " TEXT" + " , " +
                Utils.COL_PASSWORDS_PASSWORD + " TEXT" + " , " +
                Utils.COL_PASSWORDS_TAG + " INTEGER" + " , " +
                Utils.COL_PASSWORDS_IS_IMPORTANT + " BOOL" + " , " +
                Utils.COL_PASSWORDS_IS_HIDDEN + " BOOL" + " )";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean updateExistingPassword(PasswordModel passwordModel, int accountId) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Utils.COL_PASSWORDS_TITLE, passwordModel.getTitle());
        contentValues.put(Utils.COL_PASSWORDS_DATE, passwordModel.getDate());
        contentValues.put(Utils.COL_PASSWORDS_WEBSITE_NAME, passwordModel.getWebsiteName());
        contentValues.put(Utils.COL_PASSWORDS_USER_NAME, passwordModel.getUserName());
        contentValues.put(Utils.COL_PASSWORDS_EMAIL_ADDRESS, passwordModel.getEmail());
        contentValues.put(Utils.COL_PASSWORDS_PHONE_NUMBER, passwordModel.getPhoneNumber());
        contentValues.put(Utils.COL_PASSWORDS_PASSWORD, passwordModel.getPassword());
        contentValues.put(Utils.COL_PASSWORDS_TAG, passwordModel.getTag());
        contentValues.put(Utils.COL_PASSWORDS_IS_IMPORTANT, passwordModel.isImportant());
        contentValues.put(Utils.COL_PASSWORDS_IS_HIDDEN, passwordModel.isHidden());

        long insert = database.update(Utils.TABLE_NAME_PASSWORDS, contentValues, Utils.COL_PASSWORDS_ID + " = " + accountId, null);

        database.close();
        return insert != -1;
    }

    public void setAccountVisibility(PasswordModel passwordModel, boolean isHidden) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Utils.COL_PASSWORDS_TITLE, passwordModel.getTitle());
        contentValues.put(Utils.COL_PASSWORDS_DATE, passwordModel.getDate());
        contentValues.put(Utils.COL_PASSWORDS_WEBSITE_NAME, passwordModel.getWebsiteName());
        contentValues.put(Utils.COL_PASSWORDS_USER_NAME, passwordModel.getUserName());
        contentValues.put(Utils.COL_PASSWORDS_EMAIL_ADDRESS, passwordModel.getEmail());
        contentValues.put(Utils.COL_PASSWORDS_PHONE_NUMBER, passwordModel.getPhoneNumber());
        contentValues.put(Utils.COL_PASSWORDS_PASSWORD, passwordModel.getPassword());
        contentValues.put(Utils.COL_PASSWORDS_TAG, passwordModel.getTag());
        contentValues.put(Utils.COL_PASSWORDS_IS_IMPORTANT, passwordModel.isImportant());
        contentValues.put(Utils.COL_PASSWORDS_IS_HIDDEN, isHidden);

        database.update(Utils.TABLE_NAME_PASSWORDS, contentValues, Utils.COL_PASSWORDS_ID + " = " + passwordModel.getId(), null);

        database.close();
    }

    public void setAccountImportance(PasswordModel passwordModel, boolean important) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Utils.COL_PASSWORDS_TITLE, passwordModel.getTitle());
        contentValues.put(Utils.COL_PASSWORDS_DATE, passwordModel.getDate());
        contentValues.put(Utils.COL_PASSWORDS_WEBSITE_NAME, passwordModel.getWebsiteName());
        contentValues.put(Utils.COL_PASSWORDS_USER_NAME, passwordModel.getUserName());
        contentValues.put(Utils.COL_PASSWORDS_EMAIL_ADDRESS, passwordModel.getEmail());
        contentValues.put(Utils.COL_PASSWORDS_PHONE_NUMBER, passwordModel.getPhoneNumber());
        contentValues.put(Utils.COL_PASSWORDS_PASSWORD, passwordModel.getPassword());
        contentValues.put(Utils.COL_PASSWORDS_TAG, passwordModel.getTag());
        contentValues.put(Utils.COL_PASSWORDS_IS_IMPORTANT, important);
        contentValues.put(Utils.COL_PASSWORDS_IS_HIDDEN, passwordModel.isHidden());

        database.update(Utils.TABLE_NAME_PASSWORDS, contentValues, Utils.COL_PASSWORDS_ID + " = " + passwordModel.getId(), null);

        database.close();
    }


    public boolean deletePassword(int accountId) {
        SQLiteDatabase database = this.getWritableDatabase();

        int delete = database.delete(Utils.TABLE_NAME_PASSWORDS, Utils.COL_PASSWORDS_ID + " = " + accountId, null);

        database.close();
        return delete != -1;
    }


    public boolean addNewAccount(PasswordModel passwordModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Utils.COL_PASSWORDS_TITLE, passwordModel.getTitle());
        contentValues.put(Utils.COL_PASSWORDS_DATE, passwordModel.getDate());
        contentValues.put(Utils.COL_PASSWORDS_WEBSITE_NAME, passwordModel.getWebsiteName());
        contentValues.put(Utils.COL_PASSWORDS_USER_NAME, passwordModel.getUserName());
        contentValues.put(Utils.COL_PASSWORDS_EMAIL_ADDRESS, passwordModel.getEmail());
        contentValues.put(Utils.COL_PASSWORDS_PHONE_NUMBER, passwordModel.getPhoneNumber());
        contentValues.put(Utils.COL_PASSWORDS_PASSWORD, passwordModel.getPassword());
        contentValues.put(Utils.COL_PASSWORDS_TAG, passwordModel.getTag());
        contentValues.put(Utils.COL_PASSWORDS_IS_IMPORTANT, passwordModel.isImportant());
        contentValues.put(Utils.COL_PASSWORDS_IS_HIDDEN, passwordModel.isHidden());

        long insert = database.insert(Utils.TABLE_NAME_PASSWORDS, null, contentValues);

        database.close();
        return insert != -1;

    }

    public List<PasswordModel> findItemsFromPasswords(@NonNull String searchString, boolean isHidden){

        String queryString = "SELECT * FROM " + Utils.TABLE_NAME_PASSWORDS + " WHERE " + Utils.COL_PASSWORDS_TITLE + " LIKE '%" + searchString + "%'" + " AND " + Utils.COL_PASSWORDS_IS_HIDDEN + " = " + (isHidden ? 1 : 0);

        return getDataFromDatabase(queryString);
    }

    public List<PasswordModel> getImportantAccounts(boolean getImportant) {

        String queryString = "SELECT * FROM " + Utils.TABLE_NAME_PASSWORDS + " WHERE " + Utils.COL_PASSWORDS_IS_IMPORTANT + " = " + (getImportant ? 1 : 0);

        return getDataFromDatabase(queryString);

    }

    public List<PasswordModel> getAccountsList(int tagId, boolean isHidden) {

        String queryString;

        if (tagId == Constants.TAG_PASSWORDS_ALL) {
            queryString = "SELECT * FROM " + Utils.TABLE_NAME_PASSWORDS + " WHERE " + Utils.COL_PASSWORDS_IS_HIDDEN + " = " + (isHidden ? 1 : 0);
        } else {
            queryString = "SELECT * FROM " + Utils.TABLE_NAME_PASSWORDS + " WHERE " + Utils.COL_PASSWORDS_TAG + " = " + tagId;
        }

        return getDataFromDatabase(queryString);
    }

    public List<PasswordModel> getHiddenAccounts(boolean getHidden) {

        String queryString = "SELECT * FROM " + Utils.TABLE_NAME_PASSWORDS + " WHERE " + Utils.COL_PASSWORDS_IS_HIDDEN + " = " + (getHidden ? 1 : 0);

        return getDataFromDatabase(queryString);

    }

    public List<PasswordModel> getDataFromDatabase(String queryString) {

        SQLiteDatabase database = this.getReadableDatabase();

        List<PasswordModel> itemList = new ArrayList();

        Cursor cursor = database.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String date = cursor.getString(2);
                String websiteName = cursor.getString(3);
                String userName = cursor.getString(4);
                String emailAddress = cursor.getString(5);
                String phoneNumber = cursor.getString(6);
                String passwords = cursor.getString(7);
                int tags = cursor.getInt(8);
                int isImportant = cursor.getInt(9);
                int isHidden = cursor.getInt(10);

                PasswordModel passwordModel = new PasswordModel();

                try {
                    passwordModel.setId(id);
                    passwordModel.setTitle(title);
                    passwordModel.setDate(date);
                    passwordModel.setWebsiteName(websiteName);
                    passwordModel.setUserName(userName);
                    passwordModel.setEmail(emailAddress);
                    passwordModel.setPhoneNumber(phoneNumber);
                    passwordModel.setPassword(passwords);
                    passwordModel.setTag(tags);
                    passwordModel.setImportant(isImportant == 1);
                    passwordModel.setHidden(isHidden == 1);

                } catch (Exception e) {
                    Log.d(TAG, "getList: " + e.getLocalizedMessage());
                }

                itemList.add(passwordModel);

            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "getList: error");
        }
        cursor.close();
        database.close();

        return itemList;
    }

    public int getItemCount() {
        String countQuery = "SELECT  * FROM " + Constants.TABLE_NAME_PASSWORDS;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
