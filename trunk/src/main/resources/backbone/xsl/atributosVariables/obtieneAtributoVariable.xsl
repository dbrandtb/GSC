<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="/">
		<atributos-variables-vO
			xsi:type="java:mx.com.aon.catweb.configuracion.producto.atributosVariables.model.AtributosVariablesVO"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates
				select="result/storedProcedure/outparam/rows" />
		</atributos-variables-vO>
	</xsl:template>
	<xsl:template match="row">
		<xsl:if test="@CDRAMO">
			<xsl:element name="cd-ramo">
				<xsl:value-of select="@CDRAMO" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTIPSIT">
			<xsl:element name="codigo-situacion">
				<xsl:value-of select="@CDTIPSIT" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDGARANT">
			<xsl:element name="codigo-garantia">
				<xsl:value-of select="@CDGARANT" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDATRIBU">
			<xsl:element name="codigo-atributo">
				<xsl:value-of select="@CDATRIBU" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSATRIBU">
			<xsl:element name="descripcion">
				<xsl:value-of select="@DSATRIBU" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWFORMAT">
			<xsl:element name="cd-formato">
				<xsl:value-of select="@SWFORMAT" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMLMIN">
			<xsl:element name="minimo">
				<xsl:value-of select="@NMLMIN" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMLMAX">
			<xsl:element name="maximo">
				<xsl:value-of select="@NMLMAX" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWOBLIGA">
			<xsl:element name="obligatorio">
				<xsl:value-of select="@SWOBLIGA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWPRODUC">
			<xsl:element name="emision">
				<xsl:value-of select="@SWPRODUC" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWSUPLEM">
			<xsl:element name="endosos">
				<xsl:value-of select="@SWSUPLEM" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWTARIFI">
			<xsl:element name="retarificacion">
				<xsl:value-of select="@SWTARIFI" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWPRESEN">
			<xsl:element name="cotizador">
				<xsl:value-of select="@SWPRESEN" />
			</xsl:element>
		</xsl:if>		
		<xsl:if test="@CDEXPRES">
			<xsl:element name="codigo-expresion">
				<xsl:value-of select="@CDEXPRES" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTTABVAL">
			<xsl:element name="cd-tabla">
				<xsl:value-of select="@OTTABVAL" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTABLA">
			<xsl:element name="ds-tabla">
				<xsl:value-of select="@DSTABLA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDATRIBU_PADRE">
			<xsl:element name="codigo-padre">
				<xsl:value-of select="@CDATRIBU_PADRE" />
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
		<xsl:if test="@DSATRIBU_PADRE">
			<xsl:element name="ds-atributo-padre">
				<xsl:value-of select="@DSATRIBU_PADRE"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSCONDICVIS">
			<xsl:element name="ds-condicion">
				<xsl:value-of select="@DSCONDICVIS"/>
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
		<xsl:if test="@SWCOTIZA">
			<xsl:element name="cotizador">
				<xsl:value-of select="@SWCOTIZA" />
			</xsl:element>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
