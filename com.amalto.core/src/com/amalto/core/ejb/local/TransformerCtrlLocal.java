/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.ejb.local;

/**
 * Local interface for TransformerCtrl.
 * @deprecated - use TransformerV2 package
 * @xdoclet-generated at 13-08-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface TransformerCtrlLocal
   extends javax.ejb.EJBLocalObject
{
   /**
    * Creates or updates a Transformer
    * @throws XtentisException
    */
   public com.amalto.core.ejb.TransformerPOJOPK putTransformer( com.amalto.core.ejb.TransformerPOJO transformer ) throws com.amalto.core.util.XtentisException;

   /**
    * Get item
    * @throws XtentisException
    */
   public com.amalto.core.ejb.TransformerPOJO getTransformer( com.amalto.core.ejb.TransformerPOJOPK pk ) throws com.amalto.core.util.XtentisException;

   /**
    * Get item
    * @throws XtentisException
    */
   public com.amalto.core.ejb.TransformerPOJO getTransformerV1( com.amalto.core.ejb.TransformerPOJOPK pk ) throws com.amalto.core.util.XtentisException;

   /**
    * Get a Transformer - no exception is thrown: returns null if not found
    * @throws XtentisException
    */
   public com.amalto.core.ejb.TransformerPOJO existsTransformer( com.amalto.core.ejb.TransformerPOJOPK pk ) throws com.amalto.core.util.XtentisException;

   /**
    * Remove an item
    * @throws XtentisException
    */
   public com.amalto.core.ejb.TransformerPOJOPK removeTransformer( com.amalto.core.ejb.TransformerPOJOPK pk ) throws com.amalto.core.util.XtentisException;

   /**
    * Executes theTransformer
    * @throws XtentisException
    */
   public void execute( com.amalto.core.ejb.TransformerPOJOPK transformerPK,com.amalto.core.util.TypedContent content,com.amalto.core.util.TransformerPluginContext context,com.amalto.core.util.TransformerPluginCallBack callBack ) throws com.amalto.core.util.XtentisException;

   /**
    * Retrieve all Transformer PKS
    * @throws XtentisException
    */
   public java.util.Collection getTransformerPKs( java.lang.String regex ) throws com.amalto.core.util.XtentisException;

   /**
    * Retrieve all Transformer PKS
    * @throws XtentisException
    */
   public java.util.Collection getTransformerV1PKs( java.lang.String regex ) throws com.amalto.core.util.XtentisException;

   /**
    * Simplified Process items using a Transformer and a Decision Table
    * @see #process(TypedContent, TransformerPOJOPK, HashMap, String, TransformerPluginContext, TransformerPluginCallBack) below NONE: well, leave it in the pipeline as such DISCARD: remove it from the pipeline PROCESS(DataCluster,DataModel[,overwrite]): project the result to DataCluster using DataModel. By default will overwrite an existing items unless overwrite is set to false;
    * @throws XtentisException
    */
   public com.amalto.core.util.TransformerPluginContext process( com.amalto.core.util.TypedContent content,com.amalto.core.ejb.TransformerPOJOPK transformerPOJOPK,java.util.HashMap decisionTable ) throws com.amalto.core.util.XtentisException;

   /**
    * Process items using a Transformer and a Decision Table The Decision Table fixes for each output variable the action to take after a full transformer plugin cycle has been run NONE: well, leave it in the pipeline as such DISCARD: remove it from the pipeline PROJECT(DataCluster,DataModel[,overwrite]): project the result to DataCluster using DataModel. By default will overwrite an existing items unless overwrite is set to false; The pks of the element projected can be found in the Context entry com.amalto.core.ejb.itemctrl.pks
    * @throws XtentisException
    */
   public com.amalto.core.util.TransformerPluginContext process( com.amalto.core.util.TypedContent content,com.amalto.core.ejb.TransformerPOJOPK transformerPOJOPK,java.util.HashMap decisionTable,java.lang.String username,com.amalto.core.util.TransformerPluginContext context,com.amalto.core.util.TransformerPluginCallBack processCallBack ) throws com.amalto.core.util.XtentisException;

   /**
    * Simplified Process Bytes using a Transformer and a Decision Table Runs as a Background Job
    * @see #process(TypedContent, TransformerPOJOPK, HashMap, String, TransformerPluginContext, TransformerPluginCallBack) for parameter details
    * @throws XtentisException
    */
   public com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJOPK processBytesAsBackgroundJob( byte[] bytes,java.lang.String contentType,com.amalto.core.ejb.TransformerPOJOPK transformerPOJOPK,java.util.HashMap decisionTable ) throws com.amalto.core.util.XtentisException;

   /**
    * Simplified Process File using a Transformer and a Decision Table Runs as a Background Job
    * @see #process(TypedContent, TransformerPOJOPK, HashMap, String, TransformerPluginContext, TransformerPluginCallBack) for parameter details
    * @throws XtentisException
    */
   public com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJOPK processFileAsBackgroundJob( java.lang.String filename,java.lang.String contentType,com.amalto.core.ejb.TransformerPOJOPK transformerPOJOPK,java.util.HashMap decisionTable ) throws com.amalto.core.util.XtentisException;

}