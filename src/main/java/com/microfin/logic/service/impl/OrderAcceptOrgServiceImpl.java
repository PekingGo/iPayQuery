package com.microfin.logic.service.impl;

import com.microfin.common.util.Properties;
import com.microfin.common.util.StringUtil;
import com.microfin.logic.dao.BlackListDao;
import com.microfin.logic.dao.OrderAcceptOrgDao;
import com.microfin.logic.entity.*;
import com.microfin.logic.service.WatchService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("t_order_accept_org")
@Transactional(readOnly = true)
public class OrderAcceptOrgServiceImpl implements WatchService {
    @Autowired
    private OrderAcceptOrgDao orderAcceptOrgDao;

    @Override
    public void queryForData(Map<String, List<Keyword>> queryMap, Map<String, Object> resultMap) {
        List<QueryResult> list = new ArrayList<QueryResult>();
        List<Keyword> queryList = queryMap.get("t_order_accept_org");
        for (int i = 0; i < queryList.size(); i++) {
            Keyword keyword = queryList.get(i);
            QueryResult queryResult = new QueryResult();
            // 输入的查询内容
            queryResult.setKey(resultMap.get("key").toString());
            // 类别
            String category = Properties.getValue("language-zh-CN", "t_order_accept_org", "");
            queryResult.setCategory(category);
            // 信息标签
            List<QueryLabel> labelList = new ArrayList<QueryLabel>();
            OrderAcceptOrg bean = orderAcceptOrgDao.query(keyword.getP_id());
            queryResult.setKeyword(keyword.getKey_word().length()>13?keyword.getKey_word().substring(0,13)+"..":keyword.getKey_word());
            QueryLabel label = new QueryLabel("机构号",bean.getOrgNo(),true,true);
            // 机构号
            labelList.add(label);
            // 地区码
            if(StringUtil.isNotEmpty(bean.getAddrNo())&&!"0".equals(bean.getAddrNo())){
                label = new QueryLabel("地区码",bean.getAddrNo(),true,true);
                labelList.add(label);
            }
            // 银行或收单机构名称
            label = new QueryLabel("机构名称",bean.getBankName(),true,true);
            labelList.add(label);
            //机构类型
            label = new QueryLabel("机构类型",bean.getOrgType(),true,false);
            labelList.add(label);
            queryResult.setInfoArr(labelList);
            if (i == 0) {
                list.add(queryResult);
            } else {
                list.get(0).setMoreList(queryResult);
            }
        }
        if (resultMap.containsKey("list")) {
            JSONArray dataArr = JSONArray.fromObject(resultMap.get("list"));
            dataArr.addAll(JSONArray.fromObject(list));
            resultMap.put("list", dataArr);
        } else {
            resultMap.put("list", JSONArray.fromObject(list));
        }
    }
}