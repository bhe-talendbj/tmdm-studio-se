// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.core.webservice;


public class WSProcessBytesUsingTransformer {
    protected com.amalto.core.webservice.WSByteArray wsBytes;
    protected java.lang.String contentType;
    protected com.amalto.core.webservice.WSTransformerPK wsTransformerPK;
    protected com.amalto.core.webservice.WSOutputDecisionTable wsOutputDecisionTable;
    
    public WSProcessBytesUsingTransformer() {
    }
    
    public WSProcessBytesUsingTransformer(com.amalto.core.webservice.WSByteArray wsBytes, java.lang.String contentType, com.amalto.core.webservice.WSTransformerPK wsTransformerPK, com.amalto.core.webservice.WSOutputDecisionTable wsOutputDecisionTable) {
        this.wsBytes = wsBytes;
        this.contentType = contentType;
        this.wsTransformerPK = wsTransformerPK;
        this.wsOutputDecisionTable = wsOutputDecisionTable;
    }
    
    public com.amalto.core.webservice.WSByteArray getWsBytes() {
        return wsBytes;
    }
    
    public void setWsBytes(com.amalto.core.webservice.WSByteArray wsBytes) {
        this.wsBytes = wsBytes;
    }
    
    public java.lang.String getContentType() {
        return contentType;
    }
    
    public void setContentType(java.lang.String contentType) {
        this.contentType = contentType;
    }
    
    public com.amalto.core.webservice.WSTransformerPK getWsTransformerPK() {
        return wsTransformerPK;
    }
    
    public void setWsTransformerPK(com.amalto.core.webservice.WSTransformerPK wsTransformerPK) {
        this.wsTransformerPK = wsTransformerPK;
    }
    
    public com.amalto.core.webservice.WSOutputDecisionTable getWsOutputDecisionTable() {
        return wsOutputDecisionTable;
    }
    
    public void setWsOutputDecisionTable(com.amalto.core.webservice.WSOutputDecisionTable wsOutputDecisionTable) {
        this.wsOutputDecisionTable = wsOutputDecisionTable;
    }
}