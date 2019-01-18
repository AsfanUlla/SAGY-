package ga.asfanulla.shadier.Frags;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ga.asfanulla.shadier.Adapters.PrgAdapter;
import ga.asfanulla.shadier.R;

@SuppressLint("ValidFragment")
public class insFrag extends Fragment {

    private View view;
    private PrgAdapter mAdapter;
    private RecyclerView recyclerView;
    private TextView villages,web,phone,desc;
    private ArrayList<String> update,prgs;

    @SuppressLint("ValidFragment")
    public insFrag(String vid) {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pr_frag, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        villages=(TextView)view.findViewById(R.id.villages);
        web=(TextView)view.findViewById(R.id.website);
        phone=(TextView)view.findViewById(R.id.contact);
        desc=(TextView)view.findViewById(R.id.desc);
        update=new ArrayList<>();
        prgs=new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,this.getString(R.string.url_ins), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray=obj.getJSONArray("INSDET");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject tmp=jsonArray.getJSONObject(i);
                        web.setText(tmp.getString("INSWEBSITE"));
                        phone.setText(tmp.getString("INSCONTACT"));
                        desc.setText(tmp.getString("INSDES"));
                        JSONArray tt=tmp.getJSONArray("INSUPDATION");
                    }
                } catch (Exception e) {
                  //  Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
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
        init();
        return view;
    }


    private void init(){
      final  ArrayList<HashMap<String ,String >> itemList = new ArrayList<>();
       final ArrayList<HashMap<String,String>> itmli2 = new ArrayList<>();
        mAdapter = new PrgAdapter(getContext(),"ins", itemList, itmli2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
