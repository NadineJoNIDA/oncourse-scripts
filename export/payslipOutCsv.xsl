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
	<ish:entity>Payslip</ish:entity>
	<ish:version>16</ish:version>
	<ish:keyCode>ish.onCourse.payslipCsv</ish:keyCode>
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
		<xsl:text>createdOn,modifiedOn,status,budgetedQuantity,budgetedValue,dateFor,description,course-class code,quantity,taxValue,value,payrollRef,tutor first name,tutor last name,tutor email,tutor street,tutor state,tutor postcode,tutor suburb</xsl:text>
		<xsl:value-of select="$break" />
		<xsl:apply-templates select="contact/tutorPay/payline" />
	</xsl:template>
	
	<xsl:template match="payline">
		<xsl:value-of select="ish:outputValue(../createdOn)"/>
		<xsl:value-of select="ish:outputValue(../modifiedOn)"/>
		<xsl:value-of select="ish:outputValue(../status)"/>
		<xsl:value-of select="ish:outputValue(budgetedQuantity)"/>
		<xsl:value-of select="ish:outputValue(budgetedValue)"/>
		<xsl:value-of select="ish:outputValue(dateFor)"/>
		<xsl:value-of select="ish:outputValue(description)"/>
        <xsl:apply-templates select="classCost"/>
        <xsl:if test="not(classCost)">
            <xsl:value-of select="ish:outputValue('')"/>
        </xsl:if>
		<xsl:value-of select="ish:outputValue(quantity)"/>
		<xsl:value-of select="ish:outputValue(taxValue)"/>
		<xsl:value-of select="ish:outputValue(value)"/>
        <xsl:value-of select="ish:outputValue(../tutor/payrollRef)"/>
		<xsl:apply-templates select="../.."/>
		<xsl:if test="(../../following-sibling::*) or (../following-sibling::*) or (following-sibling::*)">
			<xsl:value-of select="$break" />
		</xsl:if>
	</xsl:template>

	<xsl:template match="contact">
		<xsl:variable name="contactID" select="@id"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/firstName)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/lastName)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/email)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/street)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/state)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/postcode)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/suburb, true())"/>
	</xsl:template>

    <xsl:template match="classCost">
        <xsl:variable name="classCostID" select="@id"/>
        <xsl:value-of select="ish:outputValue(concat(/data/course/class/classCost[@id=$classCostID]/../../code, '-', /data/course/class/classCost[@id=$classCostID]/../code))"/>
    </xsl:template>

</xsl:stylesheet>