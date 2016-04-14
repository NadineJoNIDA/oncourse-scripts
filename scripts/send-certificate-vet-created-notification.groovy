def run(args) {
    def currentDate = new Date()
    def allCertificates = ObjectSelect.query(Certificate)
            .where(Certificate.STUDENT.dot(Student.USI_STATUS).eq(UsiStatus.VERIFIED))
            .and(Certificate.PRINTED_ON.isNull())
            .select(args.context)

    allCertificates.each { c ->
        def eLsit = c.student.enrolments.findAll { e ->
            c.qualification?.objectId == e.courseClass.course?.qualification?.objectId &&
                    EnrolmentStatus.STATUSES_LEGIT.contains(e.status) &&
                    e.documents.find { d -> d.name == "${e.courseClass.uniqueCode}_${c.student.contact.lastName}_${c.student.contact.firstName}_Certificate.pdf" } == null
        }

        if (eLsit.size() > 0) {

			def bg = c.isQualification ? (QualificationType.SKILLSET_TYPE == c.qualification.type ? 'vet_skillset_background.pdf' :'vet_qualification_background.pdf') :  'vet_soa_background.pdf'

			def printData = report {
                keycode "ish.onCourse.certificate"
                records Arrays.asList(c)
				background bg
            }

            eLsit.each { e ->
                document {
                    action "create"
                    content printData
                    name "${e.courseClass.uniqueCode}_${c.student.contact.lastName}_${c.student.contact.firstName}_Certificate.pdf"
                    mimeType "application/pdf"
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