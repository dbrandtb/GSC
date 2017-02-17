package mx.com.gseguros.portal.mesacontrol.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.portal.mesacontrol.service.MesaControlManager;
import mx.com.gseguros.utils.Utils;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class MesaControlManagerImpl implements MesaControlManager
{
	private static Logger logger = Logger.getLogger(MesaControlManagerImpl.class);
	private MesaControlDAO mesaControlDAO;
	
	@Autowired
	private PantallasDAO pantallasDAO ;
	
	@Override
	public String cargarCdagentePorCdusuari(String cdusuari)throws Exception
	{
		logger.info(""
				+ "\n#######################################"
				+ "\n###### cargarCdagentePorCdusuari ######"
				+ "\ncdusuari: "+cdusuari);
		String cdagente=mesaControlDAO.cargarCdagentePorCdusuari(cdusuari);
		logger.info(""
				+ "\ncdagente: "+cdagente
				+ "\n###### cargarCdagentePorCdusuari ######"
				+ "\n#######################################");
		return cdagente;
	}
	
	@Override
	public void movimientoDetalleTramite(
			String ntramite
			,Date feinicio
			,String cdclausu
			,String comments
			,String cdusuari
			,String cdmotivo
			,String cdsisrol
			,String swagente
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movimientoDetalleTramite @@@@@@"
				,"\n@@@@@@ ntramite=" , ntramite
				,"\n@@@@@@ feinicio=" , feinicio
				,"\n@@@@@@ cdclausu=" , cdclausu
				,"\n@@@@@@ comments=" , comments
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdmotivo=" , cdmotivo
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ swagente=" , swagente
				));
		
		mesaControlDAO.movimientoDetalleTramite(ntramite, feinicio, cdclausu, comments, cdusuari, cdmotivo, cdsisrol, swagente, null, null);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ movimientoDetalleTramite @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void validarAntesDeTurnar(
    		String ntramite
    		,String status
    		,String cdusuari
    		,String cdsisrol
    		)throws Exception
    {
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ validarAntesDeTurnar @@@@@@"
				,"\n@@@@@@ ntramite=" , ntramite
				,"\n@@@@@@ status="   , status
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				));
		
		mesaControlDAO.validarAntesDeTurnar(ntramite,status,cdusuari,cdsisrol);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ validarAntesDeTurnar @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
    }
	
	@Deprecated
	@Override
	public String movimientoTramite(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdsucadm
			,String cdsucdoc
			,String cdtiptra
			,Date ferecepc
			,String cdagente
			,String referencia
			,String nombre
			,Date festatus
			,String status
			,String comments
			,String nmsolici
			,String cdtipsit
			,String cdusuari
			,String cdsisrol
			,String swimpres
			,String cdtipflu
			,String cdflujomc
			,Map<String,String>valores, String cdtipsup
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movimientoTramite @@@@@@"
				,"\n@@@@@@ cdunieco="   , cdunieco
				,"\n@@@@@@ cdramo="     , cdramo
				,"\n@@@@@@ estado="     , estado
				,"\n@@@@@@ nmpoliza="   , nmpoliza
				,"\n@@@@@@ nmsuplem="   , nmsuplem
				,"\n@@@@@@ cdsucadm="   , cdsucadm
				,"\n@@@@@@ cdsucdoc="   , cdsucdoc
				,"\n@@@@@@ cdtiptra="   , cdtiptra
				,"\n@@@@@@ ferecepc="   , ferecepc
				,"\n@@@@@@ cdagente="   , cdagente
				,"\n@@@@@@ referencia=" , referencia
				,"\n@@@@@@ nombre="     , nombre
				,"\n@@@@@@ festatus="   , festatus
				,"\n@@@@@@ status="     , status
				,"\n@@@@@@ comments="   , comments
				,"\n@@@@@@ nmsolici="   , nmsolici
				,"\n@@@@@@ cdtipsit="   , cdtipsit
				,"\n@@@@@@ cdusuari="   , cdusuari
				,"\n@@@@@@ cdsisrol="   , cdsisrol
				,"\n@@@@@@ swimpres="   , swimpres
				,"\n@@@@@@ cdtipflu="   , cdtipflu
				,"\n@@@@@@ cdflujomc="  , cdflujomc
				,"\n@@@@@@ valores="    , valores
				,"\n@@@@@@ cdtipsup="   , cdtipsup
				));
		
		String ntramite = mesaControlDAO.movimientoMesaControl(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsuplem
				,cdsucadm
				,cdsucdoc
				,cdtiptra
				,ferecepc
				,cdagente
				,referencia
				,nombre
				,festatus
				,status
				,comments
				,nmsolici
				,cdtipsit
				,cdusuari
				,cdsisrol
				,swimpres
				,cdtipflu
				,cdflujomc
				,valores
				,cdtipsup
				);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ ntramite=",ntramite
				,"\n@@@@@@ movimientoTramite @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return ntramite;
	}
	
	@Override
	public void guardarRegistroContrarecibo(String ntramite,String cdusuari)throws Exception
	{
		mesaControlDAO.guardarRegistroContrarecibo(ntramite,cdusuari);
	}
	
	@Override
	public void actualizarNombreDocumento(String ntramite,String cddocume,String nuevo)throws Exception
	{
		mesaControlDAO.actualizarNombreDocumento(ntramite,cddocume,nuevo);
	}
	
	@Override
	public void borrarDocumento(String ntramite,String cddocume)throws Exception
	{
		mesaControlDAO.borrarDocumento(ntramite,cddocume);
	}
	
	@Override
	public void borraDomicilioAsegSiCodposCambia(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdpos)throws Exception
	{
		mesaControlDAO.borraDomicilioAsegSiCodposCambia(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsuplem
				,cdpos);
	}
	
	public void setMesaControlDAO(MesaControlDAO mesaControlDAO) {
		this.mesaControlDAO = mesaControlDAO;
	}
	
	@Override
	public void marcarTramiteVistaPrevia(String ntramite) throws Exception
	{
		mesaControlDAO.marcarTramiteVistaPrevia(ntramite);
	}
	
	@Override
	public void movimientoExclusionUsuario(String usuario, String accion) throws Exception
	{
		mesaControlDAO.movimientoExclusionUsuario(usuario, accion);
	}
	
	@Override
	public Map<String, Item> pantallaExclusionTurnados(String cdsisrol) throws Exception
	{
		
		logger.debug(
				Utils.log("INICIO cdsisrol >>> pantallaExclusionTurnados",cdsisrol)
				);
		String paso ="  ";
		Map<String, Item> resultado = new HashMap<String, Item>();
		try {
			
			paso = "Recuperando componentes";
			List<ComponenteVO> componentesPantalla = pantallasDAO.obtenerComponentes(null, null, null, null, null, cdsisrol, "EXCLU_TURNADOS", "COMBO_USUARIO", null);
			
			paso= "Generando Componentes";
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(componentesPantalla, true, false, true, false, false, false);
			resultado.put("comboUsuario", gc.getItems());
			
		}
		catch(Exception e){
			Utils.generaExcepcion(e, paso);
		}
		
		logger.debug(
				Utils.log("FIN cdsisrol >>> pantallaExclusionTurnados",cdsisrol)
				);
		return resultado;
	}
	
	public boolean regeneraReporte(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem, String cddocume, String nmsituac, String nmcertif, String cdmoddoc) throws Exception{
		return mesaControlDAO.regeneraReporte(cdunieco, cdramo, estado, nmpoliza, nmsuplem, cddocume, nmsituac, nmcertif, cdmoddoc);
	}

	public boolean regeneraDocumentosEndoso(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem) throws Exception{
		return mesaControlDAO.regeneraDocumentosEndoso(cdunieco, cdramo, estado, nmpoliza, nmsuplem);
	}
	
	
	@Override
	public void regeneraReverso(String ntramite, String cdsisrol,String cdusuari) throws Exception{
		
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ regeneraReverso @@@@@@"
				,"\n@@@@@@ ntramite=" , ntramite
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				));
		
		String paso = null;
		try
		{
			
		 mesaControlDAO.regeneraReverso(ntramite, cdsisrol, cdusuari) ;
	
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ regeneraReverso @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}	
    
    @Override
    @Deprecated
    public void validaDuplicidadTramiteEmisionPorNmsolici (String cdunieco, String cdramo, String estado, String nmsolici) throws Exception {
        mesaControlDAO.validaDuplicidadTramiteEmisionPorNmsolici(cdunieco, cdramo, estado, nmsolici);
    }
}
