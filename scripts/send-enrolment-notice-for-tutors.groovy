def run(args) {
    def enrolment = args.value
    if (enrolment.courseClass.hasTag("Notify Manager")) {
        def tutorRoles = enrolment.courseClass.tutorRoles.findAll() { tutorRole ->
            tutorRole.definedTutorRole.name == "Course Manager"
        }
        tutorRoles.each() { tutorRole ->
            def m = Email.create("Enrolment notification")
            m.bind(enrolment: enrolment)
            m.to(tutorRole.tutor.contact)
            m.send()
        }
        args.context.commitChanges()
    }
}