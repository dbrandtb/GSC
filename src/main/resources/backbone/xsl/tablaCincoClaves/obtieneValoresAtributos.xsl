<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="/">
		<descripcion-cinco-claves-vO
			xsi:type="java:mx.com.aon.catweb.configuracion.producto.tablaCincoClaves.model.DescripcionVeinticincoAtributosVO"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates
				select="result/storedProcedure/outparam/rows" />
		</descripcion-cinco-claves-vO>
	</xsl:template>
	<xsl:template match="row">
		<xsl:if test="@OTVALOR01">
			<xsl:element name="descripcion1">
				<xsl:value-of select="@OTVALOR01" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR02">
			<xsl:element name="descripcion2">
				<xsl:value-of select="@OTVALOR02" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR03">
			<xsl:element name="descripcion3">
				<xsl:value-of select="@OTVALOR03" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR04">
			<xsl:element name="descripcion4">
				<xsl:value-of select="@OTVALOR04" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR05">
			<xsl:element name="descripcion5">
				<xsl:value-of select="@OTVALOR05" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR06">
			<xsl:element name="descripcion6">
				<xsl:value-of select="@OTVALOR06" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR07">
			<xsl:element name="descripcion7">
				<xsl:value-of select="@OTVALOR07" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR08">
			<xsl:element name="descripcion8">
				<xsl:value-of select="@OTVALOR08" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR09">
			<xsl:element name="descripcion9">
				<xsl:value-of select="@OTVALOR09" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR10">
			<xsl:element name="descripcion10">
				<xsl:value-of select="@OTVALOR10" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR11">
			<xsl:element name="descripcion11">
				<xsl:value-of select="@OTVALOR11" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR12">
			<xsl:element name="descripcion12">
				<xsl:value-of select="@OTVALOR12" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR13">
			<xsl:element name="descripcion13">
				<xsl:value-of select="@OTVALOR13" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR14">
			<xsl:element name="descripcion14">
				<xsl:value-of select="@OTVALOR14" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR15">
			<xsl:element name="descripcion15">
				<xsl:value-of select="@OTVALOR15" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR16">
			<xsl:element name="descripcion16">
				<xsl:value-of select="@OTVALOR16" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR17">
			<xsl:element name="descripcion17">
				<xsl:value-of select="@OTVALOR17" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR18">
			<xsl:element name="descripcion18">
				<xsl:value-of select="@OTVALOR18" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR19">
			<xsl:element name="descripcion19">
				<xsl:value-of select="@OTVALOR19" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR20">
			<xsl:element name="descripcion20">
				<xsl:value-of select="@OTVALOR20" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR21">
			<xsl:element name="descripcion21">
				<xsl:value-of select="@OTVALOR21" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR22">
			<xsl:element name="descripcion22">
				<xsl:value-of select="@OTVALOR22" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR23">
			<xsl:element name="descripcion23">
				<xsl:value-of select="@OTVALOR023" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR24">
			<xsl:element name="descripcion24">
				<xsl:value-of select="@OTVALOR24" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR25">
			<xsl:element name="descripcion25">
				<xsl:value-of select="@OTVALOR25" />
			</xsl:element>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>

