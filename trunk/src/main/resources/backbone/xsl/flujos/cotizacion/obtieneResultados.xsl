<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">

    <xsl:template match="/">
        <grid-vO xsi:type="java:mx.com.aon.configurador.pantallas.model.components.GridVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" mode="resultado" />

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" mode="record" />

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" mode="column" />
            
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" mode="items" />

        </grid-vO>
    </xsl:template>



    <xsl:template match="rows" mode="record">
        <xsl:for-each-group select="row" group-by="@CDPLAN">
            <record-list xsi:type="java:mx.com.aon.configurador.pantallas.model.components.RecordVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:element name="name">
                   <xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="type">string</xsl:element>

                <xsl:element name="mapping">
                    <xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

            </record-list>
            <record-list xsi:type="java:mx.com.aon.configurador.pantallas.model.components.RecordVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:element name="name">
                    CD<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="type">string</xsl:element>

                <xsl:element name="mapping">
                    CD<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

            </record-list>
            <record-list xsi:type="java:mx.com.aon.configurador.pantallas.model.components.RecordVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:element name="name">
                    DS<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="type">string</xsl:element>

                <xsl:element name="mapping">
                    DS<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

            </record-list>
			
			<record-list xsi:type="java:mx.com.aon.configurador.pantallas.model.components.RecordVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:element name="name">
                    NM<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="type">string</xsl:element>

                <xsl:element name="mapping">
                    NM<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

            </record-list>

        </xsl:for-each-group>

    </xsl:template>

    <xsl:template match="rows" mode="column">
        <xsl:for-each-group select="row" group-by="@CDPLAN">

            <column-list xsi:type="java:mx.com.aon.configurador.pantallas.model.components.ColumnGridVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:element name="header">
                    <xsl:value-of select="@DSPLAN" />
                </xsl:element>

                <xsl:element name="data-index">
                    <xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="width">100</xsl:element>

                <xsl:element name="sortable">false</xsl:element>

                <xsl:element name="id">
                    <xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="hidden">false</xsl:element>
            </column-list>

            <column-list xsi:type="java:mx.com.aon.configurador.pantallas.model.components.ColumnGridVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:element name="header">
                    CD<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="data-index">
                    CD<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="width">100</xsl:element>

                <xsl:element name="sortable">false</xsl:element>

                <xsl:element name="id">
                    CD<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="hidden">true</xsl:element>
            </column-list>

            <column-list xsi:type="java:mx.com.aon.configurador.pantallas.model.components.ColumnGridVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:element name="header">
                   DS<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="data-index">
                    DS<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="width">100</xsl:element>

                <xsl:element name="sortable">false</xsl:element>

                <xsl:element name="id">
                    DS<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="hidden">true</xsl:element>
            </column-list>
            
            <column-list xsi:type="java:mx.com.aon.configurador.pantallas.model.components.ColumnGridVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:element name="header">
                    NM<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="data-index">
                    NM<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="width">100</xsl:element>

                <xsl:element name="sortable">false</xsl:element>

                <xsl:element name="id">
                    NM<xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>

                <xsl:element name="hidden">true</xsl:element>
            </column-list>
        </xsl:for-each-group>
    </xsl:template>


     <xsl:template match="rows" mode="items">
    
     
     <xsl:for-each-group select="row" group-by="@CDPLAN">
            <item-list-plan xsi:type="java:mx.com.aon.configurador.pantallas.model.components.ItemVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:element name="clave">
                   <xsl:value-of select="@CDPLAN" />
                </xsl:element>
                
                <xsl:element name="descripcion">
                   <xsl:call-template name="strSplit">
                        <xsl:with-param name="string" select="@DSPLAN" />
                        <xsl:with-param name="pattern" select="' '" />
                    </xsl:call-template>
                </xsl:element>
           </item-list-plan>
       </xsl:for-each-group>
     
        <xsl:for-each-group select="row" group-by="@CDCIAASEG">
            <item-list-aseguradora xsi:type="java:mx.com.aon.configurador.pantallas.model.components.ItemVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:element name="clave">
                   <xsl:value-of select="@CDCIAASEG" />
                </xsl:element>
                
                <xsl:element name="descripcion">
                   <xsl:value-of select="@DSUNIECO" />
                </xsl:element>
           </item-list-aseguradora>
       </xsl:for-each-group>

    <xsl:for-each-group select="row" group-by="@CDPERPAG">
            <item-list-pago xsi:type="java:mx.com.aon.configurador.pantallas.model.components.ItemVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:element name="clave">
                   <xsl:value-of select="@CDPERPAG" />
                </xsl:element>
                
                <xsl:element name="descripcion">
                   <xsl:value-of select="@DSPERPAG" />
                </xsl:element>
           </item-list-pago>
       </xsl:for-each-group>


    </xsl:template>


    <xsl:template match="rows" mode="resultado">
    
<xsl:for-each select="row">
    <result-list xsi:type="java:mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
     
                <xsl:if test="@CDIDENTIFICA"> 
                    <xsl:element name="cd-identifica">
                    <xsl:value-of select="@CDIDENTIFICA" />
                    </xsl:element>
                </xsl:if>
            
                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cd-unieco">
                        <xsl:value-of select="@CDUNIECO" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSUNIECO">
                    <xsl:element name="ds-unieco">
                        <xsl:value-of select="@DSUNIECO" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@CDRAMO">
                    <xsl:element name="cd-ramo">
                        <xsl:value-of select="@CDRAMO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@ESTADO">
                    <xsl:element name="estado">
                        <xsl:value-of select="@ESTADO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NMPOLIZA">
                    <xsl:element name="nm-poliza">
                        <xsl:value-of select="@NMPOLIZA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NMSUPLEM">
                    <xsl:element name="nm-suplem">
                        <xsl:value-of select="@NMSUPLEM" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@STATUS">
                    <xsl:element name="status">
                        <xsl:value-of select="@STATUS" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDPLAN">
                    <xsl:element name="cd-plan">
                        <xsl:value-of select="@CDPLAN" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSPLAN">
                    <xsl:element name="ds-plan">
                        <xsl:value-of select="@DSPLAN" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@MNPRIMA">
                    <xsl:element name="mn-prima">
                        <xsl:value-of select='format-number(@MNPRIMA, "#,##0.00")' />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDCIAASEG">
                    <xsl:element name="cd-ciaaseg">
                        <xsl:value-of select="@CDCIAASEG" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDPERPAG">
                    <xsl:element name="cd-perpag">
                        <xsl:value-of select="@CDPERPAG" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSPERPAG">
                    <xsl:element name="ds-perpag">
                        <xsl:value-of select="@DSPERPAG" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDTIPSIT">
                    <xsl:element name="cd-tipsit">
                        <xsl:value-of select="@CDTIPSIT" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSTIPSIT">
                    <xsl:element name="ds-tipsit">
                        <xsl:value-of select="@DSTIPSIT" />
                    </xsl:element>
                </xsl:if>
              
                <xsl:if test="@FEEMISIO">
                    <xsl:element name="fe-emisio">
                        <xsl:value-of select="@FEEMISIO" />
                    </xsl:element>
                </xsl:if>
                	
                <xsl:if test="@FEVENCIM">
                    <xsl:element name="fe-vencim">
                        <xsl:value-of select="@FEVENCIM" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@NMSITUAC">
                    <xsl:element name="numero-situacion">
                        <xsl:value-of select="@NMSITUAC" />
                    </xsl:element>
                </xsl:if>
                
          </result-list>

      </xsl:for-each>
       
    </xsl:template>

    <xsl:template name="strSplit">
        <xsl:param name="string" />
        <xsl:param name="pattern" />

        <xsl:choose>
            <xsl:when test="contains($string, $pattern)">
                <xsl:call-template name="strSplit">
                    <xsl:with-param name="string" select="concat(substring-before($string, $pattern), substring-after($string, $pattern))" />
                    <xsl:with-param name="pattern" select="$pattern" />
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$string" />

            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
