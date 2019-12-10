def courseClass = args.entity

courseClass.tutorRoles*.tutor.unique().each { tutor ->
    email {
        template "Tutor notice of class cancellation"
        to tutor.contact
        bindings tutor: tutor, courseClass: courseClass
    }

}