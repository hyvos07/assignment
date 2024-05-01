package assignments.assignment3.payment;

// Class CreditCardPayment: Implementasi pembayaran dengan kartu kredit

public class CreditCardPayment implements DepeFoodPaymentSystem{
    public static final double TRANSACTION_FEE_PERCENTAGE = 0.02; // 2% Fee dari kartu kredit (dibebankan pada user aja)

    // Menghitung biaya transaksi dari kartu kredit
    public long countTransactionFee(long amount){
        return (long) (amount * TRANSACTION_FEE_PERCENTAGE);
    }

    @Override
    public long processPayment(long amount) {
        return amount + countTransactionFee(amount); // Jumlah saldo yang harus dipotong ke saldo user
    }
}
