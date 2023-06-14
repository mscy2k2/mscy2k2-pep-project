package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    //No-args constructor for a messageService instantiates a plain messageDAO.
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message RetrieveMessageById(int message_id){
        Message messageById = messageDAO.getMessageById(message_id);
        return messageById;
    }

    public Message addMessage(Message message){
        Message insertedMessage = messageDAO.insertMessage(message);
        return insertedMessage;
    }

    public Message updateMessage(int message_id, Message message){
        
        messageDAO.updateMessage(message_id, message);
                    
        return messageDAO.getMessageById(message_id);
    }

    public Message removeMessage(int message_id){         
            
        return messageDAO.getMessageById(message_id);
    }

    public List<Message> getAllMessagesUser(int userId) {
       
        return messageDAO.getAllMessagesByUser(userId);
    }
}
