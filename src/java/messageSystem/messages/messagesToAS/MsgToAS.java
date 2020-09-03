package messageSystem.messages.messagesToAS;

import base.AccountService;
import messageSystem.Abonent;
import messageSystem.Adress;
import messageSystem.messages.Message;

public abstract class MsgToAS extends Message {
    public MsgToAS(Adress from, Adress to) {
        super(from, to);
    }

    public void exec(Abonent abonent){
        if (abonent instanceof AccountService){
            exec((AccountService) abonent);
        } else{
            System.out.println("Ошибка: неверный тип абонента.");
        }
    }

    abstract void exec(AccountService accountService);
}