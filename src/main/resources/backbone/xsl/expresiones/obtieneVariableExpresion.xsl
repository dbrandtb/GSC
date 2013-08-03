<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" >
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each-group select="row"  group-by="@CDVARIAB">
            <variable-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.expresiones.model.VariableVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <!-- La posicion uno siempre tendra que ser la clave -->
                <xsl:if test="@CDEXPRES">
                    <xsl:element name="codigo-expresion">
                        <xsl:value-of select="@CDEXPRES"/>
                    </xsl:element>
                </xsl:if>
                <!-- La posicion dos siempre tendra que ser la descripcion -->
                <xsl:if test="@CDVARIAB">
                    <xsl:element name="codigo-variable">
                        <xsl:value-of select="@CDVARIAB"/>
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@OTTABLA">
                    <xsl:element name="tabla">
                        <xsl:value-of select="@OTTABLA"/>
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSTABLA">
                    <xsl:element name="descripcion-tabla">
                        <xsl:value-of select="@DSTABLA"/>
                    </xsl:element>
                </xsl:if>
            
				<xsl:if test="@OTSELECT">
                    <xsl:element name="descripcion-columna">
                        <xsl:value-of select="@OTSELECT"/>
                    </xsl:element>
                </xsl:if>
			<!-- SWFORMAT es la columna que se acaba de agregar sin saber en realidad el nombre-->
				<xsl:if test="@SWFORMAT">
                    <xsl:element name="switch-formato">
                        <xsl:value-of select="@SWFORMAT"/>
                    </xsl:element>
                </xsl:if>
			
				<xsl:for-each-group select="current-group()" group-by="@CDEXPRES_KEY">
						<claves xsi:type="java:mx.com.aon.catweb.configuracion.producto.expresiones.model.ClaveVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
							
							<xsl:if test="@CDEXPRES_KEY">
            			        <xsl:element name="codigo-expresion-key">
			                        <xsl:value-of select="@CDEXPRES_KEY"/>
            			        </xsl:element>
				            </xsl:if> 
				            
				            <xsl:if test="@DSCLAVE">
			                    <xsl:element name="clave">
            			            <xsl:value-of select="@DSCLAVE"/>
			                    </xsl:element>
            			    </xsl:if>
            			    
			                <xsl:if test="@OTSECUEN">
            			        <xsl:element name="codigo-secuencia">
			                        <xsl:value-of select="@OTSECUEN"/>
            			        </xsl:element>
				            </xsl:if>
	                            
    			            <xsl:if test="@OTEXPRES">
                			    <xsl:element name="expresion">
			                        <xsl:value-of select="@OTEXPRES"/>
            			        </xsl:element>
			                </xsl:if>
                
			                <xsl:if test="@SWRECALC">
            			        <xsl:element name="recalcular">
                        			<xsl:value-of select="@SWRECALC"/>
			                    </xsl:element>
            			    </xsl:if>
					
            		</claves>
				</xsl:for-each-group>
            </variable-vO>
        </xsl:for-each-group>
    </xsl:template>


</xsl:stylesheet>

