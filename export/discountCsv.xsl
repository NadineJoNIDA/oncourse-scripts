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
		<ish:entity>Discount</ish:entity>
		<ish:version>4</ish:version>
		<ish:keyCode>ish.onCourse.discountCsv</ish:keyCode>
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
		<xsl:text>name,code,modifiedOn,createdOn,canBeCombined,discountPercent,discountMax,discountMin,discountDollar,rounding,publicDescription,validFrom,validTo,combinationType,studentAge,studentConcessionObsolete,studentEnrolledWithinDays,studentPostcode</xsl:text>
				<xsl:value-of select="$break" />
		<xsl:apply-templates select="discount" />
	</xsl:template>
	
	<xsl:template match="discount">
		<xsl:value-of select="ish:outputValue(name)" />
		<xsl:value-of select="ish:outputValue(code)" />
		<xsl:value-of select="ish:outputValue(modifiedOn)" />
		<xsl:value-of select="ish:outputValue(createdOn)" />
		<xsl:value-of select="ish:outputValue(code)" />
		<xsl:value-of select="ish:outputValue(canBeCombined)" />
		<xsl:value-of select="ish:outputValue(discountPercent)" />
		<xsl:value-of select="ish:outputValue(discountMax)" />
		<xsl:value-of select="ish:outputValue(discountMin)" />
		<xsl:value-of select="ish:outputValue(discountDollar)" />
		<xsl:value-of select="ish:outputValue(rounding)" />
		<xsl:value-of select="ish:outputValue(publicDescription)" />
		<xsl:value-of select="ish:outputValue(validFrom)" />
		<xsl:value-of select="ish:outputValue(validTo)" />
		<xsl:value-of select="ish:outputValue(combinationType)" />
		<xsl:value-of select="ish:outputValue(studentAge)" />
		<xsl:value-of select="ish:outputValue(studentConcessionObsolete)" />
		<xsl:value-of select="ish:outputValue(studentEnrolledWithinDays)" />
		<xsl:value-of select="ish:outputValue(studentPostcode, true())" />
		<xsl:if test="following-sibling::*">
			<xsl:value-of select="$break" />
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>