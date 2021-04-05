
package me.freo.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resultcode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="refusalreason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "authcode",
    "reference",
    "resultcode",
    "refusalreason"
})
@XmlRootElement(name = "authoriseResponse")
public class AuthoriseResponse {

    @XmlElement(required = true)
    protected String authcode;
    @XmlElement(required = true)
    protected String reference;
    protected int resultcode;
    @XmlElement(required = true)
    protected String refusalreason;

    /**
     * Gets the value of the authcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthcode() {
        return authcode;
    }

    /**
     * Sets the value of the authcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthcode(String value) {
        this.authcode = value;
    }

    /**
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * Gets the value of the resultcode property.
     * 
     */
    public int getResultcode() {
        return resultcode;
    }

    /**
     * Sets the value of the resultcode property.
     * 
     */
    public void setResultcode(int value) {
        this.resultcode = value;
    }

    /**
     * Gets the value of the refusalreason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefusalreason() {
        return refusalreason;
    }

    /**
     * Sets the value of the refusalreason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefusalreason(String value) {
        this.refusalreason = value;
    }

}
