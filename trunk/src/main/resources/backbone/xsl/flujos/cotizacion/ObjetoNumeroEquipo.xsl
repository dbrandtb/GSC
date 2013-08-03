<?xml version="1.0" encoding="ISO-8859-1"?>
	<!-- edited with XML Spy v4.3 U (http://www.xmlspy.com) by a (a) -->
	<xsl:stylesheet version="1.0"
	 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	 xmlns:fo="http://www.w3.org/1999/XSL/Format">
	 <xsl:template match="/">
	 	 <integer xsi:type="java:java.lang.Integer">
	                <xsl:value-of select="result/storedProcedure/outparam[@*[position()=1]]/@value" />
	  	</integer>
	 </xsl:template>
	 
</xsl:stylesheet>