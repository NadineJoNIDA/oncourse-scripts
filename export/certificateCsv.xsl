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
		<ish:entity>Certificate</ish:entity>
		<ish:version>5</ish:version>
		<ish:keyCode>ish.onCourse.certificateCsv</ish:keyCode>
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
		<xsl:param name="value" as="xs:string?"/>
		<xsl:value-of select="ish:outputValue($value, false())" />
	</xsl:function>
	
	<xsl:function name="ish:outputValue">
		<xsl:param name="value" as="xs:string?"/>
		<xsl:param name="endOfLine" as="xs:boolean"/>
		<xsl:value-of select="concat($quote, replace(normalize-space($value), $quote, $doublequote), $quote)" />
		<xsl:if test="not($endOfLine)">
			<xsl:value-of select="$delim" />
		</xsl:if>
	</xsl:function>
	
	<xsl:template match="data">
		<xsl:text>studentFirstName,studentLastName,certificateNumber,fullQualification,printed,modifiedOn,createdOn,revokedOn,privateNotes,publicNotes,studentIsMale,studentIsCompany,studentCreatedOn,studentModifiedOn,studentAllowEmail,studentAllowPost,studentAllowSms,studentBirthDate,studentDeliveryStatusEmail,studentDeliveryStatusPost,studentDeliveryStatusSms,studentHomePhone,studentWorkPhone,studentMobilePhone,studentStreet,studentPostcode,studentSuburb,studentState,studentWebPassword,studentUniqueCode,studentIsTutor,studentNotes,studentEmail,studentFax,studentMessage,studentTfn,studentAbn,studentNumber,studentDisabilityType,studentEnglishProfociency,studentHighestSchoolLevel,studentIndigenousStatus,studentIsOverseasClient,studentIsStillAtSchool,studentLabourForceStatus,studentPriorEducation,studentConcessionNumberObsolete,studentConcessionTypeObsolete,studentSpecialNeeds,studentYearSchoolCompleted</xsl:text>
		<xsl:value-of select="$break" />
		<xsl:apply-templates select="certificate" />
	</xsl:template>
	
	<xsl:template match="certificate">
		<xsl:value-of select="ish:outputValue(firstName)" />
		<xsl:value-of select="ish:outputValue(lastName)" />
		<xsl:value-of select="ish:outputValue(certificateNumber)" />
		<xsl:value-of select="ish:outputValue(fullQualification)" />
		<xsl:value-of select="ish:outputValue(printed)" />
		<xsl:value-of select="ish:outputValue(modifiedOn)" />
		<xsl:value-of select="ish:outputValue(createdOn)" />
		<xsl:value-of select="ish:outputValue(revokedOn)" />
		<xsl:value-of select="ish:outputValue(privateNotes)" />
		<xsl:value-of select="ish:outputValue(publicNotes)" />
		<xsl:apply-templates select="contact" />
		<xsl:if test="following-sibling::*">
			<xsl:value-of select="$break" />
		</xsl:if>
	</xsl:template>

	<xsl:template match="contact">
		<xsl:variable name="contactID" select="@id" />
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
			<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/yearSchoolCompleted, true())" />
	</xsl:template>

</xsl:stylesheet>