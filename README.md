## **Monobank REST API Client**
Monobank REST API Client is a Java library for Monobank REST API (https://api.monobank.ua/docs/).
Provides information about currency exchange rates, user accounts, account statements.

### Quick build
To build library, you'll need:
- Java 1.8+
- Maven 3.5 or later
- Run Maven, specifying a location where the library should be installed:
`mvn -DdistributionTargetDir="<PATH>/monobank-api-client-1.0-SNAPSHOT" clean package`

### Sample Usage
You'll first need to obtain an access token on https://api.monobank.ua/.

#### Initialize Client
```
MonobankApiClient client = new MonobankApiClient();
client.setToken("<your_acess_token>");
```

#### Get statements by account
```
ClientInfo clientInfo = client.getClientInfo();
AccountInfo accountInfo = clientInfo.getAccounts().get(0);
List<AccountTransaction> accountTransactions = client.getAccountTransactions(
        accountInfo.getId(), 
        LocalDateTime.parse("2020-08-15T00:00:00"), 
        LocalDateTime.parse("2020-08-30T23:59:59"));
```

