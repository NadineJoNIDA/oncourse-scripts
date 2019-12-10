def nextWeekStart = new Date() + 7
nextWeekStart.set(hourOfDay: 0, minute: 0, second: 0)

def nextWeekEnd = new Date() + 8
nextWeekEnd.set(hourOfDay: 0, minute: 0, second: 0)

def context = args.context

def exp = CourseClass.IS_CANCELLED.eq(false)
        .andExp(CourseClass.START_DATE_TIME.ne(null))
        .andExp(CourseClass.START_DATE_TIME.between(nextWeekStart, nextWeekEnd))

def classesStartingTomorrow = context.select(SelectQuery.query(CourseClass, exp))

classesStartingTomorrow.each() { courseClass ->
    courseClass.successAndQueuedEnrolments.each() { enrolment ->
        def m = Email.create("Student notice of class commencement")
        m.bind(enrolment: enrolment)

        m.to(enrolment.student.contact)

        m.send()

        context.commitChanges()
    }
}