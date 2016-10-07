def run(args) {
  def voucher = args.entity
  
  if (voucher.status == ProductStatus.ACTIVE && voucher.confirmationStatus == ConfirmationStatus.NOT_SENT) {
    email {
      template "Voucher Email"
      bindings voucher: voucher
      to (voucher.redeemableBy ? voucher.redeemableBy : voucher.invoiceLine.invoice.contact)
    }
    
    voucher.setConfirmationStatus(ConfirmationStatus.SENT)
    args.context.commitChanges()
  }
}