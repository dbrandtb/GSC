/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.general.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;

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
					if(descripcion==null||descripcion.length()==0)
					{
						descripcion="(no especificado)";
					}
					String mezcla=nombre+"#_#"+descripcion;
					slist1.get(i).put("liga",mezcla);
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
