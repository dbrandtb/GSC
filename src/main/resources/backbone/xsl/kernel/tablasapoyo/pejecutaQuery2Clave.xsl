<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            
             
                    <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
            
        </resultado-dao>
    </xsl:template>
    
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.ice.kernel.bo.BeanTablaApoyo" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@OTVALOR01">
                    <xsl:element name="otvalor1">
                        <xsl:value-of select="@OTVALOR01" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR02">
                    <xsl:element name="otvalor2">
                        <xsl:value-of select="@OTVALOR02" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR03">
                    <xsl:element name="otvalor3">
                        <xsl:value-of select="@OTVALOR03" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR04">
                    <xsl:element name="otvalor4">
                        <xsl:value-of select="@OTVALOR04" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR05">
                    <xsl:element name="otvalor5">
                        <xsl:value-of select="@OTVALOR05" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR06">
                    <xsl:element name="otvalor6">
                        <xsl:value-of select="@OTVALOR06" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR07">
                    <xsl:element name="otvalor7">
                        <xsl:value-of select="@OTVALOR07" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR08">
                    <xsl:element name="otvalor8">
                        <xsl:value-of select="@OTVALOR08" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR09">
                    <xsl:element name="otvalor9">
                        <xsl:value-of select="@OTVALOR09" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR10">
                    <xsl:element name="otvalor10">
                        <xsl:value-of select="@OTVALOR10" />
                    </xsl:element>
                </xsl:if>


                <xsl:if test="@OTVALOR11">
                    <xsl:element name="otvalor11">
                        <xsl:value-of select="@OTVALOR11" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR12">
                    <xsl:element name="otvalor12">
                        <xsl:value-of select="@OTVALOR12" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR13">
                    <xsl:element name="otvalor13">
                        <xsl:value-of select="@OTVALOR13" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR14">
                    <xsl:element name="otvalor14">
                        <xsl:value-of select="@OTVALOR14" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR15">
                    <xsl:element name="otvalor15">
                        <xsl:value-of select="@OTVALOR15" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR16">
                    <xsl:element name="otvalor16">
                        <xsl:value-of select="@OTVALOR16" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR17">
                    <xsl:element name="otvalor17">
                        <xsl:value-of select="@OTVALOR17" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR18">
                    <xsl:element name="otvalor18">
                        <xsl:value-of select="@OTVALOR18" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR19">
                    <xsl:element name="otvalor19">
                        <xsl:value-of select="@OTVALOR19" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR20">
                    <xsl:element name="otvalor20">
                        <xsl:value-of select="@OTVALOR20" />
                    </xsl:element>
                </xsl:if>


                <xsl:if test="@OTVALOR21">
                    <xsl:element name="otvalor21">
                        <xsl:value-of select="@OTVALOR21" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR22">
                    <xsl:element name="otvalor22">
                        <xsl:value-of select="@OTVALOR22" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR23">
                    <xsl:element name="otvalor23">
                        <xsl:value-of select="@OTVALOR23" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR24">
                    <xsl:element name="otvalor24">
                        <xsl:value-of select="@OTVALOR24" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@OTVALOR25">
                    <xsl:element name="otvalor25">
                        <xsl:value-of select="@OTVALOR25" />
                    </xsl:element>
                </xsl:if>

            </cursor>
        </xsl:for-each>
    </xsl:template>
   
</xsl:stylesheet>
