package by.ganevich.entity;

public class BankAccount {
    private Long id;
    private String currency;
    private double amountOfMoney;
    private Long clientId;
    private Long bankId;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", amountOfMoney=" + amountOfMoney +
                ", clientId=" + clientId +
                ", bankId=" + bankId +
                '}';
    }
}
