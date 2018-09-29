package com.jtj.web.controller;

import com.jtj.web.common.PageDto;
import com.jtj.web.common.ResultDto;
import com.jtj.web.common.exception.AssetException;
import com.jtj.web.dto.RoleDto;
import com.jtj.web.entity.Role;
import com.jtj.web.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2017/2/22.
 */
@RestController
@RequestMapping("/role")
@RequiresRoles("system-administrator-role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    public ResultDto<Object> add(@RequestBody Role role){
        return roleService.add(role);
    }

    @DeleteMapping("/delete")
    public ResultDto<Object> delete(@RequestParam("ids") Long[] ids) throws AssetException {
        return roleService.delete(ids);
    }

    @PutMapping("/update")
    public ResultDto<Object> update(@RequestBody Role role) {
        return roleService.update(role);
    }

    @GetMapping("/list")
    public ResultDto<PageDto<Role>> getList(RoleDto dto){
        return roleService.getList(dto);
    }

    @GetMapping("/getPermission")
    public ResultDto<Object> getPermission(@RequestParam Long roleId){
        return roleService.getPermission(roleId);
    }

    @PutMapping("/updatePermission")
    public ResultDto<Object> updatePermission(@RequestBody Map<String,String> body){
    	//处理提交过来的数据为空时的处理方式
    	 List<Long> longs =null;
    	 try {
    		 longs = Arrays.stream(body.get("permissionIds").split(",")).map(Long::parseLong).collect(Collectors.toList());
		} catch (Exception e) {
			longs=new ArrayList<>();
		}
        
        return roleService.updatePermission(Long.parseLong(body.get("roleId")),longs.toArray(new Long[longs.size()]));
    }

}
