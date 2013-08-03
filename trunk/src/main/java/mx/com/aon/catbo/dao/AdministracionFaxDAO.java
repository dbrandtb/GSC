package mx.com.aon.catbo.dao;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.ExtJSAtributosFaxVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.PolizaFaxVO;
import mx.com.aon.catbo.model.MediaTO;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.PolizaDAO;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;

import mx.com.aon.portal.model.ConsultaPolizasCanceladasVO;
import mx.com.aon.portal.service.ProcessResultManager;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class AdministracionFaxDAO extends AbstractDAO{

	private static Logger logger = Logger.getLogger(PolizaDAO.class);
	
	private ProcessResultManager processResultManager;

    protected void initDao() throws Exception {
        addStoredProcedure("BUSCAR_ADMINISTRACION_FAXES",new BuscarAdministracionFax(getDataSource()));
        addStoredProcedure("BUSCAR_ADMINISTRACION_VALOR_FAXES",new BuscarAdministracionValorFax(getDataSource()));
        addStoredProcedure("GUARDA_ADMINISTRACION_FAXES",new GuardarAdministracionFax(getDataSource()));
        addStoredProcedure("GUARDA_ADMINISTRACION_VALOR_FAXES",new GuardarAdministracionValorFax(getDataSource()));
        addStoredProcedure("BORRA_ADMINISTRACION_FAXES",new BorraAdministracionFax(getDataSource()));
        addStoredProcedure("OBTIENE_TATRIARC_FAXES",new ObtieneTatriarcFaxes(getDataSource()));
        addStoredProcedure("OBTIENE_VALORES_FAXES",new ObtieneValoresFaxes(getDataSource()));
        addStoredProcedure("VALIDA_NMCASO_FAXES_CIMA",new ValidaNmcasoFaxes(getDataSource()));
        addStoredProcedure("OBTIENE_POLIZA_FAXES",new ObtienePolizaFaxes(getDataSource()));
        
        //addStoredProcedure("BORRA_ADMINISTRACION_VALOR_FAXES",new BorraAdministracionValorFax(getDataSource()));
   }


     protected class BuscarAdministracionFax extends CustomStoredProcedure {

      protected BuscarAdministracionFax(DataSource dataSource) {
          super(dataSource, "PKG_CATBO.P_OBTIENE_MFAXES");

          declareParameter(new SqlParameter("pv_dsarchivo_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmfax_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmpoliex_i", OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ConsultaAdministracionFaxMapper()));
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
     
     
     protected class BuscarAdministracionValorFax extends CustomStoredProcedure {

         protected BuscarAdministracionValorFax(DataSource dataSource) {
             super(dataSource, "PKG_CATBO.P_OBTIENE_TVALOFAX");

           
             declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_nmfax_i", OracleTypes.VARCHAR));
             declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ConsultaAdministracionValorFaxMapper()));
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
     
      protected class GuardarAdministracionFax extends CustomStoredProcedure{

     	protected GuardarAdministracionFax(DataSource dataSource) {
             super(dataSource, "PKG_CATBO.P_GUARDA_MFAXES");

             declareParameter(new SqlParameter("pv_nmcaso_i",OracleTypes.VARCHAR));
             declareParameter(new SqlInOutParameter("pv_nmfax_i", OracleTypes.NUMERIC));
            // declareParameter(new SqlParameter("pv_nmfax_i",OracleTypes.NUMERIC));
             declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_feingreso_i",OracleTypes.DATE));
             declareParameter(new SqlParameter("pv_ferecepcion_i",OracleTypes.DATE));
             declareParameter(new SqlParameter("pv_nmpoliex_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_cdusuari_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_blarchivo_i",OracleTypes.BLOB));
             declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
             declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
           
             compile();
           }

     	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            wrapperResultados.setResultado((map.get("pv_nmfax_i")).toString());
            return wrapperResultados;
        }
       }
     
     protected class GuardarAdministracionValorFax extends CustomStoredProcedure{

      	protected GuardarAdministracionValorFax(DataSource dataSource) {
              super(dataSource, "PKG_CATBO.P_GUARDA_TVALOFAX");

              declareParameter(new SqlParameter("pv_nmcaso_i",OracleTypes.VARCHAR));
              declareParameter(new SqlParameter("pv_nmfax_i",OracleTypes.NUMERIC));
              declareParameter(new SqlParameter("pv_cdtipoar_i",OracleTypes.NUMERIC));
              declareParameter(new SqlParameter("pv_cdatribu_i",OracleTypes.NUMERIC));
              declareParameter(new SqlParameter("pv_otvalor_i",OracleTypes.VARCHAR));
              
              declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
              declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            
              compile();
            }

            public WrapperResultados mapWrapperResultados(Map map) throws Exception {
                WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
                return mapper.build(map);
            }
        }
      
     
     protected class BorraAdministracionFax extends CustomStoredProcedure{
	   	 protected BorraAdministracionFax(DataSource dataSource) {
	            super(dataSource, "PKG_CATBO.P_BORRA_MFAXES");
	            
	            declareParameter(new SqlParameter("pv_nmfax_i",OracleTypes.NUMERIC));
	            declareParameter(new SqlParameter("pv_nmcaso_i",OracleTypes.VARCHAR));
	            
	            
	            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
	            compile();
	          }

	          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	              return mapper.build(map);
	          }
	   }
     
     //el Pl esta mal en la BD porque devuelve un cursor
  /*   protected class BorraAdministracionValorFax extends CustomStoredProcedure{
	   	 protected BorraAdministracionValorFax(DataSource dataSource) {
	            super(dataSource, "PKG_CATBO.P_BORRA_TVALOFAX");
	            
	            declareParameter(new SqlParameter("pv_nmfax_i",OracleTypes.NUMERIC));
	            declareParameter(new SqlParameter("pv_nmcaso_i",OracleTypes.VARCHAR));
	            
	            
	            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
	            compile();
	          }

	          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	              return mapper.build(map);
	          }
	   }*/


     protected class ObtieneTatriarcFaxes extends CustomStoredProcedure {

         protected ObtieneTatriarcFaxes(DataSource dataSource) {
             super(dataSource, "PKG_CATBO.P_OBTIENE_TATRIARC");

             declareParameter(new SqlParameter("pv_cdtipoar_i", OracleTypes.VARCHAR));
             declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ConsultaTatriarcFaxMapper()));
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
     
     
     protected class ObtieneValoresFaxes extends CustomStoredProcedure {

         protected ObtieneValoresFaxes(DataSource dataSource) {
             super(dataSource, "PKG_CATBO.P_OBTIENE_VALORES_FAX");

             declareParameter(new SqlParameter("pv_cdtipoar_i", OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_nmfax_i", OracleTypes.VARCHAR));
             
             declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ConsultaValoresFaxMapper()));
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
     
     protected class ValidaNmcasoFaxes extends CustomStoredProcedure {

         protected ValidaNmcasoFaxes(DataSource dataSource) {
             super(dataSource, "PKG_CATBO.P_VALIDA_NMCASO_FAX");

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
     
    /**************************************Mapper**********************************************/ 
    protected class ConsultaAdministracionFaxMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	FaxesVO faxesVO = new FaxesVO();

        	faxesVO.setCdtipoar(rs.getString("CDTIPOAR"));
        	faxesVO.setDsarchivo(rs.getString("DSARCHIVO"));
           	faxesVO.setNmfax(rs.getString("NMFAX"));
        	faxesVO.setNmpoliex(rs.getString("NMPOLIEX"));
        	faxesVO.setNmcaso(rs.getString("NMCASO"));
        	faxesVO.setCdusuario(rs.getString("CDUSUARIO"));
        	faxesVO.setDsusuari(rs.getString("DSUSUARI"));
        	faxesVO.setFerecepcion(ConvertUtil.convertToString(rs.getDate("FERECEPCION")));
        
            return faxesVO;
        }
    }

    protected class ConsultaAdministracionValorFaxMapper  implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	FaxesVO faxesVO = new FaxesVO();

    	faxesVO.setCdatribu(rs.getString("CDATRIBU"));
    	faxesVO.setCdtipoar(rs.getString("CDTIPOAR"));
       	faxesVO.setNmcaso(rs.getString("NMCASO"));
    	faxesVO.setNmfax(rs.getString("NMFAX"));
    	faxesVO.setOtvalor(rs.getString("OTVALOR"));
    	
        return faxesVO;
    }
}
    
    protected class ConsultaTatriarcFaxMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ExtJSAtributosFaxVO extJSAtributosFaxVO = new ExtJSAtributosFaxVO();

        	extJSAtributosFaxVO.setCdtipoar(rs.getString("CDTIPOAR"));
        	extJSAtributosFaxVO.setDsarchivo(rs.getString("DSARCHIVO"));
        	extJSAtributosFaxVO.setCdAtribu(rs.getString("CDATRIBU"));
        	extJSAtributosFaxVO.setDsAtribu(rs.getString("DSATRIBU"));
        	extJSAtributosFaxVO.setSwFormat(rs.getString("SWFORMAT"));
        	extJSAtributosFaxVO.setNmLmax(rs.getString("NMLMAX"));
        	extJSAtributosFaxVO.setNmLmin(rs.getString("NMLMIN"));
        	extJSAtributosFaxVO.setSwObliga(rs.getString("SWOBLIGA"));
        	extJSAtributosFaxVO.setDsTabla(rs.getString("DSTABLA"));
        	extJSAtributosFaxVO.setOtTabVal(rs.getString("OTTABVAL"));
        	extJSAtributosFaxVO.setStatus(rs.getString("STATUS"));
        	extJSAtributosFaxVO.setFormato(rs.getString("FORMATO"));
        	
            return extJSAtributosFaxVO;
        }
    }
    
    
    protected class ConsultaValoresFaxMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ExtJSAtributosFaxVO extJSAtributosFaxVO = new ExtJSAtributosFaxVO();

        	extJSAtributosFaxVO.setCdAtribu(rs.getString("CDATRIBU"));
        	extJSAtributosFaxVO.setDsAtribu(rs.getString("DSATRIBU"));
        	extJSAtributosFaxVO.setSwFormat(rs.getString("SWFORMAT"));
        	extJSAtributosFaxVO.setNmLmin(rs.getString("NMLMIN"));
        	extJSAtributosFaxVO.setNmLmax(rs.getString("NMLMAX"));
        	extJSAtributosFaxVO.setOtTabVal(rs.getString("OTTABVAL"));
        	extJSAtributosFaxVO.setSwObliga(rs.getString("SWOBLIGA"));
        	extJSAtributosFaxVO.setOtvalor(rs.getString("OTVALOR"));
        	extJSAtributosFaxVO.setDsvalor(rs.getString("DSVALOR"));
        	
            return extJSAtributosFaxVO;
        }
    }
    
    protected class ObtienePolizaFaxes extends CustomStoredProcedure {

        protected ObtienePolizaFaxes(DataSource dataSource) {                           
            super(dataSource, "PKG_CATBO.P_OBTIENE_CDPER_CDELE");

            declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ConsultaPolizasFaxMapper()));
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
    
      protected class ConsultaPolizasFaxMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	
        	PolizaFaxVO polizaFaxVO = new PolizaFaxVO();

        	polizaFaxVO.setCdPerson(rs.getString("CDPERSON"));
        	polizaFaxVO.setCdElemento(rs.getString("CDELEMENTO"));
        	
            return polizaFaxVO;
        }
    }
    
    
    
	
}
