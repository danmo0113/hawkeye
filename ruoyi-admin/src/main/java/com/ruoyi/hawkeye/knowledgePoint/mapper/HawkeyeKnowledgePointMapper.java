package com.ruoyi.hawkeye.knowledgePoint.mapper;

import java.util.List;
import com.ruoyi.hawkeye.knowledgePoint.domain.HawkeyeKnowledgePoint;

/**
 * 章节管理Mapper接口
 * 
 * @author 墨
 * @date 2026-04-22
 */
public interface HawkeyeKnowledgePointMapper 
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
     * 带追溯节点的章节管理列表
     * @param hawkeyeKnowledgePoint
     * @return
     */
    public List<HawkeyeKnowledgePoint> selectHawkeyeKnowledgePointParentList(HawkeyeKnowledgePoint hawkeyeKnowledgePoint);

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
     * 删除章节管理
     * 
     * @param kpId 章节管理主键
     * @return 结果
     */
    public int deleteHawkeyeKnowledgePointByKpId(Long kpId);

    /**
     * 批量删除章节管理
     *
     * @param kpIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHawkeyeKnowledgePointByKpIds(Long[] kpIds);

    /**
     * 根据名称、父级ID、层级精确查询知识点
     */
    public HawkeyeKnowledgePoint selectHawkeyeKnowledgePointByParentAndName(
            @org.apache.ibatis.annotations.Param("parentId") Long parentId,
            @org.apache.ibatis.annotations.Param("kpName") String kpName,
            @org.apache.ibatis.annotations.Param("level") Integer level);
}
