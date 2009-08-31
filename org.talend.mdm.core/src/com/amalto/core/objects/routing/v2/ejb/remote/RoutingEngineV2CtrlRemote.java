 /*
 * Generated by XDoclet - Do not edit!
 * this class was prodiuced by xdoclet automagically...
 */
package com.amalto.core.objects.routing.v2.ejb.remote;

import java.util.*;

/**
 * This class is remote adapter to RoutingEngineV2Ctrl. It provides convenient way to access
 * facade session bean. Inverit from this class to provide reasonable caching and event handling capabilities.
 *
 * Remote facade for RoutingEngineV2Ctrl.
 * @xdoclet-generated at 31-08-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */

public class RoutingEngineV2CtrlRemote extends Observable
{
    static RoutingEngineV2CtrlRemote _instance = null;
    public static RoutingEngineV2CtrlRemote getInstance() {
        if(_instance == null) {
	   _instance = new RoutingEngineV2CtrlRemote();
	}
	return _instance;
    }

  /**
   * cached remote session interface
   */
  com.amalto.core.objects.routing.v2.ejb.remote.RoutingEngineV2Ctrl _session = null;
  /**
   * return session bean remote interface
   */
   protected com.amalto.core.objects.routing.v2.ejb.remote.RoutingEngineV2Ctrl getSession() {
      try {
   	if(_session == null) {
	   _session = com.amalto.core.objects.routing.v2.ejb.local.RoutingEngineV2CtrlUtil.getHome().create();
	}
	return _session;
      } catch(Exception ex) {
        // just catch it here and return null.
        // somebody can provide better solution
	ex.printStackTrace();
	return null;
      }
   }

   public com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJOPK[] route ( com.amalto.core.ejb.ItemPOJOPK itemPOJOPK )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJOPK[] retval;
       retval =  getSession().route( itemPOJOPK );

      return retval;

   }

   public void start (  )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
      getSession().start(  );

   }

   public void stop (  )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
      getSession().stop(  );

   }

   public void suspend ( boolean suspend )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
      getSession().suspend( suspend );

   }

   public int getStatus (  )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        int retval;
       retval =  getSession().getStatus(  );

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
