package zan.api.monobank.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Request to set web hook url
 * @author azlatov
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebHookRequest {
    /**
     * Web hook URL
     */
    private String webHookUrl;

    public WebHookRequest(String webHookUrl) {
        this.webHookUrl = webHookUrl;
    }

    public String getWebHookUrl() {
        return webHookUrl;
    }

    public void setWebHookUrl(String webHookUrl) {
        this.webHookUrl = webHookUrl;
    }
}
