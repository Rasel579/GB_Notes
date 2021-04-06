package com.example.gb_notes.bussiness_logic;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observers;
    public Publisher(){
        observers = new ArrayList<Observer>();
    }
    public void subscribe(Observer observer){
        observers.add(observer);
    }
    public void unSubscribe(Observer observer){
        observers.remove(observer);
    }
    public void notify(Note note){
        for (Observer observer : observers) {
            observer.updateListOfFragments(note);
        }
    }
}
