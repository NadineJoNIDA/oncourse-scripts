import ish.oncourse.server.cayenne.Enrolment

Enrolment enrolment = args.value

if (enrolment.courseClass.sessionsCount == 1 && enrolment.courseClass.startDateTime != null &&
        enrolment.courseClass.endDateTime != null && !enrolment.courseClass.tutorRoles.isEmpty()) {

    def classes = ObjectSelect.query(CourseClass)
            .where(CourseClass.START_DATE_TIME.isNotNull()).and(CourseClass.START_DATE_TIME.lt(enrolment.courseClass.endDateTime))
            .and(CourseClass.END_DATE_TIME.isNotNull()).and(CourseClass.END_DATE_TIME.gt(enrolment.courseClass.startDateTime))
            .and(CourseClass.ROOM.eq(enrolment.courseClass.room))
            .and(CourseClass.SESSIONS_COUNT.eq(1))
            .and(CourseClass.ID.ne(enrolment.courseClass.id)).select(args.context)
            .findAll { cc ->
                cc.tutorRoles.collect { tr -> tr.tutor }
                        .contains(enrolment.courseClass.tutorRoles.collect { tr -> tr.tutor }.get(0))
            }

    classes.each { cc -> cc.maximumPlaces = cc.maximumPlaces - 1 }

    args.context.commitChanges()
}
