package com.ruoyi.hawkeye.question.service;

import java.util.List;

import com.ruoyi.hawkeye.question.domain.HawkeyeExportQuestion;
import com.ruoyi.hawkeye.question.domain.HawkeyeQuestion;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 题库题目Service接口
 * 
 * @author 墨
 * @date 2026-04-27
 */
public interface IHawkeyeQuestionService 
{
    /**
     * 查询题库题目
     * 
     * @param questionId 题库题目主键
     * @return 题库题目
     */
    public HawkeyeQuestion selectHawkeyeQuestionByQuestionId(Long questionId);

    public void selectHawkeyeQuestionListExport(HttpServletResponse response, List<HawkeyeQuestion> hawkeyeQuestionList);

    /**
     * 查询题库题目列表
     * 
     * @param hawkeyeQuestion 题库题目
     * @return 题库题目集合
     */
    public List<HawkeyeQuestion> selectHawkeyeQuestionList(HawkeyeQuestion hawkeyeQuestion);

    public List<HawkeyeQuestion> selectHawkeyeQuestionPathList(HawkeyeQuestion hawkeyeQuestion);

    /**
     * 导入
     * @param productList
     * @param updateSupport
     * @param operName
     * @return
     */
    public String importData(List<HawkeyeExportQuestion> exportQuestionList, boolean updateSupport, String operName);

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
     * 批量删除题库题目
     * 
     * @param questionIds 需要删除的题库题目主键集合
     * @return 结果
     */
    public int deleteHawkeyeQuestionByQuestionIds(Long[] questionIds);

    /**
     * 删除题库题目信息
     * 
     * @param questionId 题库题目主键
     * @return 结果
     */
    public int deleteHawkeyeQuestionByQuestionId(Long questionId);
}
