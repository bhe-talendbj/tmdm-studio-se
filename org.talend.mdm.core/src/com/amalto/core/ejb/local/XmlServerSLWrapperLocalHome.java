/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.ejb.local;

/**
 * Local home interface for XmlServerSLWrapper.
 * @xdoclet-generated at 31-08-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface XmlServerSLWrapperLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/XmlServerSLWrapperLocal";
   public static final String JNDI_NAME="amalto/local/xmldb/xmlserverslwrapper";

   public com.amalto.core.ejb.local.XmlServerSLWrapperLocal create()
      throws javax.ejb.CreateException;

}
