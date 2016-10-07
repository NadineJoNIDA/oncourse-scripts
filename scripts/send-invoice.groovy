def run(args) {
	def invoice = args.entity

	if (invoice.confirmationStatus == ConfirmationStatus.NOT_SENT) {
		if (!Money.ZERO.equals(invoice.totalIncTax)) {
			email {
				template "Tax Invoice"
				bindings invoice: invoice
				to invoice.contact >> (invoice.corporatePassUsed ? invoice.corporatePassUsed.email : invoice.contact.email)
			}
		}
		
		invoice.setConfirmationStatus(ConfirmationStatus.SENT)
		args.context.commitChanges()
	}  
}