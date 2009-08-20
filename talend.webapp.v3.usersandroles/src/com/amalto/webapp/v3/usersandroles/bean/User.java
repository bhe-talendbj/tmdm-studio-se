package com.amalto.webapp.v3.usersandroles.bean;


import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.amalto.webapp.core.util.Util;
import com.amalto.webapp.core.util.XtentisWebappException;



public class User {

	Integer ID;

	String userName;

	String givenName;

	String familyName;

	String phoneNumber;
	
	String realEmail;

	String fakeEmail;
     
	String company;
	
	long registrationDateAsLong;

	long lastVisitDateAsLong;

	String password;

	boolean enabled;

	boolean viewRealEmail;

	String signature;
	
	String homePage;

	String universe;


	Set<Role> roles;

	Map<String,String> properties;
	
	Map<String,String> dynamic;
	
	Map<String,String> applications;

	Set<String> roleNames;

	public User() {

	}

	

	
	/**
	 * User format
	 
	 <user>
	 	<username/>
	 	<password/>
	 	<givename/>
	 	<familyname/>
	 	<portalid/>
	 	<signature/>
	 	<realemail/>
	 	<fakeemail/>
	 	<viewrealemail/>
	 	<compagny/> 
	 	<registrationdate/>
	 	<lastvisitdate/>
	 	<enabled/>
	 	<homePage>
	 	<roles>
	 		<role/>
	 	</roles>
	 	<properties>
	 		<property>
	 			<name/>
	 			<value/>
	 		</property>
	 	</properties>
	 	<applications>
	 		<application>
	 			<name/>
	 			<value/>
	 		</application>
	 	</applications>
	 </user>
	 	
	 */
	
	
	public String serialize() {
		String user = 
			"<User>" +
			"    <username>"+userName+"</username>"+
			"    <password>"+password+"</password>"+
			"    <givenname>"+(givenName == null ? "" : givenName)+"</givenname>"+
			"    <familyname>"+(familyName == null ? "" : familyName)+"</familyname>"+
			"    <phonenumber>"+(phoneNumber == null ? "" : phoneNumber)+"</phonenumber>"+
			"    <company>"+ (company==null ?  "":company)+"</company>"+
			"    <id>"+ID+"</id>"+
			"    <signature>"+(signature == null  ? "" : signature)+"</signature>"+
			"    <realemail>"+(realEmail == null ? "" : realEmail)+"</realemail>"+
			"    <fakeemail>"+(fakeEmail == null ? "" : fakeEmail) +"</fakeemail>"+
			"    <viewrealemail>"+(viewRealEmail ? "yes" : "no")+"</viewrealemail>"+
			"    <registrationdate>"+(registrationDateAsLong==0 ? System.currentTimeMillis() : registrationDateAsLong)+"</registrationdate>"+
			"    <lastvisitdate>"+lastVisitDateAsLong+"</lastvisitdate>"+
			"    <enabled>"+(enabled ? "yes" : "no")+"</enabled>"+
			"    <homepage>"+homePage+"</homepage>"+
			"    <universe>"+(universe == null ? "" : universe)+"</universe>";
		user+=
				"    <roles>";
        Iterator<String> iter = roleNames.iterator();
        while(iter.hasNext()){
			user+="<role>"+iter.next()+"</role>";
		}
		user+=
			"    </roles>";
		user+=
			"    <properties>";
		if (dynamic!=null) {
			for (iter = dynamic.keySet().iterator(); iter.hasNext(); ) {
				String key = iter.next();
				String value = dynamic.get(key);
				user+=
					"        <property>" +
					"            <name>"+key+"</name>"+
					"            <value>"+value+"</value>"+
					"        </property>" ;
			}
		}
		user+=
			"    </properties>";

		user+=
			"    <applications>";
		if (applications!=null) {
			for (iter = applications.keySet().iterator(); iter.hasNext(); ) {
				String key = iter.next();
				String value = applications.get(key);
				user+=
					"        <application>" +
					"            <name>"+key+"</name>"+
					"            <value>"+value+"</value>"+
					"        </application>" ;
			}
		}
		user+=
			"    </applications>";
		user+=
			"</User>";			

		
		return user;
		
		
	}
	
	public static User parse(String xml) throws XtentisWebappException{
		User user = new User();
		parse(xml, user);
		return user;
	}
	
	
	public static void parse(String xml, User user) throws XtentisWebappException{
				
		try {
			Element result = Util.parse(xml).getDocumentElement();
			user.setUserName(Util.getFirstTextNode(result, "//username"));
			user.setPassword(Util.getFirstTextNode(result, "//password"));
			user.setGivenName(Util.getFirstTextNode(result, "//givenname"));
			user.setFamilyName(Util.getFirstTextNode(result, "//familyname"));
			user.setPhoneNumber(Util.getFirstTextNode(result, "//phonenumber"));
			user.setCompany(Util.getFirstTextNode(result, "//company"));
			//user.setID(new Integer(Util.getFirstTextNode(result, "id")));
			user.setSignature(Util.getFirstTextNode(result, "//signature"));
			user.setRealEmail(Util.getFirstTextNode(result, "//realemail"));
			user.setFakeEmail(Util.getFirstTextNode(result, "//fakeemail"));
			user.setViewRealEmail("yes".equals(Util.getFirstTextNode(result, "//viewrealemail")));
			user.setRegistrationDate(new Date(
					Long.parseLong(
							Util.getFirstTextNode(result, "//registrationdate")
					)
				)
			);
			String val = Util.getFirstTextNode(result, "//lastvisitdate");
			if ((val==null) || "0".equals(val)) {
				user.setLastVisitDate(null);
			} else {
				user.setLastVisitDate(new Date(Long.parseLong(val)));
			}
			user.setEnabled("yes".equals(Util.getFirstTextNode(result, "//enabled")));
			user.setHomePage(Util.getFirstTextNode(result, "//homepage"));
			user.setUniverse(Util.getFirstTextNode(result, "//universe"));
			
			String[] roles = Util.getTextNodes(result, "//roles/role");
			HashSet<String> rs = new HashSet<String>();
			if (roles!=null) {
				for (int i = 0; i < roles.length; i++) {
					rs.add(roles[i]);
				}
			}
			user.setRoleNames(rs);
			
			//XtentisPropertyMap propertyMap = new XtentisPropertyMap(user);
			HashMap<String,String> props = new HashMap<String,String>();
			NodeList properties = Util.getNodeList(result,"//properties/property");
			if (properties!=null) {
				for (int i = 0; i < properties.getLength(); i++) {
					Element property = (Element) properties.item(i);
					String name = Util.getFirstTextNode(property, "name");
					String value = Util.getFirstTextNode(property, "value");
					props.put(name, value);
				}
			}
			user.setDynamic(props);

			
			HashMap<String,String> apps = new HashMap<String,String>();
			NodeList appNodes = Util.getNodeList(result,"//applications/application");
			if (appNodes!=null) {
				for (int i = 0; i < appNodes.getLength(); i++) {
					Element application = (Element) appNodes.item(i);
					String name = Util.getFirstTextNode(application, "name");
					String value = Util.getFirstTextNode(application, "value");
					apps.put(name, value);
				}
			}
			user.setApplications(apps);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new XtentisWebappException("Failed to parse user: " +": "+e.getLocalizedMessage());
		}
		
	}




	public Map<String,String> getApplications() {
		return applications;
	}




	public void setApplications(Map<String,String> applications) {
		this.applications = applications;
	}




	public String getCompany() {
		return company;
	}




	public void setCompany(String company) {
		this.company = company;
	}




	public Map<String, String> getDynamic() {
		return dynamic;
	}




	public void setDynamic(Map<String, String> dynamic) {
		this.dynamic = dynamic;
	}




	public boolean isEnabled() {
		return enabled;
	}




	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}




	public String getFakeEmail() {
		return fakeEmail;
	}




	public void setFakeEmail(String fakeEmail) {
		this.fakeEmail = fakeEmail;
	}




	public String getFamilyName() {
		return familyName;
	}




	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}




	public String getGivenName() {
		return givenName;
	}




	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}




	public String getHomePage() {
		return homePage;
	}




	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}




	public Integer getID() {
		return ID;
	}




	public void setID(Integer id) {
		ID = id;
	}




	public long getLastVisitDateAsLong() {
		return lastVisitDateAsLong;
	}




	public void setLastVisitDate(Date lastVisitDate) {
		this.lastVisitDateAsLong = (lastVisitDate == null ? 0 : lastVisitDate.getTime());
	}





	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getPhoneNumber() {
		return phoneNumber;
	}




	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}




	public Map<String,String> getProperties() {
		return properties;
	}




	public void setProperties(Map<String,String> properties) {
		this.properties = properties;
	}




	public String getRealEmail() {
		return realEmail;
	}




	public void setRealEmail(String realEmail) {
		this.realEmail = realEmail;
	}




	public long getRegistrationDate() {
		return registrationDateAsLong;
	}




	public void setRegistrationDate(Date registrationDate) {
		this.registrationDateAsLong = registrationDate.getTime();
	}




	public Set<String> getRoleNames() {
		return roleNames;
	}




	public void setRoleNames(Set<String> roleNames) {
		this.roleNames = roleNames;
	}




	public Set<Role> getRoles() {
		return roles;
	}




	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}




	public String getSignature() {
		return signature;
	}




	public void setSignature(String signature) {
		this.signature = signature;
	}




	public String getUserName() {
		return userName;
	}




	public void setUserName(String userName) {
		this.userName = userName;
	}




	public boolean isViewRealEmail() {
		return viewRealEmail;
	}




	public void setViewRealEmail(boolean viewRealEmail) {
		this.viewRealEmail = viewRealEmail;
	}




	public String getUniverse() {
    	return universe;
    }




	public void setUniverse(String universe) {
    	this.universe = universe;
    }



}