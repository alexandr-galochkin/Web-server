package services.accountService;

import base.AccountService;
import base.AdressService;
import base.DBService;
import base.MessageSystem;
import frontend.Frontend;
import messageSystem.Abonent;
import messageSystem.Adress;
import services.dbService.DBServiceImpl;
import services.dbService.exceptions.DBException;

import java.util.Timer;
import java.util.TimerTask;

public class AccountServiceImpl implements AccountService, Abonent, Runnable {
    private final MessageSystem messageSystem;
    private final AdressService adressService;
    private Adress adress;

    public AccountServiceImpl(MessageSystem messageSystem, AdressService adressService) {
        this.messageSystem = messageSystem;
        this.adressService = adressService;
        adress = new Adress();
    }

    @Override
    public Adress getAdress() {
        return adress;
    }

    @Override
    public void run() {
        while (true) {
            TimerTask timerTask = new MyTimerTaskForAS(this);
            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(timerTask, 100, 100);
        }
    }

    public void addNewUser(UserProfile userProfile) throws DBException {
        adressService.getDBService().addUser(userProfile);
    }

    public void addNewUser(String login, String password) throws DBException {
        adressService.getDBService().addUser(new UserProfile(-1, login, password));
    }

    public UserProfile getUserByLogin(String login) throws DBException {
        return adressService.getDBService().getUserByLogin(login);
    }

    public boolean contains(String login) throws DBException {
        return adressService.getDBService().contains(login);
    }


    private class MyTimerTaskForAS extends TimerTask {
        private AccountService accountService;

        private MyTimerTaskForAS(AccountService accountService) {
            this.accountService = accountService;
        }

        @Override
        public void run() {
            messageSystem.execForAbonent(accountService);
        }
    }

}
