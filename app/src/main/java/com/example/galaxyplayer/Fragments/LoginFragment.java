package com.example.galaxyplayer.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.galaxyplayer.MainActivityClass;
import com.example.galaxyplayer.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private EditText nameEditText;
    private LoginFragmentListener loginFragmentListener;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME = "name";
    public static final String IS_LOGIN = "isLogin";

    private String userName;
    private Boolean isLogin;

    private Button enterButton;

    private static final String TAG = "LoginFragment";

    public interface LoginFragmentListener{
        void sendName(CharSequence input);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_login , container , false);

        Log.d(TAG, "onCreateView: Welcome to LoginFragment");
        
        nameEditText = v.findViewById(R.id.nameEditText);
         enterButton = v.findViewById(R.id.EnterButton);

        loadData();

        if (isLogin) {

            loginFragmentListener.sendName(userName);

            ((MainActivityClass)getActivity()).setViewPager(MainActivityClass.SONG_LIST_FRAGMENT);
        }

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEditText.getText().length() <= 0) {

                    Toast.makeText(getContext(), " Please Enter your name ", Toast.LENGTH_LONG).show();

                } else {

                    isLogin = true;

                    saveData();

                    loadData();

                    loginFragmentListener.sendName(userName);

                    ((MainActivityClass) getActivity()).goToFragment(new SongListFragment());
                }
            }
        });

        return v;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof LoginFragmentListener){

            loginFragmentListener = (LoginFragmentListener) context;

        }else {
            throw  new RuntimeException(context.toString() + " must implement loginFragmentListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginFragmentListener = null;
    }

    public void saveData() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(NAME, nameEditText.getText().toString());
        editor.putBoolean(IS_LOGIN, isLogin);

        editor.apply();

    }

    public void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userName = sharedPreferences.getString(NAME , "");
        isLogin = sharedPreferences.getBoolean(IS_LOGIN, false);

    }



}
