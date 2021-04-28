package hse.vpi19.banking.controller;

import hse.vpi19.banking.api.model.AccountDetails;
import hse.vpi19.banking.api.model.UserDetails;
import hse.vpi19.banking.service.Bank;
import hse.vpi19.banking.exception.ApiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {
    private Bank bank;

    public UserController(Bank bank) {
        this.bank = bank;
    }

    @GetMapping("/user/{userId}")
    @ResponseBody
    public String getUser(@PathVariable("userId") Integer userId) throws ApiException {
        return bank.getUser(userId).toString();
    }

    @PostMapping("/user/{userId}")
    @ResponseBody
    public String addUser(@PathVariable("userId") Integer userId, @RequestBody UserDetails userDetails) throws ApiException {
        return bank.addUser(userId, userDetails).toString();
    }

    @PostMapping("/user/{userId}/account")
    @ResponseBody
    public String addAccount(@PathVariable("userId") Integer userId, @Valid @RequestBody AccountDetails accountDetails) throws ApiException {
        return bank.addAccount(userId, accountDetails).toString();
    }

    @PutMapping("/user/{userId}/account/{accountId}")
    @ResponseBody
    public String editAccount(@PathVariable("userId") Integer userId, @PathVariable("accountId") Integer accountId, @Valid @RequestBody AccountDetails accountDetails) throws ApiException {
        return bank.editAccount(userId, accountId, accountDetails).toString();
    }

    @DeleteMapping("/user/{userId}")
    @ResponseBody
    public String deleteUser(@PathVariable("userId") Integer userId) throws ApiException {
        bank.deleteUser(userId);
        return "User " + userId + " deleted successfully";
    }
}
