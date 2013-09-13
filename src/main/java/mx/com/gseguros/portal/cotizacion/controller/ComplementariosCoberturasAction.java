package mx.com.gseguros.portal.cotizacion.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.gseguros.portal.cotizacion.model.Item;

public class ComplementariosCoberturasAction extends PrincipalCoreAction{

	private KernelManagerSustituto kernelManager;
	private Map<String,String>smap1;
	private Map<String,String>smap2;
	private Map<String,String>smap3;
	private Map<String,Object>omap1;
	private Map<String,Object>omap2;
	private Map<String,Object>omap3;
	private List<Map<String,String>>slist1;
	private List<Map<String,String>>slist2;
	private List<Map<String,Object>>olist1;
	private List<Map<String,Object>>olist2;
	private Item item1;
	private Item item2;
	private Item item3;
	private Item item4;
	private Map<String,String> spanel1;
	private Map<String,String> spanel2;
	private Map<String,String> spanel3;
	private Map<String,Object> opanel1;
	private Map<String,Object> opanel2;
	private Map<String,Object> opanel3;
	private Logger log=Logger.getLogger(ComplementariosCoberturasAction.class);
	private boolean success=false;
	private Map<String,String>parametros;
	
	public String pantallaCoberturas()
	{
		log.debug("smap1: "+smap1);
		ScreenInterceptor scrInt=new ScreenInterceptor();
		return scrInt.intercept(this, ScreenInterceptor.PANTALLA_COMPLEMENTARIOS_COBERTURAS_ASEGURADO);
	}
	
	public String mostrarPantallaCoberturas()
	{
		try
		{
			//modelo para cargar datos
			item1=new Item("fields",null,Item.ARR);
			item1.add(Item.crear(null,null,Item.OBJ).add("name","spanel1.asegurado"));
			item1.add(Item.crear(null,null,Item.OBJ).add("name","spanel1.producto"));
			
			//campos de formulario
			item2=new Item("items",null,Item.ARR);
			item2.add(Item.crear(null,null,Item.OBJ)
					.add(Item.crear("xtype",			"textfield"))
					.add(Item.crear("readOnly",			true))
					.add(Item.crear("name",				"spanel1.asegurado"))
					.add(Item.crear("fieldLabel",		"Asegurado"))
					.add(Item.crear("style",		    "margin:5px;"))
					);
			item2.add(Item.crear(null,null,Item.OBJ)
					.add(Item.crear("xtype",			"textfield"))
					.add(Item.crear("readOnly",			true))
					.add(Item.crear("name",				"spanel1.producto"))
					.add(Item.crear("fieldLabel",		"Producto"))
					.add(Item.crear("style",		    "margin:5px;"))
					);
		}
		catch(Exception ex)
		{
			log.error("error al armar la pantalla de coberturas",ex);
			item1=null;
			item2=null;
		}
		return SUCCESS;
	}
	
	public String cargarPantallaCoberturas()
	{
		try
		{
			/*
			PL:
			pv_cdunieco_i   input
			pv_cdramo_i     input
			pv_estado_i     input
			pv_nmpoliza_i   input
			pv_nmsituac_i   input
			*/
			slist1=kernelManager.obtenerCoberturasUsuario(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			spanel1=null;
			log.error("error al cargar la pantalla de coberturas",ex);
			success=false;
		}
		return SUCCESS;
	}
	
	public String guardarCoberturasUsuario()
	{
		log.debug(smap1);
		log.debug(slist1);
		try
		{
			int i=1;
			for(Map<String,String> cob:slist1)
			{
				/**poligar
				PL:
				pv_cdunieco_i   smap1 ready!
	            pv_cdramo_i     smap1 ready!
				pv_estado_i     smap1 ready!
				pv_nmpoliza_i   smap1 ready!
				pv_nmsituac_i   smap1 ready!
				pv_cdgarant_i   iterado GARANTIA
				pv_nmsuplem_i   #0
				pv_cdcapita_i   iterado CDCAPITA
				pv_status_i     iterado status
				pv_cdtipbca_i   iterado cdtipbca
				pv_ptvalbas_i   iterado ptvalbas
				pv_swmanual_i   iterado swmanual
				pv_swreas_i     iterado swreas
				pv_cdagrupa_i   iterado cdagrupa
				PV_ACCION       #I
				*/
				Map<String,String>mapPoligarIterado=new HashMap<String,String>(0);
				mapPoligarIterado.putAll(smap1);
				mapPoligarIterado.put("pv_cdgarant_i", cob.get("GARANTIA"));
				mapPoligarIterado.put("pv_nmsuplem_i", "0");
				mapPoligarIterado.put("pv_cdcapita_i", cob.get("CDCAPITA"));
				mapPoligarIterado.put("pv_status_i",   cob.get("status"));
				mapPoligarIterado.put("pv_cdtipbca_i", cob.get("cdtipbca"));
				mapPoligarIterado.put("pv_ptvalbas_i", cob.get("ptvalbas"));
				mapPoligarIterado.put("pv_swmanual_i", cob.get("swmanual"));
				mapPoligarIterado.put("pv_swreas_i",   cob.get("swreas"));
				mapPoligarIterado.put("pv_cdagrupa_i", cob.get("cdagrupa"));
				mapPoligarIterado.put("PV_ACCION",     "I");
				log.debug("Iteracion #"+i+" de movPoligar");
				kernelManager.movPoligar(mapPoligarIterado);
				
				/**policap
				PL:
				pv_cdunieco_i   smap1 ready!
	            pv_cdramo_i     smap1 ready!
	            pv_estado_i     smap1 ready!
	            pv_nmpoliza_i   smap1 ready!
	            pv_nmsituac_i   smap1 ready!
	            pv_cdcapita_i   iterado CDCAPITA
	            pv_nmsuplem_i   #0
	            pv_status_i     iterado status
	            pv_ptcapita_i   SOLO NUMERICO iterado SUMA_ASEGURADA
	            pv_ptreduci_i   iterado ptreduci
	            pv_fereduci_i   iterado fereduci
	            pv_swrevalo_i   iterado swrevalo
	            pv_cdagrupa_i   iterado cdagrupa
	            pv_accion_i     #I
				*/
				Map<String,String>mapPolicapIterado=new HashMap<String,String>(0);
				mapPolicapIterado.putAll(smap1);
				mapPolicapIterado.put("pv_cdcapita_i", cob.get("CDCAPITA"));
				mapPolicapIterado.put("pv_nmsuplem_i", "0");
				mapPolicapIterado.put("pv_status_i", cob.get("status"));
				String ptcapita;
				try
				{
					Double aux=Double.parseDouble(cob.get("SUMA_ASEGURADA"));
					ptcapita=aux.toString();
				}
				catch(Exception ex)
				{
					ptcapita=null;
				}
				mapPolicapIterado.put("pv_ptcapita_i",ptcapita);
				mapPolicapIterado.put("pv_ptreduci_i", cob.get("ptreduci"));
				mapPolicapIterado.put("pv_fereduci_i", cob.get("fereduci"));
				mapPolicapIterado.put("pv_swrevalo_i", cob.get("swrevalo"));
				mapPolicapIterado.put("pv_cdagrupa_i", cob.get("cdagrupa"));
				mapPolicapIterado.put("pv_accion_i", "I");
				log.debug("Iteracion #"+i+" de movPolicap");
				kernelManager.movPolicap(mapPolicapIterado);
				
				i++;
			}
			success=true;
		}
		catch(Exception ex)
		{
			log.error("Error al guardar las coberturas",ex);
			success=false;
		}
		return SUCCESS;
	}
	
	public String obtenerCamposTatrigar()
	{
		return SUCCESS;
	}
	
	public String obtenerValoresTatrigar()
	{
		return SUCCESS;
	}
	
	public String guardarValoresTatrigar()
	{
		return SUCCESS; 
	}
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
	public void setKernelManager(KernelManagerSustituto km)
	{
		this.kernelManager=km;
	}

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public Map<String, String> getSmap2() {
		return smap2;
	}

	public void setSmap2(Map<String, String> smap2) {
		this.smap2 = smap2;
	}

	public Map<String, String> getSmap3() {
		return smap3;
	}

	public void setSmap3(Map<String, String> smap3) {
		this.smap3 = smap3;
	}

	public Map<String, Object> getOmap1() {
		return omap1;
	}

	public void setOmap1(Map<String, Object> omap1) {
		this.omap1 = omap1;
	}

	public Map<String, Object> getOmap2() {
		return omap2;
	}

	public void setOmap2(Map<String, Object> omap2) {
		this.omap2 = omap2;
	}

	public Map<String, Object> getOmap3() {
		return omap3;
	}

	public void setOmap3(Map<String, Object> omap3) {
		this.omap3 = omap3;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public List<Map<String, String>> getSlist2() {
		return slist2;
	}

	public void setSlist2(List<Map<String, String>> slist2) {
		this.slist2 = slist2;
	}

	public List<Map<String, Object>> getOlist1() {
		return olist1;
	}

	public void setOlist1(List<Map<String, Object>> olist1) {
		this.olist1 = olist1;
	}

	public List<Map<String, Object>> getOlist2() {
		return olist2;
	}

	public void setOlist2(List<Map<String, Object>> olist2) {
		this.olist2 = olist2;
	}

	public Item getItem1() {
		return item1;
	}

	public void setItem1(Item item1) {
		this.item1 = item1;
	}

	public Item getItem2() {
		return item2;
	}

	public void setItem2(Item item2) {
		this.item2 = item2;
	}

	public Item getItem3() {
		return item3;
	}

	public void setItem3(Item item3) {
		this.item3 = item3;
	}

	public Item getItem4() {
		return item4;
	}

	public void setItem4(Item item4) {
		this.item4 = item4;
	}

	public Map<String, String> getSpanel1() {
		return spanel1;
	}

	public void setSpanel1(Map<String, String> spanel1) {
		this.spanel1 = spanel1;
	}

	public Map<String, String> getSpanel2() {
		return spanel2;
	}

	public void setSpanel2(Map<String, String> spanel2) {
		this.spanel2 = spanel2;
	}

	public Map<String, String> getSpanel3() {
		return spanel3;
	}

	public void setSpanel3(Map<String, String> spanel3) {
		this.spanel3 = spanel3;
	}

	public Map<String, Object> getOpanel1() {
		return opanel1;
	}

	public void setOpanel1(Map<String, Object> opanel1) {
		this.opanel1 = opanel1;
	}

	public Map<String, Object> getOpanel2() {
		return opanel2;
	}

	public void setOpanel2(Map<String, Object> opanel2) {
		this.opanel2 = opanel2;
	}

	public Map<String, Object> getOpanel3() {
		return opanel3;
	}

	public void setOpanel3(Map<String, Object> opanel3) {
		this.opanel3 = opanel3;
	}

	public boolean isSuccess() {
		return success;
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametros) {
		this.parametros = parametros;
	}
	
}
