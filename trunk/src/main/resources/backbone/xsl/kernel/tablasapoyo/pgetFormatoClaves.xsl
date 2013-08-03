<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
					<xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="error"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:element name="estado">
				<xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
			</xsl:element>
		</resultado-dao>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<cursor xsi:type="java:mx.com.ice.kernel.bo.BeanTablaApoyo" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
					<xsl:if test="@SWFORMA1">
						<xsl:element name="swforma1">
								<xsl:value-of select="@SWFORMA1"/>
						</xsl:element>				
					</xsl:if>                    
					<xsl:if test="@NMLMIN1">
							<xsl:element name="nmlmin1">
								<xsl:value-of select="@NMLMIN1"/>
							</xsl:element>					
					</xsl:if>
					<xsl:if test="@NMLMAX1">						
							<xsl:element name="nmlmax1">
								<xsl:value-of select="@NMLMAX1"/>
							</xsl:element>						
					</xsl:if>
					<xsl:if test="@SWFORMA2">						
							<xsl:element name="swforma2">
								<xsl:value-of select="@SWFORMA2"/>
							</xsl:element>						
					</xsl:if>
					<xsl:if test="@NMLMIN2">						
							<xsl:element name="nmlmin2">
								<xsl:value-of select="@NMLMIN2"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@NMLMAX2">						
							<xsl:element name="nmlmax2">
								<xsl:value-of select="@NMLMAX2"/>
							</xsl:element>
					</xsl:if>
					<xsl:if test="@SWFORMA3">						
							<xsl:element name="swforma3">
								<xsl:value-of select="@SWFORMA3"/>
							</xsl:element>
					</xsl:if>
					<xsl:if test="@NMLMIN3">						
							<xsl:element name="nmlmin3">
								<xsl:value-of select="@NMLMIN3"/>
							</xsl:element>
					</xsl:if>
					<xsl:if test="@NMLMAX3">						
							<xsl:element name="nmlmax3">
								<xsl:value-of select="@NMLMAX3"/>
							</xsl:element>
					</xsl:if>
					<xsl:if test="@SWFORMA4">						
							<xsl:element name="swforma4">
								<xsl:value-of select="@SWFORMA4"/>
							</xsl:element>
					</xsl:if>
					<xsl:if test="@NMLMIN4">						
							<xsl:element name="nmlmin4">
								<xsl:value-of select="@NMLMIN4"/>
							</xsl:element>
					</xsl:if>
					<xsl:if test="@NMLMAX4">						
							<xsl:element name="nmlmax4">
								<xsl:value-of select="@NMLMAX4"/>
							</xsl:element>
					</xsl:if>
					<xsl:if test="@SWFORMA5">						
							<xsl:element name="swforma5">
								<xsl:value-of select="@SWFORMA5"/>
							</xsl:element>
					</xsl:if>
					<xsl:if test="@NMLMIN5">						
							<xsl:element name="nmlmin5">
								<xsl:value-of select="@NMLMIN5"/>
							</xsl:element>
					</xsl:if>
					<xsl:if test="@NMLMAX5">						
							<xsl:element name="nmlmax5">
								<xsl:value-of select="@NMLMAX5"/>
							</xsl:element>						
					</xsl:if>
			</cursor>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="error">
		<xsl:element name="cd-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
		</xsl:element>
		<xsl:element name="ds-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
