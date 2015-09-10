import ish.integrations.*

def run(args) {
	def e = args.value

	ObjectSelect.query(Integration).where(Integration.TYPE.eq(IntegrationType.MOODLE)).select(args.context)
			.each { integration ->
		def tag = integration.getProperty(IntegrationsConfiguration.MOODLE_COURSE_TAG)?.value?.trim()
		if (tag != null && e.courseClass.course.hasTag(tag)) {
			moodle {
				name integration.name
				enrolment e
			}
		}
	}
}
