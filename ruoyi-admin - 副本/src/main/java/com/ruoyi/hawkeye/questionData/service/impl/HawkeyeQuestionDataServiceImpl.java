package com.ruoyi.hawkeye.questionData.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hawkeye.questionData.mapper.HawkeyeQuestionDataMapper;
import com.ruoyi.hawkeye.questionData.domain.HawkeyeQuestionData;
import com.ruoyi.hawkeye.questionData.service.IHawkeyeQuestionDataService;
import org.jsoup.Jsoup;

/**
 * 题目材料Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-28
 */
@Service
public class HawkeyeQuestionDataServiceImpl implements IHawkeyeQuestionDataService 
{
    @Autowired
    private HawkeyeQuestionDataMapper hawkeyeQuestionDataMapper;

    /**
     * 查询题目材料
     * 
     * @param qdId 题目材料主键
     * @return 题目材料
     */
    @Override
    public HawkeyeQuestionData selectHawkeyeQuestionDataByQdId(Long qdId)
    {
        return hawkeyeQuestionDataMapper.selectHawkeyeQuestionDataByQdId(qdId);
    }

    /**
     * 查询题目材料列表
     * 
     * @param hawkeyeQuestionData 题目材料
     * @return 题目材料
     */
    @Override
    public List<HawkeyeQuestionData> selectHawkeyeQuestionDataList(HawkeyeQuestionData hawkeyeQuestionData)
    {
        List<HawkeyeQuestionData> questionData = hawkeyeQuestionDataMapper.selectHawkeyeQuestionDataList(hawkeyeQuestionData);
        for (HawkeyeQuestionData questionDatum : questionData) {
            questionDatum.setQbContentText(html2Text(questionDatum.getQdContent()));
        }
        return questionData;
    }

    /**
     * Jsoup 精准提取富文本纯文本
     */
    public static String html2Text(String html) {
        if (html == null || html.isBlank()) {
            return "";
        }
        // 解析HTML，自动过滤标签、提取纯文本
        return Jsoup.parse(html).text();
    }

    /**
     * 新增题目材料
     * 
     * @param hawkeyeQuestionData 题目材料
     * @return 结果
     */
    @Override
    public int insertHawkeyeQuestionData(HawkeyeQuestionData hawkeyeQuestionData)
    {
        return hawkeyeQuestionDataMapper.insertHawkeyeQuestionData(hawkeyeQuestionData);
    }

    /**
     * 修改题目材料
     * 
     * @param hawkeyeQuestionData 题目材料
     * @return 结果
     */
    @Override
    public int updateHawkeyeQuestionData(HawkeyeQuestionData hawkeyeQuestionData)
    {
        return hawkeyeQuestionDataMapper.updateHawkeyeQuestionData(hawkeyeQuestionData);
    }

    /**
     * 批量删除题目材料
     * 
     * @param qdIds 需要删除的题目材料主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeQuestionDataByQdIds(Long[] qdIds)
    {
        return hawkeyeQuestionDataMapper.deleteHawkeyeQuestionDataByQdIds(qdIds);
    }

    /**
     * 删除题目材料信息
     * 
     * @param qdId 题目材料主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeQuestionDataByQdId(Long qdId)
    {
        return hawkeyeQuestionDataMapper.deleteHawkeyeQuestionDataByQdId(qdId);
    }
}
