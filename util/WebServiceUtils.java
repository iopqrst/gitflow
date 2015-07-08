package com.bskcare.ch.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;;

public class WebServiceUtils {

	public static String requertInterface(String wsdl,Object[] objs,String method) throws MalformedURLException,  RemoteException, ServiceException{
		String res1="";
		Service service = new Service();
		Call call;
		call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(wsdl));
		call.setOperationName(method);
		res1 = (String) call.invoke(objs);
		return res1;
	}
}
