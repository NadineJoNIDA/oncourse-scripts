def run (args) {
	
	CourseClass marketingClass = args.entity

	if (!marketingClass || marketingClass.getIsDistantLearningCourse())
		return
    
    if (!marketingClass?.firstSession?.tutors)
        return
    
    Tutor ccTutor = marketingClass?.firstSession?.tutors?.first()
	List<Enrolment> enrolmentList =  new ArrayList<>()

	def today = Calendar.getInstance().getTime()
    today.set(hourOfDay: 0, minute: 0, second: 0)

	def eighteenMonths = today.minus(548) // 18 months agos

	/**
	 * Iterate over a list of classes the turor has taught over the last 18 months
	 */
	ccTutor.courseClasses.each { cc ->
		if ( cc.id != marketingClass.id && (cc.firstSession?.startDatetime?.after(eighteenMonths) && cc.firstSession?.startDatetime?.before(today)) && (cc.successAndQueuedEnrolments.size() > 0) ) {


			/**
			 * For each enrolment in the courseClass being iterated over,
			 * iterate over all its enrolments, check if it is in the list already, is successful, and that the enrolment is not in the class selected in the UI
			 */
			cc.successAndQueuedEnrolments.each { e ->

				//don't add to enrolmentList enrolments:
				// 1) enrolment was not success
				// 2) enrolled student is already enrolled to marketingClass
				if (e.status == EnrolmentStatus.SUCCESS && !marketingClass.successAndQueuedEnrolments*.student*.id.contains(e.student.id)) {
					enrolmentList.add(e)
				}
			}
		}
	}

	List<Contact> students = new ArrayList<>()
	//create studentList
	students = enrolmentList*.student*.contact.unique()

	/**
	 * Once you have a non-duped student list, notify all students in the list
	 */
	 students.each { student ->

			 email {
				 template "alert students of related class"
				 bindings student: student, tutor: ccTutor.contact, courseClass: marketingClass
				 to student
			 }
	 }

	 email {
	 	to preference.email.admin
	 	subject 'marketing: students updated about upcoming class'
	 	content "${students.size()} prior students who attended ${ccTutor.getContact().getName(true)}\'s were notified about the upcoming class ${marketingClass?.firstSession?.startDatetime?.format("dd/MM/yyyy")} ${marketingClass.course.name}."
	 }
}