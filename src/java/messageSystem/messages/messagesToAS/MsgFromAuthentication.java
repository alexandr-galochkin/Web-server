package messageSystem.messages.messagesToAS;

import base.AccountService;
import messageSystem.Adress;
import messageSystem.messages.StatusOfAuthentication;

import static frontend.Frontend.setStatusOfAuthentication;

public class MsgFromAuthentication extends MsgToAS {
    private StatusOfAuthentication status;
    private String sessionId;
    public MsgFromAuthentication(Adress from, Adress to, String sessionId, StatusOfAuthentication status) {
        super(from, to);
        this.sessionId = sessionId;
        this.status = status;
    }

    public StatusOfAuthentication getStatus(){
        return status;
    }

    @Override
    void exec(AccountService accountService) {
        setStatusOfAuthentication(sessionId, status);
    }
}
