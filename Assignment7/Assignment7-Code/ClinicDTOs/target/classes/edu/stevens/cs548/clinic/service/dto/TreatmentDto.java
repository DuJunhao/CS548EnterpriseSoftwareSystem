//
// \u6b64\u6587\u4ef6\u662f\u7531 JavaTM Architecture for XML Binding (JAXB) \u5f15\u7528\u5b9e\u73b0 v2.2.11 \u751f\u6210\u7684
// \u8bf7\u8bbf\u95ee <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// \u5728\u91cd\u65b0\u7f16\u8bd1\u6e90\u6a21\u5f0f\u65f6, \u5bf9\u6b64\u6587\u4ef6\u7684\u6240\u6709\u4fee\u6539\u90fd\u5c06\u4e22\u5931\u3002
// \u751f\u6210\u65f6\u95f4: 2017.10.24 \u65f6\u95f4 11:39:19 AM EDT 
//


package edu.stevens.cs548.clinic.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type\u7684 Java \u7c7b\u3002
 * 
 * <p>\u4ee5\u4e0b\u6a21\u5f0f\u7247\u6bb5\u6307\u5b9a\u5305\u542b\u5728\u6b64\u7c7b\u4e2d\u7684\u9884\u671f\u5185\u5bb9\u3002
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="diagnosis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="patient" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="DrugTreatment" type="{http://www.example.org/clinic/schemas/patient}DrugTreatmentType"/&gt;
 *           &lt;element name="Radiology" type="{http://www.example.org/clinic/schemas/patient}RadiologyType"/&gt;
 *           &lt;element name="Surgery" type="{http://www.example.org/clinic/schemas/patient}SurgeryType"/&gt;
 *         &lt;/choice&gt;
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
    "diagnosis",
    "patient",
    "provider",
    "drugTreatment",
    "radiology",
    "surgery"
})
@XmlRootElement(name = "treatment-dto")
public class TreatmentDto {

    protected long id;
    @XmlElement(required = true)
    protected String diagnosis;
    protected long patient;
    protected long provider;
    @XmlElement(name = "DrugTreatment")
    protected DrugTreatmentType drugTreatment;
    @XmlElement(name = "Radiology")
    protected RadiologyType radiology;
    @XmlElement(name = "Surgery")
    protected SurgeryType surgery;

    /**
     * \u83b7\u53d6id\u5c5e\u6027\u7684\u503c\u3002
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * \u8bbe\u7f6eid\u5c5e\u6027\u7684\u503c\u3002
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * \u83b7\u53d6diagnosis\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * \u8bbe\u7f6ediagnosis\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiagnosis(String value) {
        this.diagnosis = value;
    }

    /**
     * \u83b7\u53d6patient\u5c5e\u6027\u7684\u503c\u3002
     * 
     */
    public long getPatient() {
        return patient;
    }

    /**
     * \u8bbe\u7f6epatient\u5c5e\u6027\u7684\u503c\u3002
     * 
     */
    public void setPatient(long value) {
        this.patient = value;
    }

    /**
     * \u83b7\u53d6provider\u5c5e\u6027\u7684\u503c\u3002
     * 
     */
    public long getProvider() {
        return provider;
    }

    /**
     * \u8bbe\u7f6eprovider\u5c5e\u6027\u7684\u503c\u3002
     * 
     */
    public void setProvider(long value) {
        this.provider = value;
    }

    /**
     * \u83b7\u53d6drugTreatment\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @return
     *     possible object is
     *     {@link DrugTreatmentType }
     *     
     */
    public DrugTreatmentType getDrugTreatment() {
        return drugTreatment;
    }

    /**
     * \u8bbe\u7f6edrugTreatment\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @param value
     *     allowed object is
     *     {@link DrugTreatmentType }
     *     
     */
    public void setDrugTreatment(DrugTreatmentType value) {
        this.drugTreatment = value;
    }

    /**
     * \u83b7\u53d6radiology\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @return
     *     possible object is
     *     {@link RadiologyType }
     *     
     */
    public RadiologyType getRadiology() {
        return radiology;
    }

    /**
     * \u8bbe\u7f6eradiology\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @param value
     *     allowed object is
     *     {@link RadiologyType }
     *     
     */
    public void setRadiology(RadiologyType value) {
        this.radiology = value;
    }

    /**
     * \u83b7\u53d6surgery\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @return
     *     possible object is
     *     {@link SurgeryType }
     *     
     */
    public SurgeryType getSurgery() {
        return surgery;
    }

    /**
     * \u8bbe\u7f6esurgery\u5c5e\u6027\u7684\u503c\u3002
     * 
     * @param value
     *     allowed object is
     *     {@link SurgeryType }
     *     
     */
    public void setSurgery(SurgeryType value) {
        this.surgery = value;
    }

}
