<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            p_FECHA
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="result/storedProcedure/outparam[@id='p_FECHA']/@value"/>
                        </value>
                    </values>
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            p_HORA
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="result/storedProcedure/outparam[@id='p_HORA']/@value"/>
                        </value>
                    </values>
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            p_NMSUPLEM
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="result/storedProcedure/outparam[@id='p_NMSUPLEM']/@value"/>
                        </value>
                    </values>
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            p_NSUPLOGI
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="result/storedProcedure/outparam[@id='p_NSUPLOGI']/@value"/>
                        </value>
                    </values>
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            p_FEINIVAL
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="result/storedProcedure/outparam[@id='p_FEINIVAL']/@value"/>
                        </value>
                    </values>
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            p_FEFINVAL
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="result/storedProcedure/outparam[@id='p_FEFINVAL']/@value"/>
                        </value>
                    </values>
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            p_HHINIVAL
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="result/storedProcedure/outparam[@id='p_HHINIVAL']/@value"/>
                        </value>
                    </values>
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            p_HHFINVAL
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="result/storedProcedure/outparam[@id='p_HHFINVAL']/@value"/>
                        </value>
                    </values>
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            p_SWANULA
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="result/storedProcedure/outparam[@id='p_SWANULA']/@value"/>
                        </value>
                    </values>
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
    <xsl:template name="error">
        <xsl:element name="cd-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
        </xsl:element>
        <xsl:element name="ds-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
