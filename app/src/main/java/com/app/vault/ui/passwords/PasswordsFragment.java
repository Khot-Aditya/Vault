package com.app.vault.ui.passwords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.vault.utils.Constants;
import com.app.vault.R;
import com.app.vault.utils.Utils;
import com.app.vault.databinding.FragmentPasswordsBinding;
import com.app.vault.adapter.passwords.PasswordRecyclerAdapter;
import com.app.vault.sqlite.passwords.PasswordsDatabaseHelper;
import com.google.android.material.chip.ChipGroup;

public class PasswordsFragment extends Fragment {

    private PasswordsViewModel passwordsViewModel;
    private FragmentPasswordsBinding binding;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ChipGroup chipGroup_MainActivity;

    private NestedScrollView nestedScrollView_PasswordFragment;
    private LinearLayout linearLayout_NoData;


    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        passwordsViewModel =
                new ViewModelProvider(this).get(PasswordsViewModel.class);

        binding = FragmentPasswordsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    private int getTagId(int chipId) {

        int id;
        if (chipId == R.id.chip_MainActivity_GoogleAccount) {
            id = Constants.TAG_PASSWORDS_GOOGLE_ACCOUNT;
        } else if (chipId == R.id.chip_MainActivity_SecurityPin) {
            id = Constants.TAG_PASSWORDS_SECURITY_PIN;
        } else if (chipId == R.id.chip_MainActivity_Business) {
            id = Constants.TAG_PASSWORDS_BUSINESS;
        } else if (chipId == R.id.chip_MainActivity_Wallet) {
            id = Constants.TAG_PASSWORDS_WALLET;
        } else if (chipId == R.id.chip_MainActivity_MasterPassword) {
            id = Constants.TAG_PASSWORDS_MASTER_PASSWORD;
        } else if (chipId == R.id.chip_MainActivity_E_Commerce) {
            id = Constants.TAG_PASSWORDS_E_COMMERCE;
        } else if (chipId == R.id.chip_MainActivity_Finance) {
            id = Constants.TAG_PASSWORDS_FINANCE;
        } else if (chipId == R.id.chip_MainActivity_SocialMedia) {
            id = Constants.TAG_PASSWORDS_SOCIAL_MEDIA;
        } else if (chipId == R.id.chip_MainActivity_Other) {
            id = Constants.TAG_PASSWORDS_OTHER;
        } else if (chipId == R.id.chip_MainActivity_All){
            id = Constants.TAG_PASSWORDS_ALL;
        } else {
            throw new IllegalStateException("Unexpected value: " + chipId);
        }

        return id;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        chipGroup_MainActivity = (ChipGroup) view.findViewById(R.id.chipGroup_MainActivity);
        nestedScrollView_PasswordFragment = (NestedScrollView) view.findViewById(R.id.nestedScroll_PasswordsFragment);
        linearLayout_NoData = (LinearLayout) view.findViewById(R.id.linearLayout_NoData_Passwords);


        chipGroup_MainActivity.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                refreshRecyclerView(view);
            }
        });


        swipeRefreshLayout.setColorSchemeColors(requireContext().getColor(R.color.color_secondary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chipGroup_MainActivity.check(R.id.chip_MainActivity_All);
                refreshRecyclerView(view);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        refreshRecyclerView(view);
    }

    public void refreshRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_Passwords);

        if(new PasswordsDatabaseHelper(requireContext()).getItemCount() == 0){
            swipeRefreshLayout.setVisibility(View.GONE);
            linearLayout_NoData.setVisibility(View.VISIBLE);
        }else{
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            linearLayout_NoData.setVisibility(View.GONE);
        }

        recyclerView.addItemDecoration(new Utils.SimpleDividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new PasswordRecyclerAdapter(
                new PasswordsDatabaseHelper(requireContext())
                        .getAccountsList(getTagId(chipGroup_MainActivity.getCheckedChipId()), false), requireContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}