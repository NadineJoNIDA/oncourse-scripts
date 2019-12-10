import ish.oncourse.server.cayenne.CourseClass
import ish.oncourse.server.cayenne.Message

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
                bindings courseClass: courseClass, contact: e.student.contact
            }
        }
    }
    //send notification to admin
    else {
        Message lastStudentMessage = ObjectSelect.query(Message.class)
                .where(Message.CREATOR_KEY.eq(MessageUtils.generateCreatorKey("send-class-confirmed", courseClass)))
                .orderBy(Message.ID.desc())
                .selectFirst(args.context)

        if (lastStudentMessage) {

            //Attention! Set your own 'prefix'
            String prefix = "class no viable " + preference.email.from
            String messageUniqueKey = MessageUtils.generateCreatorKey(prefix, courseClass)
            Message lastAdminMessage = ObjectSelect.query(Message.class)
                    .where(Message.CREATOR_KEY.eq(messageUniqueKey))
                    .orderBy(Message.ID.desc())
                    .selectFirst(args.context)

            if (!lastAdminMessage || lastAdminMessage.getCreatedOn() < lastStudentMessage.getCreatedOn()) {
                email {

                    from preference.email.from
                    to preference.email.admin
                    subject "Confirmed class now below class minimum"
                    key messageUniqueKey
                    content "Due to enrolment cancellations or other class changes ${courseClass.code} ${courseClass.course.name}, is no longer viable. It has already been confirmed as running via emails to the students. Please investigate. "
                }
            }
        }
    }
}