import ish.integrations.*

def run(args) {
	def yesterdayStart = new Date() - 1
	yesterdayStart.set(hourOfDay: 0, minute: 0, second: 0, millisecond: 0)

	def yesterdayEnd = new Date()
	yesterdayEnd.set(hourOfDay: 0, minute: 0, second: 0, millisecond: 0)

	def enrolmentsJustEnded = ObjectSelect.query(Enrolment)
			.where(Enrolment.COURSE_CLASS.dot(CourseClass.END_DATE_TIME).between(yesterdayStart, yesterdayEnd)).select(args.context)

	ObjectSelect.query(Integration).where(Integration.TYPE.eq(IntegrationType.SURVEYGIZMO))
			.select(args.context).findAll { integration ->
		integration.getProperty(IntegrationsConfiguration.SURVEYGIZMO_SEND_ON_ENROLMENT_COMPLETION)?.value?.toBoolean()
	}.each { integration ->
		def tag = integration.getProperty(IntegrationsConfiguration.SURVEYGIZMO_COURSE_TAG)?.value?.trim()

		enrolmentsJustEnded.each { enrolment ->
			if (tag == null	|| enrolment.courseClass.course.hasTag(tag)) {
				surveyGizmo {
					name integration.name
					template "survey invitation"
					reply preference.email.from
					contact enrolment.student.contact
				}
			}
		}
	}
}
