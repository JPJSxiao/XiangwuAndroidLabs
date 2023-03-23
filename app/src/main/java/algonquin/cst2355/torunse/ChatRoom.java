package algonquin.cst2355.torunse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2355.torunse.data.ChatMessage;
import algonquin.cst2355.torunse.data.ChatMessageDao;
import algonquin.cst2355.torunse.data.ChatViewModel;
import algonquin.cst2355.torunse.data.MessageDatabase;
import algonquin.cst2355.torunse.databinding.ActivityChatRoomBinding;
import algonquin.cst2355.torunse.databinding.ActivityMainBinding;
import algonquin.cst2355.torunse.databinding.RowLayout2Binding;
import algonquin.cst2355.torunse.databinding.RowLayoutBinding;

public class ChatRoom extends AppCompatActivity {
    private ArrayList<ChatMessage> messageList;

    private RecyclerView.Adapter myAdapter ;

    ChatMessageDao mDao;

    ChatViewModel cvm ;
    MessageDetailsFragment prevFragment;
    int position;
    public TextView messageText;
    public TextView timeText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.item_1:

                ChatMessage clickedMessage = cvm.selectedMessage.getValue();
               // int position = getAbsoluteAdapterPosition();

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                    builder.setMessage("Do you want to delete this message?");
                    builder.setTitle("Warning!!");
                builder.setPositiveButton("OK", (dialog, which)->{
                Executor thread_1 = Executors.newSingleThreadExecutor();
                thread_1.execute(()->{

                        mDao.deleteMessage(clickedMessage);
                        messageList.remove(position);
                        runOnUiThread(()->{
                            myAdapter.notifyItemRemoved(position);
                            Snackbar.make(messageText, "Item deleted", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", clk ->{
                                        Executor thread_2 = Executors.newSingleThreadExecutor();
                                        thread_2.execute(()->{

                                                    mDao.insertMessage(clickedMessage);
                                                    messageList.add(position,clickedMessage);
                                                    runOnUiThread(()->{
                                                        myAdapter.notifyItemInserted(position);
                                                    });


                                    });
                                    })
                                    .show();

                        }
                        );

                    });
                });
                    builder.setNegativeButton("Cancel", (dialog, which)->{

                    });
                    builder.create().show();
                //int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messageList.get(position);
                // ChatViewModel cvm  = new ViewModelProvider(this).get(ChatViewModel.class);
                cvm.selectedMessage.postValue(selected);
                break;

            case R.id.item_2:{

                Snackbar.make(messageText, "Version 1.0, created by Xiangwu Dai", Snackbar.LENGTH_LONG);
                break;
            }

        }


        return true;
        //return super.onOptionsItemSelected(item);
    }

    @Override//this starts teh application
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get database
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDao = db.cmDAO();

        ActivityChatRoomBinding binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.myToolbar);


        cvm  = new ViewModelProvider(this).get(ChatViewModel.class);
        //messageList = cvm.messages; //survives rotation changes
         messageList = cvm.messages.getValue();

         cvm.selectedMessage.observe(this,(newValue) -> {

            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newValue);
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();

            if (prevFragment != null){
                tx.remove(prevFragment);
            }

            tx.add(R.id.fragmentLocation, chatFragment);
            prevFragment = chatFragment;
            tx.commit();
            tx.addToBackStack("");
             

        });

//        if(messageList == null)
//        {
            cvm.messages.postValue( messageList = new ArrayList<ChatMessage>());
//        }

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
                {
                    // first load old message on the second thread
                    List<ChatMessage> previousMessage = mDao.getAllMessages(); //select * from ChatMessage
                    messageList.addAll(previousMessage); //add everything from the database

                    runOnUiThread(() -> {
                        //this is on chatRoom thread
                    binding.theRecycleView.setAdapter(myAdapter);//
                });
                });
        //only call this after loading all the messages
         myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                if (viewType == 0) {
                    RowLayout2Binding binding = RowLayout2Binding.inflate(getLayoutInflater(), parent, false);
                    View root = binding.getRoot();
                    return new MyRowHolder(root);
                } else {
                    RowLayoutBinding binding = RowLayoutBinding.inflate(getLayoutInflater(),
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

                //   SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
                //  String currentDateandTime = sdf.format(new Date());
                holder.timeText.setText(messageOnThisRow.getTimeSent());
            }
            @Override
            public int getItemCount() {
                return messageList.size();
            }
            public int getItemViewType(int position) {
                //you can return anything to represent a layout'
                if (messageList.get(position).isSentButton()){
                    return 0;
                }
                else
                    return 1;
            }
        };
        binding.theRecycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sendButton.setOnClickListener(clk ->{
            String txt = binding.editText.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
            String t = sdf.format(new Date());
            boolean sent = true;
            //messageList.add(new ChatMessage(txt,t,sent));
            ChatMessage newMessage = new ChatMessage(txt, t, sent);
            messageList.add(newMessage);

            //no more crashes
            Executor thread_1 = Executors.newSingleThreadExecutor();
            thread_1.execute(()->{
                //insert into database
                long id = mDao.insertMessage(newMessage);
                newMessage.id = id;
            });
            binding.editText.setText("");
        });

        binding.ReceiveButton.setOnClickListener(clk ->{
            String txt = binding.editText.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
            String t = sdf.format(new Date());
            boolean sent = false;
            //messageList.add(new ChatMessage(txt,t,sent));
            ChatMessage newMessage = new ChatMessage(txt, t, sent);
            messageList.add(newMessage);

            //no more crashes
            Executor thread_1 = Executors.newSingleThreadExecutor();
            thread_1.execute(()->{
                //insert into database
                long id = mDao.insertMessage(newMessage);
                newMessage.id = id;
            });
            binding.editText.setText("");
        });

    }

    class MyRowHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public TextView timeText;
        public MyRowHolder(@NonNull View itemView) { //itemView will the the root of the layout, ConstraintLayout
            super(itemView);

            itemView.setOnClickListener(click->{
                /*
               int position = getAbsoluteAdapterPosition();//which row was clicked

                ChatMessage clickedMessage = messageList.get(position);



                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                    builder.setMessage("Do you want to delete this message?");
                    builder.setTitle("Warning!!");
                builder.setPositiveButton("OK", (dialog, which)->{
                Executor thread_1 = Executors.newSingleThreadExecutor();
                thread_1.execute(()->{

                        mDao.deleteMessage(clickedMessage);
                        messageList.remove(position);
                        runOnUiThread(()->{
                            myAdapter.notifyItemRemoved(position);
                            Snackbar.make(messageText, "Item deleted", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", clk ->{
                                        Executor thread_2 = Executors.newSingleThreadExecutor();
                                        thread_2.execute(()->{

                                                    mDao.insertMessage(clickedMessage);
                                                    messageList.add(position,clickedMessage);
                                                    runOnUiThread(()->{
                                                        myAdapter.notifyItemInserted(position);
                                                    });


                                    });
                                    })
                                    .show();

                        });

                    });
                });
                    builder.setNegativeButton("Cancel", (dialog, which)->{

                    });
                    builder.create().show();*/
                position = getAbsoluteAdapterPosition();
                ChatMessage selected = messageList.get(position);
               // ChatViewModel cvm  = new ViewModelProvider(this).get(ChatViewModel.class);
               cvm.selectedMessage.postValue(selected);
            });
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}