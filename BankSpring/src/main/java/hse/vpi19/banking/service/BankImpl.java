package hse.vpi19.banking.service;

import hse.vpi19.banking.api.model.AccountDetails;
import hse.vpi19.banking.api.model.UserDetails;
import hse.vpi19.banking.model.Account;
import hse.vpi19.banking.model.Transaction;
import hse.vpi19.banking.api.model.TransactionRequest;
import hse.vpi19.banking.model.User;
import hse.vpi19.banking.exception.ApiException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BankImpl implements Bank {
    private static List<Transaction> transactions = Collections.synchronizedList(new ArrayList<>());
    private static Map<Integer, User> users = new ConcurrentHashMap<>();
    private static AtomicInteger transactionCounter = new AtomicInteger(0);
    private static AtomicInteger accountIdCounter = new AtomicInteger(0);
    private static int transactionThreads = 10;
    private static int usersAmount = 10;
    private static int transactionsAmount = 100;
    private static int minMoneyOnAccount = 100;
    private static int maxMoneyOnAccount = 200;
    private static int minMoneyTransaction = 10;
    private static int maxMoneyTransaction = 200;

    @PostConstruct
    public static void initializeAndPerformRandomTransactions() {
        for (int i = 0; i < usersAmount; i++) {
            User user = new User();
            user.setId(i);
            user.addAccount(new Account(accountIdCounter.getAndIncrement(), new BigDecimal(String.valueOf(getRandomNumber(minMoneyOnAccount, maxMoneyOnAccount)))));
            users.put(user.getId(), user);
        }
        printMoneySum();

        System.out.println(users);

        for (int i = 0 ; i < transactionsAmount; i++) {
            int fromId = getRandomNumber(0, users.size());
            int toId = getRandomNumberExcept(0, users.size(), fromId);
            Transaction transaction = new Transaction(transactionCounter.getAndIncrement(), users.get(fromId).getAccounts().get(0), users.get(toId).getAccounts().get(0),
                    new BigDecimal(String.valueOf(getRandomNumber(minMoneyTransaction, maxMoneyTransaction))));
            transactions.add(transaction);
        }

        ExecutorService transactionExecutor = Executors.newFixedThreadPool(transactionThreads);
        transactions.forEach(transactionExecutor::submit);
        try {
            transactionExecutor.shutdown();
            transactionExecutor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Error waiting termination of transaction executor");
        }
        System.out.println(users);
        System.out.println(transactions);
        printMoneySum();
    }

    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    private static int getRandomNumberExcept(int min, int max, int except) {
        int result;
        do {
            result = getRandomNumber(min, max);
        } while (result == except);
        return result;
    }

    private static void printMoneySum() {
        int summary = 0;
        for (User user: users.values()) {
            for (Account account: user.getAccounts()) {
                summary += account.getMoney().intValue();
            }
        }
        System.out.println("Summary on all accounts: " + summary);
    }

    @Override
    public User getUser(Integer userId) throws ApiException {
        User user = users.get(userId);
        if (user == null) {
            throw new ApiException("User with id " + userId + " not found");
        }
        return user;
    }

    @Override
    public User addUser(Integer userId, UserDetails userDetails) throws ApiException{
        if (!users.containsKey(userId)) {
            User user = new User(userId, userDetails.getFirstName(), userDetails.getLastName());
            users.put(user.getId(), user);
            return user;
        } else {
            throw new ApiException("User already exists");
        }
    }

    @Override
    public User addAccount(Integer userId, AccountDetails accountDetails) throws ApiException {
        User user = users.get(userId);
        if (user == null) {
            throw new ApiException("User with id " + userId + " not found");
        }
        user.addAccount(new Account(accountIdCounter.getAndIncrement(), new BigDecimal(String.valueOf(accountDetails.getAmount()))));
        return user;
    }

    @Override
    public User editAccount(Integer userId, Integer accountId, AccountDetails accountDetails) throws ApiException {
        User user = users.get(userId);
        if (user == null) {
            throw new ApiException("User with id " + userId + " not found");
        }
        Account account = user.getAccounts().stream().filter(acc -> accountId.equals(acc.getId())).findFirst().orElseThrow(
                () -> new ApiException("Account " + accountId + " not found for source user"));
        synchronized (account) {
            account.setMoney(new BigDecimal(String.valueOf(accountDetails.getAmount())));
        }
        return user;
    }

    @Override
    public void deleteUser(Integer id) throws ApiException {
        if (users.remove(id) == null) {
            throw new ApiException("User with id " + id + " not found");
        }
    }

    @Override
    public List<Transaction> getTransactions(){
        return transactions;
    }

    @Override
    public Transaction performTransaction(TransactionRequest transactionRequest) throws ApiException {
        User sourceUser = users.get(transactionRequest.getSourceUserId());
        if (sourceUser == null) {
            throw new ApiException("User with id " + transactionRequest.getSourceUserId() + " not found");
        }
        User targetUser = users.get(transactionRequest.getTargetUserId());
        if (targetUser == null) {
            throw new ApiException("User with id " + transactionRequest.getTargetUserId() + " not found");
        }
        Account sourceAccount = sourceUser.getAccounts().stream().filter(
                account -> transactionRequest.getSourceAccountId().equals(account.getId())).findFirst().orElseThrow(() -> new ApiException("Account " + transactionRequest.getSourceAccountId() + " not found for source user"));
        Account targetAccount = targetUser.getAccounts().stream().filter(
                account -> transactionRequest.getTargetAccountId().equals(account.getId())).findFirst().orElseThrow(() -> new ApiException("Account " + transactionRequest.getTargetAccountId() + " not found for target user"));
        Transaction transaction = new Transaction(transactionCounter.getAndIncrement(), sourceAccount, targetAccount, new BigDecimal(String.valueOf(transactionRequest.getAmount())));
        transaction.run();
        transactions.add(transaction);
        return transaction;
    }
}
