package com.vb.fbviewer;

/**
 * Created by bonar on 3/5/2017.
 */

/**
 * A class for storing facebook user data
 */
public class User {
    private static final String TAG = "USER";

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String GENDER = "gender";
    public static final String PICTURE = "picture";
    public static final String EMAIL = "email";

    private String mID;
    private String mFirstName;
    private String mLastName;
    private String mGender;
    private String mPhoto;
    private String mEMail;

    /**
     *
     * @param mID id.
     * @param mFirstName first name.
     * @param mLastName last name.
     * @param mGender gender.
     * @param mEMail email.
     */
    public User(String mID, String mFirstName, String mLastName, String mGender, String mEMail) {
        this.mID = mID;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mGender = mGender;
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
     * @return gender.
     */
    public String getGender() {
        return mGender;
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
