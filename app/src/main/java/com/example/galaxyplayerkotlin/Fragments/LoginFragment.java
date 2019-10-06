package com.example.galaxyplayerkotlin.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.galaxyplayerkotlin.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private EditText nameEditText;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME = "name";
    public static final String IS_LOGIN = "isLogin";

    private String userName;
    private Boolean isLogin;

    private Button enterButton;

    private static final String TAG = "LoginFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_login, container, false);

        Log.d(TAG, "onCreateView: Welcome to LoginFragment");

        nameEditText = v.findViewById(R.id.nameEditText);
        enterButton = v.findViewById(R.id.EnterButton);


        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEditText.getText().length() <= 0) {

                    nameEditText.setError("This field can not remain empty");

                } else {

                    isLogin = true;

                    saveData(nameEditText.getText().toString() , isLogin);

                    loadData();

                    Fragment fragment = new SongListFragment();

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.music_player_layout, fragment);
                    transaction.commit();

                }

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();

        if (isLogin) {

            Log.d(TAG, "onResume: We should navigate to Song_List_Fragment ");

            Fragment fragment = new SongListFragment();

            changeLoginFragment(fragment);


        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void saveData(String name  , Boolean login) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(NAME, name);
        editor.putBoolean(IS_LOGIN, login);

        editor.apply();

    }


    public void loadData() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userName = sharedPreferences.getString(NAME, "");

        Log.d(TAG, "loadData: username = " + userName);

        isLogin = sharedPreferences.getBoolean(IS_LOGIN, false);

        Log.d(TAG, "loadData: isLogin = " + isLogin);
    }

    public void changeLoginFragment(Fragment fragment){

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.music_player_layout, fragment);
        transaction.commit();

    }


}
