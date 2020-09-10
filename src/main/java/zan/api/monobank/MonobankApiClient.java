package zan.api.monobank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import zan.api.monobank.connector.HttpApiConnector;
import zan.api.monobank.connector.HttpApiResponse;
import zan.api.monobank.dto.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static zan.api.monobank.ApplicationConfig.*;

/**
 * Client for Monobank REST API
 * @author azlatov
 */
public class MonobankApiClient {

    private static final String CLIENT_INFO_URL = "/personal/client-info";
    private static final String CURRENCY_RATE_URL = "/bank/currency";
    private static final String ACCOUNT_TRANSACTIONS_URL = "/personal/statement";
    private static final String WEB_HOOK_URL = "/personal/webhook";

    /**
     * Security token {@see https://api.monobank.ua/}
     */
    private String token;
    private final ApplicationConfig applicationConfig;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * Creates client with default configuration
     * @throws IOException
     */
    public MonobankApiClient() throws IOException {
        this(new ApplicationConfig(ENVIRONMENT_DEFAULT));
    }

    /**
     * Creates client with custom configuration
     * @param applicationConfig configuration
     * @throws IOException
     */
    public MonobankApiClient(ApplicationConfig applicationConfig) throws IOException {
        this.applicationConfig = applicationConfig;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * API method retrieves client information
     * @return info about current client
     * @throws IOException
     */
    public ClientInfo getClientInfo() throws IOException {
        HttpApiConnector connector = new HttpApiConnector(applicationConfig, token);
        HttpApiResponse response = connector.get(CLIENT_INFO_URL);

        if (response.getCode() != 200) {
            handleApiError(response);
        }

        return jsonMapper.readValue(response.getResponse(), ClientInfo.class);
    }

    /**
     * API method retrieves currency exchange rates
     * @return list of currency exchange rates
     * @throws IOException
     */
    public List<CurrencyExchangeRate> getCurrencyExchangeRates() throws IOException {
        HttpApiConnector connector = new HttpApiConnector(applicationConfig, token);
        HttpApiResponse response = connector.get(CURRENCY_RATE_URL);

        if (response.getCode() != 200) {
            handleApiError(response);
        }

        return jsonMapper.readValue(response.getResponse(), new TypeReference<List<CurrencyExchangeRate>>(){});
    }

    /**
     * API method retrieves account transactions
     * @param accountId account ID ({@link AccountInfo#getId()})
     * @param from date from
     * @param to date to
     * @return list of account transactions
     * @throws IOException
     */
    public List<AccountTransaction> getAccountTransactions(String accountId, LocalDateTime from, LocalDateTime to) throws IOException {
        String url = String.format(ACCOUNT_TRANSACTIONS_URL + "/%s/%s/%s",
                accountId, UnixTimeConverter.toUnixTime(from), UnixTimeConverter.toUnixTime(to));
        HttpApiConnector connector = new HttpApiConnector(applicationConfig, token);
        HttpApiResponse response = connector.get(url);

        if (response.getCode() != 200) {
            handleApiError(response);
        }

        return jsonMapper.readValue(response.getResponse(), new TypeReference<List<AccountTransaction>>(){});
    }

    /**
     * Retrieves transactions of default account
     * @param from date from
     * @param to date to
     * @return list of account transactions
     * @throws IOException
     */
    public List<AccountTransaction> getDefaultAccountTransactions(LocalDateTime from, LocalDateTime to) throws IOException {
        return getAccountTransactions("0", from, to);
    }

    /**
     * Finds account by card number
     * @param accounts list of accounts
     * @param cardNumber card number
     * @return account
     * @throws IOException
     */
    public Optional<AccountInfo> findAccountByCardNumber(List<AccountInfo> accounts, String cardNumber) throws IOException {
        String cardMask = getCardMask(cardNumber);
        return accounts.stream()
                .filter(a -> a.getMaskedPan().contains(cardMask))
                .findFirst();
    }

    /**
     * Finds account by card number for current client
     * @param cardNumber card number
     * @return account
     * @throws IOException
     */
    public Optional<AccountInfo> findAccountByCardNumber(String cardNumber) throws IOException {
        return findAccountByCardNumber(getClientInfo().getAccounts(), cardNumber);
    }

    private String getCardMask(String cardNumber) {
        return cardNumber.substring(0, 6) + "******" + cardNumber.substring(12);
    }

    /**
     * Sets URL for web hook
     * @param webHookUrl url
     */
    public void setWebHook(String webHookUrl) throws IOException {
        String body = jsonMapper.writeValueAsString(new WebHookRequest(webHookUrl));

        HttpApiConnector connector = new HttpApiConnector(applicationConfig, token);
        HttpApiResponse response = connector.post(WEB_HOOK_URL, body);

        if (response.getCode() != 200) {
            handleApiError(response);
        }
    }

    private void handleApiError(HttpApiResponse response) {
        throw new RuntimeException(String.format("HTTP error %s: %s", response.getCode(), extractErrorMessage(response.getResponse())));
    }

    private String extractErrorMessage(String response) {
        if (isJSON(response)) {
            ErrorMessage errorMessage = null;
            try {
                errorMessage = jsonMapper.readValue(response, ErrorMessage.class);
            } catch (JsonProcessingException e) {
                // cannot parse JSON, show raw message
            }

            if (errorMessage != null) {
                return errorMessage.getErrorDescription();
            }
        }

        return response;
    }

    private boolean isJSON(String data) {
        return data.startsWith("{");
    }
}
