<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:ish="http://www.ish.com.au/schema/2009/onCourseTransform"
                exclude-result-prefixes="xsi ish" version="2.0"
                xpath-default-namespace="http://www.ish.com.au/schema/4/onCourseData">
	<xsl:output method="text" encoding="UTF-8" indent="no" />

	<ish:onCourse>
		<ish:name>Class outcomes CSV</ish:name>
		<ish:entity>CourseClass</ish:entity>
		<ish:version>2</ish:version>
		<ish:keyCode>ish.onCourse.classOutcomesCsv</ish:keyCode>
	</ish:onCourse>

	<xsl:param name="delim" select="','" />
	<xsl:param name="quote" select="'&quot;'" />
	<xsl:param name="space" select="' '"/>
	<xsl:param name="doublequote" select="'&quot;&quot;'" />
	<xsl:param name="break" select="'&#xA;'" />
	<xsl:param name="dash" select="'-'"/>

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

	<xsl:template match="data">	<xsl:text>studentNumber, firstName, lastName, classCode, nationalCode, title, startDate, endDate, status</xsl:text>
		<xsl:value-of select="$break" />
		<xsl:apply-templates select="course/class/enrolment/outcome" />
	</xsl:template>

	<xsl:template match="outcome">
		<xsl:variable name="contactId" select="../contact/@id"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId]/student/studentNumber)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId]/firstName)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId]/lastName)"/>
		<xsl:value-of select="ish:outputValue(concat(../../../code, $dash, ../../code))"/>

		<xsl:apply-templates select="module"/>

		<xsl:value-of select="ish:outputValue(format-dateTime(startDate, '[D]/[M]/[Y] [h]:[m01] [Pn]'))"/>
		<xsl:value-of select="ish:outputValue(format-dateTime(endDate, '[D]/[M]/[Y] [h]:[m01] [Pn]'))"/>
		<xsl:value-of select="ish:outputValue(status, true())"/>

		<xsl:if test="(../following-sibling::*) or (following-sibling::*)">
			<xsl:value-of select="$break" />
		</xsl:if>
	</xsl:template>

	<xsl:template match="module">
		<xsl:variable name="moduleId" select="@id"/>
		<xsl:value-of select="ish:outputValue(/data/module[@id=$moduleId]/nationalCode)"/>
		<xsl:value-of select="ish:outputValue(/data/module[@id=$moduleId]/title)"/>
	</xsl:template>

</xsl:stylesheet>
