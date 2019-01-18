package ga.asfanulla.shadier;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import android.support.v4.view.PagerAdapter;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

import ga.asfanulla.shadier.Frags.MP_FRAG;
import ga.asfanulla.shadier.Frags.MainFrag;
import ga.asfanulla.shadier.Frags.Prog_Frag;
import ga.asfanulla.shadier.Frags.insFrag;
import ga.asfanulla.shadier.Frags.vil_detFrag;
import ga.asfanulla.shadier.SubClass.SaveSharedPreference;

public class NfeedMain extends AppCompatActivity {
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    ArrayList<String> myList;
    ArrayList<String> myList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfeed_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Title");
        myList = (ArrayList<String>) getIntent().getSerializableExtra("vil");
        myList2 =  (ArrayList<String>) getIntent().getSerializableExtra("vnm");

       // Toast.makeText(this, myList+"-"+myList2, Toast.LENGTH_SHORT).show();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i=0; i<myList.size(); i++){
            adapter.addFrag(new MainFrag(myList.get(i)), myList2.get(i));
        }
        //adapter.addFrag(new MainFrag(), "Village 1");
        //adapter.addFrag(new MainFrag(), "Village 2");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
