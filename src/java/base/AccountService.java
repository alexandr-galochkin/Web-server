package base;

import accountService.UserProfile;
import dbService.DBException;

public interface AccountService {
    UserProfile getUserByLogin(String login) throws DBException;

    boolean contains(String login) throws DBException;

    void addNewUser(UserProfile newUser) throws DBException;

    public void addNewUser(String login, String password)throws DBException;
}
