<?xml version="1.0" encoding="UTF-8"?>
<!-- edited with XML Spy v4.3 U (http://www.xmlspy.com) by a (a) -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <descripcion-cinco-claves-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.tablaCincoClaves.model.DescripcionCincoClavesVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </descripcion-cinco-claves-vO>
    </xsl:template>
	<xsl:template match="row">
			<xsl:if test="@*[position()=1]">
				<xsl:element name="descripcion-clave1">
					<xsl:value-of select="@*[position()=1]"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@*[position()=2]">
				<xsl:element name="descripcion-clave2">
					<xsl:value-of select="@*[position()=2]"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@*[position()=3]">
				<xsl:element name="descripcion-clave3">
					<xsl:value-of select="@*[position()=3]"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@*[position()=4]">
				<xsl:element name="descripcion-clave4">
					<xsl:value-of select="@*[position()=4]"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@*[position()=5]">
				<xsl:element name="descripcion-clave5">
					<xsl:value-of select="@*[position()=5]"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@FEDESDE">
    			<xsl:element name="fecha-desde">
     				<xsl:value-of select="@FEDESDE"/>
   				</xsl:element>
   			</xsl:if>
   			<xsl:if test="@FEHASTA">
    			<xsl:element name="fecha-hasta">
     				<xsl:value-of select="@FEHASTA"/>
    			</xsl:element>
   			</xsl:if>
	
	</xsl:template>
</xsl:stylesheet>
