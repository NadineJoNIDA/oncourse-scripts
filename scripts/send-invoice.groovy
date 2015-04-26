def run(args) {
	def invoice = args.entity

	if (invoice.confirmationStatus == ConfirmationStatus.NOT_SENT) {
		if (!Money.ZERO.equals(invoice.getTotalIncTax())) {
			def m = Email.create("Tax Invoice")
			m.bind(invoice: invoice)
			if (invoice.corporatePassUsed) {
				m.to(invoice.contact, invoice.corporatePassUsed.getEmail())
			} else {
				m.to(invoice.contact)	
			}
			m.send()
		}
		
		invoice.setConfirmationStatus(ConfirmationStatus.SENT)
		args.context.commitChanges()
	}  
}