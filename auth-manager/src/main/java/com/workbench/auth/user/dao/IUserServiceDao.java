package com.workbench.auth.user.dao;

import com.github.pagehelper.Page;
import com.workbench.auth.menu.entity.Menu;
import com.workbench.auth.user.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 2017/6/29.
 */
@Repository
public interface IUserServiceDao {

    String query_user_columns = "SELECT u.user_id,u.user_name,u.user_name_cn,u.user_type,u.reg_date,u.user_status,u.last_login_time";

    String TABLE_NAME= "user u";

    @Select("")//to be continue
    @Options(useCache = false)
    User checkUserByIdAndPwd();

    @Select(query_user_columns + " FROM "+TABLE_NAME+" " +
            "WHERE user_name=#{username} and user_pwd = #{password}")
    @Options(useCache = false)
    User checkUserByNmAndPwd(@Param("username") String username,@Param("password") String password);

    @Select(query_user_columns + " FROM "+TABLE_NAME+" " +
            "WHERE user_name=#{username}")
    @Options(useCache = false)
    User checkUserByUserNm(String username);

    @Select(query_user_columns + " FROM "+TABLE_NAME+" " +
            "WHERE user_id=#{userId}")
    @Options(useCache = false)
    User getUserByUserId(int userId);

    @Select(query_user_columns + " FROM "+TABLE_NAME)
    @Options(useCache = false)
    List<User> listAllUser();

    @Select("<script>"+query_user_columns + " FROM "+TABLE_NAME +
            "  where u.user_name like concat('%',#{user_name},'%')"+
            "  <if test=\"user_id != 0\"> and u.user_id = #{user_id} </if> " +
            "  <if test=\"user_type != null\">   and u.user_type=#{user_type}  </if> " +
            "  <if test=\"user_type == null\">   and (u.user_type=1 or u.user_type=0)  </if> " +
            "</script>" )
    @Options(useCache = false)
    Page<User> listUsersForPage(@Param("currPage") int currPage, @Param("pageSize") int pageSize
            ,@Param("user_id") int user_id,@Param("user_name") String user_name,@Param("user_type")String user_type,@Param("originId") String originId);

    @Select("<script>"+query_user_columns + " FROM "+TABLE_NAME +
            " where  <if test=\"user_type != null\">    u.user_type=#{user_type}  </if> " +
            "  <if test=\"user_type == null\">    (u.user_type=1 or u.user_type=0)  </if> " +
            "  <if test=\"user_id != 0\"> and u.user_id = #{user_id} </if> " +
            "</script>" )
    @Options(useCache = false)
    Page<User> pageUsers(@Param("currPage") int currPage, @Param("pageSize") int pageSize
            ,@Param("user_id") int user_id,@Param("user_name") String user_name,@Param("user_type")String user_type);

    @Insert("INSERT INTO user (user_id,user_name,user_name_cn,user_type,reg_date,user_status,last_login_time,user_pwd) " +
            " VALUE (#{user_id},#{user_name},#{user_name_cn},#{user_type},now(),#{user_status},#{last_login_time},#{user_pwd})")
    @Options(useCache = false)
    void saveNewUser(User user);

    @Delete("DELETE FROM user WHERE user_id = #{user_id}")
    @Options(useCache = false)
    void delUserById(int user_id);

    @Select("select distinct am.* from user u " +
            "inner join user_role_assign ura on u.user_id = ura.user_id and u.user_name=#{user_nm} " +
            "inner join user_role_privilege urp on ura.user_role_id = urp.user_role_id " +
            "inner join app_module am on urp.module_id = am.module_id")
    @Options(useCache = false)
    List<Menu> getMenuList4User(String user_nm);

    @Update("update user set user_name=#{user_name} ,user_type=#{user_type},user_status=#{user_status} where user_id=#{user_id}")
    @Options(useCache = false)
    void updateSave(User user);

    @Update("<script>" +
            "update user set user_pwd=#{md5Value}" +
            "<if test='status!=null'> ,user_status=#{status}</if>" +
            " where user_id = #{userId}" +
            "</script>")
    void updatePwd(@Param("userId") Integer userId,
                   @Param("md5Value") String md5Value,
                   @Param("status") String status);

//    @Select(query_user_columns + " FROM "+TABLE_NAME+" " +
//            "WHERE user_nm=#{username} AND user_pwd=password(#{password})")
//    @Options(useCache = false)
//    public User checkUserExist(@Param("username") String username, @Param("password") String password);
//
//    @Select(query_user_columns + "FROM h62_sys_user WHERE user_id=#{user_id}")
//    @Options(useCache = false)
//    public User findUserByUserId(int user_id);
//
//    @Select("SELECT COUNT(1) FROM h62_sys_user WHERE user_nm=#{username}")
//    @Options(useCache = false)
//    public Integer checkUserNameExist(String username);
//    //findUserListByPage
//
//    @Select("SELECT COUNT(1) FROM h62_sys_user <if test=\"find_sys_admin != 1\"> WHERE role_id != 1 </if>")
//    @Options(useCache = false)
//    public Integer findTotalNum(Integer find_sys_admin);
//
//    @Select("<script>" +
//            "SELECT u.user_id,u.user_nm,u.user_real_nm,u.active_ind,u.user_group_id,concat(g.group_org,'_',g.group_dep) group_name," +
//            "  u.role_id,u.create_id,cu.user_real_nm create_name,u.create_ts,u.update_id,uu.user_real_nm update_name,u.update_ts" +
//            "  FROM h62_sys_user u " +
//            "  LEFT JOIN h62_sys_user_group g ON u.user_group_id = g.group_id " +
//            "  LEFT JOIN h62_sys_user cu ON u.create_id = cu.user_id " +
//            "  LEFT JOIN h62_sys_user uu ON u.update_id = uu.user_id " +
//            "  <if test=\"find_sys_admin != 1\">" +
//            "  WHERE u.role_id != 1" +
//            "  </if>" +
//            "  <choose>" +
//            "  <when test=\"orderId!=null\">" +
//            "   <choose>" +
//            "    <when test=\"orderId=='u.user_real_nm' || orderId=='create_name' || orderId=='update_name'\">" +
//            "    ORDER BY convert(${orderId} USING gbk) COLLATE gbk_chinese_ci ${order_type} " +
//            "    </when>" +
//            "    <otherwise>" +
//            "    ORDER BY ${orderId} ${order_type} " +
//            "    </otherwise>" +
//            "   </choose>" +
//            "  </when>" +
//            "  <otherwise>" +
//            "   ORDER BY u.user_id" +
//            "  </otherwise>" +
//            "  </choose>" +
//            "  LIMIT #{beginIndex}, #{pageSize};"+
//            "</script>")
//    public HashMap<String,Object> findUserListByPage(Map<String,Object> paramMap);
//
//    @Insert("INSERT INTO "+TABLE_NAME+" (user_pwd,user_nm,user_real_nm,user_group_id,role_id,active_ind,create_id,create_ts,update_id,update_ts) " +
//            " VALUE (password(#{user_pwd}),#{user_nm},#{user_real_nm},#{user_group_id},#{role_id},#{active_ind},#{create_id},now(),#{update_id},now())")
//    @Options(useCache = false)
//    public void saveOneUser(User user);
//
//    @Delete("DELETE FROM h62_sys_user WHERE user_id = #{user_id}")
//    @Options(useCache = false)
//    public void deleteUserById(String user_id);
//
//    @Update("UPDATE h62_sys_user SET user_real_nm = #{user_real_nm}, user_group_id = #{user_group_id}, " +
//            "update_id = #{update_id}, update_ts= now() WHERE user_id = #{edit_user_id}")
//    @Options(useCache = false)
//    public void modifyUserInfo(User user,String edit_user_id);
//
//    @Update("UPDATE h62_sys_user SET active_ind = #{active_ind} WHERE user_id = #{user_id} ")
//    @Options(useCache = false)
//    public void modifyUserActiveInd(String active_ind, int user_id);
//
//    @Update("UPDATE h62_sys_user SET user_pwd = password(#{user_pwd}) WHERE user_id = #{user_id} ")
//    @Options(useCache = false)
//    public void modifyUserPwd(String user_pwd,String user_id);
//
//    @Update("UPDATE h62_sys_user SET role_id = #{role_id} WHERE user_id = #{user_id} ")
//    @Options(useCache = false)
//    public void modifyUserRole(String role_id,String user_id);
//
//    @Update("UPDATE h62_sys_user SET user_group_id = #{user_group_id}, role_id = #{role_id}, update_ts= now() WHERE user_id IN " +
//            "  <foreach item=\"item\" index=\"index\" collection=\"user_id_list\" open=\"(\" separator=\",\" close=\")\">" +
//            "  #{item}" +
//            "  </foreach>")
//    @Options(useCache = false)
//    public void modifyUsersGroupAndRole(String user_group_id,String role_id,List user_id_list);
}
