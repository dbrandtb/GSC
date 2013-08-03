<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
		<item-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			
			<xsl:if test="@DSNOMBRECONJUNTO">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSNOMBRECONJUNTO"/>
				</string>
			</xsl:if>
			
			<xsl:if test="@DSDESCCONJUNTO">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSDESCCONJUNTO"/>
				</string>
			</xsl:if>		
		    
		    <xsl:if test="@DSPROCESO">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSPROCESO"/>
				</string>
		    </xsl:if>	
		    	
		    <xsl:if test="@DSELEMEN">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSELEMEN"/>
				</string>
		    </xsl:if>	

		</item-list>         
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>