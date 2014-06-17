package mx.com.gseguros.confpantallas.delegate;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import mx.com.gseguros.confpantallas.base.dao.DinamicDao;
import mx.com.gseguros.confpantallas.model.DinamicComboVo;
import mx.com.gseguros.confpantallas.model.DinamicTatriVo;


public class AdminBuscaControlDelegate {

	public String gatDataControl(String ramo, String descripcion, String query){

		String rgs = "";
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			data.put("operacion", "getListas");
			data.put("query", query);
			data.put("cdramo", ramo);
			data.put("descripcion", descripcion);
			DinamicDao dao = new DinamicDao();
			List<DinamicTatriVo> ltsP = dao.getDatosControlTatrisit(data);
			JSONArray jsonObject = JSONArray.fromObject(ltsP);
			System.out.println(jsonObject);
			rgs = jsonObject.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rgs;
		
		
		
		
		
		
		
		

		
	}
}
