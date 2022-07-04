package com.example.gasolineprices;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<FuelClass> mData ;
    boolean asd=false;
 List<cartItem> cartList;
    public RecyclerViewAdapter(Context mContext, List<FuelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
loadData();
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.fuel_list,parent,false);
        LinearLayout a=(LinearLayout)view.findViewById(R.id.buyLay);
        a.setVisibility(View.INVISIBLE);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.Linear.setVisibility(View.GONE);
        holder.city_name.setText(mData.get(position).getCity());
        holder.fuel_type.setText(mData.get(position).getType());
        holder.brand.setText(mData.get(position).getBrand());
        holder.fuel_price.setText( mData.get(position).getPrice()+"");



        holder.fuel_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(holder.Linear.getVisibility()==View.VISIBLE){

    holder.Linear.setVisibility(View.GONE);
}else
  holder.Linear.setVisibility(View.VISIBLE);



                if(holder.esCount.getText().toString().equals("1")){
                    holder.decButton.setVisibility(View.GONE);
                }



            }
        });

                holder.cardView.findViewById(R.id.buyLay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        holder.Linear.setVisibility(View.GONE);

                    }
                });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.Linear.setVisibility(View.GONE);
            }
        });

        holder.Linear.findViewById(R.id.incButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                holder.esCount.setText(String.valueOf((Integer.parseInt(holder.esCount.getText().toString())+1+"")));
                                holder.esPrice.setText(String.valueOf(((int) mData.get(position).getPrice())*Integer.parseInt(holder.esCount.getText().toString())));
                                holder.decButton.setVisibility(View.VISIBLE);





                            }
                        });
        holder.Linear.findViewById(R.id.decButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                holder.esCount.setText(String.valueOf((Integer.parseInt(holder.esCount.getText().toString())-1)));
                                holder.esPrice.setText(String.valueOf(((int) mData.get(position).getPrice())*Integer.parseInt(holder.esCount.getText().toString())));
                                if(holder.esCount.getText().toString().equals("1")){
                                    holder.decButton.setVisibility(View.GONE);
                                }





                            }
                        });

        holder.Linear.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                holder.Linear.setVisibility(View.GONE);

                                /*
                                * SEPETE EKLE
                                *
                                *
                                * */
cartItem item=new cartItem(holder.city_name.getText().toString(),holder.fuel_type.getText().toString(),holder.brand.getText().toString(),Double. valueOf(holder.fuel_price.getText().toString()),Integer. valueOf(holder.esCount.getText().toString()));
cartList.add(item);
saveData();
                                Toast.makeText(mContext, "Item added to cart", Toast.LENGTH_LONG).show();
                            }
                        });









    }

    private void getdata() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        DatabaseReference ref = database.getReference(firebaseUser.getEmail().split("@", 2)[0]).child("Cart").child("0");
        // calling add value event listener method
        // for getting the values from database.
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
                    FuelClass value = snapshot.getValue(FuelClass.class);
                    mData.add(value);}
                // after getting the value we are setting
                // our value to our text view in below line.

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}


    private void saveData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartList);
        editor.putString("task list", json);
        editor.apply();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(firebaseUser.getEmail().split("@", 2)[0]).child("Cart");




        ref.setValue(cartList);


    }

    private void loadData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<cartItem>>() {}.getType();
        cartList = gson.fromJson(json, type);

        if (cartList == null) {
            cartList = new ArrayList<>();
            getdata();
        }
      //
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button incButton,decButton;
        TextView city_name;
        TextView fuel_type;
        TextView brand;
        TextView fuel_price;
        TextView esPrice;
        TextView esCount;
        CardView cardView ;
        LinearLayout Linear,fuel_list;
        public MyViewHolder(View itemView) {
            super(itemView);
            Linear=(LinearLayout)itemView.findViewById(R.id.buyLay);
            fuel_list=(LinearLayout)itemView.findViewById(R.id.fuel_list);

            cardView = (CardView) itemView.findViewById(R.id.fuel_card);

            incButton=(Button)Linear.findViewById(R.id.incButton);
            decButton=(Button)Linear.findViewById(R.id.decButton);



            city_name = (TextView) itemView.findViewById(R.id.city_name) ;
            fuel_type = (TextView) itemView.findViewById(R.id.fuel_type) ;
            brand = (TextView) itemView.findViewById(R.id.brand) ;
            fuel_price = (TextView) itemView.findViewById(R.id.fuel_price) ;

            esPrice=(TextView) Linear.findViewById(R.id.esPrice) ;

            esCount=(TextView) Linear.findViewById(R.id.esCount) ;






        }
    }


}