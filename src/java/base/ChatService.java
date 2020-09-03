package base;


import accountService.Message;
import chatService.ChatWebSocket;

public interface ChatService {

    public void sendMessage(ChatWebSocket sender, String data);

    public void add(ChatWebSocket webSocket);

    public void remove(ChatWebSocket chatWebSocket);
}
