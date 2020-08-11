package com.example.chatpanda;

/**@author : Swaraj Deshmukh
 *  Date : 22/07/2020
 *
 */
public class MessageDetail {
    private String author;
    private String message;


    public MessageDetail(String author, String message) {
        this.author = author;
        this.message = message;
    }

    public MessageDetail(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }


}
