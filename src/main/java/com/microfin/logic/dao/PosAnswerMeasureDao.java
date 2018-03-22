package com.microfin.logic.dao;

import com.microfin.logic.entity.PosAnswerMeasure;

import java.util.List;

/**
* POS应答码Dao
*
 * Created by manxiaolei on 2017/10/11.
 */
public interface PosAnswerMeasureDao {
	PosAnswerMeasure query(String id);

	List<PosAnswerMeasure> queryByCategory(String key);
}
