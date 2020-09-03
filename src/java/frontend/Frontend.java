package frontend;

import base.AccountService;
import base.AdressService;
import base.DBService;
import base.MessageSystem;
import messageSystem.messages.StatusOfAuthentication;
import messageSystem.messages.messageToDB.MsgForAuthentication;
import services.accountService.AccountServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static messageSystem.messages.StatusOfAuthentication.InProcessing;

public class Frontend {
    private MessageSystem messageSystem;
    private AdressService adressService;
    private static final ConcurrentMap<String, StatusOfAuthentication> currentSessions = new ConcurrentHashMap<>();

    public Frontend(MessageSystem messageSystem, AdressService adressService){
        this.messageSystem = messageSystem;
        this.adressService = adressService;
    }

    public StatusOfAuthentication isAuthenticated(String login, String password, String sessionId){
        StatusOfAuthentication currentStatus = InProcessing;
        currentSessions.put(sessionId, currentStatus);
        AccountService accountService = adressService.getAccountService();
        DBService dbService = adressService.getDBService();
        messageSystem.sendMessage(new MsgForAuthentication(accountService.getAdress(), dbService.getAdress(),
                login, password, sessionId));
        while (currentStatus == InProcessing){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentStatus = currentSessions.get(sessionId);
        }
        return currentStatus;
    }

    public static void setStatusOfAuthentication(String sessionId, StatusOfAuthentication status){
        currentSessions.replace(sessionId, status);
    }
}
