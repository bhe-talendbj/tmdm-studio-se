
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WSGetRoutingOrderV2ByCriteriaWithPaging complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSGetRoutingOrderV2ByCriteriaWithPaging">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wsSearchCriteriaWithPaging" type="{http://www.talend.com/mdm}WSRoutingOrderV2SearchCriteriaWithPaging" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSGetRoutingOrderV2ByCriteriaWithPaging", propOrder = {
    "wsSearchCriteriaWithPaging"
})
public class WSGetRoutingOrderV2ByCriteriaWithPaging {

    protected WSRoutingOrderV2SearchCriteriaWithPaging wsSearchCriteriaWithPaging;

    /**
     * Default no-arg constructor
     * 
     */
    public WSGetRoutingOrderV2ByCriteriaWithPaging() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSGetRoutingOrderV2ByCriteriaWithPaging(final WSRoutingOrderV2SearchCriteriaWithPaging wsSearchCriteriaWithPaging) {
        this.wsSearchCriteriaWithPaging = wsSearchCriteriaWithPaging;
    }

    /**
     * Gets the value of the wsSearchCriteriaWithPaging property.
     * 
     * @return
     *     possible object is
     *     {@link WSRoutingOrderV2SearchCriteriaWithPaging }
     *     
     */
    public WSRoutingOrderV2SearchCriteriaWithPaging getWsSearchCriteriaWithPaging() {
        return wsSearchCriteriaWithPaging;
    }

    /**
     * Sets the value of the wsSearchCriteriaWithPaging property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSRoutingOrderV2SearchCriteriaWithPaging }
     *     
     */
    public void setWsSearchCriteriaWithPaging(WSRoutingOrderV2SearchCriteriaWithPaging value) {
        this.wsSearchCriteriaWithPaging = value;
    }

}
