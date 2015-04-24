def run(args) {
  def voucher = args.entity
  
  if (voucher.status == ProductStatus.ACTIVE && voucher.confirmationStatus == ConfirmationStatus.NOT_SENT) {
    def m = Email.create("Voucher Email")
    m.bind(voucher: voucher)
    
    if (voucher.redeemableBy) {
      m.to(voucher.redeemableBy)
    } else {
      m.to(voucher.invoiceLine.invoice.contact)
    }
    
    m.send()
    
    voucher.setConfirmationStatus(ConfirmationStatus.SENT)
    args.context.commitChanges()
  }
}