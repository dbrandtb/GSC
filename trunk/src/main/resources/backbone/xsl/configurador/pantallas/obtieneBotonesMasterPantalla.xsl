<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <button-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </button-list>
    </xsl:template>
    
    <xsl:template match="rows">
        <xsl:for-each-group select="row" group-by="@CDCAMPO">
            <master-button  xsi:type="java:com.biosnet.ice.ext.elements.form.ButtonMaster" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                                    
                            <xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
                                
                                <xsl:if test="@DSPROPIEDAD='NombreTecnico'">
                                    <xsl:element name="id">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                <xsl:if test="@DSPROPIEDAD='Etiqueta'">
                                    <xsl:element name="text">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                <xsl:if test="@DSPROPIEDAD='Tipoboton'">
                                    <xsl:element name="button-type">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                               
                                
                                <xsl:if test="@DSPROPIEDAD='Actionexito'">
                                    <xsl:element name="success-action">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                
								 <xsl:if test="@DSPROPIEDAD='Actionfracaso'">
                                    <xsl:element name="failure-action">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                 <xsl:if test="@DSPROPIEDAD='Urlboton'">
                                    <xsl:element name="url">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                
                            </xsl:for-each-group>

        </master-button>      
        
                    
                </xsl:for-each-group>
          
        
    </xsl:template>
    
    
    
</xsl:stylesheet>
