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
		<ish:entity>Qualification</ish:entity>
		<ish:version>5</ish:version>
		<ish:keyCode>ish.onCourse.qualificationCsv</ish:keyCode>
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
		<xsl:text>title,nationalCode,modifiedOn,createdOn,isAccreditedCourse,level,nominalHours,reviewDate,anzsco,fieldOfEducation,isOffered,newApprenticeship</xsl:text>
				<xsl:value-of select="$break" />
		<xsl:apply-templates select="qualification" />
	</xsl:template>
	
	<xsl:template match="qualification">
		<xsl:value-of select="ish:outputValue(title)" />
		<xsl:value-of select="ish:outputValue(nationalCode)" />
		<xsl:value-of select="ish:outputValue(modifiedOn)" />
		<xsl:value-of select="ish:outputValue(createdOn)" />
		<xsl:value-of select="ish:outputValue(type)" />
		<xsl:value-of select="ish:outputValue(level)" />
		<xsl:value-of select="ish:outputValue(nominalHours)" />
		<xsl:value-of select="ish:outputValue(reviewDate)" />
		<xsl:value-of select="ish:outputValue(anzsco)" />
		<xsl:value-of select="ish:outputValue(fieldOfEducation)" />
		<xsl:value-of select="ish:outputValue(isOffered)" />
		<xsl:value-of select="ish:outputValue(newApprenticeship, true())" />
		<xsl:if test="following-sibling::*">
			<xsl:value-of select="$break" />
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>