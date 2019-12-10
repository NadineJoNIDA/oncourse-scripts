def enrolment = args.entity

if (enrolment.status == EnrolmentStatus.SUCCESS && enrolment.confirmationStatus == ConfirmationStatus.NOT_SENT) {

    // Get FieldCongigurations of enum type "Survey"
    def fieldConfigurations = ObjectSelect.query(FieldConfiguration)
            .where(FieldConfiguration.INT_TYPE.eq(4))
            .select(args.context)

    // Set flag for rendering survey block in email template
    def onEnrolmentSurvey = fieldConfigurations.find { fc ->
        fc.getDeliverySchedule() == DeliverySchedule.ON_ENROL && enrolment.courseClass.course.getFieldConfigurationSchema().getConfigurations().contains(fc)
    }

    email {
        template "Enrolment Confirmation Survey"
        bindings enrolment: enrolment, survey: onEnrolmentSurvey
        to enrolment.student.contact
    }

    enrolment.setConfirmationStatus(ConfirmationStatus.SENT)
    args.context.commitChanges()
}