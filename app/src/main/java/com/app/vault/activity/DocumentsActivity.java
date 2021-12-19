package com.app.vault.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.vault.R;
import com.app.vault.adapter.documents.SelectedFilesRecyclerAdapter;
import com.app.vault.model.DocumentsModel;
import com.app.vault.model.SelectedFilesModel;
import com.app.vault.sqlite.documents.DocumentsDatabaseHelper;
import com.app.vault.utils.Constants;
import com.app.vault.utils.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TODO - OPTIMISE CODE

public class DocumentsActivity extends AppCompatActivity {

    private final List<SelectedFilesModel> documentModelArrayList = new ArrayList<>();
    private static final int FILE_SELECT_CODE = 0;
    private TextInputEditText edt_DocumentName;
    private ChipGroup chipGroup_AddDocument;

    private int documentId;
    private String documentName;
    private String date;
    private String files;
    private boolean isImportant;
    private boolean isHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar_AddDocumentActivity);
        edt_DocumentName = (TextInputEditText) findViewById(R.id.edt_DocumentName);
        chipGroup_AddDocument = (ChipGroup) findViewById(R.id.chipGroup_AddDocument);


        if (getIntent().getStringExtra(Utils.ACTIVITY_ACTION).equals(Utils.ACTION_EDIT)) {

            documentId = getIntent().getIntExtra(Utils.COL_DOCUMENTS_ID, -0);
            documentName = getIntent().getStringExtra(Utils.COL_DOCUMENTS_TITLE);
            date = getIntent().getStringExtra(Utils.COL_DOCUMENTS_DATE);
            files = getIntent().getStringExtra(Utils.COL_DOCUMENTS_FILES);
            chipGroup_AddDocument.check(getChipId(getIntent().getIntExtra(Utils.COL_DOCUMENTS_TAG, R.id.chip_Documents_Other)));
            isImportant = getIntent().getBooleanExtra(Utils.COL_DOCUMENTS_IS_IMPORTANT, false);
            isHidden = getIntent().getBooleanExtra(Utils.COL_DOCUMENTS_IS_HIDDEN, false);

            String[] s = files.split(",");
            for (String i : s) {
                documentModelArrayList.add(0, setSelectedDocument(Uri.parse(i)));
            }

            RecyclerView recyclerView = findViewById(R.id.recyclerview_SelectedDocuments);
            recyclerView.addItemDecoration(new Utils.SimpleDividerItemDecoration(this));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new SelectedFilesRecyclerAdapter(documentModelArrayList, this));
        }

        edt_DocumentName.setText(documentName);

        topAppBar.setNavigationOnClickListener(v -> finish());
        topAppBar.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.menu_SaveDocument) {
                if (!Objects.requireNonNull(edt_DocumentName.getText()).toString().equals("")) {
                    saveDocument();
                } else {
                    Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
                }

            } else if (item.getItemId() == R.id.menu_MoreOptionDocument) {
                Toast.makeText(DocumentsActivity.this, "More Options", Toast.LENGTH_SHORT).show();
            } else {
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }

            return false;
        });
    }

    private void saveDocument() {

        StringBuilder stringBuilder = new StringBuilder("");

        long totalFileSize = 0;
        for (SelectedFilesModel selectedFilesModel : documentModelArrayList) {

            stringBuilder.append(selectedFilesModel.getOriginalPath()).append(",");
            totalFileSize = Integer.parseInt(selectedFilesModel.getFileSize()) + totalFileSize;
        }


        String fileStringPath = stringBuilder.toString();
        if (fileStringPath.length() > 0) {
            fileStringPath = fileStringPath.substring(0, fileStringPath.length() - 1);
        }


        String s = documentModelArrayList.size() == 1 ? " file" : " files";

        DocumentsModel documentsModel = new DocumentsModel();
        documentsModel.setTitle(Objects.requireNonNull(edt_DocumentName.getText()).toString());
        documentsModel.setTag(getTagId(chipGroup_AddDocument.getCheckedChipId()));
        documentsModel.setFiles(fileStringPath);
        documentsModel.setDocumentContent(documentModelArrayList.size() + s + " • " + Formatter.formatShortFileSize(this, totalFileSize));
        documentsModel.setHidden(false);
        documentsModel.setImportant(false);

        if (documentModelArrayList.size() > 0) {
            if (new DocumentsDatabaseHelper(this).addNewDocument(documentsModel)) {
                finish();
            } else {
                Toast.makeText(this, "Sql Error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Files Added 0", Toast.LENGTH_SHORT).show();
        }


    }

    private int getTagId(int chipId) {

        if (chipId == R.id.chip_Documents_PersonalDocument) return Constants.TAG_DOCUMENTS_PERSONAL;
        else if (chipId == R.id.chip_Documents_Government)
            return Constants.TAG_DOCUMENTS_GOVERNMENT;
        else if (chipId == R.id.chip_Documents_Education) return Constants.TAG_DOCUMENTS_EDUCATION;
        else if (chipId == R.id.chip_Documents_PaymentBills)
            return Constants.TAG_DOCUMENTS_PAYMENT_BILLS;
        else if (chipId == R.id.chip_Documents_Insurance) return Constants.TAG_DOCUMENTS_INSURANCE;
        else if (chipId == R.id.chip_Documents_Business) return Constants.TAG_DOCUMENTS_BUSINESS;
        else if (chipId == R.id.chip_Finance) return Constants.TAG_DOCUMENTS_FINANCE;
        else if (chipId == R.id.chip_Documents_Medical) return Constants.TAG_DOCUMENTS_MEDICAL;
        else if (chipId == R.id.chip_Documents_Other) return Constants.TAG_DOCUMENTS_OTHER;
        else throw new IllegalStateException("Unexpected value: " + chipId);

    }

    private int getChipId(int tagId) {

        if (tagId == Constants.TAG_DOCUMENTS_PERSONAL) return R.id.chip_Documents_PersonalDocument;
        else if (tagId == Constants.TAG_DOCUMENTS_GOVERNMENT) return R.id.chip_Documents_Government;
        else if (tagId == Constants.TAG_DOCUMENTS_EDUCATION) return R.id.chip_Documents_Education;
        else if (tagId == Constants.TAG_DOCUMENTS_PAYMENT_BILLS)
            return R.id.chip_Documents_PaymentBills;
        else if (tagId == Constants.TAG_DOCUMENTS_INSURANCE) return R.id.chip_Documents_Insurance;
        else if (tagId == Constants.TAG_DOCUMENTS_BUSINESS) return R.id.chip_Documents_Business;
        else if (tagId == Constants.TAG_DOCUMENTS_FINANCE) return R.id.chip_Documents_Finance;
        else if (tagId == Constants.TAG_DOCUMENTS_MEDICAL) return R.id.chip_Documents_Medical;
        else if (tagId == Constants.TAG_DOCUMENTS_OTHER) return Constants.TAG_DOCUMENTS_OTHER;
        else throw new IllegalStateException("Unexpected value: " + tagId);
    }

    public void showFileChooser(View view) throws android.content.ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"),
                FILE_SELECT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {

            // checking empty selection
            if (data != null) {

                // checking multiple selection or not
                if (data.getClipData() != null) {
                    for (int i = 0; i < data.getClipData().getItemCount(); i++) {

                        // Get the Uri of the selected file
                        documentModelArrayList.add(0, setSelectedDocument(data.getClipData().getItemAt(i).getUri()));
                    }
                } else {

                    // Get the Uri of the selected file
                    documentModelArrayList.add(0, setSelectedDocument(data.getData()));
                }
            }

            RecyclerView recyclerView = findViewById(R.id.recyclerview_SelectedDocuments);
            recyclerView.addItemDecoration(new Utils.SimpleDividerItemDecoration(this));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new SelectedFilesRecyclerAdapter(documentModelArrayList, this));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private SelectedFilesModel setSelectedDocument(Uri uri) {

        String uriString = uri.toString();
        File myFile = new File(uriString);
        String path = myFile.getAbsolutePath();

        String displayName = "";
        String displaySize = "1000";
        String extension = "";

        if (uriString.startsWith("content://")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    displaySize = cursor.getString(cursor.getColumnIndex(OpenableColumns.SIZE));

                    extension = displayName.substring(displayName.lastIndexOf(".") + 1).toUpperCase();
                    displayName = displayName.substring(0, displayName.indexOf("."));
                }
            }

        } else if (uriString.startsWith("file://")) {
            displayName = myFile.getName();
            displaySize = "1000";

        }

        SelectedFilesModel selectedFilesModel = new SelectedFilesModel();
        selectedFilesModel.setFileName(displayName);
        selectedFilesModel.setFileSize(displaySize);
        selectedFilesModel.setFileType(extension);
        selectedFilesModel.setOriginalPath(uriString);

        return selectedFilesModel;
    }
}





