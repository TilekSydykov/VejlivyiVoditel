package kg.flaterlab.vv.ui.main;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.Window;


import io.paperdb.Paper;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.fragments.add.AddFragment;
import kg.flaterlab.vv.fragments.DashBoardFragment;
import kg.flaterlab.vv.fragments.profile.ProfileFragment;
import kg.flaterlab.vv.data.model.User;
import kg.flaterlab.vv.helper.DB;

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



        if(Paper.book().contains(DB.USER_NODE)){
            u = Paper.book().read(DB.USER_NODE, new User());
        }else{
            // if not logged in launch login activity
            // Intent intent = new Intent(this, LoginActivity.class);
            // startActivity(intent);
            Paper.book().write(DB.USER_NODE, new User("dqw", "TIlek", "1"));
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
