package zan.api.monobank.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import zan.api.monobank.UnixTimeConverter;

import java.time.LocalDateTime;

/**
 * Currency exchange rate
 * @author azlatov
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyExchangeRate {
    /**
     * Code of currency from
     */
    int currencyCodeA;

    /**
     * Code of currency to
     */
    int currencyCodeB;

    /**
     * Rate date
     */
    LocalDateTime date;

    /**
     * Rate to buy
     */
    float rateBuy;

    /**
     * Rate to sell
     */
    float rateSell;

    /**
     * Cross rate
     */
    float rateCross;

    public int getCurrencyCodeA() {
        return currencyCodeA;
    }

    public void setCurrencyCodeA(int currencyCodeA) {
        this.currencyCodeA = currencyCodeA;
    }

    public int getCurrencyCodeB() {
        return currencyCodeB;
    }

    public void setCurrencyCodeB(int currencyCodeB) {
        this.currencyCodeB = currencyCodeB;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDate(int unixTime) {
        this.date = UnixTimeConverter.toDateTime(unixTime);
    }

    public float getRateBuy() {
        return rateBuy;
    }

    public void setRateBuy(float rateBuy) {
        this.rateBuy = rateBuy;
    }

    public float getRateSell() {
        return rateSell;
    }

    public void setRateSell(float rateSell) {
        this.rateSell = rateSell;
    }

    public float getRateCross() {
        return rateCross;
    }

    public void setRateCross(float rateCross) {
        this.rateCross = rateCross;
    }
}
