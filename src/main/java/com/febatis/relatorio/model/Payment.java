package com.febatis.relatorio.model;


import com.febatis.relatorio.enums.PaymentType;

public class Payment {

    private Long id;

    private String description;

    private PaymentType paymentType;

    private double value;

    public Payment() {
    }

    public Payment(String description, PaymentType paymentType, double value) {
        this.description = description;
        this.paymentType = paymentType;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
