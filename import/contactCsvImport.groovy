/*(
contactFile=Please select contact CSV file...
)*/

import ish.oncourse.server.cayenne.Contact
import ish.oncourse.server.cayenne.ContactNoteRelation
import ish.oncourse.server.cayenne.Country
import ish.oncourse.server.cayenne.Language
import ish.oncourse.server.cayenne.Note
import ish.oncourse.server.cayenne.Student
import ish.oncourse.server.cayenne.Tutor
import ish.oncourse.server.imports.CreateUserFriendlyMessage
import ish.oncourse.server.imports.CsvParser
import org.apache.cayenne.query.ObjectSelect

import javax.script.ScriptException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

def reader = new CsvParser(new InputStreamReader(new ByteArrayInputStream(contactFile)))
int rowNumber = 0
try {
    reader.eachLine { line ->
        rowNumber++
        context.newObject(Contact).with { contact ->
            contact.title = line.title
            contact.lastName = line.lastName
            contact.firstName = line.firstName
            contact.middleName = line.middleName
            contact.honorific = line.honorific
            contact.isMale = line.gender?.toBoolean()
            contact.birthDate = line.birthDate ? LocalDate.parse(line.birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null
            contact.isCompany = line.company?.toBoolean() ?: false
            contact.isStudent = line.isStudent?.toBoolean() ?: false
            contact.isTutor = line.isTutor?.toBoolean() ?: false
            contact.allowEmail = line.allowEmail?.toBoolean() ?: false
            contact.allowPost = line.allowPost?.toBoolean() ?: false
            contact.allowSms = line.allowSms?.toBoolean() ?: false
            contact.deliveryStatusEmail = line.deliveryStatusEmail?.toInteger() ?: 0
            contact.deliveryStatusPost = line.deliveryStatusPost?.toInteger() ?: 0
            contact.deliveryStatusSms = line.deliveryStatusSms?.toInteger() ?: 0
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
                    student.studentNumber = line.studentNumber?.toLong() ?: student.studentNumber
                    student.usi = line.usi
                    student.usiStatus = UsiStatus.values().find { value -> value.displayName == line.usiStatus } ?: student.usiStatus
                    student.chessn = line.chessn

                    countryOfBirth = ObjectSelect.query(Country.class)
                        .where(Country.NAME.likeIgnoreCase(line.countryOfBirth))
                        .selectFirst(context)
                    student.countryOfBirth = countryOfBirth

                    student.townOfBirth = line.townOfBirth
                    student.indigenousStatus = AvetmissStudentIndigenousStatus.values().find { value -> value.displayName == line.studentIndigenousStatus } ?: student.indigenousStatus

                    Language languageSpokenAtHome = ObjectSelect.query(Language.class).
                        where(Language.NAME.likeIgnoreCase(line.languageSpokenAtHome)).
                        selectFirst(context)
                    student.setLanguage(languageSpokenAtHome)

                    student.englishProficiency = AvetmissStudentEnglishProficiency.values().find { value -> value.displayName == line.studentEnglishProficiency } ?: student.englishProficiency
                    student.highestSchoolLevel = AvetmissStudentSchoolLevel.values().find { value -> value.displayName == line.studentHighestSchoolLevel } ?: student.highestSchoolLevel
                    student.yearSchoolCompleted = line.studentYearSchoolCompleted?.toInteger()
                    student.priorEducationCode = AvetmissStudentPriorEducation.values().find { value -> value.displayName == line.studentPriorEducation } ?: student.priorEducationCode
                    student.isStillAtSchool = line.studentIsStillAtSchool?.toBoolean()
                    student.labourForceStatus = AvetmissStudentLabourStatus.values().find { value -> value.displayName == line.studentLabourForceStatus } ?: student.labourForceStatus
                    student.disabilityType = AvetmissStudentDisabilityType.values().find { value -> value.displayName == line.studentDisabilityType } ?: student.disabilityType
                    student.specialNeedsAssistance = line.disabilitySupportRequested?.toBoolean()
                    student.specialNeeds = line.studentSpecialNeeds
                    student.feeHelpEligible = line.vetFEEHelpEligible?.toBoolean() ?: student.feeHelpEligible
                    student.citizenship = StudentCitizenship.values().find { value -> value.displayName == line.citizenship } ?: student.citizenship
                    student.isOverseasClient = line.studentIsOverseasClient?.toBoolean() ?: student.isOverseasClient

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
        }
        context.commitChanges()
    }
    
} catch (Exception e){
    throw new ScriptException(CreateUserFriendlyMessage.valueOf(e, rowNumber).getMessage())
}