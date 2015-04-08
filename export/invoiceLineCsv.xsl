<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:ish="http://www.ish.com.au/schema/2009/onCourseTransform"
                xsi:schemaLocation="http://www.ish.com.au/schema/2009/onCourseTransform http://www.ish.com.au/schema/2009/onCourseTransform.xsd"
                exclude-result-prefixes="xsi ish" version="2.0"
                xpath-default-namespace="http://www.ish.com.au/schema/4/onCourseData">
	<xsl:output method="text" encoding="UTF-8" indent="no" />

	<ish:onCourse>
		<ish:name>InvoiceLine CSV</ish:name>
		<ish:entity>Invoice</ish:entity>
		<ish:version>10</ish:version>
		<ish:keyCode>ish.onCourse.invoiceLineCsv</ish:keyCode>
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
		<xsl:text>Invoice Number,Invoice Date,Invoice line Title,Invoice line Description,Invoice To Last Name,Invoice To First Name,Invoice line Account Code,Invoice line Account name,Invoice line amount,Invoice line Tax,Tax Type,Invoice line discount,Invoice Owing,Invoice prepaid fees remaining,Invoice line Quantity,Invoice description,Invoice Due Date,Invoice Created by,Invoice Source,Invoice modified on,Invoice created on</xsl:text>
				<xsl:value-of select="$break" />
		<xsl:apply-templates select="invoice" />
	</xsl:template>
	
	<xsl:template match="invoice">
		<xsl:apply-templates select="invoiceLine" />
	</xsl:template>
	
	<xsl:template match="invoiceLine"> 
		<xsl:value-of select="ish:outputValue(../invoiceNumber)" />  
		<xsl:value-of select="ish:outputValue(../invoiceDate)" /> 
		<xsl:value-of select="ish:outputValue(title)"/>
		<xsl:value-of select="ish:outputValue(description)"/>
		<xsl:apply-templates select="../contact"/>
		<xsl:apply-templates select="account"/>
		<xsl:value-of select="ish:outputValue(price/amount)"/>
		<xsl:value-of select="ish:outputValue(price/taxAmount)"/>
		<xsl:value-of select="ish:outputValue(price/taxName)"/>
		<xsl:value-of select="ish:outputValue(price/discountAmount)"/> 
		<xsl:value-of select="ish:outputValue(../amountOwing)" />  
		<xsl:value-of select="ish:outputValue(prepaidFeesRemaining)"/>
		<xsl:value-of select="ish:outputValue(quantity)"/>
		<xsl:value-of select="ish:outputValue(../description)" />
		<xsl:value-of select="ish:outputValue(../dateDue)" />
		<xsl:value-of select="ish:outputValue(../createdByUserName)"/>
		<xsl:value-of select="ish:outputValue(../source)" /> 
		<xsl:value-of select="ish:outputValue(format-dateTime(../modifiedOn, '[D]/[M]/[Y] [h]:[m01][Pn]'))" />
		<xsl:value-of select="ish:outputValue(format-dateTime(../createdOn, '[D]/[M]/[Y] [h]:[m01][Pn]'))" />
		<xsl:value-of select="$break" />
	</xsl:template>
	
	<xsl:template match="contact">
		<xsl:variable name="contactID" select="@id" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID][1]/lastName)" />
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID][1]/firstName)" />
	</xsl:template> 
	
	<xsl:template match="account">
		<xsl:variable name="accountID" select="@id" />
		<xsl:value-of select="ish:outputValue(/data/account[@id=$accountID][1]/accountCode)" />
		<xsl:value-of select="ish:outputValue(/data/account[@id=$accountID][1]/description)" /> 
	</xsl:template>

</xsl:stylesheet>
