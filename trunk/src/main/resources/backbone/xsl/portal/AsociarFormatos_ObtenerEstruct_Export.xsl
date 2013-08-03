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

			<xsl:if test="@DSPROCESO">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSPROCESO"/>
				</string>
			</xsl:if>
			
			<xsl:if test="@DSFORMATOORDEN">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSFORMATOORDEN"/>
				</string>
			</xsl:if>
			
			<xsl:if test="@DSELEMEN">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSELEMEN"/>
				</string>
			</xsl:if>		
		   
	    <xsl:if test="@DSUNIECO">
			<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:value-of select="@DSUNIECO"/>
			</string>
	    </xsl:if>
	    
	    <xsl:if test="@DSRAMO">
			<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:value-of select="@DSRAMO"/>
			</string>
	    </xsl:if>
	    		
		</item-list>         
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>