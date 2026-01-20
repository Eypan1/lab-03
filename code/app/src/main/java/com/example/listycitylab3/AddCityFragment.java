package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void updateCity();
    }

    private AddCityDialogListener listener;
    private City cityToEdit;

    public AddCityFragment() {}

    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    public static AddCityFragment newInstance(City city) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Check if we are editing an existing city
        boolean isEditMode = false;
        if (getArguments() != null && getArguments().containsKey("city")) {
            cityToEdit = (City) getArguments().getSerializable("city");
            isEditMode = true;
            editCityName.setText(cityToEdit.getCityName());
            editProvinceName.setText(cityToEdit.getProvinceName());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Determine the title and button text based on mode
        String title = isEditMode ? "Edit City" : "Add a city";
        String positiveButtonText = isEditMode ? "Update" : "Add";

        boolean finalIsEditMode = isEditMode;
        return builder
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(positiveButtonText, (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (finalIsEditMode) {
                        // Edit an existing city
                        cityToEdit.setCityName(cityName);
                        cityToEdit.setProvinceName(provinceName);
                        listener.updateCity();
                    } else {
                        // Add a new city
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}