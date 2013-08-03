package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.model.TreeViewVO;
import mx.com.aon.catbo.service.OperacionCATManager;
import mx.com.aon.portal.service.PagedList;

public class TreeViewAction extends AbstractListAction {

	private List<TreeViewVO> listaDatos;
	OperacionCATVO operacionCATVO = new OperacionCATVO();
	OperacionCATVO operacionCATVO_Grupo = new OperacionCATVO();
	OperacionCATVO operacionCATVO_Proc = new OperacionCATVO();
	
	private List<OperacionCATVO> mEstructuraList;
	private List<OperacionCATVO> lista;
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient OperacionCATManager operacionCATManager;
	
	
	private boolean success;
    
	private String ultimaAseguradora="";
	private String ultimoGrupo="";
	private String ultimoProceso="";
	private String CdTipGui;
	private String cdElemento;
	
	
/*	@SuppressWarnings("unchecked")
	public String traeDialogos() throws Exception{
		try{
			@SuppressWarnings("unused")
			List lista =  operacionCATManager.buscarGuionesDisp();
			mEstructuraList=lista;
			//lista.size()	
			
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}*/
	
	@SuppressWarnings("unchecked")
	public String traeDialogos() throws Exception{
		try{
			@SuppressWarnings("unused")
			List lista =  operacionCATManager.buscarGuionesDisp(CdTipGui,cdElemento);
			mEstructuraList=lista;
			//lista.size()	
			
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	
	@SuppressWarnings("unchecked")
	public String cmdObtenerDatos () throws Exception {
		
		 
		
		listaDatos = new ArrayList();
		traeDialogos();
		ultimaAseguradora="";
		for (int i=0; i<mEstructuraList.size();  i++) {
			
			operacionCATVO = (OperacionCATVO)mEstructuraList.get(i);
			
			if (!ultimaAseguradora.equals(operacionCATVO.getCdUniEco().toString()) ){
				
				ultimaAseguradora=operacionCATVO.getCdUniEco();
				TreeViewVO treeViewVO = new TreeViewVO();
				treeViewVO.setCollapsible(true);
			    treeViewVO.setLeaf(false);
			    treeViewVO.setId(operacionCATVO.getCdUniEco());
			    treeViewVO.setCls("folder");
			    treeViewVO.setText(operacionCATVO.getDsUniEco());
			    treeViewVO.setParent("0");
			    treeViewVO.setSingleClickExpanded(true);
			    treeViewVO.setType("location");
			    
			    List<TreeViewVO> children = new ArrayList<TreeViewVO>();
			    ultimoGrupo="";
			    for (int j=0; j<mEstructuraList.size(); j++) {
			    	
			    	operacionCATVO_Grupo = (OperacionCATVO)mEstructuraList.get(j);
			    	
			    	if ((operacionCATVO_Grupo.getCdUniEco().equals(ultimaAseguradora.toString()) ) && (!operacionCATVO_Grupo.getCdElemento().equals(ultimoGrupo.toString()) )){
			    	
			    	ultimoGrupo = operacionCATVO_Grupo.getCdElemento();
			    	TreeViewVO treeViewHVO = new TreeViewVO();
			    	treeViewHVO.setCollapsible(true);
			    	treeViewHVO.setLeaf(false);
			    	treeViewHVO.setId(operacionCATVO_Grupo.getCdElemento()+ ultimaAseguradora);
			    	treeViewHVO.setCls("folder");
			    	treeViewHVO.setText(operacionCATVO_Grupo.getDsElemen());
			    	treeViewHVO.setParent(operacionCATVO.getCdUniEco());
			    	treeViewHVO.setSingleClickExpanded(true);
			    	treeViewHVO.setType("location");
			    	treeViewVO.setChildren(children);
			    	children.add(treeViewHVO);
			    	
			    	List<TreeViewVO> children_1 = new ArrayList<TreeViewVO>();
			    	ultimoProceso="";
					for (int k=0; k<mEstructuraList.size(); k++) {
						
						operacionCATVO_Proc = (OperacionCATVO)mEstructuraList.get(k);
						
						if ((operacionCATVO_Proc.getCdUniEco().equals(ultimaAseguradora.toString()) ) && (operacionCATVO_Proc.getCdElemento().equals(ultimoGrupo.toString()) )&& (!operacionCATVO_Proc.getCdProceso().equals(ultimoProceso.toString()) )){
							
							ultimoProceso = operacionCATVO_Proc.getCdProceso();
							TreeViewVO treeViewH1VO = new TreeViewVO();
							treeViewH1VO.setCollapsible(true);
							treeViewH1VO.setLeaf(true);
							treeViewH1VO.setId(operacionCATVO_Proc.getCdRamo() + "/" + operacionCATVO_Proc.getCdGuion() + "/" + operacionCATVO_Proc.getCdProceso() + "/" + ultimoGrupo + "/" + ultimaAseguradora);
							
							
							treeViewH1VO.setCls("folder");
							treeViewH1VO.setText(operacionCATVO_Proc.getDsProceso());
							treeViewH1VO.setParent(operacionCATVO_Grupo.getCdElemento()+ ultimaAseguradora);
							treeViewH1VO.setSingleClickExpanded(true);
							treeViewH1VO.setType("location");
							treeViewHVO.setChildren(children_1);
							children_1.add(treeViewH1VO);	
						}
						
					}
			    	
			    	}
			    	
			    }
			 
			    listaDatos.add(treeViewVO);
				
				}
		}
		success = true;
		return SUCCESS;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<TreeViewVO> getListaDatos() {
		return listaDatos;
	}

	public List<OperacionCATVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<OperacionCATVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public void setListaDatos(List<TreeViewVO> listaDatos) {
		this.listaDatos = listaDatos;
	}

	public void setOperacionCATManager(OperacionCATManager operacionCATManager) {
		this.operacionCATManager = operacionCATManager;
	}


	public OperacionCATVO getOperacionCATVO() {
		return operacionCATVO;
	}


	public void setOperacionCATVO(OperacionCATVO operacionCATVO) {
		this.operacionCATVO = operacionCATVO;
	}

	public String getUltimaAseguradora() {
		return ultimaAseguradora;
	}

	public void setUltimaAseguradora(String ultimaAseguradora) {
		this.ultimaAseguradora = ultimaAseguradora;
	}

	public String getUltimoGrupo() {
		return ultimoGrupo;
	}

	public void setUltimoGrupo(String ultimoGrupo) {
		this.ultimoGrupo = ultimoGrupo;
	}

	public String getUltimoProceso() {
		return ultimoProceso;
	}

	public void setUltimoProceso(String ultimoProceso) {
		this.ultimoProceso = ultimoProceso;
	}

	public String getCdTipGui() {
		return CdTipGui;
	}

	public void setCdTipGui(String cdTipGui) {
		CdTipGui = cdTipGui;
	}


	public String getCdElemento() {
		return cdElemento;
	}


	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}


		
}