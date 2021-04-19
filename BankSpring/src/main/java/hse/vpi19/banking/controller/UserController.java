package hse.vpi19.banking.controller;

import hse.vpi19.banking.service.Bank;
import hse.vpi19.banking.exception.ApiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public String addUser(@PathVariable("userId") Integer userId, @RequestBody Map<String, Object> payload) throws ApiException {
        return bank.addUser(userId, (String)payload.get("firstName"), (String)payload.get("lastName")).toString();
    }

    @PostMapping("/user/{userId}/account")
    @ResponseBody
    public String addAccount(@PathVariable("userId") Integer userId, @RequestBody Map<String, Object> payload) throws ApiException {
        return bank.addAccount(userId, (Integer)payload.get("amount")).toString();
    }

    @PutMapping("/user/{userId}/account/{accountId}")
    @ResponseBody
    public String editUser(@PathVariable("userId") Integer userId, @PathVariable("accountId") Integer accountId, @RequestBody Map<String, Object> payload) throws ApiException {
        return bank.editUser(userId, accountId, (Integer)payload.get("amount")).toString();
    }

    @DeleteMapping("/user/{userId}")
    @ResponseBody
    public String deleteUser(@PathVariable("userId") Integer userId) throws ApiException {
        bank.deleteUser(userId);
        return "User " + userId + " deleted successfully";
    }
}
