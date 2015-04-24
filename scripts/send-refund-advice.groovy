def run(args) {
  def paymentOut = args.entity

  if (paymentOut.status == PaymentStatus.SUCCESS && paymentOut.confirmationStatus == ConfirmationStatus.NOT_SENT) {
    def m = Email.create("Refund advice")
    m.bind(paymentOut: paymentOut)
    m.to(paymentOut.payee)

    m.send()

    paymentOut.setConfirmationStatus(ConfirmationStatus.SENT)
    args.context.commitChanges()
  }
}