// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;


public class WSConnectorInteractionResponse {
    protected com.amalto.workbench.webservices.WSConnectorResponseCode code;
    protected com.amalto.workbench.webservices.WSBase64KeyValue[] parameters;
    
    public WSConnectorInteractionResponse() {
    }
    
    public WSConnectorInteractionResponse(com.amalto.workbench.webservices.WSConnectorResponseCode code, com.amalto.workbench.webservices.WSBase64KeyValue[] parameters) {
        this.code = code;
        this.parameters = parameters;
    }
    
    public com.amalto.workbench.webservices.WSConnectorResponseCode getCode() {
        return code;
    }
    
    public void setCode(com.amalto.workbench.webservices.WSConnectorResponseCode code) {
        this.code = code;
    }
    
    public com.amalto.workbench.webservices.WSBase64KeyValue[] getParameters() {
        return parameters;
    }
    
    public void setParameters(com.amalto.workbench.webservices.WSBase64KeyValue[] parameters) {
        this.parameters = parameters;
    }
}
