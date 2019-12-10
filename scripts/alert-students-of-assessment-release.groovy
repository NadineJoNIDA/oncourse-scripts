def today = new Date()
today.set(hourOfDay: 0, minute: 0, second: 0)

def context = args.context

def assessmentsReleasedToday = ObjectSelect.query(AssessmentClass)
        .where(AssessmentClass.RELEASE_DATE.between(today, today + 1))
        .and(AssessmentClass.ASSESSMENT.dot(Assessment.ACTIVE).eq(true))
        .select(context)

def assesmentsGrouped = [:]

assessmentsReleasedToday.each { ac ->
    ac.courseClass.successAndQueuedEnrolments*.student.flatten().unique().each { s ->
        if (!assesmentsGrouped[s]) {
            assesmentsGrouped[s] = []
        }
        assesmentsGrouped[s] << ac
    }
}

assesmentsGrouped.each { student, assessmentClassList ->
    email {
        to student.contact
        template "alert students of assessment release"
        bindings student: student, assessments: assessmentClassList
    }

}