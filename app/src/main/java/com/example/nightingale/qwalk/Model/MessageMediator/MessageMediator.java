package com.example.nightingale.qwalk.Model.MessageMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraft on 2017-05-24.
 */

public class MessageMediator {
    private List<IOnMessageRecievedListener> listeners = new ArrayList<>();

    public final void addListener(IOnMessageRecievedListener listener) {
        listeners.add(listener);
    }

    public final void removeListener(IOnMessageRecievedListener listener) {
        listeners.remove(listener);
    }

    public final void onMessageRecieved(String message) {
        for (IOnMessageRecievedListener listener : listeners) {
            if(!(message.equals("Ignore"))){
                listener.messageRecieved(message);
            }

        }
    }
}
