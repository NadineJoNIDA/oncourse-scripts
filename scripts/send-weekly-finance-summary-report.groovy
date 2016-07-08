import org.apache.commons.lang3.time.DateUtils

def run(args) {
    def context = args.context
    def accounts = ObjectSelect.query(Account)
            .orderBy(Account.ACCOUNT_CODE.ascs())
            .select(context)

    //If you want to change date period use one of the sections below:

    //set period in number of days (e.g. for the last 7 days)
    def endDate = Calendar.getInstance().getTime()
    endDate.set(hourOfDay: 0, minute: 0, second: 0)
    def startDate = endDate - 7

    
//    set period in calendar months (e.g. for the last month)
    
//    def endDate = Calendar.getInstance().getTime()
//    endDate.set(dayOfMonth: 1,hourOfDay: 0, minute: 0, second: 0)
//    println endDate
//    def startDate = DateUtils.addMonths(endDate, -1)

    smtp {
        from preference.email.from
        to preference.email.admin
        subject "onCourse transaction summary ${startDate.format("dd/MM/yy")} to ${endDate.format("dd/MM/yy")}"
        content "'Trial Balance' report for the previous 7 days."
        attachment "Trial_Balance.pdf", "application/pdf", report {
                                                                    keycode "ish.onCourse.trialBalance"
                                                                    records accounts
                                                                    param 'dateRange_from' : startDate, 'dateRange_to' : endDate
                                                                }
    }
}