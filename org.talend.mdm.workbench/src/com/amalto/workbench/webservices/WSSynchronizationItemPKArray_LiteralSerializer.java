// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.encoding.CombinedSerializer;
import com.sun.xml.rpc.encoding.DeserializationException;
import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
import com.sun.xml.rpc.encoding.SOAPSerializationContext;
import com.sun.xml.rpc.encoding.literal.LiteralObjectSerializerBase;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLReaderUtil;
import com.sun.xml.rpc.streaming.XMLWriter;

public class WSSynchronizationItemPKArray_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_wsSynchronizationItemPK_QNAME = new QName("", "wsSynchronizationItemPK");
    private static final QName ns2_WSSynchronizationItemPK_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSSynchronizationItemPK");
    private CombinedSerializer ns2_myWSSynchronizationItemPK_LiteralSerializer;
    
    public WSSynchronizationItemPKArray_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSSynchronizationItemPKArray_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns2_myWSSynchronizationItemPK_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.workbench.webservices.WSSynchronizationItemPK.class, ns2_WSSynchronizationItemPK_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSSynchronizationItemPKArray instance = new com.amalto.workbench.webservices.WSSynchronizationItemPKArray();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_wsSynchronizationItemPK_QNAME))) {
            values = new ArrayList();
            for(;;) {
                elementName = reader.getName();
                if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_wsSynchronizationItemPK_QNAME))) {
                    value = ns2_myWSSynchronizationItemPK_LiteralSerializer.deserialize(ns1_wsSynchronizationItemPK_QNAME, reader, context);
                    if (value == null) {
                        throw new DeserializationException("literal.unexpectedNull");
                    }
                    values.add(value);
                    reader.nextElementContent();
                } else {
                    break;
                }
            }
            member = new com.amalto.workbench.webservices.WSSynchronizationItemPK[values.size()];
            member = values.toArray((Object[]) member);
            instance.setWsSynchronizationItemPK((com.amalto.workbench.webservices.WSSynchronizationItemPK[])member);
        }
        else if(!(reader.getState() == XMLReader.END)) {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSSynchronizationItemPKArray instance = (com.amalto.workbench.webservices.WSSynchronizationItemPKArray)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSSynchronizationItemPKArray instance = (com.amalto.workbench.webservices.WSSynchronizationItemPKArray)obj;
        
        if (instance.getWsSynchronizationItemPK() != null) {
            for (int i = 0; i < instance.getWsSynchronizationItemPK().length; ++i) {
                ns2_myWSSynchronizationItemPK_LiteralSerializer.serialize(instance.getWsSynchronizationItemPK()[i], ns1_wsSynchronizationItemPK_QNAME, null, writer, context);
            }
        }
    }
}