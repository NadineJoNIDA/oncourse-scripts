def run(args) {
  def application = args.entity
  
  def m = Email.create("Enrolment application received")
  m.bind(application: application)
  m.to(application.student.contact)

  m.send()
}