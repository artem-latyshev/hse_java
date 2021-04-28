package hse.vpi19.banking.service;

import hse.vpi19.banking.api.model.AccountDetails;
import hse.vpi19.banking.api.model.UserDetails;
import hse.vpi19.banking.model.Transaction;
import hse.vpi19.banking.api.model.TransactionRequest;
import hse.vpi19.banking.model.User;
import hse.vpi19.banking.exception.ApiException;

import java.util.List;

public interface Bank {
    User getUser(Integer userId) throws ApiException;
    User addUser(Integer userId, UserDetails userDetails) throws ApiException;
    User addAccount(Integer userId, AccountDetails accountDetails) throws ApiException;
    User editAccount(Integer userId, Integer accountId, AccountDetails accountDetails) throws ApiException;
    void deleteUser(Integer id)throws ApiException;
    List<Transaction> getTransactions();
    Transaction performTransaction(TransactionRequest transactionRequest) throws ApiException;
}
