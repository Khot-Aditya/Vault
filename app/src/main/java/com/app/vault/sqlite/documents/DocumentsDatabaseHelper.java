package com.app.vault.sqlite.documents;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.app.vault.model.DocumentsModel;
import com.app.vault.utils.Constants;
import com.app.vault.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DocumentsDatabaseHelper extends SQLiteOpenHelper {

    public DocumentsDatabaseHelper(@Nullable Context context) {
        super(context, "documents.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + Utils.TABLE_NAME_DOCUMENTS + " (" +
                Utils.COL_DOCUMENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + "," +
                Utils.COL_DOCUMENTS_TITLE + " TEXT" + " , " +
                Utils.COL_DOCUMENTS_DATE + " TEXT" + " , " +
                Utils.COL_DOCUMENTS_FILES + " TEXT" + " , " +
                Utils.COL_DOCUMENTS_META_DATA + " TEXT" + " , " +
                Utils.COL_DOCUMENTS_TAG + " INTEGER" + " , " +
                Utils.COL_DOCUMENTS_IS_IMPORTANT + " BOOL" + " , " +
                Utils.COL_DOCUMENTS_IS_HIDDEN + " BOOL" + " )";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    public boolean updateExistingDocuments(DocumentsModel documentModel, int documentId) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(Utils.COL_PASSWORDS_TITLE, documentModel.getTitle());
//        contentValues.put(Utils.COL_PASSWORDS_DATE, documentModel.getDate());
//        contentValues.put(Utils.COL_DOCUMENTS_FILES,documentModel.getFiles());
//        contentValues.put(Utils.COL_PASSWORDS_TAG, documentModel.getTag());
//        contentValues.put(Utils.COL_PASSWORDS_IS_IMPORTANT, documentModel.isImportant());
//        contentValues.put(Utils.COL_PASSWORDS_IS_HIDDEN, documentModel.isHidden());
//
//        long insert = database.update(Utils.TABLE_NAME_DOCUMENTS, contentValues, Utils.COL_DOCUMENTS_ID + " = " + documentId, null);
//
//        database.close();
//        return insert != -1;
//    }

//    public void setAccountVisibility(PasswordModel passwordModel, boolean isHidden) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(Utils.COL_PASSWORDS_TITLE, passwordModel.getTitle());
//        contentValues.put(Utils.COL_PASSWORDS_DATE, passwordModel.getDate());
//        contentValues.put(Utils.COL_PASSWORDS_WEBSITE_NAME, passwordModel.getWebsiteName());
//        contentValues.put(Utils.COL_PASSWORDS_USER_NAME, passwordModel.getUserName());
//        contentValues.put(Utils.COL_PASSWORDS_EMAIL_ADDRESS, passwordModel.getEmail());
//        contentValues.put(Utils.COL_PASSWORDS_PHONE_NUMBER, passwordModel.getPhoneNumber());
//        contentValues.put(Utils.COL_PASSWORDS_PASSWORD, passwordModel.getPassword());
//        contentValues.put(Utils.COL_PASSWORDS_TAG, passwordModel.getTag());
//        contentValues.put(Utils.COL_PASSWORDS_IS_IMPORTANT, passwordModel.isImportant());
//        contentValues.put(Utils.COL_PASSWORDS_IS_HIDDEN, isHidden);
//
//        database.update(Utils.TABLE_NAME_PASSWORDS, contentValues, Utils.COL_PASSWORDS_ID + " = " + passwordModel.getId(), null);
//
//        database.close();
//    }

    public void setDocumentImportance(DocumentsModel documentModel, boolean important) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Utils.COL_DOCUMENTS_TITLE, documentModel.getTitle());
        contentValues.put(Utils.COL_DOCUMENTS_DATE, documentModel.getDate());
        contentValues.put(Utils.COL_DOCUMENTS_FILES, documentModel.getFiles());
        contentValues.put(Utils.COL_DOCUMENTS_META_DATA, documentModel.getDocumentContent());
        contentValues.put(Utils.COL_DOCUMENTS_TAG, documentModel.getTag());
        contentValues.put(Utils.COL_DOCUMENTS_IS_IMPORTANT, important);
        contentValues.put(Utils.COL_DOCUMENTS_IS_HIDDEN, documentModel.isHidden());

        database.update(Utils.TABLE_NAME_DOCUMENTS, contentValues, Utils.COL_DOCUMENTS_ID + " = " + documentModel.getId(), null);

        database.close();
    }


    public boolean deleteDocument(int documentId) {
        SQLiteDatabase database = this.getWritableDatabase();

        int delete = database.delete(Utils.TABLE_NAME_DOCUMENTS, Utils.COL_DOCUMENTS_ID + " = " + documentId, null);

        database.close();
        return delete != -1;
    }


    public boolean addNewDocument(DocumentsModel documentModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Utils.COL_DOCUMENTS_TITLE, documentModel.getTitle());
        contentValues.put(Utils.COL_DOCUMENTS_DATE, documentModel.getDate());
        contentValues.put(Utils.COL_DOCUMENTS_FILES, documentModel.getFiles());
        contentValues.put(Utils.COL_DOCUMENTS_META_DATA, documentModel.getDocumentContent());
        contentValues.put(Utils.COL_DOCUMENTS_TAG, documentModel.getTag());
        contentValues.put(Utils.COL_DOCUMENTS_IS_IMPORTANT, documentModel.isImportant());
        contentValues.put(Utils.COL_DOCUMENTS_IS_HIDDEN, documentModel.isHidden());

        long insert = database.insert(Utils.TABLE_NAME_DOCUMENTS, null, contentValues);

        database.close();
        return insert != -1;

    }

//    public List<PasswordModel> findItemsFromPasswords(@NonNull String searchString, boolean isHidden){
//
//        String queryString = "SELECT * FROM " + Utils.TABLE_NAME_PASSWORDS + " WHERE " + Utils.COL_PASSWORDS_TITLE + " LIKE '%" + searchString + "%'" + " AND " + Utils.COL_PASSWORDS_IS_HIDDEN + " = " + (isHidden ? 1 : 0);
//
//        return getDataFromDatabase(queryString);
//    }

//    public List<PasswordModel> getImportantAccounts(boolean getImportant) {
//
//        String queryString = "SELECT * FROM " + Utils.TABLE_NAME_PASSWORDS + " WHERE " + Utils.COL_PASSWORDS_IS_IMPORTANT + " = " + (getImportant ? 1 : 0);
//
//        return getDataFromDatabase(queryString);
//
//    }

    public List<DocumentsModel> getAccountsList(int tagId, boolean isHidden) {

        String queryString;

        if (tagId == Constants.TAG_DOCUMENTS_ALL) {
            queryString = "SELECT * FROM " + Utils.TABLE_NAME_DOCUMENTS + " WHERE " + Utils.COL_DOCUMENTS_IS_HIDDEN + " = " + (isHidden ? 1 : 0);
        } else {
            queryString = "SELECT * FROM " + Utils.TABLE_NAME_DOCUMENTS + " WHERE " + Utils.COL_DOCUMENTS_TAG + " = " + tagId;
        }

        return getDataFromDatabase(queryString);
    }

//    public List<DocumentsModel> getHiddenAccounts(boolean getHidden) {
//
//        String queryString = "SELECT * FROM " + Utils.TABLE_NAME_DOCUMENTS + " WHERE " + Utils.COL_DOCUMENTS_IS_HIDDEN + " = " + (getHidden ? 1 : 0);
//
//        return getDataFromDatabase(queryString);
//
//    }

    private List<DocumentsModel> getDataFromDatabase(String queryString) {

        SQLiteDatabase database = this.getReadableDatabase();

        List<DocumentsModel> itemList = new ArrayList();

        Cursor cursor = database.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String date = cursor.getString(2);
                String files = cursor.getString(3);
                String metaData = cursor.getString(4);
                int tags = cursor.getInt(5);
                int isImportant = cursor.getInt(6);
                int isHidden = cursor.getInt(7);

                DocumentsModel documentModel = new DocumentsModel();

                try {
                    documentModel.setId(id);
                    documentModel.setTitle(title);
                    documentModel.setDate(date);
                    documentModel.setFiles(files);
                    documentModel.setDocumentContent(metaData);
                    documentModel.setTag(tags);
                    documentModel.setImportant(isImportant == 1);
                    documentModel.setHidden(isHidden == 1);

                } catch (Exception e) {
                    Log.d(TAG, "getList: " + e.getLocalizedMessage());
                }

                itemList.add(documentModel);

            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "getList: error");
        }
        cursor.close();
        database.close();

        return itemList;
    }

    public int getItemCount() {
        String countQuery = "SELECT  * FROM " + Constants.TABLE_NAME_DOCUMENTS;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
