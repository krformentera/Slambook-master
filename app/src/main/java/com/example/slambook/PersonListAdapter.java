package com.example.slambook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder> {

    private ArrayList<Person> persons;
    private Context mContext;
    private OnClickListener listener1;
    private OnClickListener listener2;


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }


    /**
     * Holds variables in a View
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView name;
        TextView remark;
        Button deleteBtn;
        Button editBtn;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            this.profilePic = (ImageView) itemView.findViewById(R.id.profile_person);
            this.name = (TextView) itemView.findViewById(R.id.indiviName);
            this.remark = (TextView) itemView.findViewById(R.id.indiviRemark);
            this.deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
            this.editBtn = (Button) itemView.findViewById(R.id.editBtn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            this.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener1 != null){
                        listener1.OnClickListener(position);
                    }
                }
            });

            this.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener2 != null){
                        if (position != RecyclerView.NO_POSITION) {
                            listener2.OnClickListener(position);
                        }
                    }
                }
            });

        }
    }

    public PersonListAdapter(Context context, ArrayList<Person> objects) {
        this.mContext = context;
        this.persons = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_entry_1, parent, false);
        ViewHolder vh = new ViewHolder(v, onItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person person = persons.get(position);
        if(person != null) {
            byte[] images = person.getByteProfilePic();
            Bitmap bitmapImages = BitmapFactory.decodeByteArray(images, 0, images.length);
            holder.profilePic.setImageBitmap(bitmapImages);
            holder.name.setText(person.getFn());
            holder.remark.setText(person.getRemark());
        }
    }// end fo curly braces onBindViewHolder

    @Override
    public int getItemCount() {
        return this.persons.size();
    }


    public interface OnClickListener {
        void OnClickListener(int position);
    }

    public void setOnClickListener(OnClickListener listener1) {
        this.listener1 = listener1;
    }
    public void setOnClickListener2(OnClickListener listener2) {
        this.listener2 = listener2;
    }

}
