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
		<ish:entity>Contact</ish:entity>
		<ish:version>6</ish:version>
		<ish:keyCode>ish.onCourse.contactCsv</ish:keyCode>
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
		<xsl:text>lastName,firstName,male,company,createdOn,modifiedOn,allowEmail,allowPost,allowSms,birthDate,deliveryStatusEmail,deliveryStatusPost,deliveryStatusSms,homePhone,workPhone,mobilePhone,street,postcode,suburb,state,uniqueCode,isStudent,isTutor,notes,email,fax,message,tfn,abn,studentNumber,studentDisabilityType,studentEnglishProfociency,studentHighestSchoolLevel,studentIndigenousStatus,studentIsOverseasClient,studentIsStillAtSchool,studentLabourForceStatus,studentPriorEducation,studentConcessionNumberObsolete,studentConcessionTypeObsolete,studentSpecialNeeds,studentYearSchoolCompleted,tutorCompanyName,tutorDateStarted,tutorDateFinished,tutorResume</xsl:text>
		<xsl:value-of select="$break" />
		<xsl:apply-templates select="contact" />
	</xsl:template>
	
	<xsl:template match="contact">
		<xsl:value-of select="ish:outputValue(lastName)" />
		<xsl:value-of select="ish:outputValue(firstName)" />
		<xsl:value-of select="ish:outputValue(male)" />
		<xsl:value-of select="ish:outputValue(company)" />
		<xsl:value-of select="ish:outputValue(createdOn)" />
		<xsl:value-of select="ish:outputValue(modifiedOn)" />
		<xsl:value-of select="ish:outputValue(allowEmail)" />
		<xsl:value-of select="ish:outputValue(allowPost)" />
		<xsl:value-of select="ish:outputValue(allowSms)" />
		<xsl:value-of select="ish:outputValue(birthDate)" />
		<xsl:value-of select="ish:outputValue(deliveryStatusEmail)" />
		<xsl:value-of select="ish:outputValue(deliveryStatusPost)" />
		<xsl:value-of select="ish:outputValue(deliveryStatusSms)" />
		<xsl:value-of select="ish:outputValue(homePhone)" />
		<xsl:value-of select="ish:outputValue(workPhone)" />
		<xsl:value-of select="ish:outputValue(mobilePhone)" />
		<xsl:value-of select="ish:outputValue(street)" />
		<xsl:value-of select="ish:outputValue(postcode)" />
		<xsl:value-of select="ish:outputValue(suburb)" />
		<xsl:value-of select="ish:outputValue(state)" />
		<xsl:value-of select="ish:outputValue(uniqueCode)" />
		<xsl:value-of select="ish:outputValue(isStudent)" />
		<xsl:value-of select="ish:outputValue(isTutor)" />
		<xsl:value-of select="ish:outputValue(notes)" />
		<xsl:value-of select="ish:outputValue(email)" />
		<xsl:value-of select="ish:outputValue(fax)" />
		<xsl:value-of select="ish:outputValue(message)" />
		<xsl:value-of select="ish:outputValue(tfn)" />
		<xsl:value-of select="ish:outputValue(abn)" />
		<xsl:value-of select="ish:outputValue(student/studentNumber)" />
		<xsl:value-of select="ish:outputValue(student/disabilityType)" />
		<xsl:value-of select="ish:outputValue(student/englishProficiency)" />
		<xsl:value-of select="ish:outputValue(student/highestSchoolLevel)" />
		<xsl:value-of select="ish:outputValue(student/indigenousStatus)" />
		<xsl:value-of select="ish:outputValue(student/isOverseasClient)" />
		<xsl:value-of select="ish:outputValue(student/isStillAtSchool)" />
		<xsl:value-of select="ish:outputValue(student/labourForceStatus)" />
		<xsl:value-of select="ish:outputValue(student/priorEducationCode)" />
		<xsl:value-of select="ish:outputValue(student/concessionNumberObsolete)" />
		<xsl:value-of select="ish:outputValue(student/concessionTypeObsolete)" />
		<xsl:value-of select="ish:outputValue(student/specialNeeds)" />
		<xsl:value-of select="ish:outputValue(student/yearSchoolCompleted)" />
		<xsl:value-of select="ish:outputValue(tutor/companyName)" />
		<xsl:value-of select="ish:outputValue(tutor/dateStarted)" />
		<xsl:value-of select="ish:outputValue(tutor/dateFinished)" />
		<xsl:value-of select="ish:outputValue(tutor/resume, true())" />
		<xsl:if test="following-sibling::*">
			<xsl:value-of select="$break" />
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
