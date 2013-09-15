package mx.com.gseguros.portal.cotizacion.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.SqlParameter;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.Tatri;
import mx.com.gseguros.portal.general.util.GeneradorCampos;

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
	private String str1;
	private String str2;
	
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
		log.debug(smap1);
		try
		{
			UserVO usuSes=(UserVO)session.get("USUARIO");
			DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usuSes.getUser());
			
			/*
			pv_cdramo_i       smap1 ready!
            pv_cdtipsit_i     NOT!
            pv_cdgarant_i     smap1 ready!
			*/
			smap1.put("pv_cdtipsit_i",datUsu.getCdtipsit());
			List<Tatri>listTatri=kernelManager.obtenerTatrigar(smap1);
			GeneradorCampos genCam=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			genCam.setCdgarant(smap1.get("pv_cdgarant_i"));
			genCam.genera(listTatri);
			str1=genCam.getFields().toString();
			str2=genCam.getItems().toString();
			// f i e l d s : [ ... ]
			//0 1 2 3 4 5 6 7
			str1=str1.substring(7).replace("\n", "");
			// i t e m s : [ ... ]
			//0 1 2 3 4 5 6 7
			str2=str2.substring(6).replace("\n", "");
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener los campos tatrigar",ex);
			success=false;
		}
		return SUCCESS;
	}
	
	public String obtenerValoresTatrigar()
	{
		log.debug(smap1);
		try
		{
			/*
			pv_cdunieco_i      smap1 ready!
            pv_cdramo_i        smap1 ready!
            pv_estado_i        smap1 ready!
            pv_nmpoliza_i      smap1 ready!
            pv_nmsituac_i      smap1 ready!
            pv_cdgarant_i      smap1 ready!
			*/
			parametros=new HashMap<String,String>(0);
			Map<String,Object>parametrosCargados=kernelManager.obtenerValoresTatrigar(smap1);
			Iterator<Entry<String, Object>> it=parametrosCargados.entrySet().iterator();
			while(it.hasNext())
			{
				Entry<String,Object> entry=it.next();
				parametros.put("pv_"+entry.getKey(), (String)entry.getValue());
			}
			log.debug(parametros);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener los valores de tatrigar",ex);
			success=true;
		}
		return SUCCESS;
	}
	
	public String guardarValoresTatrigar()
	{
		log.debug("\n##############################"
				+ "\n###### Guardar tvalogar ######");
		try
		{
			/*
			pv_cdunieco  smap1 ready!
	        pv_cdramo    smap1 ready!
	        pv_estado    smap1 ready!
	        pv_nmpoliza  smap1 ready!
	        pv_nmsituac  smap1 ready!
	        pv_cdgarant  smap1 ready!
	        pv_nmsuplem  #0
	        pv_status    #V
	        */
			smap1.putAll(parametros);
			smap1.put("pv_status", "V");
			smap1.put("pv_nmsuplem", "0");
			log.debug("smap1"+smap1);
	        log.debug("parametros"+parametros);
	        kernelManager.pMovTvalogar(smap1);
	        success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar tvalogar",ex);
			success=false;
		}
		log.debug("\n###### Guardar tvalogar ######"
				+ "\n##############################");
		return SUCCESS; 
	}
	
	public String pantallaDomicilio()
	{
		log.debug("\n###################################"
				+ "\n###################################"
				+ "\n###### pantalla de domicilio ######"
				+ "\n######                       ######"
				+ "\n######                       ######");
		log.debug("smap1: "+smap1);
		ScreenInterceptor scrInt=new ScreenInterceptor();
		return scrInt.intercept(this, ScreenInterceptor.PANTALLA_COMPLEMENTARIOS_DOMICILIO_ASEGURADO);
	}
	
	public String mostrarPantallaDomicilio()
	{
		try
		{
			UserVO usuSes=(UserVO) session.get("USUARIO");
			DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usuSes.getUser());
			
			Map<String,String>paramTatriper=new HashMap<String,String>(0);
			paramTatriper.put("pv_cdramo_i"   , smap1.get("pv_cdramo"));
			paramTatriper.put("pv_cdrol_i"    , smap1.get("pv_cdrol"));
			paramTatriper.put("pv_cdtipsit_i" , datUsu.getCdtipsit());
			List<Tatri>tatriper=kernelManager.obtenerTatriper(paramTatriper);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdrol(smap1.get("pv_cdrol"));
			gc.setCdramo(smap1.get("pv_cdramo"));
			gc.setCdtipsit(datUsu.getCdtipsit());
			gc.genera(tatriper);
			item1=gc.getFields();
			item2=gc.getItems();
			/*item1=Item.crear("fields",null,Item.ARR)//quitame
					.add(Item.crear(null,null,Item.OBJ).add("name","parametros.pv_otvalor01"))//quitame
					.add(Item.crear(null,null,Item.OBJ).add("name","parametros.pv_otvalor02"))//quitame
					.add(Item.crear(null,null,Item.OBJ)//quitame
							.add("name"       , "parametros.pv_otvalor03")//quitame
							.add("type"       , "date")//quitame
							.add("dateFormat" , "d/m/Y")//quitame
							)//generador campos*/
			item1
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.asegurado"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.rfc"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.telefono"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.codigoPostal"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.ciudad"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.delegacion"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.colonia"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.calle"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.exterior"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.interior"))
					;
			/*
			item2=Item.crear("items",null,Item.ARR)//generador campos
					.add(Item.crear(null,null,Item.OBJ)//quitame
							.add("name"       , "parametros.pv_otvalor01")//quitame
							.add("xtype"       , "textfield")//quitame
							.add("allowBlank" , false)//quitame
							)//generador campos
					.add(Item.crear(null,null,Item.OBJ)//quitame
							.add("name"       , "parametros.pv_otvalor02")//quitame
							.add("xtype"       , "textfield")//quitame
							.add("allowBlank" , true)//quitame
							)//generador campos
					.add(Item.crear(null,null,Item.OBJ)//quitame
							.add("name"       , "parametros.pv_otvalor03")//quitame
							.add("xtype"       , "datefield")//quitame
							.add("format"       , "d/m/Y")//quitame
							.add("allowBlank" , false)//quitame
							)//generador campos
					;*/
		}
		catch(Exception ex)
		{
			log.error("error al mostrar la pantalla de domicilio",ex);
		}
		log.debug("\n######                       ######"
				+ "\n######                       ######"
				+ "\n###### pantalla de domicilio ######"
				+ "\n###################################"
				+ "\n###################################");
		return SUCCESS;
	}
	
	public String cargarPantallaDomicilio()
	{
		log.debug("\n##########################################"
				+ "\n##########################################"
				+ "\n###### cargar pantalla de domicilio ######"
				+ "\n######                              ######"
				+ "\n######                              ######");
		try
		{
			
			UserVO usuSes=(UserVO)session.get("USUARIO");
			DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usuSes.getUser());
			
			log.debug(smap1);
			/*
			pv_cdunieco_i  smap1 ready!
            pv_cdramo_i    smap1 ready!
            pv_estado_i    smap1 ready!
            pv_nmpoliza_i  smap1 ready!
            pv_nmsituac_i  smap1 ready!
            pv_cdrol_i     smap1 ready!
            pv_cdperson_i  smap1 ready!
            pv_nmsumplem_i #0
            pv_status_i    #V    QUITADO X(
            pv_cdtipsit_i  session
           */
			smap1.put("pv_nmsuplem_i" , "0");
			//smap1.put("pv_status_i"   , "V");
			smap1.put("pv_cdtipsit_i" ,  datUsu.getCdtipsit());
			Map<String,Object>parametrosCargados=null;
			try
			{
				parametrosCargados=kernelManager.obtenerValoresTatriper(smap1);
			}
			catch(Exception ex)
			{
				log.debug("no hay valores para tatriper",ex);
				parametrosCargados=new HashMap<String,Object>(0);
			}
			parametros=new HashMap<String,String>(0);
			Iterator<Entry<String, Object>> it=parametrosCargados.entrySet().iterator();
			while(it.hasNext())
			{
				Entry<String,Object> entry=it.next();
				parametros.put("pv_"+entry.getKey(), (String)entry.getValue());
			}
			log.debug(parametros);
			/*parametros=new HashMap<String,String>(0);//quitame
			parametros.put("pv_otvalor01", "valor1");//quitame
			parametros.put("pv_otvalor02", "valor2");//quitame
			parametros.put("pv_otvalor03", "17/08/1990");//quitame*/
			
			String nombreAsegurado = smap1.get("nombreAsegurado");
			String rfcAsegurado    = smap1.get("cdrfc");
			smap1=new HashMap<String,String>(0);
			smap1.put("asegurado"    , nombreAsegurado);
			smap1.put("rfc"          , rfcAsegurado);
			smap1.put("telefono"     , "012464666589");
			smap1.put("codigoPostal" , "99999");
			smap1.put("ciudad"       , "Tlaxcala");
			smap1.put("delegacion"   , "tlaxcala");
			smap1.put("colonia"      , "Acuitlapilco");
			smap1.put("calle"        , "Zaragoza");
			smap1.put("exterior"     , "B");
			smap1.put("interior"     , "3");
			
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al cargar los datos de domicilio",ex);
			success=false;
		}
		
		log.debug("\n######                              ######"
				+ "\n######                              ######"
				+ "\n###### cargar pantalla de domicilio ######"
				+ "\n##########################################"
				+ "\n##########################################");
		return SUCCESS;
	}
	
	public String guardarPantallaDomicilio()
	{
		log.debug("\n###########################################"
				+ "\n###########################################"
				+ "\n###### guardar pantalla de domicilio ######"
				+ "\n######                               ######"
				+ "\n######                               ######");
		try
		{
			log.debug(smap1);
			log.debug(parametros);
			UserVO usuSes=(UserVO)session.get("USUARIO");
			DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usuSes.getUser());
			
			/*
			pv_cdunieco    smap1  ready!
			pv_cdramo      smap1  ready!
			pv_estado      smap1  ready!
			pv_nmpoliza    smap1  ready!
			pv_nmsituac    smap1  ready!
			pv_nmsuplem    #0
			pv_status      #V
			pv_cdrol       smap1  ready!
			pv_cdperson    smap1  ready!
			pv_cdatribu    #null
			pv_cdtipsit    session
			pv_otvalor01   parametros ready!
			...
			*/
			parametros.putAll(smap1);
			parametros.put("pv_nmsuplem" , "0");
			parametros.put("pv_status"   , "0");
			parametros.put("pv_cdatribu" , null);
			parametros.put("pv_cdtipsit" , datUsu.getCdtipsit());
			kernelManager.pMovTvaloper(parametros);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar los datos de domicilio",ex);
			success=false;
		}
		log.debug("\n######                               ######"
				+ "\n######                               ######"
				+ "\n###### guardar pantalla de domicilio ######"
				+ "\n###########################################"
				+ "\n###########################################");
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

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}
	
}
