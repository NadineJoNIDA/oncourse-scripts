def run(args) {
    def currentDate = new Date()
    def allCertificates = ObjectSelect.query(Certificate)
            .where(Certificate.STUDENT.dot(Student.USI_STATUS).eq(UsiStatus.VERIFIED))
            .and(Certificate.PRINTED_ON.isNull())
            .select(args.context)

    allCertificates.each { c ->
        def eLsit = c.student.enrolments.findAll { e ->
            c.qualification.objectId == e.courseClass.course?.qualification?.objectId &&
                    EnrolmentStatus.STATUSES_LEGIT.contains(e.status) &&
                    e.documents.find { d -> d.name == "${e.courseClass.uniqueCode}_${c.student.contact.lastName}_${c.student.contact.firstName}_Certificate.pdf" } == null
        }

        if (eLsit.size() > 0) {
            def printData = report {
                keycode "ish.onCourse.certificate"
                records Arrays.asList(c)
            }

            eLsit.each { e ->
                document {
                    action "create"
                    content printData
                    name "${e.courseClass.uniqueCode}_${c.student.contact.lastName}_${c.student.contact.firstName}_Certificate.pdf"
                    mimeType "image/pdf"
                    permission AttachmentInfoVisibility.STUDENTS
                    attach e
                }

                email {
                    template "Certificate available"
                    bindings enrolment: e
                    to e.student.contact
                }

                c.setPrintedOn(currentDate)
                args.context.commitChanges()

            }
        }
    }
}