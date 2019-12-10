import ish.integrations.IntegrationType

def start = new Date() - 1
start.set(hourOfDay: 0, minute: 0, second: 0, millisecond: 0)

def end = new Date()
end.set(hourOfDay: 0, minute: 0, second: 0, millisecond: 0)

def accountTransactions = ObjectSelect.query(AccountTransaction).where(AccountTransaction.CREATED_ON.between(start, end)).select(args.context)

ObjectSelect.query(Integration).where(Integration.TYPE.eq(IntegrationType.MYOB)).select(args.context)
        .each { integration ->
            myob {
                name integration.name
                transactions accountTransactions
            }
        }