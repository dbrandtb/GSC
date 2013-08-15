package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.PersonasVO;
import mx.com.aon.portal.model.UsuarioClaveVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PersonasManager;
import mx.com.aon.portal.util.Util;


/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Personas.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaPersonasAction extends AbstractListAction {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaPersonasAction.class);
	private PersonasManager personasManager;
	
	private List<PersonasVO> mListaPersonas;
	private List<UsuarioClaveVO> mListaUsuarioSinPersonas;
	
	private String codTipoPersonaJ;
	private String codCorporativo;
	private String nombre;
	private String rfc;
	
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
    /**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Metodo que realiza la busqueda de datos de persona
	 * en base a codigo de tipo persona, codigo corporativo, nombre, referencia 
	 * 
	 * @param
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarPersonas () throws Exception {
		try {
			PagedList pagedList = personasManager.buscarPersonas(codTipoPersonaJ, codCorporativo, nombre, rfc, start, limit);
			mListaPersonas = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
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
	}

	/**
	 * Metodo que realiza la busqueda de datos de usuario sin persona
	 * en base a el nombre 
	 * 
	 * @param
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarUsuarioSinPersonas() throws Exception {
		try {
			PagedList pagedList = personasManager.buscarUsuarioSinPersona(nombre, start, limit);
			mListaUsuarioSinPersonas = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
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
	}	
	

	
	/**
	 * Metodo que busca un conjunto de personas y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarClick() throws Exception {
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "Personas." + exportFormat.getExtension();
			TableModelExport model = personasManager.getModel(codTipoPersonaJ, codCorporativo, nombre, rfc);
			inputStream = exportFormat.export(model);
			success = true;
		} catch (Exception e) {
			success = false;
		}
		return SUCCESS;
	}
	public String getCodTipoPersonaJ() {
		return codTipoPersonaJ;
	}

	public void setCodTipoPersonaJ(String codTipoPersonaJ) {
		this.codTipoPersonaJ = codTipoPersonaJ;
	}

	public String getCodCorporativo() {
		return codCorporativo;
	}

	public void setCodCorporativo(String codCorporativo) {
		this.codCorporativo = codCorporativo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public void setPersonasManager(PersonasManager personasManager) {
		this.personasManager = personasManager;
	}


	public List<PersonasVO> getMListaPersonas() {
		return mListaPersonas;
	}


	public void setMListaPersonas(List<PersonasVO> listaPersonas) {
		mListaPersonas = listaPersonas;
	}


	public String getFormato() {
		return formato;
	}


	public void setFormato(String formato) {
		this.formato = formato;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public PersonasManager obtenPersonasManager() {
		return personasManager;
	}


	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}

	public List<UsuarioClaveVO> getMListaUsuarioSinPersonas() {
		return mListaUsuarioSinPersonas;
	}

	public void setMListaUsuarioSinPersonas(
			List<UsuarioClaveVO> listaUsuarioSinPersonas) {
		mListaUsuarioSinPersonas = listaUsuarioSinPersonas;
	}

	
}
