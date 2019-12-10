def application = args.entity

email {
    template "Enrolment application received"
    bindings application: application
    to application.student.contact
}

email {
    to preference.email.admin
    subject "Application for enrolment received"
    content "Hi \n\n${application.student.contact.fullName} has just applied for ${application.course.name}. \n\nThis is an automated notification from onCourse to advise follow up may be required"
}