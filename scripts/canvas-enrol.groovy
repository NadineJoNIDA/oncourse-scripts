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
            course_code e.courseClass.course.code
            section_code e.courseClass.uniqueCode
            create_section true
            create_student true
        }
    }
}
