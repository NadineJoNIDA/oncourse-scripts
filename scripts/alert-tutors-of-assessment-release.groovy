def today = new Date()
today.set(hourOfDay: 0, minute: 0, second: 0)

def context = args.context

def assessmentsReleasedToday = ObjectSelect.query(AssessmentClass)
		.where(AssessmentClass.RELEASE_DATE.between(today, today+1))
		.and(AssessmentClass.ASSESSMENT.dot(Assessment.ACTIVE).eq(true))
		.select(context)

assessmentsReleasedToday.each { a ->
	a.courseClass.tutorRoles.each { tr ->
		email {
			to tr.tutor.contact
			template "alert tutors of assessment release"
			bindings tutor: tr.tutor, assessment: a
		}
	}
}