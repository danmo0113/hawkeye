package com.ruoyi.hawkeye.knowledgePoint.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hawkeye.knowledgePoint.mapper.HawkeyeKnowledgePointMapper;
import com.ruoyi.hawkeye.knowledgePoint.domain.HawkeyeKnowledgePoint;
import com.ruoyi.hawkeye.knowledgePoint.service.IHawkeyeKnowledgePointService;

/**
 * 章节管理Service业务层处理
 * 
 * @author 墨
 * @date 2026-04-22
 */
@Service
public class HawkeyeKnowledgePointServiceImpl implements IHawkeyeKnowledgePointService 
{
    @Autowired
    private HawkeyeKnowledgePointMapper hawkeyeKnowledgePointMapper;

    /**
     * 查询章节管理
     * 
     * @param kpId 章节管理主键
     * @return 章节管理
     */
    @Override
    public HawkeyeKnowledgePoint selectHawkeyeKnowledgePointByKpId(Long kpId)
    {
        return hawkeyeKnowledgePointMapper.selectHawkeyeKnowledgePointByKpId(kpId);
    }

    /**
     * 查询章节管理列表
     * 
     * @param hawkeyeKnowledgePoint 章节管理
     * @return 章节管理
     */
    @Override
    public List<HawkeyeKnowledgePoint> selectHawkeyeKnowledgePointList(HawkeyeKnowledgePoint hawkeyeKnowledgePoint)
    {
        return hawkeyeKnowledgePointMapper.selectHawkeyeKnowledgePointList(hawkeyeKnowledgePoint);
    }

    @Override
    public List<HawkeyeKnowledgePoint> selectHawkeyeKnowledgePointParentList(HawkeyeKnowledgePoint hawkeyeKnowledgePoint) {
        return hawkeyeKnowledgePointMapper.selectHawkeyeKnowledgePointParentList(hawkeyeKnowledgePoint);
    }

    /**
     * 新增章节管理
     * 
     * @param hawkeyeKnowledgePoint 章节管理
     * @return 结果
     */
    @Override
    public int insertHawkeyeKnowledgePoint(HawkeyeKnowledgePoint hawkeyeKnowledgePoint)
    {
        hawkeyeKnowledgePoint.setCreateTime(DateUtils.getNowDate());
        return hawkeyeKnowledgePointMapper.insertHawkeyeKnowledgePoint(hawkeyeKnowledgePoint);
    }

    /**
     * 修改章节管理
     * 
     * @param hawkeyeKnowledgePoint 章节管理
     * @return 结果
     */
    @Override
    public int updateHawkeyeKnowledgePoint(HawkeyeKnowledgePoint hawkeyeKnowledgePoint)
    {
        hawkeyeKnowledgePoint.setUpdateTime(DateUtils.getNowDate());
        return hawkeyeKnowledgePointMapper.updateHawkeyeKnowledgePoint(hawkeyeKnowledgePoint);
    }

    /**
     * 批量删除章节管理
     * 
     * @param kpIds 需要删除的章节管理主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeKnowledgePointByKpIds(Long[] kpIds)
    {
        return hawkeyeKnowledgePointMapper.deleteHawkeyeKnowledgePointByKpIds(kpIds);
    }

    /**
     * 删除章节管理信息
     * 
     * @param kpId 章节管理主键
     * @return 结果
     */
    @Override
    public int deleteHawkeyeKnowledgePointByKpId(Long kpId)
    {
        return hawkeyeKnowledgePointMapper.deleteHawkeyeKnowledgePointByKpId(kpId);
    }
}
