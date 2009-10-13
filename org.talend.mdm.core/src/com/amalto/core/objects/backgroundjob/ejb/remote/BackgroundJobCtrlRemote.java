 /*
 * Generated by XDoclet - Do not edit!
 * this class was prodiuced by xdoclet automagically...
 */
package com.amalto.core.objects.backgroundjob.ejb.remote;

import java.util.*;

/**
 * This class is remote adapter to BackgroundJobCtrl. It provides convenient way to access
 * facade session bean. Inverit from this class to provide reasonable caching and event handling capabilities.
 *
 * Remote facade for BackgroundJobCtrl.
 * @xdoclet-generated at 13-10-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */

public class BackgroundJobCtrlRemote extends Observable
{
    static BackgroundJobCtrlRemote _instance = null;
    public static BackgroundJobCtrlRemote getInstance() {
        if(_instance == null) {
	   _instance = new BackgroundJobCtrlRemote();
	}
	return _instance;
    }

  /**
   * cached remote session interface
   */
  com.amalto.core.objects.backgroundjob.ejb.remote.BackgroundJobCtrl _session = null;
  /**
   * return session bean remote interface
   */
   protected com.amalto.core.objects.backgroundjob.ejb.remote.BackgroundJobCtrl getSession() {
      try {
   	if(_session == null) {
	   _session = com.amalto.core.objects.backgroundjob.ejb.local.BackgroundJobCtrlUtil.getHome().create();
	}
	return _session;
      } catch(Exception ex) {
        // just catch it here and return null.
        // somebody can provide better solution
	ex.printStackTrace();
	return null;
      }
   }

   public com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJOPK putBackgroundJob ( com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJO backgroundJob )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJOPK retval;
       retval =  getSession().putBackgroundJob( backgroundJob );

      return retval;

   }

   public com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJO getBackgroundJob ( com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJO retval;
       retval =  getSession().getBackgroundJob( pk );

      return retval;

   }

   public com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJO existsBackgroundJob ( com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJO retval;
       retval =  getSession().existsBackgroundJob( pk );

      return retval;

   }

   public com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJOPK removeBackgroundJob ( com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.backgroundjob.ejb.BackgroundJobPOJOPK retval;
       retval =  getSession().removeBackgroundJob( pk );

      return retval;

   }

   public java.util.Collection getBackgroundJobPKs ( java.lang.String regex )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        java.util.Collection retval;
       retval =  getSession().getBackgroundJobPKs( regex );

      return retval;

   }

  /**
   * override this method to provide feedback to interested objects
   * in case collections were changed.
   */
  public void invalidate() {

  	setChanged();
	notifyObservers();
  }
}
