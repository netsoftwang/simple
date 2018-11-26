package com.palace.memories.auth;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.palace.memories.db.DB;
import com.palace.memories.utils.AESUtil;
import com.palace.memories.utils.PA;

public class UserService {
	static private Logger log = LoggerFactory.getLogger(UserService.class);
	public static int USER_OK = 0;
	public static int USER_NOT_EXIST=1;
	public static int USER_NAME_PASS_ERROR=2;
	static Map<Long,User> userMap = new HashMap<Long, User>();	
	
	public static int doLogin(Long userId,String pass,String type) {
		try {
			pass = AESUtil.encrypt(pass);
		} catch (Exception e) {
			log.warn("",e);
			return USER_NAME_PASS_ERROR;
		}
		User user = userMap.get(userId);
		if(user == null) {
			String sql =" select * from tbUser where ? = ? ";
			Map<String,Object> map  = DB.reIns.queryForMap(sql,type,userId);
			if(map == null ) {
				return USER_NOT_EXIST;
			}
			if(pass.equals(MapUtils.getString(map, PA.strPassword))) {
				return USER_NAME_PASS_ERROR;
			}
			user = new User();
			user.lLoginTime = System.currentTimeMillis();
			user.lUserId = MapUtils.getLong(map, PA.lUserId);
			user.password = pass;
			user.strUserName = MapUtils.getString(map, PA.strUserName);
			user.strID = MapUtils.getString(map, PA.strID);
			user.strPhone = MapUtils.getString(map, PA.strPhone);
			user.strEmail = MapUtils.getString(map, PA.strEmail);
			user.strAccType = MapUtils.getString(map,PA.strAccType);
			userMap.put(userId,user);
			return USER_OK;
		}
		if(user.password.equals(pass)) {
			user.lLoginTime = System.currentTimeMillis();
			user.strAccType = type;
			return USER_OK;
		}
		return USER_NAME_PASS_ERROR;
	}
	
	public static int authen_pass = 0;
	public static int authen_error = 1;
	static Map<String,User> userAuthenMap = new HashMap<String, User>();	
	public static int checkURI(long lUserId,String uri) {
		User user = userMap.get(lUserId);
		if(user == null) {
			throw new IllegalArgumentException("用户信息不存在,请重新登录");
		}
		if(user.resourceMap == null) {
			user.loadResource();
		}
		Resource res = user.resourceMap.get(uri);
		if(res == null ) {
			return authen_error;
		}
		return authen_pass;
	}
	
	
}
