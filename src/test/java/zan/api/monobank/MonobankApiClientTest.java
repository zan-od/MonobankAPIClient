package zan.api.monobank;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import zan.api.monobank.dto.AccountInfo;
import zan.api.monobank.dto.AccountTransaction;
import zan.api.monobank.dto.ClientInfo;
import zan.api.monobank.dto.CurrencyExchangeRate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;
import static zan.api.monobank.ApplicationConfig.*;

public class MonobankApiClientTest {

    private final ApplicationConfig testConfig = new ApplicationConfig(ENVIRONMENT_TEST);

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(testConfig.getServerPort());

    public MonobankApiClientTest() throws IOException {
    }

    @Test
    public void getClientInfo() throws IOException {
        // given
        MonobankApiClient client = new MonobankApiClient(testConfig);

        wireMockRule.stubFor(get("/personal/client-info")
                .willReturn(okJson(
                        "{\"clientId\": \"123\", " +
                        "\"name\": \"UserName\"}")));

        // when
        ClientInfo clientInfo = client.getClientInfo();

        // then
        assertNotNull(clientInfo);
        assertEquals("123", clientInfo.getClientId());
        assertEquals("UserName", clientInfo.getName());
    }

    @Test
    public void clientInfoIgnoreUnknownProperties() throws IOException {
        // given
        MonobankApiClient client = new MonobankApiClient(testConfig);

        wireMockRule.stubFor(get("/personal/client-info")
                .willReturn(okJson(
                        "{\"clientId\": \"123\", " +
                                "\"unknownProperty\": \"value\"}")));

        // when
        ClientInfo clientInfo = client.getClientInfo();

        // then
        assertNotNull(clientInfo);
        assertEquals("123", clientInfo.getClientId());
        assertNull(clientInfo.getName());
    }

    @Test
    public void accountInfoIgnoreUnknownProperties() throws IOException {
        // given
        MonobankApiClient client = new MonobankApiClient(testConfig);

        wireMockRule.stubFor(get("/personal/client-info")
                .willReturn(okJson(
                        "{\"accounts\": [{\"id\": \"1\", \"unknownProperty\": \"value\"}]}")));

        // when
        ClientInfo clientInfo = client.getClientInfo();

        // then
        assertNotNull(clientInfo);
        assertEquals(1, clientInfo.getAccounts().size());
        assertEquals("1", clientInfo.getAccounts().get(0).getId());
    }

    @Test
    public void getClientInfoHandleJsonError() throws IOException {
        // given
        MonobankApiClient client = new MonobankApiClient(testConfig);

        wireMockRule.stubFor(get("/personal/client-info")
                .willReturn(aResponse().withStatus(403).withBody(
                        "{\"errorDescription\": \"Description\"}")));

        // when
        ClientInfo clientInfo = null;
        RuntimeException actualException = null;
        try {
            client.getClientInfo();
        } catch (RuntimeException e) {
            actualException = e;
        }

        // then
        assertNull(clientInfo);
        assertNotNull(actualException);
        assertEquals("HTTP error 403: Description", actualException.getMessage());
    }

    @Test
    public void getClientInfoHandleError() throws IOException {
        // given
        MonobankApiClient client = new MonobankApiClient(testConfig);

        wireMockRule.stubFor(get("/personal/client-info")
                .willReturn(aResponse().withStatus(404).withBody(
                        "Error Description")));

        // when
        ClientInfo clientInfo = null;
        RuntimeException actualException = null;
        try {
            client.getClientInfo();
        } catch (RuntimeException e) {
            actualException = e;
        }

        // then
        assertNull(clientInfo);
        assertNotNull(actualException);
        assertEquals("HTTP error 404: Error Description", actualException.getMessage());
    }

    @Test
    public void getCurrencyExchangeRates() throws IOException {
        // given
        MonobankApiClient client = new MonobankApiClient(testConfig);

        wireMockRule.stubFor(get("/bank/currency")
                .willReturn(okJson(
                        "[{\"currencyCodeA\":840,\"currencyCodeB\":980,\"date\":1598735406,\"rateBuy\":27.4,\"rateSell\":27.6243}]")));

        // when
        List<CurrencyExchangeRate> rates = client.getCurrencyExchangeRates();

        // then
        assertNotNull(rates);
        assertEquals(1, rates.size());
        assertEquals(840, rates.get(0).getCurrencyCodeA());
        assertEquals(980, rates.get(0).getCurrencyCodeB());
        assertEquals(LocalDateTime.parse("2020-08-29T21:10:06"), rates.get(0).getDate());
    }

    @Test
    public void getDefaultAccountTransactions() throws IOException {
        // given
        MonobankApiClient client = new MonobankApiClient(testConfig);

        wireMockRule.stubFor(get("/personal/statement/0/1597449600/1598831999")
                .willReturn(okJson(
                        "[{\"id\":\"a1\",\"time\":1598806344,\"description\":\"test\",\"amount\":-56810,\"balance\":10000,\"hold\":true}]")));

        // when
        List<AccountTransaction> transactions = client.getDefaultAccountTransactions(LocalDateTime.parse("2020-08-15T00:00:00"), LocalDateTime.parse("2020-08-30T23:59:59"));

        // then
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        assertEquals(-56810, transactions.get(0).getAmount());
        assertEquals("a1", transactions.get(0).getId());
        assertEquals(LocalDateTime.parse("2020-08-30T16:52:24"), transactions.get(0).getTime());
    }

    @Test
    public void findAccountByCardNumber() throws IOException {
        // given
        MonobankApiClient client = new MonobankApiClient(testConfig);

        wireMockRule.stubFor(get("/personal/client-info")
                .willReturn(okJson(
                        "{\"accounts\":[{\"id\":\"1\", \"maskedPan\":[\"567812******1234\"]}," +
                                "{\"id\":\"2\", \"maskedPan\":[\"123456******5678\"]}]}")));

        // when
        Optional<AccountInfo> account = client.findAccountByCardNumber("1234560000005678");

        // then
        assertTrue(account.isPresent());
        assertEquals("2", account.get().getId());
    }
}