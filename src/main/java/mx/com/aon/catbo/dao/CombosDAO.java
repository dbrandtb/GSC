package mx.com.aon.catbo.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;

import javax.sql.DataSource;

import mx.com.aon.catbo.model.ElementoComboBoxVO;
import mx.com.aon.catbo.model.ResponsablesVO;
import mx.com.aon.catbo.model.EncuestaVO;

import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.model.AlertasUsuarioPolizaPorAseguradoraVO;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class CombosDAO extends AbstractDAO{

    private static Logger logger = Logger.getLogger(CombosDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("COMBO_OBTIENE_FORMATOS",new ComboObtieneFormatos(getDataSource()));
        addStoredProcedure("COMBO_OBTIENE_TABLA",new ComboObtieneTablas(getDataSource()));
        addStoredProcedure("COMBO_USUARIO_REEMPLAZO",new ComboObtieneUsuarios(getDataSource()));
        addStoredProcedure("COMBO_OBTIENE_ALGORITMOS",new ComboObtieneAlgoritmos(getDataSource()));
        addStoredProcedure("COMBO_OBTIENE_TIPO_GUION",new ComboObtieneTipoGuion (getDataSource()));
        addStoredProcedure("COMBO_OBTIENE_TIPO_FAX",new ComboObtieneTipoFax (getDataSource()));
        addStoredProcedure("COMBO_USUARIO_RESPONSABLE",new ComboUsuarioResponsable (getDataSource()));
        addStoredProcedure("COMBO_CARGA_ARCHIVOS",new ComboCargaArchivos (getDataSource()));
        addStoredProcedure("COMBO_CARGA_ARCHIVOS2",new ComboCargaArchivos2 (getDataSource()));
        addStoredProcedure("OBTIENE_RAMOS_2",new ComboRamos2 (getDataSource()));
        addStoredProcedure("OBTIENE_TABLA",new ObtieneTabla (getDataSource()));
        addStoredProcedure("OBTIENE_POLIZA_POR_ASEGURADORA2",new ComboObtienePolizaAseguradora (getDataSource()));
        
    }




    protected class ComboObtieneFormatos extends CustomStoredProcedure {

        protected ComboObtieneFormatos(DataSource dataSource) {
            super(dataSource, "PKG_LISTAS.P_LISTA_TCATALOG");


            declareParameter(new SqlParameter("pv_cdtabla_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdidioma_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdregion_i", OracleTypes.VARCHAR));

            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboValoresMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();
          }

          @SuppressWarnings("unchecked")
  		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);

              return wrapperResultados;
          }
      }


    
    
    protected class ComboObtieneUsuarios extends CustomStoredProcedure {

        protected ComboObtieneUsuarios(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_OBTIENE_RESPONS_SUPLENTE");


            declareParameter(new SqlParameter("pv_cdmatriz_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdmodulo_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdnivatn_i", OracleTypes.NUMERIC));

            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboUsuarioMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();

          }

          @SuppressWarnings("unchecked")
  		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);

              return wrapperResultados;
          }
      }
    
    
    
    protected class ComboValoresMapper implements RowMapper{
    		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
    			ElementoComboBoxVO elementoComboBoxVO = new ElementoComboBoxVO();
    			elementoComboBoxVO.setCodigo(rs.getString("CODIGO"));
    			elementoComboBoxVO.setDescripcion(rs.getString("DESCRIPL"));
    			return elementoComboBoxVO;
            }
    }
    

    protected class ComboUsuarioMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
			ResponsablesVO responsableVO = new ResponsablesVO();
			responsableVO.setCdusuari(rs.getString("CDUSUARI"));
			responsableVO.setDsusuari(rs.getString("DSUSUARI"));
			
			return responsableVO;
        }
   }
    
    
    


    
    protected class ComboObtieneTablas extends CustomStoredProcedure {

        protected ComboObtieneTablas(DataSource dataSource) {
            super(dataSource, "PKG_WIZARD.P_LISVALATRIBVAR");


            declareParameter(new SqlParameter("pv_cdtabla_i", OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboValoresMapper2()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();

          }


          @SuppressWarnings("unchecked")
  		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);

              return wrapperResultados;
          }
      }
    
    protected class ComboValoresMapper2 implements RowMapper{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
			ElementoComboBoxVO elementoComboBoxVO = new ElementoComboBoxVO();
			elementoComboBoxVO.setCodigo(rs.getString("CDTABLA"));
			elementoComboBoxVO.setDescripcion(rs.getString("DSTABLA"));
			return elementoComboBoxVO;

        }
    }
    
    // ComboObtieneAlgoritmos 
    protected class ComboObtieneAlgoritmos extends CustomStoredProcedure {

        protected ComboObtieneAlgoritmos(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_OBTIENE_ALGORITMO");
           
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboObtieneAlgoritmosMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();

          }


          @SuppressWarnings("unchecked")
  		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);

              return wrapperResultados;
          }
      }

    protected class ComboObtieneAlgoritmosMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
			ElementoComboBoxVO elementoComboBoxVO = new ElementoComboBoxVO();
			elementoComboBoxVO.setCodigo(rs.getString("IDGENERADOR"));
			elementoComboBoxVO.setDescripcion(rs.getString("DESCRIPCION"));
			return elementoComboBoxVO;

        }
    }
    //FIN ComboObtieneAlgoritmos
    protected class ComboObtieneTipoGuion extends CustomStoredProcedure {

        protected ComboObtieneTipoGuion(DataSource dataSource) {
            super(dataSource, "PKG_LISTAS.P_CTIPGUION");
           
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboObtieneTipoGuionMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();

          }


          @SuppressWarnings("unchecked")
  		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);

              return wrapperResultados;
          }
      }

    protected class ComboObtieneTipoGuionMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
			ElementoComboBoxVO elementoComboBoxVO = new ElementoComboBoxVO();
			elementoComboBoxVO.setCodigo(rs.getString("CDTIPGUI"));
			elementoComboBoxVO.setDescripcion(rs.getString("DSTIPGUI"));
			return elementoComboBoxVO;

        }
    }
    
    
    /*****************************Combo Tipo de Fax *****************************/

    protected class ComboObtieneTipoFax extends CustomStoredProcedure {

        protected ComboObtieneTipoFax(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_OBTIENE_TIPOS_FAX");
           
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboObtieneTipoFaxMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();

          }


          @SuppressWarnings("unchecked")
  		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);

              return wrapperResultados;
          }
      }

    protected class ComboObtieneTipoFaxMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
			ElementoComboBoxVO elementoComboBoxVO = new ElementoComboBoxVO();
			elementoComboBoxVO.setCdtipoar(rs.getString("CDTIPOAR"));
			elementoComboBoxVO.setDsarchivo(rs.getString("DSARCHIVO"));
			elementoComboBoxVO.setIndarchivo(rs.getString("INDARCHIVO"));
			return elementoComboBoxVO;

        }
    }
    
    
    protected class ComboUsuarioResponsable extends CustomStoredProcedure {

        protected ComboUsuarioResponsable(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_Obtiene_Usuario_Encuesta");


            declareParameter(new SqlParameter("pv_nmconfig_i", OracleTypes.NUMERIC));

            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboUsuarioResponsableMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();

          }

          @SuppressWarnings("unchecked")
  		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);

              return wrapperResultados;
          }
    
          protected class ComboUsuarioResponsableMapper implements RowMapper{
      		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
      			ResponsablesVO responsablesVO = new ResponsablesVO();
      			responsablesVO.setDsusuari(rs.getString("DSUSUARI"));
      			responsablesVO.setCdusuario(rs.getString("CDUSUARIO"));
      			
      			
      			return responsablesVO;

              }
          }
                 
    }
    
    protected class ComboCargaArchivos extends CustomStoredProcedure {

        protected ComboCargaArchivos(DataSource dataSource) {
            super(dataSource, "PKG_TCATALOG.P_OBTIENE_TIPOARCHIVO");


            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboCargaArchivosMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();

          }

          @SuppressWarnings("unchecked")
  		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);

              return wrapperResultados;
          }
    
          protected class ComboCargaArchivosMapper implements RowMapper{
      		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
      			ElementoComboBoxVO elementoComboBoxVO = new ElementoComboBoxVO();
      			elementoComboBoxVO.setCodigo(rs.getString("NOMPARAM"));
      			elementoComboBoxVO.setDescripcion(rs.getString("DESPARAM"));
      			elementoComboBoxVO.setDirectorioAsociado(rs.getString("VALPARAM"));
      			
      			
      			return elementoComboBoxVO;

              }
          }
                 
    }

    
    protected class ComboCargaArchivos2 extends CustomStoredProcedure {

        protected ComboCargaArchivos2(DataSource dataSource) {
            super(dataSource, "PKG_TCATALOG.P_OBTIENE_TIPOARCHIVO2");


            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboCargaArchivosMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();

          }

          @SuppressWarnings("unchecked")
  		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);

              return wrapperResultados;
          }
    
          protected class ComboCargaArchivosMapper implements RowMapper{
      		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
      			ElementoComboBoxVO elementoComboBoxVO = new ElementoComboBoxVO();
      			elementoComboBoxVO.setCodigo(rs.getString("NOMPARAM"));
      			elementoComboBoxVO.setDescripcion(rs.getString("DESPARAM"));
      			elementoComboBoxVO.setDirectorioAsociado(rs.getString("VALPARAM"));
      			
      			
      			return elementoComboBoxVO;

              }
          }
                 
    }
    
    protected class ComboRamos2 extends CustomStoredProcedure {

        protected ComboRamos2(DataSource dataSource) {
            super(dataSource, "PKG_LISTAS.P_PRODUCTOS");
          
            declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdelemento_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
            
           
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboCargaRamosMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();

          }

          @SuppressWarnings("unchecked")
  		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);

              return wrapperResultados;
          }
    }
          protected class ComboCargaRamosMapper implements RowMapper{
      		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
      			ElementoComboBoxVO elementoComboBoxVO = new ElementoComboBoxVO();
      			
      			elementoComboBoxVO.setCdRamo(rs.getString("CDRAMO"));  //setDescripcion(rs.getString("DESPARAM"));//  
      			elementoComboBoxVO.setDsRamo(rs.getString("DSRAMO"));  //setDirectorioAsociado(rs.getString("VALPARAM"));
      			
      			
      			return elementoComboBoxVO;

              }
          }
                 
          
          protected class ObtieneTabla extends CustomStoredProcedure {

              protected ObtieneTabla(DataSource dataSource) {
                  super(dataSource, "PKG_ENCUESTAS.P_OBTIENE_CDTABLA");
                
                                 
                 
                  declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new obtieneTablasMapper()));
                  declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
                  declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

                  compile();

                }

                @SuppressWarnings("unchecked")
        		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
                    WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
                    WrapperResultados wrapperResultados = mapper.build(map);
                    List result = (List) map.get("pv_registro_o");
                    wrapperResultados.setItemList(result);

                    return wrapperResultados;
                }
          }
                protected class obtieneTablasMapper implements RowMapper{
            		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
            			EncuestaVO encuestaVO = new EncuestaVO();
            			
            			encuestaVO.setCdTabla(rs.getString("CDTABLA"));//.setCdRamo(rs.getString("CDRAMO"));  //setDescripcion(rs.getString("DESPARAM"));//  
            			encuestaVO.setDsLabel(rs.getString("DSLABEL"));//.setDsRamo(rs.getString("DSRAMO"));  //setDirectorioAsociado(rs.getString("VALPARAM"));
            			
            			
            			return encuestaVO;

                    }
                }
                       
                
                protected class ComboObtienePolizaAseguradora extends CustomStoredProcedure {

        			

                    protected ComboObtienePolizaAseguradora(DataSource dataSource) {
                        super(dataSource, "PKG_LISTAS.P_POLIZAS_X_NIV_CIA");

                        declareParameter(new SqlParameter("p_cve_aseguradora", OracleTypes.NUMERIC));
                        declareParameter(new SqlParameter("pv_cdelemento", OracleTypes.NUMERIC));
                  
                        declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboPolizaAseguradoraMapper()));
                        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
                        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

                        compile();
                      }
                    
              
                    @SuppressWarnings("unchecked")
            		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
                        WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
                        WrapperResultados wrapperResultados = mapper.build(map);
                        List result = (List) map.get("pv_registro_o");
                        wrapperResultados.setItemList(result);

                        return wrapperResultados;
                    }
               }
                
                protected class ComboPolizaAseguradoraMapper implements RowMapper{
            		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
            			AlertasUsuarioPolizaPorAseguradoraVO polizaPorAseguradoraVO = new AlertasUsuarioPolizaPorAseguradoraVO();
        
            			polizaPorAseguradoraVO.setNmPoliza(rs.getString("NMPOLIZA"));
            			polizaPorAseguradoraVO.setNmPoliex(rs.getString("NMPOLIEX"));
            			
            			logger.debug( polizaPorAseguradoraVO.toString());
            			
            			return polizaPorAseguradoraVO;
                    }
               }
                                
    }
