package net.kyaz0x1.dcautomaticstatus.api.models;

import com.google.gson.annotations.SerializedName;

public class Status {

    private String text;
    @SerializedName("emoji_name")
    private String emojiName;

    public Status(String text, String emojiName){
        this.text = text;
        this.emojiName = emojiName;
    }

    public String getText() {
        return text;
    }

    public String getEmojiName() {
        return emojiName;
    }

}