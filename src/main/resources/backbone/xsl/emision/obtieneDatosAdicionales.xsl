<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" 
                        xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <xsl:apply-templates select="." />
        </xsl:for-each>
    </xsl:template>
    <xsl:template match="row">
        <rol-vO xsi:type="java:mx.com.aon.procesos.emision.model.DatosAdicionalesVO">
		    <xsl:if test="@DSATRIBU">
    			<xsl:element name="etiqueta">
    				<xsl:value-of select="@DSATRIBU"/>
    			</xsl:element>
    		</xsl:if>
            <xsl:if test="@OTVALOR">
    			<xsl:element name="valor">
    				<xsl:value-of select="@OTVALOR"/>
    			</xsl:element>
    		</xsl:if>
            <xsl:if test="@DSVALOR">
                <xsl:element name="descripcion">
                    <xsl:value-of select="@DSVALOR"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSNOMBRE">
                <xsl:element name="nombre-componente">
                    <xsl:value-of select="@DSNOMBRE"/>
                </xsl:element>
            </xsl:if>
        </rol-vO>
    </xsl:template>
</xsl:stylesheet>        