package mx.com.gseguros.portal.reexpedicionDocumentos.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.ObjetoBD;
import mx.com.gseguros.portal.reexpedicionDocumentos.service.ReexpedicionDocumentosManager;
import mx.com.gseguros.utils.HttpUtil;

import org.apache.struts2.ServletActionContext;

public class ReexpedicionDocumentosAction extends PrincipalCoreAction
{
	private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ReexpedicionDocumentosAction.class);
	
	private ConsultasManager              consultasManager;
	private Map<String,Item>              itemMap;
	private LinkedHashMap<String,Object>  linkedObjectMap;
	private String                        mensaje;
	private PantallasManager              pantallasManager;
	private ReexpedicionDocumentosManager reexpedicionDocumentosManager;
	private List<Map<String,String>>      stringList;
	private Map<String,String>            stringMap;
	private boolean                       success;

	public String principal()
	{
		logger.info(""
				+ "\n#########################################"
				+ "\n###### marcoReexpedicionDocumentos ######"
				);
		try
		{
			UserVO usuario     = (UserVO)session.get("USUARIO");
			String cdsisrol    = usuario.getRolActivo().getObjeto().getValue();
			String pantalla    = "MARCO_REEXPEDICION_DOCUMENTOS";
			String seccion     = "FORMULARIO";
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			List<ComponenteVO> listaComponentesFormulario = pantallasManager.obtenerComponentes(null, null, null, null, null, null, pantalla, seccion, null);
			gc.generaComponentes(listaComponentesFormulario, true, false, true, false, false, false);
			itemMap = new HashMap<String,Item>();
			itemMap.put("itemsDeFormulario",gc.getItems());
			seccion = "MODELO_POLIZA";
			List<ComponenteVO> listaComponentesModeloPoliza = pantallasManager.obtenerComponentes(null, null, null, null, null, null, pantalla, seccion, null);
			gc.generaComponentes(listaComponentesModeloPoliza, true, true, false, true, false, false);
			itemMap.put("fieldsModeloPoliza",gc.getFields());
			itemMap.put("columnasGridPolizas",gc.getColumns());
			seccion = "BOTONES_GRID";
			List<ComponenteVO> listaBotonesGrid = pantallasManager.obtenerComponentes(null, null, null, null, null, cdsisrol, pantalla, seccion, null);
			gc.generaComponentes(listaBotonesGrid, true, false, false, false, false, true);
			itemMap.put("botonesGridPolizas",gc.getButtons());
		}
		catch(Exception ex)
		{
			logger.error("error al cargar marco de reexpedicion de documentos",ex);
		}
		logger.info(""
				+ "\n###### marcoReexpedicionDocumentos ######"
				+ "\n#########################################"
				);
		return SUCCESS;
	}
	
	public String regeneraMedicinaPreventiva()
	{
		logger.info(""
				+ "\n########################################"
				+ "\n###### regeneraMedicinaPreventiva ######"
				);
		logger.info("stringMap: "+stringMap);
		try
		{
			String ntramite = stringMap.get("NTRAMITE");
			String cdunieco = stringMap.get("CDUNIECO");
			String cdramo   = stringMap.get("CDRAMO");
			String estado   = stringMap.get("ESTADO");
			String nmpoliza = stringMap.get("NMPOLIZA");
			String nmsuplem = stringMap.get("NMSUPLEM");
			String reportePagina1 = "P_1_INDIS.rdf";
			String reportePagina2 = "P_2_INDIS.rdf";
			String pdfPagina1 = "P_1_INDIS.pdf";
			String pdfPagina2 = "P_2_INDIS.pdf";
			
			String url=this.getText("ruta.servidor.reports")
					+ "?destype=cache"
					+ "&desformat=PDF"
					+ "&userid="+this.getText("pass.servidor.reports")
					+ "&report="+reportePagina1
					+ "&paramform=no"
					+ "&ACCESSIBLE=YES"
					+ "&p_unieco="+cdunieco
					+ "&p_ramo="+cdramo
					+ "&p_estado='M'"
					+ "&p_poliza="+nmpoliza
					+ "&p_suplem="+nmsuplem;
			HttpUtil.generaArchivo(url,this.getText("ruta.documentos.poliza")+"/"+ntramite+"/"+pdfPagina1);
			url=this.getText("ruta.servidor.reports")
					+ "?destype=cache"
					+ "&desformat=PDF"
					+ "&userid="+this.getText("pass.servidor.reports")
					+ "&report="+reportePagina2
					+ "&paramform=no"
					+ "&ACCESSIBLE=YES"
					+ "&p_unieco="+cdunieco
					+ "&p_ramo="+cdramo
					+ "&p_estado='M'"
					+ "&p_poliza="+nmpoliza
					+ "&p_suplem="+nmsuplem;
			HttpUtil.generaArchivo(url,this.getText("ruta.documentos.poliza")+"/"+ntramite+"/"+pdfPagina2);
			mensaje = "Documento de medicina preventiva reexpedido";
			success = true;
		}
		catch(Exception ex)
		{
			logger.error("error al regenerar medicina preventiva",ex);
			mensaje = ex.getMessage();
			success = false;
		}
		logger.info(""
				+ "\n###### regeneraMedicinaPreventiva ######"
				+ "\n########################################"
				);
		return SUCCESS;
	}
	
	public String regeneraMedicinaPreventivaEspecialista()
	{
		logger.info(""
				+ "\n####################################################"
				+ "\n###### regeneraMedicinaPreventivaEspecialista ######"
				);
		logger.info("stringMap: "+stringMap);
		try
		{
			String ntramite = stringMap.get("NTRAMITE");
			String cdunieco = stringMap.get("CDUNIECO");
			String cdramo   = stringMap.get("CDRAMO");
			String estado   = stringMap.get("ESTADO");
			String nmpoliza = stringMap.get("NMPOLIZA");
			String nmsuplem = stringMap.get("NMSUPLEM");
			String reportePagina1 = "P_1_ESPE.rdf";
			String reportePagina2 = "P_2_ESPE.rdf";
			String pdfPagina1 = "P_1_ESPE.pdf";
			String pdfPagina2 = "P_2_ESPE.pdf";
			
			String url=this.getText("ruta.servidor.reports")
					+ "?destype=cache"
					+ "&desformat=PDF"
					+ "&userid="+this.getText("pass.servidor.reports")
					+ "&report="+reportePagina1
					+ "&paramform=no"
					+ "&ACCESSIBLE=YES"
					+ "&p_unieco="+cdunieco
					+ "&p_ramo="+cdramo
					+ "&p_estado='M'"
					+ "&p_poliza="+nmpoliza
					+ "&p_suplem="+nmsuplem;
			HttpUtil.generaArchivo(url,this.getText("ruta.documentos.poliza")+"/"+ntramite+"/"+pdfPagina1);
			url=this.getText("ruta.servidor.reports")
					+ "?destype=cache"
					+ "&desformat=PDF"
					+ "&userid="+this.getText("pass.servidor.reports")
					+ "&report="+reportePagina2
					+ "&paramform=no"
					+ "&ACCESSIBLE=YES"
					+ "&p_unieco="+cdunieco
					+ "&p_ramo="+cdramo
					+ "&p_estado='M'"
					+ "&p_poliza="+nmpoliza
					+ "&p_suplem="+nmsuplem;
			HttpUtil.generaArchivo(url,this.getText("ruta.documentos.poliza")+"/"+ntramite+"/"+pdfPagina2);
			mensaje = "Documento de medicina preventiva expecialista reexpedido";
			success = true;
		}
		catch(Exception ex)
		{
			logger.error("error al regenerar medicina preventiva",ex);
			mensaje = ex.getMessage();
			success = false;
		}
		logger.info(""
				+ "\n###### regeneraMedicinaPreventivaEspecialista ######"
				+ "\n####################################################"
				);
		return SUCCESS;
	}
	
	public String obtenerDatosEmail()
	{
		logger.info(""
				+ "\n###############################"
				+ "\n###### obtenerDatosEmail ######"
				);
		logger.info("stringMap: "+stringMap);
		try
		{
			String direccionIPLocal  = ServletActionContext.getRequest().getLocalAddr();
			int    puertoLocal       = ServletActionContext.getRequest().getLocalPort();
			String contexto          = ServletActionContext.getServletContext().getServletContextName();
			String urlReporte        = "http://"+direccionIPLocal+":"+puertoLocal+"/"+contexto+"/reportes/procesoObtencionReporte.action";
			//stringMap=new HashMap<String,String>();
			stringMap.put("url",urlReporte);
			
			LinkedHashMap<String,Object> parametros = new LinkedHashMap<String,Object>();
			parametros.put("param1","1");//el id del proceso que recibe el PL para regresar los correos a cuales se debe enviar
			parametros.put("param2",stringMap.get("cdunieco"));
			List<Map<String,String>> listaEmails = consultasManager.consultaDinamica(ObjetoBD.OBTIENE_EMAIL, parametros);
			String correos = "";
			if(listaEmails!=null)
			{
				boolean primero=true;
				for(Map<String,String>email:listaEmails)
				{
					if(primero)
					{
						correos=email.get("DESCRIPL");
						primero=false;
					}	
					else
					{
						correos=correos+";"+email.get("DESCRIPL");
					}
				}
			}
			stringMap.put("correos",correos);
			success = true;
			mensaje = "Correo(s) obtenido(s)";
		}
		catch(Exception ex)
		{
			logger.error("error al obtener los datos de email de recibos subsecuentes",ex);
			success = false;
			mensaje = ex.getMessage();
		}
		logger.info(""
				+ "\n###### obtenerDatosEmail ######"
				+ "\n###############################"
				);
		return SUCCESS;
	}
	
	public void setReexpedicionDocumentosManager(
			ReexpedicionDocumentosManager reexpedicionDocumentosManager) {
		this.reexpedicionDocumentosManager = reexpedicionDocumentosManager;
	}

	public Map<String, Item> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, Item> itemMap) {
		this.itemMap = itemMap;
	}

	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<Map<String, String>> getStringList() {
		return stringList;
	}

	public void setStringList(List<Map<String, String>> stringList) {
		this.stringList = stringList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, String> getStringMap() {
		return stringMap;
	}

	public void setStringMap(Map<String, String> stringMap) {
		this.stringMap = stringMap;
	}

	public void setConsultasManager(ConsultasManager consultasManager) {
		this.consultasManager = consultasManager;
	}

	public LinkedHashMap<String, Object> getLinkedObjectMap() {
		return linkedObjectMap;
	}

	public void setLinkedObjectMap(LinkedHashMap<String, Object> linkedObjectMap) {
		this.linkedObjectMap = linkedObjectMap;
	}
}