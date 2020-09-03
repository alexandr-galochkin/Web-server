package messageSystem.messages.messageToDB;

import base.DBService;
import messageSystem.Adress;
import messageSystem.messages.StatusOfAuthentication;
import messageSystem.messages.messagesToAS.MsgFromAuthentication;

public class MsgForAuthentication extends MsgToDB {
    private String name;
    private String password;
    private String sessionId;

    public MsgForAuthentication(Adress from, Adress to, String name, String password, String sessionId){
        super(from, to);
        this.name = name;
        this.password = password;
        this.sessionId = sessionId;
    }

    @Override
    void exec(DBService dbService) {
        StatusOfAuthentication status = dbService.authentication(name, password);
        MsgFromAuthentication msgBack = new MsgFromAuthentication(getTo(), getFrom(), sessionId, status);
        dbService.getMessageSystem().sendMessage(msgBack);
    }
}
