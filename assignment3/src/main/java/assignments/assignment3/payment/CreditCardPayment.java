package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem {
    private static final double TRANSACTION_FEE_PERCENTAGE = 0.02;

    @Override
    public double processPayment(double saldo, double amount) {
        return amount + (amount * TRANSACTION_FEE_PERCENTAGE);
    }

    public double getTransactionFeePercentage(long amount) {
        return (amount * TRANSACTION_FEE_PERCENTAGE);
    }
}