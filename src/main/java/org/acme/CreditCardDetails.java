package org.acme;

public class CreditCardDetails {
    public String holder;
    public String number;
    public String validThru;
    public String securityCode;

    public CreditCardDetails(String holder, String number, String validThru, String securityCode) {
        this.holder = holder;
        this.number = number;
        this.validThru = validThru;
        this.securityCode = securityCode;
    }

    public CreditCardDetails() {
    }

    
}
