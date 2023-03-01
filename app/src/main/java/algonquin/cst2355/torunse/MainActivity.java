package algonquin.cst2355.torunse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2355.torunse.data.ChatMessage;
import algonquin.cst2355.torunse.data.ChatViewModel;
import algonquin.cst2355.torunse.databinding.ActivityMainBinding;
import algonquin.cst2355.torunse.databinding.RowLayout2Binding;
import algonquin.cst2355.torunse.databinding.RowLayoutBinding;

public class MainActivity extends AppCompatActivity {
//    private ArrayList<String> messageList;
    private ArrayList<ChatMessage> messageList;

    private RecyclerView.Adapter myAdapter ;

    @Override//this starts teh application
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ChatViewModel cvm  = new ViewModelProvider(this).get(ChatViewModel.class);
        messageList = cvm.messages;


        binding.theRecycleView.setAdapter(new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                if (viewType == 0) {
                    RowLayoutBinding binding = RowLayoutBinding.inflate(getLayoutInflater(), parent, false);
                    View root = binding.getRoot();
                    return new MyRowHolder(root);
                } else {
                    RowLayout2Binding binding = RowLayout2Binding.inflate(getLayoutInflater(),
                            parent, false

                    );
                    View root = binding.getRoot();
                    return new MyRowHolder(root);

                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
 //               String messageOnThisRow = messageList.get(position);//which string goes in this row?

                ChatMessage messageOnThisRow = messageList.get(position);
                holder.messageText.setText(messageOnThisRow.getMessage());

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
                String currentDateandTime = sdf.format(new Date());
                holder.timeText.setText(currentDateandTime);
            }


            @Override
            public int getItemCount() {
                return messageList.size();
            }
            public int getItemViewType(int position) {
                //you can return anything to represent a layout
                return position % 2;
            }

        });
        binding.theRecycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sendButton.setOnClickListener(clk ->{
            String txt = binding.editText.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String t = sdf.format(new Date());
            boolean sent = true;

            messageList.add(new ChatMessage(txt,t,sent));

            binding.editText.setText("");
        });

        binding.ReceiveButton.setOnClickListener(clk ->{
            String txt = binding.editText.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String t = sdf.format(new Date());
            boolean sent = false;

            messageList.add(new ChatMessage(txt,t,sent));

            binding.editText.setText("");
        });

    }

    class MyRowHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public TextView timeText;
        public MyRowHolder(@NonNull View itemView) { //itemView will the the root of the layout, ConstraintLayout
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}