<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" >
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
    	 <xsl:for-each-group select="row"  group-by="@CDELEMENTO">
            <user-vO xsi:type="java:mx.com.aon.portal.model.UserVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">            
        		<xsl:if test="@CDUSUARIO">
                	<xsl:element name="user">
                    	<xsl:value-of select="@CDUSUARIO"/>
		            </xsl:element>
        		</xsl:if>
        		<xsl:if test="@CDPERSON">
                	<xsl:element name="codigo-persona">
                    	<xsl:value-of select="@CDPERSON"/>
		            </xsl:element>
        		</xsl:if>
        		<xsl:if test="@DSUSUARI">
                	<xsl:element name="name">
                    	<xsl:value-of select="@DSUSUARI"/>
		            </xsl:element>
        		</xsl:if>
		        	<xsl:for-each-group select="current-group()"  group-by="@CDELEMENTO">
		            	<empresa xsi:type="java:mx.com.aon.portal.model.EmpresaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		              		<xsl:for-each-group select="current-group()"  group-by="@CDELEMENTO">
        		        		<xsl:if test="@CDELEMENTO">
                		    		<xsl:element name="elemento-id">
                        				<xsl:value-of select="@CDELEMENTO"/>
		                    		</xsl:element>
        		        		</xsl:if>
                				<xsl:if test="@DSELEMEN">
                    				<xsl:element name="nombre">
		                        		<xsl:value-of select="@DSELEMEN"/>
        		            		</xsl:element>
                				</xsl:if>
                			</xsl:for-each-group>
            			</empresa>
        			</xsl:for-each-group>
        			<xsl:for-each-group select="current-group()" group-by="@CDSISROL">
						<roles xsi:type="java:mx.com.aon.portal.model.RolVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">    		     
							<xsl:for-each-group select="current-group()" group-by="@CDSISROL">
								<objeto xsi:type="java:mx.com.aon.portal.model.BaseObjectVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">						
			    		            <xsl:if test="@CDSISROL">
            					        <xsl:element name="value">
			                    			<xsl:value-of select="@CDSISROL"/>
		            			        </xsl:element>
						            </xsl:if>	                            
    					            <xsl:if test="@DSSISROL">
                					    <xsl:element name="label">
			                    		    <xsl:value-of select="@DSSISROL"/>
		            			        </xsl:element>
					                </xsl:if>	
            					</objeto>
							</xsl:for-each-group>
        		    	</roles>
        		</xsl:for-each-group>
    		</user-vO>
        </xsl:for-each-group>        		
    </xsl:template>


</xsl:stylesheet>

