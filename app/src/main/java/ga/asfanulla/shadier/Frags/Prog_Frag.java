package ga.asfanulla.shadier.Frags;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ga.asfanulla.shadier.Adapters.PrgAdapter;
import ga.asfanulla.shadier.R;
import ga.asfanulla.shadier.SubClass.SaveSharedPreference;

public class Prog_Frag extends Fragment{

    private View view;
    private PrgAdapter mAdapter;
    private RecyclerView recyclerView;

    private ArrayList<HashMap<String ,String >> itemList;
    private ArrayList<HashMap<String,String >> itmlst2;

    private String vid;

    @SuppressLint("ValidFragment")
    public Prog_Frag(String vid) {
        this.vid = vid;
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
        init();

        return view;
    }

    private void init(){
        itemList = new ArrayList<>();
        itmlst2 = new ArrayList<>();
        getData2(vid);
        mAdapter = new PrgAdapter(getContext(),"pr",itemList, itmlst2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }


    public void getData2(final String Id){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = "http://ec2-35-154-222-185.ap-south-1.compute.amazonaws.com/sagy/newProgram.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getContext(), "loaded", Toast.LENGTH_SHORT).show();
                       showJSON(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Err",Toast.LENGTH_LONG).show();
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("VID", Id);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void showJSON(String response) {

        String pid = "";
        String pnm = "";
        String vid = "";
        String vnm = "";
        String pstrdt = "";
        String pexpdt = "";
        String mpid = "";
        String mpnm = "";
        String insid = "";
        String insnm = "";
        String pdes = "";
        String plikes="";
        String pcomcount = "";
        String pimg = "";
        String pupd = "";
        String pupid = "";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray re = jsonObject.getJSONArray("PDET");

            itemList.clear();
            itmlst2.clear();

            for (int i = 0; i < re.length(); i++) {
                JSONObject admin = re.getJSONObject(i);
                pid = admin.getString("PID");
                pnm = admin.getString("PNAME");
                vid = admin.getString("VID");
                vnm = admin.getString("VNAME");
                pstrdt = admin.getString("PSTARTDATE");
                pexpdt = admin.getString("PEXPENDDATE");
                mpid = admin.getString("MPID");
                mpnm = admin.getString("MPNAME");
                insid = admin.getString("INSID");
                insnm = admin.getString("INSTNAME");
                pdes = admin.getString("PROGDESC");
                plikes = admin.getString("PLIKES");
                pcomcount = admin.getString("PCOMNTCOUNT");
                pimg = admin.getString("PROJIMAGE");

                HashMap<String, String> temp = new HashMap<String, String>();
                temp.put("pid", pid);
                temp.put("pnm",pnm);
                temp.put("vid", vid);
                temp.put("vnm", vnm);
                temp.put("pstrdt", pstrdt);
                temp.put("pexdt", pexpdt);
                temp.put("mpid", mpid);
                temp.put("mpnm", mpnm);
                temp.put("insid",insid);
                temp.put("insmm", insnm);
                temp.put("pdes", pdes);
                temp.put("plikes", plikes);
                temp.put("pcomm", pcomcount);
                temp.put("pimg", pimg);

                JSONArray tt = admin.getJSONArray("PUPDATES");
                for (int j=0; j<tt.length(); j++){
                    JSONObject ad = tt.getJSONObject(j);
                    pupd = ad.getString("DESCR");
                    pupid = ad.getString("PID2");
                    HashMap<String ,String> tm2 = new HashMap<>();
                    tm2.put("pupd",pupd);
                    tm2.put("pupid",pupid);
                    itmlst2.add(tm2);
                   // mAdapter.notifyDataSetChanged();
                }

                itemList.add(temp);
                mAdapter.notifyDataSetChanged();

            }



        } catch (Exception e) {
            //Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

}
