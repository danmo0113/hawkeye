package com.ruoyi.hawkeye.questionKp.mapper;

import java.util.List;
import com.ruoyi.hawkeye.questionKp.domain.HawkeyeQuestionKp;

/**
 * 题目-考点关联Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-28
 */
public interface HawkeyeQuestionKpMapper 
{
    /**
     * 查询题目-考点关联
     * 
     * @param id 题目-考点关联主键
     * @return 题目-考点关联
     */
    public HawkeyeQuestionKp selectHawkeyeQuestionKpById(Long id);

    /**
     * 查询题目-考点关联列表
     * 
     * @param hawkeyeQuestionKp 题目-考点关联
     * @return 题目-考点关联集合
     */
    public List<HawkeyeQuestionKp> selectHawkeyeQuestionKpList(HawkeyeQuestionKp hawkeyeQuestionKp);

    /**
     * 新增题目-考点关联
     * 
     * @param hawkeyeQuestionKp 题目-考点关联
     * @return 结果
     */
    public int insertHawkeyeQuestionKp(HawkeyeQuestionKp hawkeyeQuestionKp);

    /**
     * 修改题目-考点关联
     * 
     * @param hawkeyeQuestionKp 题目-考点关联
     * @return 结果
     */
    public int updateHawkeyeQuestionKp(HawkeyeQuestionKp hawkeyeQuestionKp);

    /**
     * 删除题目-考点关联
     * 
     * @param id 题目-考点关联主键
     * @return 结果
     */
    public int deleteHawkeyeQuestionKpById(Long id);

    /**
     * 批量删除题目-考点关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHawkeyeQuestionKpByIds(Long[] ids);
}
