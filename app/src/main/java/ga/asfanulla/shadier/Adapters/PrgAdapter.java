package ga.asfanulla.shadier.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ga.asfanulla.shadier.R;

public class PrgAdapter extends RecyclerView.Adapter<PrgAdapter.MyViewHolder>{

    private Context context;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private String key;

     private ArrayList<HashMap<String ,String>> itemList;
     private ArrayList<HashMap<String,String>> itmli2;

    public PrgAdapter(Context context, String key, ArrayList<HashMap<String ,String >> itemList, ArrayList<HashMap<String,String>> itmli2){
        super();
        this.context = context;
        this.key = key;
        this.itemList = itemList;
        this.itmli2 = itmli2;
        //this.plist = plist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView pnm, mpnm, mnmra, inm, inmra, pdesc, compbut, initdate, estdt, ti1, ti2, licont, comcunt;
        private ImageView head_img, mpavt, insavt;
        private TextView qup;

        public MyViewHolder(View view) {
            super(view);

            if(key.equals("pr")) {

            pnm = view.findViewById(R.id.pnm);
            head_img = view.findViewById(R.id.head_image);
            qup = view.findViewById(R.id.qkup);
            mpavt = view.findViewById(R.id.content_avatar2);
            mpnm = view.findViewById(R.id.content_name_view2);
           // mnmra = view.findViewById(R.id.nmra2);
            insavt = view.findViewById(R.id.content_avatar);
            inm = view.findViewById(R.id.content_name_view);
            //inmra = view.findViewById(R.id.nmra);
            pdesc = (TextView) view.findViewById(R.id.des);
            compbut = view.findViewById(R.id.content_request_btn);
            initdate = view.findViewById(R.id.init_date);
            estdt = view.findViewById(R.id.title_time_label);
            ti1 = view.findViewById(R.id.title_from_address);
            ti2 = view.findViewById(R.id.title_to_address);
            licont = view.findViewById(R.id.title_requests_count);
            comcunt = view.findViewById(R.id.title_pledge);

            view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((FoldingCell) view).toggle(false);
                        registerToggle(getAdapterPosition());
                    }
                });
            }

        }
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    @NonNull
    @Override
    public PrgAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if(key.equals("pr")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pr_crd, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ins_card, parent, false);
        }
        return new  MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrgAdapter.MyViewHolder holder, int position) {
        if(key.equals("pr")){
            HashMap<String,String> tt1 = itemList.get(position);
            HashMap<String ,String > tt2 = itmli2.get(position);
            holder.pnm.setText(tt1.get("pnm"));
            holder.mpnm.setText(tt1.get("mpnm"));
            holder.inm.setText(tt1.get("insmm"));
            holder.pdesc.setText(tt1.get("pdes"));
            holder.initdate.setText(tt1.get("pstrdt"));
            holder.estdt.setText(tt1.get("pexdt"));
            holder.ti1.setText(tt1.get("pnm"));
            holder.licont.setText(tt1.get("plikes"));
            holder.comcunt.setText(tt1.get("pcomm"));

            holder.qup.setText(tt2.get("pupd"));

            Picasso.with(context)
                    .load(context.getString(R.string.imgurl)+tt1.get("pimg"))
                    .fit()
                    .centerCrop()
                    .into(holder.head_img);

        }
    }

    @Override
    public int getItemCount() {
        if(key.equals("pr")) {
            return itemList.size();
        } else {
            return 1;
        }
    }
}
