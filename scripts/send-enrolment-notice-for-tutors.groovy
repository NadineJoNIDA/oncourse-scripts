def enrolment = args.value
if (enrolment.courseClass.hasTag("Notify Manager")) {
    def tutorRoles = enrolment.courseClass.tutorRoles.findAll() { tutorRole ->
        tutorRole.definedTutorRole.name == "Course Manager"
    }
    tutorRoles.each() { tutorRole ->
        email {
            template "Enrolment notification"
            bindings enrolment: enrolment
            to tutorRole.tutor.contact
        }
    }
}