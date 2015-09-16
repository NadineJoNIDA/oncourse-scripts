/*(
contactFile=Please select contact CSV file...
)*/

import ish.oncourse.server.imports.CsvParser

def reader = new CsvParser(new InputStreamReader(new ByteArrayInputStream(contactFile)))

reader.eachLine { line ->
	context.newObject(Contact).with { contact ->
		contact.lastName = line.lastName
		contact.firstName = line.firstName
		contact.isMale = line.male?.toBoolean()
		contact.isCompany = line.company?.toBoolean()
		contact.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(line.createdOn)
		contact.modifiedOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(line.createdOn)
		contact.allowEmail = line.allowEmail?.toBoolean()
		contact.allowPost = line.allowPost?.toBoolean()
		contact.birthDate = line.birthDate ? new SimpleDateFormat("yyyy-MM-dd").parse(line.birthDate) : null
		contact.deliveryStatusEmail = line.deliveryStatusEmail?.toInteger()
		contact.deliveryStatusPost = line.deliveryStatusPost?.toInteger()
		contact.deliveryStatusSms = line.deliveryStatusSms?.toInteger()
		contact.homePhone = line.homePhone
		contact.workPhone = line.workPhone
		contact.mobilePhone = line.mobilePhone
		contact.street = line.street
		contact.postcode = line.postcode
		contact.suburb = line.suburb
		contact.state = line.state
		contact.uniqueCode = line.uniqueCode
		contact.isStudent = line.isStudent?.toBoolean() ?: false
		contact.isTutor = line.isTutor?.toBoolean() ?: false
		contact.email = line.email
		contact.fax = line.fax
		contact.message = line.message
		contact.tfn = line.tfn
		contact.abn = line.abn
		if (contact.isStudent) {
			context.newObject(Student).with { student ->
				student.contact = contact
				student.studentNumber = line.studentNumber?.toLong()
				student.disabilityType = AvetmissStudentDisabilityType.values().find { value -> value.displayName == line.studentDisabilityType }
				student.englishProficiency = AvetmissStudentEnglishProficiency.values().find { value -> value.displayName == line.studentEnglishProficiency }
				student.highestSchoolLevel = AvetmissStudentSchoolLevel.values().find { value -> value.displayName == line.studentHighestSchoolLevel }
				student.indigenousStatus = AvetmissStudentIndigenousStatus.values().find { value -> value.displayName == line.studentIndigenousStatus }
				student.isOverseasClient = line.studentIsOverseasClient?.toBoolean()
				student.isStillAtSchool = line.studentIsStillAtSchool?.toBoolean()
				student.labourForceStatus = AvetmissStudentLabourStatus.values().find { value -> value.displayName == line.studentLabourForceStatus }
				student.priorEducationCode = AvetmissStudentPriorEducation.values().find { value -> value.displayName == line.studentPriorEducation }
				student.concessionNumberObsolete = line.studentConcessionNumberObsolete
				student.concessionTypeObsolete = line.studentConcessionTypeObsolete?.toInteger()
				student.specialNeeds = line.studentSpecialNeeds
				student.yearSchoolCompleted = line.studentYearSchoolCompleted?.toInteger()
			}
		}
		if (contact.isTutor) {
			context.newObject(Tutor).with { tutor ->
				tutor.contact = contact
				tutor.dateStarted = line.tutorDateStarted ? new SimpleDateFormat("yyyy-MM-dd").parse(line.tutorDateStarted) : null
				tutor.dateFinished = line.tutorDateFinished ? new SimpleDateFormat("yyyy-MM-dd").parse(line.tutorDateFinished) : null
				tutor.resume = line.tutorResume
			}
		}

		context.commitChanges()
	}
}