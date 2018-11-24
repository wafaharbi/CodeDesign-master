package com.example.wafa.studentapp;

public class Chat {


        String sender, reciver , message, date ;


    public Chat(String sender, String reciver, String message , String date) {
            this.sender = sender;
            this.reciver = reciver;
            this.message = message;
            this.date=date;


        }


    public Chat(String date){

            this.date=date;
        }


        public String getDate() {


            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getReciver() {
            return reciver;
        }

        public void setReciver(String reciver) {
            this.reciver = reciver;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }



        Chat(){

        }


    }


