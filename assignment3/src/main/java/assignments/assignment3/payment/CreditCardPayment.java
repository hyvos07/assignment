package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem{
    public static double TRANSACTION_FEE_PERCENTAGE = 0.02;

    // Menghitung biaya transaksi dari kartu kredit
    public long countTransactionFee(long amount){
        return (long) (amount * TRANSACTION_FEE_PERCENTAGE);
    }

    @Override
    public long processPayment(long amount) {
        return amount + countTransactionFee(amount);
    }
}
