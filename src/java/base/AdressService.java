package base;

import messageSystem.Adress;

public interface AdressService {
    AccountService getAccountService();
    DBService getDBService();

    void addAccountService();
    void addDBService();


}
