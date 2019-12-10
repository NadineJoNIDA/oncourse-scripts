def paymentIn = args.entity

if (paymentIn.status == PaymentStatus.SUCCESS && paymentIn.confirmationStatus == ConfirmationStatus.NOT_SENT && !Money.ZERO.equals(paymentIn.amount)) {
    email {
        template "Payment Receipt"
        bindings paymentIn: paymentIn
        to paymentIn.payer
    }

    paymentIn.setConfirmationStatus(ConfirmationStatus.SENT)
    args.context.commitChanges()
}