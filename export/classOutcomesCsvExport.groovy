records.collectMany { CourseClass cc -> cc.enrolments }.collectMany { e -> e.outcomes }.each { o ->
	csv << [
			"studentNumber": o.enrolment.student.studentNumber,
			"firstName"    : o.enrolment.student.contact.firstName,
			"lastName"     : o.enrolment.student.contact.lastName,
			"classCode"    : "${o.enrolment.courseClass.course.code}-${o.enrolment.courseClass.code}",
			"nationalCode" : o.module?.nationalCode,
			"title"        : o.module?.title,
			"startDate"    : o.startDate?.format("D/M/Y h:m a"),
			"endDate"      : o.endDate?.format("D/M/Y h:m a"),
			"status"       : o.status?.displayName
	]
}
