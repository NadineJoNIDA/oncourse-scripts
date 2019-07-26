import ish.integrations.*

def run(args) {
    def e = args.value

    def integrations = query {
        entity Integration
        query "type is CANVAS"
        context args.context
    }

    integrations.each { integration ->
        if (e.courseClass.course.hasTag("Canvas", true)) {
            canvas {
                name integration.name
                enrolment e
                create_section true
                create_student true
            }
        }
    }
}
