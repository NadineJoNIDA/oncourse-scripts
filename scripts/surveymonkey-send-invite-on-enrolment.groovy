import ish.integrations.IntegrationType
import ish.integrations.IntegrationsConfiguration

def enrolment = args.value

ObjectSelect.query(Integration).where(Integration.TYPE.eq(IntegrationType.SURVEYMONKEY))
        .select(args.context).findAll { integration ->
    integration.getProperty(IntegrationsConfiguration.SURVEYMONKEY_SEND_ON_ENROLMENT_SUCCESS)?.value?.toBoolean()
}.each { integration ->
    def tag = integration.getProperty(IntegrationsConfiguration.SURVEYMONKEY_COURSE_TAG)?.value?.trim()
    if (tag == null || enrolment.courseClass.course.hasTag(tag)) {
        surveyMonkey {
            name integration.name
            contact enrolment.student.contact
            template "survey invitation"
            reply preference.email.from
        }
    }
}