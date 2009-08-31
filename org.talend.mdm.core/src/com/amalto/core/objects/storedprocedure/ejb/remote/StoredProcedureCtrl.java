/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.storedprocedure.ejb.remote;

/**
 * Remote interface for StoredProcedureCtrl.
 * @xdoclet-generated at 31-08-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface StoredProcedureCtrl
   extends javax.ejb.EJBObject
{
   /**
    * Creates or updates a Stored Procedure
    * @throws XtentisException
    */
   public com.amalto.core.objects.storedprocedure.ejb.StoredProcedurePOJOPK putStoredProcedure( com.amalto.core.objects.storedprocedure.ejb.StoredProcedurePOJO storedProcedure )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Get item
    * @throws XtentisException
    */
   public com.amalto.core.objects.storedprocedure.ejb.StoredProcedurePOJO getStoredProcedure( com.amalto.core.objects.storedprocedure.ejb.StoredProcedurePOJOPK pk )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Get a Stored Procedure - no exception is thrown: returns null if not found
    * @throws XtentisException
    */
   public com.amalto.core.objects.storedprocedure.ejb.StoredProcedurePOJO existsStoredProcedure( com.amalto.core.objects.storedprocedure.ejb.StoredProcedurePOJOPK pk )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Remove an item
    * @throws XtentisException
    */
   public com.amalto.core.objects.storedprocedure.ejb.StoredProcedurePOJOPK removeStoredProcedure( com.amalto.core.objects.storedprocedure.ejb.StoredProcedurePOJOPK pk )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Executes the stored procedure and return the result as a Collection
    * @throws XtentisException
    */
   public java.util.Collection execute( com.amalto.core.objects.storedprocedure.ejb.StoredProcedurePOJOPK sppk,java.lang.String revisionID,com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dcpk,java.lang.String[] parameters )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Retrieve all Stored Procedure PKS
    * @throws XtentisException
    */
   public java.util.Collection getStoredProcedurePKs( java.lang.String regex )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

}
