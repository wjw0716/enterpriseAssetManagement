package com.jtj.web.dao;

import com.jtj.web.entity.AssetOperationRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2017/3/15.
 */
@Component
public interface AssetOperationRecordDao{

    int addOperationRecord(AssetOperationRecord record);

    List<AssetOperationRecord> getOperationRecordByUuid(@Param("uuid") String uuid);

}
