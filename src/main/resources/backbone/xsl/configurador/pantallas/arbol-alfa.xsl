<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<master-vO xsi:type="java:com.biosnet.ice.ext.elements.model.JSONMasterVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates select="result/storedProcedure/outparam/rows" />
		</master-vO>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each-group select="row" group-by="@SWDATCOM">
			<xsl:if test="@SWDATCOM != 'S'">
				<xsl:for-each-group select="current-group()" group-by="@CDBLOQUE">
        
					<xsl:if test="@CDBLOQUE != '3'">
        
						<section-list xsi:type="java:com.biosnet.ice.ext.elements.model.RamaMaster" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
							<xsl:if test="@DSBLOQUE">
								<xsl:element name="text">
									<xsl:value-of select="@DSBLOQUE" />
								</xsl:element>
							</xsl:if>
							<xsl:if test="@CDBLOQUE">
								<xsl:element name="id">
									<xsl:value-of select="@CDBLOQUE" />
								</xsl:element>
							</xsl:if>                
                
							<xsl:element name="leaf">false</xsl:element>
                
                
							<xsl:for-each-group select="current-group()" group-by="@CDCAMPO">
								<children xsi:type="java:com.biosnet.ice.ext.elements.model.RamaMaster" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">                        
                        
									<xsl:element name="leaf">true</xsl:element>                        
                        
									<xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
										<xsl:if test="@DSPROPIEDAD='NombreTecnico'">                                        
											<xsl:element name="id">
												<xsl:value-of select="@OTVALOR" />
											</xsl:element>
										</xsl:if>
                                    
										<xsl:if test="@DSPROPIEDAD='Etiqueta'">
											<xsl:element name="text">
												<xsl:value-of select="@OTVALOR" />
											</xsl:element>
										</xsl:if>
                                    
										<xsl:if test="@DSPROPIEDAD='Agrupador'">
											<xsl:element name="agrupador">
												<xsl:value-of select="@OTVALOR" />
											</xsl:element>
										</xsl:if>                                    
                                    
										<xsl:if test="@DSPROPIEDAD='OrdenAgrupacion'">
											<xsl:element name="orden-agrupacion">
												<xsl:value-of select="@OTVALOR" />
											</xsl:element>
										</xsl:if>
                                    
									</xsl:for-each-group>
                        
                        
									<xsl:if test="@OTVALOR = '2'">                            
										<ext-control xsi:type="java:com.biosnet.ice.ext.elements.form.TextFieldControl">
											<xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
												<xsl:if test="@DSPROPIEDAD='TipoDato'">
													<xsl:if test="@OTVALOR='A'">
														<xsl:element name="xtype">textfield</xsl:element>
													</xsl:if>
													<xsl:if test="@OTVALOR='N'">
														<xsl:element name="xtype">numberfield</xsl:element>
													</xsl:if>
													<xsl:if test="@OTVALOR='D'">
														<xsl:element name="xtype">datefield</xsl:element>
													</xsl:if>
													<xsl:if test="@OTVALOR='F'">
														<xsl:element name="xtype">datefield</xsl:element>
													</xsl:if>
												</xsl:if>
											</xsl:for-each-group>
											<xsl:element name="hidde-parent">true</xsl:element>
											<xsl:element name="width">200</xsl:element>
                            
                            
											<xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
												<xsl:if test="@DSPROPIEDAD='NombreTecnico'">
													<xsl:element name="name">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
													<xsl:element name="id">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='LongitudMinima'">
													<xsl:if test="@OTVALOR>'0'">
														<xsl:element name="min-length">
															<xsl:value-of select="@OTVALOR" />
														</xsl:element>
														<xsl:element name="min-length-text">El mínimo de caracteres es 
															<xsl:value-of select="@OTVALOR" />
														</xsl:element>
													</xsl:if>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='LongitudMaxima'">
													<xsl:if test="@OTVALOR>'0'">
														<xsl:element name="max-length">
															<xsl:value-of select="@OTVALOR" />
														</xsl:element>
														<xsl:element name="max-length-text">El máximo de caracteres es 
															<xsl:value-of select="@OTVALOR" />
														</xsl:element>
													</xsl:if>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='UsarcomoFiltro'">
													<xsl:element name="usarcomo-filtro">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='Etiqueta'">
													<xsl:element name="field-label">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='Obligatorio'">
													<xsl:if test="@OTVALOR='S'">
														<xsl:element name="allow-blank">false</xsl:element>
													</xsl:if>
													<xsl:if test="@OTVALOR='N'">
														<xsl:element name="allow-blank">true</xsl:element>
													</xsl:if>
												</xsl:if>
											</xsl:for-each-group>
                                
										</ext-control>
									</xsl:if>
                        
                        
									<xsl:if test="@OTVALOR = '5'">                        
										<ext-control xsi:type="java:com.biosnet.ice.ext.elements.form.ComboMaster">
                            
											<xsl:element name="xtype">combo</xsl:element>
											<xsl:element name="display-field">label</xsl:element>
											<xsl:element name="value-field">value</xsl:element>
											<xsl:element name="empty-text">Seleccione...</xsl:element>
											<xsl:element name="trigger-action">all</xsl:element>
											<xsl:element name="type-ahead">true</xsl:element>
											<xsl:element name="mode">local</xsl:element>
											<xsl:element name="force-selection">true</xsl:element>
											<xsl:element name="list-width">200</xsl:element>
											<xsl:element name="width">200</xsl:element>
											<xsl:element name="select-on-focus">true</xsl:element>
                                
											<xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">                                    
												<xsl:if test="@DSPROPIEDAD='NombreTecnico'">
													<xsl:element name="name">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
													<xsl:element name="id">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
													<xsl:element name="hidden-name">h
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
												<xsl:if test="@DSPROPIEDAD='ListaValores'">
													<xsl:element name="backup-table">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
												<xsl:if test="@DSPROPIEDAD='UsarcomoFiltro'">
													<xsl:element name="usarcomo-filtro">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
												<xsl:if test="@DSPROPIEDAD='Etiqueta'">
													<xsl:element name="field-label">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
												<xsl:if test="@DSPROPIEDAD='Obligatorio'">
													<xsl:if test="@OTVALOR='S'">
														<xsl:element name="allow-blank">false</xsl:element>
													</xsl:if>
													<xsl:if test="@OTVALOR='N'">
														<xsl:element name="allow-blank">true</xsl:element>
													</xsl:if>
												</xsl:if>
											</xsl:for-each-group>
										</ext-control>
									</xsl:if>
                        
                        
									<xsl:if test="@OTVALOR = '9'">                            
										<ext-control xsi:type="java:com.biosnet.ice.ext.elements.form.NumberFieldControl">
											<xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
												<!--xsl:if test="@DSPROPIEDAD='TipoDato'">
														<xsl:if test="@OTVALOR='A'">
															<xsl:element name="xtype">textfield</xsl:element>
														</xsl:if>
														<xsl:if test="@OTVALOR='N'">
															<xsl:element name="xtype">numberfield</xsl:element>
														</xsl:if>
														<xsl:if test="@OTVALOR='D'">
															<xsl:element name="xtype">datefield</xsl:element>
														</xsl:if>
														<xsl:if test="@OTVALOR='F'">
															<xsl:element name="xtype">datefield</xsl:element>
														</xsl:if>
													</xsl:if-->
											</xsl:for-each-group>
											<xsl:element name="hidde-parent">true</xsl:element>
											<xsl:element name="width">200</xsl:element>
                            
                            
											<xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
												<xsl:if test="@DSPROPIEDAD='NombreTecnico'">
													<xsl:element name="name">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
													<xsl:element name="id">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='LongitudMinima'">
													<xsl:element name="min-length">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
													<xsl:element name="min-length-text">El mínimo de caracteres es 
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='LongitudMaxima'">
													<xsl:element name="max-length">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
													<xsl:element name="max-length-text">El máximo de caracteres es 
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='UsarcomoFiltro'">
													<xsl:element name="usarcomo-filtro">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='Etiqueta'">
													<xsl:element name="field-label">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='Obligatorio'">
													<xsl:if test="@OTVALOR='S'">
														<xsl:element name="allow-blank">false</xsl:element>
													</xsl:if>
													<xsl:if test="@OTVALOR='N'">
														<xsl:element name="allow-blank">true</xsl:element>
													</xsl:if>
												</xsl:if>
											</xsl:for-each-group>
                                
										</ext-control>
									</xsl:if>
                        
									<xsl:if test="@OTVALOR = '8'">                            
										<ext-control xsi:type="java:com.biosnet.ice.ext.elements.form.DateFieldControl">
											<xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
												<!--xsl:if test="@DSPROPIEDAD='TipoDato'">
														<xsl:if test="@OTVALOR='A'">
															<xsl:element name="xtype">textfield</xsl:element>
														</xsl:if>
														<xsl:if test="@OTVALOR='N'">
															<xsl:element name="xtype">numberfield</xsl:element>
														</xsl:if>
														<xsl:if test="@OTVALOR='D'">
															<xsl:element name="xtype">datefield</xsl:element>
														</xsl:if>
														<xsl:if test="@OTVALOR='F'">
															<xsl:element name="xtype">datefield</xsl:element>
														</xsl:if>
													</xsl:if-->
											</xsl:for-each-group>
											<xsl:element name="hidde-parent">true</xsl:element>
											<xsl:element name="width">200</xsl:element>
                            
                            
											<xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
												<xsl:if test="@DSPROPIEDAD='NombreTecnico'">
													<xsl:element name="name">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
													<xsl:element name="id">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='UsarcomoFiltro'">
													<xsl:element name="usarcomo-filtro">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='Etiqueta'">
													<xsl:element name="field-label">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='Obligatorio'">
													<xsl:if test="@OTVALOR='S'">
														<xsl:element name="allow-blank">false</xsl:element>
													</xsl:if>
													<xsl:if test="@OTVALOR='N'">
														<xsl:element name="allow-blank">true</xsl:element>
													</xsl:if>
												</xsl:if>
											</xsl:for-each-group>
                                
										</ext-control>
									</xsl:if>                      
                        
									<xsl:if test="@OTVALOR = '3'">                            
										<ext-control xsi:type="java:com.biosnet.ice.ext.elements.form.CheckBoxControl">
											<xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
												<!--xsl:if test="@DSPROPIEDAD='TipoDato'">
														<xsl:if test="@OTVALOR='A'">
															<xsl:element name="xtype">textfield</xsl:element>
														</xsl:if>
														<xsl:if test="@OTVALOR='N'">
															<xsl:element name="xtype">numberfield</xsl:element>
														</xsl:if>
														<xsl:if test="@OTVALOR='D'">
															<xsl:element name="xtype">datefield</xsl:element>
														</xsl:if>
														<xsl:if test="@OTVALOR='F'">
															<xsl:element name="xtype">datefield</xsl:element>
														</xsl:if>
													</xsl:if-->
											</xsl:for-each-group>
											<xsl:element name="hidde-parent">true</xsl:element>
											<xsl:element name="width">200</xsl:element>
                            
                            
											<xsl:for-each-group select="current-group()" group-by="@DSPROPIEDAD">
												<xsl:if test="@DSPROPIEDAD='NombreTecnico'">
													<xsl:element name="name">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
													<xsl:element name="id">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<!--xsl:if test="@DSPROPIEDAD='LongitudMinima'">
														<xsl:element name="min-length"><xsl:value-of select="@OTVALOR" /></xsl:element>
													</xsl:if>

													<xsl:if test="@DSPROPIEDAD='LongitudMaxima'">
														<xsl:element name="max-length"><xsl:value-of select="@OTVALOR" /></xsl:element>
													</xsl:if-->
                                    
												<xsl:if test="@DSPROPIEDAD='UsarcomoFiltro'">
													<xsl:element name="usarcomo-filtro">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
                                    
												<xsl:if test="@DSPROPIEDAD='Etiqueta'">
													<xsl:element name="field-label">
														<xsl:value-of select="@OTVALOR" />
													</xsl:element>
												</xsl:if>
											</xsl:for-each-group>
                                
										</ext-control>
									</xsl:if>                      
                        


								</children>
							</xsl:for-each-group>
						</section-list>
					</xsl:if>  
          
				</xsl:for-each-group>
			</xsl:if>
		</xsl:for-each-group>
	</xsl:template>    

    
</xsl:stylesheet>