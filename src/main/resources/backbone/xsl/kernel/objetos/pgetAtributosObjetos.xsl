<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
			<xsl:element name="estado">
				<xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
			</xsl:element>
		</resultado-dao>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
					<xsl:if test="@OTTABVAL">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								OTTABVAL
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTTABVAL"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSATRIBU">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								DSATRIBU
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSATRIBU"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWPRODUC">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWPRODUC
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWPRODUC"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDATRIBU">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDATRIBU
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDATRIBU"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWSUPLEM">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWSUPLEM
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWSUPLEM"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWFORMAT">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWFORMAT
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWFORMAT"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWOBLIGA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWOBLIGA
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWOBLIGA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								OTVALOR
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR"/>
							</value>
						</values>
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
