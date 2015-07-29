records.each { Qualification q ->
	csv << [
			"title"             : q.title,
			"nationalCode"      : q.nationalCode,
			"modifiedOn"        : q.modifiedOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"),
			"createdOn"         : q.createdOn?.format("yyyy-MM-dd'T'HH:mm:ssZ"),
			"isAccreditedCourse": q.type?.displayName,
			"level"             : q.level,
			"nominalHours"      : q.nominalHours,
			"reviewDate"        : q.reviewDate?.format("yyyy-MM-dd"),
			"anzsco"            : q.anzsco,
			"fieldOfEducation"  : q.fieldOfEducation,
			"isOffered"         : q.isOffered,
			"newApprenticeship" : q.newApprenticeship
	]
}
