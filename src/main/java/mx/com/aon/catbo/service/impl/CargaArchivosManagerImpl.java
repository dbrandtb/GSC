package mx.com.aon.catbo.service.impl;


import org.apache.log4j.Logger;


import mx.com.aon.catbo.model.MediaTO;
import mx.com.aon.catbo.model.FileVO;
import mx.com.aon.catbo.service.CargaArchivosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import java.io.FilenameFilter;

import java.io.*;  

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.*;
import java.util.*;
import java.io.File;


public class CargaArchivosManagerImpl extends AbstractManagerJdbcTemplateInvoke implements CargaArchivosManager {

    protected static Logger logger = Logger.getLogger(CargaArchivosManagerImpl.class);

    public PagedList obtenerArchivos(String directorio, String fechaDesde, String fechaHasta,int start, int limit) throws ApplicationException {
        
    	 String fdt = "dd/MM/yyyy"; //mascara que utiliza
	         
		 File myDir = new File(directorio);
	        // Define a filter for java source files beginning with F
	     //FilenameFilter select = new FileListFilter("", "SYS"); //esto para futuro para cargar archivos con cierta extension   
	       
	     File[] contents = null;
	     contents = myDir.listFiles();
	     PagedList pagedList = new PagedList();
	              
	        pagedList.setItemsRangeList(new ArrayList());       
	       
	        try{
	           
	        SimpleDateFormat sdf = new SimpleDateFormat(fdt);
	        sdf.setLenient(false);
	        Date feDesde = new Date();
	        Date feHasta = new Date();
	        
	        if (contents != null) {
	        	//si traemos filtro entre fechas llenamos la lista con todos los archivos que se encontraron en el directorio entre esas fechas
	        	if(((!fechaDesde.equals(""))&&(fechaDesde != null)) || ((!fechaHasta.equals(""))&&(fechaHasta != null))){
	        		if ((!fechaDesde.equals(""))&&(fechaDesde != null)) //si viene algo hacemos parseo sino nada porque se produce excepcion
	        				feDesde = sdf.parse(fechaDesde);
	    	        if ((!fechaHasta.equals(""))&&(fechaHasta != null)) //si viene algo hacemos parseo sino nada porque se produce excepcion
	    	        		feHasta = sdf.parse(fechaHasta);
	    	        Calendar feHastaCalendar = new GregorianCalendar();
	    	        Calendar feDesdeCalendar = new GregorianCalendar();
	    	        feDesdeCalendar.setTime(feDesde); //buscamos desde las 00:00 de la fecha desde 
	    	        feHastaCalendar.setTime(feHasta);
	    	        //buscamos desde
	    	        feDesdeCalendar.set(Calendar.HOUR_OF_DAY, 00);  // en la fecha hasta buscamos hasta las 23:59 de la fecha hasta
	    	        feDesdeCalendar.set(Calendar.MINUTE, 00);
	    	        feDesdeCalendar.set(Calendar.SECOND, 00);
	    	        //hasta
	    	        feHastaCalendar.set(Calendar.HOUR_OF_DAY, 23);  // en la fecha hasta buscamos hasta las 23:59 de la fecha hasta
	    	        feHastaCalendar.set(Calendar.MINUTE, 59);
	    	        feHastaCalendar.set(Calendar.SECOND, 59);
	    	        
	    	        
	    	   	for (File file : contents) {                     
                    if(file.isFile()) //si es archivo
                    {  Date ultimaModificacion = new Date(file.lastModified());
                       Calendar ultimaModificacionCalendar = new GregorianCalendar();
                       ultimaModificacionCalendar.setTime(ultimaModificacion);
                    
                    FileVO fileVO= new FileVO();
                    fileVO.setNombre(file.toString());   //fileVO.setNombre(file.toString().replaceAll("\\\\", "\\\\\\\\"));
                    fileVO.setUltimaModificacion(new Date(file.lastModified()).toString());
                    fileVO.setDirectorio(file.getAbsolutePath());
                    	logger.debug("desde"+feDesdeCalendar.toString()+ "ultimaModificacion" + ultimaModificacion.toString()+ "hasta" + feHastaCalendar.toString());
                    // si las dos fechas vienen con datos
                    if(((!fechaDesde.equals(""))&&(fechaDesde != null)) && ((!fechaHasta.equals(""))&&(fechaHasta != null))){
                    	if (( ultimaModificacionCalendar.compareTo(feDesdeCalendar) >= 0)&&( ultimaModificacionCalendar.compareTo(feHastaCalendar) <= 0))
                            {pagedList.getItemsRangeList().add(fileVO);}
                    }
                    // si la fecha desde viene con datos y la fecha hasta NO viene con datos
                     if((!fechaDesde.equals(""))&&(fechaDesde != null)&&(fechaHasta.equals(""))){    
                        if ( ultimaModificacionCalendar.compareTo(feDesdeCalendar) >= 0)
                        {pagedList.getItemsRangeList().add(fileVO);}
                    }
                    // si la fecha hasta viene con datos y la fecha desde NO viene con datos
                     if ((!fechaHasta.equals(""))&&(fechaHasta != null)&& (fechaDesde.equals(""))){
                        if ( ultimaModificacionCalendar.compareTo(feHastaCalendar) <= 0)
                        {pagedList.getItemsRangeList().add(fileVO);}
                    }
                 
	        		} //fin si es archivo
	    	   	  } //fin for
	        	} //sino traemos filtro entre fechas llenamos la lista con todos los archivos que se encontraron en el directorio
	        	else{
	        		for (File file : contents){
	        			if(file.isFile())
	        				 {FileVO fileVO= new FileVO();
	        				 fileVO.setNombre(file.toString()); //fileVO.setNombre(file.toString().replaceAll("\\\\", "\\\\\\\\"));
                        	 fileVO.setUltimaModificacion(new Date(file.lastModified()).toString());
                        	 fileVO.setDirectorio(file.getAbsolutePath());
	        				 pagedList.getItemsRangeList().add(fileVO);}
	        		}
	        		
	        	}
        
          }
	        
	      } 
	        catch (ParseException e){
	            
	        }
	        catch (IllegalArgumentException e){
	       
	            }
	        logger.debug("contents" + contents.length);
	        if (pagedList.getItemsRangeList().size() == 0){
	            logger.debug("No se encontraron datos");
	            throw new ApplicationException("No se encontraron datos");
	        }

	        
        return pagedList;
    }

	public String guardaArchivos(final InputStream inputStream, String directorio, String nombre) throws ApplicationException, Exception{
		
    	int caracter; 
        int fin_archivo = -1; 
        // Creamos el flujo
        OutputStream outputStream=null;
 
        try{
        // aca va al directorio donde guardamos
        outputStream = new FileOutputStream( directorio+"/"+nombre); 
        
        // Realizamos operaciones de entrada y salida 
        caracter = inputStream.read(); 
        while( caracter != fin_archivo ) { 
          outputStream.write( caracter ); 
           caracter = inputStream.read();  
        	}
        
        }
        finally {
        	      if (inputStream != null) {
        	        inputStream.close();
        	      }
        	      if (outputStream != null) {
        	        outputStream.close();
        	      }
       }
				
		WrapperResultados res =  new WrapperResultados();
		//esto lo hice hardcode para probar
		res.setMsgId("200019");
		res.setMsgText("El archivo se ha cargado correctamente");
		
	      return res.getMsgText();
		  
	      
	}

	
	
	/*public PagedList obtieneAtributosFax(String pv_dsarchivo_i, int start, int limit) throws ApplicationException{
    	HashMap map = new HashMap();
        map.put("pv_dsarchivo_i", pv_dsarchivo_i);
                
        String endpointName = "OBTIENE_ATRIBUTOS_FAX";
      
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }*/
		
}	
	


