<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg-text">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
        	<xsl:if test="@OTTABVAL != ''">
	            <item-list xsi:type="java:com.biosnet.ice.ext.elements.form.ComboControl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	            	<xsl:element name="xtype">combo</xsl:element>      
                    <xsl:element name="width">200</xsl:element>
                    <xsl:element name="empty-text">Seleccione...</xsl:element>                   
                    <xsl:element name="display-field">label</xsl:element>      
                    <xsl:element name="value-field">value</xsl:element> 
                    <xsl:element name="mode">local</xsl:element>                   
                    <xsl:element name="trigger-action">all</xsl:element>      
                    <xsl:element name="type-ahead">true</xsl:element>
                    <xsl:element name="editable">true</xsl:element>
                    
                    <xsl:if test="@CDFORMATOORDEN">
                        <xsl:element name="id">
                            <xsl:value-of select="@CDFORMATOORDEN"/>_<xsl:value-of select="@CDSECCION"/>_<xsl:value-of select="@CDATRIBU"/>
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="@CDFORMATOORDEN">
                        <xsl:element name="name">
                            <xsl:value-of select="@CDFORMATOORDEN"/>_<xsl:value-of select="@CDSECCION"/>_<xsl:value-of select="@CDATRIBU"/>
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="@CDCAMPO">
                        <xsl:element name="hidden-name">
                            <xsl:value-of select="@CDCAMPO"/>
                        </xsl:element>
                    </xsl:if>
                    <!--
                    <xsl:if test="@SWOBLIGA='S'">
                        <xsl:element name="force-selection">true</xsl:element>
                    </xsl:if>
                    <xsl:if test="@SWOBLIGA='N'">
                        <xsl:element name="force-selection">false</xsl:element>
                    </xsl:if>
                    -->
                    <xsl:if test="@DSATRIBU">
                        <xsl:element name="field-label">
                            <xsl:value-of select="@DSATRIBU"/>
                        </xsl:element>
                    </xsl:if> 
                    <xsl:if test="@OTTABVAL">
                        <xsl:element name="backup-table">
                            <xsl:value-of select="@OTTABVAL"/>
                        </xsl:element>
                    </xsl:if> 
                    <xsl:if test="@CDAGRUPA">
                        <xsl:element name="grouping">
                            <xsl:value-of select="@CDAGRUPA"/>
                        </xsl:element>
                    </xsl:if> 
                    <xsl:if test="@CDATRIBU">
                        <xsl:element name="grouping-id">
                            <xsl:value-of select="@CDATRIBU"/>
                        </xsl:element>
                    </xsl:if> 
                    <!--
                    <xsl:if test="@MODIFICABLE">
                        <xsl:if test="@MODIFICABLE='S'">
                            <xsl:element name="read-only">false</xsl:element>
                        </xsl:if>
                        <xsl:if test="@MODIFICABLE='N'">
                            <xsl:element name="read-only">true</xsl:element>
                        </xsl:if>
                    </xsl:if>
                    <xsl:if test="@VISIBLE">
                        <xsl:if test="@VISIBLE='S'">
                            <xsl:element name="hidden">false</xsl:element>
                        </xsl:if>
                        <xsl:if test="@VISIBLE='N'">
                            <xsl:element name="hidden">true</xsl:element>
                        </xsl:if>
                    </xsl:if>
                    -->
	            </item-list>
	        </xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>




