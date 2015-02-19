package test;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.ws.autosgs.dao.AutosDAOSIGS;
import mx.com.gseguros.ws.autosgs.emision.model.EmisionAutosVO;
import mx.com.gseguros.ws.folioserviciopublico.client.jaxws.FolioWS;
import mx.com.gseguros.ws.folioserviciopublico.client.jaxws.FolioWSService;
import mx.com.gseguros.ws.folioserviciopublico.client.jaxws.RequestFolio;
import mx.com.gseguros.ws.folioserviciopublico.client.jaxws.ResponseFolio;

import org.apache.log4j.Logger;
//import org.exolab.castor.types.Date;
import org.springframework.beans.factory.annotation.Autowired;


/*
@Namespace("/test")
@ResultPath(value="/")
*/
public class TestAction extends PrincipalCoreAction {

	private static final long serialVersionUID = -3861435458381281429L;
	
	private static Logger logger = Logger.getLogger(TestAction.class);

//	@Autowired
//	private transient EmisionAutosService emisionAutosService;
	
	private Map<String, String> params;
	
	private EmisionAutosVO emisionAutos;
	
	private ResponseFolio responseFolio;
	
	@Autowired
	private AutosDAOSIGS autosDAOSIGS;
	
	/*
	@Action(value="invocaServicioCotizacionAutos",
		results={@Result(name="success", type="json")}
	)
	*/
	public String invocaServicioCotizacionAutos() throws Exception {
		
		logger.debug("prueba para SP AUTOS" + params);
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("Sucursal"        , "4");
			params.put("Ramo"            , "711");
			params.put("Poliza"          , "121");
			params.put("TipoEndoso"      , " ");
			params.put("NumeroEndoso"    , "0");
			params.put("Recibo"          , "2");
			params.put("TotalRecibos"    , "2");
			params.put("PrimaNeta"       , "7384.748");
			params.put("Iva"             , "1286.077");
			params.put("Recargo"         , "273.235");
			params.put("Derechos"        , "380");
			params.put("CesionComision"  , "0");
			params.put("ComisionPrima"   , "738.4748");
			params.put("ComisionRecargo" , "27.3235");
			params.put("FechaInicio"     , sdf.format(new Date()));
			params.put("FechaTermino"    , sdf.format(new Date()));
			
			Integer res = autosDAOSIGS.insertaReciboAuto(params);
			
		} catch (Exception e){
			logger.error("Error en Envio Recibo Auto: " + e.getMessage(),e);
		}
		
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
            FolioWS port = service.getFolioWSPort();
            
            RequestFolio req = new RequestFolio();
            req.setNumFolio( Integer.parseInt(params.get("numfolio")) ); // 984452
            req.setSucursalAdmin( Integer.parseInt(params.get("sucursaladmin")) ); //120 
            
            // Invocamos el WS:
            responseFolio = port.validarFolio(req);
            
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