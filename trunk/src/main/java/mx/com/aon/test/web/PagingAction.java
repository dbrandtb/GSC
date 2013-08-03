/*
 * AON
 * 
 * Creado el 22/02/2008 06:56:45 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.test.web;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.test.model.ReciboVO;
import mx.com.aon.test.service.PagingManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * PagingAction
 * 
 * <pre>
 *   Action que atiende la petición de información para la consulta de datos de 
 *   la tabla con mecanismo de paginación.
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class PagingAction extends ActionSupport {
	
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(PagingAction.class);

	/**
	 * UDI por defecto
	 */
	private static final long serialVersionUID = -1408300638184806793L;
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private PagingManager pagingManager;
	
	/**
	 * Atributo agregado como parametro de la petición por struts que indica
	 * el inicio de el número de linea en cual iniciar  
	 */
	private String start;
	
	/**
	 * Atributo agregado como parametro de la petición por struts que indica la cantidad
	 * de registros a ser consultados 
	 */
	private String limit;
	
	/**
	 * Atributo agregado como parametro de la petición por struts que indica el Id
	 * de la columna por la cual se va a ser el ordenamiento
	 */
	private String sort;
	
	/**
	 * Atributo agregado como parametro de la petición por struts que indica la dirección 
	 * (ASC o DESC) de la consulta.
	 */
	private String dir;
	
	/**
	 * Atributo de respuesta interpretado por strust con el número de registros totales
	 * que devuelve la consulta.
	 */
	private int totalCount;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	private List<ReciboVO> mreciboList;
	
	
	/**
	 * Metodo <code>execute</code> con el que es llamado desde Struts para atender la petición web.
	 * 
	 */
	public String execute() throws Exception{
		try{
			// Se actualiza el atributo limit para que contenga el numero 
			// final el registro a ser consultado
			limit = String.valueOf( Integer.valueOf(start)+Integer.valueOf(limit) );

			sort = sort.toUpperCase();
			
			// Se genra la petición a partir de los valores generados y 
			// extraidos de la petición al manager
			this.mreciboList = pagingManager.getRecibos(start, limit, sort, dir);
			
			// Se convierte el primer bean con el valor del total de registros y
			// se almacena en el atributo correspondiente
			this.totalCount = Integer.valueOf(this.mreciboList.get(0).getCount());
			
			// Se quita el primer registro ya que no contiene información.
			this.mreciboList.remove(0);
		}catch (ApplicationException e){
			logger.error(e.getMessage(),e);
		}
		// Ya que el tipo de respuesta es JSON unicamente se atiende a SUCCESS
		// el plugin de struts-json interpretara y tranformara los atributos con 
		// la respuesta.
		return SUCCESS;
	}


	// GETTERS AND SETTERS

	// Metodo de acceso utilizado por Spring para agregar el manager
	public void setPagingManager(PagingManager pagingManager) {this.pagingManager = pagingManager;}
	
	// Metodos de acceso de los atributos agregados por struts con los parametros de la consulta
	public String getStart() {return start;}
	public void setStart(String start) {this.start = start;}
	public String getLimit() {return limit;}
	public void setLimit(String limit) {this.limit = limit;}
	public String getSort() {return sort;}
	public void setSort(String sort) {this.sort = sort;}
	public String getDir() {return dir;}
	public void setDir(String dir) {this.dir = dir;}

	// Metodos de acceso de los atributos de respuesta de la consulta
	public int getTotalCount() {return totalCount;}
	public void setTotalCount(int totalCount) {this.totalCount = totalCount;}
	public List<ReciboVO> getMreciboList() {return mreciboList;}
	public void setMreciboList(List<ReciboVO> mreciboList) {this.mreciboList = mreciboList;}

}
