def run(args) {
    def enrolment = args.entity

    email {
        template "Enrolment Cancellation"
        to enrolment.student.contact
        bindings enrolment : enrolment
    }
}