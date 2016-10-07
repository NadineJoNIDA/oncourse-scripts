def run(args) {
    def dayAfterTomorrowStart = new Date() + 2
    dayAfterTomorrowStart.set(hourOfDay: 0, minute: 0, second: 0)

    def dayAfterTomorrowEnd = new Date() + 3
    dayAfterTomorrowEnd.set(hourOfDay: 0, minute: 0, second: 0)

    def context = args.context

    def classesStartingTomorrow = ObjectSelect.query(CourseClass)
            .where(CourseClass.IS_CANCELLED.eq(false))
            .and(CourseClass.START_DATE_TIME.ne(null))
            .and(CourseClass.START_DATE_TIME.between(dayAfterTomorrowStart, dayAfterTomorrowEnd))
            .select(context)

    classesStartingTomorrow.findAll { cc ->
        cc.successAndQueuedEnrolments.size() >= cc.minimumPlaces
    }*.tutorRoles.flatten().each() { role ->
        email {
            template "Tutor notice of class commencement"
            bindings courseClass: role.courseClass, tutor: role.tutor
            to role.tutor.contact
        }
    }
}