package accountService;

import base.AccountService;
import base.DBService;
import dbService.DBException;

public class AccountServiceImpl implements AccountService {
    private final base.DBService DBService;
    public AccountServiceImpl(DBService DBService) {
        this.DBService = DBService;
    }

    public void addNewUser(UserProfile userProfile)throws DBException {
        DBService.addUser(userProfile);
    }

    public void addNewUser(String login, String password)throws DBException {
        DBService.addUser(new UserProfile(-1, login, password));
    }

    public UserProfile getUserByLogin(String login)throws DBException {
        return DBService.getUserByLogin(login);
    }

    public boolean contains(String login)throws DBException {
        return DBService.contains(login);
    }
}
