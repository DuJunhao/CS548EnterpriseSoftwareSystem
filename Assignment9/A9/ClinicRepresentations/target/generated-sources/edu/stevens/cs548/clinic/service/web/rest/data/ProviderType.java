//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.11 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2017.12.03 时间 11:23:00 PM EST 
//


package edu.stevens.cs548.clinic.service.web.rest.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.stevens.cs548.clinic.service.web.rest.data.dap.LinkType;


/**
 * <p>ProviderType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ProviderType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://cs548.stevens.edu/clinic/service/web/rest/data/dap}LinkType"/&gt;
 *         &lt;element name="NPI" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="specialization" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProviderType", propOrder = {
    "id",
    "npi",
    "specialization"
})
public class ProviderType {

    @XmlElement(required = true)
    protected LinkType id;
    @XmlElement(name = "NPI")
    protected long npi;
    @XmlElement(required = true)
    protected String specialization;

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link LinkType }
     *     
     */
    public LinkType getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link LinkType }
     *     
     */
    public void setId(LinkType value) {
        this.id = value;
    }

    /**
     * 获取npi属性的值。
     * 
     */
    public long getNPI() {
        return npi;
    }

    /**
     * 设置npi属性的值。
     * 
     */
    public void setNPI(long value) {
        this.npi = value;
    }

    /**
     * 获取specialization属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * 设置specialization属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialization(String value) {
        this.specialization = value;
    }

}
