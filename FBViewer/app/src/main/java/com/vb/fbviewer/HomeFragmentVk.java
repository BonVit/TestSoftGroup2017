package com.vb.fbviewer;

import android.app.Activity;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.login.LoginManager;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bonar on 3/8/2017.
 */

/**
 * Home page vk.
 */
public class HomeFragmentVk extends Fragment {
    private static final String TAG = "HomeFragmentVk";

    private static final String ME = "me";

    private static final String REQUEST_USER = "";
    private static final String REQUEST_PICTURE = "/picture";

    SimpleDraweeView mPhoto;
    TextView mUserName;
    TextView mUserCity;
    TextView mUserEMail;
    RecyclerView mRecyclerView;

    UserVk mUser;
    List<UserVk> mFriends;

    private Callbacks mCallbacks;

    /**
     * A callback that provides interface to get onclick listener from main activity.
     */
    public interface Callbacks {
        void onFriendSelected(UserVk friend);
    }

    /**
     * New instance of HomeFragmentVk
     * @return
     * @see HomeFragmentVk
     */
    public static HomeFragmentVk newInstance() {
        return new HomeFragmentVk();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_vk, container, false);

        setHasOptionsMenu(true);

        mUserName = (TextView) v.findViewById(R.id.user_view_vk_name);
        mUserCity = (TextView) v.findViewById(R.id.user_view_vk_city);
        mUserEMail = (TextView) v.findViewById(R.id.user_view_vk_email);

        mPhoto = (SimpleDraweeView) v.findViewById(R.id.user_view_vk_photo);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_home_vk_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.setAdapter(new FriendAdapter(mFriends));

        requestUser();
        requestFriends();

        return v;
    }

    /**
     * Request user and render view.
     */
    private void requestUser()
    {
        //Get user info
        VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,UserVk.PICTURE + ", " + UserVk.CITY))
                .executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.i(TAG, response.json.toString());

                try {
                    JSONObject resp = response.json.getJSONArray("response").getJSONObject(0);
                    String mFristName = resp.getString(UserVk.FIRST_NAME);
                    String mLastName = resp.getString(UserVk.LAST_NAME);
                    String mCity = resp.getJSONObject(UserVk.CITY).getString("title");
                    String mPhoto = resp.getString(UserVk.PICTURE);

                    mUser = new UserVk(VKSdk.getAccessToken().userId, mFristName, mLastName, mCity,
                            VKSdk.getAccessToken().email);
                    mUser.setPhoto(mPhoto);

                    renderUser();

                } catch (JSONException e) {
                }
            }
        });
    }

    /**
     * Request user friends and render view.
     */
    public void requestFriends()
    {
        VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, UserVk.FIRST_NAME + ", " +
                UserVk.PICTURE + ", " + UserVk.ONLINE))
                .executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        Log.i(TAG, response.json.toString());

                        try {
                            JSONObject resp = response.json.getJSONObject("response");
                            int mFriendsCount = resp.getInt("count");

                            mFriends = new ArrayList<UserVk>();

                            JSONArray friends = resp.getJSONArray("items");
                            JSONObject item;

                            for(int i = 0; i < mFriendsCount; i++) {
                                item = friends.getJSONObject(i);

                                String mId = item.getString(UserVk.ID);
                                String mFirstName = item.getString(UserVk.FIRST_NAME);
                                String mLastName = item.getString(UserVk.LAST_NAME);
                                String mPhoto = item.getString(UserVk.PICTURE);
                                int mIsOnline = item.getInt(UserVk.ONLINE);

                                UserVk user = new UserVk(mId, mFirstName, mLastName, null, null);
                                user.setPhoto(mPhoto);
                                if(mIsOnline == 0)
                                    user.setIsOnline(false);
                                else
                                    user.setIsOnline(true);

                                mFriends.add(user);
                            }

                            renderFriends();

                        } catch (JSONException e) {
                        }
                    }
                });
    }

    /**
     * Render view.
     */
    private void renderFriends() {
        FriendAdapter mFriendAdapter = new FriendAdapter(mFriends);
        mRecyclerView.setAdapter(mFriendAdapter);
    }

    /**
     * Render view.
     */
    private void renderUser()
    {
        mUserName.setText(mUser.getFirstName() + " " + mUser.getLastName());
        mUserCity.setText(mUser.getCity());
        mUserEMail.setText(mUser.getEMail());

        Uri uri = Uri.parse(mUser.getPhoto());
        mPhoto.setImageURI(uri);

        this.getView().invalidate();
    }

    /**
     * On create menu.
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu);

        /*MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }*/
    }

    /**
     * Menu item on click.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_item_logout:
                Log.i(TAG, Boolean.toString(VKSdk.isLoggedIn()));
                VKSdk.logout();
                ((MainActivity) getActivity()).replaceFragment();
                Log.i(TAG, Boolean.toString(VKSdk.isLoggedIn()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * View holder for recycler view.
     */
    private class FriendHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private SimpleDraweeView mPhoto;
        private TextView mName;
        private TextView mIsOnline;

        private UserVk mUser;

        public FriendHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mPhoto = (SimpleDraweeView)
                    itemView.findViewById(R.id.item_friend_vk_photo);
            mName = (TextView)
                    itemView.findViewById(R.id.item_friend_vk_name);
            mIsOnline = (TextView)
                    itemView.findViewById(R.id.item_friend_vk_online);
        }

        public void bindFriend(UserVk user) {
            mUser = user;

            Uri uri = Uri.parse(mUser.getPhoto());
            mPhoto.setImageURI(uri);
            mName.setText(mUser.getFirstName() + " " + mUser.getLastName());
            if(mUser.isOnline())
                mIsOnline.setText(getString(R.string.online));
            else
                mIsOnline.setText(getString(R.string.offline));
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Clicked");
            mCallbacks.onFriendSelected(mUser);
        }
    }

    /**
     * Adapter for recycler view.
     */
    private class FriendAdapter extends RecyclerView.Adapter<FriendHolder> {
        private List<UserVk> mFriends;
        public FriendAdapter (List<UserVk> friends) {
            mFriends = friends;
        }

        @Override
        public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.item_friend_vk, parent, false);
            return new FriendHolder(view);
        }
        @Override
        public void onBindViewHolder(FriendHolder holder, int position) {
            UserVk friend = mFriends.get(position);
            holder.bindFriend(friend);
        }
        @Override
        public int getItemCount() {
            return mFriends.size();
        }
    }

    /**
     * On attach fragment to activity.
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    /**
     * On detach fragment to activity.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
}
