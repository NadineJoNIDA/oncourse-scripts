def run(args) {
  def dayAfterTomorrowStart = new Date() + 2
  dayAfterTomorrowStart.set(hourOfDay: 0, minute: 0, second: 0)

  def dayAfterTomorrowEnd = new Date() + 3
  dayAfterTomorrowEnd.set(hourOfDay: 0, minute: 0, second: 0)

  def context = args.context

  def exp = CourseClass.IS_CANCELLED.eq(false)
        .andExp(CourseClass.START_DATE_TIME.ne(null))
        .andExp(CourseClass.START_DATE_TIME.between(dayAfterTomorrowStart, dayAfterTomorrowEnd))

  def classesStartingTomorrow = context.select(SelectQuery.query(CourseClass, exp))

  classesStartingTomorrow.each() { courseClass ->
    courseClass.tutorRoles.each() { role ->
      def m = Email.create("Tutor notice of class commencement")
      m.bind(courseClass: courseClass)
      m.bind(tutor: role.tutor)

      m.to(role.tutor.contact)

      m.send()

      context.commitChanges()
    }
  }
}