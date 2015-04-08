<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:ish="http://www.ish.com.au/schema/2009/onCourseTransform"
                xsi:schemaLocation="http://www.ish.com.au/schema/2009/onCourseTransform http://www.ish.com.au/schema/2009/onCourseTransform.xsd"
                exclude-result-prefixes="xsi ish" version="2.0"
                xpath-default-namespace="http://www.ish.com.au/schema/4/onCourseData">
	<xsl:output method="text" encoding="UTF-8" indent="no" />
	
	<ish:onCourse>
		<ish:name>CSV</ish:name>
		<ish:entity>Enrolment</ish:entity>
		<ish:version>5</ish:version>
		<ish:keyCode>ish.onCourse.enrolmentCsv</ish:keyCode>
	</ish:onCourse>
	
	<xsl:param name="delim" select="','" />
	<xsl:param name="quote" select="'&quot;'" />
	<xsl:param name="doublequote" select="'&quot;&quot;'" />
	<xsl:param name="break" select="'&#xA;'" />
	
	<xsl:template match="*">
		<xsl:value-of select="concat($quote, replace(normalize-space(), $quote, $doublequote), $quote)" />
		<xsl:if test="following-sibling::*">
			<xsl:value-of select="$delim" />
		</xsl:if>
	</xsl:template>
	
	<xsl:function name="ish:outputValue">
		<xsl:param name="value" as="xs:string?" />
		<xsl:value-of select="ish:outputValue($value, false())" />
	</xsl:function>
	
	<xsl:function name="ish:outputValue">
		<xsl:param name="value" as="xs:string?" />
		<xsl:param name="endOfLine" as="xs:boolean" />
		<xsl:value-of select="concat($quote, replace(normalize-space($value), $quote, $doublequote), $quote)" />
		<xsl:if test="not($endOfLine)">
			<xsl:value-of select="$delim" />
		</xsl:if>
	</xsl:function>
	
	<xsl:template match="data">
		<xsl:text>source,status,fundingSource,studyReason,modifiedOn,createdOn,studentLastName,studentFirstName,studentMale,studentCompany,studentCreatedOn,studentModifiedOn,studentAllowEmail,studentAllowPost,studentAllowSms,studentBirthDate,studentDeliveryStatusEmail,studentDeliveryStatusPost,DeliveryStatusSms,studentHomePhone,studentWorkPhone,studentMobilePhone,studentStreet,studentPostcode,studentSuburb,studentState,studentWebPassword,studentUniqueCode,studentIsTutor,studentNotes,studentEmail,studentFax,studentMessage,studentTfn,studentAbn,studentNumber,studentDisabilityType,studentEnglishProfociency,studentHighestSchoolLevel,studentIndigenousStatus,studentIsOverseasClient,studentIsStillAtSchool,studentLabourForceStatus,studentPriorEducation,studentConcessionNumberObsolete,studentConcessionTypeObsolete,studentSpecialNeeds,studentYearSchoolCompleted,classCode,classBudgetedPlaces,classCreatedOn,classModifiedOn,classDeliveryMode,classStartDateTime,classEndDateTime,classIsCancelled,classFundingSource,classIsDistantLearningCourse,classIsWebVisible,classMaximumPlaces,classMinimumPlaces,classNotes,classDetBookingId,classMessage,classReportableHours,classPriceAmount,classPriceTaxAmount,classPriceTaxName,classPriceDiscountAmount,courseCode,courseName,courseCreatedOn,courseModifiedOn,courseCurrentlyOffered,courseFieldOfEducation,courseIsWebVisible,courseIsVET,courseReportableHours,courseWebDescription,courseAllowWaitingLists,courseIsShownOnWeb,courseIsSufficientForQualification,coursePrintedBrochureDescription</xsl:text>
		<xsl:value-of select="$break" />
		<xsl:apply-templates select="enrolment" />
	</xsl:template>
	
	<xsl:template match="enrolment">
		<xsl:value-of select="ish:outputValue(source)" />
		<xsl:value-of select="ish:outputValue(status)" />
		<xsl:value-of select="ish:outputValue(fundingSource)" />
		<xsl:value-of select="ish:outputValue(studyReason)" />
		<xsl:value-of select="ish:outputValue(modifiedOn)" />
		<xsl:value-of select="ish:outputValue(createdOn)" />
		<xsl:apply-templates select="contact" />
		<xsl:apply-templates select="class" />
		<xsl:if test="following-sibling::*">
			<xsl:value-of select="$break" />
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="contact">
		<xsl:variable name="contactID" select="@id" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/lastName)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/firstName)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/male)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/company)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/createdOn)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/modifiedOn)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/allowEmail)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/allowPost)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/allowSms)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/birthDate)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/deliveryStatusEmail)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/deliveryStatusPost)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/deliveryStatusSms)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/homePhone)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/workPhone)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/mobilePhone)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/street)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/postcode)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/suburb)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/state)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/webPassword)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/uniqueCode)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/isTutor)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/notes)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/email)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/fax)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/message)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/tfn)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/abn)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/studentNumber)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/disabilityType)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/englishProficiency)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/highestSchoolLevel)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/indigenousStatus)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/isOverseasClient)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/isStillAtSchool)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/labourForceStatus)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/priorEducationCode)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/concessionNumberObsolete)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/concessionTypeObsolete)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/specialNeeds)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/yearSchoolCompleted)" />
	</xsl:template>
	
	<xsl:template match="class">
		<xsl:variable name="classID" select="@id" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/code)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/budgetedPlaces)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/createdOn)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/modifiedOn)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/deliveryMode)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/startDateTime)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/endDateTime)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/isCancelled)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/fundingSource)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/isDistantLearningCourse)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/isWebVisible)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/maximumPlaces)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/minimumPlaces)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/notes)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/detBookingId)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/message)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/reportableHours)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/price[1]/amount)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/price[1]/taxAmount)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/price[1]/taxName)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/price[1]/discountAmount)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../code)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../name)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../createdOn)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../modifiedOn)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../currentlyOffered)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../fieldOfEducation)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../isWebVisible)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../isVET)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../reportableHours)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../webDescription)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../allowWaitingLists)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../isShownOnWeb)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../isSufficientForQualification)" />
		<xsl:value-of select="ish:outputValue(/data/course/class[@id=$classID]/../printedBrochureDescription, true())" />
	</xsl:template>
</xsl:stylesheet>