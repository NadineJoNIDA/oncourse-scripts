import groovy.time.TimeCategory


def courseClass = args.entity

def last5Mins
use(TimeCategory) {
    last5Mins = new Date() - 5.minutes
}

def cancelledEnrolments = Enrolment.STATUS.in(EnrolmentStatus.CANCELLED, EnrolmentStatus.REFUNDED).andExp(Enrolment.MODIFIED_ON.gte(last5Mins)).filterObjects(courseClass.getEnrolments())

cancelledEnrolments.each { e ->
    email {
        template "Class Cancellation"
        to e.student.contact
        bindings enrolment: e
    }
}