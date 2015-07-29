csv.delimiter = '\t'

records.each { AccountTransaction t ->
	csv << [
			"Date"          : t.transactionDate?.format("D/M/Y"),
			"Memo"          : t.transactionDescription,
			"Account Number": t.account.accountCode,
			"Debit Amount"  : t.amount.compareTo(Money.ZERO) > 1 ? t.amount.toPlainString() : Money.ZERO.toPlainString(),
			"Credit Amount" : t.amount.compareTo(Money.ZERO) > 1 ? Money.ZERO.toPlainString() : t.amount.toPlainString()
	]
}
