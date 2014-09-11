package test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.ws.autosgs.model.EmisionAutosVO;
import mx.com.gseguros.ws.folioserviciopublico.client.jaxws.FolioWS;
import mx.com.gseguros.ws.folioserviciopublico.client.jaxws.FolioWSService;
import mx.com.gseguros.ws.folioserviciopublico.client.jaxws.RequestFolio;
import mx.com.gseguros.ws.folioserviciopublico.client.jaxws.ResponseFolio;
import mx.com.gseguros.ws.folioserviciopublico.service.EmisionAutosService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("testAction")
@Scope("prototype")
/*
@Namespace("/test")
@ResultPath(value="/")
*/
public class TestAction extends PrincipalCoreAction {

	private static final long serialVersionUID = -3861435458381281429L;
	
	private static Logger logger = Logger.getLogger(TestAction.class);

	@Autowired
	private transient EmisionAutosService emisionAutosService;
	
	private Map<String, String> params;
	
	private EmisionAutosVO emisionAutos;
	
	private ResponseFolio responseFolio;
	
	/*
	@Action(value="invocaServicioCotizacionAutos",
		results={@Result(name="success", type="json")}
	)
	*/
	public String invocaServicioCotizacionAutos() throws Exception {
		
		logger.debug("params=" + params);
		
		//System.out.println("params==" + params);
		
		emisionAutos = emisionAutosService.cotizaEmiteAutomovilWS(
				params.get("cdunieco"), params.get("cdramo"), 
				params.get("estado"), params.get("nmpoliza"), 
				params.get("nmsuplem"), params.get("nmtramite"),
				params.get("cdtipsit"),
				(UserVO) session.get("USUARIO"));
		
		return SUCCESS;
	}
	
	
	public String invocaServiciosFolioAutos() throws Exception {
		
		logger.debug("params=" + params);
		System.out.println("params==" + params);
		
		FolioWSService service = null;
        try {
            // Creamos el servicio con el WSDL:
            URL wsdlLocation = new URL("http://10.1.1.134:8000/folioserviciopublico-ws/servicios?wsdl");
            String targetNamespace="http://com.gs.folioserviciopublico.soap.folio";
            String name="FolioWSService";
            service = new FolioWSService( wsdlLocation, new QName(targetNamespace, name));
            //service = new FolioWSService();
            FolioWS port = service.getFolioWSPort();
            
            // Añadimos capacidades de seguridad a la llamada:
            //BindingProvider provider = (BindingProvider) port;
            //provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "user");   
            //provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "12345678");
            
            // Generamos los Datos de entrada:
            RequestFolio req = new RequestFolio();
            req.setNumFolio( Integer.parseInt(params.get("numfolio")) ); // 984452
            req.setSucursalAdmin( Integer.parseInt(params.get("sucursaladmin")) ); //120 
            
            // Invocamos el WS:
            responseFolio = port.validarFolio(req);
            
            /*
            ResponseFolioVO respVO = new ResponseFolioVO();
            respVO.setFolio(resp.getFolio());
            respVO.setCodigo(resp.getCodigo());
            respVO.setExito(resp.isExito());
            respVO.setMensaje(resp.getMensaje());
            */
            
            // Mostramos el resultado:
            //System.out.println("resultado gral=" + ToStringBuilder.reflectionToString(resp, ToStringStyle.MULTI_LINE_STYLE));
            //System.out.println("resultado folio=" + ToStringBuilder.reflectionToString(resp.getFolio(), ToStringStyle.MULTI_LINE_STYLE));
            
        } catch (MalformedURLException e ) {
            e.printStackTrace();
        }
		
		return SUCCESS;
	}

	
	//Getters and setters:
	
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public EmisionAutosVO getEmisionAutos() {
		return emisionAutos;
	}

	public void setEmisionAutos(EmisionAutosVO emisionAutos) {
		this.emisionAutos = emisionAutos;
	}

	public ResponseFolio getResponseFolio() {
		return responseFolio;
	}

	public void setResponseFolio(ResponseFolio responseFolio) {
		this.responseFolio = responseFolio;
	}
	
}