package services.dbService.dao;

import services.accountService.UserProfile;
import services.dbService.dataSets.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;


public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UsersDataSet get(long id) throws HibernateException {
        return (UsersDataSet) session.get(UsersDataSet.class, id);
    }

    public long getUserId(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return ((UsersDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult()).getId();
    }

    public UsersDataSet getUserByLogin(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return ((UsersDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult());
    }

    public long insertUser(String login, String password) throws HibernateException {
        return (long) session.save(new UsersDataSet(login, password));
    }

    public long insertUser(UserProfile userProfile) throws HibernateException {
        userProfile.setId((long) session.save(userProfile));
        return userProfile.getId();
    }

    public List<UserProfile> allUsers(){
        List<UsersDataSet> listOfDataSet;
        List<UserProfile> listOfProfile = new ArrayList<>();
        try {
            listOfDataSet= session.createCriteria(UsersDataSet.class).list();
        } catch (Exception e){
            return null;
        }
        for (UsersDataSet currentDataSet: listOfDataSet) {
            listOfProfile.add(new UserProfile(currentDataSet.getId(), currentDataSet.getLogin(), currentDataSet.getPassword()));
        }
        return listOfProfile;
    }
}
