package dbService.dao;

import accountService.Message;
import dbService.dataSets.MessagesDataSet;
import dbService.dataSets.UsersDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class MessagesDAO {
    private Session session;

    public MessagesDAO(Session session) {
        this.session = session;
    }

    public MessagesDataSet get(long id) throws HibernateException {
        return (MessagesDataSet) session.get(MessagesDataSet.class, id);
    }

    public long insertMessage(UsersDataSet sender, UsersDataSet recipient, String message, Date timeOfSending) throws HibernateException {
        return (long) session.save(new MessagesDataSet(sender, recipient, message, timeOfSending));
    }

    public long insertMessage(Message message) throws HibernateException {
        message.setId((long) session.save(message.toMessageDataSet()));
        return message.getId();
    }

    public long insertMessage(MessagesDataSet messagesDataSet){
        messagesDataSet.setId((long) session.save(messagesDataSet));
        return messagesDataSet.getId();
    }

    public List<MessagesDataSet> allMessages(){
        List<MessagesDataSet> listOfDataSet;
        try {
            listOfDataSet= session.createCriteria(MessagesDataSet.class).list();
        } catch (Exception e){
            return null;
        }
        return listOfDataSet;
    }
}
