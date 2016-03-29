def run(args) {
    def endDate = Calendar.getInstance().getTime()
    endDate.set(hourOfDay: 0, minute: 0, second: 0)

    def allClasses = ObjectSelect.query(CourseClass)
            .where(CourseClass.END_DATE_TIME.between(endDate - 1, endDate))
            .and(CourseClass.IS_CANCELLED.eq(false))
            .select(args.context)

    def nonVetClasses = allClasses.findAll { cc -> cc.course.courseModules.isEmpty() }

    //change 'notVETClasses' below to 'allClasses' to create attendance certificates for all classes, including those with Units of Competency attached
    nonVetClasses.each { cc ->

        //Uncomment the line below to create attendance certificates for enrolments with attendance over 80%
        //def enrolmentsOver80 = cc.successAndQueuedEnrolments.findAll { e -> e.attendancePercent >= 80 }

        cc.successAndQueuedEnrolments.each { e ->
            def printData = report {
                keycode "ish.oncourse.nonVetCertificate"
                records Arrays.asList(e)
                background "certificate_attendance_backgound.pdf"
            }

            def docData = document {
                action "create"
                content printData
                name "${cc.uniqueCode}_${e.student.contact.lastName}_${e.student.contact.firstName}_Certificate_Attendance.pdf"
                mimeType "image/pdf"
                permission AttachmentInfoVisibility.STUDENTS
                attach e
            }

            email {
                template "Certificate available"
                bindings enrolment: e
                to e.student.contact
            }
        }
    }
}