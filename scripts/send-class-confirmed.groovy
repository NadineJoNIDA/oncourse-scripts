def run(args) {
    List<CourseClass> classes = ObjectSelect.query(CourseClass)
            .select(args.context)

    //to change profit condition of class confirm, just set 'expectedProfit' with value you want. $500 is the default
    def expectedProfit = 500

    for (CourseClass courseClass : classes) {
        //conditions to send 'Class Confirmed' messages to students
        if (courseClass.getSuccessAndQueuedEnrolments().size() >= courseClass.getMinimumPlaces() && courseClass.actualTotalProfit >= expectedProfit) {
            courseClass.getSuccessAndQueuedEnrolments().each { e ->
                email {
                    template "Class Confirmed"
                    key "send-class-confirmed", courseClass
                    keyCollision "drop"
                    to e.student.contact
                    bindings courseClass: courseClass
                }
            }
        }
        //send notification to admin
        else {
            Message lastStudentMessage = MessageUtils.getLastMessageByKey(MessageUtils.generateCreatorKey("send-class-confirmed", courseClass), args.context)

            if (lastStudentMessage){

                //Attention! Set your own 'prefix'
                String prefix = "class no viable natalia.borisova@sydney.edu.au"
                String messageUniqueKey = MessageUtils.generateCreatorKey(prefix, courseClass)
                Message lastAdminMessage = MessageUtils.getLastMessageByKey(messageUniqueKey, args.context)

                if (!lastAdminMessage || lastAdminMessage.getCreatedOn() < lastStudentMessage.getCreatedOn()) {
                    email {

                        //Attention! Set your own 'from' and 'to'
                        from "info@sydney.edu.au"
                        to "natalia.borisova@sydney.edu.au"
                        subject "Confirmed class now below class minimum"
                        key messageUniqueKey
                        content "Due to enrolment cancellations or other class changes ${courseClass.code} ${courseClass.course.name}, is no longer viable. It has already been confirmed as running via emails to the students. Please investigate. "
                    }
                }
            }
        }
    }
}