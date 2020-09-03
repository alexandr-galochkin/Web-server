package base;

import messageSystem.Abonent;
import messageSystem.Adress;
import messageSystem.messages.Message;

public interface MessageSystem {
    void sendMessage(Message message);

    void execForAbonent(Abonent abonent);

    public void addAdress(Adress adress);
}
