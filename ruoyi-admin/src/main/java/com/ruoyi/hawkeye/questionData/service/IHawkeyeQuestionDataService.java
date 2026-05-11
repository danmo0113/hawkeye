package com.ruoyi.hawkeye.questionData.service;

import java.util.List;
import com.ruoyi.hawkeye.questionData.domain.HawkeyeQuestionData;

/**
 * 题目材料Service接口
 * 
 * @author ruoyi
 * @date 2026-04-28
 */
public interface IHawkeyeQuestionDataService 
{
    /**
     * 查询题目材料
     * 
     * @param qdId 题目材料主键
     * @return 题目材料
     */
    public HawkeyeQuestionData selectHawkeyeQuestionDataByQdId(Long qdId);

    /**
     * 查询题目材料列表
     * 
     * @param hawkeyeQuestionData 题目材料
     * @return 题目材料集合
     */
    public List<HawkeyeQuestionData> selectHawkeyeQuestionDataList(HawkeyeQuestionData hawkeyeQuestionData);

    /**
     * 新增题目材料
     * 
     * @param hawkeyeQuestionData 题目材料
     * @return 结果
     */
    public int insertHawkeyeQuestionData(HawkeyeQuestionData hawkeyeQuestionData);

    /**
     * 修改题目材料
     * 
     * @param hawkeyeQuestionData 题目材料
     * @return 结果
     */
    public int updateHawkeyeQuestionData(HawkeyeQuestionData hawkeyeQuestionData);

    /**
     * 批量删除题目材料
     * 
     * @param qdIds 需要删除的题目材料主键集合
     * @return 结果
     */
    public int deleteHawkeyeQuestionDataByQdIds(Long[] qdIds);

    /**
     * 删除题目材料信息
     * 
     * @param qdId 题目材料主键
     * @return 结果
     */
    public int deleteHawkeyeQuestionDataByQdId(Long qdId);
}
