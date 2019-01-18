package ga.asfanulla.shadier.Frags;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ga.asfanulla.shadier.Adapters.MPAdapter;
import ga.asfanulla.shadier.Adapters.VillageAdapter;
import ga.asfanulla.shadier.NfeedMain;
import ga.asfanulla.shadier.R;

public class MP_FRAG extends Fragment {

    private View view;
    private TextView designation,consti,state,desc,pnm;
    private ArrayList<String> update,pgs;
    private RecyclerView recyclerView,recyclerView1;;
    String name="";
    MPAdapter mpAdapter,pgadapter;
    private String vid;
    @SuppressLint("ValidFragment")
    public MP_FRAG(String vid) {
       this.vid = vid;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mp_frg, container, false);
        designation=(TextView)view.findViewById(R.id.designation);
        consti=(TextView)view.findViewById(R.id.consti);
        state=(TextView)view.findViewById(R.id.state);
        desc=(TextView)view.findViewById(R.id.desc);
        pnm=(TextView)view.findViewById(R.id.pnm);
        recyclerView=(RecyclerView)view.findViewById(R.id.qkup);
        recyclerView1=(RecyclerView)view.findViewById(R.id.prgLi);
        update=new ArrayList<>();
        pgs=new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager mLayoutManager1= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView1.setLayoutManager(mLayoutManager1);
        pgadapter=new MPAdapter(getActivity(),pgs);
        mpAdapter=new MPAdapter(getActivity(),update);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(pgadapter);
        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mpAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,this.getString(R.string.url_mp), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response).getJSONObject("MPDET");
                    JSONArray t=obj.getJSONArray("MPUPDATE");
                    update.clear();
                    for(int i=0;i<t.length();i++)
                    {
                        JSONObject tmp=t.getJSONObject(i);
                        update.add("-> "+tmp.getString("DESCR"));
                        mpAdapter.notifyDataSetChanged();
                    }
                    JSONArray t1=obj.getJSONArray("MPPROG");
                    pgs.clear();
                    for(int i=0;i<t.length();i++)
                    {
                        JSONObject tmp=t1.getJSONObject(i);
                        pgs.add("-> "+tmp.getString("PNAME"));
                        pgadapter.notifyDataSetChanged();
                    }
                    if(!obj.isNull("MPDESIG"))
                        designation.setText(obj.getString("MPDESIG"));
                    if(!obj.isNull("MPSTATE"))
                        state.setText(obj.getString("MPSTATE"));
                    if(!obj.isNull("MPDESCR"))
                        desc.setText(obj.getString("MPDESCR"));
                    if(!obj.isNull("MPDESCR"))
                        consti.setText(obj.getString("MPCONSTI"));
                    if(!obj.isNull("MPNAME"))
                        pnm.setText(obj.getString("MPNAME"));

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
                //params.put("VID",id);
                return params;
            }
        } ;
        queue.add(stringRequest);

        return view;
    }

}
