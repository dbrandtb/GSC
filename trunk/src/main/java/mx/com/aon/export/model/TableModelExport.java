/*
 * AON
 * 
 * Creado el 26/02/2008 12:15:41 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.export.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TableModelExport
 * 
 * <pre>
 *     Modelo general de la informacion a exportarse en las vistas
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class TableModelExport {

	private String[] columnName;
	
	private int columnCount = -1;

	private int rowCount = -1;

	private List<?> information = null;
	
	/**
	 * Se indica el arreglo con el nombre de las columnas a ser utilizadas
	 * @param columnName arreglo con los nombre de las columnas
	 */
	public void setColumnName( String[] columnName ){
		this.columnName = columnName;
		this.columnCount = columnName.length;
        //workaround en caso de que venga nula la lista de elementos de la consulta
        if (information == null) {
          ArrayList listOne = new ArrayList();
          for (int i=0; i< columnCount ; i++) {
              listOne.add("");
          }
         ArrayList listResult = new ArrayList();
         listResult.add(listOne);
         information = listResult;
        }
	}
	
	/**
	 * Extrae el nombre de la columna indicandole el indice de esta 
	 * @param column indice de columna
	 * @return Nombre de la columna indicada
	 */
	public String getColumnName(int column) {
		if( column < 0 || columnName == null || columnName.length <= column){
			return null;
		}
		return columnName[column];
	}

	/**
	 * Extrae el renglon a ser utilizado.
	 * @param row
	 * @return ArrayList
	 */
	public ArrayList<?> getRow(int row) {
		return (ArrayList<?>)information.get(row);
	}

	/**
	 * Extrae el numero de columnas
	 * @return int
	 */
	public int getColumnCount() {
		if( columnCount != -1 ){
			return columnCount;
		}
		if( columnName == null ){
			return -1;
		}
		return columnName.length;
	}

	/**
	 * Extrae el número de renglones
	 * @return int
	 */
	public int getRowCount() {
		if( rowCount != -1 ){
			return rowCount;
		}
		return information.size();
	}

	/**
	 * Se indica el numero de columnas
	 * @param columnCount
	 */
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	/**
	 * Se indica el numero de renglones
	 * @param rowCount
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * Ingresa la informacion al modelo
	 * @param information
	 */
	public void setInformation(List<?>information) {
        if (information != null) {
		this.information = information;
		this.rowCount = information.size();
	}
    }

}
