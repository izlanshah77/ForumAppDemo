package com.example.forumappdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    EditText etMessage;
    FloatingActionButton fabSend;
    RecyclerView rvMessages;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth auth;
    ArrayList<ChatMessage> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        etMessage = findViewById(R.id.etMessage);
        fabSend = findViewById(R.id.fab);

        messages = new ArrayList<ChatMessage>();
        rvMessages = findViewById(R.id.list_of_messages);
        layoutManager = new LinearLayoutManager(this);
        rvAdapter = new MessageAdapter(messages);
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setAdapter(rvAdapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMessage msg = dataSnapshot.getValue(ChatMessage.class);
                messages.add(msg);
                rvAdapter.notifyDataSetChanged();
                rvMessages.scrollToPosition(messages.size() -1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etMessage.getText() != null && etMessage.getText().toString() != ""){
                    FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(etMessage.getText().toString(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                    etMessage.setText("");
                    rvAdapter.notifyDataSetChanged();
                }

            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out){
            auth.getInstance().signOut();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}
