package com.example.galaxyplayer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galaxyplayer.Fragments.LoginFragment;
import com.example.galaxyplayer.Fragments.PlayFragment;
import com.example.galaxyplayer.Fragments.SongListFragment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MainActivityClass extends AppCompatActivity implements LoginFragment.LoginFragmentListener {

    ViewPager viewPager ;
    FrameLayout frameLayout;
    SectionStatePageAdapter sectionStatePageAdapter;

    private static final String TAG = "MainActivityClass";

    public static final int SONG_LIST_FRAGMENT  = 0;
    public static final int LOGIN_FRAGMENT = 1;
    public static final int PLAY_FRAGMENT = 2;

    public static boolean permissionAllowed ;

    public SongListFragment songListFragment;
    public LoginFragment loginFragment;
    public PlayFragment playFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        //viewPager = findViewById(R.id.view_pager);
        frameLayout = findViewById(R.id.container_main);


        songListFragment = new SongListFragment();
        loginFragment = new LoginFragment();
        playFragment = new PlayFragment();
        Log.d(TAG, "onCreate: fragments newed");

        sectionStatePageAdapter = new SectionStatePageAdapter(getSupportFragmentManager());

        Log.d(TAG, "onCreate: sectionStatePageAdapter newd");

        //setupViewPager(viewPager);

        Log.d(TAG, "onCreate: viewPager set to setupViewPager");

        //viewPager.setCurrentItem(LOGIN_FRAGMENT);

        Log.d(TAG, "onCreate: we should now be in Login Fragment");

        goToFragment(loginFragment);

        permissionAllowed = checkPermissionREAD_EXTERNAL_STORAGE(this);

    }

    private void setupViewPager(ViewPager viewPager){

        SectionStatePageAdapter adapter = new SectionStatePageAdapter(getSupportFragmentManager());

        sectionStatePageAdapter.addFragment(songListFragment , "songListFragment");
        sectionStatePageAdapter.addFragment(loginFragment , "loginFragment");
        sectionStatePageAdapter.addFragment(playFragment , "playFragment");
        viewPager.setAdapter(adapter);

    }

    public void goToFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace( R.id.container_main , fragment )
                .commit();

    }

    public void setViewPager(int fragmentNumber){

        viewPager.setCurrentItem(fragmentNumber);

    }

    public final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    @Override
    public void sendName(CharSequence input) {

    }
}
