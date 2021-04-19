package hse.vpi19.banking.service;

import hse.vpi19.banking.model.Transaction;
import hse.vpi19.banking.model.User;
import hse.vpi19.banking.exception.ApiException;

import java.util.List;

public interface Bank {
    User getUser(Integer userId) throws ApiException;
    User addUser(Integer userId, String firstName, String lastName) throws ApiException;
    User addAccount(Integer userId, Integer amount) throws ApiException;
    User editUser(Integer userId, Integer accountId, Integer amount) throws ApiException;
    void deleteUser(Integer id)throws ApiException;
    List<Transaction> getTransactions();
    Transaction performTransaction(Integer sourceUserId, Integer sourceAccountId, Integer targetUserId, Integer targetAccountId, Integer amount) throws ApiException;
}
