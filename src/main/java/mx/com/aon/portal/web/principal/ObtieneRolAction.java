package mx.com.aon.portal.web.principal;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.model.principal.ConfiguracionVO;
import mx.com.aon.portal.model.principal.RolVO;
import mx.com.aon.portal.model.principal.RolesVO;
import mx.com.aon.portal.model.principal.SeccionVO;
import mx.com.aon.portal.model.principal.TipoVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.principal.PrincipalManager;
import mx.com.aon.portal.util.ImageUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.opensymphony.xwork2.ActionContext;
import mx.com.ice.kernel.core.PropertyReader;

/**
 * 
 * @author sergio.ramirez
 * 
 */
public class ObtieneRolAction extends PrincipalCoreAction {
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -7629384251752332553L;
	private List<RolVO> roles;
	private ArrayList<ClienteVO> clientes;
	private ArrayList<SeccionVO> secciones;
	private ArrayList<RolesVO> rolesLista;
	private ArrayList<TipoVO> tipos;
	private RolVO rolVo;
	private ConfiguracionVO configuracionVo;

	private String claveConfiguracion;
	private String claveSistemaRol;
	private String claveElemento;
	private String dsConfiguracion;
	private String seccionForm;
	private String tabla;
	private boolean success;
	private PrincipalManager principalManager;
	private transient PrincipalManager principalManagerJdbcTemplate;

	/**
	 * parameters to insert.
	 */

	private String nombre;
	private String claveCliente;
	private String claveRol;
	private String claveSeccion;
	private int habilitar;
	private String especific;
	private String content;
	private String tipo;
	private String archivo;
	private String archivoDos;
	private String texto;
	protected File archivoImagen;
	protected String pathFile;
	private String contenidoSec;
	

	/**
	 * parameters to edit.
	 */

	private String dsSistemaRol;
	private String dsElemento;
	private String cdConfigura;
	private String dsConfigura;
	private String dsElemen;
	private String dsRol;
	private String dsSeccion;
	private String dsTipo;
	private int swHabilitado;
	private boolean habilitado;
	private String especificacion;
	private String contenido;
	private String dsArchivo;
	private String newArchivo;
	private int numeroFila;
	private String chkHabilitado;
	//Para obtener los nombres de los archivos junto con su extension.
	private String nombreUpload1;
	private String nombreUpload2;
    
	private String rutaPubImage = PropertyReader.readProperty("img.url.pub.confpagimage");
	//private static String pruebaRuta = PropertyReader.readProperty("xml.url.file.test");
	
	
	/**
	 * Contiene la respuesta json que se devolverá cuando se sube un archivo
	 * 
	 */
	private String resultadoUpload;

	private String cmd;

	protected int start = 0;
	
	protected int limit = 20;

	protected int totalCount;

	/*
	public String execute() throws Exception {
		
		return INPUT;
	}
*/
	/**
	 * Metodo que carga todas las configuraciones existentes de la base DB.
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String rolesJson() throws ApplicationException {
		logger.debug("nombre--->"+ claveConfiguracion);
		logger.debug("rol---->" + claveSistemaRol);
		logger.debug("cliente--->" + claveElemento);
		logger.debug("seccion--->" + seccionForm);
		logger.debug("numeroFila->"+numeroFila);
		
		logger.debug("rutaPubImage ->"+ rutaPubImage );
		//logger.debug("pruebaRuta ->"+ pruebaRuta);
		
		
	
		try {
			if(StringUtils.isNotBlank(claveConfiguracion )){
				//if(!session.containsKey("CLAVE_CONF"))
					session.put("CLAVE_CONF", claveConfiguracion);
			}
			if(StringUtils.isNotBlank(claveSistemaRol)){
				//if(!session.containsKey("CLAVE_ROL"))
					session.put("CLAVE_ROL", claveSistemaRol);
			}
			if(StringUtils.isNotBlank(claveElemento)){
				//if(!session.containsKey("CLAVE_ELEM"))
					session.put("CLAVE_ELEM", claveElemento);
			}
			
			//Exportador Generico
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("nombre", claveConfiguracion);
			params.put("rol", claveSistemaRol);
			params.put("cliente", claveElemento);
			params.put("seccion", seccionForm);
			String [] NOMBRE_COLUMNAS = { "ROL", "CLIENTE", "SECCION", "NOMBRE" , "VISIBLE" };
			session.put("PARAMETROS_EXPORT", params);
			session.put("NOMBRE_COLUMNAS", NOMBRE_COLUMNAS);
			session.put("ENDPOINT_EXPORT_NAME", "EXPORT_CONFIGURACIONES");
			
			//roles = principalManager.getRoles(claveConfiguracion, claveSistemaRol, claveElemento, seccionForm);
			PagedList pagedList = principalManager.getRoles(claveConfiguracion, claveSistemaRol, claveElemento, seccionForm, start, limit);
			totalCount = pagedList.getTotalItems();
			roles = pagedList.getItemsRangeList();
			for (RolVO lista : roles) {
				if(lista.getSwHabilitado().equals("1")){
					lista.setHabilitado(true);
				}else{
					lista.setHabilitado(false);
				}
				//if(!session.containsKey("DESC_CONF"))
					session.put("DESC_CONF", lista.getDsConfigura());
				//if(!session.containsKey("DESC_ROL"))
					session.put("DESC_ROL",  lista.getDsRol());
				//if(!session.containsKey("DESC_ELEM"))
					session.put("DESC_ELEM", lista.getDsElemen());
				//
				if(StringUtils.isNotBlank(lista.getEspecificacion())){
					lista.setEspecificacion(StringEscapeUtils.unescapeHtml(lista.getEspecificacion()));
					logger.debug("texto transfromado :"+StringEscapeUtils.unescapeHtml(lista.getEspecificacion()));
				}
				
				
			}
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
		/*if(roles != null && roles.isEmpty()){
			success = false;
		}
		logger.debug("roles-->" + roles);
		success = true;
		
		
		return SUCCESS;*/
	}

	/**
	 * Metodo que carga una lista de objetos de tipo ClienteVO.
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetComboCliente() throws Exception {
		logger.debug("Cargando combo de Clientes");
		if(session.containsKey("LISTA_CLIENTES")){
			clientes = (ArrayList<ClienteVO>) session.get("LISTA_CLIENTES");
		}else{
			clientes = principalManager.getListaCliente();
			session.put("LISTA_CLIENTES", clientes);
		}
		success = true;
		return SUCCESS;
	}

	/**
	 * Metodo que obtiene una lista del objeto RolesVO
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetComboRol() throws Exception {
		logger.debug("Cargando combo de Roles");
		if(session.containsKey("LISTA_ROLES")){
			rolesLista = (ArrayList<RolesVO>) session.get("LISTA_ROLES");
		}else{
		rolesLista = principalManager.getListaRol();
		session.put("LISTA_ROLES", rolesLista);
		}
		success = true;
		return SUCCESS;
	}

	/**
	 * Metodo que obtiene una lista del objeto SeccionVO
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetComboSeccion() throws Exception {
		logger.debug("Cargando combo de Secciones");
		if(session.containsKey("LISTA_SECCIONES")){
			secciones= (ArrayList<SeccionVO>) session.get("LISTA_SECCIONES");
		}else{
		secciones = principalManager.getListaSeccion();
		session.put("LISTA_SECCIONES", secciones);
		}
		success = true;
		return SUCCESS;
	}

	/**
	 * Metodo que obtiene una lista del objeto TipoVO
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetComboTipo() throws Exception {
		tabla = "CTIPOPAGPR";
		logger.debug("Cargando combo de tipos");
		/*if(session.containsKey("LISTA_TIPOS")){
			tipos=(ArrayList<TipoVO>) session.get("LISTA_TIPOS");
		}else{*/
		tipos = principalManager.getListaTipo(tabla);
		session.put("LISTA_TIPOS", tipos);
		//}
		success = true;
		return SUCCESS;
	}

	/**
	 * Metodo que agrega una nueva configuracion a la base de datos.
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String addRoles() throws Exception {
		logger.debug("Entrando al metodo Agregar/Editar Configuracion");
		logger.debug("dsConfiguracion		:" + dsConfiguracion);
		logger.debug("claveConfiguracion	:" + claveConfiguracion);
		logger.debug("cdElemento			:" + claveElemento);
		logger.debug("cdRol					:" + claveSistemaRol);
		logger.debug("cdSeccion				:" + claveSeccion);
		logger.debug("habilitar				:" + chkHabilitado);
		logger.debug("especificacion		:" + especific);
		logger.debug("tipo					:" + tipo);
		logger.debug("archivo				:" + archivo);
		logger.debug("archivoDos			:" + archivoDos);
		logger.debug("text					:" + texto);
		logger.debug("dsRol					:" + dsSistemaRol);
		logger.debug("dsElemento			:" + dsElemento);
		logger.debug("nombreUpload1			:" + nombreUpload1);
		logger.debug("nombreUpload2			:" + nombreUpload2);
		
		session.put("NOMBRE_SECCION", claveSeccion);
		session.put("NOMBRE_TIPO", tipo);
		ConfiguracionVO configuracionVo = new ConfiguracionVO();
		configuracionVo.setClaveConfig(claveConfiguracion);
		configuracionVo.setDescripcionConfig(dsConfiguracion);
		configuracionVo.setClaveElemento(claveElemento);
		configuracionVo.setClaveRol(claveSistemaRol);
		
		List<SeccionVO> seccion = (List<SeccionVO>) ActionContext.getContext().getSession().get("LISTA_SECCIONES");
		if(StringUtils.isNotBlank(claveSeccion)){
		for (SeccionVO secc : seccion) {
			if (secc.getDsSeccion().equals(claveSeccion)) {
				configuracionVo.setClaveSeccion(secc.getCdSeccion());
				//success=true;
			}
		}
		}else{
			//success=false;
		}

		if (chkHabilitado == null || chkHabilitado.equalsIgnoreCase("false")) {
			configuracionVo.setSwHabilitado(0);
		} else {
			configuracionVo.setSwHabilitado(1);
		}
		if (StringUtils.isNotBlank(especific)) {
			configuracionVo.setEspecificacion(especific);
			//success = true;
		} else {
			if (StringUtils.isNotBlank(texto)) {
				configuracionVo.setEspecificacion(StringEscapeUtils.escapeHtml(texto));
				logger.debug("texto transfromado -->"+StringEscapeUtils.escapeHtml(texto));
				//success= true;
			}
		}
		List<TipoVO> tipoList = (List<TipoVO>) ActionContext.getContext().getSession().get("LISTA_TIPOS");
		if(StringUtils.isNotBlank(tipo)){
		for (TipoVO tip : tipoList) {
			if (tip.getValor().equals(tipo)) {
				configuracionVo.setClaveTipo(tip.getClave());
				//success = true;
			}
		}
		}else{
			//success=false;
		}
		//Se guardan las imagenes en el servidor
		String nomParam = null;
		if (cmd != null && !cmd.equals("")) {
	    	MultiPartRequestWrapper multiPartRequestWrapper = ((MultiPartRequestWrapper)ServletActionContext.getRequest());
			Enumeration<String> enumParams = multiPartRequestWrapper.getFileParameterNames();
			int i = 0;
			while (enumParams.hasMoreElements()) {
				nomParam = enumParams.nextElement();
				logger.debug("Parametro: " + nomParam);
				File[] files = multiPartRequestWrapper.getFiles(nomParam);
				String REAL = ServletActionContext.getServletContext().getRealPath(archivo);
				File file = new File(files[0].getName());
				String archivosReturn = ImageUtils.uploadAnyFile(files[0], multiPartRequestWrapper.getFileNames(nomParam)[0]);
				if (i == 0) {
					if (StringUtils.isNotBlank(archivosReturn)) {
						configuracionVo.setContenidoDato(archivosReturn);
						File nombreArchivo = new File(archivosReturn);
						configuracionVo.setArchivoLoad(nombreArchivo.getName().toString());
					}
				} else {
					configuracionVo.setContenidoDatoSeg(archivosReturn);
				}
				i++;
			}
		}

		
		if(StringUtils.isNotBlank(archivo) && StringUtils.isNotBlank(nombreUpload1)){
			
			//String extension = archivo.substring((archivo.length()-3), archivo.length());
			//logger.debug("archivo extension:"+ extension);

			String REAL = ServletActionContext.getServletContext().getRealPath(archivo); 
			logger.debug("REAL:"+ REAL); 
			
			File file = new File(archivo); 
			logger.debug("archivo Real:" + file.getAbsolutePath());
			

			
			String archivoReturn = ImageUtils.uploadAnyFile(file, nombreUpload1);		
			if(StringUtils.isNotBlank(archivoReturn)){
				logger.debug("archivoReturn:"+archivoReturn);
				configuracionVo.setContenidoDato(archivoReturn);
			
				//Se crea de nuevo el file para obtener el nombre y asignarlo a donde corresponde.

				File nombreArchivo = new File(archivoReturn);
				logger.debug("nombre de Archivo"+nombreArchivo.getName());
				configuracionVo.setArchivoLoad(nombreArchivo.getName().toString());
				//success=true;
			}else{
				//success=false;
			}
		}
		
		if(StringUtils.isNotBlank(archivoDos)){

			//String extension = archivoDos.substring((archivoDos.length()-3), archivoDos.length());
			//logger.debug("archivoDos extension:"+ extension);
			
			File otherFile = new File(archivoDos);
			String otherFileReturn = ImageUtils.uploadAnyFile(otherFile, nombreUpload2);
			if(StringUtils.isNotBlank(otherFileReturn)){
				logger.debug("otherFileReturn"+otherFileReturn);
				configuracionVo.setContenidoDatoSeg(otherFileReturn);
				//success=true;
			}
			
			
		}
		
		configuracionVo.setArchivoLoad(nombreUpload1);
		configuracionVo.setContenidoDatoSeg(nombreUpload2);
		/*principalManager.agregarconfiguracion(configuracionVo);
		success = true;
		return SUCCESS;*/
		
		String messageResult = "";
        try
        {        	
        	//messageResult = principalManager.agregarNuevaConfiguracion(configuracionVo);
        	messageResult = principalManagerJdbcTemplate.agregarNuevaConfiguracion(configuracionVo);
            success = true;
            addActionMessage(messageResult);
            if (cmd != null && !cmd.equals("")) {
	    		resultadoUpload = "{'success':true, actionMessages:['" + messageResult + "']}";
            }
            return SUCCESS;
        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
            success = false;
            if (cmd != null && !cmd.equals("")) {
				resultadoUpload = "{'success':false,'errors':{'Error':'" + e.getMessage() + "'}, actionErrors:['" + e.getMessage() + "']}";
            }
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	/**
	 * Metodo creado para editar una configuracion existente en la base de datos.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String editarRoles() throws Exception {
		logger.debug("cdConfigura-->" + cdConfigura);
		logger.debug("dsConfigura-->" + dsConfigura);
		logger.debug("dsElemen-->" + dsElemen);
		logger.debug("dsRol-->" + dsRol);
		logger.debug("dsSeccion-->" + dsSeccion);
		logger.debug("dsTipo-->" + dsTipo);
		logger.debug("swHabilitado-->" + swHabilitado);
		logger.debug("habilitado"+ habilitado);
		logger.debug("especificacion-->" + especificacion);
		logger.debug("contenido-->" + contenido);
		logger.debug("dsArchivo-->" + dsArchivo);
		logger.debug("archivo-->" + archivo);
		logger.debug("archivoDos-->"+archivoDos);

		ConfiguracionVO configuracionVo = new ConfiguracionVO();
		configuracionVo.setClaveConfig(cdConfigura);
		configuracionVo.setDescripcionConfig(dsConfigura);
		List<ClienteVO> cliente = (List<ClienteVO>) session.get("LISTA_CLIENTES");
		for (ClienteVO clien : cliente) {
			if (clien.getDescripcion().equals(dsElemen)) {
				configuracionVo.setClaveElemento(clien.getClaveCliente());
			}
		}
		List<RolesVO> rolesList = (List<RolesVO>) session.get("LISTA_ROLES");
		for (RolesVO ro : rolesList) {
			if (ro.getDsRol().equals(dsRol)) {
				configuracionVo.setClaveRol(ro.getCdRol());
			}
		}
		List<SeccionVO> seccion = (List<SeccionVO>) session.get("LISTA_SECCIONES");
		for (SeccionVO secc : seccion) {
			if (secc.getDsSeccion().equals(dsSeccion)) {
				configuracionVo.setClaveSeccion(secc.getCdSeccion());
			}
		}
		List<TipoVO> tipoList = (List<TipoVO>) session.get("LISTA_TIPOS");
		for (TipoVO tip : tipoList) {
			if (tip.getValor().equals(dsTipo)) {
				configuracionVo.setClaveTipo(tip.getClave());
			}
		}
		if(habilitado==true){
			configuracionVo.setSwHabilitado(1);
		}else{
			configuracionVo.setSwHabilitado(0);
		}
		if (StringUtils.isNotBlank(especificacion)) {
			configuracionVo.setEspecificacion(StringEscapeUtils.escapeHtml(especificacion));		
			success= true;
		}		

		if (StringUtils.isNotBlank(newArchivo)) {
			logger.debug("--El archivo entro al metodo--");
		} else {
			configuracionVo.setArchivoLoad(dsArchivo);
		}
		if(StringUtils.isNotBlank(archivo)){
			
			String extension = archivo.substring((archivo.length()-3), archivo.length());
			logger.debug("archivo extension:"+ extension);			

			File file = new File(archivo); 
			String archivoReturn = ImageUtils.uploadAnyFile(file, extension);		
			if(StringUtils.isNotBlank(archivoReturn)){
				configuracionVo.setContenidoDato(archivoReturn);
				File nombreArchivo = new File(archivoReturn);
				configuracionVo.setArchivoLoad(nombreArchivo.getName().toString());
				success=true;
			}
		}
		if(StringUtils.isNotBlank(archivoDos)){
			
			String extension = archivoDos.substring((archivoDos.length()-3), archivoDos.length());
			logger.debug("archivoDos extension:"+ extension);
			
			File otherFile = new File(archivoDos);
			String otherFileReturn = ImageUtils.uploadAnyFile(otherFile, extension);
			if(StringUtils.isNotBlank(otherFileReturn)){
				logger.debug("otherFileReturn"+otherFileReturn);
				configuracionVo.setContenidoDatoSeg(otherFileReturn);
				success=true;
			}	
		}
		principalManager.agregarconfiguracion(configuracionVo);

		success = true;
		return SUCCESS;
	}
	
	public String uploadFiles () throws Exception {
		logger.debug( "into uploadFiles " );
		logger.debug("rutaPubImage ->"+ rutaPubImage );
		//logger.debug("pruebaRuta ->"+ pruebaRuta);
		
		ConfiguracionVO configuracionVo = new ConfiguracionVO();
		String nomParam = null;
		String arrNames []= new String[2];
		List<ConfiguracionVO> listaConfiguracion = new ArrayList<ConfiguracionVO>();
		try {
			if (cmd != null && !cmd.equals("")) {
		    	MultiPartRequestWrapper multiPartRequestWrapper = ((MultiPartRequestWrapper)ServletActionContext.getRequest());
				Enumeration<String> enumParams = multiPartRequestWrapper.getFileParameterNames();
				int i = 0;
				while (enumParams.hasMoreElements()) {
					nomParam = enumParams.nextElement();
					logger.debug("Parametro: " + nomParam);
					File[] files = multiPartRequestWrapper.getFiles(nomParam);
					@SuppressWarnings("unused")
					String REAL = ServletActionContext.getServletContext().getRealPath(archivo);
					File file = new File(files[0].getName());
					String archivosReturn = ImageUtils.uploadAnyFile(files[0], multiPartRequestWrapper.getFileNames(nomParam)[0]);
					if (i == 0) {
						if (StringUtils.isNotBlank(archivosReturn)) {
							configuracionVo.setContenidoDato(archivosReturn);
							File nombreArchivo = new File(archivosReturn);
							configuracionVo.setArchivoLoad(nombreArchivo.getName().toString());
							//arrNames[i] = nombreArchivo.getName().toString();
							listaConfiguracion.add(configuracionVo);
						}
					} else {
						configuracionVo.setContenidoDatoSeg(archivosReturn);
						//arrNames[i] = archivosReturn;
						listaConfiguracion.add(configuracionVo);
					}
					i++;
				}
			}
			success = true;
			StringBuffer sb = new StringBuffer();
			sb.append("{'success':true, 'actionMessages':[], 'fileList':[");
			for (int i=0; i<listaConfiguracion.size(); i++) {
				configuracionVo = (ConfiguracionVO)listaConfiguracion.get(i);
				if (i == 0) {
					sb.append("'").append(configuracionVo.getArchivoLoad()).append("'");
				}else {
					sb.append(", '").append(configuracionVo.getContenidoDatoSeg()).append("'");
				}
			}
			sb.append("]}");
    		resultadoUpload = sb.toString();//"{'success':true, actionMessages:[" + arrNames.toString() + "]}";
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			resultadoUpload = "{'success':false,'errors':{'Error':'" + e.getMessage() + "'}, actionErrors:['" + e.getMessage() + "']}";
			return SUCCESS;
		}
	}
	@SuppressWarnings("unchecked")
	public String editarConf() throws Exception {
		logger.debug("***editarConf***");
		logger.debug("dsConfiguracion		:" + dsConfiguracion);
		logger.debug("claveConfiguracion	:" + claveConfiguracion);
		logger.debug("cdElemento			:" + claveElemento);
		logger.debug("cdRol					:" + claveSistemaRol);
		logger.debug("cdSeccion				:" + claveSeccion);
		logger.debug("especificacion		:" + especific);
		logger.debug("tipo					:" + tipo);
		logger.debug("archivo				:" + archivo);
		logger.debug("archivoDos			:" + archivoDos);
		logger.debug("text					:" + texto);
		logger.debug("dsRol					:" + dsSistemaRol);
		logger.debug("dsElemento			:" + dsElemento);
		logger.debug("chkHabilitado			:" + chkHabilitado);
		logger.debug("nombreUpload1			:" + nombreUpload1);
		logger.debug("nombreUpload2			:" + nombreUpload2);
		
		
		String nomParam = null;
		try{
			session.put("NOMBRE_SECCION", claveSeccion);
			session.put("NOMBRE_TIPO", tipo);
			
			ConfiguracionVO configuracionVo = new ConfiguracionVO();
			configuracionVo.setClaveConfig(claveConfiguracion);
			configuracionVo.setDescripcionConfig(dsConfiguracion);
			configuracionVo.setClaveElemento(claveElemento);
			configuracionVo.setClaveRol(claveSistemaRol);
			
			List<SeccionVO> seccion = (List<SeccionVO>) ActionContext.getContext().getSession().get("LISTA_SECCIONES");
			if(StringUtils.isNotBlank(claveSeccion)){
				for (SeccionVO secc : seccion) {
					if (secc.getDsSeccion().equals(claveSeccion)) {
						configuracionVo.setClaveSeccion(secc.getCdSeccion());
						success=true;
					}
				}
			}
			
			//para editar
			/*if(chkHabilitado.equalsIgnoreCase("on")){
				configuracionVo.setSwHabilitado(1);
			}else{
				configuracionVo.setSwHabilitado(0);
			}*/
			
			if(chkHabilitado==null || chkHabilitado.equals("false") || chkHabilitado.equals("")){
				configuracionVo.setSwHabilitado(0);
			}else{
				configuracionVo.setSwHabilitado(1);
			}
			
			if (StringUtils.isNotBlank(especific)) {
				configuracionVo.setEspecificacion(especific);
				success = true;
			} else {
				if (StringUtils.isNotBlank(texto)) {
					configuracionVo.setEspecificacion(StringEscapeUtils.escapeHtml(texto));
					logger.debug("texto transfromado -->"+StringEscapeUtils.escapeHtml(texto));
				}
			}
			List<TipoVO> tipoList = (List<TipoVO>) ActionContext.getContext().getSession().get("LISTA_TIPOS");
			if(StringUtils.isNotBlank(tipo)){
			for (TipoVO tip : tipoList) {
				if (tip.getValor().equals(tipo)) {
					configuracionVo.setClaveTipo(tip.getClave());
				}
			}
			}
	    	//cmd = "upload";
			//configuracionVo.setArchivoLoad(nombreUpload1);
			//configuracionVo.setContenidoDatoSeg(nombreUpload2);
			//validamos si cargo un archivo o si no se cargo archivo 
			if( StringUtils.isNotBlank(nombreUpload1) ){
				configuracionVo.setArchivoLoad(nombreUpload1);	
			}else{
				configuracionVo.setArchivoLoad(dsArchivo);
			}
			
			if( StringUtils.isNotBlank(nombreUpload2) ){
				configuracionVo.setArchivoLoad(nombreUpload2);		
			}
			
			
			
			
			/*if (cmd != null && !cmd.equals("")) {
		    	MultiPartRequestWrapper multiPartRequestWrapper = ((MultiPartRequestWrapper)ServletActionContext.getRequest());
				Enumeration<String> enumParams = multiPartRequestWrapper.getFileParameterNames();
				int i = 0;
				while (enumParams.hasMoreElements()) {
					nomParam = enumParams.nextElement();
					logger.debug("Parametro: " + nomParam);
					File[] files = multiPartRequestWrapper.getFiles(nomParam);
					String REAL = ServletActionContext.getServletContext().getRealPath(archivo);
					File file = new File(files[0].getName());
					String archivosReturn = ImageUtils.uploadAnyFile(files[0], multiPartRequestWrapper.getFileNames(nomParam)[0]);
					if (i == 0) {
						if (StringUtils.isNotBlank(archivosReturn)) {
							configuracionVo.setContenidoDato(archivosReturn);
							File nombreArchivo = new File(archivosReturn);
							configuracionVo.setArchivoLoad(nombreArchivo.getName().toString());
						}
					} else {
						configuracionVo.setContenidoDatoSeg(archivosReturn);
					}
					i++;
				}
			}
			//Se guardan las imagenes en el servidor
			if(StringUtils.isNotBlank(archivo) && StringUtils.isNotBlank(nombreUpload1)){
				
				//String extension = archivo.substring((archivo.length()-3), archivo.length());
				//logger.debug("archivo extension:"+ extension);	
				
				String REAL = ServletActionContext.getServletContext().getRealPath(archivo); 
				logger.debug("REAL:"+ REAL); 
				
				File file = new File(archivo); 
				logger.debug("archivo Real:" + file.getAbsolutePath()); 
				
				String archivoReturn = ImageUtils.uploadAnyFile(file, nombreUpload1);		
				if(StringUtils.isNotBlank(archivoReturn)){
					logger.debug("archivoReturn:"+archivoReturn);
					configuracionVo.setContenidoDato(archivoReturn);
				
					//Se crea de nuevo el file para obtener el nombre y asignarlo a donde corresponde.
	
					File nombreArchivo = new File(archivoReturn);
					logger.debug("nombre de Archivo"+nombreArchivo.getName());
					configuracionVo.setArchivoLoad(nombreArchivo.getName().toString());
				}
			}
			
			if(StringUtils.isNotBlank(archivoDos) && StringUtils.isNotBlank(nombreUpload2)){
				
				//String extension = archivoDos.substring((archivoDos.length()-3), archivoDos.length());
				//logger.debug("archivoDos extension:"+ extension);
				
				File otherFile = new File(archivoDos);
				String otherFileReturn = ImageUtils.uploadAnyFile(otherFile, nombreUpload2);
				if(StringUtils.isNotBlank(otherFileReturn)){
					logger.debug("otherFileReturn"+otherFileReturn);
					configuracionVo.setContenidoDatoSeg(otherFileReturn);
				}
			}
			*/
			//principalManager.agregarconfiguracion(configuracionVo);
			String messageResult = "";
	        //try
	        //{        	
	        	//messageResult = principalManager.editarNuevaConfiguracion(configuracionVo);
				messageResult = principalManagerJdbcTemplate.editarNuevaConfiguracion(configuracionVo);
	            success = true;
	            addActionMessage(messageResult);
	    		resultadoUpload = "{'success':true, actionMessages:['" + messageResult + "']}";
	            return SUCCESS;
	        //}
	            /*catch(ApplicationException e)
			{
	            success = false;
	            addActionError(e.getMessage());
	            return SUCCESS;

	        }catch( Exception e){
	            success = false;
	            addActionError(e.getMessage());
				resultadoUpload = "{'success':false,'errors':{'Error':'" + e.getMessage() + "'}}";
	            return SUCCESS;
	        }*/
		
		}catch(Exception e){
			resultadoUpload = "{'success':false,'errors':{'Error':'" + e.getMessage() + "'}, actionErrors:['" + e.getMessage() + "']}";
			logger.debug("Exception :"+e);
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPathFile() {
		return pathFile;
	}
	/**
	 * 
	 * @param pathFile
	 */
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	/**
	 * 
	 * @return
	 */
	public File getArchivoImagen() {
		return archivoImagen;
	}
	/**
	 * 
	 * @param archivoImagen
	 */
	public void setArchivoImagen(File archivoImagen) {
		this.archivoImagen = archivoImagen;
	}
	/**
	 * 
	 * @return
	 */
	public String getCdConfigura() {
		return cdConfigura;
	}
	/**
	 * 
	 * @param cdConfigura
	 */
	public void setCdConfigura(String cdConfigura) {
		this.cdConfigura = cdConfigura;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsConfigura() {
		return dsConfigura;
	}
	/**
	 * 
	 * @param dsConfigura
	 */
	public void setDsConfigura(String dsConfigura) {
		this.dsConfigura = dsConfigura;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsElemen() {
		return dsElemen;
	}
	/**
	 * 
	 * @param dsElemen
	 */
	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsRol() {
		return dsRol;
	}
	/**
	 * 
	 * @param dsRol
	 */
	public void setDsRol(String dsRol) {
		this.dsRol = dsRol;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsSeccion() {
		return dsSeccion;
	}
	/**
	 * 
	 * @param dsSeccion
	 */
	public void setDsSeccion(String dsSeccion) {
		this.dsSeccion = dsSeccion;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsTipo() {
		return dsTipo;
	}
	/**
	 * 
	 * @param dsTipo
	 */
	public void setDsTipo(String dsTipo) {
		this.dsTipo = dsTipo;
	}
	/**
	 * 
	 * @return
	 */
	public int getSwHabilitado() {
		return swHabilitado;
	}
	/**
	 * 
	 * @param swHabilitado
	 */
	public void setSwHabilitado(int swHabilitado) {
		this.swHabilitado = swHabilitado;
	}
	/**
	 * 
	 * @return
	 */
	public String getEspecificacion() {
		return especificacion;
	}
	/**
	 * 
	 * @param especificacion
	 */
	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	}
	/**
	 * 
	 * @return
	 */
	public String getContenido() {
		return contenido;
	}
	/**
	 * 
	 * @param contenido
	 */
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsArchivo() {
		return dsArchivo;
	}
	/**
	 * 
	 * @param dsArchivo
	 */
	public void setDsArchivo(String dsArchivo) {
		this.dsArchivo = dsArchivo;
	}
	/**
	 * 
	 * @return
	 */
	public String getEspecific() {
		return especific;
	}
	/**
	 * 
	 * @param especific
	 */
	public void setEspecific(String especific) {
		this.especific = especific;
	}
	/**
	 * 
	 * @return
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 
	 * @return
	 */
	public ConfiguracionVO getConfiguracionVo() {
		return configuracionVo;
	}
	/**
	 * 
	 * @param configuracionVo
	 */
	public void setConfiguracionVo(ConfiguracionVO configuracionVo) {
		this.configuracionVo = configuracionVo;
	}
	/**
	 * 
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveCliente() {
		return claveCliente;
	}
	/**
	 * 
	 * @param claveCliente
	 */
	public void setClaveCliente(String claveCliente) {
		this.claveCliente = claveCliente;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveRol() {
		return claveRol;
	}
	/**
	 * 
	 * @param claveRol
	 */
	public void setClaveRol(String claveRol) {
		this.claveRol = claveRol;
	}
	/**
	 * 	
	 * @return
	 */
	public String getClaveSeccion() {
		return claveSeccion;
	}
	/**
	 * 
	 * @param claveSeccion
	 */
	public void setClaveSeccion(String claveSeccion) {
		this.claveSeccion = claveSeccion;
	}
	/**
	 * 
	 * @return
	 */
	public int getHabilitar() {
		return habilitar;
	}
	/**
	 * 
	 * @param habilitar
	 */
	public void setHabilitar(int habilitar) {
		this.habilitar = habilitar;
	}
	/**
	 * 
	 * @return
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * 
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * 
	 * @return
	 */
	public String getTabla() {
		return tabla;
	}
	/**
	 * 
	 * @param tabla
	 */
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<TipoVO> getTipos() {
		return tipos;
	}
	/**
	 * 
	 * @param tipos
	 */
	public void setTipos(ArrayList<TipoVO> tipos) {
		this.tipos = tipos;
	}
	/**
	 * 
	 * @return
	 */
	public RolVO getRolVo() {
		return rolVo;
	}
	/**
	 * 
	 * @param rolVo
	 */
	public void setRolVo(RolVO rolVo) {
		this.rolVo = rolVo;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<RolesVO> getRolesLista() {
		return rolesLista;
	}
	/**
	 * 
	 * @param rolesLista
	 */
	public void setRolesLista(ArrayList<RolesVO> rolesLista) {
		this.rolesLista = rolesLista;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<SeccionVO> getSecciones() {
		return secciones;
	}
	/**
	 * 
	 * @param secciones
	 */
	public void setSecciones(ArrayList<SeccionVO> secciones) {
		this.secciones = secciones;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<ClienteVO> getClientes() {
		return clientes;
	}
	/**
	 * 
	 * @param clientes
	 */
	public void setClientes(ArrayList<ClienteVO> clientes) {
		this.clientes = clientes;
	}
	/**
	 * 
	 * @param principalManager
	 */
	public void setPrincipalManager(PrincipalManager principalManager) {
		this.principalManager = principalManager;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * 
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * 
	 * @return
	 */
	/*public ArrayList<RolVO> getRoles() {
		return roles;
	}*/
	/**
	 * 
	 * @param roles
	 */
	public void setRoles(ArrayList<RolVO> roles) {
		this.roles = roles;
	}
	/**
	 * 
	 * @return
	 */
	public String getSeccionForm() {
		return seccionForm;
	}
	/**
	 * 
	 * @param seccionForm
	 */
	public void setSeccionForm(String seccionForm) {
		this.seccionForm = seccionForm;
	}
	/**
	 * 
	 * @return
	 */
	public String getArchivo() {
		return archivo;
	}
	/**
	 * 
	 * @param archivo
	 */
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	/**
	 * 
	 * @return
	 */
	public String getNewArchivo() {
		return newArchivo;
	}
	/**
	 * 
	 * @param newArchivo
	 */
	public void setNewArchivo(String newArchivo) {
		this.newArchivo = newArchivo;
	}
	/**
	 * 
	 * @return
	 */
	public String getTexto() {
		return texto;
	}
	/**
	 * 
	 * @param texto
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}
	/**
	 * 
	 * @return
	 */
	public String getArchivoDos() {
		return archivoDos;
	}
	/**
	 * 
	 * @param archivoDos
	 */
	public void setArchivoDos(String archivoDos) {
		this.archivoDos = archivoDos;
	}
	/**
	 * 
	 * @return
	 */
	public String getContenidoSec() {
		return contenidoSec;
	}
	/**
	 * 
	 * @param contenidoSec
	 */
	public void setContenidoSec(String contenidoSec) {
		this.contenidoSec = contenidoSec;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveConfiguracion() {
		return claveConfiguracion;
	}
	/**
	 * 
	 * @param claveConfiguracion
	 */
	public void setClaveConfiguracion(String claveConfiguracion) {
		this.claveConfiguracion = claveConfiguracion;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveSistemaRol() {
		return claveSistemaRol;
	}
	/**
	 * 
	 * @param claveSistemaRol
	 */
	public void setClaveSistemaRol(String claveSistemaRol) {
		this.claveSistemaRol = claveSistemaRol;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveElemento() {
		return claveElemento;
	}
	/**
	 * 
	 * @param claveElemento
	 */
	public void setClaveElemento(String claveElemento) {
		this.claveElemento = claveElemento;
	}
	/**
	 * 
	 * @return
	 */
	public int getNumeroFila() {
		return numeroFila;
	}
	/**
	 * 
	 * @param numeroFila
	 */
	public void setNumeroFila(int numeroFila) {
		this.numeroFila = numeroFila;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsConfiguracion() {
		return dsConfiguracion;
	}
	/**
	 * 
	 * @param dsConfiguracion
	 */
	public void setDsConfiguracion(String dsConfiguracion) {
		this.dsConfiguracion = dsConfiguracion;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getDsSistemaRol() {
		return dsSistemaRol;
	}

	public void setDsSistemaRol(String dsSistemaRol) {
		this.dsSistemaRol = dsSistemaRol;
	}

	public String getDsElemento() {
		return dsElemento;
	}

	public void setDsElemento(String dsElemento) {
		this.dsElemento = dsElemento;
	}

	public String getChkHabilitado() {
		return chkHabilitado;
	}

	public void setChkHabilitado(String chkHabilitado) {
		this.chkHabilitado = chkHabilitado;
	}

	public String getNombreUpload1() {
		return nombreUpload1;
	}

	public void setNombreUpload1(String nombreUpload1) {
		this.nombreUpload1 = nombreUpload1;
	}

	public String getNombreUpload2() {
		return nombreUpload2;
	}

	public void setNombreUpload2(String nombreUpload2) {
		this.nombreUpload2 = nombreUpload2;
	}

	public String getResultadoUpload() {
		return resultadoUpload;
	}

	public void setResultadoUpload(String resultadoUpload) {
		this.resultadoUpload = resultadoUpload;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<RolVO> getRoles() {
		return roles;
	}

	public void setPrincipalManagerJdbcTemplate(
			PrincipalManager principalManagerJdbcTemplate) {
		this.principalManagerJdbcTemplate = principalManagerJdbcTemplate;
	}

	/**
	 * @param rutaPubImage the rutaPubImage to set
	 */
	public void setRutaPubImage(String rutaPubImage) {
		this.rutaPubImage = rutaPubImage;
	}

	/**
	 * @return the rutaPubImage
	 */
	public String getRutaPubImage() {
		return rutaPubImage;
	}
		
	

}
