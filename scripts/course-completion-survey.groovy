// Ended yesterday
def endDate = Calendar.getInstance().getTime()
endDate.set(hourOfDay: 0, minute: 0, second: 0)

def classes = ObjectSelect.query(CourseClass)
        .where(CourseClass.IS_CANCELLED.eq(false))
        .and(CourseClass.END_DATE_TIME.between(endDate - 1, endDate))
        .select(args.context)

classes.each { c ->
    if (!c.hasTag("no survey")) {
        c.successAndQueuedEnrolments.each { e ->
            email {
                template "Course completion survey"
                bindings enrolment: e
                to e.student.contact
            }
        }
    }
}