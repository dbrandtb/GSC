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
        <rol-atributo-variable-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.rol.model.RolAtributoVariableVO">
        <xsl:if test="@CDATRIBU">
			<xsl:element name="codigo-atributo-variable">
				<xsl:value-of select="@CDATRIBU"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSATRIBU">
			<xsl:element name="descripcion-atributo-variable">
				<xsl:value-of select="@DSATRIBU"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWOBLIGA">
			<xsl:element name="switch-obligatorio">
				<xsl:value-of select="@SWOBLIGA"/>
			</xsl:element>
		</xsl:if>
		 <xsl:if test="@OTTABVAL">
			<xsl:element name="ottabval">
				<xsl:value-of select="@OTTABVAL"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTABLA">
			<xsl:element name="descripcion-lista-de-valores">
				<xsl:value-of select="@DSTABLA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWCOTIZA">
			<xsl:element name="aparece-cotizador">
				<xsl:value-of select="@SWCOTIZA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWCOTUPD">
			<xsl:element name="modifica-cotizador">
				<xsl:value-of select="@SWCOTUPD" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWDATCOM">
			<xsl:element name="dato-complementario">
				<xsl:value-of select="@SWDATCOM"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWCOMOBL">
			<xsl:element name="obligatorio-complementario">
				<xsl:value-of select="@SWCOMOBL"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWCOMUPD">
			<xsl:element name="modificable-complementario">
				<xsl:value-of select="@SWCOMUPD"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWENDOSO">
			<xsl:element name="aparece-endoso">
				<xsl:value-of select="@SWENDOSO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWENDOBL">
			<xsl:element name="obligatorio-endoso">
				<xsl:value-of select="@SWENDOBL"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWENDUPD">
			<xsl:element name="modifica-endoso">
				<xsl:value-of select="@SWENDUPD"/>
			</xsl:element>
		</xsl:if>				
		<xsl:if test="@CDATRIBU_PADRE">
			<xsl:element name="codigo-padre">
				<xsl:value-of select="@CDATRIBU_PADRE"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSATRIBU_PADRE">
			<xsl:element name="descripcion-padre">
				<xsl:value-of select="@DSATRIBU_PADRE"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMORDEN">
			<xsl:element name="orden">
				<xsl:value-of select="@NMORDEN"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMAGRUPA">
			<xsl:element name="agrupador">
				<xsl:value-of select="@NMAGRUPA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDCONDICVIS">
			<xsl:element name="codigo-condicion">
				<xsl:value-of select="@CDCONDICVIS"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSCONDICVIS">
			<xsl:element name="descripcion-condicion">
				<xsl:value-of select="@DSCONDICVIS"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWTARIFI">
			<xsl:element name="retarificacion">
				<xsl:value-of select="@SWTARIFI" />
			</xsl:element>
		</xsl:if>
		
        </rol-atributo-variable-vO>
    </xsl:template>
</xsl:stylesheet>  