/*(
contactFile=Please select contact CSV file...
)*/


import ish.oncourse.server.cayenne.ContactNoteRelation
import ish.oncourse.server.cayenne.Country
import ish.oncourse.server.cayenne.Language
import ish.oncourse.server.cayenne.Note
import ish.oncourse.server.imports.CsvParser
import org.apache.cayenne.query.ObjectSelect

import java.text.SimpleDateFormat

def reader = new CsvParser(new InputStreamReader(new ByteArrayInputStream(contactFile)))

reader.eachLine { line ->
	context.newObject(Contact).with { contact ->
		contact.title = line.title
		contact.lastName = line.lastName
		contact.firstName = line.firstName
		contact.middleName = line.middleName
		contact.honorific = line.honorific
		contact.isMale = line.gender?.toBoolean()
		contact.birthDate = line.birthDate ? new SimpleDateFormat("yyyy-MM-dd").parse(line.birthDate) : null
		contact.isCompany = line.company?.toBoolean()
		contact.isStudent = line.isStudent?.toBoolean() ?: false
		contact.isTutor = line.isTutor?.toBoolean() ?: false
		contact.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(line.createdOn)
		contact.modifiedOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(line.createdOn)
		contact.allowEmail = line.allowEmail?.toBoolean()
		contact.allowPost = line.allowPost?.toBoolean()
		contact.allowSms = line.allowSms?.toBoolean()
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
		contact.email = line.email
		contact.fax = line.fax
		contact.uniqueCode = line.uniqueCode

		if (line.notes) {
			ContactNoteRelation contactNoteRelation = context.newObject(ContactNoteRelation)
			Note note = context.newObject(Note)
			contactNoteRelation.note = note
			contactNoteRelation.notableEntity = contact
			note.note = line.notes
		}

		contact.message = line.message
		contact.tfn = line.tfn
		contact.abn = line.abn
		if (contact.isStudent) {
			context.newObject(Student).with { student ->
				student.contact = contact
				student.studentNumber = line.studentNumber?.toLong()
				student.usi = line.usi
				student.usiStatus = UsiStatus.values().find { value -> value.displayName == line.usiStatus }
				student.chessn = line.chessn

				countryOfBirth = ObjectSelect.query(Country.class)
						.where(Country.NAME.likeIgnoreCase(line.countryOfBirth))
						.selectFirst(context)
				student.countryOfBirth = countryOfBirth

				student.townOfBirth = line.townOfBirth
				student.indigenousStatus = AvetmissStudentIndigenousStatus.values().find { value -> value.displayName == line.studentIndigenousStatus }

				Language languageSpokenAtHome = ObjectSelect.query(Language.class).
						where(Language.NAME.likeIgnoreCase(line.languageSpokenAtHome)).
						selectFirst(context)
				student.setLanguage(languageSpokenAtHome)

				student.englishProficiency = AvetmissStudentEnglishProficiency.values().find { value -> value.displayName == line.studentEnglishProficiency }
				student.highestSchoolLevel = AvetmissStudentSchoolLevel.values().find { value -> value.displayName == line.studentHighestSchoolLevel }
				student.yearSchoolCompleted = line.studentYearSchoolCompleted?.toInteger()
				student.priorEducationCode = AvetmissStudentPriorEducation.values().find { value -> value.displayName == line.studentPriorEducation }
				student.isStillAtSchool = line.studentIsStillAtSchool?.toBoolean()
				student.labourForceStatus = AvetmissStudentLabourStatus.values().find { value -> value.displayName == line.studentLabourForceStatus }
				student.disabilityType = AvetmissStudentDisabilityType.values().find { value -> value.displayName == line.studentDisabilityType }
				student.specialNeedsAssistance = line.disabilitySupportRequested?.toBoolean()
				student.specialNeeds = line.studentSpecialNeeds
				student.feeHelpEligible = line.vetFEEHelpEligible?.toBoolean()
				student.citizenship = StudentCitizenship.values().find { value -> value.displayName == line.citizenship }
				student.isOverseasClient = line.studentIsOverseasClient?.toBoolean()

				countryOfResidency = ObjectSelect.query(Country.class)
						.where(Country.NAME.likeIgnoreCase(line.countryOfResidency))
						.selectFirst(context)
				student.countryOfResidency = countryOfResidency

				student.passportNumber = line.passportNumber
				student.visaType = line.visaType
				student.visaNumber = line.visaNumber
				student.visaExpiryDate = line.visaExpiryDate ? new SimpleDateFormat("yyyy-MM-dd").parse(line.visaExpiryDate) : null
				student.medicalInsurance = line.overseasHealthCareNo
			}
		}
		if (contact.isTutor) {
			context.newObject(Tutor).with { tutor ->
				tutor.contact = contact
				tutor.payrollRef = line.tutorPayrollNo
				tutor.dateStarted = line.tutorDateStarted ? new SimpleDateFormat("yyyy-MM-dd").parse(line.tutorDateStarted) : null
				tutor.dateFinished = line.tutorDateFinished ? new SimpleDateFormat("yyyy-MM-dd").parse(line.tutorDateFinished) : null
				tutor.resume = line.tutorResume
			}
		}

		context.commitChanges()
	}
}