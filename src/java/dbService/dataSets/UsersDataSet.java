package dbService.dataSets;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UsersDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    @Column(name = "id")
    private long id;

    @Column(name = "login", unique = true, updatable = false)
    private String login;

    @Column(name = "password", unique = false, updatable = true)
    private String password;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet(String login, String password) {
        this.setLogin(login);
        this.setPassword(password);
    }

    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet(long id, String login, String password) {
        this.id = id;
        this.setLogin(login);
        this.setPassword(password);
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login){this.login = login;};

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", login='" + login +
                ", password='" + password +'\'' +
                '}';
    }
}