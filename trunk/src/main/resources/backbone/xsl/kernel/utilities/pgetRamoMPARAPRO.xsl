<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_dsramo
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_dsramo']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_swcoaseg
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_swcoaseg']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_swunilIf
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_swunilIf']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_swdivide
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_swdivide']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_swcolind
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_swcolind']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_swbefini
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_swbefini']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_swreaseg
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_swreaseg']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_cdtipagc
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_cdtipagc']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_cdroltom
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_cdroltom']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_nomimage
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_nomimage']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_tipimage
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_tipimage']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_nmagente
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_nmagente']/@value"/>
				</value>
			</values>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    po_swunlink
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='po_swunlink']/@value"/>
				</value>
			</values>
		</resultado-dao>
	</xsl:template>
</xsl:stylesheet>
