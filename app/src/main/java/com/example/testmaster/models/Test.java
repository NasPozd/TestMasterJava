package com.example.testmaster.models;

import java.util.HashMap;

public class Test{
    public String id ="";
    public String title = "";
    public HashMap<String,Question> questions = new HashMap<String,Question>();

    public Test(){}

    public  Test( String id, String title){
        this.id = id;
        this.title = title;
    }
    public Test( String id, String title,HashMap<String,Question> questions){
        this.id = id;
        this.title = title;
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", questions=" + questions +
                '}';
    }
}
