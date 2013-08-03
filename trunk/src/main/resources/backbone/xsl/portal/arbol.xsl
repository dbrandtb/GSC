<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" >
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
    	<xsl:for-each-group select="row"  group-by="@CDELEMENTO">
        	<rama-vO xsi:type="java:mx.com.aon.portal.model.RamaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">                    				        		       
				<xsl:if test="@DSELEMEN">
                	<xsl:element name="text">
		            	<xsl:value-of select="@DSELEMEN"/>
        		    </xsl:element>
                </xsl:if>
        		<xsl:if test="@CDELEMENTO">
                	<xsl:element name="codigo-objeto">
                		<xsl:value-of select="@CDELEMENTO"/>
		        	</xsl:element>
                	<xsl:element name="cd-elemento">
                		<xsl:value-of select="@CDELEMENTO"/>
		        	</xsl:element>
        		</xsl:if>
        		<xsl:if test="@CDUSUARIO">
                	<xsl:element name="nick">
		        		<xsl:value-of select="@CDUSUARIO"/>
        			</xsl:element>
               	</xsl:if>
        		<xsl:if test="@DSUSUARI">
                	<xsl:element name="name">
                		<xsl:value-of select="@DSUSUARI"/>
		        	</xsl:element>
        		</xsl:if>
        		<xsl:if test="@DSSISROL">
                	<xsl:element name="ds-rol">
		        		<xsl:value-of select="@DSSISROL"/>
        			</xsl:element>
                </xsl:if>
        		<xsl:if test="@CDSISROL">
                	<xsl:element name="clave-rol">
                		<xsl:value-of select="@CDSISROL"/>
		        	</xsl:element>
        		</xsl:if>
		        <xsl:for-each-group select="current-group()"  group-by="@CDSISROL">
		        	<children xsi:type="java:mx.com.aon.portal.model.RamaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                		<xsl:if test="@DSSISROL">
                			<xsl:element name="text">
		        				<xsl:value-of select="@DSSISROL"/>
        					</xsl:element>
                		</xsl:if>
        				<xsl:if test="@CDSISROL">
                			<xsl:element name="codigo-objeto">
                				<xsl:value-of select="@CDSISROL"/>
		        			</xsl:element>
        				</xsl:if>
        				<xsl:if test="@CDELEMENTO">
                			<xsl:element name="cd-elemento">
                				<xsl:value-of select="@CDELEMENTO"/>
		        			</xsl:element>
        				</xsl:if>
        			</children>	
                </xsl:for-each-group>              			        			        			
    		</rama-vO>
        </xsl:for-each-group>        		
    </xsl:template>
</xsl:stylesheet>

