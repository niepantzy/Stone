package com.stone.game.template;
import com.stone.core.annotation.TemplateClass;
/**
 * The template of ItemTemplate( 物品模版 );<br>
 *
 * Auto generated by tools, do not modified;
 *
 * @author crazyjohn
 *
 */
 @TemplateClass(templateFile = "xml/ItemTemplate.xml")
public class ItemTemplate {
    /** ids */
	protected int id;
    /**  售价s */
	protected long buyCoin;
    /**  描述s */
	protected String desc;
    /**  属性列表s */
	protected List<com.stone.tools.template.Attribute> attributes;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public long getBuyCoin() {
		return buyCoin;
	}
	
	public void setBuyCoin(long buyCoin) {
		this.buyCoin = buyCoin;
	}
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<com.stone.tools.template.Attribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<com.stone.tools.template.Attribute> attributes) {
		this.attributes = attributes;
	}

}