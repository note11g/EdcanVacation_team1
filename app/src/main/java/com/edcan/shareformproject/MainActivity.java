package com.edcan.shareformproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.edcan.shareformproject.databinding.ActivityMainBinding;
import com.edcan.shareformproject.fragment.ExploreFragment;
import com.edcan.shareformproject.fragment.HomeFragment;
import com.edcan.shareformproject.fragment.MoreFragment;
import com.edcan.shareformproject.fragment.NowShareFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottomMain.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    switchFragment(HomeFragment.newInstance());
                    break;
                case R.id.explore:
                    switchFragment(ExploreFragment.newInstance());
                    break;
                case R.id.nowshare:
                    switchFragment(NowShareFragment.newInstance());
                    break;
                case R.id.more:
                    switchFragment(MoreFragment.newInstance());
                    break;
            }
            return true;
        });
        switchFragment(HomeFragment.newInstance());
    }

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main, fragment);
        transaction.commit();
    }
}