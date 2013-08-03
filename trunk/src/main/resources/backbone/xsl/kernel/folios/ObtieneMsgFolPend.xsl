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
					<cursor xsi:type="mx.com.royalsun.vo.folios.MsgFolioPendienteVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
					 <xsl:if test="@NMOPERAC">
                    	<xsl:element name="nm-operac">
                        	<xsl:value-of select="@NMOPERAC" />
                    	</xsl:element>
                	</xsl:if>
					<xsl:if test="@ORDTRAB">
                    	<xsl:element name="ord-trab">
                        	<xsl:value-of select="@ORDTRAB" />
                    	</xsl:element>
                	</xsl:if>
					<xsl:if test="@CDAGENTE">
                    	<xsl:element name="cd-agente">
                        	<xsl:value-of select="@CDAGENTE" />
                    	</xsl:element>
                	</xsl:if>
					<xsl:if test="@TIPOENDO">
                    	<xsl:element name="tipo-endo">
                        	<xsl:value-of select="@TIPOENDO" />
                    	</xsl:element>
                	</xsl:if>
					<xsl:if test="@FECHAREC">
                    	<xsl:element name="fecha-rec">
                        	<xsl:value-of select="@FECHAREC" />
                    	</xsl:element>
                	</xsl:if>
					<xsl:if test="@CDUSUARI">
                    	<xsl:element name="cd-usuari">
                        	<xsl:value-of select="@CDUSUARI" />
                    	</xsl:element>
                	</xsl:if>
					<xsl:if test="@DESCRIPL">
                    	<xsl:element name="descripl">
                        	<xsl:value-of select="@DESCRIPL" />
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