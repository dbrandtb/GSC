package mx.com.aon.catbo.service.impl;

import org.apache.log4j.Logger;

import mx.com.aon.catbo.model.ConfigurarCompraTiempoVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.MediaTO;
import mx.com.aon.catbo.service.ArchivosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArchivosManagerImpl extends AbstractManagerJdbcTemplateInvoke implements ArchivosManager {

    protected static Logger logger = Logger.getLogger(ArchivosManagerImpl.class);

    public PagedList obtenerArchivos(String pv_nmcaso_i, String pv_nmovimiento_i, int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_nmcaso_i", pv_nmcaso_i);
        map.put("pv_nmovimiento_i", pv_nmovimiento_i);
        String endpointName = "OBTIENE_ARCHIVOS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }

	public String borrarArchivos(String pv_nmcaso_i, String pv_nmovimiento_i, String pv_nmarchivo_i) throws ApplicationException {
		
		    HashMap map = new HashMap();
	        map.put("pv_nmcaso_i", pv_nmcaso_i);
	        map.put("pv_nmovimiento_i", pv_nmovimiento_i);
	        map.put("pv_nmarchivo_i", pv_nmarchivo_i);
	        
	        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_ARCHIVOS");
	        return res.getMsgText();
	}

	public String guardaArchivos(String pv_nmcaso_i, String pv_nmovimiento_i,
			String pv_nmarchivo_i, String pv_dsarchivo_i, String pv_cdtipoar_i, String pv_blarchivo_i, String pv_cdusuario_i)
			throws ApplicationException {
		
		   HashMap map = new HashMap();
	        map.put("pv_nmcaso_i", pv_nmcaso_i);
	        map.put("pv_nmovimiento_i", pv_nmovimiento_i);
	        map.put("pv_nmarchivo_i", pv_nmarchivo_i);
	        map.put("pv_dsarchivo_i", pv_dsarchivo_i);
	        map.put("pv_cdtipoar_i", pv_cdtipoar_i);
	        map.put("pv_blarchivo_i", pv_blarchivo_i);
	        map.put("pv_cdusuario_i", pv_cdusuario_i);
	        
	        
	        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_ARCHIVOS");
	        return res.getMsgText();
	}
	
	
	//Administración de Faxes - Pantalla CatBo_TatriFax//

	public PagedList obtieneAtributosFax(String pv_dsarchivo_i, int start, int limit) throws ApplicationException{
    	HashMap map = new HashMap();
        map.put("pv_cdarchivo_i", pv_dsarchivo_i);
                
        String endpointName = "OBTIENE_ATRIBUTOS_FAX";
      
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }
	
	public FaxesVO getObtieneAtributosFax(String pv_cdtipoar_i, String pv_cdatribu_i) throws ApplicationException{
    	HashMap map = new HashMap();
        map.put("pv_cdtipoar_i", pv_cdtipoar_i);
        map.put("pv_cdatribu_i", pv_cdatribu_i);
          
        String endpointName = "OBTIENE_ATRIBUTOS_FAX_REG";
      
        return (FaxesVO)getBackBoneInvoke(map, endpointName);
    }
	

	public String borrarAtributosFax(String pv_cdtipoar_i, String pv_cdatribu_i) throws ApplicationException {
		
		    HashMap map = new HashMap();
		    map.put("pv_cdtipoar_i", pv_cdtipoar_i);
	        map.put("pv_cdatribu_i", pv_cdatribu_i);
	       
	        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_ATRIBUTOS_FAX");
	        return res.getMsgText();
	}
	
	
	public String guardarAtributosFax(String pv_cdtipoar_i, String pv_cdatribu_i,
			String pv_dsatribu_i, String pv_swformat_i, String pv_nmlmax_i, String pv_nmlmin_i, String pv_swobliga_i,
			String pv_ottabval_i, String pv_status_i)throws ApplicationException {

			HashMap map = new HashMap();
			map.put("pv_cdtipoar_i", pv_cdtipoar_i); 
	        map.put("pv_cdatribu_i", pv_cdatribu_i);	        
			map.put("pv_dsatribu_i", pv_dsatribu_i);
			map.put("pv_swformat_i", pv_swformat_i);
	        map.put("pv_nmlmax_i", pv_nmlmax_i);
	        map.put("pv_nmlmin_i", pv_nmlmin_i);

	        String _pv_swobliga_i= (pv_swobliga_i=="false")?"0":"1";
	        map.put("pv_swobliga_i", _pv_swobliga_i);
	        map.put("pv_ottabval_i", pv_ottabval_i);
	       // String _pv_status_i= (pv_status_i.equals("activo"))?"1":"0";
	        map.put("pv_status_i", pv_status_i);
	        
	        
	        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_ATRIBUTOS_FAX");
	        return res.getMsgText();
	}
	
	
	public String actualizarAtributosFax(String pv_cdtipoar_i, String pv_cdatribu_i,
			String pv_dsatribu_i, String pv_swformat_i, String pv_nmlmax_i, String pv_nmlmin_i, String pv_swobliga_i,
			String pv_ottabval_i, String pv_status_i)throws ApplicationException {

			HashMap map = new HashMap();
			map.put("pv_cdtipoar_i", pv_cdtipoar_i); 
	        map.put("pv_cdatribu_i", pv_cdatribu_i);	        
			map.put("pv_dsatribu_i", pv_dsatribu_i);
			map.put("pv_swformat_i", pv_swformat_i);
	        map.put("pv_nmlmax_i", pv_nmlmax_i);
	        map.put("pv_nmlmin_i", pv_nmlmin_i);

	        String _pv_swobliga_i= (pv_swobliga_i==null)?"0":"1";
	        map.put("pv_swobliga_i", _pv_swobliga_i);
	        map.put("pv_ottabval_i", pv_ottabval_i);
	       // String _pv_status_i= (pv_status_i.equals("activo"))?"1":"0";
	        map.put("pv_status_i", pv_status_i);
	        
	        
	        WrapperResultados res =  returnBackBoneInvoke(map,"ACTUALIZAR_ATRIBUTOS_FAX");
	        return res.getMsgText();
	}
	
	public String editarGuardarAtributosFax(String pv_cdtipoar_i, String pv_cdatribu_i,
			String pv_dsatribu_i, String pv_swformat_i, String pv_nmlmax_i, String pv_nmlmin_i, String pv_swobliga_i,
			String pv_ottabval_i, String pv_status_i)throws ApplicationException {

			HashMap map = new HashMap();
			map.put("pv_cdtipoar_i", pv_cdtipoar_i); 
	        map.put("pv_cdatribu_i", pv_cdatribu_i);	        
			map.put("pv_dsatribu_i", pv_dsatribu_i);
			map.put("pv_swformat_i", pv_swformat_i);
	        map.put("pv_nmlmax_i", pv_nmlmax_i);
	        map.put("pv_nmlmin_i", pv_nmlmin_i);
	        map.put("pv_swobliga_i", pv_swobliga_i);
	        map.put("pv_ottabval_i", pv_ottabval_i);
	        map.put("pv_status_i", pv_status_i);
	        
	        WrapperResultados res =  returnBackBoneInvoke(map,"EDITAR_GUARDAR_ATRIBUTOS_FAX");
	        return res.getMsgText();
	}
	

	public TableModelExport getModel(String pv_dsarchivo_i) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("pv_cdarchivo_i", pv_dsarchivo_i);
				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_ATRIBUTOS_FAX_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Atributo","Descripcion","Máximo","Mínimo","Obligatorio","Tabla","Estado","Formato"});
		return model;

   }
	
    /*lista.add(rs.getString("CDTIPOAR"));
    lista.add(rs.getString("DSARCHIVO"));
    lista.add(rs.getString("CDATRIBU"));
    lista.add(rs.getString("DSATRIBU"));
    lista.add(rs.getString("SWFORMAT"));
    lista.add(rs.getString("NMLMAX"));
    lista.add(rs.getString("NMLMIN"));
    lista.add(rs.getString("SWOBLIGA"));
    lista.add(rs.getString("DSTABLA"));
    lista.add(rs.getString("OTTABVAL"));
    lista.add(rs.getString("STATUS"));*/
  //  lista.add(rs.getString("FORMATO"));

	
	
	

	public PagedList buscarAtributosArchivos(String pv_dsarchivo_i, int start,int limit) throws ApplicationException {
		// TODO Auto-generated method stub
        HashMap map = new HashMap();
        map.put("pv_dsarchivo_i", pv_dsarchivo_i);
        
        String endpointName = "BUSCAR_ATRIBUTOS_ARCHIVOS";
        
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }

	
	public String guardaAtributosArchivos(String pv_cdtipoar_i,String pv_cdatribu_i, String pv_swformat_i, String pv_nmlmax_i,
			String pv_nmlmin_i, String pv_swobliga_i, String pv_status_i) throws ApplicationException {
		// TODO Auto-generated method stub
		HashMap map = new HashMap();
		
		map.put("pv_cdtipoar_i", pv_cdtipoar_i);
		map.put("pv_cdatribu_i", pv_cdatribu_i);
		map.put("pv_swformat_i", pv_swformat_i);
		map.put("pv_nmlmax_i", pv_nmlmax_i);
		map.put("pv_nmlmin_i", pv_nmlmin_i);
		map.put("pv_swobliga_i", pv_swobliga_i);
		map.put("pv_status_i", pv_status_i);
        WrapperResultados res =  returnBackBoneInvoke(map,"AGREGAR_ATRIBUTOS_ARCHIVOS");
		
		return null;
	}

	public TableModelExport getModel2(String dsArchivo) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		List lista = null;
		HashMap map = new HashMap();
		
		map.put("pv_dsarchivo_i", dsArchivo);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map,"OBTENER_ATRIBUTOS_ARCHIVOS_EXPORT");
        model.setInformation(lista);
		model.setColumnName(new String[]{"Tipo","Atributo","Formato","Mínimo","Máximo","Obligatorio","Estado"});

		     //	{"cdTipoar","cdAtribu","swFormat","nmLmin","nmLmax","swObliga","status"}
		
		return model;
	}

	public String guardaArchivosCaso(MediaTO mediaTO, InputStream inputStream, final int contentLength)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}   
	
	
	

}
