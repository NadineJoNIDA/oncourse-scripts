import ish.integrations.IntegrationType
import ish.integrations.IntegrationsConfiguration

def yesterdayStart = new Date() - 1
yesterdayStart.set(hourOfDay: 0, minute: 0, second: 0, millisecond: 0)

def yesterdayEnd = new Date()
yesterdayEnd.set(hourOfDay: 0, minute: 0, second: 0, millisecond: 0)

def enrolmentsJustEnded = ObjectSelect.query(Enrolment)
        .where(Enrolment.COURSE_CLASS.dot(CourseClass.END_DATE_TIME).between(yesterdayStart, yesterdayEnd)).select(args.context)

ObjectSelect.query(Integration).where(Integration.TYPE.eq(IntegrationType.SURVEYMONKEY))
        .select(args.context).findAll { integration ->
    integration.getProperty(IntegrationsConfiguration.SURVEYMONKEY_SEND_ON_ENROLMENT_COMPLETION)?.value?.toBoolean()
}.each { integration ->
    def tag = integration.getProperty(IntegrationsConfiguration.SURVEYMONKEY_COURSE_TAG)?.value?.trim()

    enrolmentsJustEnded.each { enrolment ->
        if (tag == null || enrolment.courseClass.course.hasTag(tag)) {
            surveyMonkey {
                name integration.name
                contact enrolment.student.contact
                template "survey invitation"
                reply preference.email.from
            }
        }
    }
}