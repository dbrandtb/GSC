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
            <item-list xsi:type="java:mx.com.aon.catbo.model.CasoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        <xsl:if test="@CDNUMECASO">
		            <xsl:element name="cd-num-caso">
		                <xsl:value-of select="@CDNUMECASO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDMODULO">
		            <xsl:element name="cd-modulo">
		                <xsl:value-of select="@CDMODULO" />
		            </xsl:element>
		        </xsl:if>
                <xsl:if test="@DSMODULO">
                    <xsl:element name="des-modulo">
                        <xsl:value-of select="@DSMODULO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@IND_NUMERACION">
                    <xsl:element name="ind-numer">
                        <xsl:value-of select="@IND_NUMERACION" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMDESDE">
                    <xsl:element name="nm-desde">
                        <xsl:value-of select="@NMDESDE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMHASTA">
                    <xsl:element name="nm-hasta">
                        <xsl:value-of select="@NMHASTA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMCASO">
                    <xsl:element name="nm-caso">
                        <xsl:value-of select="@NMCASO" />
                    </xsl:element>
                </xsl:if>               
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

