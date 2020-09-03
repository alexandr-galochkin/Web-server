package base;

import messageSystem.Abonent;
import messageSystem.messages.StatusOfAuthentication;
import services.accountService.UserProfile;
import services.dbService.exceptions.DBException;

import java.util.List;

public interface DBService extends Abonent, Runnable {
    void printConnectInfo();

    UserProfile getUser(long id) throws DBException;

    List<UserProfile> allUsers();

    long addUser(UserProfile userProfile) throws DBException;

    long addUser(String login, String password) throws DBException;

    UserProfile getUserByLogin(String login) throws DBException;

    boolean contains(String login);

    StatusOfAuthentication authentication(String login, String password);

    MessageSystem getMessageSystem();

}
