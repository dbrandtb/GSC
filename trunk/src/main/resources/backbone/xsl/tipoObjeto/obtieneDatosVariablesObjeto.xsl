<?xml version="1.0" encoding="UTF-8"?>
<!-- edited with XML Spy v4.3 U (http://www.xmlspy.com) by a (a) -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates select="result/storedProcedure/outparam/rows "/>
		</array-list>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="row">
		<dato-variable-objeto-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.tipoObjeto.model.DatoVariableObjetoVO">
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
			<xsl:element name="codigo-tabla">
				<xsl:value-of select="@OTTABVAL"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTABLA">
			<xsl:element name="descripcion-tabla">
				<xsl:value-of select="@DSTABLA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWFORMAT">
			<xsl:element name="codigo-formato">
				<xsl:value-of select="@SWFORMAT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMLMAX">
			<xsl:element name="maximo">
				<xsl:value-of select="@NMLMAX"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMLMIN">
			<xsl:element name="minimo">
				<xsl:value-of select="@NMLMIN"/>
			</xsl:element>
		</xsl:if>
		 <xsl:if test="@SWPRODUC">
			<xsl:element name="emision">
				<xsl:value-of select="@SWPRODUC"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWSUPLEM">
			<xsl:element name="endoso">
				<xsl:value-of select="@SWSUPLEM"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWACTUAL">
			<xsl:element name="retarificacion">
				<xsl:value-of select="@SWACTUAL"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTIPOBJ">
			<xsl:element name="codigo-objeto">
				<xsl:value-of select="@CDTIPOBJ"/>
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
		<xsl:if test="@SWCOTIZA">
			<xsl:element name="aparece-cotizador">
				<xsl:value-of select="@SWCOTIZA" />
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
		</dato-variable-objeto-vO>
	</xsl:template>
</xsl:stylesheet>

