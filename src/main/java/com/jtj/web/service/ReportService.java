package com.jtj.web.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jtj.web.common.ResultDto;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2016/12/23 23:26 End.
 */
@Service
public interface ReportService {

    ResultDto<Object> getOverall();

    ResultDto<Object> getBorrow(LocalDate startTime, LocalDate endTime);

	Map<String, Object> getExcel();
}
