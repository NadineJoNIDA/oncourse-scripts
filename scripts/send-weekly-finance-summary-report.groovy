import java.time.LocalDate

def run(args) {
    def context = args.context
    def accounts = ObjectSelect.query(Account)
            .orderBy(Account.ACCOUNT_CODE.ascs())
            .select(context)

    //If you want to change date period use one of the sections below:

    //set period in number of days (e.g. for the last 7 days)
    LocalDate endDate = LocalDate.now().minusDays(1)
    LocalDate startDate = endDate.minusDays(6)



//    set period in calendar months (e.g. for the last month)
//    LocalDate endDate = LocalDate.now().minusMonths(1)
//    endDate = endDate.withDayOfMonth(endDate.lengthOfMonth())
//    LocalDate startDate = endDate.withDayOfMonth(1)

    email {
        from preference.email.from
        to preference.email.admin
        subject "onCourse transaction summary ${startDate.format("dd/MM/yy")} to ${endDate.format("dd/MM/yy")}"
        content "'Trial Balance' report for the previous 7 days."
        attachment "Trial_Balance.pdf", "application/pdf", report {
                                                                    keycode "ish.onCourse.trialBalance"
                                                                    records accounts
                                                                    param 'localdateRange_from' : startDate, 'localdateRange_to' : endDate
                                                                }
    }
}