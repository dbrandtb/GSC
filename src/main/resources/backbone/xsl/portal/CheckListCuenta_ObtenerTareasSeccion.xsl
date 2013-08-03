<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:java="http://xml.apache.org/xslt/java"
                exclude-result-prefixes="java">

    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_msg_id']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_title']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>

    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.CheckListCtaTareaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <!-- Aqui ponemos nuestro VO -->
                <xsl:if test="@CDTAREA">
                    <xsl:element name="codigo-tarea">
                        <xsl:value-of select="@CDTAREA"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSTAREA">
                    <xsl:element name="ds-tarea">
                        <xsl:value-of select="@DSTAREA"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FGCOMPLE">
                    <xsl:element name="fg-comple">
                        <xsl:value-of select="@FGCOMPLE"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FGNOREQU">
                    <xsl:element name="fg-no-requ">
                        <xsl:value-of select="@FGNOREQU"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTAREA_PADRE">
                    <xsl:element name="cdtarea-padre">
                        <xsl:value-of select="@CDTAREA_PADRE"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSTAREAPADRE">
                    <xsl:element name="ds-tarea-padre">
                        <xsl:value-of select="@DSTAREAPADRE"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSURL">
                    <xsl:element name="ds-url">
                        <xsl:value-of select="@DSURL"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSAYUDA">
                    <xsl:element name="ds-ayuda">
                        <xsl:value-of select="@DSAYUDA"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FG_PENDIENTE">
                    <xsl:element name="fg-pendiente">
                        <xsl:value-of select="@FG_PENDIENTE"/>
                    </xsl:element>
                </xsl:if>


           </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>