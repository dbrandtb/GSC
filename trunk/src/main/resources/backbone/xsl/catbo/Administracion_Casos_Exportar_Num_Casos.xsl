<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
		<wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
            </xsl:element>
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </wrapper-resultados>
    </xsl:template>
 
     <xsl:template match="rows">
        <xsl:for-each select="row">
		<item-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:if test="@DSMODULO">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSMODULO"/>
				</string>
			</xsl:if>
            <xsl:if test="@NMDESDE">
                <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:value-of select="@NMDESDE"/>
                </string>
            </xsl:if>	
            <xsl:if test="@NMHASTA">
                <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:value-of select="@NMHASTA"/>
                </string>
            </xsl:if>	                		
            <xsl:if test="@NMCASO">
                <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:value-of select="@NMCASO"/>
                </string>
            </xsl:if>	                		

		</item-list>         
        </xsl:for-each>
    </xsl:template>
 
</xsl:stylesheet>