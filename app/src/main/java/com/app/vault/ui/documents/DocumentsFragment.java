package com.app.vault.ui.documents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.vault.R;
import com.app.vault.databinding.FragmentDocumentsBinding;
import com.app.vault.model.DocumentsModel;
import com.app.vault.adapter.documents.DocumentsRecyclerAdapter;
import com.app.vault.sqlite.documents.DocumentsDatabaseHelper;
import com.app.vault.utils.Constants;
import com.app.vault.utils.Utils;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class DocumentsFragment extends Fragment {

    private DocumentsViewModel documentsViewModel;
    private FragmentDocumentsBinding binding;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ChipGroup chipGroup_Documents;


    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        documentsViewModel =
                new ViewModelProvider(this).get(DocumentsViewModel.class);

        binding = FragmentDocumentsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh_Documents);
        chipGroup_Documents = (ChipGroup) view.findViewById(R.id.chipGroup_Documents);


        chipGroup_Documents.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                refreshRecyclerView(view);
            }
        });

        swipeRefreshLayout.setColorSchemeColors(requireContext().getColor(R.color.color_secondary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chipGroup_Documents.check(R.id.chip_Documents_All);
                refreshRecyclerView(view);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void refreshRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_Documents);

        List<DocumentsModel> documentModelList = new ArrayList<>();

        String selectedDocumentModel = null;


//        documentModelList.add(new DocumentsModel("Aadhaar Card","2 items • 12kb", selectedDocumentModel, 100));
//        documentModelList.add(new DocumentsModel("Drivers Licence","1 items • 1kb", selectedDocumentModel, 100));
//        documentModelList.add(new DocumentsModel("10th Certificate","2 items • 4kb", selectedDocumentModel, 100));
//        documentModelList.add(new DocumentsModel("Property Documents","3 items • 6kb", selectedDocumentModel, 100));
//        documentModelList.add(new DocumentsModel("Account Documents","6 items • 19kb", selectedDocumentModel, 100));
//        documentModelList.add(new DocumentsModel("Insurance","1 items • 2kb", selectedDocumentModel, 100));

//        if(new PasswordsDatabaseHelper(requireContext()).getItemCount() == 0){
//            swipeRefreshLayout.setVisibility(View.GONE);
//            linearLayout_NoData.setVisibility(View.VISIBLE);
//        }else{
//            swipeRefreshLayout.setVisibility(View.VISIBLE);
//            linearLayout_NoData.setVisibility(View.GONE);
//        }

        recyclerView.addItemDecoration(new Utils.SimpleDividerItemDecoration(requireContext()));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new DocumentsRecyclerAdapter(
                new DocumentsDatabaseHelper(requireContext())
                        .getAccountsList(getTagId(chipGroup_Documents.getCheckedChipId()), false), requireContext()));
    }

    private int getTagId(int chipId) {

        int id;
        if (chipId == R.id.chip_Documents_PersonalDocument) {
            id = Constants.TAG_DOCUMENTS_PERSONAL;
        } else if (chipId == R.id.chip_Documents_Government) {
            id = Constants.TAG_DOCUMENTS_GOVERNMENT;
        } else if (chipId == R.id.chip_Documents_Education) {
            id = Constants.TAG_DOCUMENTS_EDUCATION;
        } else if (chipId == R.id.chip_Documents_PaymentBills) {
            id = Constants.TAG_DOCUMENTS_PAYMENT_BILLS;
        } else if (chipId == R.id.chip_Documents_Insurance) {
            id = Constants.TAG_DOCUMENTS_INSURANCE;
        } else if (chipId == R.id.chip_Documents_Business) {
            id = Constants.TAG_DOCUMENTS_BUSINESS;
        } else if (chipId == R.id.chip_Documents_Finance) {
            id = Constants.TAG_DOCUMENTS_FINANCE;
        } else if (chipId == R.id.chip_Documents_Medical) {
            id = Constants.TAG_DOCUMENTS_MEDICAL;
        } else if (chipId == R.id.chip_Documents_Other) {
            id = Constants.TAG_DOCUMENTS_OTHER;
        } else if (chipId == R.id.chip_Documents_All){
            id = Constants.TAG_DOCUMENTS_ALL;
        }else {
            throw new IllegalStateException("Unexpected value: " + chipId);
        }

        return id;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshRecyclerView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}