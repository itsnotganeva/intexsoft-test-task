package by.ganevich.client;

import by.ganevich.dto.ExchangeRateDto;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.enums.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WebClient {

    @Value("${currency-service.url}")
    private String url;

    public ExchangeRateDto getExchangeRate(BankAccount bankAccount) {
        Currency currency = bankAccount.getCurrency();

        RestTemplate restTemplate = new RestTemplate();
        ExchangeRateDto exchangeRate = restTemplate.getForObject(url + currency.name(), ExchangeRateDto.class);
        return exchangeRate;
    }
}
