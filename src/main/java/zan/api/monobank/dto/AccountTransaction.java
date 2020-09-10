package zan.api.monobank.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import zan.api.monobank.UnixTimeConverter;

import java.time.LocalDateTime;

/**
 * Account transaction
 * @author azlatov
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountTransaction {
    /**
     * Transaction id
     */
    String id;

    /**
     * Transaction date
     */
    LocalDateTime time;

    /**
     * Transaction description
     */
    String description;

    /**
     * Merchant Category Code (ISO 18245)
     */
    int mcc;

    /**
     * Transaction amount (in minimal money units, should be divided by 100 for UAH)
     */
    long amount;


    /**
     * Operation amount (in minimal money units, should be divided by 100 for UAH)
     */
    long operationAmount;

    /**
     * Transaction currency code
     */
    int currencyCode;

    /**
     * Bank commission rate (in minimal money units)
     */
    long commissionRate;

    /**
     * Transaction cashback amount (in minimal money units)
     */
    long cashbackAmount;

    /**
     * Account balance after transaction (in minimal money units)
     */
    long balance;

    /**
     * Transaction is hold
     */
    boolean hold;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setTime(int unixTime) {
        this.time = UnixTimeConverter.toDateTime(unixTime);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMcc() {
        return mcc;
    }

    public void setMcc(int mcc) {
        this.mcc = mcc;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(long operationAmount) {
        this.operationAmount = operationAmount;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    public long getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(long commissionRate) {
        this.commissionRate = commissionRate;
    }

    public long getCashbackAmount() {
        return cashbackAmount;
    }

    public void setCashbackAmount(long cashbackAmount) {
        this.cashbackAmount = cashbackAmount;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isHold() {
        return hold;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }
}
