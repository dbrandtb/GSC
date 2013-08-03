package mx.com.aon.catbo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;

import mx.com.aon.catbo.model.AsigEncuestaVO;
import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.catbo.model.EncuestaVO;
import mx.com.aon.catbo.service.ConfigurarEncuestaManager2;
import mx.com.aon.catbo.service.ConsultarAsigEncuestaManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.UserSQLDateConverter;
import mx.com.aon.portal.util.WrapperResultados;

public class ConfigurarEncuestaManagerImpl2 extends AbstractManagerJdbcTemplateInvoke implements ConfigurarEncuestaManager2{
	
	Logger logger = Logger.getLogger(ConfigurarEncuestaManagerImpl2.class);
	
	private String nmconig;
	private int start;
	private int limit;
	private int cant;
	private int i;

	public String getNmconig() {
		return nmconig;
	}

	public void setNmconig(String nmconig) {
		this.nmconig = nmconig;
	}

	@SuppressWarnings("unchecked")
	public String guardarConfigurarEncuesta(
			ConfigurarEncuestaVO configurarEncuestaVO)
			throws ApplicationException {
		PagedList  lista = null;
		HashMap map = new HashMap();
		List lista1=null;
		
		
		map.put("pv_nmconfig_i",ConvertUtil.nvl(configurarEncuestaVO.getNmConfig()));
		map.put("pv_cdunieco_i",ConvertUtil.nvl(configurarEncuestaVO.getCdUnieco()));
		map.put("pv_cdramo_i",ConvertUtil.nvl(configurarEncuestaVO.getCdRamo()));
		map.put("pv_cdelemento_i",ConvertUtil.nvl(configurarEncuestaVO.getCdElemento()));
		map.put("pv_cdproceso_i",configurarEncuestaVO.getCdProceso());
		map.put("pv_cdcampan_i",configurarEncuestaVO.getCdCampan());
		map.put("pv_cdmodulo_i",configurarEncuestaVO.getCdModulo());
		map.put("pv_cdencuesta_i",ConvertUtil.nvl(configurarEncuestaVO.getCdEncuesta()));
		
		Converter converter = new UserSQLDateConverter("");	
		map.put("pv_fedesde_i", converter.convert(java.util.Date.class, configurarEncuestaVO.getFedesde_i()));//ConvertUtil.convertToDate(configurarEncuestaVO.getFedesde_i()));//
		map.put("pv_fehasta_i", converter.convert(java.util.Date.class, configurarEncuestaVO.getFehasta_i()));//  ConvertUtil.convertToDate(configurarEncuestaVO.getFehasta_i())); // 
		
		WrapperResultados res =  returnBackBoneInvoke(map,"GUARDACONFIGURACION_ENCUESTA");
		
		nmconig= res.getResultado();
		
		if (nmconig!=""){
		   
			
			
		
	           map.put("pv_nmconfig_i", nmconig);
	     
	          WrapperResultados resultado= new WrapperResultados(); 
	    
	          PagedList respuesta = new PagedList();
	    
	 
	     
		   
		     resultado= returnBackBoneInvoke(map, "OBTIENE_POLIZA");
		     
		     return resultado.getMsgText();
		    
		}
		    /*
		    if(resultado.getItemList().size()==0)
		    {
		    	return ("No existen Pólizas para la Configuración generada");
		    }
		    
		    /*if (resultado.getMsgId().equals("200086"))
		    {
		    	return resultado.getMsgText();
		    }
		    
		    resultado.setNotPagedTotalItems( resultado.getItemList().size() );
		    if (resultado.getItemList().size() < (start + resultado.getItemList().size())) {
		          limit =  resultado.getItemList().size();
		          
		        }  else {
		          limit = start + resultado.getItemList().size();
		        }
		    resultado.setItemList( resultado.getItemList().subList(start, resultado.getItemList().size()) );
		    
		    respuesta.setItemsRangeList(resultado.getItemList());
		    respuesta.setTotalItems(resultado.getItemList().size());
	 
		    
	  	 
	    if ((respuesta!=null)&&(respuesta.getTotalItems()!=0))
	    { 	
		  List<?> elementos = respuesta.getItemsRangeList();
				
		//cant=lista.getTotalItems();
	
		  if (elementos!=null)
		  {
		     for (int j=0; j<elementos.size(); j++)
		     {	 
			    //AsigEncuestaVO asigEncuestaVO= null; 
			    AsigEncuestaVO asigEncuestaVO=(AsigEncuestaVO)elementos.get(j);
			  
			    map.put("pv_nmconfig_i",asigEncuestaVO.getNmConfig());
		        map.put("pv_cdunieco_i",asigEncuestaVO.getCdUnieco());
		        map.put("pv_cdramo_i",asigEncuestaVO.getCdRamo());
		        map.put("pv_estado_i",asigEncuestaVO.getEstado());
		        map.put("pv_nmpoliza_i",asigEncuestaVO.getNmPoliza());
		        map.put("pv_cdperson_i",asigEncuestaVO.getCdPerson());
		        map.put("pv_status_i",asigEncuestaVO.getStatus());
		        map.put("pv_cdusuario_i",asigEncuestaVO.getCdUsuario());
		              
		        

		        returnBackBoneInvoke(map,"GUARDAR_ASIGNACION_ENCUESTA");
			  } 
		  }		
	              
           
	    }*/
		
		
	  //  return resultado.getMsgText();
		
	    else {return res.getMsgText();}
	    
	}

	@SuppressWarnings("unchecked")
	public String guardarConfigurarEncuestaEditar(
			ConfigurarEncuestaVO configurarEncuestaVO)
			throws ApplicationException {
		PagedList  lista = null;
		HashMap map = new HashMap();
		List lista1=null;
		
		
		map.put("pv_nmconfig_i",ConvertUtil.nvl(configurarEncuestaVO.getNmConfig()));
		map.put("pv_cdunieco_i",ConvertUtil.nvl(configurarEncuestaVO.getCdUnieco()));
		map.put("pv_cdramo_i",ConvertUtil.nvl(configurarEncuestaVO.getCdRamo()));
		map.put("pv_cdelemento_i",ConvertUtil.nvl(configurarEncuestaVO.getCdElemento()));
		map.put("pv_cdproceso_i",configurarEncuestaVO.getCdProceso());
		map.put("pv_cdcampan_i",configurarEncuestaVO.getCdCampan());
		map.put("pv_cdmodulo_i",configurarEncuestaVO.getCdModulo());
		map.put("pv_cdencuesta_i",ConvertUtil.nvl(configurarEncuestaVO.getCdEncuesta()));
		
		Converter converter = new UserSQLDateConverter("");	
		map.put("pv_fedesde_i", converter.convert(java.util.Date.class, configurarEncuestaVO.getFedesde_i()));//ConvertUtil.convertToDate(configurarEncuestaVO.getFedesde_i()));//
		map.put("pv_fehasta_i", converter.convert(java.util.Date.class, configurarEncuestaVO.getFehasta_i()));//  ConvertUtil.convertToDate(configurarEncuestaVO.getFehasta_i())); // 
		
		WrapperResultados res =  returnBackBoneInvoke(map,"GUARDACONFIGURACION_ENCUESTA_EDITAR");
		return res.getMsgText();
      
	}
	
	
	
	public EncuestaVO getEncuestaPregunta(String cdEncuesta) throws ApplicationException
	{
		HashMap map = new HashMap();
		map.put("pv_cdencuesta_i",cdEncuesta);
		
        return (EncuestaVO)getBackBoneInvoke(map,"OBTENERREG_ENCUESTA_PREGUNTAS");
	}
	
	
	
	
	
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
}
