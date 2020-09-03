package services.adressService;

import base.AccountService;
import base.AdressService;
import base.DBService;
import base.MessageSystem;
import services.accountService.AccountServiceImpl;
import services.dbService.DBServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class AdressServiceImpl implements AdressService {
    private final MessageSystem messageSystem;
    private int numberOfAccountServices = 1;
    private int numberOfDBServices = 1;

    private final List<AccountService> accountServices = new ArrayList<>(numberOfAccountServices);
    private final List<DBService> dbServices = new ArrayList<>(numberOfDBServices);

    public AdressServiceImpl(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
        for(int i = 0; i < numberOfDBServices; i++){
            DBService newDBService = new DBServiceImpl(messageSystem);
            messageSystem.addAdress(newDBService.getAdress());
            Thread newThread = new Thread(newDBService);
            newThread.start();
            dbServices.add(newDBService);

        }
        for(int i = 0; i < numberOfDBServices; i++){
            AccountService newAccountService = new AccountServiceImpl(messageSystem, this);
            messageSystem.addAdress(newAccountService.getAdress());
            Thread newThread = new Thread(newAccountService);
            newThread.start();
            System.out.println("NewThread");
            accountServices.add(newAccountService);
        }
    }

    // TODO: 03.09.2020
    @Override
    public AccountService getAccountService() {
        return accountServices.get(0);
    }

    // TODO: 03.09.2020
    @Override
    public DBService getDBService() {
        return dbServices.get(0);
    }

    @Override
    public void addAccountService() {
        accountServices.add(new AccountServiceImpl(messageSystem, this));
        numberOfAccountServices++;

    }

    @Override
    public void addDBService() {
        dbServices.add(new DBServiceImpl(messageSystem));
        numberOfDBServices++;
    }
}
