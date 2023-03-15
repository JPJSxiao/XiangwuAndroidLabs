package algonquin.cst2355.torunse.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {

//public ArrayList<String> messages = new ArrayList<>();

    //public ArrayList<ChatMessage> messages = new ArrayList<>();
    public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<>();
    public MutableLiveData<ChatMessage> selectedMessage = new MutableLiveData<>();

}
