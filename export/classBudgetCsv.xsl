<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:ish="http://www.ish.com.au/schema/2009/onCourseTransform"
                xsi:schemaLocation="http://www.ish.com.au/schema/2009/onCourseTransform http://www.ish.com.au/schema/2009/onCourseTransform.xsd"
                exclude-result-prefixes="xsi ish" version="2.0"
                xpath-default-namespace="http://www.ish.com.au/schema/4/onCourseData">
	<xsl:output method="text" encoding="UTF-8" indent="no" />
	
	<ish:onCourse>
		<ish:name>Class Budget Summary</ish:name>
		<ish:entity>CourseClass</ish:entity>
		<ish:version>8</ish:version>
		<ish:keyCode>ish.onCourse.ClassBudgetCsv</ish:keyCode>
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
	
	<xsl:template match="data">	<xsl:text>Class name,Code,Start,End,Enrol maximum,Enrol budget,Enrol actual,Class fee,Income maximum,Income budget,Income actual,Expenses maximum,Expenses budget,Expenses actual,Profit maximum,Profit budget,Profit actual</xsl:text>
		<xsl:value-of select="$break" />
		<xsl:apply-templates select="course/class" />
	</xsl:template>
	
	<xsl:template match="class">
		<xsl:value-of select="ish:outputValue(../name)" />
		<xsl:value-of select="ish:outputValue(concat(../code, '-',code))" />
		<xsl:value-of select="ish:outputValue(format-dateTime(startDateTime, '[Y]-[M01]-[D01]'))" />
		<xsl:value-of select="ish:outputValue(format-dateTime(endDateTime, '[Y]-[M01]-[D01]'))" />
		<xsl:value-of select="ish:outputValue(maximumPlaces)" />
		<xsl:value-of select="ish:outputValue(budgetedPlaces)" />
		<xsl:value-of select="concat($quote, count(enrolment[status='Active']), $quote, $delim)"/>
		<xsl:value-of select="ish:outputValue(price[1]/amount)" />
		<xsl:value-of select="ish:outputValue(cost/incomeMaximum)"/>
		<xsl:value-of select="ish:outputValue(cost/incomeBudget)"/>
		<xsl:value-of select="ish:outputValue(cost/incomeActual)"/>
		<xsl:value-of select="ish:outputValue(cost/expensesMaximum)"/>
		<xsl:value-of select="ish:outputValue(cost/expensesBudget)"/>
		<xsl:value-of select="ish:outputValue(cost/expensesActual)"/>
		<xsl:value-of select="ish:outputValue(cost/profitMaximum)"/>
		<xsl:value-of select="ish:outputValue(cost/profitBudget)"/>
		<xsl:value-of select="ish:outputValue(cost/profitActual, true())"/>

		<xsl:if test="(../following-sibling::*) or (following-sibling::*)">
			<xsl:value-of select="$break" />
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
