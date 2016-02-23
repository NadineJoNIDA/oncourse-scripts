import ish.integrations.IntegrationType

def run(args) {
    def enrolment = args.entity

    ObjectSelect.query(Integration).where(Integration.TYPE.eq(IntegrationType.CLOUDASSESS)).select(args.context)
            .each { integration ->
        cloudassess {
            name integration.name
            action "enrol"
            contact enrolment.student.contact
            cloudassess_course 123 // set there Cloud Assess database course Id
        }
    }
}