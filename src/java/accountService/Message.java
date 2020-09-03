package accountService;

import dbService.dataSets.MessagesDataSet;

import java.util.Date;

public class Message {
    private final UserProfile sender;
    private final UserProfile recipient;
    private final String text;
    private final Date timeOfSending;
    private long id;

    public Message(long id, UserProfile sender, UserProfile recipient, String text, Date timeOfSending){
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.text = text;
        this.timeOfSending = (Date)timeOfSending.clone();
    }

    public Message(UserProfile sender, UserProfile recipient, String text, Date timeOfSending){
        this.sender = sender;
        this.recipient = recipient;
        this.text = text;
        this.timeOfSending = (Date)timeOfSending.clone();
    }

    public Message(MessagesDataSet messagesDataSet){
        this.id = messagesDataSet.getId();
        this.recipient = new UserProfile(messagesDataSet.getRecipient());
        this.sender = new UserProfile(messagesDataSet.getSender());
        this.text = messagesDataSet.getMessage();
        this.timeOfSending = (Date)messagesDataSet.getTimeOfSending().clone();
    }

    public long getId(){return id;}

    public UserProfile getSender(){return sender;}

    public UserProfile getRecipient(){return recipient;}

    public String getText(){return text;}

    public Date getTimeOfSending(){return (Date)timeOfSending.clone();}

    public void setId(long id){this.id = id;}

    public MessagesDataSet toMessageDataSet(){
        return new MessagesDataSet(this.id, this.sender.toUsersDataSet(),
                this.recipient.toUsersDataSet(), this.text, this.timeOfSending);
    }

    @Override
    public String toString(){
        return "Message:{" +
                "id=" + id + '\n'+
                "from='" + sender.toString() + '\n'+
                "to='" + recipient +'\n' +
                "with text: " + text + '\n' +
                "time of sending: " + timeOfSending;
    }
}
