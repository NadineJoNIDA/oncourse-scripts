import ish.integrations.IntegrationType

def start = new Date() - 1
start.set(hourOfDay: 0, minute: 0, second: 0, millisecond: 0)

def end = new Date()
end.set(hourOfDay: 0, minute: 0, second: 0, millisecond: 0)

def accountTransactions = ObjectSelect.query(AccountTransaction).where(AccountTransaction.CREATED_ON.between(start, end)).select(args.context)

ObjectSelect.query(Integration).where(Integration.TYPE.eq(IntegrationType.XERO)).select(args.context)
        .each { integration ->
            xero {
                name integration.name
                narration "onCourse transaction summary for period ${start.format("h a d MMM yyyy")} to ${end.format("h a d MMM yyyy")}"
                transactions accountTransactions
            }
        }