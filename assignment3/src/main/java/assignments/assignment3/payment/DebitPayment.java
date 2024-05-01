package assignments.assignment3.payment;

// Class DebitPayment: Implementasi pembayaran dengan kartu debit

public class DebitPayment implements DepeFoodPaymentSystem{
    public static double MINIMUM_TOTAL_PRICE = 50000; // Rp 50.000 minimal total harga
    
    @Override
    public long processPayment(long amount) {
        if(amount < MINIMUM_TOTAL_PRICE){
            return -1; // False return value
        }

        return amount; // Debit langsung potong aja
    }
}
