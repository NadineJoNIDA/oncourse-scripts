def usiRequiredDate = Date.parse("dd/MM/yyyy", "01/01/2015")
def monthBefore = new Date().minus(30)

def context = args.context

def enrolmentsWithoutUsi = ObjectSelect.query(Enrolment)
        .where(Enrolment.CREATED_ON.gt(monthBefore))
        .and(Enrolment.STATUS.eq(EnrolmentStatus.SUCCESS))
        .and(Enrolment.COURSE_CLASS.dot(CourseClass.COURSE).dot(Course.COURSE_MODULES).ne(null))
        .and(Enrolment.STUDENT.dot(Student.USI).eq(null))
        .and(Enrolment.COURSE_CLASS.dot(CourseClass.END_DATE_TIME).eq(null).orExp(Enrolment.COURSE_CLASS.dot(CourseClass.END_DATE_TIME).gt(usiRequiredDate)))
        .select(context)

enrolmentsWithoutUsi.each() { enrolment ->
    email {
        template "USI reminder email"
        bindings enrolment: enrolment
        to enrolment.student.contact
    }
}

email {
    from preference.email.admin
    subject 'USI reminder email notification'
    to preference.email.admin
    content "A USI reminder was sent to ${enrolmentsWithoutUsi.size()} students enrolled in VET classes who have not supplied their USI."
}

