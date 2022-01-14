package by.ganevich.entity;

import java.util.Date;

public class Transaction {
    private Long id;
    private Long senderId;
    private Long recipientId;
    private Double amountOfMoney;
    private Date date;

    public Long getSenderId() {
        return senderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(Double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", amountOfMoney=" + amountOfMoney +
                ", date=" + date +
                '}';
    }
}
