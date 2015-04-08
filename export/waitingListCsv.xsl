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
		<ish:entity>WaitingList</ish:entity>
		<ish:version>3</ish:version>
		<ish:keyCode>ish.onCourse.waitingListCsv</ish:keyCode>
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
	
	<xsl:template match="data">	<xsl:text>Course code,Course name,created,Student count,Notes,Student notes,Title,First name,Last name,Email,Street,Suburb,State,Post code,Mobile phone,Home phone,Work phone,Birth date</xsl:text>
		<xsl:value-of select="$break" />
		<xsl:apply-templates select="waitingList" />
	</xsl:template>
	
	<xsl:template match="waitingList">
		<xsl:apply-templates select="course"/>

		<xsl:value-of select="ish:outputValue(createdOn)"/>
		<xsl:value-of select="ish:outputValue(studentCount)"/>
		<xsl:value-of select="ish:outputValue(notes)"/>
		<xsl:value-of select="ish:outputValue(studentNotes)"/>
		
		<xsl:apply-templates select="contact"/>

		<xsl:if test="(../following-sibling::*) or (following-sibling::*)">
			<xsl:value-of select="$break" />
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="course">
		<xsl:variable name="courseId" select="@id"/>
		
		<xsl:value-of select="ish:outputValue(/data/course[@id=$courseId][1]/code)"/>
		<xsl:value-of select="ish:outputValue(/data/course[@id=$courseId][1]/name)"/>
	</xsl:template>
	
	<xsl:template match="contact">
		<xsl:variable name="contactId" select="@id"/>
		
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/title)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/firstName)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/lastName)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/email)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/street)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/suburb)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/state)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/postcode)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/mobilePhone)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/homePhone)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/workPhone)"/>
		<xsl:value-of select="ish:outputValue(/data/contact[@id=$contactId][1]/birthDate, true())"/>
	</xsl:template>
</xsl:stylesheet>
