package ga.asfanulla.shadier.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ga.asfanulla.shadier.R;

public class VillageAdapter extends RecyclerView.Adapter<VillageAdapter.MyHolder> {
    ArrayList<String> arrayList;
    Context context;
    public VillageAdapter(Context context, ArrayList<String> arrayList) {
        super();
        this.arrayList=arrayList;
        this.context=context;
    }
    class MyHolder extends RecyclerView.ViewHolder
    { TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView.setText(arrayList.get(position));
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.text, parent, false);
        MyHolder myHolder=new MyHolder(v);
        return myHolder;
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
