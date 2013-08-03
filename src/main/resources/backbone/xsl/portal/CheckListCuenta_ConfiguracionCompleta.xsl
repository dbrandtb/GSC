<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- edited with XML Spy v4.3 U (http://www.xmlspy.com) by a (a) -->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam"/>
        </wrapper-resultados>
    </xsl:template>
    <xsl:template match="outparam">
            <xsl:if test="@id = 'po_resultado' ">
                <resultado>
                    <xsl:value-of select="@value"/>
                </resultado>
            </xsl:if>
            <xsl:if test="@id = 'pv_msg_id_o' ">
                <msg-id>
                    <xsl:value-of select="@value"/>
                </msg-id>
            </xsl:if>
            <xsl:if test="@id = 'pv_title_o' ">
                <msg>
                    <xsl:value-of select="@value"/>
                </msg>
            </xsl:if>
    </xsl:template>
</xsl:stylesheet>

        