package com.example.gasolineprices;


import static android.content.Context.MODE_PRIVATE;

import static org.apache.xalan.xsltc.compiler.sym.error;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class PastPaymentAdapter extends RecyclerView.Adapter<PastPaymentAdapter.MyViewHolder> {

    private static MutableLiveData<Double> mutableLiveData=new MutableLiveData<>();;
    private Context mContext ;
    private List<List<cartItem>> mData ;
    private List<String> mCodes;

    public PastPaymentAdapter(Context mContext, List<List<cartItem>> mData,List<String> mCodes) {
        this.mContext = mContext;
        this.mData = mData;
        this.mCodes=mCodes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.pastpay_card,parent,false);
        MyViewHolder  asd=new MyViewHolder(view);
        return asd;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
holder.payment_details.setVisibility(View.GONE);
      String texte="";
      double totalPir=0;
        for (int i = 0; i < mData.get(position).size(); i++) {
            texte+=mData.get(position).get(i).City+" "+mData.get(position).get(i).Type+" "+mData.get(position).get(i).Brand+" "+mData.get(position).get(i).Count+" x "+mData.get(position).get(i).Price+" = "+mData.get(position).get(i).Price*mData.get(position).get(i).Count+"\n";
            totalPir+=mData.get(position).get(i).Price*mData.get(position).get(i).Count;
        }




        holder.payment_id.setText(mCodes.get(position));
        holder.payment_text.setText(texte);

        holder.payment_price.setText(totalPir+"");




            holder.clickLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.payment_details.getVisibility() == View.VISIBLE) {
                    holder.payment_details.setVisibility(View.GONE);
                    }
else
    holder.payment_details.setVisibility(View.VISIBLE);

                }
            });







        holder.payment_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.payment_details.setVisibility(View.GONE);
            }
        });





    }


//check
    @Override
    public int getItemCount() {
        return mData.size();
    }

    private List<String> getCode() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        DatabaseReference ref = database.getReference(firebaseUser.getEmail().split("@", 2)[0]).child("Payments");
        // calling add value event listener method
        // for getting the values from database.

        List<String> newList = null;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.


                for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                    newList.add(postSnapshot.getKey());




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return newList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView payment_id,payment_price,payment_text;
        CardView payment_card;
        LinearLayout payment_lay,clickLay,payment_details;
        public MyViewHolder(View itemView) {
            super(itemView);
            payment_lay=(LinearLayout)itemView.findViewById(R.id.payment_lay);
            clickLay=(LinearLayout)itemView.findViewById(R.id.clickLay);
            payment_details=(LinearLayout)itemView.findViewById(R.id.payment_details);

            payment_card = (CardView) itemView.findViewById(R.id.payment_card);




            payment_id = (TextView) itemView.findViewById(R.id.payment_id) ;
            payment_price = (TextView) itemView.findViewById(R.id.payment_price) ;
            payment_text = (TextView) itemView.findViewById(R.id.payment_text) ;






        }
    }


}


