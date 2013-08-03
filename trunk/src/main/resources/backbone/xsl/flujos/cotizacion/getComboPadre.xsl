<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" 
                        xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    
    <xsl:template match="/">
        <obtener-padre xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </obtener-padre>
    </xsl:template>
  
     <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.configurador.pantallas.model.components.ComboClearOnSelectVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

				<xsl:if test="@CDATRIBU">
					<xsl:element name="cdatribu">
						<xsl:value-of select="@CDATRIBU"/>
					</xsl:element>
				</xsl:if>

				<xsl:if test="@OTTABVAL">
					<xsl:element name="ottabval">
						<xsl:value-of select="@OTTABVAL"/>
					</xsl:element>
				</xsl:if>
			
          </item-list>
        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>