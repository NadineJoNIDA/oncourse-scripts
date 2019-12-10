def oneWeek = new Date() + 1
oneWeek.set(hourOfDay: 0, minute: 0, second: 0)

def oneWeekOneDay = new Date() + 8
oneWeekOneDay.set(hourOfDay: 0, minute: 0, second: 0)

ObjectSelect.query(Membership)
        .where(Membership.EXPIRY_DATE.between(oneWeek, oneWeekOneDay))
        .select(args.context)
        .each { membership ->
            email {
                template "Membership Notification Renewal"
                bindings membership: membership
                to membership.contact
            }
        }