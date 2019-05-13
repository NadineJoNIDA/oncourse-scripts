def run(args) {

	def yesterdayStart = new Date() - 1
	yesterdayStart.set(hourOfDay: 0, minute: 0, second: 0)

	def yesterdayEnd = new Date()
	yesterdayEnd.set(hourOfDay: 0, minute: 0, second: 0)

	def context = args.context

	def enrolments = ObjectSelect.query(Enrolment)
			.where(Enrolment.COURSE_CLASS.dot(CourseClass.COURSE).dot(Course.IS_VET).eq(true))
			.and(Enrolment.OUTCOMES.dot(Outcome.MODIFIED_ON).between(yesterdayStart, yesterdayEnd))
			.select(context)

	enrolments = enrolments.findAll { e ->  e.outcomes.findAll { o -> OutcomeStatus.STATUS_NOT_SET.equals(o.status) }.empty }

	enrolments.each { e ->
				
		def unlinkedOutcomes = e.outcomes.findAll { o -> o.certificateOutcomes.empty }
		int successfulOutcomesCount = unlinkedOutcomes.findAll { o -> OutcomeStatus.STATUSES_VALID_FOR_CERTIFICATE.contains(o.status) }.size()
		
		if (successfulOutcomesCount > 0) {
			boolean fullQualification = e.courseClass.course.isSufficientForQualification
			boolean validToCertificate = successfulOutcomesCount == e.outcomes.size()

			context.newObject(Certificate).with { certificate ->
				certificate.setStudent(e.student)
				certificate.setQualification(e.courseClass.course.qualification)
				certificate.setAwardedOn(new Date())
				unlinkedOutcomes.each { o ->
					certificate.addToOutcomes(o)
				}

				if (fullQualification && validToCertificate) {
					certificate.setIsQualification(true)
				} else {
					certificate.setIsQualification(false)
				}
			}
			context.commitChanges()
		}
	}
}