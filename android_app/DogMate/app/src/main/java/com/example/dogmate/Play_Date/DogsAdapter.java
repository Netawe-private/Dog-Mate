package com.example.dogmate.Play_Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dogmate.R;
import com.example.dogmate.model.Dog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//The adapter creates view holders as needed, and binds the view holders to their data
public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogsViewHolder> {

    private List<Dog> mDogs;
    private Context mContext;
    private DogsAdapterListener mListener;

    public interface DogsAdapterListener {
        void emailDogOwner(String ownerUserName);
    }

    public void setDogs(List<Dog> dogs) {
        mDogs = dogs;
        notifyDataSetChanged();
    }

    public DogsAdapter(Context context, DogsAdapterListener listener){
        mContext = context;
        mListener = listener;
    }

    // Create new views (invoked by the layout manager).
    // The views in the list are represented by view holder objects.
    // Each view holder is in charge of displaying a single item with a view
    @NonNull
    @Override
    public DogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.list_item_search_dog, parent, false);
        return new DogsViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager).
    // Get element from your dataset at this position + replace the contents of the view with that element
    @Override
    public void onBindViewHolder(@NonNull DogsViewHolder holder, int position) {
        Dog dog = mDogs.get(position);
        if(dog != null){
            Glide.with(mContext)
                    .load(dog.getImage())
                    .into(holder.mDogImage);
            holder.mName.setText(String.format("%s %s",
                    mContext.getString(R.string.dog_item_name), dog.getName()));
            holder.mCity.setText(String.format("%s %s",
                    mContext.getString(R.string.dog_item_city), dog.getCity()));
            holder.mNeighborhood.setText(String.format("%s %s",
                    mContext.getString(R.string.dog_item_neighborhood), dog.getNeighborhood()));
            holder.mSize.setText(String.format("%s %s",
                    mContext.getString(R.string.dog_item_size), dog.getSize()));
            holder.mBreed.setText(String.format("%s %s",
                    mContext.getString(R.string.dog_item_breed), dog.getBreed()));
            holder.mTemperament.setText(String.format("%s %s",
                    mContext.getString(R.string.dog_item_temperament), dog.getTemperament()));
            holder.mMessageOwner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.emailDogOwner(dog.getOwner());
                }
            });
        }
    }

    @Override
    public int getItemCount() {             // Return the size of your dataset (invoked by the layout manager)
        return mDogs == null ? 0 : mDogs.size();
    }

    class DogsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_dog) ImageView mDogImage;
        @BindView(R.id.tv_name) TextView mName;
        @BindView(R.id.tv_city) TextView mCity;
        @BindView(R.id.tv_neighborhood) TextView mNeighborhood;
        @BindView(R.id.tv_size) TextView mSize;
        @BindView(R.id.tv_breed) TextView mBreed;
        @BindView(R.id.tv_temperament) TextView mTemperament;
        @BindView(R.id.tv_message_owner) TextView mMessageOwner;

        DogsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
