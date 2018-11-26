package com.palace.memories.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.palace.memories.db.DB;

public class User {

	public Long lUserId;
	public String strUserAlias;
	public String strEmail;
	public String strPhone;
	public String strID;
	public String strUserName;
	public String password;
	public long lLoginTime;
	public String strIp;
	public Object strAccType;
	
	public Map<String,Resource> resourceMap ;

	public void loadResource() {
		synchronized (User.class) {
			resourceMap = new HashMap<String,Resource>();
			String sql =" select * from role rol join resource res on rol.Id = res.lRoleId where rol.lUserId = ? ";
			for(Map<String,Object> map : DB.reIns.queryForList(sql)) {
				Resource rr = new Resource();
				rr.strName = MapUtils.getString(map, "strName");
				rr.strResource = MapUtils.getString(map, "strResource");
				rr.strType = MapUtils.getString(map, "strType");
				resourceMap.put(rr.strResource,rr);
			}
		}
	}
	
	
}
