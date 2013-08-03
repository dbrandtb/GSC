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

public class ArchivoDAO extends AbstractDAO{


	 protected void initDao() throws Exception {
        addStoredProcedure("OBTIENE_ARCHIVOS",new BuscarArchivos(getDataSource()));
        addStoredProcedure("BORRAR_ARCHIVOS",new BorrarArchivos(getDataSource())); 
        addStoredProcedure("GUARDA_ARCHIVOS",new GuardarArchivos(getDataSource()));

        addStoredProcedure("AGREGAR_ATRIBUTOS_ARCHIVOS",new agregarAtributoArchivo(getDataSource()));
        addStoredProcedure("BUSCAR_ATRIBUTOS_ARCHIVOS",new BuscarAtributosArchivos(getDataSource()));
        addStoredProcedure("BORRAR_ATRIBUTOS_ARCHIVOS",new BorrarAtributosArchivos(getDataSource())); 
        addStoredProcedure("EDITAR_ATRIBUTOS_ARCHIVOS",new EditarAtributosArchivos(getDataSource())); 
        addStoredProcedure("OBTENER_ATRIBUTOS_ARCHIVOS_EXPORT",new AtributosArchivosExport(getDataSource())); 
        
        
        addStoredProcedure("OBTENER_FAXES",new ObtenerFaxes(getDataSource()));
        addStoredProcedure("BORRAR_FAX",new BorrarFax(getDataSource()));
        addStoredProcedure("BORRAR_VALORES_FAX",new BorrarValoresFax(getDataSource()));
        
        addStoredProcedure("OBTIENE_ATRIBUTOS_FAX",new ObtieneAtributosFax(getDataSource()));
        addStoredProcedure("OBTIENE_ATRIBUTOS_FAX_REG",new ObtieneAtributosFaxReg(getDataSource()));
        addStoredProcedure("BORRAR_ATRIBUTOS_FAX",new BorraAtributosFax(getDataSource()));
        addStoredProcedure("GUARDAR_ATRIBUTOS_FAX",new GuardarAtributosFax(getDataSource()));
        addStoredProcedure("EDITAR_GUARDAR_ATRIBUTOS_FAX",new EditarGuardarAtributosFax(getDataSource()));
        addStoredProcedure("OBTIENE_ATRIBUTOS_FAX_EXPORT",new ObtieneAtributosFaxExport(getDataSource()));
        addStoredProcedure("ACTUALIZAR_ATRIBUTOS_FAX",new ActualizarAtributosFax(getDataSource()));

        
       
        
        //addStoredProcedure("GUARDA_ARCHIVOS_MOVIMIENTO",new GuardarArchivosMovimiento(getDataSource())); 
        

    }
   protected class BuscarArchivos extends CustomStoredProcedure {
    
     protected BuscarArchivos(DataSource dataSource) {
         super(dataSource, "PKG_CATBO.P_OBTIENE_MCASOARC");
         declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
         declareParameter(new SqlParameter("pv_nmovimiento_i", OracleTypes.NUMERIC));

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
   
   protected class BorrarArchivos extends CustomStoredProcedure{
   	 protected BorrarArchivos(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_BORRA_MCASOARC");
            declareParameter(new SqlParameter("pv_nmcaso_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmovimiento_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmarchivo_i",OracleTypes.NUMERIC));
            
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }

         /* public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }*/
          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
  			WrapperResultadosGeneric res = new WrapperResultadosGeneric();
  			return res.build(map);
  		}
          
   }

   protected class GuardarArchivos extends CustomStoredProcedure{
   protected GuardarArchivos(DataSource dataSource) {
       super(dataSource,"PKG_CATBO.P_GUARDA_MCASOARC");
       declareParameter(new SqlParameter("pv_nmcaso_i",OracleTypes.VARCHAR));
       declareParameter(new SqlParameter("pv_nmovimiento_i",OracleTypes.NUMERIC));
       declareParameter(new SqlParameter("pv_nmarchivo_i",OracleTypes.NUMERIC));
       declareParameter(new SqlParameter("pv_dsarchivo_i",OracleTypes.VARCHAR));
       declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.VARCHAR));
       declareParameter(new SqlParameter("pv_blarchivo_i",OracleTypes.CLOB));
       declareParameter(new SqlParameter("pv_cdusuario_i",OracleTypes.VARCHAR));
       
       declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
       declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
       compile();
     }

     public WrapperResultados mapWrapperResultados(Map map) throws Exception {
         WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
         return mapper.build(map);
     }
   }
   
       protected class agregarAtributoArchivo extends CustomStoredProcedure {

       protected agregarAtributoArchivo(DataSource dataSource) {
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
                  super(dataSource, "PKG_CATBO.P_BORRA_TATRIARC ");
                  declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.VARCHAR));
                  declareParameter(new SqlParameter("pv_cdatribu_i",OracleTypes.NUMERIC));
                  
                  declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
                  declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
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
           archivoVO.setTipo(rs.getString("CDTIPOAR"));
           archivoVO.setDescripcion(rs.getString("DSARCHIVO"));
           archivoVO.setNumArchivo(rs.getString("NMARCHIVO"));
           archivoVO.setDsArchivo(rs.getString("DSNOMARC"));
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
                       
           declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
           declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
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
                          
              declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
              declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
              compile();
            }


            public WrapperResultados mapWrapperResultados(Map map) throws Exception {
                WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
                return mapper.build(map);
            }
     	
     }
      
   
   //INICIO---------------------PANTALLA CatBo_TatriFax-----------------------------//
   protected class ObtieneAtributosFax extends CustomStoredProcedure{
    	 protected ObtieneAtributosFax(DataSource dataSource) {
             super(dataSource, "PKG_CATBO.P_OBTIENE_TATRIARC");                /*P_OBTENER_TATRIARC");    		/* P_OBTIENE_TATRIARC */
             declareParameter(new SqlParameter("pv_cdarchivo_i",OracleTypes.VARCHAR));
             
             declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneAtributosFaxMapper()));
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
   
   protected class ObtieneAtributosFaxMapper  implements RowMapper {
       public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           FaxesVO faxesVO = new FaxesVO();
           
           faxesVO.setCdtipoar(rs.getString("CDTIPOAR"));
           faxesVO.setDsarchivo(rs.getString("DSARCHIVO"));
           faxesVO.setCdatribu(rs.getString("CDATRIBU"));
           faxesVO.setDsAtribu(rs.getString("DSATRIBU"));
           faxesVO.setSwFormat(rs.getString("SWFORMAT"));
           faxesVO.setNmLmax(rs.getString("NMLMAX"));
           faxesVO.setNmLmin(rs.getString("NMLMIN"));
           faxesVO.setSwObliga(rs.getString("SWOBLIGA"));
           faxesVO.setDsTabla(rs.getString("DSTABLA"));
           faxesVO.setOtTabVal(rs.getString("OTTABVAL"));
           faxesVO.setStatus(rs.getString("STATUS"));
           faxesVO.setFormato(rs.getString("FORMATO"));
        
           return faxesVO;
       }
   }
   
  
   
   protected class ObtieneAtributosFaxReg extends CustomStoredProcedure{
   	 protected ObtieneAtributosFaxReg(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_OBTIENE_TATRIARC_REG");
            declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdatribu_i",OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneAtributosFaxRegMapper()));
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
  
  protected class ObtieneAtributosFaxRegMapper  implements RowMapper {
      public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
          FaxesVO faxesVO = new FaxesVO();
          
          faxesVO.setCdtipoar(rs.getString("CDTIPOAR"));  //aqui
          faxesVO.setDsarchivo(rs.getString("DSARCHIVO"));
          faxesVO.setCdatribu(rs.getString("CDATRIBU"));
          faxesVO.setDsAtribu(rs.getString("DSATRIBU"));
          faxesVO.setSwFormat(rs.getString("SWFORMAT"));
          faxesVO.setNmLmax(rs.getString("NMLMAX"));
          faxesVO.setNmLmin(rs.getString("NMLMIN"));
          faxesVO.setSwObliga(rs.getString("SWOBLIGA"));
          faxesVO.setDsTabla(rs.getString("DSTABLA"));
          faxesVO.setOtTabVal(rs.getString("OTTABVAL"));
          faxesVO.setStatus(rs.getString("STATUS"));
          
          return faxesVO;
      }
  }
 
  protected class BorraAtributosFax extends CustomStoredProcedure{
  	 protected BorraAtributosFax(DataSource dataSource) {
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
  
  protected class GuardarAtributosFax extends CustomStoredProcedure{
	    protected GuardarAtributosFax(DataSource dataSource) {
	        super(dataSource,"PKG_CATBO.P_INSERTA_TATRIARC");                    //"PKG_CATBO.P_GUARDA_TATRIARC");
	        
	        declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.VARCHAR)); 
	        declareParameter(new SqlParameter("pv_cdatribu_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_dsatribu_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_swformat_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_nmlmax_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_nmlmin_i",OracleTypes.VARCHAR));

	        declareParameter(new SqlParameter("pv_swobliga_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_ottabval_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_status_i",OracleTypes.VARCHAR));
	      //  declareParameter(new SqlParameter("pv_opera_i",OracleTypes.NUMERIC)); // Y este????? Viene en 0 si edita y en 1 si agrega
	        
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
	        compile();
	      }

	      public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	          WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	          return mapper.build(map);
	      }
	    }
  //Actualizar 
  protected class ActualizarAtributosFax extends CustomStoredProcedure{
	    protected ActualizarAtributosFax(DataSource dataSource) {
	        super(dataSource,"PKG_CATBO.P_ACTUALIZA_TATRIARC");                    //"PKG_CATBO.P_GUARDA_TATRIARC");
	        
	        declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.VARCHAR)); 
	        declareParameter(new SqlParameter("pv_cdatribu_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_dsatribu_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_swformat_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_nmlmax_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_nmlmin_i",OracleTypes.NUMERIC));

	        declareParameter(new SqlParameter("pv_swobliga_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_ottabval_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_status_i",OracleTypes.VARCHAR));
	      //  declareParameter(new SqlParameter("pv_opera_i",OracleTypes.NUMERIC)); // Y este????? Viene en 0 si edita y en 1 si agrega
	        
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
	        compile();
	      }

	      public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	          WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	          return mapper.build(map);
	      }
	    }
  
  protected class EditarGuardarAtributosFax extends CustomStoredProcedure{
	    protected EditarGuardarAtributosFax(DataSource dataSource) {
	        super(dataSource,"PKG_CATBO.P_OBTIENE_TATRIARC_REG");  
	        
	        declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.VARCHAR)); 
	        declareParameter(new SqlParameter("pv_cdatribu_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_dsatribu_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_swformat_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_nmlmax_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_nmlmin_i",OracleTypes.NUMERIC));
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
 
  protected class ObtieneAtributosFaxExport extends CustomStoredProcedure {

      protected ObtieneAtributosFaxExport(DataSource dataSource) {
         
    	  super(dataSource, "PKG_CATBO.P_OBTIENE_TATRIARC");                /*P_OBTENER_TATRIARC");    		/* P_OBTIENE_TATRIARC */
          declareParameter(new SqlParameter("pv_cdarchivo_i",OracleTypes.VARCHAR));
          
          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new AdministracioTiposFaxMapperExport()));
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
  
  protected class AdministracioTiposFaxMapperExport  implements RowMapper {
      @SuppressWarnings("unchecked")
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
          ArrayList lista =  new ArrayList(11);
       
          lista.add(rs.getString("CDTIPOAR"));
          lista.add(rs.getString("DSARCHIVO"));
       // lista.add(rs.getString("CDATRIBU"));
       // lista.add(rs.getString("DSATRIBU"));
       //   lista.add(rs.getString("SWFORMAT"));
          lista.add(rs.getString("NMLMAX"));
          lista.add(rs.getString("NMLMIN"));
          lista.add(rs.getString("SWOBLIGA"));
          lista.add(rs.getString("DSTABLA"));
      //    lista.add(rs.getString("OTTABVAL"));
          lista.add(rs.getString("STATUS"));
          lista.add(rs.getString("FORMATO"));
          return lista;
          
      }
  }
   
//FIN---------------------PANTALLA CatBo_TatriFax-----------------------------//
  
  
  /* protected class ObtenerValoresFaxes extends CustomStoredProcedure {
       
       protected ObtenerValoresFaxes(DataSource dataSource) {
           super(dataSource, "PKG_CATBO.P_OBTIENE_TVALOFAX ");
           
           declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
           declareParameter(new SqlParameter("pv_nmfax_i", OracleTypes.NUMERIC));
                       
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
       
       protected class ObtenerValoresFaxesMapper  implements RowMapper {
           public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
           	ArchivosFaxesVO archivosFaxesVO = new ArchivosFaxesVO();
               
           	archivosFaxesVO.setCdatribu(rs.getString("CDATRIBU"));
           	archivosFaxesVO.setCdtipoar(rs.getString("CDTIPOAR"));
           	archivosFaxesVO..setNmfax(rs.getString("NMCASO"));
           	archivosFaxesVO..setNmpoliex(rs.getString("NMFAX"));
           	archivosFaxesVO.setNmcaso(rs.getString("OTVALOR"));
           	
               
               return faxesVO;
           }
       }
   
*/


}
