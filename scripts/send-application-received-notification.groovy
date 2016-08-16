def run(args) {
  def application = args.entity
  
  def m = Email.create("Enrolment application received")
  m.bind(application: application)
  m.to(application.student.contact)

  m.send()

				smtp {
				to preference.email.admin
				subject "Application for enrolment received"
				content "Hi \n\n${application.student.contact.fullName} has just applied for ${application.course.name}. \n\nThis is an automated notification from onCourse to advise follow up may be required"
			}
}