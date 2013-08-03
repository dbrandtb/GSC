
package mx.com.aon.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.model.ConsultaPolizasCanceladasVO;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.procesos.emision.model.RecibosVO;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class PolizaDAO extends AbstractDAO{

    private static Logger logger = Logger.getLogger(PolizaDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("BUSCAR_POLIZAS_CANCELAR",new BuscarPolizasACancelar(getDataSource()));
        addStoredProcedure("MODIFICA_CANCELACION_POLIZAS",new ModificarCancelacionPoliza(getDataSource()));
        addStoredProcedure("BUSCAR_POLIZAS_CANCELADAS",new BuscarPolizasCanceladas(getDataSource()));
        addStoredProcedure("OBTIENE_POLIZAS_CANCELADAS_EXPORT",new ObtienePolizasCanceladasExport(getDataSource()));
        addStoredProcedure("OBTIENE_POLIZAS_CANCELAR_EXPORT",new ObtienePolizasCancelarExport(getDataSource()));
        addStoredProcedure("REVERTIR_POLIZAS_CANCELADAS",new RevertirPolizasCanceladas(getDataSource()));
        addStoredProcedure("CANCELACION_MANUAL_POLIZAS_CALCULAR_PRIMA",new CalcularPrima(getDataSource()));
        addStoredProcedure("CANCELACION_MANUAL_POLIZAS_GUARDAR",new GuardarPoliza(getDataSource()));
        addStoredProcedure("REHABILITACION_MANUAL_REHABILITAR_POLIZA",new RehabilitarPoliza(getDataSource()));
        addStoredProcedure("POLIZAS_MAESTRAS_GUARDAR",new GuardarPolizaMaestra(getDataSource()));
        addStoredProcedure("REHABILITACION_MASIVA_REHABILITAR_POLIZA",new RehabilitarPolizaMasiva(getDataSource()));
        addStoredProcedure("POLIZAS_CANCELADAS_OBTIENE_RECIBOS_DETALLE",new PolizasCanceladasObtieneReciboDetalle(getDataSource()));

    }


    protected class RevertirPolizasCanceladas extends CustomStoredProcedure{

    	protected RevertirPolizasCanceladas(DataSource dataSource) {
            super(dataSource, "PKG_CANCELA.P_REVIERTE_CANCELA");

            declareParameter(new SqlParameter("p_CDUNIECO",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("p_CDRAMO",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("p_ESTADO",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("p_NMPOLIZA",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("p_NSUPLOGI",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("p_NMSUPLEM",OracleTypes.BIGINT));

            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();
          }

    	
          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
      }



    protected class BuscarPolizasACancelar extends CustomStoredProcedure {

      protected BuscarPolizasACancelar(DataSource dataSource) {
          super(dataSource, "PKG_CANCELA.P_OBTIENE_POLIZA_A_CANCELAR");

          declareParameter(new SqlParameter("pv_asegurado_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsuniage_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsramo_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ConsultaPolizasACancelarMapper()));
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


    protected class ModificarCancelacionPoliza extends CustomStoredProcedure {

      protected ModificarCancelacionPoliza(DataSource dataSource) {
          super(dataSource, "PKG_CANCELA.P_MODIFICA_CANCELACION");

          declareParameter(new SqlParameter("pv_cdunieco_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cdramo_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_estado_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmpoliza_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_nmsuplem_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cdagrupa_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_nmrecibo_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdcancel_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_status_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_fecancel_i",OracleTypes.DATE));
          declareParameter(new SqlParameter("pv_swcancela_i",OracleTypes.VARCHAR));

          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

          compile();
        }

        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }








    protected class BuscarPolizasCanceladas extends CustomStoredProcedure {

      protected BuscarPolizasCanceladas(DataSource dataSource) {
          super(dataSource, "PKG_CANCELA.P_POLIZAS_CANCELADAS");

          declareParameter(new SqlParameter("pv_asegurado_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsuniage_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsramo_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdrazon_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_fecancel_ini_i", OracleTypes.DATE));
          declareParameter(new SqlParameter("pv_fecancel_fin_i", OracleTypes.DATE));
          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ConsultaPolizasCanceladasMapper()));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o ", OracleTypes.VARCHAR));

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


    protected class ConsultaPolizasCanceladasMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ConsultaPolizasCanceladasVO consultaPolizasCanceladasVO = new ConsultaPolizasCanceladasVO();

        	consultaPolizasCanceladasVO.setAsegurado(rs.getString("ASEGURADO"));
        	consultaPolizasCanceladasVO.setCdUniage(rs.getString("CDUNIAGE"));
        	consultaPolizasCanceladasVO.setDsUnieco(rs.getString("DSUNIECO"));
        	consultaPolizasCanceladasVO.setCdUnieco(rs.getString("CDUNIECO"));
        	consultaPolizasCanceladasVO.setCdRamo(rs.getString("CDRAMO"));
        	consultaPolizasCanceladasVO.setDsRamo(rs.getString("DSRAMO"));
        	consultaPolizasCanceladasVO.setEstado(rs.getString("ESTADO"));
        	consultaPolizasCanceladasVO.setNmPoliza(rs.getString("NMPOLIZA"));
        	consultaPolizasCanceladasVO.setNmSituac(rs.getString("NMSITUAC"));
            consultaPolizasCanceladasVO.setFeCancel(ConvertUtil.convertToString(rs.getDate("FECANCEL")));
        	consultaPolizasCanceladasVO.setPrimaNoDevengada(rs.getString("PRIMA_NO_DEVENGADA"));
        	consultaPolizasCanceladasVO.setCdRazon(rs.getString("CDRAZON"));
        	consultaPolizasCanceladasVO.setDsRazon(rs.getString("DSRAZON"));
        	consultaPolizasCanceladasVO.setNmsuplem(rs.getString("NMSUPLEM"));
        	consultaPolizasCanceladasVO.setNmPoliex(rs.getString("NMPOLIEX"));
        	consultaPolizasCanceladasVO.setInciso(rs.getString("INCISO"));
        	consultaPolizasCanceladasVO.setPol_bol(rs.getString("POL_BOL"));
        	logger.debug("FEEFECTO: " + ConvertUtil.convertToString(rs.getDate("FEEFECTO")));
        	logger.debug("FEANULAC: " + ConvertUtil.convertToString(rs.getDate("FEANULAC")));
        	logger.debug("FEVENCIM: " + ConvertUtil.convertToString(rs.getDate("FEVENCIM")));
        	logger.debug("FEPROREN: " + ConvertUtil.convertToString(rs.getDate("FEPROREN")));
        	consultaPolizasCanceladasVO.setFeefecto(ConvertUtil.convertToString(rs.getDate("FEEFECTO")));
            consultaPolizasCanceladasVO.setFeanulac(ConvertUtil.convertToString(rs.getDate("FEANULAC")));
            consultaPolizasCanceladasVO.setFevencim(ConvertUtil.convertToString(rs.getDate("FEVENCIM")));
            consultaPolizasCanceladasVO.setFeproren(ConvertUtil.convertToString(rs.getDate("FEPROREN")));
            consultaPolizasCanceladasVO.setComentarios(rs.getString("COMENTARIOS"));
            consultaPolizasCanceladasVO.setNmCancel(rs.getString("NMCANCEL"));
            consultaPolizasCanceladasVO.setCdPerson(rs.getString("CDPERSON"));
            consultaPolizasCanceladasVO.setCdMoneda(rs.getString("CDMONEDA"));
            consultaPolizasCanceladasVO.setReha(rs.getString("REHA"));
            
            logger.debug( consultaPolizasCanceladasVO.toString() );
            return consultaPolizasCanceladasVO;
        }
    }

    protected class ConsultaPolizasACancelarMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ConsultaPolizasCanceladasVO consultaPolizasCanceladasVO = new ConsultaPolizasCanceladasVO();

        	consultaPolizasCanceladasVO.setAsegurado(rs.getString("ASEGURADO"));
        	consultaPolizasCanceladasVO.setCdUniage(rs.getString("CDUNIAGE"));
        	consultaPolizasCanceladasVO.setDsUnieco(rs.getString("DSUNIECO"));
        	consultaPolizasCanceladasVO.setCdRamo(rs.getString("CDRAMO"));
        	consultaPolizasCanceladasVO.setDsRamo(rs.getString("DSRAMO"));
        	consultaPolizasCanceladasVO.setNmPoliza(rs.getString("NMPOLIZA"));
        	consultaPolizasCanceladasVO.setNmSituac(rs.getString("NMSITUAC"));
        	consultaPolizasCanceladasVO.setTipoCancel(rs.getString("TIPO_CANCEL"));
        	consultaPolizasCanceladasVO.setFeCancel(ConvertUtil.convertToString(rs.getDate("FECANCEL")));
            consultaPolizasCanceladasVO.setCdUnieco(rs.getString("CDUNIECO"));
            consultaPolizasCanceladasVO.setNmsuplem(rs.getString("NMSUPLEM"));
            consultaPolizasCanceladasVO.setCdagrupa(rs.getString("CDAGRUPA"));
            consultaPolizasCanceladasVO.setCdcancel(rs.getString("CDCANCEL"));
            consultaPolizasCanceladasVO.setStatus(rs.getString("STATUS"));
            consultaPolizasCanceladasVO.setNmPoliex(rs.getString("NMPOLIEX"));
            if (rs.getString("SWCANCELA") != null) {
	            if (rs.getString("SWCANCELA").equals("N")){
	            	 consultaPolizasCanceladasVO.setSwcancela("0");
	            }else{
	            	 consultaPolizasCanceladasVO.setSwcancela("1");
	            }
            }

            consultaPolizasCanceladasVO.setEstado(rs.getString("ESTADO"));
            consultaPolizasCanceladasVO.setNmrecibo(rs.getString("NMRECIBO"));


            return consultaPolizasCanceladasVO;
        }
    }





    protected class ObtienePolizasCanceladasExport extends CustomStoredProcedure {

        protected ObtienePolizasCanceladasExport(DataSource dataSource) {
            super(dataSource, "PKG_CANCELA.P_POLIZAS_CANCELADAS");

            declareParameter(new SqlParameter("pv_asegurado_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_dsuniage_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_dsramo_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdrazon_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_fecancel_ini_i", OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fecancel_fin_i", OracleTypes.DATE));



            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new PolizasCanceladasMapperExport()));
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

    protected class PolizasCanceladasMapperExport  implements RowMapper {
        @SuppressWarnings("unchecked")
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArrayList lista =  new ArrayList(6);

            lista.add(rs.getString("ASEGURADO"));
        	lista.add(rs.getString("DSUNIECO"));
        	lista.add(rs.getString("DSRAMO"));
        	lista.add(rs.getString("NMPOLIEX"));
        	lista.add(rs.getString("NMSITUAC"));
        	lista.add(ConvertUtil.convertToString(rs.getDate("FECANCEL")));
        	lista.add(rs.getString("DSRAZON"));

            return lista;
        }
    }


    protected class CalcularPrima extends CustomStoredProcedure{

    	protected CalcularPrima(DataSource dataSource) {
            super(dataSource, "PKG_CANCELA.P_PRIMA_NO_DEVENGADA");

            declareParameter(new SqlParameter("pv_cdunieco_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmpoliza_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_feefecto_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fecancel_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fevencim_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_cdrazon_i",OracleTypes.VARCHAR));

            declareParameter(new SqlOutParameter("pv_val_prim", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_texto_o", OracleTypes.VARCHAR));
            
            //declareParameter(new SqlOutParameter("pv_texto_o", OracleTypes.NUMERIC));
            //declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
            //declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();
          }

        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados  wrapperResultados = mapper.build(map);
            String _pvValPrim = ((map.get("pv_val_prim"))==null)?"":(map.get("pv_val_prim")).toString();
            wrapperResultados.setResultado(_pvValPrim);
            
            return wrapperResultados;
        }
      }

    protected class GuardarPoliza extends CustomStoredProcedure{

    	protected GuardarPoliza(DataSource dataSource) {
            super(dataSource, "PKG_CANCELA.P_CANCELA_POLIZA");

            declareParameter(new SqlParameter("pv_cdunieco_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cduniage_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmsituac_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdrazon_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_comenta_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_feefecto_i",OracleTypes.DATE));            
            declareParameter(new SqlParameter("pv_fevencim_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fecancel_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_usuario_i",OracleTypes.VARCHAR));

            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_texto_o", OracleTypes.VARCHAR));

           // declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
           // declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();
          }

          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
      }

    protected class RehabilitarPoliza extends CustomStoredProcedure{

    	protected RehabilitarPoliza(DataSource dataSource) {
            super(dataSource, "PKG_CANCELA.P_REHABILITA_POLIZA");

            declareParameter(new SqlParameter("pv_cdunieco_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_feefecto_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fevencim_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fecancel_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fereinst_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_cdrazon_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdperson_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdmoneda_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmcancel_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_comments_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmsuplem_i",OracleTypes.NUMERIC));

            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            
            //declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
            //declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();

/*
<statements>
	<statement type="stored" name="PKG_CANCELA.P_REHABILITA_POLIZA" id="pRehabilitarPoliza">
        <param eval="true" name="pv_cdunieco_i" type="NUMERIC">$!params.get("cdUniEco")</param>
        <param eval="true" name="pv_cdramo_i" type="NUMERIC">$!params.get("cdRamo")</param>
        <param eval="true" name="pv_estado_i" type="VARCHAR">$!params.get("estado")</param>
        <param eval="true" name="pv_nmpoliza_i" type="NUMERIC">$!params.get("nmPoliza")</param>
        <param eval="true" name="pv_feefecto_i" type="VARCHAR">$!params.get("feEfecto")</param>
        <param eval="true" name="pv_fevencim_i" type="VARCHAR">$!params.get("feVencim")</param>
        <param eval="true" name="pv_fecancel_i" type="VARCHAR">$!params.get("feCancel")</param>
        <param eval="true" name="pv_fereinst_i" type="VARCHAR">$!params.get("feReInst")</param>
        <param eval="true" name="pv_cdrazon_i" type="NUMERIC">$!params.get("cdRazon")</param>
        <param eval="true" name="pv_cdperson_i" type="NUMERIC">$!params.get("cdPerson")</param>
        <param eval="true" name="pv_cdmoneda_i" type="VARCHAR">$!params.get("cdMoneda")</param>
        <param eval="true" name="pv_nmcancel_i" type="NUMERIC">$!params.get("nmCancel")</param>
        <param eval="true" name="pv_comments_i" type="VARCHAR"><![CDATA[$!params.get("comentarios")]]></param>
        <param eval="true" name="pv_nmsuplem_i" type="NUMERIC">$!params.get("nmSuplem")</param>


		<outparam id="pv_msg_id_o" type="NUMERIC"/>
        <outparam id="pv_title_o" type="VARCHAR"></outparam>
	</statement>

             */
          }

          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
      }


    protected class ObtienePolizasCancelarExport extends CustomStoredProcedure {

        protected ObtienePolizasCancelarExport(DataSource dataSource) {
            super(dataSource, "PKG_CANCELA.P_OBTIENE_POLIZA_A_CANCELAR");


            declareParameter(new SqlParameter("pv_asegurado_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_dsuniage_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_dsramo_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.VARCHAR));



         
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new PolizasACancelarMapperExport()));
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

    protected class PolizasACancelarMapperExport  implements RowMapper {
        @SuppressWarnings("unchecked")
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArrayList lista =  new ArrayList(6);

            lista.add(rs.getString("ASEGURADO"));
        	lista.add(rs.getString("DSUNIECO"));
        	lista.add(rs.getString("DSRAMO"));
        	lista.add(rs.getString("NMPOLIEX"));
        	lista.add(rs.getString("NMSUPLEM"));
        	lista.add(ConvertUtil.convertToString(rs.getDate("FECANCEL")));

            return lista;
        }
    }
    
    /*Polizas Maestras - Guardar*/
    protected class GuardarPolizaMaestra extends CustomStoredProcedure{

    	protected GuardarPolizaMaestra(DataSource dataSource) {
            super(dataSource, "PKG_CONFG_CUENTA.P_GUARDA_POLIZA_MAESTRA");

            declareParameter(new SqlParameter("pv_cdpolmtra_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdelemento_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdcia_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdtipo_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliex_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_feinicio_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fefin_i",OracleTypes.DATE));            
            declareParameter(new SqlParameter("pv_cdnumpol_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdnumren_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdtipsit_i",OracleTypes.VARCHAR));

            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_text_o", OracleTypes.VARCHAR));

            compile(); 
          }

          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
      }
    
    protected class RehabilitarPolizaMasiva extends CustomStoredProcedure {

        protected RehabilitarPolizaMasiva(DataSource dataSource) {
            super(dataSource, "PKG_CANCELA.P_REHABILITA_POLIZA");
         
            declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_feefecto_i", OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fevencim_i", OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fecancel_i", OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fereinst_i", OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_cdrazon_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdmoneda_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmcancel_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_comments_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.NUMERIC));

            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }

          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              return wrapperResultados;
          }
      }
    
    
    
    protected class PolizasCanceladasObtieneReciboDetalle extends CustomStoredProcedure {

        protected PolizasCanceladasObtieneReciboDetalle(DataSource dataSource) {
            super(dataSource, "PKG_CONSULTA.P_OBTIENE_MRECIDET_C");

            declareParameter(new SqlParameter("pv_cdUnieco_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdRamo_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_Estado_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_NmPoliza_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmRecibo_i", OracleTypes.NUMERIC));
            
            declareParameter(new SqlOutParameter("pv_RegDatos_o", OracleTypes.CURSOR, new PolizasCanceladasObtieneReciboDetalleMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

            compile();

          }

          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);

              List result = (List) map.get("pv_RegDatos_o");
              wrapperResultados.setItemList(result);

              return wrapperResultados;
          }
      }
    
    
    protected class PolizasCanceladasObtieneReciboDetalleMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	 RecibosVO  RecibosDetalleVO = new RecibosVO();
        	 RecibosDetalleVO.setCdTipoCon( rs.getString("CDTIPCON"));
        	 RecibosDetalleVO.setDsTipoCon( rs.getString("DSTIPCON"));
        	 RecibosDetalleVO.setImporteCon(rs.getString("PTIMPORT"));
        	 
            return RecibosDetalleVO;
        }
    }  
    
}
