package com.example.freshspringboot.model;

public class ChatMessage {

    private long time;
    private String contents;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "time=" + time +
                ", contents='" + contents + '\'' +
                '}';
    }
}
