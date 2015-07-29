xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")

xml.brochure {
	courseClasses = records.groupBy { CourseClass cc -> cc.course }
	subj = courseClasses.keySet().collectMany { Course co -> co.tags }.findAll { Tag t -> NodeSpecialType.SUBJECTS.equals(t.getRoot().specialType) }.unique()[0]?.getRoot()
	if (subj) {
		"Tag"(subj.name) {
			addChild(subj, xml, courseClasses)
		}
	}
}

def addChild(parent, xml, courseClasses) {
	parent.childTags.each { Tag child ->
		xml.Tag(child.name) {
			courseClasses.each { Course co, List<CourseClass> classes ->
				if (co.tags.contains(child)) {
					"Course"() {
						CourseName(co.name)
						Description(co.printedBrochureDescription)
						classes.each { CourseClass cc ->
							"Class"() {
								cc.tutorRoles.each { CourseClassTutor tr ->
									"Tutor"(tr.tutor.fullName)
								}
								ClassCode(co.code + "-" + cc.code)
								"Site"(cc.room?.site?.name)
								if (!cc.sessions.isEmpty()) {
									mkp.yield("\n")
									mkp.yield(cc.startDateTime?.format("EEE d MMMMM hh:mma") + "-" + cc.endDateTime?.format("hh:mma"))
									mkp.yield("\n")
									mkp.yield(cc.sessionsCount.toString() + " sessions of")
									AverageHours(cc.getFirstSession().durationInHours.setScale(1, java.math.RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + (cc.getFirstSession().durationInHours.compareTo(BigDecimal.ONE) > 1 ? " hrs" : " hr"))

								}
								Price(cc.feeIncGst?.toPlainString())
							}
						}
						courseHorizontalLine()
					}
				}
			}
			addChild(child, xml, courseClasses)
		}
	}
}
