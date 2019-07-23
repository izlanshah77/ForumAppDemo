package com.example.forumappdemo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private ArrayList<ChatMessage> messageArrayList;
    FirebaseAuth auth;

    public static class MessageViewHolder extends  RecyclerView.ViewHolder{

        public TextView tvUser, tvTime, tvMsg;
        public CardView msgCard;

        public MessageViewHolder(View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.message_user);
            tvTime = itemView.findViewById(R.id.message_time);
            tvMsg = itemView.findViewById(R.id.message_text);
            msgCard = itemView.findViewById(R.id.msgCard);

        }
    }

    public MessageAdapter(ArrayList<ChatMessage> messages){
        messageArrayList = messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item, viewGroup, false);
        MessageViewHolder mvh = new MessageViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        ChatMessage current = messageArrayList.get(i);
        messageViewHolder.tvUser.setText(current.getMessageUser());
        messageViewHolder.tvMsg.setText(current.getMessageText());
        messageViewHolder.tvTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", current.getMessageTime()));
        if (auth.getInstance().getCurrentUser().getEmail() == current.getMessageUser()){
            messageViewHolder.msgCard.setCardBackgroundColor(Color.parseColor("#809627"));
        }

    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }
}
