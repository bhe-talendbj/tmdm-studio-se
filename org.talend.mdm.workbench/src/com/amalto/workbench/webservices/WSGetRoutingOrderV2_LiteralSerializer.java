// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation （1.1.2_01，编译版 R40）
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.xsd.XSDConstants;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;

public class WSGetRoutingOrderV2_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_wsRoutingOrderPK_QNAME = new QName("", "wsRoutingOrderPK");
    private static final QName ns2_WSRoutingOrderV2PK_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSRoutingOrderV2PK");
    private CombinedSerializer ns2_myWSRoutingOrderV2PK_LiteralSerializer;
    
    public WSGetRoutingOrderV2_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSGetRoutingOrderV2_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns2_myWSRoutingOrderV2PK_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.workbench.webservices.WSRoutingOrderV2PK.class, ns2_WSRoutingOrderV2PK_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSGetRoutingOrderV2 instance = new com.amalto.workbench.webservices.WSGetRoutingOrderV2();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_wsRoutingOrderPK_QNAME)) {
                member = ns2_myWSRoutingOrderV2PK_LiteralSerializer.deserialize(ns1_wsRoutingOrderPK_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setWsRoutingOrderPK((com.amalto.workbench.webservices.WSRoutingOrderV2PK)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_wsRoutingOrderPK_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSGetRoutingOrderV2 instance = (com.amalto.workbench.webservices.WSGetRoutingOrderV2)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSGetRoutingOrderV2 instance = (com.amalto.workbench.webservices.WSGetRoutingOrderV2)obj;
        
        if (instance.getWsRoutingOrderPK() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns2_myWSRoutingOrderV2PK_LiteralSerializer.serialize(instance.getWsRoutingOrderPK(), ns1_wsRoutingOrderPK_QNAME, null, writer, context);
    }
}
