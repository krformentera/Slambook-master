package com.example.slambook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {

    private ArrayList<Accounts> accountsArrayList;
    private Context mContext;
    private PersonListAdapter.OnClickListener listener;

    /**
     * Holds variables in a View
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPic;
        TextView accountName;
        Button deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewPic = (ImageView) itemView.findViewById(R.id.user_profile_pic);
            this.accountName = (TextView) itemView.findViewById(R.id.indi_account_name);
            this.deleteBtn = (Button) itemView.findViewById(R.id.logout_user);
            this.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.OnClickListener(position);
                    }
                }
            });
        }
    }

    public AccountListAdapter(Context context, ArrayList<Accounts> objects) {
        this.mContext = context;
        this.accountsArrayList = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_user_2, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Accounts accounts = this.accountsArrayList.get(position);
        if(accounts != null) {
            byte[] images = accounts.getByteUserPofilePic();
            Bitmap bitmapImages = BitmapFactory.decodeByteArray(images, 0, images.length);
            holder.imageViewPic.setImageBitmap(bitmapImages);
            holder.accountName.setText(accounts.getUsername());
        }

    }

    @Override
    public int getItemCount() {
        return this.accountsArrayList.size();
    }

    public ArrayList<Accounts> getItems() {
        return accountsArrayList;
    }

    public void setItems(ArrayList<Accounts> mItems) {
        this.accountsArrayList = mItems;
    }

    public void setOnClickListener(PersonListAdapter.OnClickListener listener) {
        this.listener = listener;
    }

}
