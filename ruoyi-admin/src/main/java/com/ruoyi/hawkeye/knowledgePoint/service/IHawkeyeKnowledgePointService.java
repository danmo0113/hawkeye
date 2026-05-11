package com.ruoyi.hawkeye.knowledgePoint.service;

import java.util.List;
import com.ruoyi.hawkeye.knowledgePoint.domain.HawkeyeKnowledgePoint;

/**
 * 章节管理Service接口
 * 
 * @author 墨
 * @date 2026-04-22
 */
public interface IHawkeyeKnowledgePointService 
{
    /**
     * 查询章节管理
     * 
     * @param kpId 章节管理主键
     * @return 章节管理
     */
    public HawkeyeKnowledgePoint selectHawkeyeKnowledgePointByKpId(Long kpId);

    /**
     * 查询章节管理列表
     * 
     * @param hawkeyeKnowledgePoint 章节管理
     * @return 章节管理集合
     */
    public List<HawkeyeKnowledgePoint> selectHawkeyeKnowledgePointList(HawkeyeKnowledgePoint hawkeyeKnowledgePoint);

    /**
     * 查询章节管理带父级的列表
     * @param hawkeyeKnowledgePoint
     * @return
     */
//    public List<HawkeyeKnowledgePoint> selectHawkeyeKnowledgePointParentList(HawkeyeKnowledgePoint hawkeyeKnowledgePoint);

    /**
     * 新增章节管理
     * 
     * @param hawkeyeKnowledgePoint 章节管理
     * @return 结果
     */
    public int insertHawkeyeKnowledgePoint(HawkeyeKnowledgePoint hawkeyeKnowledgePoint);

    /**
     * 修改章节管理
     * 
     * @param hawkeyeKnowledgePoint 章节管理
     * @return 结果
     */
    public int updateHawkeyeKnowledgePoint(HawkeyeKnowledgePoint hawkeyeKnowledgePoint);

    /**
     * 批量删除章节管理
     * 
     * @param kpIds 需要删除的章节管理主键集合
     * @return 结果
     */
    public int deleteHawkeyeKnowledgePointByKpIds(Long[] kpIds);

    /**
     * 删除章节管理信息
     * 
     * @param kpId 章节管理主键
     * @return 结果
     */
    public int deleteHawkeyeKnowledgePointByKpId(Long kpId);
}
