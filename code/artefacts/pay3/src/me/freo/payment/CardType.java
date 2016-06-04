
package me.freo.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cardType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cardnumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expiryMonth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="expiryYear" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cvc" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cardType", propOrder = {
    "cardnumber",
    "postcode",
    "name",
    "expiryMonth",
    "expiryYear",
    "cvc"
})
public class CardType {

    protected int cardnumber;
    @XmlElement(required = true)
    protected String postcode;
    @XmlElement(required = true)
    protected String name;
    protected int expiryMonth;
    protected int expiryYear;
    protected int cvc;

    /**
     * Gets the value of the cardnumber property.
     * 
     */
    public int getCardnumber() {
        return cardnumber;
    }

    /**
     * Sets the value of the cardnumber property.
     * 
     */
    public void setCardnumber(int value) {
        this.cardnumber = value;
    }

    /**
     * Gets the value of the postcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Sets the value of the postcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostcode(String value) {
        this.postcode = value;
    }

    /**
     * Gets the value of the name property.
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
     * Sets the value of the name property.
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
     * Gets the value of the expiryMonth property.
     * 
     */
    public int getExpiryMonth() {
        return expiryMonth;
    }

    /**
     * Sets the value of the expiryMonth property.
     * 
     */
    public void setExpiryMonth(int value) {
        this.expiryMonth = value;
    }

    /**
     * Gets the value of the expiryYear property.
     * 
     */
    public int getExpiryYear() {
        return expiryYear;
    }

    /**
     * Sets the value of the expiryYear property.
     * 
     */
    public void setExpiryYear(int value) {
        this.expiryYear = value;
    }

    /**
     * Gets the value of the cvc property.
     * 
     */
    public int getCvc() {
        return cvc;
    }

    /**
     * Sets the value of the cvc property.
     * 
     */
    public void setCvc(int value) {
        this.cvc = value;
    }

}
