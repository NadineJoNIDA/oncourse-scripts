<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:ish="http://www.ish.com.au/schema/2009/onCourseTransform"
                exclude-result-prefixes="xsi ish" version="2.0"
                xpath-default-namespace="http://www.ish.com.au/schema/4/onCourseData">
	<xsl:output method="text" encoding="UTF-8" indent="no" />
	
	<ish:onCourse>
		<ish:name>CSV</ish:name>
		<ish:entity>CourseClass</ish:entity>
		<ish:version>12</ish:version>
		<ish:keyCode>ish.onCourse.courseClassCsv</ish:keyCode>
	</ish:onCourse>
	
	<xsl:param name="delim" select="','" />
	<xsl:param name="quote" select="'&quot;'" />
	<xsl:param name="space" select="' '"/>
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

	<xsl:function name="ish:outputTutor">
		<xsl:param name="fname" as="xs:string?"/>
		<xsl:param name="lname" as="xs:string?"/>
		<xsl:value-of select="normalize-space(concat($fname, $space, $lname))" />
	</xsl:function>
	
	<xsl:template match="data">	<xsl:text>code,budgetedPlaces,createdOn,modifiedOn,deliveryMode,startDateTime,endDateTime,isCancelled,fundingSource,isDistantLearningCourse,isWebVisible,maximumPlaces,minimumPlaces,notes,detBookingId,message,reportableHours,priceAmount,priceTaxAmount,PriceTaxName,priceDiscountAmount,roomName,roomSiteName,roomSiteStreet,roomSiteState,roomSitePostcode,roomSiteSuburb,courseCode,courseName,courseCreatedOn,courseModifiedOn,courseCurrentlyOffered,courseFieldOfEducation,courseIsWebVisible,courseIsVET,courseReportableHours,courseWebDescription,courseAllowWaitingLists,courseIsShownOnWeb,courseIsSufficientForQualification,coursePrintedBrochureDescription,tutors,enrolmentCount</xsl:text>
		<xsl:value-of select="$break" />
		<xsl:apply-templates select="course/class" />
	</xsl:template>
	
	<xsl:template match="class">
		<xsl:value-of select="ish:outputValue(code)" />
		<xsl:value-of select="ish:outputValue(budgetedPlaces)" />
		<xsl:value-of select="ish:outputValue(createdOn)" />
		<xsl:value-of select="ish:outputValue(modifiedOn)" />
		<xsl:value-of select="ish:outputValue(deliveryMode)" />
		<xsl:value-of select="ish:outputValue(startDateTime)" />
		<xsl:value-of select="ish:outputValue(endDateTime)" />
		<xsl:value-of select="ish:outputValue(isCancelled)" />
		<xsl:value-of select="ish:outputValue(fundingSource)" />
		<xsl:value-of select="ish:outputValue(isDistantLearningCourse)" />
		<xsl:value-of select="ish:outputValue(isWebVisible)" />
		<xsl:value-of select="ish:outputValue(maximumPlaces)" />
		<xsl:value-of select="ish:outputValue(minimumPlaces)" />
		<xsl:value-of select="ish:outputValue(notes)" />
		<xsl:value-of select="ish:outputValue(detBookingId)" />
		<xsl:value-of select="ish:outputValue(message)" />
		<xsl:value-of select="ish:outputValue(reportableHours)" />
		<xsl:value-of select="ish:outputValue(price[1]/amount)" />
		<xsl:value-of select="ish:outputValue(price[1]/taxAmount)" />
		<xsl:value-of select="ish:outputValue(price[1]/taxName)" />
		<xsl:value-of select="ish:outputValue(price[1]/discountAmount)" />
		<xsl:apply-templates select="room" />
		<xsl:if test="not(room)" >
			<xsl:value-of select="ish:outputValue('')"/>
			<xsl:value-of select="ish:outputValue('')"/>
			<xsl:value-of select="ish:outputValue('')"/>
			<xsl:value-of select="ish:outputValue('')"/>
			<xsl:value-of select="ish:outputValue('')"/>
			<xsl:value-of select="ish:outputValue('')"/>
		</xsl:if>
		<xsl:value-of select="ish:outputValue(../code)" />
		<xsl:value-of select="ish:outputValue(../name)" />
		<xsl:value-of select="ish:outputValue(../createdOn)" />
		<xsl:value-of select="ish:outputValue(../modifiedOn)" />
		<xsl:value-of select="ish:outputValue(../currentlyOffered)" />
		<xsl:value-of select="ish:outputValue(../fieldOfEducation)" />
		<xsl:value-of select="ish:outputValue(../isWebVisible)" />
		<xsl:value-of select="ish:outputValue(../isVET)" />
		<xsl:value-of select="ish:outputValue(../reportableHours)" />
		<xsl:value-of select="ish:outputValue(../webDescription)" />
		<xsl:value-of select="ish:outputValue(../allowWaitingLists)" />
		<xsl:value-of select="ish:outputValue(../isShownOnWeb)" />
		<xsl:value-of select="ish:outputValue(../isSufficientForQualification)" />
		<xsl:value-of select="ish:outputValue(../printedBrochureDescription)" />
		
		<xsl:value-of select="$quote"/>
		<xsl:for-each select="tutorRole">
			<xsl:variable name="contactId" select="contact/@id"/>
			<xsl:value-of select="ish:outputTutor(/data/contact[@id=$contactId]/firstName, /data/contact[@id=$contactId]/lastName)"/>
			<xsl:if test="position() != last()">
				<xsl:value-of select="concat($delim, $space)"/>
			</xsl:if>
		</xsl:for-each>
		<xsl:value-of select="concat($quote, $delim)"/>
		<xsl:value-of select="concat($quote, count(enrolment[status='Active']), $quote, $delim)"/>
		<xsl:if test="(../following-sibling::*) or (following-sibling::*)">
			<xsl:value-of select="$break" />
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="room">
		<xsl:variable name="roomID" select="@id" />
		<xsl:value-of select="ish:outputValue(/data/site/room[@id=$roomID]/name)" />
		<xsl:value-of select="ish:outputValue(/data/site/room[@id=$roomID]/../name)" />
		<xsl:value-of select="ish:outputValue(/data/site/room[@id=$roomID]/../street)" />
		<xsl:value-of select="ish:outputValue(/data/site/room[@id=$roomID]/../state)" />
		<xsl:value-of select="ish:outputValue(/data/site/room[@id=$roomID]/../postcode)" />
		<xsl:value-of select="ish:outputValue(/data/site/room[@id=$roomID]/../suburb)" />
	</xsl:template>
	
</xsl:stylesheet>
