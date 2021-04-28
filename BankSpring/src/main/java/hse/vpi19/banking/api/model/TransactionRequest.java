package hse.vpi19.banking.api.model;

import javax.validation.constraints.NotNull;

public class TransactionRequest {
    @NotNull
    private Integer sourceUserId;
    @NotNull
    private Integer sourceAccountId;
    @NotNull
    private Integer targetUserId;
    @NotNull
    private Integer targetAccountId;
    @NotNull
    private Integer amount;

    public TransactionRequest() {
    }

    public TransactionRequest(Integer sourceUserId, Integer sourceAccountId, Integer targetUserId, Integer targetAccountId, Integer amount) {
        this.sourceUserId = sourceUserId;
        this.sourceAccountId = sourceAccountId;
        this.targetUserId = targetUserId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
    }

    public Integer getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(Integer sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public Integer getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Integer sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Integer getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Integer targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Integer getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(Integer targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
