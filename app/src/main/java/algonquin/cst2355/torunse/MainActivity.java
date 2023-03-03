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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}