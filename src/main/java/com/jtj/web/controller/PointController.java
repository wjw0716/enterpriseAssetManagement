package com.jtj.web.controller;

import com.jtj.web.common.PageDto;
import com.jtj.web.common.ResultCode;
import com.jtj.web.common.ResultDto;
import com.jtj.web.common.exception.AssetException;
import com.jtj.web.dto.PointDto;
import com.jtj.web.entity.KeyValue;
import com.jtj.web.entity.Point;
import com.jtj.web.service.PointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2017/2/22.
 */
@Api("部门管理")
@RestController
@RequestMapping("/point")
@RequiresPermissions(value = {"system-administrator-permission","sys:point"},logical = Logical.OR)
public class PointController {

    @Autowired
    private PointService pointService;

    @ApiOperation(value = "添加部门")
    @PostMapping("/add")
    public ResultDto<Object> add(@RequestBody Point point){
        return pointService.add(point);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/delete/{id}")
    public ResultDto<Object> deleteById(@PathVariable("id") Long id) throws AssetException {
        return pointService.deleteById(id);
    }

    @ApiOperation(value = "更新部门")
    @PutMapping("/update")
    public ResultDto<Object> update(@RequestBody Point point) {
        return pointService.update(point);
    }

    @ApiOperation(value = "部门列表")
    @GetMapping("/list")
    public ResultDto<PageDto<Point>> getList(PointDto dto){
        return pointService.getList(dto);
    }

    @ApiOperation(value = "部门树")
    @GetMapping("/tree")
    public ResultDto<List<Point>> getPointTree(){
        return pointService.getResultTree();
    }

    @ApiOperation(value = "获取部门")
    @GetMapping("/map")
    public ResultDto<List<KeyValue>> getMapByPid(@RequestParam("pid") Long pid) throws AssetException {
        return pointService.getMapByPid(pid);
    }

    @ApiOperation(value = "获取部门")
    @GetMapping("/get")
    public ResultDto<List<Point>> getPointByPid(@RequestParam(value = "pid",required = false) Long pid){
        return pointService.getResultTreeNodesByPid(pid);
    }

    @ApiOperation(value = "获取部门")
    @GetMapping("/get/{id}")
    public ResultDto<Point> getPointById(@PathVariable("id") Long id){
        return pointService.getResultTreeEntityById(id);
    }

    @ApiOperation(value = "获取部门")
    @GetMapping("/root/query")
    public ResultDto<List<Point>> getQueryRootPoint(){
        ResultDto<List<Point>> result = new ResultDto<>(ResultCode.SUCCESS);
        result.setObject(pointService.getQueryRootPoint());
        return result;
    }

    @ApiOperation(value = "刷新部门缓存数据")
    @PostMapping("/refresh")
    public ResultDto<List<Point>> refresh(){
        ResultDto<List<Point>> result = new ResultDto<>(ResultCode.SUCCESS);
        pointService.refreshTreeData();
        return result;
    }

}
