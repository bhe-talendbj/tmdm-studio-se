/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.view.ejb.local;

/**
 * Local home interface for ViewCtrl.
 * @xdoclet-generated at 13-10-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface ViewCtrlLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/ViewCtrlLocal";
   public static final String JNDI_NAME="amalto/local/core/viewctrl";

   public com.amalto.core.objects.view.ejb.local.ViewCtrlLocal create()
      throws javax.ejb.CreateException;

}
