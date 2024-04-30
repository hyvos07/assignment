package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem{
    public static double MINIMUM_TOTAL_PRICE = 50000; // Rp 50.000
    
    @Override
    public long processPayment(long amount) {
        if(amount < MINIMUM_TOTAL_PRICE){
            return -1;
        }
        
        return amount;
    }
}
