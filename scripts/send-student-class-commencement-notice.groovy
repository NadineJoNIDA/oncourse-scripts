def run(args) {
    def tomorrowStart = new Date() + 1
    tomorrowStart.set(hourOfDay: 0, minute: 0, second: 0)

    def tomorrowEnd = new Date() + 2
    tomorrowEnd.set(hourOfDay: 0, minute: 0, second: 0)

    def context = args.context

    def classesStartingTomorrow = ObjectSelect.query(CourseClass)
            .where(CourseClass.IS_CANCELLED.eq(false))
            .and(CourseClass.START_DATE_TIME.ne(null))
            .and(CourseClass.START_DATE_TIME.between(tomorrowStart, tomorrowEnd))
            .select(context)

    classesStartingTomorrow.findAll { cc ->
        cc.successAndQueuedEnrolments.size() >= cc.minimumPlaces
    }*.successAndQueuedEnrolments.flatten().each() { enrolment ->
        email {
            template "Student notice of class commencement"
            bindings enrolment: enrolment
            to enrolment.student.contact
        }
    }
}