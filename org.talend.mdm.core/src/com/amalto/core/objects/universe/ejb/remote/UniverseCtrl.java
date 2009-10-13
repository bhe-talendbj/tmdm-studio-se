/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.universe.ejb.remote;

/**
 * Remote interface for UniverseCtrl.
 * @xdoclet-generated at 13-10-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface UniverseCtrl
   extends javax.ejb.EJBObject
{
   /**
    * Creates or updates a Universe
    * @throws XtentisException
    */
   public com.amalto.core.objects.universe.ejb.UniversePOJOPK putUniverse( com.amalto.core.objects.universe.ejb.UniversePOJO universe )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Creates or updates a Universe
    * @throws XtentisException
    */
   public com.amalto.core.webservice.WSUniversePKArray getUniverseByRevision( java.lang.String name,java.lang.String revision,java.lang.String type )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Get Universe
    * @throws XtentisException
    */
   public com.amalto.core.objects.universe.ejb.UniversePOJO getUniverse( com.amalto.core.objects.universe.ejb.UniversePOJOPK pk )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Get a Universe - no exception is thrown: returns null if not found
    * @throws XtentisException
    */
   public com.amalto.core.objects.universe.ejb.UniversePOJO existsUniverse( com.amalto.core.objects.universe.ejb.UniversePOJOPK pk )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Remove an item
    * @throws XtentisException
    */
   public com.amalto.core.objects.universe.ejb.UniversePOJOPK removeUniverse( com.amalto.core.objects.universe.ejb.UniversePOJOPK pk )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * getAllCreatedRevisions
    */
   public java.util.Collection getAllCreatedRevisions( com.amalto.core.objects.universe.ejb.UniversePOJOPK pk )
      throws java.rmi.RemoteException;

   /**
    * getAllQuotedRevisions
    */
   public java.util.Collection getAllQuotedRevisions( com.amalto.core.objects.universe.ejb.UniversePOJOPK pk )
      throws java.rmi.RemoteException;

   /**
    * getUniverseCreator
    */
   public com.amalto.core.objects.universe.ejb.UniversePOJOPK getUniverseCreator( com.amalto.core.objects.universe.ejb.RevisionPOJOPK pk )
      throws java.rmi.RemoteException;

   /**
    * getUniverseQuoter
    */
   public java.util.Collection getUniverseQuoter( com.amalto.core.objects.universe.ejb.RevisionPOJOPK pk )
      throws java.rmi.RemoteException;

   /**
    * Retrieve all Universe PKS
    * @throws XtentisException
    */
   public java.util.Collection getUniversePKs( java.lang.String regex )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

}
