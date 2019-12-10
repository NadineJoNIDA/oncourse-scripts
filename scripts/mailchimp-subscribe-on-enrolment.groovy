def e = args.value

if (e.student.contact.email && e.student.contact.allowEmail) {
    mailchimp {
        name "Enrolment"
        action "subscribe"
        email e.student.contact.email
        firstName e.student.contact.firstName
        lastName e.student.contact.lastName
    }
}
