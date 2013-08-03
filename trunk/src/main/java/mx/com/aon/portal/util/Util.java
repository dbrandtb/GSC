/**
 * 
 */
package mx.com.aon.portal.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import mx.com.aon.portal.service.PagedList;

/**
 * @author dacevedo
 * Clase que contiene metodos estaticos de utilidad
 */
public class Util {

    private static Map contentTypeMap = new HashMap();

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


    public static String getContentType(String format) {
        return (String)contentTypeMap.get(format);
    }

    static {
        contentTypeMap.put("PDF","application/pdf");
        contentTypeMap.put("CSV","application/excel");
        contentTypeMap.put("XML","application/xml");
        contentTypeMap.put("DOM","text/html");
        contentTypeMap.put("TXT","text/plain");
        contentTypeMap.put("XLS","application/excel");
        contentTypeMap.put("RTF","application/rtf");
        contentTypeMap.put("JPG","image/jpeg");
    }
}
