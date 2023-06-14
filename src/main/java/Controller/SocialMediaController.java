package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.post("/messages", this::postMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postUserLoginHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    public void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    public void getMessageByIdHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messagess = messageService.RetrieveMessageById(message_id);
        if(messagess == null){
            messagess = null;
        }else
            ctx.json(messagess);
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage == null || addedMessage.message_text.length() >= 255 || addedMessage.message_text.isEmpty()){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedMessage));
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        System.out.println(updatedMessage);
        if(updatedMessage == null || updatedMessage.message_text.length() >= 255 || updatedMessage.message_text.isEmpty()){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }
    }
 
    public void deleteMessageHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messag = messageService.removeMessage(message_id);
        if(messag == null){
            ctx.status(200);
        }else
            ctx.json(messag);
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount == null || addedAccount.password.length() <= 4 || addedAccount.username.equals("")){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }

    public void postUserLoginHandler(Context ctx)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account accountss = accountService.RetrieveUserLogin(account);
        if(accountss == null){
            ctx.status(401);
        }else
            ctx.json(mapper.writeValueAsString(accountss));
    }

    public void getAllMessagesByUserHandler(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> msg = messageService.getAllMessagesUser(id);
        ctx.json(msg).status(200);
    }

}