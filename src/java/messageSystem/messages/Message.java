package messageSystem.messages;

import messageSystem.Abonent;
import messageSystem.Adress;

public abstract class Message {
    private final Adress from;
    private final Adress to;

    public Message(Adress from, Adress to){
        this.from = from;
        this.to = to;
    }

    public Adress getFrom(){
        return from;
    }

    public Adress getTo(){
        return to;
    }

    public abstract void exec(Abonent abonent);
}
