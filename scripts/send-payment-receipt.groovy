def run(args) {
  def paymentIn = args.entity

  if (paymentIn.status == PaymentStatus.SUCCESS && paymentIn.confirmationStatus == ConfirmationStatus.NOT_SENT && !Money.ZERO.equals(paymentIn.amount)) {
    def m = Email.create("Payment Receipt")
    m.bind(paymentIn: paymentIn)
    m.to(paymentIn.payer)

    m.send()

    paymentIn.setConfirmationStatus(ConfirmationStatus.SENT)
    args.context.commitChanges()
  }
}