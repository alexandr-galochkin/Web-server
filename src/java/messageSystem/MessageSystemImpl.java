package messageSystem;

import base.MessageSystem;
import messageSystem.messages.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystemImpl implements MessageSystem {
    private final Map<Adress, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();

    @Override
    public void sendMessage(Message message) {
        Queue<Message> messageQueue = messages.get(message.getTo());
        messageQueue.add(message);
    }

    @Override
    public void execForAbonent(Abonent abonent) {
        Queue<Message> messageQueue = messages.get(abonent.getAdress());
        while (!messageQueue.isEmpty()){
            Message messsage = messageQueue.poll();
            messsage.exec(abonent);
        }
    }

    public void addAdress(Adress adress){
        if (!messages.containsKey(adress)){
            messages.put(adress, new ConcurrentLinkedQueue<>());
        }
    }
}
