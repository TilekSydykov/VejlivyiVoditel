package kg.flaterlab.vv;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;

import android.view.MenuItem;
import android.view.Window;


import io.paperdb.Paper;
import kg.flaterlab.vv.fragments.AddFragment;
import kg.flaterlab.vv.fragments.DashBoardFragment;
import kg.flaterlab.vv.fragments.ProfileFragment;
import kg.flaterlab.vv.data.model.User;
import kg.flaterlab.vv.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    User u;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    replaceFragment(DashBoardFragment.newInstance(), "fg");
                    return true;
                case R.id.navigation_add:
                    replaceFragment(AddFragment.newInstance(), "fc");
                    return true;
                case R.id.navigation_profile:
                    replaceFragment(ProfileFragment.newInstance(), "fk");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        if(Paper.book().contains("user")){
            u = Paper.book().read("user", new User());
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(DashBoardFragment.newInstance(), "fg");
    }

    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, newFragment, tag)
                .commit();

    }
}
