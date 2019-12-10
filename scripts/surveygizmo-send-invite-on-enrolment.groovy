import ish.integrations.IntegrationType
import ish.integrations.IntegrationsConfiguration

def enrolment = args.value

ObjectSelect.query(Integration).where(Integration.TYPE.eq(IntegrationType.SURVEYGIZMO))
        .select(args.context).findAll { integration ->
    integration.getProperty(IntegrationsConfiguration.SURVEYGIZMO_SEND_ON_ENROLMENT_SUCCESS)?.value?.toBoolean()
}.each { integration ->
    def tag = integration.getProperty(IntegrationsConfiguration.SURVEYGIZMO_COURSE_TAG)?.value?.trim()
    if (tag == null || enrolment.courseClass.course.hasTag(tag)) {
        surveyGizmo {
            name integration.name
            template "survey invitation"
            reply preference.email.from
            contact enrolment.student.contact
        }
    }
}
