package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.entity.Admin;
import com.atguigu.atcrowdfunding.entity.AdminExample;
import com.atguigu.atcrowdfunding.exception.CrowdAdminLoginAcctRedoException;
import com.atguigu.atcrowdfunding.exception.CrowdAdminLoginException;
import com.atguigu.atcrowdfunding.mapper.AdminMapper;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.utils.CrowdUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zbystart
 * @create 2021-02-06 12:11
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;


    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    /**
     * 添加账户
     * @param admin
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void saveAdmin(Admin admin) {
        //对密码加密
        String userPswd = admin.getUserPswd();
        admin.setUserPswd(CrowdUtils.md5(userPswd));

        //创建时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(date);
        admin.setCreateTime(format);

        try{
            adminMapper.insert(admin);
        }catch (Exception e) {
            //如果出现了唯一约束的异常说明，用户名重复
            if (e instanceof DuplicateKeyException) {
                throw new CrowdAdminLoginAcctRedoException("登录账号重复！");
            }
        }

    }


    @Override
    @Transactional(readOnly = true)
    public Admin getAdminById(Integer id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    /**
     * 登录验证
     * @param loginAcct
     * @param userPswd
     * @return
     */
    @Override
    public Admin getAdminByLoginAcctAndUserPswd(String loginAcct, String userPswd) {
        AdminExample example  = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        //根据账户查找用户
        List<Admin> admins = adminMapper.selectByExample(example);
        if (admins == null || admins.size() == 0){
            throw new CrowdAdminLoginException(CrowdAdminConstant.ADMIN_LOGIN_FAILURE);
        }
        if (admins.size() > 1){
            throw new CrowdAdminLoginException(CrowdAdminConstant.ADMIN_DATA_ERROR_MSSAGE);
        }
        Admin admin = admins.get(0);
        String encoded = CrowdUtils.md5(userPswd);
        if (!Objects.equals(admin.getUserPswd(),encoded)){
            throw new CrowdAdminLoginException(CrowdAdminConstant.ADMIN_LOGIN_FAILURE);
        }
        return admin;
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param pageFootCount
     * @return
     */
    @Override
    public PageInfo<Admin> getAdminListByPage(Integer pageNum,Integer pageSize,Integer pageFootCount) {
        PageHelper.startPage(pageNum,pageSize);
        List<Admin> admins = adminMapper.selectByExample(new AdminExample());
        // 带有逻辑的查询
//        List<Admin> admins = adminMapper.logicSelectAdminList();
        PageInfo<Admin> pageInfo = new PageInfo<>(admins,pageFootCount);
        return pageInfo;
    }

    /**
     * 分页条件查询
     * @param queryCondition
     * @param pageNum
     * @param pageSize
     * @param pageFootCount
     * @param model
     * @return
     */
    @Override
    public PageInfo<Admin> getAdminListByCondition(String queryCondition,
                                                   Integer pageNum,
                                                   Integer pageSize,
                                                   Integer pageFootCount,
                                                   Model model) {

        PageHelper.startPage(pageNum,pageSize);
        List<Admin> admins = adminMapper.queryAdminListByCondition(queryCondition);
        // 带有逻辑的查询
//        List<Admin> admins = adminMapper.logicSelectAdminListByCondition(queryCondition);
        if (admins == null || admins.size() == 0){
            model.addAttribute(CrowdAdminConstant.ADMIN_PAGE_MASSAGE,"没有符合条件的数据");
            return null;
        }
        PageInfo<Admin> pageInfo = new PageInfo<>(admins,pageFootCount);
        return pageInfo;
    }

    /**
     * 更新账户信息
     * @param admin
     */
    @Override
    public void putAdminById(Admin admin) {
         adminMapper.updateAdminById(admin);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void removeAdminById(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
//        adminMapper.logicDeleteAdminById(id);
    }

    /**
     * 批量删除
     * @param adminIds
     */
    @Override
    public void removeBatchAdminById(String[] adminIds) {
        AdminExample adminExample = new AdminExample();
        ArrayList<Integer> list = new ArrayList<>();
        for (String adminId : adminIds){
            list.add(Integer.parseInt(adminId));
        }
        // where id in (1,2,3,4,5,6);
        adminExample.createCriteria().andIdIn(list);
        adminMapper.deleteByExample(adminExample);

//        adminMapper.batchLogicDeleteAdminById(adminIds);
    }

}
