<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <master-vO xsi:type="java:com.biosnet.ice.ext.elements.model.MasterWrapperVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </master-vO>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each-group select="row" group-by="@CDBLOQUE">
            <section-list xsi:type="java:com.biosnet.ice.ext.elements.model.SectionVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@DSBLOQUE">
                    <xsl:element name="ds-block">
                        <xsl:value-of select="@DSBLOQUE"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDBLOQUE">
                    <xsl:element name="cd-block">
                        <xsl:value-of select="@CDBLOQUE"/>
                    </xsl:element>
                </xsl:if>
                <xsl:for-each-group select="current-group()" group-by="@CDCAMPO">
                    <field-list xsi:type="java:com.biosnet.ice.ext.elements.model.FieldVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:if test="@CDCAMPO">
                            <xsl:element name="cd-field">
                                <xsl:value-of select="@CDCAMPO"/>
                            </xsl:element>
                        </xsl:if>
                        <xsl:if test="@DSCAMPO">
                            <xsl:element name="ds-field">
                                <xsl:value-of select="@DSCAMPO"/>
                            </xsl:element>
                        </xsl:if>
                        
                        
                        
                        <xsl:if test="@OTVALOR = '2'">
                            <!--xsl:call-template name="textfield-item"/-->
                             <control-item xsi:type="java:com.biosnet.ice.ext.elements.form.TextFieldControl">
                            
                            <!--xsl:element name="xtype">textfield</xsl:element-->
                            <xsl:element name="hidde-parent">true</xsl:element>                            
                            <xsl:element name="width">200</xsl:element>
                            <!--xsl:element name="type">text</xsl:element-->
                            
                            <xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
                                
                                <xsl:if test="@DSPROPIEDAD='NombreTecnico'">
                                    <xsl:element name="name">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                    
                                    <xsl:element name="id">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                <xsl:if test="@DSPROPIEDAD='LongitudMinima'">
                                    <xsl:element name="min-length">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                <xsl:if test="@DSPROPIEDAD='LongitudMaxima'">
                                    <xsl:element name="max-length">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                <xsl:if test="@DSPROPIEDAD='Etiqueta'">
                                    <xsl:element name="field-label">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                            </xsl:for-each-group>

        </control-item>
                                                      
                        </xsl:if>
                        
                        <!-- valor 5 para pruebas aunque es 1 -->
                        <xsl:if test="@OTVALOR = '5'">
                            <!--xsl:call-template name="combo-item"/-->
                            <!--control-item  xsi:type="java:com.aon.catweb.configurador.pantallas.model.controls.ComboControl"-->
                            <control-item  xsi:type="java:com.biosnet.ice.ext.elements.form.ComboMaster">
                            
                            <!--xsl:element name="xtype">combo</xsl:element-->
                            <xsl:element name="display-field">label</xsl:element>
                            <xsl:element name="value-field">value</xsl:element>
                            <xsl:element name="empty-text">Seleccione...</xsl:element>
                            <xsl:element name="trigger-action">all</xsl:element>
                            <xsl:element name="type-ahead">true</xsl:element>
                            <xsl:element name="mode">local</xsl:element>
                            <xsl:element name="force-selection">true</xsl:element>
                            <xsl:element name="list-width">200</xsl:element>
                            <xsl:element name="width">200</xsl:element>
                            <xsl:element name="select-on-focus">true</xsl:element>
                                     
                            <xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
                                
                                <xsl:if test="@DSPROPIEDAD='NombreTecnico'">
                                    <xsl:element name="name">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                    
                                    <xsl:element name="id">                                        
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                    
                                    <xsl:element name="hidden-name">
                                        h<xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                    
                                   
                                    
                                </xsl:if>
                                
                                <xsl:if test="@DSPROPIEDAD='ListaValores'">
                                    <xsl:element name="backup-table">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                <xsl:if test="@DSPROPIEDAD='Etiqueta'">
                                    <xsl:element name="field-label">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                
                            </xsl:for-each-group>
        </control-item>
                            
                            
                        </xsl:if>
                        
                        <xsl:call-template name="field-properties"/>
                        
                    </field-list>
                </xsl:for-each-group>
            </section-list>
        </xsl:for-each-group>
    </xsl:template>
    
    <xsl:template name="field-properties">
        <xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
                                
            <xsl:if test="@DSPROPIEDAD='Visible'">
                <xsl:element name="visible">
                    <xsl:value-of select="@OTVALOR"/>
                </xsl:element>
            </xsl:if>
                                
            <xsl:if test="@DSPROPIEDAD='CampoPadre'">
                <xsl:element name="campo-padre">
                    <xsl:value-of select="@OTVALOR"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSPROPIEDAD='Agrupador'">
                <xsl:element name="agrupador">
                    <xsl:value-of select="@OTVALOR"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSPROPIEDAD='OrdenAgrupacion'">
                <xsl:element name="orden-agrupacion">
                    <xsl:value-of select="@OTVALOR"/>
                </xsl:element>
            </xsl:if>
                                
                                
                                
        </xsl:for-each-group>
        
        
    </xsl:template> 
    
    
    
    
    
    <!--xsl:template name="combo-item">
                                 
        <control-item  type="java:com.aon.catweb.configurador.pantallas.model.controls.ComboControl">
                            
                            <xsl:element name="xtype">combo</xsl:element>
                            <xsl:element name="displayField">label</xsl:element>
                            <xsl:element name="valueField">value</xsl:element>
                            
                                    
                            <xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
                                
                                <xsl:if test="@DSPROPIEDAD='NombreTecnico'">
                                    <xsl:element name="name">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                <xsl:if test="@DSPROPIEDAD='ListaValores'">
                                    <xsl:element name="store">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                
                                
                            </xsl:for-each-group>
        </control-item>  
        
    </xsl:template-->
    
    <!--xsl:template name="textfield-item">
        <control-item type="java:com.aon.catweb.configurador.pantallas.model.controls.TextFieldControl">
                            
                            <xsl:element name="xtype">textfield</xsl:element>
                            
                            <xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
                                
                                <xsl:if test="@DSPROPIEDAD='NombreTecnico'">
                                    <xsl:element name="name">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                <xsl:if test="@DSPROPIEDAD='LongitudMinima'">
                                    <xsl:element name="minLength">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                <xsl:if test="@DSPROPIEDAD='LongitudMaxima'">
                                    <xsl:element name="maxLength">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                                <xsl:if test="@DSPROPIEDAD='Etiqueta'">
                                    <xsl:element name="fieldLabel">
                                        <xsl:value-of select="@OTVALOR"/>
                                    </xsl:element>
                                </xsl:if>
                                
                            </xsl:for-each-group>

        </control-item>
    </xsl:template-->
    
    <!-- ELEMENTOS DE TIPO NUMERICO -->
    <!--xsl:template name="numberfield-element">
        <control xsi:type="java:com.biosnet.ice.ext.elements.form.NumberFieldControl">
            <xsl:element name="xtype">combo</xsl:element>
            
        </control>
    </xsl:template-->
    
    <!-- BOTONES -->
    <!--xsl:template name="button-element">
        <control xsi:type="java:com.biosnet.ice.ext.elements.form.ComboControl">
            <xsl:element name="xtype">button</xsl:element>            
        </control>
    </xsl:template-->
    
    
</xsl:stylesheet>
