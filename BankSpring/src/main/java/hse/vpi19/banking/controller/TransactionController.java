package hse.vpi19.banking.controller;

import hse.vpi19.banking.service.Bank;
import hse.vpi19.banking.exception.ApiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class TransactionController {
    private Bank bank;

    public TransactionController(Bank bank) {
        this.bank = bank;
    }

    @GetMapping("/transaction")
    @ResponseBody
    public String getTransactions() {
        return bank.getTransactions().toString();
    }

    @PostMapping("/transaction")
    @ResponseBody
    public String performTransaction(@RequestBody Map<String, Object> payload) throws ApiException {
        return bank.performTransaction((Integer)payload.get("sourceUserId"), (Integer)payload.get("sourceAccountId"), (Integer)payload.get("targetUserId"),
                (Integer)payload.get("targetAccountId"), (Integer)payload.get("amount")).toString();
    }

}
