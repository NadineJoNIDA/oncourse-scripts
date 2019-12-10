import ish.oncourse.server.cayenne.CourseClass
import ish.oncourse.server.cayenne.Enrolment
import ish.oncourse.server.cayenne.Student
import ish.oncourse.server.cayenne.Tutor

CourseClass marketingClass = args.entity

if (!marketingClass || marketingClass.isDistantLearningCourse) {
	return
}


if (marketingClass?.firstSession?.tutors?.empty) {
	return
}


Tutor ccTutor = marketingClass.firstSession.tutors[0]
List<Enrolment> enrolmentList =  new ArrayList<>()

def today = Calendar.getInstance().getTime()
today.set(hourOfDay: 0, minute: 0, second: 0)

def eighteenMonths = today.minus(548) // 18 months ago

/**
 * Iterate over a list of classes the tutor has taught over the last 18 months
 */
List<Student> students = ccTutor.courseClasses.findAll { cc ->  cc.course.id != marketingClass.course.id &&
		cc.firstSession?.startDatetime?.after(eighteenMonths) &&
		cc.firstSession?.startDatetime?.before(today) &&
		cc.successAndQueuedEnrolments.size() > 0
}*.enrolments.flatten().findAll { e -> EnrolmentStatus.SUCCESS == e.status }*.student.flatten().unique()


/**
 * Exclude students that are already enrolled to marketingClass or any class with the same courseId
 */
students.removeAll { s ->
	s.enrolments.any { e -> EnrolmentStatus.SUCCESS == e.status && e.courseClass.course.id == marketingClass.course.id }
}

/**
 * Once you have a non-duped student list, notify all students in the list
 */
students*.contact.flatten().each { student ->

	email {
		template "alert students of related class"
		bindings student: student, tutor: ccTutor.contact, courseClass: marketingClass
		to student
		key "alert students of related classes", marketingClass
		keyCollision "drop"
	}
}

email {
	to preference.email.admin
	subject 'marketing: students updated about upcoming class'
	content "${students.size()} prior students who attended ${ccTutor.getContact().getName(true)}\'s were notified about the upcoming class ${marketingClass?.firstSession?.startDatetime?.format("dd/MM/yyyy")} ${marketingClass.course.name}."
}