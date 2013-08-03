<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:template match="/">
        <master-vO xsi:type="java:com.aon.catweb.configurador.pantallas.model.master.MasterVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </master-vO>        
    </xsl:template>
    
    <xsl:template match="rows">
        <xsl:for-each-group select="row" group-by="@CDBLOQUE">
            <seccion-list xsi:type="java:com.aon.catweb.configurador.pantallas.model.master.SectionVo" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                    <xsl:if test="@DSBLOQUE">
                        <xsl:element name="ds-bloque">
                            <xsl:value-of select="@DSBLOQUE"/>
                        </xsl:element>
                    </xsl:if>
                    
                    <xsl:if test="@CDBLOQUE">
                        <xsl:element name="cd-bloque">
                            <xsl:value-of select="@CDBLOQUE"/>
                        </xsl:element>
                    </xsl:if>
                    
                    <xsl:for-each-group select="current-group()" group-by="@CDCAMPO">
                        <field-list xsi:type="java:com.aon.catweb.configurador.pantallas.model.master.FieldVo" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            
                            <xsl:if test="@CDCAMPO">
                                <xsl:element name="cd-campo">
                                    <xsl:value-of select="@CDCAMPO"/>
                                </xsl:element>
                            </xsl:if>
                    
                            <xsl:if test="@DSCAMPO">
                                <xsl:element name="ds-campo">
                                    <xsl:value-of select="@DSCAMPO"/>
                                </xsl:element>
                            </xsl:if>
                            
                            <xsl:if test="@CDPROPIEDAD!='' "  >
                            
                                <xsl:for-each-group select="current-group()" group-by="@CDPROPIEDAD" >
                                    <property-list xsi:type="java:com.aon.catweb.configurador.pantallas.model.master.PropertyVo" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                        
                                        <xsl:if test="@DSPROPIEDAD">
                                            <xsl:element name="ds-propiedad">
                                                <xsl:value-of select="@DSPROPIEDAD"/>
                                            </xsl:element>
                                        </xsl:if>
                        
                                        <xsl:if test="@OTVALOR">
                                            <xsl:element name="otvalor">
                                                <xsl:value-of select="@OTVALOR"/>
                                            </xsl:element>
                                        </xsl:if>
                                
                                        <xsl:if test="@SWMODIFICABLE">
                                            <xsl:element name="sw-modificable">
                                                <xsl:value-of select="@SWMODIFICABLE"/>
                                            </xsl:element>
                                        </xsl:if>
                        
                                        <xsl:if test="@CDPROPIEDAD">
                                            <xsl:element name="cd-propiedad">
                                                <xsl:value-of select="@CDPROPIEDAD"/>
                                            </xsl:element>
                                        </xsl:if>                               
                                    
                                    </property-list>
                                </xsl:for-each-group>                       
                            </xsl:if>       
                            
                        </field-list>
                    </xsl:for-each-group>  
                    
            </seccion-list>
        </xsl:for-each-group>
    </xsl:template>
        
</xsl:stylesheet>
