csv.delimiter = '\t'

csv << ["[VERSION200]": ""]

records.each { Certificate c ->
	csv << [
			"1" : c.certificateOutcomes.collect { co -> co.outcome.enrolment }.unique().first()?.vetClientID,
			"2" : c.student.studentNumber,
			"3" : c.student.contact.firstName,
			"4" : c.student.contact.lastName,
			"5" : c.student.contact.birthDate?.format("DD/MM/YYYY"),
			"6" : c.student.contact.isMale == null ? "@" : (c.student.contact.isMale ? "M" : "F"),
			"7" : c.qualification?.nationalCode,
			"8" : c.isQualification ? "Q" : "S",
			"9" : c.createdOn?.format("DD/MM/YYYY"),
			"10": c.certificateNumber,
			"11": "English",
			"12": c.certificateOutcomes.collect { co -> co.outcome?.module?.nationalCode }.join('\t')
	]
}
