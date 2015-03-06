package mx.com.gseguros.portal.endosos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.SlistSmapVO;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.endosos.service.EndososAutoManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.utils.Utilerias;
import mx.com.gseguros.utils.Utils;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class EndososAutoManagerImpl implements EndososAutoManager
{
	private static Logger logger = Logger.getLogger(EndososAutoManagerImpl.class);
	
	private PantallasDAO pantallasDAO;
	private EndososDAO   endososDAO;
	
	@Override
	public Map<String,Item> construirMarcoEndosos(String cdsisrol) throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ construirMarcoEndosos @@@@@@"
				,"\n@@@@@@ cdsisrol=",cdsisrol
				));
		
		Map<String,Item>items=new HashMap<String,Item>();
		
		String paso="";
		
		try
		{
			paso="Recuperando componentes del formulario de busqueda";
			logger.info(paso);
			List<ComponenteVO>componentesFiltro=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"FORM_BUSQUEDA"      //seccion
					,null //orden
					);
			
			paso="Recuperando componentes del grid de polizas";
			logger.info(paso);
			List<ComponenteVO>componentesGridPolizas=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"GRID_POLIZAS"       //seccion
					,null //orden
					);
			
			paso="Recuperando componentes del grid de historico de poliza";
			logger.info(paso);
			List<ComponenteVO>componentesGridHistoricoPoliza=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MARCO_ENDOSOS_AUTO"    //pantalla
					,"GRID_HISTORICO_POLIZA" //seccion
					,null //orden
					);
			
			paso="Recuperando componentes del grid de grupos";
			logger.info(paso);
			List<ComponenteVO>componentesGridGrupos=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"GRID_GRUPOS"        //seccion
					,null //orden
					);
			
			paso="Recuperando componentes del grid de familias";
			logger.info(paso);
			List<ComponenteVO>componentesGridFamilias=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"GRID_FAMILIAS"        //seccion
					,null //orden
					);
			
			paso="Construyendo componentes del formulario de busqueda";
			logger.info(paso);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(componentesFiltro, true, false, true, false, false, false);
			items.put("formBusqItems" , gc.getItems());
			
			paso="Construyendo componentes del grid de polizas";
			logger.info(paso);
			gc.generaComponentes(componentesGridPolizas, true, false, false, true, true, false);
			items.put("gridPolizasColumns" , gc.getColumns());
			
			paso="Construyendo componentes del grid de historico de poliza";
			logger.info(paso);
			gc.generaComponentes(componentesGridHistoricoPoliza, true, false, false, true, false, false);
			items.put("gridHistoricoColumns" , gc.getColumns());
			
			paso="Construyendo componentes del grid de grupos";
			logger.info(paso);
			gc.generaComponentes(componentesGridGrupos, true, false, false, true, false, false);
			items.put("gridGruposColumns" , gc.getColumns());
			
			paso="Construyendo componentes del grid de familias";
			logger.info(paso);
			gc.generaComponentes(componentesGridFamilias, true, false, false, true, false, false);
			items.put("gridFamiliasColumns" , gc.getColumns());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ items=",items
				,"\n@@@@@@ construirMarcoEndosos @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public String recuperarColumnasIncisoRamo(String cdramo)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarColumnasIncisoRamo @@@@@@"
				,"\n@@@@@@ cdramo=",cdramo
				));
		
		String cols = null;
		String paso = "";
		
		try
		{
			paso="Recuperando columnas de incisos para el producto";
			logger.info(paso);
			List<ComponenteVO>columnas=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"COLUMNAS_INCISO"    //seccion
					,null //orden
					);
			
			paso="Construyendo columnas de incisos para el producto";
			logger.info(paso);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(columnas, true, false, false, true, true, false);
			cols=gc.getColumns().toString();
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex,paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ columnas=",cols
				,"\n@@@@@@ recuperarColumnasIncisoRamo @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return cols;
	}
	
	@Override
	public SlistSmapVO recuperarEndososClasificados(
			String cdramo
			,String nivel
			,String multiple
			,String tipoflot
			,List<Map<String,String>>incisos
			,String cdsisrol
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarEndososClasificados @@@@@@"
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ nivel="    , nivel
				,"\n@@@@@@ multiple=" , multiple
				,"\n@@@@@@ tipoflot=" , tipoflot
				,"\n@@@@@@ incisos="  , incisos
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				));

		SlistSmapVO resp = new SlistSmapVO();
		String      paso = null;
		
		try
		{
			double millis = System.currentTimeMillis();
			double random = 1000d*Math.random();
			logger.debug(millis);
			logger.debug(random);
			String stamp  = String.format("%.0f.%.0f",millis,random);
			
			logger.debug(Utilerias.join("stamp=",stamp));
			
			resp.getSmap().put("stamp" , stamp);
			
			if(incisos.size()>0)
			{
				paso="Insertando situaciones para evaluacion";
				logger.info(paso);
				
				for(Map<String,String>inciso:incisos)
				{
					endososDAO.insertarIncisoEvaluacion(
							stamp
							,inciso.get("CDUNIECO")
							,inciso.get("CDRAMO")
							,inciso.get("ESTADO")
							,inciso.get("NMPOLIZA")
							,inciso.get("NMSUPLEM_TVAL")
							,inciso.get("NMSITUAC")
							,inciso.get("CDTIPSIT")
							);
				}
			}
			
			paso="Recuperando lista de endosos";
			logger.info(paso);
			resp.setSlist(endososDAO.recuperarEndososClasificados(stamp,cdramo,nivel,multiple,tipoflot,cdsisrol));
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ recuperarEndososClasificados @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public Map<String,Item>pantallaEndosoValosit(
			String cdtipsup
			,String cdramo
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ pantallaEndosoValosit @@@@@@"
				,"\n@@@@@@ cdtipsup=" , cdtipsup
				,"\n@@@@@@ cdramo="   , cdramo
				));
		
		Map<String,Item>items = new HashMap<String,Item>();
		String          paso  = null;
		
		try
		{
			paso="Recuperando columnas de lectura";
			logger.info(paso);
			
			List<ComponenteVO>columnasLectura=pantallasDAO.obtenerComponentes(
					cdtipsup //cdtiptra
					,null    //cdunieco
					,cdramo
					,null    //cdtipsit
					,null    //estado
					,null    //cdsisrol
					,"ENDOSO_VALOSIT_AUTO"
					,"COLUMNAS_LECTURA"
					,null    //orden
					);
			
			paso="Recuperando columnas editables";
			logger.info(paso);
			
			List<ComponenteVO>columnasEditables=pantallasDAO.obtenerComponentes(
					cdtipsup //cdtiptra
					,null    //cdunieco
					,cdramo
					,null    //cdtipsit
					,null    //estado
					,null    //cdsisrol
					,"ENDOSO_VALOSIT_AUTO"
					,"COLUMNAS_EDITABLES"
					,null    //orden
					);
			
			paso="Construyendo columnas de lectura";
			logger.info(paso);
			
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(columnasLectura, true, false, false, true, false, false);
			
			items.put("gridColumnsLectura"   , gc.getColumns());
			
			paso="Construyendo columnas editables";
			logger.info(paso);
			
			gc.generaComponentes(columnasEditables, true, false, false, true, true, false);
			
			items.put("gridColumnsEditables" , gc.getColumns());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ pantallaEndosoValosit @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public void guardarTvalositEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String status
			,String cdtipsit
			,String otvalor01,String otvalor02,String otvalor03,String otvalor04,String otvalor05
			,String otvalor06,String otvalor07,String otvalor08,String otvalor09,String otvalor10
			,String otvalor11,String otvalor12,String otvalor13,String otvalor14,String otvalor15
			,String otvalor16,String otvalor17,String otvalor18,String otvalor19,String otvalor20
			,String otvalor21,String otvalor22,String otvalor23,String otvalor24,String otvalor25
			,String otvalor26,String otvalor27,String otvalor28,String otvalor29,String otvalor30
			,String otvalor31,String otvalor32,String otvalor33,String otvalor34,String otvalor35
			,String otvalor36,String otvalor37,String otvalor38,String otvalor39,String otvalor40
			,String otvalor41,String otvalor42,String otvalor43,String otvalor44,String otvalor45
			,String otvalor46,String otvalor47,String otvalor48,String otvalor49,String otvalor50
			,String otvalor51,String otvalor52,String otvalor53,String otvalor54,String otvalor55
			,String otvalor56,String otvalor57,String otvalor58,String otvalor59,String otvalor60
			,String otvalor61,String otvalor62,String otvalor63,String otvalor64,String otvalor65
			,String otvalor66,String otvalor67,String otvalor68,String otvalor69,String otvalor70
			,String otvalor71,String otvalor72,String otvalor73,String otvalor74,String otvalor75
			,String otvalor76,String otvalor77,String otvalor78,String otvalor79,String otvalor80
			,String otvalor81,String otvalor82,String otvalor83,String otvalor84,String otvalor85
			,String otvalor86,String otvalor87,String otvalor88,String otvalor89,String otvalor90
			,String otvalor91,String otvalor92,String otvalor93,String otvalor94,String otvalor95
			,String otvalor96,String otvalor97,String otvalor98,String otvalor99
			,String tstamp)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarTvalositEndoso @@@@@@"
				,"\n@@@@@@ cdunieco="  , cdunieco
				,"\n@@@@@@ cdramo="    , cdramo
				,"\n@@@@@@ estado="    , estado
				,"\n@@@@@@ nmpoliza="  , nmpoliza
				,"\n@@@@@@ nmsituac="  , nmsituac
				,"\n@@@@@@ nmsuplem="  , nmsuplem
				,"\n@@@@@@ status="    , status
				,"\n@@@@@@ cdtipsit="  , cdtipsit
				,"\n@@@@@@ otvalor01=" , otvalor01
				,"\n@@@@@@ otvalor02=" , otvalor02
				,"\n@@@@@@ otvalor03=" , otvalor03
				,"\n@@@@@@ otvalor04=" , otvalor04
				,"\n@@@@@@ otvalor05=" , otvalor05
				,"\n@@@@@@ otvalor06=" , otvalor06
				,"\n@@@@@@ otvalor07=" , otvalor07
				,"\n@@@@@@ otvalor08=" , otvalor08
				,"\n@@@@@@ otvalor09=" , otvalor09
				,"\n@@@@@@ otvalor10=" , otvalor10
				,"\n@@@@@@ otvalor11=" , otvalor11
				,"\n@@@@@@ otvalor12=" , otvalor12
				,"\n@@@@@@ otvalor13=" , otvalor13
				,"\n@@@@@@ otvalor14=" , otvalor14
				,"\n@@@@@@ otvalor15=" , otvalor15
				,"\n@@@@@@ otvalor16=" , otvalor16
				,"\n@@@@@@ otvalor17=" , otvalor17
				,"\n@@@@@@ otvalor18=" , otvalor18
				,"\n@@@@@@ otvalor19=" , otvalor19
				,"\n@@@@@@ otvalor20=" , otvalor20
				,"\n@@@@@@ otvalor21=" , otvalor21
				,"\n@@@@@@ otvalor22=" , otvalor22
				,"\n@@@@@@ otvalor23=" , otvalor23
				,"\n@@@@@@ otvalor24=" , otvalor24
				,"\n@@@@@@ otvalor25=" , otvalor25
				,"\n@@@@@@ otvalor26=" , otvalor26
				,"\n@@@@@@ otvalor27=" , otvalor27
				,"\n@@@@@@ otvalor28=" , otvalor28
				,"\n@@@@@@ otvalor29=" , otvalor29
				,"\n@@@@@@ otvalor30=" , otvalor30
				,"\n@@@@@@ otvalor31=" , otvalor31
				,"\n@@@@@@ otvalor32=" , otvalor32
				,"\n@@@@@@ otvalor33=" , otvalor33
				,"\n@@@@@@ otvalor34=" , otvalor34
				,"\n@@@@@@ otvalor35=" , otvalor35
				,"\n@@@@@@ otvalor36=" , otvalor36
				,"\n@@@@@@ otvalor37=" , otvalor37
				,"\n@@@@@@ otvalor38=" , otvalor38
				,"\n@@@@@@ otvalor39=" , otvalor39
				,"\n@@@@@@ otvalor40=" , otvalor40
				,"\n@@@@@@ otvalor41=" , otvalor41
				,"\n@@@@@@ otvalor42=" , otvalor42
				,"\n@@@@@@ otvalor43=" , otvalor43
				,"\n@@@@@@ otvalor44=" , otvalor44
				,"\n@@@@@@ otvalor45=" , otvalor45
				,"\n@@@@@@ otvalor46=" , otvalor46
				,"\n@@@@@@ otvalor47=" , otvalor47
				,"\n@@@@@@ otvalor48=" , otvalor48
				,"\n@@@@@@ otvalor49=" , otvalor49
				,"\n@@@@@@ otvalor50=" , otvalor50
				,"\n@@@@@@ otvalor51=" , otvalor51
				,"\n@@@@@@ otvalor52=" , otvalor52
				,"\n@@@@@@ otvalor53=" , otvalor53
				,"\n@@@@@@ otvalor54=" , otvalor54
				,"\n@@@@@@ otvalor55=" , otvalor55
				,"\n@@@@@@ otvalor56=" , otvalor56
				,"\n@@@@@@ otvalor57=" , otvalor57
				,"\n@@@@@@ otvalor58=" , otvalor58
				,"\n@@@@@@ otvalor59=" , otvalor59
				,"\n@@@@@@ otvalor60=" , otvalor60
				,"\n@@@@@@ otvalor61=" , otvalor61
				,"\n@@@@@@ otvalor62=" , otvalor62
				,"\n@@@@@@ otvalor63=" , otvalor63
				,"\n@@@@@@ otvalor64=" , otvalor64
				,"\n@@@@@@ otvalor65=" , otvalor65
				,"\n@@@@@@ otvalor66=" , otvalor66
				,"\n@@@@@@ otvalor67=" , otvalor67
				,"\n@@@@@@ otvalor68=" , otvalor68
				,"\n@@@@@@ otvalor69=" , otvalor69
				,"\n@@@@@@ otvalor70=" , otvalor70
				,"\n@@@@@@ otvalor71=" , otvalor71
				,"\n@@@@@@ otvalor72=" , otvalor72
				,"\n@@@@@@ otvalor73=" , otvalor73
				,"\n@@@@@@ otvalor74=" , otvalor74
				,"\n@@@@@@ otvalor75=" , otvalor75
				,"\n@@@@@@ otvalor76=" , otvalor76
				,"\n@@@@@@ otvalor77=" , otvalor77
				,"\n@@@@@@ otvalor78=" , otvalor78
				,"\n@@@@@@ otvalor79=" , otvalor79
				,"\n@@@@@@ otvalor80=" , otvalor80
				,"\n@@@@@@ otvalor81=" , otvalor81
				,"\n@@@@@@ otvalor82=" , otvalor82
				,"\n@@@@@@ otvalor83=" , otvalor83
				,"\n@@@@@@ otvalor84=" , otvalor84
				,"\n@@@@@@ otvalor85=" , otvalor85
				,"\n@@@@@@ otvalor86=" , otvalor86
				,"\n@@@@@@ otvalor87=" , otvalor87
				,"\n@@@@@@ otvalor88=" , otvalor88
				,"\n@@@@@@ otvalor89=" , otvalor89
				,"\n@@@@@@ otvalor90=" , otvalor90
				,"\n@@@@@@ otvalor91=" , otvalor91
				,"\n@@@@@@ otvalor92=" , otvalor92
				,"\n@@@@@@ otvalor93=" , otvalor93
				,"\n@@@@@@ otvalor94=" , otvalor94
				,"\n@@@@@@ otvalor95=" , otvalor95
				,"\n@@@@@@ otvalor96=" , otvalor96
				,"\n@@@@@@ otvalor97=" , otvalor97
				,"\n@@@@@@ otvalor98=" , otvalor98
				,"\n@@@@@@ otvalor99=" , otvalor99
				,"\n@@@@@@ tsmap="     , tstamp
				));
		
		String paso = "Guardando atributos";
		
		try
		{
			endososDAO.guardarTvalositEndoso(cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsituac
					,nmsuplem
					,status
					,cdtipsit
					,otvalor01 , otvalor02 , otvalor03 , otvalor04 , otvalor05
					,otvalor06 , otvalor07 , otvalor08 , otvalor09 , otvalor10
					,otvalor11 , otvalor12 , otvalor13 , otvalor14 , otvalor15
					,otvalor16 , otvalor17 , otvalor18 , otvalor19 , otvalor20
					,otvalor21 , otvalor22 , otvalor23 , otvalor24 , otvalor25
					,otvalor26 , otvalor27 , otvalor28 , otvalor29 , otvalor30
					,otvalor31 , otvalor32 , otvalor33 , otvalor34 , otvalor35
					,otvalor36 , otvalor37 , otvalor38 , otvalor39 , otvalor40
					,otvalor41 , otvalor42 , otvalor43 , otvalor44 , otvalor45
					,otvalor46 , otvalor47 , otvalor48 , otvalor49 , otvalor50
					,otvalor51 , otvalor52 , otvalor53 , otvalor54 , otvalor55
					,otvalor56 , otvalor57 , otvalor58 , otvalor59 , otvalor60
					,otvalor61 , otvalor62 , otvalor63 , otvalor64 , otvalor65
					,otvalor66 , otvalor67 , otvalor68 , otvalor69 , otvalor70
					,otvalor71 , otvalor72 , otvalor73 , otvalor74 , otvalor75
					,otvalor76 , otvalor77 , otvalor78 , otvalor79 , otvalor80
					,otvalor81 , otvalor82 , otvalor83 , otvalor84 , otvalor85
					,otvalor86 , otvalor87 , otvalor88 , otvalor89 , otvalor90
					,otvalor91 , otvalor92 , otvalor93 , otvalor94 , otvalor95
					,otvalor96 , otvalor97 , otvalor98 , otvalor99
					,tstamp);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		

		logger.info(Utilerias.join(
				 "\n@@@@@@ guardarTvalositEndoso @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	public void confirmarEndosoTvalositAuto(
			String cdtipsup
			,String tstamp
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoTvalositAuto @@@@@@"
				,"\n@@@@@@ cdtipsup=" , cdtipsup
				,"\n@@@@@@ tstamp="   , tstamp
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				));
		
		String paso="Confirmando endoso";
		
		try
		{
			endososDAO.confirmarEndosoTvalositAuto(cdtipsup,tstamp,cdunieco,cdramo,estado,nmpoliza);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ confirmarEndosoTvalositAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	/*
	 * Getters y setters
	 */
	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}

	public void setEndososDAO(EndososDAO endososDAO) {
		this.endososDAO = endososDAO;
	}
}