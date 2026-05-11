package com.ruoyi.hawkeye.question.mapper;

import java.util.List;
import com.ruoyi.hawkeye.question.domain.HawkeyeQuestion;

/**
 * 题库题目Mapper接口
 * 
 * @author 墨
 * @date 2026-04-27
 */
public interface HawkeyeQuestionMapper 
{
    /**
     * 查询题库题目
     * 
     * @param questionId 题库题目主键
     * @return 题库题目
     */
    public HawkeyeQuestion selectHawkeyeQuestionByQuestionId(Long questionId);

    /**
     * 查询题库题目列表
     * 
     * @param hawkeyeQuestion 题库题目
     * @return 题库题目集合
     */
    public List<HawkeyeQuestion> selectHawkeyeQuestionList(HawkeyeQuestion hawkeyeQuestion);

    public List<HawkeyeQuestion> selectHawkeyeQuestionPathList(HawkeyeQuestion hawkeyeQuestion);

    /**
     * 新增题库题目
     * 
     * @param hawkeyeQuestion 题库题目
     * @return 结果
     */
    public int insertHawkeyeQuestion(HawkeyeQuestion hawkeyeQuestion);

    /**
     * 修改题库题目
     * 
     * @param hawkeyeQuestion 题库题目
     * @return 结果
     */
    public int updateHawkeyeQuestion(HawkeyeQuestion hawkeyeQuestion);

    /**
     * 删除题库题目
     * 
     * @param questionId 题库题目主键
     * @return 结果
     */
    public int deleteHawkeyeQuestionByQuestionId(Long questionId);

    /**
     * 批量删除题库题目
     * 
     * @param questionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHawkeyeQuestionByQuestionIds(Long[] questionIds);
}
