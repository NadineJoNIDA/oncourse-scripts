def run(args) {
  def usiRequiredDate = Date.parse("dd/MM/yyyy", "01/01/2015")
  def monthBefore = new Date().minus(30)

  def context = args.context

  def exp = Enrolment.CREATED_ON.gt(monthBefore)
        .andExp(Enrolment.STATUS.eq(EnrolmentStatus.SUCCESS))
        .andExp(Enrolment.COURSE_CLASS.dot(CourseClass.COURSE).dot(Course.IS_VET).eq(true))
        .andExp(Enrolment.STUDENT.dot(Student.USI).eq(null))
        .andExp(Enrolment.COURSE_CLASS.dot(CourseClass.END_DATE_TIME).eq(null).orExp(Enrolment.COURSE_CLASS.dot(CourseClass.END_DATE_TIME).gt(usiRequiredDate)));

  def enrolmentsWithoutUsi = context.select(SelectQuery.query(Enrolment, exp))

  enrolmentsWithoutUsi.each() {
    def m = Email.create("USI reminder email")
    m.bind(enrolment: it)

    m.to(it.student.contact)

    m.send()

    context.commitChanges()
  }
}