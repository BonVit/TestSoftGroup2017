package com.vb.fbviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiMessage;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bonar on 3/8/2017.
 */

/**
 * An messageing activity for vk.
 */
public class MessengerVkActivity extends AppCompatActivity {
    private static final String TAG = "MessengerVkActivity";

    private static final String EXTRA_USER_ID =
            "com.vd.fbviewer.user_id";
    private static final String EXTRA_USER_NAME =
            "com.vd.fbviewer.user_name";

    private RecyclerView recyclerView;
    private Button btnSend;
    private EditText editText;

    private String mUserId;
    private String mUserName;

    private List<VkMessage> messages;

    /**
     * Returns intent with params to start this activity.
     * @param context context.
     * @param id user id.
     * @param userName user name.
     * @return intent with params.
     */
    public static Intent newIntent(Context context, String id, String userName)
    {
        Intent intent = new Intent(context, MessengerVkActivity.class);
        intent.putExtra(EXTRA_USER_ID, id);
        intent.putExtra(EXTRA_USER_NAME, userName);
        return intent;
    }

    /**
     * On create activity.
     * @param savedInstanceState saved instance state bundle.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_messages);

        recyclerView = (RecyclerView) findViewById(R.id.activity_messages_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        btnSend = (Button) findViewById(R.id.activity_messages_send_text);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKRequest request = new VKRequest("messages.send", VKParameters.from(VKApiConst.USER_ID,
                        mUserId, VKApiConst.MESSAGE, editText.getText().toString()));

                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                    }
                });
            }
        });

        editText = (EditText) findViewById(R.id.activity_messages_text);

        mUserId = getIntent().getStringExtra(EXTRA_USER_ID);
        mUserName = getIntent().getStringExtra(EXTRA_USER_NAME);

        Log.d(TAG, mUserId);
        Log.d(TAG, VKSdk.getAccessToken().userId);

        requestMsgs();
    }

    /**
     * Requests and renders messages of logged in user and chosen user.
     */
    private void requestMsgs()
    {
        messages = new ArrayList<VkMessage>();

        VKRequest request = new VKRequest("messages.getHistory", VKParameters.from(VKApiConst.USER_ID, mUserId));
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                Log.i(TAG, response.json.toString());

                try {
                    JSONArray resp = response.json.getJSONObject("response").getJSONArray("items");

                    for(int i = 0; i < resp.length(); i++)
                    {
                        VKApiMessage m = new VKApiMessage(resp.getJSONObject(i));
                        VkMessage msg = new VkMessage(m.body, m.date, m.out);
                        messages.add(msg);
                    }

                    Log.i(TAG, messages.toArray().toString());

                    renderMessages();

                } catch (JSONException e) {
                }
            }
        });

    }

    /**
     * Renders messages.
     */
    private void renderMessages()
    {
        MessageAdapter mMessageAdapter = new MessageAdapter(messages, mUserName);
        recyclerView.setAdapter(mMessageAdapter);
    }

    /**
     * Recycler view holder.
     */
    private class MessageHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView text;

        private VkMessage message;
        private String userName;

        public MessageHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.item_message_user_name);
            text = (TextView) itemView.findViewById(R.id.item_message_text);

        }

        public void bindMessage(VkMessage msg, String userName) {
            message = msg;
            this.userName = userName;

            mName.setText(userName);
            text.setText(msg.getText());

            if(msg.isOut())
            {
                mName.setText(getString(R.string.me));
                mName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                text.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            }
            else
            {
                mName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                text.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }
        }

    }

    /**
     * Recycler view adapter.
     */
    private class MessageAdapter extends RecyclerView.Adapter<MessengerVkActivity.MessageHolder> {
        private List<VkMessage> messages;
        private String mUserName;

        public MessageAdapter (List<VkMessage> messages, String mUserName) {
            this.messages = messages;
            this.mUserName = mUserName;
        }

        @Override
        public MessengerVkActivity.MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

            View view = layoutInflater
                    .inflate(R.layout.item_message, parent, false);

            return new MessengerVkActivity.MessageHolder(view);
        }
        @Override
        public void onBindViewHolder(MessengerVkActivity.MessageHolder holder, int position) {
            VkMessage msg = messages.get(position);
            holder.bindMessage(msg, mUserName);
        }
        @Override
        public int getItemCount() {
            return messages.size();
        }
    }
}
