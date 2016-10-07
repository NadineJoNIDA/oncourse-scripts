def run(args) {
    def context = args.context

    def waitingLists = ObjectSelect.query(WaitingList)
            .select(context)

    waitingLists.each() { waitingList ->
        def courseClasses = waitingList.course.courseClasses.findAll() { courseClass ->
            courseClass.isActive && courseClass.isShownOnWeb && courseClass.successAndQueuedEnrolments.size() < courseClass.maximumPlaces && (courseClass.isDistantLearningCourse || new Date() < courseClass.startDateTime)
        }
        
        if (courseClasses.size() > 0) {
            email {
                template "Waiting List reminder"
                bindings waitingList : waitingList, courseClasses : courseClasses
                to waitingList.student.contact
            }
        }
    }
    return null
}