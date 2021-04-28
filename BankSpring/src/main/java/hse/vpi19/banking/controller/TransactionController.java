package hse.vpi19.banking.controller;

import hse.vpi19.banking.api.model.TransactionRequest;
import hse.vpi19.banking.service.Bank;
import hse.vpi19.banking.exception.ApiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String performTransaction(@Valid @RequestBody TransactionRequest transactionRequest) throws ApiException {
        return bank.performTransaction(transactionRequest).toString();
    }

}
