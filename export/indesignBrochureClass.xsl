<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:ish="http://www.ish.com.au/schema/2009/onCourseTransform"
	xmlns="http://www.ish.com.au/schema/4/onCourseData" xpath-default-namespace="http://www.ish.com.au/schema/4/onCourseData"
	xsi:schemaLocation="http://www.ish.com.au/schema/2009/onCourseTransform http://www.ish.com.au/schema/2009/onCourseTransform.xsd"
	exclude-result-prefixes="xsi ish" version="2.0">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="no" />
	<ish:onCourse>
		<ish:name>Indesign Brochure</ish:name>
		<ish:entity>CourseClass</ish:entity>
		<ish:version>9</ish:version>
		<ish:keyCode>ish.onCourse.indesignBrochureClass</ish:keyCode>
	</ish:onCourse>
	<xsl:variable name="newline">
		<xsl:text>
</xsl:text>
	</xsl:variable>
	<xsl:variable name="tab">
		<xsl:text>	</xsl:text>
	</xsl:variable>
	<xsl:template match="/data">
		<brochure>
			<xsl:apply-templates select="tag[name='Subjects']" />
		</brochure>
	</xsl:template>
	<xsl:template match="tag">
		<Tag>
			<xsl:value-of select="name" />
			<xsl:value-of select="$newline" />
			<xsl:variable name="tagID" select="@id" />
			<xsl:apply-templates select="/data/course[tag/@id=$tagID]" />
			<xsl:apply-templates select="tag" />
		</Tag>
	</xsl:template>
	<xsl:template match="course">
		<Course>
			<CourseName>
				<xsl:value-of select="name" />
			</CourseName>
			<xsl:value-of select="$newline" />
			<Description>
				<xsl:value-of select="printedBrochureDescription" />
			</Description>
			<xsl:value-of select="$newline" />
			<xsl:apply-templates select="class" />
			<courseHorizontalLine />
			<xsl:value-of select="$newline" />
		</Course>
	</xsl:template>
	<xsl:template match="class">
		<Class>
			<xsl:apply-templates select="tutorRole" />
			<xsl:value-of select="$newline" />
			<ClassCode><xsl:value-of select="../code" />-<xsl:value-of select="code" /></ClassCode>
			<xsl:value-of select="$tab" />
			<xsl:apply-templates select="room" />
			<xsl:value-of select="$newline" />
			<!--
				see http://www.w3.org/TR/xslt20/#date-picture-string for formatting options 
			-->
			<xsl:value-of select="format-dateTime(startDateTime, '[FNn,0-4] [D] [MNn]')" />
			<xsl:value-of select="$tab" />
			<xsl:value-of select="replace(format-dateTime(startDateTime, '[h]:[m01][Pn]'),'\.', '' )" />
			<xsl:text>-</xsl:text>
			<xsl:value-of select="replace(format-dateTime(endDateTime, '[h]:[m01][Pn]'),'\.', '' )" />
			<xsl:value-of select="$newline" />
			<xsl:variable name="sessionCount" select="count(session)" />
			<xsl:value-of select="$sessionCount" />
			<xsl:choose>
				<xsl:when test="$sessionCount > 1"> sessions of </xsl:when>
				<xsl:otherwise> session of </xsl:otherwise>
			</xsl:choose>
			<xsl:variable name="duration"
				select="xs:dayTimeDuration(xs:dateTime(session[1]/end) - xs:dateTime(session[1]/start)) div xs:dayTimeDuration('PT1H')" />
			<AverageHours>
				<xsl:value-of select="$duration" />
				<xsl:choose>
					<xsl:when test="$duration > 1"> hrs </xsl:when>
					<xsl:otherwise> hr </xsl:otherwise>
				</xsl:choose>
			</AverageHours>
			<xsl:value-of select="$tab" />
			<Price>
				<xsl:value-of select="format-number(price[1]/amount,'$###,###0.00')" />
			</Price>
			<xsl:value-of select="$newline" />
		</Class>
	</xsl:template>
	<xsl:template match="tutorRole">
		<Tutor>
			<xsl:variable name="tutorID" select="contact/@id" />
			<xsl:value-of select="/data/contact[@id=$tutorID]/firstName" />
			<xsl:text> </xsl:text>
			<xsl:value-of select="/data/contact[@id=$tutorID]/lastName" />
		</Tutor>
	</xsl:template>
	<xsl:template match="room">
		<Site>
			<xsl:variable name="roomID" select="@id" />
			<xsl:value-of select="/data/site/room[@id=$roomID]/../name" />
		</Site>
	</xsl:template>
</xsl:stylesheet>
