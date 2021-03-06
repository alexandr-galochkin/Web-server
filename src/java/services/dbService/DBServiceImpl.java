package services.dbService;

import base.AdressService;
import base.DBService;
import base.MessageSystem;
import messageSystem.Abonent;
import messageSystem.Adress;
import messageSystem.messages.Message;
import messageSystem.messages.StatusOfAuthentication;
import services.accountService.UserProfile;
import services.dbService.exceptions.DBException;
import services.dbService.dao.UsersDAO;
import services.dbService.dataSets.UsersDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DBServiceImpl implements DBService, Abonent, Runnable {
    private static final String hibernate_show_sql = "false";
    private static final String hibernate_hbm2ddl_auto = "create";

    private final MessageSystem messageSystem;
    private final SessionFactory sessionFactory;
    private Adress adress;

    public DBServiceImpl(MessageSystem messageSystem) {
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
        this.messageSystem = messageSystem;
        adress = new Adress();
    }

    @SuppressWarnings("UnusedDeclaration")
    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example?serverTimezone=UTC");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    @Override
    public Adress getAdress() {
        return adress;
    }

    @Override
    public void run() {
        while(true){
            TimerTask timerTask = new MyTimerTaskForDBS(this);
            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(timerTask, 100, 100);
        }
    }

    public UserProfile getUser(long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UsersDataSet dataSet = dao.get(id);
            session.close();
            return new UserProfile(dataSet);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public UserProfile getUserByLogin(String login) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UsersDataSet dataSet = dao.getUserByLogin(login);
            session.close();
            return new UserProfile(dataSet);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public long addUser(String login, String password) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            long id = dao.insertUser(login, password);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }


    public long addUser(UserProfile userProfile) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            long id = dao.insertUser(userProfile);
            userProfile.setId(id);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public boolean contains(String login){
        Session session = sessionFactory.openSession();
        UsersDAO dao = new UsersDAO(session);
        UsersDataSet dataSet = dao.getUserByLogin(login);
        session.close();
        return dataSet != null;
    }

    @Override
    public StatusOfAuthentication authentication(String login, String password) {
        UserProfile user;
        try {
            user = this.getUserByLogin(login);
        } catch (DBException e) {
            e.printStackTrace();
            return StatusOfAuthentication.UserIsNotRegistered;
        }
        if (!user.getPassword().equals(password)){
            System.out.println("Ошибка: неверный пароль.");
            return StatusOfAuthentication.WrongPassword;
        }
        return StatusOfAuthentication.UserIsAuthenticated;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return messageSystem;
    }


    public List<UserProfile> allUsers(){
        Session session = sessionFactory.openSession();
        UsersDAO dao = new UsersDAO(session);
        return dao.allUsers();
    }


    public void printConnectInfo() {
        try {
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private class MyTimerTaskForDBS extends TimerTask{
        private DBServiceImpl dbService;
        private MyTimerTaskForDBS(DBServiceImpl dbService){
            this.dbService = dbService;
        }
        @Override
        public void run() {
                messageSystem.execForAbonent(dbService);
        }
    }

}
