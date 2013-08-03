package mx.com.aon.catbo.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;

import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.catbo.model.ArchivoVO;
import mx.com.aon.catbo.model.ArchivosFaxesVO;
import mx.com.aon.catbo.model.CompraTiempoVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.portal.dao.CustomStoredProcedure;

public class AdministracionTipoArchivoDAO extends AbstractDAO{


	 protected void initDao() throws Exception {
        addStoredProcedure("OBTIENE_ARCHIVOS",new BuscarArchivos(getDataSource()));
        addStoredProcedure("BORRA_TIPO_ARCHIVO",new BorrarTipoArchivo(getDataSource())); 
        addStoredProcedure("GUARDA_TIPO_ARCHIVO",new GuardarArchivos(getDataSource()));
        addStoredProcedure("OBTENER_ARCHIVOS_EXPORT",new ArchivosExport(getDataSource())); 
        
        
        addStoredProcedure("BORRAR_ARCHIVOS",new BorrarArchivos(getDataSource())); 

        addStoredProcedure("AGREGAR_ATRIBUTOS_ARCHIVOS",new AgregarAtributoArchivo(getDataSource()));
        addStoredProcedure("BUSCAR_ATRIBUTOS_ARCHIVOS",new BuscarAtributosArchivos(getDataSource()));
        addStoredProcedure("BORRAR_ATRIBUTOS_ARCHIVOS",new BorrarAtributosArchivos(getDataSource())); 
        addStoredProcedure("EDITAR_ATRIBUTOS_ARCHIVOS",new EditarAtributosArchivos(getDataSource())); 
        addStoredProcedure("OBTENER_ATRIBUTOS_ARCHIVOS_EXPORT",new AtributosArchivosExport(getDataSource())); 
        
        
        addStoredProcedure("OBTENER_FAXES",new ObtenerFaxes(getDataSource()));
        addStoredProcedure("BORRAR_FAX",new BorrarFax(getDataSource()));
        addStoredProcedure("BORRAR_VALORES_FAX",new BorrarValoresFax(getDataSource()));
        
        ///addStoredProcedure("OBTIENE_ATRIBUTOS_FAX",new ObtieneAtributosFax(getDataSource()));
        //addStoredProcedure("OBTIENE_ATRIBUTOS_FAX_REG",new ObtieneAtributosFaxReg(getDataSource()));
        //addStoredProcedure("BORRAR_ATRIBUTOS_FAX",new BorraAtributosFax(getDataSource()));
        //addStoredProcedure("GUARDAR_ATRIBUTOS_FAX",new GuardarAtributosFax(getDataSource()));
        //addStoredProcedure("EDITAR_GUARDAR_ATRIBUTOS_FAX",new EditarGuardarAtributosFax(getDataSource()));
        //addStoredProcedure("OBTIENE_ATRIBUTOS_FAX_EXPORT",new ObtieneAtributosFaxExport(getDataSource()));

        
       
        
        //addStoredProcedure("GUARDA_ARCHIVOS_MOVIMIENTO",new GuardarArchivosMovimiento(getDataSource())); 
        

    }
   protected class BuscarArchivos extends CustomStoredProcedure {
    
     protected BuscarArchivos(DataSource dataSource) {
         super(dataSource, "PKG_CATBO.P_OBTIENE_CATBOTIPAR");
         declareParameter(new SqlParameter("pv_dsarchivo_i", OracleTypes.VARCHAR));
         //declareParameter(new SqlParameter("pv_nmovimiento_i", OracleTypes.NUMERIC));

         declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ArchivoMapper()));
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
   
   protected class ArchivosExport extends CustomStoredProcedure {

       protected ArchivosExport(DataSource dataSource) {
           
           super(dataSource, "PKG_CATBO.P_OBTIENE_CATBOTIPAR");
           declareParameter(new SqlParameter("pv_dsarchivo_i", OracleTypes.VARCHAR));

           declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneArchivoMapperExport()));
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
   
   protected class ObtieneArchivoMapperExport  implements RowMapper {
       @SuppressWarnings("unchecked")
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           ArrayList lista =  new ArrayList(6);
           //lista.add(rs.getString("CDTIPOAR"));
           lista.add(rs.getString("DSARCHIVO"));
           lista.add(rs.getString("INDARCHIVO"));
           //lista.add(rs.getString("DSINDARCHIVO"));
           return lista;
       }
   }
   
   
   
   
   protected class BorrarArchivos extends CustomStoredProcedure{
   	 protected BorrarArchivos(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_BORRA_MCASOARC");
            declareParameter(new SqlParameter("pv_nmcaso_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmovimiento_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmarchivo_i",OracleTypes.NUMERIC));
            
            declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
            compile();
          }

          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
   }
   
   /** administracion tipo de archivo       */  
   
   protected class GuardarArchivos extends CustomStoredProcedure{
   protected GuardarArchivos(DataSource dataSource) {
       super(dataSource,"PKG_CATBO.P_GUARDA_CATBOTIPAR");
       
       declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.VARCHAR));
       declareParameter(new SqlParameter("pv_dsarchivo_i",OracleTypes.VARCHAR));
       declareParameter(new SqlParameter("pv_indarchivo_i",OracleTypes.VARCHAR));
       
       declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
       declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
       compile();
     }
   public WrapperResultados mapWrapperResultados(Map map) throws Exception {
       WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
       return mapper.build(map);
      }
 }
   

   
   protected class BorrarTipoArchivo extends CustomStoredProcedure{
	   	 protected BorrarTipoArchivo(DataSource dataSource) {
	            super(dataSource, "PKG_CATBO.P_BORRA_CATBOTIPAR");
	            
	            declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.VARCHAR));
	            
	            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
	            compile();
	          }

	          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	              return mapper.build(map);
	          }
	   }
   
   
 /** administracion tipo de archivo       */  
   
   
    protected class AgregarAtributoArchivo extends CustomStoredProcedure {
       protected AgregarAtributoArchivo(DataSource dataSource) {
           super(dataSource, "PKG_CATBO.P_INSERTA_TATRIARC");
           
           declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.VARCHAR));
           declareParameter(new SqlParameter("pv_cdatribu_i",OracleTypes.NUMERIC));
           declareParameter(new SqlParameter("pv_dsatribu_i",OracleTypes.VARCHAR));
           declareParameter(new SqlParameter("pv_swformat_i",OracleTypes.VARCHAR ));
           declareParameter(new SqlParameter("pv_nmlmax_i",OracleTypes.NUMERIC));
           declareParameter(new SqlParameter("pv_nmlmin_i",OracleTypes.NUMERIC ));
           declareParameter(new SqlParameter("pv_swobliga_i",OracleTypes.VARCHAR));
           declareParameter(new SqlParameter("pv_ottabval_i",OracleTypes.VARCHAR));
           declareParameter(new SqlParameter("pv_status_i",OracleTypes.VARCHAR));

           declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
           declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
           compile();
         }
       
       
         public WrapperResultados mapWrapperResultados(Map map) throws Exception {
             WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
             return mapper.build(map);
         }
     }

       protected class BuscarAtributosArchivos extends CustomStoredProcedure {
           
           protected BuscarAtributosArchivos(DataSource dataSource) {
               super(dataSource, "PKG_CATBO.P_OBTIENE_TATRIARC");
               declareParameter(new SqlParameter("pv_dsarchivo_i", OracleTypes.VARCHAR));

               declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new Atributos_ArchivosMapper()));
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

       protected class BorrarAtributosArchivos extends CustomStoredProcedure{
         	 protected BorrarAtributosArchivos(DataSource dataSource) {
                  super(dataSource, "PKG_CATBO.P_BORRA_TATRIARC");
                  declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.VARCHAR));
                  declareParameter(new SqlParameter("pv_cdatribu_i",OracleTypes.NUMERIC));
                  
                  declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
                  declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
                  compile();
                }

                public WrapperResultados mapWrapperResultados(Map map) throws Exception {
                    WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
                    return mapper.build(map);
                }
         }
      
       protected class EditarAtributosArchivos extends CustomStoredProcedure {
           
           protected EditarAtributosArchivos(DataSource dataSource) {
               super(dataSource, "PKG_CATBO.P_OBTIENE_TATRIARC_REG");
               declareParameter(new SqlParameter("pv_cdtipoar_i", OracleTypes.VARCHAR));
               declareParameter(new SqlParameter("pv_cdatribu_i", OracleTypes.NUMERIC));

               
               declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new Atributos_ArchivosMapper()));
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

       
       protected class Atributos_ArchivosMapper  implements RowMapper {
           public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
               ArchivoVO archivoVO = new ArchivoVO();
               archivoVO.setCdTipoar(rs.getString("CDTIPOAR"));
               archivoVO.setDsArchivo(rs.getString("DSARCHIVO"));
               
               archivoVO.setCdAtribu(rs.getString("CDATRIBU"));
               archivoVO.setDsAtribu( rs.getString("DSATRIBU"));
               archivoVO .setSwFormat(rs.getString("SWFORMAT"));

               archivoVO.setNmLmax(rs.getString("NMLMAX"));
               archivoVO.setNmLmin(rs.getString("NMLMIN"));
               archivoVO.setSwObliga(rs.getString("SWOBLIGA"));

               archivoVO.setDsTabla(rs.getString("DSTABLA"));
               archivoVO.setOtTabval(rs.getString("OTTABVAL"));
               archivoVO.setStatus(rs.getString("STATUS"));

               return archivoVO;
           }
       }
   
   protected class ArchivoMapper  implements RowMapper {
       public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           ArchivoVO archivoVO = new ArchivoVO();
           archivoVO.setCdTipoar(rs.getString("CDTIPOAR"));//(rs.getString("CDTIPOAR"));
           archivoVO.setDsArchivo(rs.getString("DSARCHIVO"));//(rs.getString("DSARCHIVO"));
           archivoVO.setIndArchivo(rs.getString("INDARCHIVO"));//(rs.getString("INDARCHIVO"));
           archivoVO.setDsIndArchivo(rs.getString("DSINDARCHIVO"));//(rs.getString("DSINDARCHIVO"));
           return archivoVO;
       }
   }
   

   protected class AtributosArchivosExport extends CustomStoredProcedure {

       protected AtributosArchivosExport(DataSource dataSource) {
           
           super(dataSource, "PKG_CATBO.P_OBTIENE_TATRIARC");
           declareParameter(new SqlParameter("pv_dsarchivo_i", OracleTypes.VARCHAR));

           declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new AtributoArchivoMapperExport()));
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
   
      protected class AtributoArchivoMapperExport  implements RowMapper {
         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           ArrayList lista =  new ArrayList(7);
           
           lista.add(rs.getString("cdTipoar"));  //pv_cdtipoar_i
           lista.add(rs.getString("cdAtribu"));  //pv_cdatribu_i
           lista.add(rs.getString("swFormat"));  //pv_swformat_i
           lista.add(rs.getString("nmLmin"));    //pv_nmlmin_i
           lista.add(rs.getString("nmLmax"));    //pv_nmlmax_i
           lista.add(rs.getString("swObliga"));  //pv_swobliga_i
           lista.add(rs.getString("status"));    //pv_status_i
           
           return lista;
        }
     }
   
  /////////////////////////////////////////////////////////////
   
   

   protected class ObtenerFaxes extends CustomStoredProcedure {
   
   protected ObtenerFaxes(DataSource dataSource) {
       super(dataSource, "PKG_CATBO.P_OBTIENE_MFAXES");
       declareParameter(new SqlParameter("pv_dsarchivo_i", OracleTypes.VARCHAR));
       declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
       declareParameter(new SqlParameter("pv_nmfax_i", OracleTypes.NUMERIC));
       declareParameter(new SqlParameter("pv_nmpoliex_i", OracleTypes.VARCHAR));
       
       declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ArchivoMapper()));
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
   
   protected class ObtenerFaxesMapper  implements RowMapper {
       public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
       	FaxesVO faxesVO = new FaxesVO();
           
       	faxesVO.setCdtipoar(rs.getString("CDTIPOAR"));
       	faxesVO.setDsarchivo(rs.getString("DSARCHIVO"));
       	faxesVO.setNmfax(rs.getString("NMFAX"));
       	faxesVO.setNmpoliex(rs.getString("NMPOLIEX"));
       	faxesVO.setNmcaso(rs.getString("NMCASO"));
       	faxesVO.setCdusuario(rs.getString("CDUSUARIO"));
       	faxesVO.setDsusuari(rs.getString("DSUSUARI"));
       	faxesVO.setFerecepcion(rs.getString("FERECEPCION"));
           
           return faxesVO;
       }
   }
   
   protected class BorrarFax extends CustomStoredProcedure{
  	 protected BorrarFax(DataSource dataSource) {
           super(dataSource, "PKG_CATBO.P_BORRA_MFAXES");
           declareParameter(new SqlParameter("pv_nmfax_i",OracleTypes.NUMERIC));
                       
           declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
           declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
           compile();
         }


         public WrapperResultados mapWrapperResultados(Map map) throws Exception {
             WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
             return mapper.build(map);
         }
  	
  }
   
   protected class BorrarValoresFax extends CustomStoredProcedure{
     	 protected BorrarValoresFax(DataSource dataSource) {
              super(dataSource, "PKG_CATBO.P_BORRA_TVALOFAX");
              declareParameter(new SqlParameter("pv_nmfax_i",OracleTypes.NUMERIC));
                          
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
