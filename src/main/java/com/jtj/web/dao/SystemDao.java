package com.jtj.web.dao;

import com.jtj.web.entity.Dictionary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2017/4/10.
 */
@Component
public interface SystemDao {

    int init();

    List<Dictionary> getDictionary();

}
