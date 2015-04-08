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
        <ish:entity>Certificate</ish:entity>
        <ish:version>1</ish:version>
        <ish:keyCode>ish.onCourse.certificateCQRCsv</ish:keyCode>
    </ish:onCourse>

    <xsl:param name="delim" select="'&#x9;'" />
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

    <xsl:function name="ish:outputGender">
        <xsl:param name="value" as="xs:string?"/>
        <xsl:choose>
            <xsl:when test="$value='true'">
                <xsl:value-of select="ish:outputValue('M', false())" />
            </xsl:when>
            <xsl:when test="$value='false'">
                <xsl:value-of select="ish:outputValue('F', false())" />
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="ish:outputValue('@', false())" />
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>

    <xsl:function name="ish:outputIssuedType">
        <xsl:param name="value" as="xs:string?"/>
        <xsl:choose>
            <xsl:when test="$value='true'">
                <xsl:value-of select="ish:outputValue('Q', false())" />
            </xsl:when>
            <xsl:when test="$value='false'">
                <xsl:value-of select="ish:outputValue('S', false())" />
            </xsl:when>
        </xsl:choose>
    </xsl:function>

    <xsl:template match="data">
        <xsl:text>[VERSION200]</xsl:text>
        <xsl:value-of select="$break" />
        <xsl:apply-templates select="certificate" />
    </xsl:template>

    <xsl:template match="certificate">
        <xsl:apply-templates select="enrolment" />
        <xsl:apply-templates select="contact" />
        <xsl:value-of select="ish:outputValue(nationalCode)" />
        <xsl:value-of select="ish:outputIssuedType(fullQualification)" />
        <xsl:value-of select="ish:outputValue(format-dateTime(createdOn,'[D01]/[M01]/[Y0001]'))" />
        <xsl:value-of select="ish:outputValue(certificateNumber)" />
        <xsl:value-of select="ish:outputValue('English')" />
        <xsl:apply-templates select="module" />
        <xsl:value-of select="$break" />
    </xsl:template>

    <xsl:template match="enrolment">
        <xsl:variable name="enrolmentID" select="@id" />
        <xsl:value-of select="ish:outputValue(/data/enrolment[@id=$enrolmentID][1]/vetClientID)" />
    </xsl:template>

    <xsl:template match="contact">
        <xsl:variable name="contactID" select="@id" />
        <xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/student/studentNumber)" />
        <xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/firstName)" />
        <xsl:value-of select="ish:outputValue(/data/contact[@id=$contactID]/lastName)" />
        <xsl:value-of select="ish:outputValue(format-date(/data/contact[@id=$contactID]/birthDate,'[D01]/[M01]/[Y0001]'))" />
        <xsl:value-of select="ish:outputGender(/data/contact[@id=$contactID]/male)" />
    </xsl:template>

    <xsl:template match="module">
        <xsl:variable name="moduleID" select="@id" />
        <xsl:value-of select="ish:outputValue(/data/module[@id=$moduleID]/nationalCode)" />
    </xsl:template>

</xsl:stylesheet>