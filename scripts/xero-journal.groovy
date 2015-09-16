import ish.integrations.*

def run(args) {
	def start = new Date() - 1
	start.set(hourOfDay: 0, minute: 0, second: 0, millisecond: 0)

	def end = new Date()
	end.set(hourOfDay: 0, minute: 0, second: 0, millisecond: 0)

	def transactions = ObjectSelect.query(AccountTransaction).where(AccountTransaction.CREATED_ON.between(start, end))

	ObjectSelect.query(Integration).where(Integration.TYPE.eq(IntegrationType.XERO)).select(args.context)
			.each { integration ->
		xero {
			name integration.name
			narration "onCourse transaction summary for period ${start.format("h a d MMM yyyy")} to ${end.format("h a d MMM yyyy")}"
			transactions transactions
		}
	}
}