<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg-text">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.CatalogoLogicoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        
		        <xsl:if test="@CDTABLA">
		            <xsl:element name="cdtabla">
		                <xsl:value-of select="@CDTABLA" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDREGION">
		            <xsl:element name="cdregion">
		                <xsl:value-of select="@CDREGION" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSREGION">
		            <xsl:element name="dsregion">
		                <xsl:value-of select="@DSREGION" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDIDIOMA">
		            <xsl:element name="cdidioma">
		                <xsl:value-of select="@CDIDIOMA" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSIDIOMA">
		            <xsl:element name="dsidioma">
		                <xsl:value-of select="@DSIDIOMA" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CODIGO">
		            <xsl:element name="codigo">
		                <xsl:value-of select="@CODIGO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DESCRIPC">
		            <xsl:element name="descripcion">
		                <xsl:value-of select="@DESCRIPC" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DESCRIPL">
		            <xsl:element name="descripcion-larga">
		                <xsl:value-of select="@DESCRIPL" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSLABEL">
		            <xsl:element name="etiqueta">
		                <xsl:value-of select="@DSLABEL" />
		            </xsl:element>
		        </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>