package zan.api.monobank.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Client info
 * @author azlatov
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientInfo {
    /**
     * Client id
     */
    String clientId;

    /**
     * Client name
     */
    String name;

    /**
     * Webhook URL (@see {@link MonobankApiClient#setWebHook})
     */
    String webHookUrl;

    /**
     * Client accounts
     */
    List<AccountInfo> accounts;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebHookUrl() {
        return webHookUrl;
    }

    public void setWebHookUrl(String webHookUrl) {
        this.webHookUrl = webHookUrl;
    }

    public List<AccountInfo> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountInfo> accounts) {
        this.accounts = accounts;
    }
}
