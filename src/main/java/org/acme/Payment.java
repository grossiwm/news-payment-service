package org.acme;

import java.math.BigDecimal;
import java.util.Calendar;

public class Payment {

    public Long id; 
    public Calendar requestDate;
    public Calendar processedDate;
    public BigDecimal amount;
    public CreditCardDetails creditCardDetails;
    
}
