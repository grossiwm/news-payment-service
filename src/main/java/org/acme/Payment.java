package org.acme;

import java.math.BigDecimal;
import java.util.Calendar;

public class Payment {

    public Long id; 
    public Calendar requestDate;
    public Calendar processedDate;
    public BigDecimal amount;
    public CreditCardDetails creditCardDetails;

    

    public Payment(Long id, Calendar requestDate, Calendar processedDate, BigDecimal amount,
            CreditCardDetails creditCardDetails) {
        this.id = id;
        this.requestDate = requestDate;
        this.processedDate = processedDate;
        this.amount = amount;
        this.creditCardDetails = creditCardDetails;
    }



    public Payment() {
    }

    
    
}
