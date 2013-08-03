<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="/">
		<master-vO xsi:type="java:mx.com.aon.configurador.pantallas.model.master.MasterVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
		</master-vO>		
	</xsl:template>
	
	<xsl:template match="rows">
		<xsl:for-each-group select="row" group-by="@CDBLOQUE"  >
			<seccion-list xsi:type="java:mx.com.aon.configurador.pantallas.model.master.SectionVo" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
					<xsl:if test="@DSBLOQUE">
						<xsl:element name="ds-bloque">
							<xsl:value-of select="@DSBLOQUE"/>
						</xsl:element>
					</xsl:if>
					
					<xsl:if test="@CDBLOQUE">
						<xsl:element name="cd-bloque">
							<xsl:value-of select="@CDBLOQUE"/>
						</xsl:element>
					</xsl:if>
					
					<xsl:for-each-group select="current-group()" group-by="@CDCAMPO">
						<field-list xsi:type="java:mx.com.aon.configurador.pantallas.model.master.FieldVo" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
							
							<xsl:if test="@CDCAMPO">
								<xsl:element name="cd-campo">
									<xsl:value-of select="@CDCAMPO"/>
								</xsl:element>
							</xsl:if>
					
							<xsl:if test="@DSCAMPO">
								<xsl:element name="ds-campo">
									<xsl:value-of select="@DSCAMPO"/>
								</xsl:element>
							</xsl:if>
							
						</field-list>
					</xsl:for-each-group>	
					
					
			</seccion-list>
		</xsl:for-each-group>
	</xsl:template>
		
</xsl:stylesheet>
