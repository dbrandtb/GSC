<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.catbo.model.MatrizAsignacionVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        
		        
		        <xsl:if test="@CDMATRIZ">
		            <xsl:element name="cdmatriz">
		                <xsl:value-of select="@CDMATRIZ" />
		            </xsl:element>
		        </xsl:if>		 
		        
		        <xsl:if test="@CDPROCESO">
		            <xsl:element name="cdproceso">
		                <xsl:value-of select="@CDPROCESO" />
		            </xsl:element>
		        </xsl:if>		        
		        
		        <xsl:if test="@DSPROCESO">
		            <xsl:element name="dsproceso">
		                <xsl:value-of select="@DSPROCESO" />
		            </xsl:element>
		        </xsl:if>
		        
		         <xsl:if test="@CDFORMATOORDEN">
                    <xsl:element name="cdformatoorden">
                        <xsl:value-of select="@CDFORMATOORDEN" />
                    </xsl:element>
                </xsl:if>
		        
                <xsl:if test="@DSFORMATOORDEN">
                    <xsl:element name="dsformatoorden">
                        <xsl:value-of select="@DSFORMATOORDEN" />
                    </xsl:element>
                      </xsl:if>    
                          
                 <xsl:if test="@CDELEMEN">
                    <xsl:element name="cdelemento">
                        <xsl:value-of select="@CDELEMEN" />
                    </xsl:element>
                    </xsl:if>
                    
                <xsl:if test="@DSELEMEN">
                    <xsl:element name="dselemen">
                        <xsl:value-of select="@DSELEMEN" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cdunieco">
                        <xsl:value-of select="@CDUNIECO" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@DSUNIECO">
                    <xsl:element name="dsunieco">
                        <xsl:value-of select="@DSUNIECO" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@CDRAMO">
                    <xsl:element name="cdramo">
                        <xsl:value-of select="@CDRAMO" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSRAMO">
                    <xsl:element name="dsramo">
                        <xsl:value-of select="@DSRAMO" />
                    </xsl:element>
                </xsl:if>
                
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>


  