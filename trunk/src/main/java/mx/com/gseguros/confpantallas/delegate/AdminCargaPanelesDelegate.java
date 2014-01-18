package mx.com.gseguros.confpantallas.delegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import mx.com.gseguros.confpantallas.base.dao.DinamicDao;
import mx.com.gseguros.confpantallas.model.DinamicComboVo;
import mx.com.gseguros.confpantallas.model.DinamicData;
import mx.com.gseguros.confpantallas.model.DinamicItemBean;
import mx.com.gseguros.confpantallas.model.DinamicPanelAttrGetVo;
import mx.com.gseguros.confpantallas.model.ViewBean;
import mx.com.gseguros.confpantallas.model.ViewPanel;
import net.sf.json.JSONArray;

public class AdminCargaPanelesDelegate {
	private Logger log = Logger.getAnonymousLogger();
	ArrayList<String> mapaCmb = new ArrayList<String>();
	ArrayList<String> mapaCmbHijo = new ArrayList<String>();
	
	public String GetListadePaneles(){
		String rgs = "";
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			data.put("query", "listaPanelesExistentes");
			DinamicDao dao = new DinamicDao();
			List<DinamicComboVo> ltsP = dao.getListaCombox(data);
			JSONArray jsonObject = JSONArray.fromObject(ltsP);
			System.out.println(jsonObject);
			rgs = jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rgs;
	}
	
	public String getDataCombo(String tabla, String valor){
		String rgs = "";
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			DinamicDao dao = new DinamicDao();
			data.put("query", "listaValoresTablaApoyo");
			data.put("tabla", tabla);

			List<DinamicComboVo> ltsP = dao.getDinamicComboVo(data);
			JSONArray jsonObject = JSONArray.fromObject(ltsP);
			System.out.println(jsonObject);
			rgs = jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rgs;
		
	}
	public String getDataComboHijo(String tabla, String valor){
		String rgs = "";
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			DinamicDao dao = new DinamicDao();
			data.put("query", "listaValoresTablaApoyoHijos");
			data.put("tabla", tabla);
			data.put("valor", valor);
			List<DinamicComboVo> ltsP = dao.getDinamicComboVo(data);
			JSONArray jsonObject = JSONArray.fromObject(ltsP);
			System.out.println(jsonObject);
			rgs = jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rgs;
		
	}
	public List<DinamicData> GetListaTablas(List<String> comboNames, List<String> comboNamesHijo){
		List<DinamicData> ltsP = new ArrayList<DinamicData>();
		Iterator<String> it = comboNames.iterator();
		while (it.hasNext()) {
			DinamicData dt = new DinamicData();
			String txt =  it.next();
			dt.setNombreVar(txt);
			dt.setDescripcion(this.isSoon(comboNamesHijo, txt));
			ltsP.add(dt);
		}
		return ltsP;
	}
	public String isSoon(List<String> comboNamesHijo, String txt){
		String rgs = "llenaCombo";
		Iterator<String> it = comboNamesHijo.iterator();
		txt = "store"+txt;
		while (it.hasNext()) {
			if(txt.equals(it.next())){rgs="";break;}
		}
		return rgs;
	}
	public HashMap<String, Object> GeneraJson(String panel){
		log.info("Voy a buscar el ID del panel: "+panel);
		HashMap<String, Object> rgs = new HashMap<String, Object>();
		List<ViewBean> listViewBean = new ArrayList<ViewBean>();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("operacion", "getListas");
		data.put("panel", panel);
		data.put("query", "IdPanel");
		List<HashMap> listaIdPanel = this.GetLista(data);
		
		List<ViewPanel> ltsPaneles = new ArrayList<ViewPanel>();

		Integer cP = 0;
		Iterator<HashMap> itLpf = listaIdPanel.listIterator();
		while(itLpf.hasNext()) {
			Map rowI = itLpf.next();
			Integer idP = Integer.valueOf(rowI.get("IDPANEL").toString());
			Integer idT = Integer.valueOf(rowI.get("IDPADRE").toString());
			log.info("Trabajando con el panel: "+idP);
			log.info("Su panel papa es:::::::: "+idT);
			if(cP != idT){
				ViewPanel vP = new ViewPanel();
				vP.setId(rowI.get("IDPANEL").toString());
				vP.setIdPadre(rowI.get("IDPADRE").toString());
				cP = idT;
				data.clear();
				data.put("operacion", "getListas");
				data.put("id", idP);
				data.put("query", "AttrPanel");
				vP.setListaAttr(this.GetListaDinamicPanelAttrGetVo(data));
				vP.setListaCtrls(new ArrayList<String>());
				ltsPaneles.add(vP);
			}else{
				Iterator<ViewPanel> itPnl = ltsPaneles.iterator();
				while (itPnl.hasNext()) {
					ViewPanel viewPanel =  itPnl.next();
					if(viewPanel.getIdPadre().equals(rowI.get("IDPADRE").toString())){
						viewPanel.getListaCtrls().add(rowI.get("IDPANEL").toString());
						break;
					}
				}
				
			}
		}
		Iterator<ViewPanel> itPnl = ltsPaneles.iterator();
		while (itPnl.hasNext()) {
			ViewPanel viewPanel =  itPnl.next();
			Integer idP = Integer.valueOf(viewPanel.getId());
			ViewBean pnl = new ViewBean();
			pnl.setNombreVar("miVar"+this.getValorAttr(viewPanel.getListaAttr(), "id"));
			pnl.setCodigo(this.transToJson(viewPanel.getListaAttr(),idP, viewPanel.getListaCtrls(), "con"));
			pnl.setXtype(this.getValorAttr(viewPanel.getListaAttr(), "xtype"));
			listViewBean.add(pnl);
		}
		rgs.put("lista", listViewBean);
		rgs.put("listaCmb", mapaCmb);
		rgs.put("listaCmbHijo", mapaCmbHijo);
		return rgs;
	} 
	
	public String transToJson(List<DinamicPanelAttrGetVo> listaAttrPanel, Integer idP, ArrayList<String> listaCtrls, String cabecero){
		StringBuffer strJson = new StringBuffer();
		String rgs = "";
		String tipo = this.getValorAttr(listaAttrPanel, "xtype");
		if(cabecero.equals("con")){
			if(tipo.equals("form")){
				strJson.append("Ext.form.Panel({");
			}else if(tipo.equals("tabpanel")){
				strJson.append("Ext.TabPanel({");
			}else if(tipo.equals("window")){
				strJson.append("Ext.Window({");
			}else if(tipo.equals("panel")){
				strJson.append("Ext.Panel({");
			}
		}else{
			strJson.append("{");
		}
		
		Iterator<DinamicPanelAttrGetVo> lts = listaAttrPanel.iterator();
		while (lts.hasNext()) {
			DinamicPanelAttrGetVo attrLts = lts.next();
			strJson.append(attrLts.getAttr()).append(":");
			strJson.append(this.setStrAttr(attrLts.getValor(), attrLts.getTipo()));
			strJson.append(",");
		}
		ArrayList<Object> ltsCtrl = null;
		
		if(tipo.equals("form") || tipo.equals("tabpanel") || tipo.equals("panel") || tipo.equals("window")){
			strJson.append("items:[");
			//Conocer el numero y orden de controles ligados al panel
			ltsCtrl = this.transControlesAttrToJson(idP);
			strJson.append(ltsCtrl.get(0));
			Iterator<String> itlistaCtrls = listaCtrls.iterator();
			StringBuffer tmpStr = new StringBuffer();
			while (itlistaCtrls.hasNext()){
				String pnl = itlistaCtrls.next();
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("operacion", "getListas");
				data.put("id", pnl);
				data.put("query", "AttrPanel");
				tmpStr.append(this.transToJson(this.GetListaDinamicPanelAttrGetVo(data),Integer.parseInt(pnl), new ArrayList<String>(),"")).append(",");
				System.out.println(tmpStr.toString());
				System.out.println("pausa");
			}
			rgs = tmpStr.toString();
			if(!rgs.equals("")){
				rgs = rgs.substring(0, tmpStr.toString().length()-1);
				strJson.append(rgs);
			}
		}
		
		if(((List) ltsCtrl.get(1)).size() > 0){
			StringBuffer strConBtn = new StringBuffer(); 
			strConBtn.append("],buttons: [");
			Iterator<List> itlts = ((List) ltsCtrl.get(1)).iterator();
			while (itlts.hasNext()) {
				ArrayList<DinamicPanelAttrGetVo> ltsAttr = (ArrayList)itlts.next();
				strConBtn.append(this.getStrJsonAttrCtrol(ltsAttr,"",null,0));
			}
			rgs = strConBtn.toString();
			rgs = rgs.substring(0, strConBtn.toString().length()-1);
			rgs += "]"; 
			strJson.append(rgs);
		}else{
			strJson.append("]");
		}
		if(cabecero.equals("con")){
			strJson.append("});");
		}else{
			strJson.append("}");
		}
		rgs = strJson.toString();
		log.info(rgs);
		return rgs;
	}
	
	public ArrayList<Object> transControlesAttrToJson(Integer idP){
		ArrayList<Object> rgs = new ArrayList();
		StringBuffer strCtrlAttrJson = new StringBuffer();
		List<List> ltsCtrlAttrButton = new ArrayList<List>();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("operacion", "getListas");
		data.put("panelID", idP);
		data.put("query", "ListaIdControles");
		List<HashMap> listaIdControles = this.GetLista(data);
		
		log.info("Numero de controles a insertar: " + listaIdControles.size());
		Iterator<HashMap> itCA = listaIdControles.listIterator();
		while(itCA.hasNext()) {
			Map rowI = itCA.next();
			Integer idCtrl = Integer.valueOf(rowI.get("IDCONTROL").toString());
			String strCtrl = rowI.get("DESCRIPCION").toString();
			Integer rowCtrl = 0;
			Boolean isBton = false;
			if(strCtrl.indexOf("button") > -1){
				isBton = true;
				rowCtrl = this.IsFormularioConBotones(idCtrl, idP);
			}
			//Voy por los atributos del control
			data.clear();
			data.put("operacion", "getListas");
			data.put("idControl", idCtrl);
			data.put("idPanel", idP);
			data.put("query", "AttrControl");
			List<DinamicPanelAttrGetVo> listaAttrControl = this.GetListaDinamicPanelAttrGetVo(data);
			if((isBton) && rowCtrl > 0){
				ltsCtrlAttrButton.add(listaAttrControl);
			}else{
				log.info("Listado de atributos del control: " + listaAttrControl);
				strCtrlAttrJson.append(this.getStrJsonAttrCtrol(listaAttrControl,strCtrl,listaIdControles,idP));
			}
		}
		String rgsC = strCtrlAttrJson.toString();
		if(!rgsC.equals("")){
			rgsC = rgsC.substring(0, strCtrlAttrJson.toString().length()-1);
		}

		log.info("String total de atributos de controles: " + rgsC);	
		rgs.add(rgsC);
		rgs.add(ltsCtrlAttrButton);
		return rgs;
	}
	
	private String getStrJsonAttrCtrol(List<DinamicPanelAttrGetVo> listaAttrControl, String ctrl, List<HashMap> listaIdControles,Integer idP){
		StringBuffer stB = new StringBuffer();
		stB.append("{");
		Iterator<DinamicPanelAttrGetVo> lts = listaAttrControl.iterator();
		StringBuffer rgsAttr = new StringBuffer();
		while (lts.hasNext()) {
			DinamicPanelAttrGetVo attrLts = lts.next();
			if(!attrLts.getAttr().equals("row")){//Excluyo atributo....
				if(attrLts.getAttr().equals("store")){
					//voy a validar que exista el strore para que no truene en tiempo de construccion
					String nameStore = attrLts.getValor();
					if(!this.existeStore(nameStore).equals("")){
						rgsAttr.append(attrLts.getAttr()).append(":");
						rgsAttr.append("store"+nameStore);
						rgsAttr.append(",");
						mapaCmb.add(nameStore);
					}
				}else if (attrLts.getAttr().equals("estilo")){
					String attrG = this.setStrAttr(attrLts.getValor(), attrLts.getTipo());
					if(attrLts.getValor().equals("reset")){
						rgsAttr.append("handler").append(":").append("function() {this.up('form').getForm().reset();}").append(",");
					}else if (attrLts.getValor().equals("submit")){
						rgsAttr.append("formBind: true,");
						rgsAttr.append("handler: function() {var form = this.up('form').getForm();if (form.isValid()) {form.submit({success: function(form, action) {window.open('../../jsp/confpantallas/newpanel.jsp');},failure: function(form, action) {Ext.Msg.alert('Failed', 'fallo');}});}}");
						rgsAttr.append(",");
					}
				}else if (attrLts.getAttr().equals("isPadre")){
					String idCmb = this.getValorAttr(listaAttrControl, "isPadre");
					String strCmb = this.getAttrControl(idCmb, "name", listaIdControles, idP, "id");
					String strStore = "store"+this.getAttrControl(idCmb, "name", listaIdControles, idP, "store");
					mapaCmbHijo.add(strStore);
					rgsAttr.append("\n");
					rgsAttr.append("listeners").append(":");
					rgsAttr.append("{").append("select: function (combo, record){ ");
					rgsAttr.append("\n");
					rgsAttr.append("Ext.getCmp('").append(strCmb).append("').reset();");
					rgsAttr.append("\n");
					//rgsAttr.append(strStore).append(".clearFilter(true);");
					//rgsAttr.append("\n");
					//rgsAttr.append(strStore).append(".filter('key', combo.value);");
					//rgsAttr.append("\n");
					rgsAttr.append(strStore+".load({params:{valor:combo.value,tarea: 'llenaComboHijo'}});");
					rgsAttr.append("\n");
					
					rgsAttr.append("Ext.getCmp('").append(strCmb).append("').enable();");
					rgsAttr.append("\n");
					rgsAttr.append("}").append("}");
					rgsAttr.append(",");
				}else{
					rgsAttr.append(attrLts.getAttr()).append(":");
					rgsAttr.append(this.setStrAttr(attrLts.getValor(), attrLts.getTipo()));
					rgsAttr.append(",");
				}
			}
		}
		String rgs = rgsAttr.toString();
		rgs = rgs.substring(0, rgsAttr.toString().length()-1);
		stB.append(rgs);
		if(ctrl.indexOf("combobox") > -1){
			String name = this.getValorAttr(listaAttrControl, "name");
			stB.append(isComboboxHijo(name,listaIdControles,idP));
		}
		stB.append("},");
		log.info("Total de atributos del Control: " + stB.toString());
		return stB.toString();
	}
	private String getAttrControl(String control,String attrBusca,List<HashMap> listaIdControles,Integer idP,String attr){
		String rgs = "";
		HashMap<String, Object> data = new HashMap<String, Object>();
		Iterator<HashMap> itCA = listaIdControles.listIterator();
		while(itCA.hasNext()) {
			Map rowI = itCA.next();
			Integer idCtrl = Integer.valueOf(rowI.get("IDCONTROL").toString());
			data.clear();
			data.put("operacion", "getListas");
			data.put("idControl", idCtrl);
			data.put("idPanel", idP);
			data.put("query", "AttrControl");
			List<DinamicPanelAttrGetVo> listaAttrControl = this.GetListaDinamicPanelAttrGetVo(data);
			String isp = this.getValorAttr(listaAttrControl, attrBusca);
			if(control.equals(isp)){
				rgs = this.getValorAttr(listaAttrControl, attr);
				break;
			}
		}
		return rgs;
	}
	private String isComboboxHijo(String name,List<HashMap> listaIdControles,Integer idP){
		String rgs = "";
		HashMap<String, Object> data = new HashMap<String, Object>();
		Iterator<HashMap> itCA = listaIdControles.listIterator();
		while(itCA.hasNext()) {
			Map rowI = itCA.next();
			Integer idCtrl = Integer.valueOf(rowI.get("IDCONTROL").toString());
			data.clear();
			data.put("operacion", "getListas");
			data.put("idControl", idCtrl);
			data.put("idPanel", idP);
			data.put("query", "AttrControl");
			List<DinamicPanelAttrGetVo> listaAttrControl = this.GetListaDinamicPanelAttrGetVo(data);
			String isp = this.getValorAttr(listaAttrControl, "isPadre");
			if(name.equals(isp)){
				rgs = ",disabled:true";
				break;
			}
		}
		return rgs;
	}
	
	private Integer IsFormularioConBotones(Integer idC,Integer idP){
		Integer rgs = 0;
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("operacion", "getListas");
		data.put("panelID", idP);
		data.put("controlID", idC);
		data.put("query", "getTipoBoton");
		List<HashMap> dato = this.GetLista(data);
		Map mapD = dato.get(0);
		rgs = Integer.parseInt(mapD.get("VALOR").toString());
		log.info("El row del boton es: " + rgs);
		return rgs;
	}
	
	private String setStrAttr(String valor, String tipo ){
		String rgs = "";
		if("S".equals(tipo)){
			rgs = "'"+valor+"'";
		}else{
			rgs = valor;
		}
		return rgs;
	}
	
	private String existeStore(String name){
		String rgs = "";
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			DinamicDao dao = new DinamicDao();
			data.put("operacion", "analizaStore");
			data.put("store", name);
			data.put("query", "nameTablaApoyoData");
			List<DinamicItemBean> ltsP = dao.getDinamicItemBean(data);
			//JSONArray jsonObject = JSONArray.fromObject(ltsP);
			//System.out.println(jsonObject);
			//rgs = jsonObject.toString();
			if(ltsP.size() > 0){
				rgs = "existe";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rgs;
	}
	
	private String getValorAttr(List<DinamicPanelAttrGetVo> listaAttrPanel, String attr){
		String rgs = "";
		Iterator<DinamicPanelAttrGetVo> lts = listaAttrPanel.iterator();
		while (lts.hasNext()) {
			DinamicPanelAttrGetVo attrLts = lts.next();
			if(attrLts.getAttr().equals(attr)){
				rgs = attrLts.getValor();
				break;
			}
		}
		return rgs;
	}
	
	
	
	public List<DinamicPanelAttrGetVo> GetListaDinamicPanelAttrGetVo (HashMap<String, Object> data){
		List<DinamicPanelAttrGetVo> rgs = new ArrayList<DinamicPanelAttrGetVo>();
		try {
			DinamicDao dao = new DinamicDao();
			HashMap<String, String> mapa = new HashMap<String, String>();
			if (data.get("id") != null){mapa.put("id", data.get("id").toString());}
			if (data.get("idPanel") != null){mapa.put("idPanel", data.get("idPanel").toString());}
			if (data.get("idControl") != null){mapa.put("idControl", data.get("idControl").toString());}
			mapa.put("query", data.get("query").toString());
			rgs = (List<DinamicPanelAttrGetVo>) dao.getDinamicPanelAttrGetVo(mapa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rgs;
	}
	public List<HashMap> GetLista (HashMap<String, Object> data){
		List<HashMap> rgs = new ArrayList<HashMap>();
		try {
			DinamicDao dao = new DinamicDao();
			HashMap<String, String> mapa = new HashMap<String, String>();
			if (data.get("panel") != null){mapa.put("panel", data.get("panel").toString());}
			if (data.get("panelID") != null){mapa.put("panelID", data.get("panelID").toString());}
			if (data.get("controlID") != null){mapa.put("controlID", data.get("controlID").toString());}
			mapa.put("query", data.get("query").toString());
			rgs = (List<HashMap>) dao.GetListadosHM(mapa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rgs;
	}
	

}

























