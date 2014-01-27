/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.general.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.utils.HttpUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author Jair
 */
public class SubirArchivoAction extends PrincipalCoreAction implements ServletRequestAware{

    private HttpServletRequest servletRequest;
    private File file;
    private String fileFileName;
    private String fileContentType;
    private String targetId;
    private org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(SubirArchivoAction.class);
    private String uploadKey;
    private float progreso;
    private String progresoTexto;
    private Map<String,String>smap1;
    private KernelManagerSustituto kernelManager;
    SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
    private List<Map<String,String>>slist1;
    private boolean success;
    
    public String mostrarPanel()
    {
        return SUCCESS;
    }
    
    public String jsonObtenerProgreso()
    {
        CustomProgressListener listener=(CustomProgressListener) session.get("SK_PROGRESS_LISTENER");
        if(listener.getPercentDone()==100)
        {
            progresoTexto="Terminado";
        }
        else
        {
            progresoTexto="Cargando ("+listener.getPercentDone()+"%)...";
        }
        progreso=(Float.parseFloat(listener.getPercentDone()+""))/100f;
        return SUCCESS;
    }
    
    public String mostrarBarra()
    {
        return SUCCESS;
    }
    
    public String ponerLlaveSesion()
    {
        session.put("SK_LLAVE_ULTIMO_ARCHIVO",uploadKey);
        log.debug("Se puso la ultima llave en sesion: "+session.get("SK_LLAVE_ULTIMO_ARCHIVO"));
        return SUCCESS;
    }
    
    public String subirArchivo()
    {
    	log.debug("smap1 "+smap1);
        log.debug("file "+file);
        log.debug("fileFileName "+fileFileName);
        log.debug("fileContentType "+fileContentType);
        
        try
        {
        	String nombreArchivo=System.currentTimeMillis()+"_"+((long)(Math.random()*10000l))+"."+fileFileName.substring(fileFileName.indexOf(".")+1);
        	String nuevaRuta=this.getText("ruta.documentos.poliza")+"/"+smap1.get("ntramite")+"/"
                +nombreArchivo;
        	String antiguaRuta=file.getAbsolutePath();
        	log.debug("se movera desde::: "+antiguaRuta);
            log.debug("se movera a    ::: "+nuevaRuta);
            
            String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+smap1.get("ntramite");
            File carpeta = new File(rutaCarpeta);
            if(!carpeta.exists())
            {
            	log.debug("no existe la carpeta::: "+rutaCarpeta);
            	carpeta.mkdir();
            	if(carpeta.exists())
            	{
            		log.debug("carpeta creada");
            	}
            	else
            	{
            		log.debug("carpeta NO creada");
            	}
            }
            else
            {
            	log.debug("existe la carpeta   ::: "+rutaCarpeta);
            }
            if(file.renameTo(new File(nuevaRuta)))
    		{
    			log.debug("archivo movido");	
    		}
    		else
    		{
    			log.debug("archivo NO movido");
    		}
            /*
            pv_cdunieco_i
            pv_cdramo_i
            pv_estado_i
            pv_nmpoliza_i
            pv_nmsuplem_i
            pv_feinici_i
            pv_cddocume_i
            pv_dsdocume_i
            */
            Map<String,Object>paramMovDocu=new LinkedHashMap<String,Object>(0);
            paramMovDocu.put("pv_cdunieco_i" , smap1.get("cdunieco"));
            paramMovDocu.put("pv_cdramo_i"   , smap1.get("cdramo"));
            paramMovDocu.put("pv_estado_i"   , smap1.get("estado"));
            paramMovDocu.put("pv_nmpoliza_i" , smap1.get("nmpoliza"));
            paramMovDocu.put("pv_nmsolici_i" , smap1.get("nmsolici"));
            paramMovDocu.put("pv_nmsuplem_i" , smap1.get("nmsuplem"));
            paramMovDocu.put("pv_ntramite_i" , smap1.get("ntramite"));
            paramMovDocu.put("pv_feinici_i"  , renderFechas.parse(smap1.get("fecha")));
            paramMovDocu.put("pv_cddocume_i" , nombreArchivo);
            paramMovDocu.put("pv_dsdocume_i" , smap1.get("descripcion"));
            paramMovDocu.put("pv_tipmov_i"   , smap1.get("tipomov"));
            paramMovDocu.put("pv_swvisible_i"   , null);
            kernelManager.guardarArchivo(paramMovDocu);
        }
        
        catch(Exception ex)
        {
        	log.error("error al mover el archivo",ex);
        }
        return SUCCESS;
    }

	public String ventanaDocumentosPolizaLoad()
	{
		logger.debug(""
				+ "\n#########################################"
				+ "\n#########################################"
				+ "\n###### ventanaDocumentosPolizaLoad ######"
				+ "\n######                             ######"
				+ "\n######                             ######"
				);
		try
		{
			/*
			pv_cdunieco_i
			pv_cdramo_i
			pv_estado_i
			pv_nmpoliza_i
			pv_registro_o
			pv_msg_id_o
			pv_title_o
			*/
			Map<String,Object>paramGetDocu=new LinkedHashMap<String,Object>(0);
			Iterator it=smap1.entrySet().iterator();
			while(it.hasNext())
			{
				Entry e=(Entry) it.next();
				paramGetDocu.put((String)e.getKey(),e.getValue());
			}
			slist1=kernelManager.obtenerDocumentosPoliza(paramGetDocu);
			if(slist1!=null&&slist1.size()>0)
			{
				for(int i=0;i<slist1.size();i++)
				{
					String nombre=slist1.get(i).get("cddocume");
					String descripcion=slist1.get(i).get("dsdocume");
					String tipmov     =slist1.get(i).get("tipmov");
					String nmsuplem   =slist1.get(i).get("nmsuplem");
					String nsuplogi   =slist1.get(i).get("nsuplogi");
					if(descripcion==null||descripcion.length()==0)
					{
						descripcion="(no especificado)";
					}
					String mezcla=nombre+"#_#"+descripcion;
					slist1.get(i).put("liga",mezcla);
					slist1.get(i).put("orden",nmsuplem+"#_#"+tipmov+"#_#"+nsuplogi);
				}
			}
			success=true;
		}
		catch(Exception ex)
		{
			success=false;
		}
		logger.debug(""
				+ "\n######                             ######"
				+ "\n######                             ######"
				+ "\n###### ventanaDocumentosPolizaLoad ######"
				+ "\n#########################################"
				+ "\n#########################################"
				);
		return SUCCESS;
	}
    
	////////////////////////////////////////////////
	////// generar contrarecibo de documentos //////
	/*////////////////////////////////////////////*/
	public String generarContrarecibo()
	{
		logger.debug(""
				+ "\n#################################"
				+ "\n#################################"
				+ "\n###### generarContrarecibo ######"
				+ "\n######                     ######"
				);
		logger.debug("smap1: "+smap1);
		
		SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	    Calendar calendarHoy=Calendar.getInstance();
		
		try
		{
			UserVO usu=(UserVO)session.get("USUARIO");
			DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usu.getUser());
			
			long timestamp=System.currentTimeMillis();
			String random=((long)(Math.random()*10000l))+"";
			/*String claveReciboTabla="contrarecibo_"+slist1.get(0).get("pv_ntramite_i")+"_"+timestamp+"_"+random;
			logger.debug("la clave para guardar en BD es: "+claveReciboTabla);
			for(Map<String,String> docu:slist1)
			{
				docu.put("pv_cdconrec_i",claveReciboTabla);
				kernelManager.preparaContrarecibo(docu);
			}*/
			String filePath=this.getText("ruta.documentos.poliza")+"/"+smap1.get("ntramite")+"/contrarecibo_"+timestamp+"_"+random+".pdf";
			String requestUrl=this.getText("ruta.servidor.reports")
					+ "?destype=cache"
					+ "&desformat=PDF"
					+ "&userid="+this.getText("pass.servidor.reports")
					+ "&report=CONTRA_RECIBO.rdf"
					+ "&paramform=no"
					+ "&ACCESSIBLE=YES"
					+ "&p_fecha="+renderFechas.format(calendarHoy.getTime())
					+ "&p_tramite="+smap1.get("ntramite")
					+ "&p_usuario="+datUsu.getCdusuari()
					+ "&desname="+filePath;
			logger.debug("se pide el contrarecibo a: "+requestUrl);
			logger.debug("se guardara el contrarecibo en: "+filePath);
			HttpUtil.generaArchivo(requestUrl, filePath);
			uploadKey="contrarecibo_"+timestamp+"_"+random+".pdf";
			success=true;
		}
		catch(Exception ex)
		{
			logger.error("error al generar contrarecibo",ex);
			success=false;
		}
		logger.debug(""
				+ "\n######                     ######"
				+ "\n###### generarContrarecibo ######"
				+ "\n#################################"
				+ "\n#################################"
				);
		return SUCCESS;
	}

	public String habilitaSigRecibo(){
		logger.debug("Entrando a habilitar siguiente recibo");
		try{
			progresoTexto = kernelManager.habilitaSigRecibo(smap1);
			success=false;
			
			logger.debug("Mensaje de habilitar recibos: " + progresoTexto);
			
			if(StringUtils.isBlank(progresoTexto)){
				progresoTexto="Error al habilitar el recibo subsecuente.";
			} else if("3".equals(progresoTexto)){
				progresoTexto = "No existen recibos por habilitar.";
			} else {
				progresoTexto="Se ha habilitado el recibo subsecuente.";
				success=true;
			}
		}catch(Exception ex){
			logger.error("Error al habilitar el recibo subsecuente.",ex);
			progresoTexto = ex.getMessage();
			success=false;
		}
		
		
		return SUCCESS;
	}
	
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
    public void setServletRequest(HttpServletRequest hsr) {
        this.servletRequest=hsr;
    }

    public File getFile() {
        return file;
    }

    public String getProgresoTexto() {
        return progresoTexto;
    }

    public void setProgresoTexto(String progresoTexto) {
        this.progresoTexto = progresoTexto;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getUploadKey() {
        return uploadKey;
    }

    public void setUploadKey(String uploadKey) {
        this.uploadKey = uploadKey;
    }

    public float getProgreso() {
        return progreso;
    }

    public void setProgreso(float progreso) {
        this.progreso = progreso;
    }

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
    
}
