package com.uniba.di.dfmdevelop.labservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PaymentDetailDTO {

    @NotEmpty
    @Size(min = 1, max = 3, message = "Il campo deve indicare il simbolo della valuta o la sigla es: EUR, USD, €, $")
    private String currency;

    @NotEmpty
    @Size(min = 1, max = 10, message = "Il campo deve indicare un importo valido")
    private Double amount;

    @NotEmpty
    @Size(min = 1, max = 20, message = "Il campo deve indicare un metodo di pagamento valido")
    private String method;

    @NotEmpty
    @Size(min = 1, max = 100, message = "Il campo deve indicare il motivo della transazione")
    private String description;

    public PaymentDetailDTO(String currency,
                            Double amount,
                            String method,
                            String description) {
        this.currency = currency;
        this.amount = amount;
        this.method = method;
        this.description = description;
    }

    public PaymentDetailDTO() { super(); }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PaymentDetailDTO{" +
                "currency='" + currency + '\'' +
                ", amount=" + amount +
                ", method='" + method + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
