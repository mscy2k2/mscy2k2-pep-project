package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    AccountDAO accountDAO;

     //No-args constructor for a AccountService instantiates a plain accountDAO.
     public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    
    public Account addAccount(Account account){
        Account insertedAccount = accountDAO.insertAccount(account);
        return insertedAccount;
    }

    public Account RetrieveAccountById(int account_id){
        Account accountById = accountDAO.getAccountById(account_id);
        return accountById;
    }
    
}
