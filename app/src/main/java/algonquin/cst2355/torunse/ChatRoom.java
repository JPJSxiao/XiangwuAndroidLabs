package algonquin.cst2355.torunse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_chat_room);
        setContentView(binding.getRoot());
    }
}