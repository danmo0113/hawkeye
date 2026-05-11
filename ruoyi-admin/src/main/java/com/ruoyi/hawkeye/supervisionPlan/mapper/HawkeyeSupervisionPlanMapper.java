package com.ruoyi.hawkeye.supervisionPlan.mapper;

import java.util.List;
import com.ruoyi.hawkeye.supervisionPlan.domain.HawkeyeSupervisionPlan;

/**
 * 督学计划Mapper接口
 * 
 * @author 墨
 * @date 2026-05-11
 */
public interface HawkeyeSupervisionPlanMapper 
{
    /**
     * 查询督学计划
     * 
     * @param planId 督学计划主键
     * @return 督学计划
     */
    public HawkeyeSupervisionPlan selectHawkeyeSupervisionPlanByPlanId(Long planId);

    /**
     * 查询督学计划列表
     * 
     * @param hawkeyeSupervisionPlan 督学计划
     * @return 督学计划集合
     */
    public List<HawkeyeSupervisionPlan> selectHawkeyeSupervisionPlanList(HawkeyeSupervisionPlan hawkeyeSupervisionPlan);

    /**
     * 新增督学计划
     * 
     * @param hawkeyeSupervisionPlan 督学计划
     * @return 结果
     */
    public int insertHawkeyeSupervisionPlan(HawkeyeSupervisionPlan hawkeyeSupervisionPlan);

    /**
     * 修改督学计划
     * 
     * @param hawkeyeSupervisionPlan 督学计划
     * @return 结果
     */
    public int updateHawkeyeSupervisionPlan(HawkeyeSupervisionPlan hawkeyeSupervisionPlan);

    /**
     * 删除督学计划
     * 
     * @param planId 督学计划主键
     * @return 结果
     */
    public int deleteHawkeyeSupervisionPlanByPlanId(Long planId);

    /**
     * 批量删除督学计划
     * 
     * @param planIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHawkeyeSupervisionPlanByPlanIds(Long[] planIds);
}
