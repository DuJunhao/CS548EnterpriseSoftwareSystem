//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.11 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2017.12.03 时间 11:22:57 PM EST 
//


package edu.stevens.cs548.clinic.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="patient-id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="treatments" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "patientId",
    "name",
    "dob",
    "age",
    "treatments"
})
@XmlRootElement(name = "patient-dto")
public class PatientDto
    implements Serializable
{

    private final static long serialVersionUID = 0L;
    protected long id;
    @XmlElement(name = "patient-id")
    protected long patientId;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected Date dob;
    protected int age;
    @XmlElement(nillable = true)
    protected List<Long> treatments;

    /**
     * 获取id属性的值。
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * 获取patientId属性的值。
     * 
     */
    public long getPatientId() {
        return patientId;
    }

    /**
     * 设置patientId属性的值。
     * 
     */
    public void setPatientId(long value) {
        this.patientId = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取dob属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDob() {
        return dob;
    }

    /**
     * 设置dob属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDob(Date value) {
        this.dob = value;
    }

    /**
     * 获取age属性的值。
     * 
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置age属性的值。
     * 
     */
    public void setAge(int value) {
        this.age = value;
    }

    /**
     * Gets the value of the treatments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the treatments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTreatments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getTreatments() {
        if (treatments == null) {
            treatments = new ArrayList<Long>();
        }
        return this.treatments;
    }

}
