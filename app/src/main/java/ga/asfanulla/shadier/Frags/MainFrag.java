package ga.asfanulla.shadier.Frags;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ga.asfanulla.shadier.Adapters.VillageAdapter;
import ga.asfanulla.shadier.NfeedMain;
import ga.asfanulla.shadier.R;
import ga.asfanulla.shadier.SubClass.SaveSharedPreference;

@SuppressLint("ValidFragment")
public class MainFrag extends Fragment {

    private NavigationTabStrip mCenterNavigationTabStrip;
    private ViewPager mViewPager;
    private ArrayList<String> update,url;
    private RecyclerView recyclerView;
    String name="";
    VillageAdapter villageAdapter;
    private static FragmentManager fragmentManager;
    private static View view;

    String vid;

    @SuppressLint("ValidFragment")
    public MainFrag(String vid){
      this.vid = vid;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.main_frag, container, false);
        update=new ArrayList<>();
        url=new ArrayList<>();
        //Toast.makeText(getContext(), vid, Toast.LENGTH_SHORT).show();
        getJson(vid);
        recyclerView=(RecyclerView)view.findViewById(R.id.qkup);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        villageAdapter=new VillageAdapter(getActivity(),update);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(villageAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        initUI();
        return view;
    }
    public void getJson(final String id)
    {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,this.getString(R.string.url_vill), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response).getJSONObject("VILDET");
                    name=obj.getString("VNAME");
                    JSONArray jsonArray=obj.getJSONArray("VILUPDATE");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject tmp=jsonArray.getJSONObject(i);
                        update.add("-> "+tmp.getString("DESCR"));
                        villageAdapter.notifyDataSetChanged();
                    }
                    JSONArray jsonArray1=obj.getJSONArray("IMGURL");
                    for (int i=0;i<jsonArray1.length();i++)
                    {
                        JSONObject tmp=jsonArray1.getJSONObject(i);
                        url.add(tmp.getString("PATH"));
                    }
                } catch (Exception e) {
                  //  Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<String, String>();
                params.put("VID",id);
                return params;
            }
        } ;
        queue.add(stringRequest);




    }
    private void initUI() {
        //fragmentManager = getActivity().getSupportFragmentManager();'
        mViewPager = (ViewPager) view.findViewById(R.id.vp);
        mCenterNavigationTabStrip = view.findViewById(R.id.nts_center);

        setupViewPager(mViewPager);
        mCenterNavigationTabStrip.setViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        MainFrag.ViewPagerAdapter adapter = new MainFrag.ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new Prog_Frag(vid));
        adapter.addFrag(new MP_FRAG(vid));
        adapter.addFrag(new insFrag(vid));
        //  adapter.addFrag(new vil_detFrag());
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }





}
