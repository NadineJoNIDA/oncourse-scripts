def run(args) {

    def context = args.context

    def endOfDate = Calendar.getInstance().getTime()
    endOfDate.set(hourOfDay: 0, minute: 0, second: 0)
    def startOfDate = endOfDate -1

    def sessions = ObjectSelect.query(Session)
            .where(Session.START_DATETIME.between(startOfDate, endOfDate))
            .and(Session.COURSE_CLASS.dot(CourseClass.IS_CANCELLED).eq(false))
            .and(Session.COURSE_CLASS.dot(CourseClass.IS_ACTIVE).eq(true))
            .select(context)

    //uncomment this section to only run for courses tagged with 'checkAttendance'
//    sessions = sessions.findAll { s ->
//        s.courseClass.course.hasTag('checkAttendance')
//    }


    def sessionsWithUnmarkedAttendance = sessions.findAll { s ->
        s.attendance.findAll { a ->
            AttendanceType.UNMARKED.equals(a.attendanceType)
        }.size() > 0
    }

    if (!sessionsWithUnmarkedAttendance.empty) {
        def bodyContent = [
                'Dear Admin,',
                '',
                "The following sessions ran on ${startOfDate.format("d/M/yy")} and had unmarked attendance:"
        ]


        sessionsWithUnmarkedAttendance.each { s ->
            def tutors = s.tutors*.contact*.fullName.flatten()
            def tutorString = tutors.size() > 0 ? "Tutor${tutors.size() > 1 ? 's' : ''}: ${tutors.join(", ")}" : ''

            bodyContent << ''
            bodyContent << "${s.courseClass.course.name} (${s.courseClass.uniqueCode}) ${tutorString}"
            bodyContent << "Enrolled: ${s.courseClass.successAndQueuedEnrolments.size()}"
            bodyContent << "Unmarked attendance: ${s.attendance.findAll{ a -> AttendanceType.UNMARKED.equals(a.attendanceType)}.size()}"
            bodyContent << "Absent: ${s.attendance.findAll{ a -> [AttendanceType.DID_NOT_ATTEND_WITH_REASON, AttendanceType.DID_NOT_ATTEND_WITHOUT_REASON].contains(a.attendanceType)}.size()}"
            bodyContent << "Attended: ${s.attendance.findAll{ a -> [AttendanceType.ATTENDED, AttendanceType.PARTIAL].contains(a.attendanceType)}.size()}"

        }

        email {
            from preference.email.from
            subject 'Notification of unmarked attendance'
            to preference.email.admin
            content bodyContent.join(System.getProperty('line.separator'))
        }
    }
}

