def run(args) {
    def courseClass = args.entity

    courseClass.successAndQueuedEnrolments.each { e ->
        email {
            template "Class Cancellation"
            to enrolment.student.contact
            bindings enrolment : e
        }
    }
}