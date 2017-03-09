package com.vb.fbviewer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.login.LoginManager;

/**
 * Created by bonar on 3/5/2017.
 */

/**
 * Facebook home page.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private static final String ME = "me";

    private static final String REQUEST_USER = "";
    private static final String REQUEST_PICTURE = "/picture";

    SimpleDraweeView mPhoto;
    TextView mUserName;
    TextView mUserGender;
    TextView mUserEMail;

    User mUser;

    /**
     * New instance of HomeFragment.
     * @return
     * @see HomeFragment
     */
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    /**
     * On creates view.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        setHasOptionsMenu(true);

        mUserName = (TextView) v.findViewById(R.id.user_view_name);
        mUserGender = (TextView) v.findViewById(R.id.user_view_gender);
        mUserEMail = (TextView) v.findViewById(R.id.user_view_email);

        mPhoto = (SimpleDraweeView) v.findViewById(R.id.user_view_photo);

        request(ME, REQUEST_USER);
        request(ME, REQUEST_PICTURE);

        return v;
    }

    /**
     * Render view.
     * @param requestCode what to render.
     * @param url url to photo.
     */
    private void render(final String requestCode, String url)
    {
        switch(requestCode)
        {
            case REQUEST_USER:
                mUserName.setText(mUser.getFirstName() + " " + mUser.getLastName());
                mUserGender.setText(mUser.getGender());
                mUserEMail.setText(mUser.getEMail());
                break;

            case REQUEST_PICTURE:
                /**
                 * Since Facebook api for android is totally closed without permission
                 * lets get a photo with link below
                 * */
                Uri uri = Uri.parse("http://graph.facebook.com/" + url + "/picture?type=large");
                mPhoto.setImageURI(uri);
                break;
            default:
                break;
        }


    }

    /**
     * Request data.
     * @param id user id.
     * @param requestCode what to request.
     */
    public void request(String id, final String requestCode)
    {
        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + id + requestCode,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.i(TAG, response.toString());

                        String url = null;

                        switch (requestCode)
                        {
                            case REQUEST_USER:
                                try {
                                User user = new User(
                                    response.getJSONObject().getString(User.ID),
                                    response.getJSONObject().getString(User.FIRST_NAME),
                                    response.getJSONObject().getString(User.LAST_NAME),
                                    response.getJSONObject().getString(User.GENDER),
                                    response.getJSONObject().getString(User.EMAIL)
                                );

                                if(mUser == null)
                                    mUser = user;
                                } catch (Exception e) {
                                    Log.e(TAG, e.toString());
                                }
                                break;

                            case REQUEST_PICTURE:
                                try {
                                    while(mUser.getID() == null);
                                    url = mUser.getID();
                                } catch (Exception e){
                                    Log.e(TAG, e.toString());
                                }
                                break;
                            default:
                                break;
                        }

                        render(requestCode, url);


                    }
                }
        );

        Log.i(TAG, request.toString());

        Bundle parameters = new Bundle();
        switch (requestCode)
        {
            case REQUEST_USER:
                parameters.putString(GraphRequest.FIELDS_PARAM, User.ID + ", " + User.FIRST_NAME + ", "
                        + User.LAST_NAME + ", " + User.GENDER + ", " + User.EMAIL);
                break;
            case REQUEST_PICTURE:
                parameters.putString(GraphRequest.FIELDS_PARAM, User.PICTURE);
                parameters.putBoolean("redirect", true);
                break;
            default:
                break;
        }
        request.setParameters(parameters);
        request.executeAsync();
    }

    /**
     * On menu options create.
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
     * On menu item clicked.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_item_logout:
                LoginManager.getInstance().logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
