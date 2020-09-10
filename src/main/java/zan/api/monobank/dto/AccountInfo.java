package zan.api.monobank.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * Account info
 * @author azlatov
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountInfo {
    /**
     * Account id
     */
    String id;

    /**
     * Currency code
     */
    String currencyCode;

    /**
     * Cashback type
     */
    String cashbackType;

    /**
     * Balance (in minimal money units)
     */
    long balance;

    /**
     * Credit limit (in minimal money units)
     */
    long creditLimit;

    /**
     * List of masked card numbers
     */
    List<String> maskedPan;

    /**
     * Account type
     */
    String type;

    /**
     * Account IBAN code
     */
    String iban;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCashbackType() {
        return cashbackType;
    }

    public void setCashbackType(String cashbackType) {
        this.cashbackType = cashbackType;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(long creditLimit) {
        this.creditLimit = creditLimit;
    }

    public List<String> getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(List<String> maskedPan) {
        this.maskedPan = maskedPan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
