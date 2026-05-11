package com.ruoyi.hawkeye.questionKp.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hawkeye.questionKp.mapper.HawkeyeQuestionKpMapper;
import com.ruoyi.hawkeye.questionKp.domain.HawkeyeQuestionKp;
import com.ruoyi.hawkeye.questionKp.service.IHawkeyeQuestionKpService;

/**
 * 题目-考点关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-28
 */
@Service
public class HawkeyeQuestionKpServiceImpl implements IHawkeyeQuestionKpService 
{
    @Autowired
    private HawkeyeQuestionKpMapper hawkeyeQuestionKpMapper;

    /**
     * 查询题目-考点关联
     * 
     * @param id 题目-考点关联主键
     * @return 题目-考点关联
     */
    @Override
    public HawkeyeQuestionKp selectHawkeyeQuestionKpById(Long id)
    {
        return hawkeyeQuestionKpMapper.selectHawkeyeQuestionKpById(id);
    }

    /**
     * 查询题目-考点关联列表
     * 
     * @param hawkeyeQuestionKp 题目-考点关联
     * @return 题目-考点关联
     */
    @Override
    public List<HawkeyeQuestionKp> selectHawkeyeQuestionKpList(HawkeyeQuestionKp hawkeyeQuestionKp)
    {
        return hawkeyeQuestionKpMapper.selectHawkeyeQuestionKpList(hawkeyeQuestionKp);
    }

    /**
     * 新增题目-考点关联
     * 
     * @param hawkeyeQuestionKp 题目-考点关联
     * @return 结果
     */
    @Override
    public int insertHawkeyeQuestionKp(HawkeyeQuestionKp hawkeyeQuestionKp)
    {
        return hawkeyeQuestionKpMapper.insertHawkeyeQuestionKp(hawkeyeQuestionKp);
    }

    /**
     * 修改题目-考点关联
     * 
     * @param hawkeyeQuestionKp 题目-考点关联
     * @return 结果
     */
    @Override
    public int updateHawkeyeQuestionKp(HawkeyeQuestionKp hawkeyeQuestionKp)
    {
        return hawkeyeQuestionKpMapper.updateHawkeyeQuestionKp(hawkeyeQuestionKp);
    }

    /**
     * 批量删除题目-考点关联
     * 
     * @param ids 需要删除的题目-考点关联主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeQuestionKpByIds(Long[] ids)
    {
        return hawkeyeQuestionKpMapper.deleteHawkeyeQuestionKpByIds(ids);
    }

    /**
     * 删除题目-考点关联信息
     * 
     * @param id 题目-考点关联主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeQuestionKpById(Long id)
    {
        return hawkeyeQuestionKpMapper.deleteHawkeyeQuestionKpById(id);
    }
}
