package com.example.wafa.studentapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{



    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;

    FirebaseUser fuser ;

    public UserAdapter(Context mContext , List<Chat> mChat) {
        this.mContext = mContext;
        this.mChat = mChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new UserAdapter.ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new UserAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mChat.get(position);


        holder.show_messge.setText(chat.getMessage());


        holder.show_time.setText(chat.getDate());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView show_messge ;
        public TextView show_time ;


        public  ViewHolder(View itemView){
            super(itemView);

            show_messge = itemView.findViewById(R.id.show_message);

            show_time = itemView.findViewById(R.id.datechat);



        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();


        if(mChat.get(position).getSender().equals(fuser.getUid())){

            return MSG_TYPE_RIGHT;
        }
        else {
            return  MSG_TYPE_LEFT;
        }
    }
}
