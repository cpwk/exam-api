package com.yang.exam.api.Role.service;

import com.yang.exam.api.Role.Repository.RoleRepository;
import com.yang.exam.api.Role.model.Role;
import com.yang.exam.api.admin.authority.AdminPermissionVO;
import com.yang.exam.commons.exception.ErrorCode;
import com.yang.exam.commons.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yangchengcheng
 * @Date: 2019/12/30 17:28
 * @Version：1.0
 */
@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    private RoleRepository roleRepository;

    //
//    @Autowired
//    private KvCacheFactory kvCacheFactory;
//
//    private KvCacheWrap<Integer, Role> roleCache;
//
//    @PostConstruct
//    public void init() {
//        roleCache = kvCacheFactory.create(new CacheOptions("role", 1, Constants.CACHE_REDIS_EXPIRE),
//                new RepositoryProvider<Integer, Role>() {
//
//                    @Override
//                    public Role findByKey(Integer key) throws RepositoryException {
//                        return roleRepository.findById(key).orElse(null);
//                    }
//
//                    @Override
//                    public Map<Integer, Role> findByKeys(Collection<Integer> ids) throws RepositoryException {
//                        throw new UnsupportedOperationException("findByKeys");
//                    }
//
//                }, new BeanModelConverter<>(Role.class));
//    }
    //

    @Override
    public void save_role(Role role) {


        roleRepository.save(role);
//        roleCache.remove(role.getId());
    }

    @Override
    public void remove_role(int id) {
        roleRepository.deleteById(id);
//        roleCache.remove(id);

    }

    @Override
    public Role role(Integer id) throws Exception {
//        return roleCache.findByKey(id);
        return getById(id);
    }

    @Override
    public List<Role> roles(boolean init) {
        List<Role> roles = roleRepository.findAll();
        if (init) {
            for (Role r : roles) {
                r.setPstr(AdminPermissionVO.initPermissionsByPs(r.getPermissions()));
            }
        }
        return roles;
    }

    @Override
    public Role findById(Integer id) throws Exception {
//        return roleCache.findByKey(id);
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role getById(Integer id) throws Exception {
        Role role = findById(id);
        if (role == null) {
            throw new ServiceException(ErrorCode.ERR_DATA_NOT_FOUND);
        }
        return role;
    }
}
