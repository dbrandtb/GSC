package mx.com.gseguros.portal.general.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.dao.RecibosDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.DetalleReciboVO;
import mx.com.gseguros.portal.general.model.ReciboVO;
import mx.com.gseguros.portal.general.service.RecibosManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.renovacion.service.impl.RenovacionManagerImpl;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utils;
import mx.com.gseguros.ws.autosgs.dao.impl.AutosSIGSDAOImpl.EjecutaVidaPorRecibo;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Recibo;
import mx.com.gseguros.ws.ice2sigs.client.model.ReciboWrapper;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService.Operacion;

public class RecibosManagerImpl implements RecibosManager {

    private static final Logger           logger       = Logger.getLogger(RenovacionManagerImpl.class);
	
    private RecibosDAO   recibosDAO;
	
	@Autowired
	private PantallasDAO pantallasDAO;
	
	@Autowired
	private Ice2sigsService ice2sigsService;
	
	@Override
	public List<ReciboVO> obtieneRecibos(String cdunieco, String cdramo, String nmpoliza, String nmsuplem) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i",   cdramo);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		return recibosDAO.obtieneRecibos(params);
	}

	@Override
	public List<DetalleReciboVO> obtieneDetallesRecibo(String cdunieco, String cdramo, String estado, String nmpoliza, String nmrecibo) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdUnieco_i", cdunieco);
		params.put("pv_cdRamo_i", cdramo);
		params.put("pv_Estado_i", estado);
		params.put("pv_NmPoliza_i", nmpoliza);
		params.put("pv_nmRecibo_i", nmrecibo);
		return recibosDAO.obtieneDetalleRecibo(params);
	}
	
	@Override
    public ManagerRespuestaImapVO pantallaRecibosSISA(String cdsisrol) throws Exception{
        logger.info(
                new StringBuilder()
                .append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                .append("\n@@@@@@ pantallaRenovacionIndividual @@@@@@")
                .append("\n@@@@@@ cdsisrol=").append(cdsisrol)
                .toString());
        ManagerRespuestaImapVO resp = new ManagerRespuestaImapVO(true);
        String paso = "";
        //obtener componentes
        try{
            paso = "antes de obtener grid de recibos";
            Map<String,Item> imap = new HashMap<String,Item>();
            paso = "despues de declarar imap";
            List<ComponenteVO> itemsRecibo = pantallasDAO.obtenerComponentes(
                    null,null,null,null,null,cdsisrol,"CONSULTA_RECIBOS_SISA","MODELO_RECIBOS",null);          
            GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
            gc.generaComponentes(itemsRecibo, true, true, false, true, true, false);          
            imap.put("itemsReciboFields"  , gc.getFields());
            imap.put("itemsReciboColumns" , gc.getColumns());
            
            List<ComponenteVO> itemsDetalleRecibo = pantallasDAO.obtenerComponentes(
                    null,null,null,null,null,cdsisrol,"CONSULTA_RECIBOS_SISA","DETALLE_RECIBO",null);          
            gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
            gc.generaComponentes(itemsDetalleRecibo, true, true, false, true, true, false);          
            imap.put("itemsDetalleFields"  , gc.getFields());
            imap.put("itemsDetalleColumns" , gc.getColumns());
            
            resp.setImap(imap);
        }
        catch(Exception ex){
            Utils.generaExcepcion(ex, paso);
        }
        logger.info(
            new StringBuilder()
                .append("\n@@@@@@ ").append(resp)
                .append("\n@@@@@@ pantallaRenovacionIndividual @@@@@@")
                .append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                .toString());
        return resp;
	}

    @Override
    public List<Map<String, String>> obtenerDatosRecibosSISA(String cdunieco, String cdramo, String estado, String nmpoliza) throws Exception{
        String paso = "";
        List<Map<String, String>> lista = new ArrayList<Map<String,String>>();
        try{
            paso  = "Antes de obtener datos de recibos";
            lista = recibosDAO.obtenerDatosRecibosSISA(cdunieco, cdramo, estado, nmpoliza);            
            paso  = "Despues de obtener datos de recibos";
        }
        catch(Exception ex){
            Utils.generaExcepcion(ex, paso);
        }
        return lista;
    }
	
    @Override
    public String consolidarRecibos(String cdunieco, String cdramo, String estado, String nmpoliza, UserVO user, List<Map<String, String>> lista) throws Exception{
        logger.debug(Utils.log(
                "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
               ,"\n@@@@@@ consolidarRecibos @@@@@@"
               ,"\n@@@@@@ lista    = " , lista
               ));
        String folio = "";    
        String paso  = "";
        try{
            folio = recibosDAO.consolidarRecibos(lista);
            actualizarFolioSIGS(cdunieco, cdramo, estado, nmpoliza, folio, user, lista);            
        }
        catch(Exception ex){
            Utils.generaExcepcion(ex, paso);
        }
        logger.debug(Utils.log(
                "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
               ,"\n@@@@@@ consolidarRecibos @@@@@@"
               ,"\n@@@@@@ folio    = " , folio
               ));
        return folio;
    }
	
    public void desconsolidarRecibos(String cdunieco, String cdramo, String estado, String nmpoliza, UserVO user, List<Map<String, String>> lista) throws Exception{
        logger.debug(Utils.log(
                "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
               ,"\n@@@@@@ desconsolidarRecibos @@@@@@"
               ,"\n@@@@@@ lista = " , lista
               ));
        String paso = "";
        try{
            paso = "Antes de desconsolidar recibos";
            for(Map<String, String> recibo:lista){
                recibosDAO.desconsolidarRecibos(recibo.get("folio"));
                actualizarFolioSIGS(cdunieco, cdramo, estado, nmpoliza, recibo.get("nmrecibo"), user, lista);
            }
        }
        catch(Exception ex){
            Utils.generaExcepcion(ex, paso);
        }
        logger.debug(Utils.log(
                "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
               ,"\n@@@@@@ desconsolidarRecibos @@@@@@"
               ));
    }
    
    @Override
    public List<DetalleReciboVO> obtieneDetallesReciboSISA(String cdunieco, String cdramo, String estado, String nmpoliza, String nmrecibo, String nmfolcon) throws Exception {
        String paso = "";
        List<DetalleReciboVO> detalle = new ArrayList<DetalleReciboVO>();
        try{
            detalle = recibosDAO.obtieneDetalleReciboSISA(cdunieco, cdramo, estado, nmpoliza, nmrecibo, nmfolcon);
        }
        catch(Exception ex){
            Utils.generaExcepcion(ex, paso);
        }
        return detalle;
    }
    
    @Override
    public void actualizarFolioSIGS(String cdunieco, String cdramo, String estado, String nmpoliza, String rmdbRn, UserVO user, List<Map<String, String>> lista) throws Exception{
        String paso = "";
        List<Map<String, String>> infoRecibos = new ArrayList<Map<String,String>>();
        try{
            paso = "Obteniendo informacion de recibos";
            for(Map<String, String> map : lista){
                String nmrecibo = map.get("nmrecibo");
                String nmsuplem = map.get("nmsuplem");
                String ntramite = map.get("ntramite");
                paso = "Antes de obtener info para SIGS";
                infoRecibos = recibosDAO.obtenerInfoRecibos(cdunieco, cdramo, estado, nmpoliza, nmrecibo, nmsuplem);
                paso = "logueando info recibos";
                logger.debug(infoRecibos);
                paso = "Antes de recorrer info recibos";
                Utils.log(paso);
                for(int i = 0; i < infoRecibos.size(); i++){
                    paso = "Antes de cast ReciboWrapper";
                    Utils.log(paso);
                    ReciboWrapper reciboWrap = (ReciboWrapper) infoRecibos.get(i);
                    reciboWrap.setOperacion(String.valueOf(Operacion.ACTUALIZA));
                    reciboWrap.getRecibo().setRmdbRn(Integer.parseInt(rmdbRn));
                    logger.debug("Operacion "+reciboWrap.getOperacion());
                    logger.debug("rmdbrn "+reciboWrap.getRecibo().getRmdbRn());
                    Utils.log("Operacion",reciboWrap.getOperacion());
                    Utils.log("rmdbrn",reciboWrap.getRecibo().getRmdbRn());                    
                    ice2sigsService.ejecutaWSrecibos(cdunieco, cdramo, estado, nmpoliza, nmsuplem, cdunieco, ntramite, false, user, reciboWrap);
                }
                /*for(Map<String, String> mapa:infoRecibos){
                    paso = "Antes de cast ReciboWrapper";
                    ReciboWrapper reciboWrap = (ReciboWrapper) mapa;
                    reciboWrap.setOperacion(String.valueOf(Operacion.ACTUALIZA));
                    reciboWrap.getRecibo().setRmdbRn(Integer.parseInt(rmdbRn));
                    Utils.log("rmdbrn",reciboWrap.getRecibo().getRmdbRn());
                    logger.debug(reciboWrap.getRecibo());
                }*/
            }
            paso = "Actualizando informacion de recibos en SIGS";
        }
        catch(Exception ex){
            Utils.generaExcepcion(ex, paso);
        }
    }

    public void setRecibosDAO(RecibosDAO recibosDAO) {
        this.recibosDAO = recibosDAO;
    }
}