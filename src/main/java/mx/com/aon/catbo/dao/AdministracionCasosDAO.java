package mx.com.aon.catbo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ExtJSAtributosVO;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.UserSQLDateConverter;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.beanutils.Converter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class AdministracionCasosDAO extends AbstractDAO{


	 protected void initDao() throws Exception {
        addStoredProcedure("OBTENER_CASOS",new BuscarCasos(getDataSource()));
        addStoredProcedure("OBTENER_CASO_REG", new ObtenerCasoReg(getDataSource()));
        addStoredProcedure("OBTENER_MCASO_USR", new ObtenerMCasoUsr(getDataSource()));
        addStoredProcedure("OBTENER_ATRIBUTOS_VARIABLES_CASO", new ObtenerAtributosVariablesCaso(getDataSource()));
        addStoredProcedure("OBTIENE_BUSCAR_CASOS_EXPORT",new BuscarCasosExportar(getDataSource()));
        addStoredProcedure("GUARDA_CDMODULO", new GuardarModuloUsuarioReasignacion(getDataSource()));
        addStoredProcedure("OBTIENE_MOVIMIENTOS_CASOS_EXPORT",new BuscarMovimientosCasosExportar(getDataSource()));
        addStoredProcedure("VALIDA_TEIMPO_MATRIZ",new ValidarTiempoMatriz(getDataSource()));
    }
	 
	 /*
	   <param eval="true" name="pv_nmcaso_i" type="VARCHAR"><![CDATA[$!params.get("pv_nmcaso_i")]]></param>
			 <param eval="true" name="pv_cdorden_i" type="VARCHAR"><![CDATA[$!params.get("pv_cdorden_i")]]></param>
	  	  	 <param eval="true" name="pv_dsproceso_i" type="VARCHAR"><![CDATA[$!params.get("pv_dsproceso_i")]]></param>
	  	     <param eval="true" name="pv_feingreso_i" type="VARCHAR"><![CDATA[$!params.get("pv_feingreso_i")]]></param>
	  	      <param eval="true" name="pv_cdpriord_i" type="VARCHAR"><![CDATA[$!params.get("pv_cdpriord_i")]]></param>
	  	      <param eval="true" name="pv_cdperson_i" type="NUMERIC"><![CDATA[$!params.get("pv_cdperson_i")]]></param>
	  	      <param eval="true" name="pv_dsperson_i" type="VARCHAR"><![CDATA[$!params.get("pv_dsperson_i")]]></param>
	  	   	  <param eval="true" name="pv_cdusuario_i" type="VARCHAR"><![CDATA[$!params.get("pv_cdusuario_i")]]></param>
	   */

	 /**
	  * Métodos usados para exportar Detalle de Caso
	  */
	 
	 //Obtiene datos del caso
	 protected class ObtenerCasoReg extends CustomStoredProcedure {
		 protected ObtenerCasoReg (DataSource dataSource){
			 super(dataSource, "PKG_CATBO.P_OBTIENE_MCASO_REG");
			 declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("pv_cdmatriz_i", OracleTypes.NUMERIC));
			 declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new CasoRegMapper()));
			 declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			 declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			 compile();
		 }
		 
		 @SuppressWarnings("unchecked")
		public WrapperResultados mapWrapperResultados (Map map) throws Exception {
			 WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			 WrapperResultados wrapperResultados = mapper.build(map);
			 List result = (List) map.get("pv_registro_o");
			 wrapperResultados.setItemList(result);
			 
			 return wrapperResultados;
		 }
	 }

	   protected class CasoRegMapper  implements RowMapper {
	       public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	           CasoVO casoVO = new CasoVO();
	           casoVO.setCdProceso(rs.getString("CDPROCESO"));
	           casoVO.setDesProceso(rs.getString("DSPROCESO"));
	           casoVO.setCdPrioridad(rs.getString("CDPRIORD"));
	           casoVO.setDesPrioridad(rs.getString("DSPRIORIDAD"));
	           casoVO.setDsNivatn(rs.getString("DSNIVATN"));
	           casoVO.setNmCaso(rs.getString("NMCASO"));
	           casoVO.setCdUsuario(rs.getString("CDUSUARIO"));
	           casoVO.setCdUnieco(rs.getString("CDUNIECO"));
	           casoVO.setDesUnieco(rs.getString("DSUNIECO"));
	           casoVO.setColor(rs.getString("COLOR"));
	           casoVO.setCdOrdenTrabajo(rs.getString("CDORDENTRABAJO"));
	           casoVO.setCdnumerordencia(rs.getString("CDNUMERORDENCIA"));
	           casoVO.setCdModulo(rs.getString("CDMODULO"));
	           casoVO.setCdStatus(rs.getString("CDSTATUS"));
	           casoVO.setTreSolucion(rs.getString("TIEMPORESTANTEPARAATENDER"));
	           casoVO.setDesModulo(rs.getString("DSMODULO"));
	           casoVO.setTesCalamiento(rs.getString("TIEMPORESTANTEPARAESCALAR"));
	           casoVO.setNmVecesCompra(rs.getString("NMVECESCOMPRA"));
	           casoVO.setCdFormatoOrden(rs.getString("CDFORMATOORDEN"));
	           casoVO.setNmovimiento(rs.getString("NMOVIMIENTO"));
	           casoVO.setDesStatus(rs.getString("DSSTATUS"));
	           casoVO.setCdNivatn(rs.getString("CDNIVATN"));
	           casoVO.setNmCompra(rs.getString("NMCOMPRA"));
	           
	           return casoVO;
	       }
	   }
	   //Final... obtiene datos del caso
	   
	   //Obtiene los atributos variables
	   protected class ObtenerAtributosVariablesCaso extends CustomStoredProcedure {
		   protected ObtenerAtributosVariablesCaso (DataSource dataSource) {
			   super(dataSource, "PKG_ORDENT.P_OBTIENE_ATRIB_SECCION");
			   declareParameter(new SqlParameter("pv_cdformatoorden_i", OracleTypes.NUMERIC));
			   declareParameter(new SqlParameter("pv_cdseccion_i", OracleTypes.NUMERIC));
				 declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerAtributosVariablesCasoMapper()));
				 declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
				 declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
				 compile();
			   
		   }
		   public WrapperResultados mapWrapperResultados (Map map) throws Exception {
			   WrapperResultadosGeneric mapper = new WrapperResultadosGeneric ();
			   WrapperResultados wrapperResultados = mapper.build(map);
			   List result = (List) map.get("pv_registro_o");
			   wrapperResultados.setItemList(result);
			   return wrapperResultados;
		   }
	   }
	   protected class ObtenerAtributosVariablesCasoMapper implements RowMapper{
	       public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	   ExtJSAtributosVO atributosVO = new ExtJSAtributosVO ();
	    	   atributosVO.setCdFormatoOrden(rs.getString("CDFORMATOORDEN"));
	    	   atributosVO.setDsFomatoOrden(rs.getString("DSFORMATOORDEN"));
	    	   atributosVO.setCdSeccion(rs.getString("CDSECCION"));
	    	   atributosVO.setDsSeccion(rs.getString("DSSECCION"));
	    	   atributosVO.setDsAtribu(rs.getString("DSATRIBU"));
	    	   atributosVO.setCdAtribu(rs.getString("CDATRIBU"));
	    	   atributosVO.setCdBloque(rs.getString("CDBLOQUE"));
	    	   atributosVO.setDsBloque(rs.getString("DSBLOQUE"));
	    	   atributosVO.setCdCampo(rs.getString("CDCAMPO"));
	    	   atributosVO.setDsCampo(rs.getString("DSCAMPO"));
	    	   atributosVO.setOtTabVal(rs.getString("OTTABVAL"));
	    	   atributosVO.setSwFormat(rs.getString("SWFORMAT"));
	    	   atributosVO.setMaxLength(rs.getString("NMLMAX"));
	    	   atributosVO.setMinLength(rs.getString("NMLMIN"));
	    	   atributosVO.setCdExpres(rs.getString("CDEXPRES"));
	    	   atributosVO.setNmOrden(rs.getString("NMORDEN"));

	    	   return atributosVO;
	       }
	   }
	   //Final.... Obtiene los atributos variables
	   
	   //Obtiene los datos del usuario
	   protected class ObtenerMCasoUsr extends CustomStoredProcedure {
		   protected ObtenerMCasoUsr (DataSource dataSource) {
			   super(dataSource, "PKG_CATBO.P_OBTENER_MCASOUSR_REG");
				 declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
				 declareParameter(new SqlParameter("pv_cdmatriz_i", OracleTypes.NUMERIC));
				 declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerMCasoUsrMapper()));
				 declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
				 declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
				 compile();
		   }
		   public WrapperResultados mapWrapperResultados (Map map) throws Exception {
			   WrapperResultadosGeneric mapper = new WrapperResultadosGeneric ();
			   WrapperResultados wrapperResultados = mapper.build(map);
			   List result = (List) map.get("pv_registro_o");
			   wrapperResultados.setItemList(result);
			   return wrapperResultados;
		   }
	   }

	   protected class ObtenerMCasoUsrMapper implements RowMapper {
		   public Object mapRow (ResultSet rs, int rowNum) throws SQLException {
			   CasoVO casoVO = new CasoVO ();
			   casoVO.setCdUsuario(rs.getString("CDUSUARIO"));
			   casoVO.setDesUsuario(rs.getString("DSUSUARI"));
			   casoVO.setCdRolmat(rs.getString("CDROLMAT"));
			   //casoVO.setEmail(rs.getString("EMAIL"));
			   casoVO.setDesRolmat(rs.getString("DSROLMAT"));

			   return casoVO;
		   }
	   }
	   //Final.... Obtiene los datos del usuario
	   
		 /**
		  * Final.... Métodos usados para exportar Detalle de Caso
		  */
	 protected class BuscarCasos extends CustomStoredProcedure {
    
     protected BuscarCasos(DataSource dataSource) {
         super(dataSource, "PKG_CATBO.P_OBTIENE_MCASOS");
         declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
         declareParameter(new SqlParameter("pv_cdorden_i", OracleTypes.VARCHAR));
         declareParameter(new SqlParameter("pv_dsproceso_i", OracleTypes.VARCHAR));
         declareParameter(new SqlParameter("pv_feingreso_i", OracleTypes.DATE));
         declareParameter(new SqlParameter("pv_cdpriord_i", OracleTypes.VARCHAR));
         declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.NUMERIC));
         declareParameter(new SqlParameter("pv_dsperson_i", OracleTypes.VARCHAR));
         declareParameter(new SqlParameter("pv_cdusuario_i", OracleTypes.VARCHAR));
       
      

         declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new CasosMapper()));
         declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
         declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
         compile();
       }

       public WrapperResultados mapWrapperResultados(Map map) throws Exception {
           WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
           WrapperResultados wrapperResultados = mapper.build(map);
           List result = (List) map.get("pv_registro_o");
           wrapperResultados.setItemList(result);
           return wrapperResultados;
       }
   }
   
   protected class CasosMapper  implements RowMapper {
       public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           CasoVO casoVO = new CasoVO();
           casoVO.setCdMatriz(rs.getString("CDMATRIZ"));
           casoVO.setNmCaso(rs.getString("NMCASO"));
           casoVO.setCdProceso(rs.getString("CDPROCESO"));
           casoVO.setDesProceso(rs.getString("DSPROCESO"));//DSPROCESO
           casoVO.setCdUnieco(rs.getString("CDUNIECO"));
           casoVO.setDesUnieco(rs.getString("DSUNIECO"));
           casoVO.setCdRamo(rs.getString("CDRAMO"));
           casoVO.setDsRamo(rs.getString("DSRAMO"));
           casoVO.setFeRegistro(rs.getString("FEREGISTRO"));
           casoVO.setTesCalamiento(rs.getString("TESCALAMIENTO"));
           casoVO.setTreSolucion(rs.getString("TRESOLUCION"));
           casoVO.setCdPerson(rs.getString("CDPERSON"));//CDPERSON
           casoVO.setCdnumerordencia(rs.getString("CDNUMERORDENCIA"));
           casoVO.setCdFormatoOrden(rs.getString("CDFORMATOORDEN"));
          //dsnombre
           casoVO.setCdPrioridad(rs.getString("CDPRIORD"));
           casoVO.setDesPrioridad(rs.getString("DSPRIORIDAD"));
           casoVO.setCdStatus(rs.getString("CDSTATUS"));
           casoVO.setDesStatus(rs.getString("DSSTATUS"));
           casoVO.setNmCompra(rs.getString("NMCOMPRA"));
           casoVO.setDesModulo(rs.getString("DSMODULO"));
           casoVO.setCdNivatn(rs.getString("CDNIVATN"));//
           casoVO.setDsNivatn(rs.getString("DSNIVATN"));//
           casoVO.setIndSemafColor(rs.getString("IND_SEMAF_COLOR"));
           casoVO.setDesNombre(rs.getString("DSNOMBRE"));
           
           /*
           casoVO.setCdOrdenTrabajo(rs.getString("CDORDENTRABAJO"));
          
           casoVO.setCdUsuario(rs.getString("CDUSUARIO"));
           casoVO.setDesUsuario(rs.getString("DSUSUARI"));
           
           
          
           
       
           casoVO.setIndSemafColor(rs.getString("IND_SEMAF_COLOR"));//
           casoVO.setIdMetContact(rs.getString("IDSMETCONTAC")) ;//
    
           casoVO.setFeResolucion(rs.getString("FERESOLUCION"));
           
           casoVO.setFeEscalamiento(rs.getString("FEESCALAMIENTO"));//
           casoVO.setNmVecesCompra(rs.getString("NMVECESCOMPRA"));
           
           casoVO.setIndPoliza(rs.getString("IND_POLIZA"));
           
           
           
          
           casoVO.setPorcentaje(rs.getString("PORCENTAJE"));*/
           
           
           
           return casoVO;
       }
   }
   
   /*	               
        <xsl:if test="@IDSMETCONTAC">**
                    <xsl:element name="id-met-contact">
                        <xsl:value-of select="@IDSMETCONTAC" />**
                    </xsl:element>
                </xsl:if>                           
          <xsl:if test="@FEREGISTRO">
                    <xsl:element name="fe-registro">
                        <xsl:value-of select="@FEREGISTRO" />**
                    </xsl:element>
                </xsl:if>  
          <xsl:if test="@FERESOLUCION">
                    <xsl:element name="fe-resolucion">
                        <xsl:value-of select="@FERESOLUCION" />**
                    </xsl:element>
                </xsl:if>        
         <xsl:if test="@TRESOLUCION">
                    <xsl:element name="tre-solucion">
                        <xsl:value-of select="@TRESOLUCION" />**
                    </xsl:element>
                </xsl:if>                   
                           
            <xsl:if test="@FEESCALAMIENTO">
                    <xsl:element name="fe-escalamiento">
                        <xsl:value-of select="@FEESCALAMIENTO" />**
                    </xsl:element>
                </xsl:if>  
       <xsl:if test="@NMVECESCOMPRA">
                    <xsl:element name="nm-veces-compra">
                        <xsl:value-of select="@NMVECESCOMPRA" />**
                    </xsl:element>
                </xsl:if>           
         <xsl:if test="@TESCALAMIENTO">
                    <xsl:element name="tes-calamiento">
                        <xsl:value-of select="@TESCALAMIENTO" />**
                    </xsl:element>
                </xsl:if>  
           
          <xsl:if test="@IND_POLIZA">
                    <xsl:element name="ind-poliza">
                        <xsl:value-of select="@IND_POLIZA" />**
                    </xsl:element>
                </xsl:if>          
                 <xsl:if test="@CDNUMERORDENCIA">
                    <xsl:element name="cdnumerordencia">
                        <xsl:value-of select="@CDNUMERORDENCIA" />**
                    </xsl:element>
                </xsl:if>                
				<xsl:if test="@CDFORMATOORDEN">
                    <xsl:element name="cd-formato-orden">
                        <xsl:value-of select="@CDFORMATOORDEN" />**
                    </xsl:element>
                </xsl:if> 
                <xsl:if test="@NMCOMPRA">
                    <xsl:element name="nm-compra">
                        <xsl:value-of select="@NMCOMPRA" />**
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSMODULO">
                    <xsl:element name="des-modulo">
                        <xsl:value-of select="@DSMODULO" />**
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDNIVATN">
                    <xsl:element name="cd-nivatn">
                        <xsl:value-of select="@CDNIVATN" />**
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSNIVATN">
                    <xsl:element name="ds-nivatn">
                        <xsl:value-of select="@DSNIVATN" />**
                    </xsl:element>
                </xsl:if>
              	<xsl:if test="@PORCENTAJE">
                    <xsl:element name="porcentaje">
                        <xsl:value-of select="@PORCENTAJE" />
                    </xsl:element>
                </xsl:if>*/

   protected class BuscarCasosExportar extends CustomStoredProcedure {

       protected BuscarCasosExportar(DataSource dataSource) {
           super(dataSource, "PKG_CATBO.P_OBTIENE_MCASOS");
           
           declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
           declareParameter(new SqlParameter("pv_cdorden_i", OracleTypes.VARCHAR));
           declareParameter(new SqlParameter("pv_dsproceso_i", OracleTypes.VARCHAR));
           declareParameter(new SqlParameter("pv_feingreso_i", OracleTypes.DATE));
           declareParameter(new SqlParameter("pv_cdpriord_i", OracleTypes.VARCHAR));
           declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.NUMERIC));
           declareParameter(new SqlParameter("pv_dsperson_i", OracleTypes.VARCHAR));
           declareParameter(new SqlParameter("pv_cdusuario_i", OracleTypes.VARCHAR));
         
        

           declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new CasosMapperExport()));
           declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
           declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
           compile();

         }


         public WrapperResultados mapWrapperResultados(Map map) throws Exception {
             WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
             WrapperResultados wrapperResultados = mapper.build(map);
             List result = (List) map.get("pv_registro_o");
             wrapperResultados.setItemList(result);
             return wrapperResultados;
         }
     }
   
   protected class CasosMapperExport  implements RowMapper {
       @SuppressWarnings("unchecked")
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           ArrayList lista =  new ArrayList(7);
            
        lista.add(rs.getString("NMCASO"));         	
       	lista.add(rs.getString("CDNUMERORDENCIA"));
       	lista.add(rs.getString("DSPROCESO"));
       	//lista.add(rs.getString("FEREGISTRO"));
       	lista.add(ConvertUtil.convertToString(rs.getDate("FEREGISTRO")));
       	lista.add(rs.getString("DSNOMBRE"));
       	lista.add(rs.getString("DSPRIORIDAD"));
       	lista.add(rs.getString("IND_SEMAF_COLOR"));

       	
       	return lista;
       }
   }
   
   
   protected class GuardarModuloUsuarioReasignacion extends CustomStoredProcedure {
	   protected GuardarModuloUsuarioReasignacion(DataSource dataSource) {
           super(dataSource, "PKG_CATBO.P_GUARDA_CDMODULO");
           
           declareParameter(new SqlParameter("pv_cdmodulo_i", OracleTypes.VARCHAR));
           declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
        
           declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
           declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
           compile();
         }
	   
	   public WrapperResultados mapWrapperResultados(Map map) throws Exception {
           WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
           return mapper.build(map);
       }
   }

   protected class BuscarMovimientosCasosExportar extends CustomStoredProcedure {

       protected BuscarMovimientosCasosExportar(DataSource dataSource) {
           super(dataSource, "PKG_CATBO.P_OBTIENE_MCASOSMOV");
           
           declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
          
           declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new MovCasosMapperExport()));
           declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
           declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
           compile();

         }


         public WrapperResultados mapWrapperResultados(Map map) throws Exception {
             WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
             WrapperResultados wrapperResultados = mapper.build(map);
             List result = (List) map.get("pv_registro_o");
             wrapperResultados.setItemList(result);
             return wrapperResultados;
         }
     }
   
   protected class MovCasosMapperExport  implements RowMapper {
       @SuppressWarnings("unchecked")
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	   Converter converter = new UserSQLDateConverter("");
    	   ArrayList lista =  new ArrayList(5);
          
           lista.add(rs.getString("NMOVIMIENTO"));         	
       	   //lista.add(rs.getString("CDSTATUS"));
       	   lista.add(rs.getString("DSSTATUS"));
       	   lista.add(rs.getString("DSOBSERVACION"));
       	   lista.add(ConvertUtil.convertToString(rs.getDate("FEREGISTRO")));
       	
       	return lista;
       }
   }
   
   protected class ValidarTiempoMatriz extends CustomStoredProcedure {

       protected ValidarTiempoMatriz(DataSource dataSource) {
           super(dataSource, "PKG_CATBO.VALIDA_TIEMPOS_MATRIZ");

           declareParameter(new SqlParameter("pv_cdmatriz_i", OracleTypes.NUMBER));
          
           declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
           declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

           compile();

         }

       public WrapperResultados mapWrapperResultados(Map map) throws Exception {
           WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
           return mapper.build(map);
       }
     }
}
