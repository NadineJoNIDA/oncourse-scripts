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
		<ish:entity>PaymentOut</ish:entity>
		<ish:version>8</ish:version>
		<ish:keyCode>ish.onCourse.paymentOutCsv</ish:keyCode>
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
		<xsl:text>id,payeeLastName,payeeFirstName,payeeEmail,modifiedOn,createdOn,amount,dateBanked,reconciled,status,type,creditCardCVV,creditCardExpiry,creditCardName,creditCardNumber,creditCardType,gatewayReference,chequeBank,chequeBranch,chequeDrawer,privateNotes</xsl:text>
		<xsl:value-of select="$break" />
		<xsl:apply-templates select="paymentOut" />
	</xsl:template>
	
	<xsl:template match="paymentOut">
		<xsl:value-of select="ish:outputValue(@id)" />
		<xsl:apply-templates select="contact" />
		<xsl:value-of select="ish:outputValue(modifiedOn)" />
		<xsl:value-of select="ish:outputValue(createdOn)" />
		<xsl:value-of select="ish:outputValue(amount)" />
		<xsl:value-of select="ish:outputValue(dateBanked)" />
		<xsl:value-of select="ish:outputValue(reconciled)" />
		<xsl:value-of select="ish:outputValue(status)" />
		<xsl:value-of select="ish:outputValue(type)" />
		<xsl:value-of select="ish:outputValue(creditCardCVV)" />
		<xsl:value-of select="ish:outputValue(creditCardExpiry)" />
		<xsl:value-of select="ish:outputValue(creditCardName)" />
		<xsl:value-of select="ish:outputValue(creditCardNumber)" />
		<xsl:value-of select="ish:outputValue(creditCardType)" />
		<xsl:value-of select="ish:outputValue(gatewayReference)" />
		<xsl:value-of select="ish:outputValue(chequeBank)" />
		<xsl:value-of select="ish:outputValue(chequeBranch)" />
		<xsl:value-of select="ish:outputValue(chequeDrawer)" />
		<xsl:value-of select="ish:outputValue(privateNotes, true())" />
		<xsl:if test="following-sibling::*">
			<xsl:value-of select="$break" />
		</xsl:if>
	</xsl:template>

	<xsl:template match="contact">
		<xsl:variable name="contactID" select="@id" />
			<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/lastName)" />
			<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/firstName)" />
			<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/email)" />
	</xsl:template>

</xsl:stylesheet>
