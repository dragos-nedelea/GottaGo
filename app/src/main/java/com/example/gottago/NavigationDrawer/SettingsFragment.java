package com.example.gottago.NavigationDrawer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gottago.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    private Spinner languageSpinner;
    private ImageButton backBtn;
    private Button changePasswordBtn;


    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize views
        languageSpinner = v.findViewById(R.id.languageSpinner);
        backBtn = v.findViewById(R.id.backBtn);
        changePasswordBtn = v.findViewById(R.id.forgotPassword);


        // Set click listener for back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        // Set click listener for change password button
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current user's email address
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                String emailAddress = user.getEmail();

                // Show an alert dialog to confirm password reset
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Reset Password");
                builder.setMessage("Are you sure you want to reset your password?");

                // Set up the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Send a password reset email to the user
                        auth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Password reset email sent.", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getContext(), "Failed to send password reset email.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });


//         Set adapter and listener for language spinner
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.language_options, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        languageSpinner.setAdapter(adapter);
//        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                String languageCode;
//                switch (position) {
//                    case 0: // Romanian
//                        languageCode = getString(R.string.language_code_ro);
//                        break;
//                    default:
//                        languageCode = getString(R.string.language_code_en);
//                }
//                // Change app language
//                Locale locale = new Locale(languageCode);
//                Configuration config = getResources().getConfiguration();
//                config.setLocale(locale);
//                Context context = requireContext().createConfigurationContext(config);
//                getActivity().recreate();
//                getActivity().setContentView(R.layout.fragment_settings);
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // Do nothing
//            }
//        });

        return v;
    }
}
