package base;

import accountService.Message;
import accountService.UserProfile;
import dbService.DBException;
import dbService.dataSets.MessagesDataSet;
import dbService.dataSets.UsersDataSet;

import java.util.Date;
import java.util.List;

public interface DBService {
    void printConnectInfo();

    UserProfile getUser(long id) throws DBException;

    List<UserProfile> allUsers();

    public List<MessagesDataSet> allMessages();

    long addUser(UserProfile userProfile) throws DBException;

    long addUser(String login, String password) throws DBException;

    UserProfile getUserByLogin(String login) throws DBException;

    boolean contains(String login);

    public long addMessage(Message message) throws DBException;
}
