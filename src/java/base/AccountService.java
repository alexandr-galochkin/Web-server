package base;

import messageSystem.Abonent;
import services.accountService.UserProfile;
import services.dbService.exceptions.DBException;

public interface AccountService extends Abonent, Runnable {
    UserProfile getUserByLogin(String login) throws DBException;

    boolean contains(String login) throws DBException;

    void addNewUser(UserProfile newUser) throws DBException;

    public void addNewUser(String login, String password)throws DBException;
}
