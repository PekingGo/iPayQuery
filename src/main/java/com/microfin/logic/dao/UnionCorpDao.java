package com.microfin.logic.dao;

import com.microfin.logic.entity.UnionCorp;

import java.util.List;

/**
* 银联认证机构
*
 * Created by manxiaolei on 2017/10/11.
 */
public interface UnionCorpDao {
	UnionCorp query(String id);
	List<UnionCorp> queryByCategory(String key);
}
