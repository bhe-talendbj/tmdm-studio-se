/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.synchronization.ejb.local;

/**
 * Local home interface for SynchronizationItemCtrl.
 * @xdoclet-generated at 13-10-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface SynchronizationItemCtrlLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/SynchronizationItemCtrlLocal";
   public static final String JNDI_NAME="amalto/local/core/synchronizationItemctrl";

   public com.amalto.core.objects.synchronization.ejb.local.SynchronizationItemCtrlLocal create()
      throws javax.ejb.CreateException;

}
