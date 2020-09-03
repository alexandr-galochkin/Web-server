package messageSystem.messages.messageToDB;

import base.DBService;
import messageSystem.Abonent;
import messageSystem.Adress;
import messageSystem.messages.Message;

public abstract class MsgToDB extends Message {
    public MsgToDB(Adress from, Adress to) {
        super(from, to);
    }

    public void exec(Abonent abonent){
        if (abonent instanceof DBService){
            exec((DBService) abonent);
        } else{
            System.out.println("Ошибка: неверный тип абонента.");
        }
    }

    abstract void exec(DBService dbService);
}
