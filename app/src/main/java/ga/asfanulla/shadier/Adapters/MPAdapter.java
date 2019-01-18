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

public class MPAdapter extends RecyclerView.Adapter<MPAdapter.MyHolder> {
    ArrayList<String> arrayList;
    Context context;
    public MPAdapter(Context context, ArrayList<String> arrayList) {
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
    public void onBindViewHolder(@NonNull MPAdapter.MyHolder holder, int position) {
        holder.textView.setText(arrayList.get(position));
    }

    @NonNull
    @Override
    public MPAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.text, parent, false);
        MPAdapter.MyHolder myHolder=new MPAdapter.MyHolder(v);
        return myHolder;
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
