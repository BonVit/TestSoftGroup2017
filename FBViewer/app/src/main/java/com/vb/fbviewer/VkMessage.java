package com.vb.fbviewer;

/**
 * Created by bonar on 3/8/2017.
 */


/**
 * Structre for storing message.
 */
public class VkMessage {
    private String text;
    private long date;
    private boolean out;

    /**
     *
     * @param text message text.
     * @param date date.
     * @param out output or input.
     */
    public VkMessage(String text, long date, boolean out) {
        this.text = text;
        this.date = date;
        this.out = out;
    }

    /**
     *
     * @return message text.
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text message text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return date.
     */
    public long getDate() {
        return date;
    }

    /**
     *
     * @param date date.
     */
    public void setDate(long date) {
        this.date = date;
    }

    /**
     *
     * @return input ot output.
     */
    public boolean isOut() {
        return out;
    }

    /**
     *
     * @param out input or output.
     */
    public void setOut(boolean out) {
        this.out = out;
    }
}
