// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
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

public class WSItemPKsByCriteriaResponseResults_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_date_QNAME = new QName("", "date");
    private static final QName ns3_long_TYPE_QNAME = SchemaConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns3_myns3__long__long_Long_Serializer;
    private static final QName ns1_wsItemPK_QNAME = new QName("", "wsItemPK");
    private static final QName ns2_WSItemPK_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSItemPK");
    private CombinedSerializer ns2_myWSItemPK_LiteralSerializer;
    private static final QName ns1_taskId_QNAME = new QName("", "taskId");
    private static final QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    
    public WSItemPKsByCriteriaResponseResults_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSItemPKsByCriteriaResponseResults_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns3_myns3__long__long_Long_Serializer = (CombinedSerializer)registry.getSerializer("", long.class, ns3_long_TYPE_QNAME);
        ns2_myWSItemPK_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.workbench.webservices.WSItemPK.class, ns2_WSItemPK_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns3_string_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSItemPKsByCriteriaResponseResults instance = new com.amalto.workbench.webservices.WSItemPKsByCriteriaResponseResults();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_date_QNAME)) {
                member = ns3_myns3__long__long_Long_Serializer.deserialize(ns1_date_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setDate(((Long)member).longValue());
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_date_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_wsItemPK_QNAME)) {
                member = ns2_myWSItemPK_LiteralSerializer.deserialize(ns1_wsItemPK_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setWsItemPK((com.amalto.workbench.webservices.WSItemPK)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_wsItemPK_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_taskId_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_taskId_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setTaskId((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_taskId_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSItemPKsByCriteriaResponseResults instance = (com.amalto.workbench.webservices.WSItemPKsByCriteriaResponseResults)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSItemPKsByCriteriaResponseResults instance = (com.amalto.workbench.webservices.WSItemPKsByCriteriaResponseResults)obj;
        
        if (new Long(instance.getDate()) == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3__long__long_Long_Serializer.serialize(new Long(instance.getDate()), ns1_date_QNAME, null, writer, context);
        if (instance.getWsItemPK() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns2_myWSItemPK_LiteralSerializer.serialize(instance.getWsItemPK(), ns1_wsItemPK_QNAME, null, writer, context);
        if (instance.getTaskId() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getTaskId(), ns1_taskId_QNAME, null, writer, context);
    }
}
