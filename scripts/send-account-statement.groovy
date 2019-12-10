def contactList = ObjectSelect.query(Contact)
        .where(Contact.EMAIL.isNotNull())
        .select(args.context)
contactList.findAll { contact -> contact.totalOwing > 0 }.each { c ->
    email {
        from preference.email.from
        to c.email
        subject "Account statement"
        content "Dear ${c.fullName}, \n\n Your statement from ${preference.college.name} is attached. The total outstanding on the account is ${c?.totalOwing}. \n\n" \
	 					   + "If you wish to pay by credit card or view the invoice visit ${c.getPortalLink('history', 30)} \n\n" \
						   + "If you need to speak about this statement or use another payment method, please contact us on ${Preferences.get("avetmiss.phone")}. \n\n" \
                           + "Regards,\n" \
						   + "${preference.college.name}"
        attachment "Statement_Report.pdf", "application/pdf", report {
            keycode "ish.onCourse.statementReport.all"
            records Arrays.asList(c)
            background "statement_record_background.pdf"
        }
    }
}
