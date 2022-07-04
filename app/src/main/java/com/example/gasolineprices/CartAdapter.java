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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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



public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private static MutableLiveData<Double> mutableLiveData=new MutableLiveData<>();;
    private Context mContext ;
    private List<cartItem> mData ;
    boolean asd=false;
    ICart mListener;
    List<cartItem> cartList;
    public CartAdapter(Context mContext, List<cartItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        loadData();
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cart_item,parent,false);
        MyViewHolder  asd=new MyViewHolder(view);
        return asd;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.city_name.setText(mData.get(position).getCity());
        holder.fuel_type.setText(mData.get(position).getType());
        holder.brand.setText(mData.get(position).getBrand());
        holder.fuel_price.setText( mData.get(position).getPrice()+"");
        holder.buyCount.setText(mData.get(position).Count+"");
        holder.buyPrice.setText((mData.get(position).Count*mData.get(position).Price)+"");
        double para=0;
        for (int i = 0; i < cartList.size(); i++) {
            para+=(cartList.get(i).Price*cartList.get(i).Count);
        }

        mutableLiveData.postValue(para);




        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartList.remove(cartList.get(position));
                saveData();
                loadData();
                notifyDataSetChanged();
                double para=0;
                for (int i = 0; i < cartList.size(); i++) {
                    para+=(cartList.get(i).Price*cartList.get(i).Count);
                }

                mutableLiveData.postValue(para);

            }
        });

    }
    public static MutableLiveData<Double> getLiveData() {
        return mutableLiveData;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartList);
        editor.putString("task list", json);
        editor.apply();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        DatabaseReference ref = database.getReference(firebaseUser.getEmail().split("@", 2)[0]).child("Cart");
        ref.setValue(cartList);

    }
    private void getdata() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        DatabaseReference ref = database.getReference(firebaseUser.getEmail().split("@", 2)[0]).child("Cart");
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
                    cartItem value = snapshot.getValue(cartItem.class);
                    mData.add(value);}
                // after getting the value we are setting
                // our value to our text view in below line.

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}
    private void loadData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<cartItem>>() {}.getType();
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        cartList = gson.fromJson(json, type);
        mData=cartList;

        //getdata();
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ICart {
        void update(List<cartItem> cartList);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button remove;
        TextView city_name;
        TextView fuel_type;
        TextView brand;
        TextView fuel_price;
        TextView buyPrice;
        TextView buyCount;
        CardView cardView;
        LinearLayout Linear,fuel_list;
        public MyViewHolder(View itemView) {
            super(itemView);
            Linear=(LinearLayout)itemView.findViewById(R.id.remLay);
            fuel_list=(LinearLayout)itemView.findViewById(R.id.cart_fuel_list);

            cardView = (CardView) itemView.findViewById(R.id.cart_fuel_card);

            remove=(Button)Linear.findViewById(R.id.delButton);



            city_name = (TextView) itemView.findViewById(R.id.cart_city_name) ;
            fuel_type = (TextView) itemView.findViewById(R.id.cart_fuel_type) ;
            brand = (TextView) itemView.findViewById(R.id.cart_brand) ;
            fuel_price = (TextView) itemView.findViewById(R.id.cart_fuel_price) ;

            buyPrice=(TextView) Linear.findViewById(R.id.buyPrice) ;

            buyCount=(TextView) Linear.findViewById(R.id.buyCount) ;






        }
    }


}


