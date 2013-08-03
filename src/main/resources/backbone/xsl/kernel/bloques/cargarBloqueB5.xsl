<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<!--  <xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">-->
				
					<xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
				<!-- </xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="error"/>
				</xsl:otherwise>
			</xsl:choose> -->
			<xsl:element name="estado">
				<xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
			</xsl:element>
			<!-- 
			Esto es temporal hasta que acomoden bien el PL
			 -->
			<xsl:element name="cd-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
		</xsl:element>
		<xsl:element name="ds-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
		</xsl:element>
		</resultado-dao>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<cursor xsi:type="java:mx.com.ice.services.to.BloqueVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
					<xsl:if test="@CDUNIECO">
							<xsl:element name="cdunieco">
								<xsl:value-of select="@CDUNIECO"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@CDRAMO">
							<xsl:element name="cdramo">
								<xsl:value-of select="@CDRAMO"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@ESTADO">
							<xsl:element name="estado">
								<xsl:value-of select="@ESTADO"/>
							</xsl:element>
					</xsl:if>
					<xsl:if test="@NMPOLIZA">
							<xsl:element name="nmpoliza">
								<xsl:value-of select="@NMPOLIZA"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@NMSITUAC">
							<xsl:element name="nmsituac">
								<xsl:value-of select="@NMSITUAC"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@NMSUPLEM">
							<xsl:element name="nmsuplem">
								<xsl:value-of select="@NMSUPLEM"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@STATUS">
							<xsl:element name="status">
								<xsl:value-of select="@STATUS"/>
							</xsl:element>
					</xsl:if>
					<xsl:if test="@CDTIPSIT">
							<xsl:element name="cdtipsit">
								<xsl:value-of select="@CDTIPSIT"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@SWREDUCI">
							<xsl:element name="swreduci">
								<xsl:value-of select="@SWREDUCI"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@CDAGRUPA">
							<xsl:element name="cdagrupa">
								<xsl:value-of select="@CDAGRUPA"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@CDESTADO">
							<xsl:element name="cdestado">
								<xsl:value-of select="@CDESTADO"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@FEFECSIT">
							<xsl:element name="fefecsit">
								<xsl:value-of select="@FEFECSIT"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@FECHAREF">
							<xsl:element name="fecharef">
								<xsl:value-of select="@FECHAREF"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@INDPARBE">
							<xsl:element name="indparbe">
								<xsl:value-of select="@INDPARBE"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@FEINIPBS">
							<xsl:element name="feinipbs">
								<xsl:value-of select="@FEINIPBS"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@INTFINAN">
							<xsl:element name="intfinan">
								<xsl:value-of select="@INTFINAN"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@CDMOTANU">
							<xsl:element name="cdmotanu">
								<xsl:value-of select="@CDMOTANU"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@FEINISUS">
							<xsl:element name="feinisus">
								<xsl:value-of select="@FEINISUS"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@FEFINSUS">
							<xsl:element name="fefinsus">
								<xsl:value-of select="@FEFINSUS"/>
							</xsl:element>
						
					</xsl:if>
					<xsl:if test="@CDPLAN">
							<xsl:element name="cdplan">
								<xsl:value-of select="@CDPLAN"/>
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
