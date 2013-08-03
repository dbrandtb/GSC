/**
 * 
 */
package mx.com.aon.catbo.util;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.catbo.service.PagedList;

/**
 * @author dacevedo
 * Clase que contiene metodos estaticos de utilidad
 */
public class Util {

	/**
	 * Metodo estatico que permite obtener una lista paginada pasada por parámetros
	 * junto a los atributos start y limit.
	 * 
	 * @param start
	 * @param limit
	 * @param lista
	 * @return Retorna una instancia de un objeto PagedList
	 * 
	 * @deprecated Este metodo ya no sera necesario utilizaremos 
	 *  AbstractManager.pagedBackBoneInvoke, que no requiere este metodo
	 */
	@SuppressWarnings("unchecked")
	public static PagedList obtenerListaPaginada(int start, int limit,List lista) {
		PagedList pagedList = new PagedList();

		pagedList.setTotalItems(lista.size());
		List listaResultado = new ArrayList();

		listaResultado = lista.subList(start, limit);
		
		pagedList.setItemsRangeList(listaResultado);
		return pagedList;
	}
}
