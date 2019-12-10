def paymentOut = args.entity

if (paymentOut.status == PaymentStatus.SUCCESS && paymentOut.confirmationStatus == ConfirmationStatus.NOT_SENT) {
    email {
        template "Refund advice"
        bindings paymentOut: paymentOut
        to paymentOut.payee
    }

    paymentOut.setConfirmationStatus(ConfirmationStatus.SENT)
    args.context.commitChanges()
}