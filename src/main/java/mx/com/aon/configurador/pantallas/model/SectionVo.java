package mx.com.aon.configurador.pantallas.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 *  Clase Value Object para los atributos del master 
 *  NOTA:esta repetida dentro de la carpeta master
 * 
 * @author  aurora.lozada
 * 
 */
@Deprecated
public class SectionVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -143759424265034410L;

	private String dsBlock;
	
	private String cdBlock;
	
	private List<FieldVo> fieldList;
	
	
	public String getDsBlock() {
		return dsBlock;
	}
	public void setDsBlock(String dsBlock) {
		this.dsBlock = dsBlock;
	}
	public String getCdBlock() {
		return cdBlock;
	}
	public void setCdBlock(String cdBlock) {
		this.cdBlock = cdBlock;
	}
	public List<FieldVo> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<FieldVo> fieldList) {
		this.fieldList = fieldList;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer fieldBuffer = new StringBuffer();		
		String agrupador = null;
		FieldVo field = null;		
		int size = fieldList.size();
		boolean iterar = false;
		
//		for (int index = 0; index < size; index ++ ){
//			field = fieldList.get(index);
//			
//			fieldBuffer.append("['").append(cdBlock).append("','").append(field.getDsField()).append("','',").append(field.getControlItem().toString()).append("]");
//			
//			if( (index + 1) < size ){
//				fieldBuffer.append(",");
//			}
//			
//		}
		
		
		
		for(int index =0; index < size; index++){
			field = fieldList.get(index);
			
			if( StringUtils.isBlank(field.getAgrupador())){
				fieldBuffer.append("['").append(cdBlock).append("','").append(field.getDsField()).append("','',").append(field.getControlItem().toString()).append("]");
			}else{
				agrupador = field.getAgrupador();
				fieldBuffer.append("['").append(dsBlock).append("','").append(field.getDsField()).append("','',");
				fieldBuffer.append("function(add,parent){ var config = { xtype:'fieldset',title:'Legend',autoHeight:true,items:[] }; config.items.push(");
				iterar = true;
				
				while(iterar){
					fieldBuffer.append( fieldList.get(index).getControlItem().toString() );
					
					if( (index + 1) < size && agrupador.equals( fieldList.get(index + 1).getAgrupador()  )  ){
						fieldBuffer.append(",");
						++index;
					}else{
						iterar = false;
					}
				}
				
				fieldBuffer.append("); add.call(this, config); } ]");
				
			}
			
			if( (index + 1) < size ){
				fieldBuffer.append(",");
			}
			
			
		}
		
		
//		
//		for( int index =0; index < size; index ++ ){
//			field = fieldList.get(index);
//			
//			if(  StringUtils.isBlank(field.getAgrupador()) ){
//				fieldBuffer.append("['").append(dsBlock).append("','").append(field.getDsField()).append("','',").append(field.getControlItem().toString()).append("]");				
//			}else{
//				agrupador = field.getAgrupador();
//				fieldBuffer.append("['").append(dsBlock).append("','").append(field.getDsField()).append("','',");
//				fieldBuffer.append("function(add,parent){ var config = { xtype:'fieldset',title:'Legend',autoHeight:true,items:[] }; config.push(");
//				boolean iterar = true;
//				
//				while (iterar){
//					fieldBuffer.append(field.getControlItem().toString());
//					
//					if( ( index + 1 )  < size  ){
//						++index;
//						field = fieldList.get(index);
//						if( agrupador.equals(field.getAgrupador()) ) {
//							fieldBuffer.append(",");
//						}else{
//							iterar = false;
//							--index;
//						}
//					}else{
//						iterar = false;
//					}
//					
//				}
//				
//				fieldBuffer.append("); add.call(this, config); } ]");
//				
//			}
//			
//			if( index < size){
//				fieldBuffer.append(",");
//			}
//			
//		}
		
		
		
		return fieldBuffer.toString();
	}
	
	
	
	
	
	

}
