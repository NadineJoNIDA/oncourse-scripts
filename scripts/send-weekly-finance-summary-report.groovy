def run(args) {
    def context = args.context
    def accounts = ObjectSelect.query(Account).select(context)

    //this ordering needs to do not in ObjectSelect. It helps to avoid changes after updating Cayenne to 4.0.M2
    Ordering.orderList(accounts, Account.ACCOUNT_CODE.ascs())


    def endDate = Calendar.getInstance().getTime()
    endDate.set(hourOfDay: 0, minute: 0, second: 0)

    def startDate = endDate - 7

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