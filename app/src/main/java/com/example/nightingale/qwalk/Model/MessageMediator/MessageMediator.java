package com.example.nightingale.qwalk.Model.MessageMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraft on 2017-05-24.
 */

public class MessageMediator {
    private List<IOnMessageRecievedListener> listeners = new ArrayList<>();

    public void addListener(IOnMessageRecievedListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IOnMessageRecievedListener listener) {
        listeners.remove(listener);
    }

    public void onMessageRecieved(String message) {
        for (IOnMessageRecievedListener listener : listeners) {
            listener.messageRecieved(message);
        }
    }
}
