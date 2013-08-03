/**
 * 
 */

/**
 * 
 */
package mx.com.aon.catbo.util;

import mx.com.aon.catbo.model.ConfigurarEstructuraVO;
import mx.com.aon.portal.service.PagedList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author CIMA_USR
 *
 * @deprecated Remover esta clase porfavor, ya existe Util.obtenerListaPaginada (dacevedo) 
 *
 * Clase que contiene métodos estáticos.
 */
public class ConfigurarEstructuraUtilidad {

	/**
	 * Método estático que permite obtener una lista<EstructuraVO> paginada pasada por parámetros
	 * junto a los atributos start y limit.
	 * 
	 * @param start
	 * @param limit
	 * @param lista
	 * @return
	 * 
	 * @deprecated Este metodo ya no se utilizara mas, utilizaremos 
	 * la version que retorna WrapperResultados de Util.obtenerListaPaginada
	 */
	public static PagedList obtenerListaPaginada(int start, int limit,List<ConfigurarEstructuraVO> lista) {
		PagedList pagedList = new PagedList();

		pagedList.setTotalItems(lista.size());
		Iterator<ConfigurarEstructuraVO> iterConfigurarEstructura = lista.iterator();
		List<ConfigurarEstructuraVO> listaResultado = new ArrayList<ConfigurarEstructuraVO>();
		int i = 0;
		while(iterConfigurarEstructura!= null && iterConfigurarEstructura.hasNext() && i<Integer.valueOf(limit).intValue())
		{
			ConfigurarEstructuraVO estructura = iterConfigurarEstructura.next();
			if(i>= Integer.valueOf(start).intValue())
			{
				listaResultado.add(estructura);
			}
			i++;
		}
		pagedList.setItemsRangeList(listaResultado);
		return pagedList;
	}
}
