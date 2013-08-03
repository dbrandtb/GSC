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
            <item-list xsi:type="java:mx.com.aon.catbo.model.PolizasVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        <xsl:if test="@CDUNIECO">
		            <xsl:element name="cdunieco">
		                <xsl:value-of select="@CDUNIECO" />
		            </xsl:element>
		        </xsl:if>
		        
                <xsl:if test="@DSUNIECO">
                    <xsl:element name="dsunieco">
                        <xsl:value-of select="@DSUNIECO" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@CDRAMO">
                    <xsl:element name="cdramo">
                        <xsl:value-of select="@CDRAMO" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@DSRAMO">
                    <xsl:element name="dsramo">
                        <xsl:value-of select="@DSRAMO" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@ESTADO">
                    <xsl:element name="estado">
                        <xsl:value-of select="@ESTADO" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@NMPOLIZA">
                    <xsl:element name="nmpoliza">
                        <xsl:value-of select="@NMPOLIZA" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@NMPOLIEX">
                    <xsl:element name="nmpoliex">
                        <xsl:value-of select="@NMPOLIEX" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@NMSITUAEXT">
                    <xsl:element name="nmsituaext">
                        <xsl:value-of select="@NMSITUAEXT" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@NMSBSITEXT">
                    <xsl:element name="nmsbsitext">
                        <xsl:value-of select="@NMSBSITEXT" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@DSROLMAT">
                    <xsl:element name="dsrolmat">
                        <xsl:value-of select="@DSROLMAT" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@NOMBREPERSONA">
                    <xsl:element name="nombre-persona">
                        <xsl:value-of select="@NOMBREPERSONA" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@CDRFC">
                    <xsl:element name="cdrfc">
                        <xsl:value-of select="@CDRFC" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@FEINIVAL">
                    <xsl:element name="feinival">
                        <xsl:value-of select="@FEINIVAL" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@FEFINVAL">
                    <xsl:element name="fefinval">
                        <xsl:value-of select="@FEFINVAL" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@DSFORPAG">
                    <xsl:element name="dsforpag">
                        <xsl:value-of select="@DSFORPAG" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@SUMA">
                    <xsl:element name="suma">
                        <xsl:value-of select="@SUMA" />
                    </xsl:element>
                </xsl:if>
               
               
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

