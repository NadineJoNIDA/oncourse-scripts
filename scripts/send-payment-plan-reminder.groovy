import org.apache.commons.lang3.time.DateUtils

def run(args) {

    def context = args.context

    def today = Calendar.getInstance().getTime()
    today.set(hourOfDay: 0, minute: 0, second: 0)
    def plusWeek = today + 8 //this day and 7 days include last day


    def invoices = ObjectSelect.query(Invoice)
            .where(Invoice.AMOUNT_OWING.gt(Money.ZERO))
            .and(Invoice.DATE_DUE.lte(plusWeek))
            .select(context)

    invoices.findAll { i ->
        DateUtils.isSameDay(today + 7, i.dateDue) || // 7 days before the payment due date
                DateUtils.isSameDay(today, i.dateDue) || // day the payment is due
                (((today - i.dateDue) % 7 == 0) && i.overdue.isGreaterThan(Money.ZERO)) // every 7 days of overdue
    }.each { i ->
        email {
            template "Payment reminder"
            bindings invoice: i
            to i.contact
        }
    }




}