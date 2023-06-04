package com.example.quanlypet.ui.activity;

import static ua.naiksoftware.stomp.dto.LifecycleEvent.Type.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.SearchManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.MessageInfo;
import com.example.quanlypet.ui.fragment.ChatFragment;
import com.example.quanlypet.ui.fragment.MessageFragment;
import com.example.quanlypet.viewmodel.ChatViewModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.CompletableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;

public class ChatActivity extends AppCompatActivity {

    private final static String TAG = ChatActivity.class.getName();
    private Toolbar toolbar;
    private StompClient stomp;
    private Fragment fragmentActive;
    private MessageFragment messageFragment;
    private ChatFragment chatFragment;
    private ChatViewModel chatViewModel;
    private CompositeDisposable compositeDisposable;
    private final SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getChatFragment();
        init();
        connectView();
    }

    private void init(){
        stomp = Stomp.over(Stomp.ConnectionProvider.OKHTTP, DataStatic.BASE_WS+"/ws");
        resetSubscriptions();
        connect();
    }

    private void resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        compositeDisposable = new CompositeDisposable();
    }

    public void connect(){
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        List<StompHeader> headers = new ArrayList<>();
        headers.add(new StompHeader("id", uid.toString()));

        stomp.withClientHeartbeat(1000).withServerHeartbeat(1000);
        resetSubscriptions();

        Disposable dispLifecycle = stomp.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Log.d(TAG, "Stomp connection opened");
                            break;
                        case ERROR:
                            Log.e(TAG, "Stomp connection error");
                            break;
                        case CLOSED:
                            Log.d(TAG, "Stomp connection closed");
                            resetSubscriptions();
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            Log.d(TAG, "Stomp failed server heartbeat");
                            break;
                    }
                });

        compositeDisposable.add(dispLifecycle);

        // Receive greetings
        Disposable dispTopic = stomp.topic("/user/"+uid+"/queue/messages")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    Log.d(TAG, topicMessage.getPayload());
//                    addItem(mGson.fromJson(topicMessage.getPayload(), EchoModel.class));
                    MessageInfo messageInfo = new Gson().fromJson(topicMessage.getPayload(), MessageInfo.class);
                    if(fragmentActive instanceof ChatFragment){
                        chatFragment.updateItem(messageInfo);
                    }else if(fragmentActive instanceof MessageFragment){
                        if(messageFragment.getIdGroup().equals(messageInfo.getIdGroup())){
                            messageFragment.pushMessage(messageInfo);
                        }
                    }
                }, throwable -> {
                    Log.e(TAG, throwable.getMessage());
                });

        compositeDisposable.add(dispTopic);
        stomp.connect(headers);
    }

    public void sendMessage(MessageInfo messageInfo){
        compositeDisposable.add(stomp.send("/app/chat", new Gson().toJson(messageInfo))
                .compose(applySchedulers())
                .subscribe(() -> {
                    Log.d(TAG, "STOMP echo send successfully");
                }, throwable -> {
                    Log.e(TAG, throwable.getMessage());
                }));
    }

    private void connectView(){
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        toolbar = findViewById(R.id.id_tollBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tin nhắn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    protected CompletableTransformer applySchedulers() {
        return upstream -> upstream
                .unsubscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getChatFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        chatFragment = new ChatFragment(this);
        transaction.add(R.id.layout_content, chatFragment);
        transaction.commit();
    }

    public void getMessageFragment(Integer id, String title){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        messageFragment = new MessageFragment(this, id);
        toolbar.setTitle(title);
        transaction.replace(R.id.layout_content, messageFragment).addToBackStack(DataStatic.STACK_APP);
        transaction.commit();
    }

    public ChatViewModel getChatViewModel() {
        return chatViewModel;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setFragmentActive(Fragment fragmentActive) {
        this.fragmentActive = fragmentActive;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}