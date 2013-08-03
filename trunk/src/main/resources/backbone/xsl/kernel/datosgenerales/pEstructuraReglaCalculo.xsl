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
			<cursor xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
					<xsl:if test="@DSCLAVE1">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								DSCLAVE1
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSCLAVE1"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWFORMA1">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWFORMA1
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWFORMA1"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMIN1">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMIN1
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMIN1"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMAX1">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMAX1
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMAX1"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSCLAVE2">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								DSCLAVE2
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSCLAVE2"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWFORMA2">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWFORMA2
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWFORMA2"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMIN2">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMIN2
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMIN2"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMAX2">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMAX2
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMAX2"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSCLAVE3">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								DSCLAVE3
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSCLAVE3"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWFORMA3">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWFORMA3
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWFORMA3"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMIN3">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMIN3
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMIN3"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMAX3">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMAX3
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMAX3"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSCLAVE4">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								DSCLAVE4
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSCLAVE4"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWFORMA4">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWFORMA4
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWFORMA4"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMIN4">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMIN4
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMIN4"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMAX4">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMAX4
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMAX4"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSCLAVE5">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								DSCLAVE5
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSCLAVE5"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWFORMA5">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWFORMA5
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWFORMA5"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMIN5">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMIN5
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMIN5"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMAX5">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMAX5
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMAX5"/>
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
					<xsl:if test="@NMLMIN">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMIN
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMIN"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMAX">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMAX
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMAX"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSTABLA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								DSTABLA
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSTABLA"/>
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
