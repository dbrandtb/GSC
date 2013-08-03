<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_title_o']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.DescuentoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        <xsl:if test="@CDDSCTO">
		            <xsl:element name="cd-dscto">
		                <xsl:value-of select="@CDDSCTO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSNOMBRE">
		            <xsl:element name="ds-dscto">
		                <xsl:value-of select="@DSNOMBRE" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDTIPO">
		            <xsl:element name="cd-tipo">
		                <xsl:value-of select="@CDTIPO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSTIPO">
		            <xsl:element name="ds-tipo">
		                <xsl:value-of select="@DSTIPO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDELEMENTO">
		            <xsl:element name="cd-elemento">
		                <xsl:value-of select="@CDELEMENTO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSELEMEN">
		            <xsl:element name="ds-nombre">
		                <xsl:value-of select="@DSELEMEN" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@FGACUMUL">
		            <xsl:element name="fg-acumul">
		                <xsl:value-of select="@FGACUMUL" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSACUMUL">
		            <xsl:element name="ds-acumul">
		                <xsl:value-of select="@DSACUMUL" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSESTADO">
		            <xsl:element name="ds-estado">
		                <xsl:value-of select="@DSESTADO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDESTADO">
		            <xsl:element name="cd-estado">
		                <xsl:value-of select="@CDESTADO" />
		            </xsl:element>
		        </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>