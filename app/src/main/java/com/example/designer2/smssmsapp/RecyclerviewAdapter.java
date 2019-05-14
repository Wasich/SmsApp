package com.example.designer2.smssmsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {
    static List<DBModel> dbList;
    private ArrayList<DBModel> mArrayList;
    static Context context;
    RecyclerView mrecyclerview;

     public RecyclerviewAdapter(Context context, List<DBModel> dbList , RecyclerView recyclerView){
        this.dbList = new ArrayList<DBModel>();
        this.context = context;
        this.dbList = dbList;
        this.mArrayList = (ArrayList<DBModel>) dbList;
        this.mrecyclerview = recyclerView;

    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.mobile.setText(dbList.get(i).getMobile());
        myViewHolder.sms.setText(dbList.get(i).getMessage());
//


    }

    @Override
    public int getItemCount() {
        return dbList.size();

    }

    public DBModel getItem(int position) {
        return dbList.get(position);
    }

    //@Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    dbList = mArrayList;
                } else {

                    ArrayList<DBModel> filteredList = new ArrayList<>();

                    for (DBModel dbModel : mArrayList) {

                        if (dbModel.getMessage().toLowerCase().contains(charString)) {

                            filteredList.add(dbModel);
                        }
                    }

                    dbList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dbList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dbList = (ArrayList<DBModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
//    public void setfilter(List<DBModel> filtermodel) {
//        dbList=new ArrayList<>();
//        dbList.addAll(filtermodel);
//        notifyDataSetChanged();
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mobile, sms;
        CardView container;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mobile = itemView.findViewById(R.id.txtmobile);
            sms = itemView.findViewById(R.id.txtsms);
            container = itemView.findViewById(R.id.cardview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    view = LayoutInflater.from(context).inflate(R.layout.view_detail,null);
                    TextView number = view.findViewById(R.id.number_detail);
                    TextView sms  =  view.findViewById(R.id.sms_detail);
                    number.setText(dbList.get(position).getMobile());
                    sms.setText(dbList.get(position).getMessage());

                    builder.setView(view);
                    AlertDialog alertDialog  = builder.create();
                    alertDialog.show();


                }
            });


      itemView.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
              final int position = getAdapterPosition();
              AlertDialog.Builder builder = new AlertDialog.Builder(context);
              builder.setTitle("Choose option");
              builder.setMessage("ARE YOU sure you want to delete");
              builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      DatabaseHelper databaseHelper = new DatabaseHelper(context);
                      databaseHelper.deleteARow(dbList.get(position).getMessage(),context);
                      dbList.remove(position);
                      mrecyclerview.removeViewAt(position);
                      notifyItemRemoved(position);
                      notifyItemRangeChanged(position,dbList.size());
                      notifyDataSetChanged();



                  }
              });
              builder.create().show();
              return false;
          }
      });

        }

    }
}
