package hse.vpi19.banking.api.model;

import javax.validation.constraints.NotNull;

public class AccountDetails {
    @NotNull
    private Integer amount;

    public AccountDetails() {
    }

    public AccountDetails(@NotNull Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
