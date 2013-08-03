<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">

    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.flujos.cotizacion.model.MPoliObjVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                
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
 	   	
			    <xsl:if test="@CDTIPOBJ">
	  				<xsl:element name="cdtipobj">
		    			<xsl:value-of select="@CDTIPOBJ"/>
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

			    <xsl:if test="@NMOBJETO">
				   <xsl:element name="nmobjeto">
	    				<xsl:value-of select="@NMOBJETO"/>
				   </xsl:element>
 	   			</xsl:if>

				<xsl:if test="@DSOBJETO">
					<xsl:element name="dsobjeto">
	    				<xsl:value-of select="@DSOBJETO"/>
			   		</xsl:element>
				</xsl:if>  
		
				<xsl:if test="@PTOBJETO">
					<xsl:element name="ptobjeto">
			    		<xsl:value-of select="@PTOBJETO"/>
			   		</xsl:element>
				</xsl:if>
				
				<xsl:if test="@CDAGRUPA">
					<xsl:element name="cdagrupa">
	    				<xsl:value-of select="@CDAGRUPA"/>
			   		</xsl:element>
				</xsl:if>  
		
				<xsl:if test="@NMVALOR">
					<xsl:element name="nmvalor">
			    		<xsl:value-of select="@NMVALOR"/>
			   		</xsl:element>
				</xsl:if>
				
				<xsl:if test="@DSDESCRIPCION">
					<xsl:element name="dsdescripcion">
			    		<xsl:value-of select="@DSDESCRIPCION"/>
			   		</xsl:element>
				</xsl:if>
				
                
            </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>




	    
