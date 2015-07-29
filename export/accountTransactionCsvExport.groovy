records.each { AccountTransaction t ->
	csv << [
			"Transaction date"   : t.transactionDate?.format("DD-MM-Y hh:mm:ss"),
			"Account code"       : t.account.accountCode,
			"Account description": t.account.description,
			"Account type"       : t.account.type.displayName,
			"Amount"             : t.amount?.toPlainString(),
			"Source"             : t.source,
			"Invoice number"     : t.invoiceNumber,
			"Payment type"       : t.paymentType,
			"Contact name"       : t.contactName,
			"id"                 : t.id
	]
}
