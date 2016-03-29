def run(args) {
    def e = args.entity

    cloudassess {
        name "cloud assess"
        action "enrol"
        enrolment e
    }
}