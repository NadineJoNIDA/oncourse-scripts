def run(args) {
    def context = args.context
    def waitingLists = context.select(SelectQuery.query(WaitingList))

    waitingLists.each() { waitingList ->
        def courseClasses = waitingList.course.courseClasses.findAll() { courseClass ->
            courseClass.isActive && courseClass.isShownOnWeb && courseClass.successAndQueuedEnrolments.size() < courseClass.maximumPlaces && (courseClass.isDistantLearningCourse || new Date() < courseClass.startDateTime)
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