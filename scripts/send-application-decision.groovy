def run(args) {
  def application = args.entity

  if (application.confirmationStatus == ConfirmationStatus.NOT_SENT) {
    def m = null

    if (application.status == ApplicationStatus.OFFERED) {
      m = Email.create("Enrolment application accepted")
    } else if (application.status == ApplicationStatus.REJECTED) {
      m = Email.create("Enrolment application rejected")
    }

    if (m) {
      m.bind(application: application)
      m.to(application.student.contact)

      m.send()

      application.setConfirmationStatus(ConfirmationStatus.SENT)
      args.context.commitChanges()
    }
  }
}