package com.vb.fbviewer;

/**
 * Created by bonar on 3/5/2017.
 */

/**
 * Structure for storing vk user.
 */
public class UserVk {
    private static final String TAG = "UserVk";

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String CITY= "city";
    public static final String PICTURE = "photo_100";
    public static final String EMAIL = "email";
    public static final String ONLINE = "online";

    private String mID;
    private String mFirstName;
    private String mLastName;
    private String mCity;
    private String mPhoto;
    private String mEMail;

    private boolean mIsOnline = false;

    /**
     *
     * @return is user online.
     */
    public boolean isOnline() {
        return mIsOnline;
    }

    /**
     *
     * @param mIsOnline is user online.
     */
    public void setIsOnline(boolean mIsOnline) {
        this.mIsOnline = mIsOnline;
    }

    /**
     *
     * @param mID user id.
     * @param mFirstName first name.
     * @param mLastName last name.
     * @param mCity city.
     * @param mEMail email.
     */
    public UserVk(String mID, String mFirstName, String mLastName, String mCity, String mEMail) {
        this.mID = mID;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mCity = mCity;
        this.mEMail = mEMail;
    }

    /**
     *
     * @return id.
     */
    public String getID() {
        return mID;
    }

    /**
     *
     * @return first name.
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     *
     * @return last name.
     */
    public String getLastName() {
        return mLastName;
    }

    /**
     *
     * @return city.
     */
    public String getCity() {
        return mCity;
    }

    /**
     *
     * @return photo url.
     */
    public String getPhoto() {
        return mPhoto;
    }

    /**
     *
     * @param mPhoto photo url.
     */
    public void setPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    /**
     *
     * @return email.
     */
    public String getEMail() {
        return mEMail;
    }
}
