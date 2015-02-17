
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteMDMJobResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteMDMJobResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://www.talend.com/mdm}wsBoolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteMDMJobResponse", propOrder = {
    "_return"
})
public class DeleteMDMJobResponse {

    @XmlElement(name = "return")
    protected WsBoolean _return;

    /**
     * Default no-arg constructor
     * 
     */
    public DeleteMDMJobResponse() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public DeleteMDMJobResponse(final WsBoolean _return) {
        this._return = _return;
    }

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link WsBoolean }
     *     
     */
    public WsBoolean getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsBoolean }
     *     
     */
    public void setReturn(WsBoolean value) {
        this._return = value;
    }

}