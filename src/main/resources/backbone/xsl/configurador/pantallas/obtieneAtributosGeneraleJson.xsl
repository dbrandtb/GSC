<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <general-json-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </general-json-list>
    </xsl:template>
    
    <xsl:template match="rows">
        <xsl:for-each-group select="row" group-by="@CDCAMPO">
            <master-property  xsi:type="java:mx.com.aon.configurador.pantallas.model.AtributosMasterVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                                    
                            <xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
                                
<!--                                <xsl:if test="@DSPROPIEDAD='TipoPropiedad'">-->
<!--                                    <xsl:element name="tipo">-->
<!--                                        <xsl:value-of select="@OTVALOR"/>-->
<!--                                    </xsl:element>-->
<!--                                </xsl:if>-->
<!--                                -->
<!--                                <xsl:if test="@DSPROPIEDAD='Etiqueta'">-->
<!--                                    <xsl:element name="text">-->
<!--                                        <xsl:value-of select="@OTVALOR"/>-->
<!--                                    </xsl:element>-->
<!--                                </xsl:if>-->
<!--                                -->
<!--                                <xsl:if test="@DSPROPIEDAD='Etiqueta'">-->
<!--                                    <xsl:element name="text">-->
<!--                                        <xsl:value-of select="@OTVALOR"/>-->
<!--                                    </xsl:element>-->
<!--                                </xsl:if>                      -->

                                    
                                    
                                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                                        <key xsi:type="java:java.lang.String">
                                            <xsl:value-of select="@DSPROPIEDAD"/>
                                        </key>
                                        <value xsi:type="java:java.lang.String">
                                            <xsl:value-of select="@OTVALOR"/>
                                        </value>
                                    </values>

                               
                                
                                
                            </xsl:for-each-group>

        </master-property>      
        
                    
                </xsl:for-each-group>
          
        
    </xsl:template>
    
    
    
</xsl:stylesheet>
