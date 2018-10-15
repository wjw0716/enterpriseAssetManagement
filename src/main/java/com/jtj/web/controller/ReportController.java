package com.jtj.web.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jtj.web.common.ResultDto;
import com.jtj.web.common.utils.ExcelUtils;
import com.jtj.web.service.ReportService;


/**
 * Created by MrTT (jiang.taojie@foxmail.com) 2017/2/22.
 */
@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@GetMapping("/getOverall")
	@RequiresPermissions("report:getOverall")
	public ResultDto<Object> getOverall() {
		return reportService.getOverall();
	}

	@GetMapping("/getBorrow")
	@RequiresPermissions("report:getBorrow")
	public ResultDto<Object> getBorrow(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startTime,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime) {
		return reportService.getBorrow(startTime, endTime);
	}

	@GetMapping(path = "/download")
	public ResponseEntity<Resource> download() throws UnsupportedEncodingException {
		
		
		Map<String, Object> overall = reportService.getExcel();
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(overall);
		Map<String,String> keys = new HashMap<>();
		keys.put("normal", "正常");
		keys.put("borrow", "领用");
		keys.put("maintenance", "维修");
		keys.put("abandoned", "报废");
		byte[] exports = ExcelUtils.exports("资产总览", list,keys);
		
		
		ByteArrayResource resource = new ByteArrayResource(exports);
		return ResponseEntity.ok().header("Content-Disposition", "attachment;fileName='"+new String("资产总览.xls".getBytes(),"iso8859-1")+"'")
				.contentLength(exports.length).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

}
