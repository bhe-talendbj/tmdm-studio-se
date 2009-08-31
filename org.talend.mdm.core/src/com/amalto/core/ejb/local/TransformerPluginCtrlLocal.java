/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.ejb.local;

/**
 * Local interface for TransformerPluginCtrl.
 * @deprecated - use TransformerV2 package
 * @xdoclet-generated at 31-08-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface TransformerPluginCtrlLocal
   extends javax.ejb.EJBLocalObject
{
   /**
    * Configuration received from outbound, typically a portlet The default implementation stores the configuration string "as is"
    * @throws EJBException
    */
   public void putConfiguration( java.lang.String configuration ) throws com.amalto.core.util.XtentisException;

   /**
    * Retrieves the configuration The default implementation renders the configuration string "as stored" and ignore the optional parameter
    * @throws EJBException
    */
   public java.lang.String getConfiguration( java.lang.String optionalParameter ) throws com.amalto.core.util.XtentisException;

   /**
    * Configuration received from outbound, typically a portlet The default implementation stores the configuration string "as is"
    * @throws EJBException
    */
   public void putServiceData( java.lang.String serviceData ) throws com.amalto.core.util.XtentisException;

   /**
    * To be Implemented. Returns the unique JNDI name of the service. The JNDI name must be of the type amalto/local/service/[NAME] where [NAME] matchs the pattern "[a-zA-Z][a-zA-Z0-9]*" and is unique accross services
    * @throws EJBException
    */
   public java.lang.String getJNDIName(  ) throws com.amalto.core.util.XtentisException;

   /**
    * To be Implemented. Returns the description of the service. Can be null
    * @throws EJBException
    */
   public java.lang.String getDescription( java.lang.String twoLettersLanguageCode ) throws com.amalto.core.util.XtentisException;

   /**
    * To be Implemented. Returns the description of the service. Can be null
    * @throws EJBException
    */
   public java.lang.String getDocumentation( java.lang.String twoLettersLanguageCode ) throws com.amalto.core.util.XtentisException;

   /**
    * Initializes the plugin. The plugin must returns the context passed as parameter. The plugin can add elements to the context Processing should not start before start() is called The plugin should call the callback contentReady method when content is ready for output and passed back the pluginHandle. The plugin must call the callback done method when it has finished processing.
    * @return the Context
    * @throws EJBException
    */
   public com.amalto.core.util.TransformerPluginContext init( int pluginHandle,java.lang.String parameters,com.amalto.core.util.TransformerPluginContext context,com.amalto.core.util.TransformerPluginCallBack callBack ) throws com.amalto.core.util.XtentisException;

   /**
    * Called by the transformer to request that the plugin processing executes
    */
   public void execute( com.amalto.core.util.TransformerPluginContext context,com.amalto.core.util.TypedContent content ) throws com.amalto.core.util.XtentisException;

   /**
    * Called by the transformer to request that the plugin processing stops
    */
   public void stop( com.amalto.core.util.TransformerPluginContext context ) throws com.amalto.core.util.XtentisException;

   /**
    * Called by the transformer when the process completes Typically used to clean up pipeline variables
    */
   public com.amalto.core.util.TransformerPluginContext end( com.amalto.core.util.TransformerPluginContext context ) throws com.amalto.core.util.XtentisException;

   /**
    * To be Implemented. Check wheteher a particular Content-type is accepted as Input (small caps) Can be null
    * @throws EJBException
    */
   public boolean acceptsContentType( java.lang.String contentType,java.lang.String parameters ) throws com.amalto.core.util.XtentisException;

   /**
    * To be Implemented. The plugin output content-type. It may include charset information e.g. text/plain; charset=utf-8 Can be null
    * @throws EJBException
    */
   public java.lang.String getOutputContentType( java.lang.String parameters ) throws com.amalto.core.util.XtentisException;

   /**
    * To be Implemented. Returns the XML schema for the parameters Can be null
    * @throws EJBException
    */
   public java.lang.String getParametersSchema(  ) throws com.amalto.core.util.XtentisException;

   /**
    * To be Implemented. Compile the parameters in the form it must be saved for use by the plugin
    * @throws EJBException
    */
   public java.lang.String compileParameters( java.lang.String parameters ) throws com.amalto.core.util.XtentisException;

   /**
    * To be Implemented. Decompile the parameters from thr form it is saved in to the human readable form
    * @throws EJBException
    */
   public java.lang.String decompileParameters( java.lang.String parameters ) throws com.amalto.core.util.XtentisException;

}
