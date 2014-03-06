package mx.com.gseguros.confpantallas.delegate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.confpantallas.base.dao.DinamicDao;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;

public class AdminControlesPredeterminados extends AbstractManagerDAO{

	public String getTablas(){
		String rgs = "";
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("operacion", "getListas");
		data.put("query", "ListadeTablasPredeterminados");
		try {
			HashMap<String, Object> dataR = new HashMap<String, Object>();
			//dataR = dnc.dispatch(data);
			System.out.println(data);
			List<Map> lts = (List<Map>) dataR.get("rgs");
			Iterator<Map> itLts = lts.iterator();
			StringBuffer strJson = new StringBuffer();
			strJson.append("{\"children\":[");
			while (itLts.hasNext()) {
				Map map = itLts.next();
				strJson.append("{\"text\":\"" + map.get("NOMBRE") + "\",\"leaf\":false, id:\"tabla_" + map.get("TABLA") + "\"},");
			}
			rgs = strJson.toString();
			rgs = rgs.substring(0, strJson.toString().length()-1);
			rgs = rgs + "]}";
			System.out.println(rgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rgs;
	}
	
	public String getInfo(String query, Boolean leaf, String idAttr, String cdramo){
		String rgs = "";
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("operacion", "getListas");
		data.put("query", query);
		data.put("cdramo", cdramo);
		data.put("tabla", "");
		try {
			DinamicDao dao = new DinamicDao();
			List<Map> lts = dao.GetListados(data);
			
			Iterator<Map> itLts = lts.iterator();
			StringBuffer strJson = new StringBuffer();
			strJson.append("{\"children\":[");
			while (itLts.hasNext()) {
				Map map = itLts.next();
				strJson.append("{\"text\":\"").append(map.get("NOMBRE")).append("\",\"leaf\":").append(leaf).append(", id:\"").append(idAttr).append("_").append(map.get("TABLA")).append("\"},");
			}
			rgs = strJson.toString();
			rgs = rgs.substring(0, strJson.toString().length()-1);
			rgs = rgs + "]}";
			System.out.println(rgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rgs;
	}
	
}
