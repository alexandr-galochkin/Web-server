package services.accountService;

import services.dbService.dataSets.UsersDataSet;

public class UserProfile {
    private final String login;
    private final String password;
    private long id;

    public UserProfile(long id, String login, String password) {
        this.login = login;
        this.password = password;
        this.id = id;
    }

    public UserProfile(String dataSetLogin, String login) {
        this.login = login;
        this.password = login;
    }

    public UserProfile(UsersDataSet usersDataSet) {
        this.login = usersDataSet.getLogin();
        this.password = usersDataSet.getPassword();
        this.id = usersDataSet.getId();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UsersDataSet toUsersDataSet(){
        return new UsersDataSet(this.getId(), this.getLogin(), this.getPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login +
                ", password='" + password +'\'' +
                '}';
    }
}
