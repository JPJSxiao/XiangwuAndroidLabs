package algonquin.cst2355.torunse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2355.torunse.data.ChatMessage;
import algonquin.cst2355.torunse.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {

    ChatMessage selected;

    public MessageDetailsFragment(ChatMessage m){
        selected = m;
    }


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.messageText.setText(selected.getMessage());
        binding.timeText.setText(selected.getTimeSent());
        if (selected.isSentButton()){
            binding.typeText.setText("Send");
        }
        else {
            binding.typeText.setText("Receive");
        }
        binding.databaseText.setText("ID = " + selected.id);
        return binding.getRoot();
    }
}
