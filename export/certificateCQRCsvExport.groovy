output << '[VERSION200]' << "\r\n"

records.each { Certificate c ->
	output << "\"${c.certificateOutcomes.collect { co -> co.outcome.enrolment }.find { e -> e.vetClientID }?.vetClientID ?: ""}\"" << '\t'
	output << "\"${c.student.studentNumber}\"" << '\t'
	output << "\"${c.student.contact.firstName}\"" << '\t'
	output << "\"${c.student.contact.lastName}\"" << '\t'
	output << "\"${c.student.contact.birthDate?.format("dd/MM/YYYY")}\"" << '\t'
	output << "\"${c.student.contact.isMale == null ? "@" : (c.student.contact.isMale ? "M" : "F")}\"" << '\t'
	output << "\"${c.qualification?.nationalCode ?: ""}\"" << '\t'
	output << "\"${c.isQualification ? "Q" : "S"}\"" << '\t'
	output << "\"${c.createdOn?.format("dd/MM/YYYY")}\"" << '\t'
	output << "\"${c.certificateNumber}\"" << '\t'
	output << "\"English\"" << '\t'
	c.certificateOutcomes.collect { co -> co.outcome?.module?.nationalCode }.each { code ->
		output << "\"${code}\"" << '\t'
	}
	output << "\r\n"
}
