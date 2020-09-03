package dbService.dataSets;

import accountService.Message;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "messages")
public class MessagesDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;
    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", unique = false, updatable = false)
    private UsersDataSet sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", unique = false, updatable = false)
    private UsersDataSet recipient;

    @Column(name = "message", unique = false, updatable = false)
    private String message;

    @Column(name = "time", unique = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfSending;

    @SuppressWarnings("UnusedDeclaration")
    public MessagesDataSet() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public MessagesDataSet(UsersDataSet sender, UsersDataSet recipient, String message, Date timeOfSending) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.timeOfSending = (Date)timeOfSending.clone();
    }

    @SuppressWarnings("UnusedDeclaration")
    public MessagesDataSet(long id, UsersDataSet sender, UsersDataSet recipient, String message, Date timeOfSending) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.timeOfSending = (Date)timeOfSending.clone();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UsersDataSet getRecipient() {
        return recipient;
    }

    public UsersDataSet getSender(){
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimeOfSending(){return timeOfSending;}

    @Override
    public String toString(){
        return "MessageDataSet:{" +
                "id=" + id + '\n'+
                "from='" + sender.toString() + '\n'+
                "to='" + recipient +'\n' +
                "with text: " + message + '\n' +
                "time of sending: " + timeOfSending;
    }
}
