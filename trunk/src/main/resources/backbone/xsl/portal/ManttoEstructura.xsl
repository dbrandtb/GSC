<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" 
xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
             <!-- Aqui hay que seleccionar el parametro de salida de tipo cursor que queremos tomar -->
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <xsl:apply-templates select="." />
        </xsl:for-each>
    </xsl:template>
    <xsl:template match="row">
        <inciso-vO xsi:type="java:mx.com.portal.model.EstructuraVO">
            <xsl:if test="@CDESTRUC">
                <xsl:element name="cd-estruct">
                    <xsl:value-of select="@CDESTRUCT" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSESTRUCT">
                <xsl:element name="ds-estruct">
                    <xsl:value-of select="@DSESTRUCT" />
                </xsl:element>
            </xsl:if>
        </inciso-vO>
    </xsl:template>
</xsl:stylesheet>


<!-- ?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.royalsun.to.ResultadoDAO">
            <xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
                    <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="error"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
            </xsl:element>
        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.portal.model.EstructuraVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDESTRUC">
                    <xsl:element name="cd-estruct">
                        <xsl:value-of select="@CDESTRUCT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSESTRUCT">
                    <xsl:element name="ds-estruct">
                        <xsl:value-of select="@DSESTRUCT" />
                    </xsl:element>
                </xsl:if>
            </cursor>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="error">
        <xsl:element name="cd-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
        </xsl:element>
        <xsl:element name="ds-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet-->

