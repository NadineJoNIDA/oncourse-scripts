def run(args) {
    def context = args.context
    def waitingLists = context.select(SelectQuery.query(WaitingList))

    waitingLists.each() { waitingList ->
        def courseClasses = []
        waitingList.course.courseClasses.each() { courseClass ->
            if (courseClass.isActive && courseClass.isShownOnWeb && courseClass.successAndQueuedEnrolments.size() < courseClass.maximumPlaces) {
                if (courseClass.isDistantLearningCourse) {
                    courseClasses.add(courseClass)
                } else if (new Date() < courseClass.endDateTime) {
                    courseClasses.add(courseClass)
                }
            }
        }
        if (courseClasses.size() > 0) {
            def m = Email.create("Waiting List reminder")
            m.bind(waitingList : waitingList)
            m.bind(courseClasses : courseClasses)
            m.to(waitingList.student.contact)
            m.send()

            context.commitChanges()
        }
    }
    return null
}